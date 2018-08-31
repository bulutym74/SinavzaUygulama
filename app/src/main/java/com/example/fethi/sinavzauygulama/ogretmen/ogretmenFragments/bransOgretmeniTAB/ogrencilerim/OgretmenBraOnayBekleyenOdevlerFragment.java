package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.bransOgretmeniTAB.ogrencilerim;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.activities.UserInfoItem;
import com.example.fethi.sinavzauygulama.diger.Islevsel;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OdevOnaylaDersItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OdevOnaylaOdevItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgrenciItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgretmenExpLVAdapterOnayBekleyenOdevler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenBraOnayBekleyenOdevlerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public OgretmenExpLVAdapterOnayBekleyenOdevler expand_adapter;
    public ExpandableListView exp_lv_onayBekleyenOdevler;
    public FloatingActionButton fab_check;
    public FloatingActionButton fab_cancel;
    private ArrayList<OdevOnaylaDersItem> dersler = new ArrayList<>();
    JSONArray seciliOdevler;

    SwipeRefreshLayout refreshLayout;
    JSONObject res;
    String token;
    Realm realm = Realm.getDefaultInstance();

    LinearLayout yokView;
    public OgrenciItem seciliOgrenci;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_onay_bekleyen_odevler, container, false);

        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        onBackPressed();
                    }
                }, 100);
            }
        });

        yokView = view.findViewById(R.id.yokView);

        exp_lv_onayBekleyenOdevler = view.findViewById(R.id.exp_lv_onayBekleyenOdevler);

        fab_cancel = view.findViewById(R.id.fab_cancel);
        fab_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                seciliOdevler = new JSONArray();

                for (OdevOnaylaDersItem ders : dersler) {
                    for (OdevOnaylaOdevItem odev : ders.getOdevler()) {
                        if (odev.isSelected()) {
                            seciliOdevler.put(odev.getId());
                        }
                    }
                }
                if (seciliOdevler.length() == 0) {
                    Toast toast = Toast.makeText(getActivity(), "Reddetmek icin ödev seçiniz", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 300);
                    toast.show();
                } else
                    ShowConfirmDialogReddet(getActivity(), seciliOdevler);

            }
        });

        fab_check = view.findViewById(R.id.fab_check);
        fab_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                seciliOdevler = new JSONArray();

                for (OdevOnaylaDersItem ders : dersler) {
                    for (OdevOnaylaOdevItem odev : ders.getOdevler()) {
                        if (odev.isSelected()) {
                            seciliOdevler.put(odev.getId());
                        }
                    }
                }
                if (seciliOdevler.length() == 0) {
                    Toast toast = Toast.makeText(getActivity(), "Onaylamak icin ödev seçiniz", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 300);
                    toast.show();
                } else
                    ShowConfirmDialogOnayla(getActivity(), seciliOdevler);

            }
        });


        expand_adapter = new OgretmenExpLVAdapterOnayBekleyenOdevler(getActivity(), dersler);
        exp_lv_onayBekleyenOdevler.setAdapter(expand_adapter);

        refreshLayout = view.findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.primaryPurple),
                getResources().getColor(R.color.primaryOrange),
                getResources().getColor(R.color.lightgreen));


        if (dersler.size() == 0)
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                    parseJSON();
                }
            });


        return view;
    }

    private void ShowConfirmDialogReddet(Context context, final JSONArray seciliOdevler) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage(seciliOdevler.length() + " ödev reddedilsin mi?")
                .setCancelable(true)
                .setPositiveButton("REDDET", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        final JSONObject parameters = new JSONObject();

                        try {
                            parameters.accumulate("odevler", seciliOdevler);
                            parameters.accumulate("onay", false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                        token = realm.where(UserInfoItem.class).findAll().get(0).getToken();

                        JsonObjectRequest objectRequest = new JsonObjectRequest(
                                Request.Method.POST,
                                Islevsel.odevOnayRetURL,
                                parameters,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(final JSONObject response) {
                                        try {
                                            if (response.getBoolean("isFailed")) {
                                                Log.e("FAILED : ", response.getString("message"));

                                                Toast toast = Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT);
                                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                                toast.show();
                                            } else {
                                                res = response;

                                                Toast toast = Toast.makeText(getApplicationContext(), seciliOdevler.length() + " ögrenci reddedildi!", Toast.LENGTH_SHORT);
                                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                                toast.show();

                                                parseJSON();
                                            }
                                            expand_adapter.notifyDataSetChanged();
                                        } catch (JSONException e) {

                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Log.e("ERROR : ", error.toString());

                                        Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                                        toast.show();
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                final Map<String, String> headers = new HashMap<>();
                                headers.putAll(super.getHeaders());

                                headers.put("Authorization", "Bearer " + token);

                                return headers;
                            }
                        };
                        requestQueue.add(objectRequest);

                    }
                })
                .setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void ShowConfirmDialogOnayla(Context context, final JSONArray seciliOdevler) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage(seciliOdevler.length() + " ödev onaylansın mı?")
                .setCancelable(true)
                .setPositiveButton("ONAYLA", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final JSONObject parameters = new JSONObject();

                        try {
                            parameters.accumulate("odevler", seciliOdevler);
                            parameters.accumulate("onay", true);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                        token = realm.where(UserInfoItem.class).findAll().get(0).getToken();

                        JsonObjectRequest objectRequest = new JsonObjectRequest(
                                Request.Method.POST,
                                Islevsel.odevOnayRetURL,
                                parameters,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(final JSONObject response) {
                                        try {
                                            if (response.getBoolean("isFailed")) {
                                                Log.e("FAILED : ", response.getString("message"));

                                                Toast toast = Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT);
                                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                                toast.show();
                                            } else {
                                                res = response;

                                                Toast toast = Toast.makeText(getApplicationContext(), seciliOdevler.length() + " öğrenci onaylandı!", Toast.LENGTH_SHORT);
                                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                                toast.show();

                                                parseJSON();
                                            }
                                            expand_adapter.notifyDataSetChanged();
                                        } catch (JSONException e) {

                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Log.e("ERROR : ", error.toString());

                                        Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                                        toast.show();
                                    }
                                }
                        ) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                final Map<String, String> headers = new HashMap<>();
                                headers.putAll(super.getHeaders());

                                headers.put("Authorization", "Bearer " + token);

                                return headers;
                            }
                        };
                        requestQueue.add(objectRequest);

                    }
                })
                .setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void parseJSON() {

        final JSONObject parameters = new JSONObject();

        try {
            parameters.accumulate("ogrenciId", seciliOgrenci.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        refreshLayout.setRefreshing(true);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        token = realm.where(UserInfoItem.class).findAll().get(0).getToken();

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                Islevsel.odevOnaylaURL,
                parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        try {
                            if (response.getBoolean("isFailed")) {
                                Log.e("FAILED : ", response.getString("message"));

                                Toast toast = Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                toast.show();

                            } else {
                                res = response;
                                dersler.clear();
                                for (int i = 0; i < res.getJSONArray("dersler").length(); i++) {
                                    JSONObject dersItem = (JSONObject) res.getJSONArray("dersler").get(i);
                                    OdevOnaylaDersItem tempDers = new OdevOnaylaDersItem(dersItem.getString("name"), new ArrayList<OdevOnaylaOdevItem>());

                                    for (int j = 0; j < dersItem.getJSONArray("odevler").length(); j++) {
                                        JSONObject odevItem = (JSONObject) dersItem.getJSONArray("odevler").get(j);
                                        OdevOnaylaOdevItem tempOdev = new OdevOnaylaOdevItem(odevItem.getString("testAdi"),
                                                odevItem.getString("kitapAdi"),
                                                odevItem.getString("konuAdi"),
                                                odevItem.getInt("id"));

                                        tempDers.getOdevler().add(tempOdev);
                                    }
                                    dersler.add(tempDers);
                                }
                            }
                            isEmpty();
                            expand_adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            isEmpty();
                            e.printStackTrace();
                        }

                        refreshLayout.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR : ", error.toString());
                        isEmpty();

                        Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                        toast.show();
                        refreshLayout.setRefreshing(false);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.putAll(super.getHeaders());

                headers.put("Authorization", "Bearer " + token);

                return headers;
            }
        };
        requestQueue.add(objectRequest);
    }


    public void isEmpty() {
        if (dersler.isEmpty()) {
            yokView.setVisibility(View.VISIBLE);
        } else
            yokView.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        parseJSON();
    }

    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

}