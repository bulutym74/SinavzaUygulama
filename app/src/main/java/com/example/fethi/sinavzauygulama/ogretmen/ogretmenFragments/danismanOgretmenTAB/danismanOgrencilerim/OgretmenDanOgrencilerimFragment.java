package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.danismanOgrencilerim;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.activities.UserInfoItem;
import com.example.fethi.sinavzauygulama.diger.Islevsel;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgrenciItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgretmenExpLVAdapterDanOgrencilerim;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.SinifItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.bransOgretmeniTAB.ogrencilerim.OgretmenBraOgrenciBilgilerFragment;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.bransOgretmeniTAB.ogrencilerim.OgretmenBraOnayBekleyenOdevlerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenDanOgrencilerimFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public OgretmenExpLVAdapterDanOgrencilerim expand_adapter;
    public ExpandableListView expand_lv_siniflar;
    public ArrayList<SinifItem> siniflar = new ArrayList<>();

    SwipeRefreshLayout refreshLayout;
    JSONObject res;
    String token;

    LinearLayout yokView;

    public int durum;

    int childPosition;
    int groupPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_danisman_ogrencilerim, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
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
        expand_lv_siniflar = view.findViewById(R.id.expand_lv_dan_ogr);

        expand_adapter = new OgretmenExpLVAdapterDanOgrencilerim(getActivity(), siniflar);
        expand_lv_siniflar.setAdapter(expand_adapter);

        expand_lv_siniflar.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (durum == 0){
                    OgretmenDanOgrenciBilgilerFragment nextFrag = new OgretmenDanOgrenciBilgilerFragment();
                    nextFrag.seciliOgrenci = siniflar.get(groupPosition).getOgrenciler().get(childPosition);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                            .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
                else {
                    OgretmenDanOnayBekleyenOdevlerFragment nextFrag = new OgretmenDanOnayBekleyenOdevlerFragment();
                    nextFrag.seciliOgrenci = siniflar.get(groupPosition).getOgrenciler().get(childPosition);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                            .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                }
                return true;
            }

        });
        expand_lv_siniflar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                int itemType = ExpandableListView.getPackedPositionType(id);

                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    childPosition = ExpandableListView.getPackedPositionChild(id);
                    groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    ShowConfirmDialogSilOgrenci(getContext(), siniflar.get(groupPosition).getOgrenciler().get(childPosition));
                    //do your per-item callback here
                    return true; //true if we consumed the click, false if not

                } else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    ShowConfirmDialogSilSinif(getContext(), siniflar.get(groupPosition));
                    //do your per-group callback here
                    return true; //true if we consumed the click, false if not

                } else {
                    // null item; we don't consume the click
                    return false;
                }
            }
        });

        refreshLayout = view.findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.primaryPurple),
                getResources().getColor(R.color.primaryOrange),
                getResources().getColor(R.color.lightgreen));


        if (siniflar.size() == 0)
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                    if (durum == 0) parseJSON_0();
                    else parseJSON_1();
                }
            });

        return view;
    }
    private void ShowConfirmDialogSilSinif(Context context, final SinifItem seciliSinif) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage(seciliSinif.getSinifAdi()+" sınıfı silinsin mi?")
                .setCancelable(true)
                .setPositiveButton("SİL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                siniflar.remove(seciliSinif);

                                Toast toast = Toast.makeText(getApplicationContext(), seciliSinif.getSinifAdi()+" sınıfı silindi", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                toast.show();

                                expand_adapter.notifyDataSetChanged();
                            }
                        }, 500);





//                        final JSONObject parameters = new JSONObject();
//
//                        try {
//                            parameters.accumulate("silinecekSinif", seciliSinif.getId());
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//
//                        try {
//                            Realm realm = Realm.getDefaultInstance();
//                            token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        JsonObjectRequest objectRequest = new JsonObjectRequest(
//                                Request.Method.POST,
//                                Islevsel.ogretmenSinifSilURL,
//                                parameters,
//                                new Response.Listener<JSONObject>() {
//                                    @Override
//                                    public void onResponse(final JSONObject response) {
//                                        try {
//                                            if (response.getBoolean("isFailed")) {
//                                                Log.e("FAILED : ", response.getString("message"));
//
//                                                Toast toast = Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT);
//                                                toast.setGravity(Gravity.BOTTOM, 0, 300);
//                                                toast.show();
//                                            } else {
//                                                res = response;
//
//                                                siniflar.remove(seciliSinif);
//
//                                                Toast toast = Toast.makeText(getApplicationContext(), seciliSinif.getSinifAdi()+" sınıfı silindi", Toast.LENGTH_SHORT);
//                                                toast.setGravity(Gravity.BOTTOM, 0, 300);
//                                                toast.show();
//
//                                                expand_adapter.notifyDataSetChanged();
//                                            }
//
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                },
//                                new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        Log.e("ERROR : ", error.toString());
//
//                                        Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
//                                        toast.setGravity(Gravity.BOTTOM, 0, 300);
//                                        toast.show();
//                                    }
//                                }
//                        ) {
//                            @Override
//                            public Map<String, String> getHeaders() throws AuthFailureError {
//                                final Map<String, String> headers = new HashMap<>();
//                                headers.putAll(super.getHeaders());
//
//                                headers.put("Authorization", "Bearer " + token);
//
//                                return headers;
//                            }
//                        };
//                        requestQueue.add(objectRequest);
//                        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
//                                60000,
//                                3,
//                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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

    private void ShowConfirmDialogSilOgrenci(Context context, final OgrenciItem seciliOgrenci) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage(seciliOgrenci.getOgrAdi()+" sınıfınızdan silinsin mi?")
                .setCancelable(true)
                .setPositiveButton("SİL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                siniflar.get(groupPosition).getOgrenciler().remove(seciliOgrenci);

                                Toast toast = Toast.makeText(getApplicationContext(), seciliOgrenci.getOgrAdi()+" sınıfınızdan silindi", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                toast.show();

                                expand_adapter.notifyDataSetChanged();
                            }
                        }, 500);






//                        final JSONObject parameters = new JSONObject();
//
//                        try {
//                            parameters.accumulate("silinecekSinif", seciliSinif.getId());
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//
//                        try {
//                            Realm realm = Realm.getDefaultInstance();
//                            token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        JsonObjectRequest objectRequest = new JsonObjectRequest(
//                                Request.Method.POST,
//                                Islevsel.ogretmenSinifSilURL,
//                                parameters,
//                                new Response.Listener<JSONObject>() {
//                                    @Override
//                                    public void onResponse(final JSONObject response) {
//                                        try {
//                                            if (response.getBoolean("isFailed")) {
//                                                Log.e("FAILED : ", response.getString("message"));
//
//                                                Toast toast = Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT);
//                                                toast.setGravity(Gravity.BOTTOM, 0, 300);
//                                                toast.show();
//                                            } else {
//                                                res = response;
//
//                                                siniflar.remove(seciliSinif);
//
//                                                Toast toast = Toast.makeText(getApplicationContext(), seciliSinif.getSinifAdi()+" sınıfı silindi", Toast.LENGTH_SHORT);
//                                                toast.setGravity(Gravity.BOTTOM, 0, 300);
//                                                toast.show();
//
//                                                expand_adapter.notifyDataSetChanged();
//                                            }
//
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                },
//                                new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        Log.e("ERROR : ", error.toString());
//
//                                        Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
//                                        toast.setGravity(Gravity.BOTTOM, 0, 300);
//                                        toast.show();
//                                    }
//                                }
//                        ) {
//                            @Override
//                            public Map<String, String> getHeaders() throws AuthFailureError {
//                                final Map<String, String> headers = new HashMap<>();
//                                headers.putAll(super.getHeaders());
//
//                                headers.put("Authorization", "Bearer " + token);
//
//                                return headers;
//                            }
//                        };
//                        requestQueue.add(objectRequest);
//                        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
//                                60000,
//                                3,
//                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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

    public void parseJSON_0() {

        refreshLayout.setRefreshing(true);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        try {
            Realm realm = Realm.getDefaultInstance();
            token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Islevsel.danismanOgretmenSiniflarURL,
                new JSONObject(),
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
                                siniflar.clear();
                                for (int i = 0; i < res.getJSONArray("siniflar").length(); i++) {
                                    JSONObject sinifItem = (JSONObject) res.getJSONArray("siniflar").get(i);
                                    SinifItem tempSinif = new SinifItem(sinifItem.getString("name"),
                                            new ArrayList<OgrenciItem>());

                                    for (int j = 0; j < sinifItem.getJSONArray("ogrenciler").length(); j++) {
                                        JSONObject ogrenciItem = (JSONObject) sinifItem.getJSONArray("ogrenciler").get(j);
                                        tempSinif.getOgrenciler().add(new OgrenciItem(ogrenciItem.getString("name"), ogrenciItem.getInt("id")));
                                    }
                                    siniflar.add(tempSinif);
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
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void parseJSON_1() {

        refreshLayout.setRefreshing(true);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        try {
            Realm realm = Realm.getDefaultInstance();
            token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Islevsel.danismanOgretmenSiniflarOdevOnayURL,
                new JSONObject(),
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
                                siniflar.clear();
                                for (int i = 0; i < res.getJSONArray("siniflar").length(); i++) {
                                    JSONObject sinifItem = (JSONObject) res.getJSONArray("siniflar").get(i);
                                    SinifItem tempSinif = new SinifItem(sinifItem.getString("name"),
                                            new ArrayList<OgrenciItem>());

                                    for (int j = 0; j < sinifItem.getJSONArray("ogrenciler").length(); j++) {
                                        JSONObject ogrenciItem = (JSONObject) sinifItem.getJSONArray("ogrenciler").get(j);
                                        OgrenciItem ogrenciItem1 = new OgrenciItem(ogrenciItem.getString("name"), ogrenciItem.getInt("id"));
                                        ogrenciItem1.setOnaylanacakOdev(ogrenciItem.getInt("onaylanacakOdev"));
                                        tempSinif.getOgrenciler().add(ogrenciItem1);
                                    }
                                    siniflar.add(tempSinif);
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
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    public void isEmpty() {
        if (siniflar.isEmpty()) {
            yokView.setVisibility(View.VISIBLE);
        } else
            yokView.setVisibility(View.GONE);
    }
    @Override
    public void onRefresh() {
        if (durum == 0) parseJSON_0();
        else parseJSON_1();
    }

}
