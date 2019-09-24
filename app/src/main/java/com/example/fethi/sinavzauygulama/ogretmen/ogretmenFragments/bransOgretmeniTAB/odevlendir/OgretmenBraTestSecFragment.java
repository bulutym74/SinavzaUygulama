package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.bransOgretmeniTAB.odevlendir;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fethi.sinavzauygulama.activities.UserInfoItem;
import com.example.fethi.sinavzauygulama.diger.Islevsel;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OdevSecKitapItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OdevSecKonuItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OdevSecTestItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgrenciItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgretmenExpLVAdapterTestSec;
import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.SinifItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenBraTestSecFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public OdevSecKitapItem seciliKitap;
    public OgretmenExpLVAdapterTestSec expand_adapter;

    public ExpandableListView expand_lv_testsec;

    private TextView title;
    public TextView secili_soru;

    public ArrayList<SinifItem> seciliSiniflar;

    SwipeRefreshLayout refreshLayout;
    JSONObject res;
    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_testsec, container, false);

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
        secili_soru = view.findViewById(R.id.secili_soru);

        expand_lv_testsec = view.findViewById(R.id.expand_lv_testSec);
        title = view.findViewById(R.id.title);
        title.setText(seciliKitap.getKitapAdi());

        expand_adapter = new OgretmenExpLVAdapterTestSec(getActivity(), seciliKitap.getKonular(), this,0);
        expand_lv_testsec.setAdapter(expand_adapter);
        expand_adapter.updateSoru();

        refreshLayout = view.findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.primaryPurple),
                getResources().getColor(R.color.primaryOrange),
                getResources().getColor(R.color.lightgreen));

        if (seciliKitap.getKonular().size() == 0)
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

        JSONArray seciliOgrenciler = new JSONArray();
        for (SinifItem sinif : seciliSiniflar) {
            for (OgrenciItem ogrenci : sinif.getOgrenciler())
                seciliOgrenciler.put(ogrenci.getId());
        }

        try {
            parameters.accumulate("ogrenciler", seciliOgrenciler);
            parameters.accumulate("kitapId", seciliKitap.getId());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        try {
            Realm realm = Realm.getDefaultInstance();
            token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                Islevsel.kesisenKitapKonularURL,
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
                                seciliKitap.getKonular().clear();

                                for (int k = 0; k < res.getJSONArray("konular").length(); k++) {

                                    JSONObject konu = res.getJSONArray("konular").getJSONObject(k);
                                    OdevSecKonuItem konuItem = new OdevSecKonuItem(konu.getString("name"), new ArrayList<OdevSecTestItem>());

                                    for (int m = 0; m < konu.getJSONArray("testler").length(); m++) {
                                        JSONObject test = konu.getJSONArray("testler").getJSONObject(m);
                                        OdevSecTestItem testItem = new OdevSecTestItem(test.getString("name"), test.getInt("soru"), test.getInt("id"), test.getInt("status"));

                                        konuItem.getTestler().add(testItem);
                                    }
                                    seciliKitap.getKonular().add(konuItem);
                                }
                            }

                            //isEmpty();
                            expand_adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            //isEmpty();
                            e.printStackTrace();
                        }
                        expand_adapter.updateSoru();
                        refreshLayout.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //isEmpty();

                        Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                        toast.show();
                        refreshLayout.setRefreshing(false);
                        expand_adapter.updateSoru();

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

    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    @Override
    public void onRefresh() {
        parseJSON();
    }
}
