package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.bransOgretmeniTAB.ogrencilerim;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.GecikenDersItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.GecikenKonuItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgrenciItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgretmenExpLVAdapterGecikenOdevler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenBraGecikenOdevlerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ArrayList<GecikenDersItem> dersler = new ArrayList<>();

    public OgretmenExpLVAdapterGecikenOdevler expand_adapter;

    public ExpandableListView exp_lv_geciken_odevler;

    LinearLayout yokView;

    SwipeRefreshLayout refreshLayout;
    JSONObject res;
    String token;
    Realm realm = Realm.getDefaultInstance();
    public OgrenciItem seciliOgrenci;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_geciken_odevler, container, false);

        yokView = view.findViewById(R.id.yokView);

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

        exp_lv_geciken_odevler = view.findViewById(R.id.exp_lv_geciken_odevler);

        expand_adapter = new OgretmenExpLVAdapterGecikenOdevler(getActivity(), dersler);
        exp_lv_geciken_odevler.setAdapter(expand_adapter);
        exp_lv_geciken_odevler.setClickable(false);

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

    public void parseJSON() {

        refreshLayout.setRefreshing(true);

        final JSONObject parameters = new JSONObject();

        try {
            parameters.accumulate("ogrenciId", seciliOgrenci.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        token = realm.where(UserInfoItem.class).findAll().get(0).getToken();

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                Islevsel.danismanOgrenciGecikenlerURL,
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
                                    JSONObject gecikenDers = (JSONObject) res.getJSONArray("dersler").get(i);
                                    GecikenDersItem dersItem = new GecikenDersItem(gecikenDers.getString("name"), new ArrayList<GecikenKonuItem>());

                                    for (int j = 0; j < gecikenDers.getJSONArray("konular").length(); j++) {

                                        JSONObject gecikenKonu = (JSONObject) gecikenDers.getJSONArray("konular").get(j);
                                        GecikenKonuItem konuItem = new GecikenKonuItem(gecikenKonu.getString("name"), gecikenKonu.getInt("soru"));

                                        dersItem.setSoruSayisi(dersItem.getSoruSayisi() + konuItem.getSoruSayisi());
                                        dersItem.getKonular().add(konuItem);
                                    }
                                    dersler.add(dersItem);
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

                        Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                        toast.show();
                        isEmpty();
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

    @Override
    public void onRefresh() {
        parseJSON();
    }

    public void isEmpty() {
        if (dersler.isEmpty()) {
            yokView.setVisibility(View.VISIBLE);
        } else
            yokView.setVisibility(View.GONE);
    }

    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

}
