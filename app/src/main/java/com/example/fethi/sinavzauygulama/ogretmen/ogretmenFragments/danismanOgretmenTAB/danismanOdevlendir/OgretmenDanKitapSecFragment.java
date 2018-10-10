package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.danismanOdevlendir;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.activities.UserInfoItem;
import com.example.fethi.sinavzauygulama.diger.Islevsel;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OdevSecDersItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OdevSecKitapItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OdevSecKonuItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OdevSecTestItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgrenciItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgretmenExpLVAdapterKitapSec;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.SinifItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenDanKitapSecFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    public ArrayList<OdevSecDersItem> dersler = new ArrayList<>();
    public OgretmenExpLVAdapterKitapSec expand_adapter;
    private FloatingActionButton fab_kitapSec;

    public ArrayList<SinifItem> seciliSiniflar;

    SwipeRefreshLayout refreshLayout;
    JSONObject res;
    String token;

    LinearLayout ortakKitapYokView;
    public TextView secili_soru;
    LinearLayout view_seciliSoru;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_kitapsec, container, false);

        ortakKitapYokView = view.findViewById(R.id.ortakKitapYokView);

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
        view_seciliSoru = view.findViewById(R.id.view_seciliSoru);

        ExpandableListView expandlist_view_kitapsec = view.findViewById(R.id.expand_lv_kitapSec);
        fab_kitapSec = view.findViewById(R.id.fab_kitapSec);
        fab_kitapSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OgretmenDanTarihSecFragment nextFrag = new OgretmenDanTarihSecFragment();
                nextFrag.seciliSiniflar = seciliSiniflar;

                for (OdevSecDersItem ders : dersler) {
                    OdevSecDersItem tempDers = new OdevSecDersItem(ders.getDersAdi(), new ArrayList<OdevSecKitapItem>());

                    for (OdevSecKitapItem kitap : ders.getKitaplar()) {
                        OdevSecKitapItem tempKitap = new OdevSecKitapItem(kitap.getKitapAdi(), new ArrayList<OdevSecKonuItem>());
                        for (OdevSecKonuItem konu : kitap.getKonular()) {
                            OdevSecKonuItem tempKonu = new OdevSecKonuItem(konu.getKonuAdi(), new ArrayList<OdevSecTestItem>());

                            for (OdevSecTestItem test : konu.getTestler()) {
                                if (test.isSelected())
                                    tempKonu.getTestler().add(test);
                            }
                            if (!tempKonu.getTestler().isEmpty())
                                tempKitap.getKonular().add(tempKonu);
                        }
                        if (!tempKitap.getKonular().isEmpty())
                            tempDers.getKitaplar().add(tempKitap);
                    }
                    if (!tempDers.getKitaplar().isEmpty())
                        nextFrag.seciliDersler.add(tempDers);
                }

                if (nextFrag.seciliDersler.size() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Lütfen test seçiniz", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 300);
                    toast.show();
                    return;
                }

                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        int count = 0;
        for (OdevSecDersItem ders : dersler) {
            for (OdevSecKitapItem kitap : ders.getKitaplar()) {
                for (OdevSecKonuItem konu : kitap.getKonular()) {
                    for (OdevSecTestItem test : konu.getTestler()) {
                        if (test.isSelected()) {
                            count += test.getSoruSayisi();
                        }
                    }
                }
            }
        }
        secili_soru.setText("" + count);

        //if (dersler.size() == 0) parseJSON();
        //else view_seciliSoru.setVisibility(View.VISIBLE);

        expand_adapter = new OgretmenExpLVAdapterKitapSec(getActivity(), dersler);
        expandlist_view_kitapsec.setAdapter(expand_adapter);
        expandlist_view_kitapsec.setClickable(true);

        expandlist_view_kitapsec.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                OgretmenDanTestSecFragment nextFrag = new OgretmenDanTestSecFragment();
                nextFrag.seciliKitap = dersler.get(groupPosition).getKitaplar().get(childPosition);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return false;
            }

        });

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
        else{
            view_seciliSoru.setVisibility(View.VISIBLE);
            fab_kitapSec.setVisibility(View.VISIBLE);
        }

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
                Islevsel.danismanKesisenKitaplarURL,
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

                                    JSONObject ders = res.getJSONArray("dersler").getJSONObject(i);
                                    OdevSecDersItem dersItem = new OdevSecDersItem(ders.getString("name"), new ArrayList<OdevSecKitapItem>());

                                    for (int j = 0; j < ders.getJSONArray("kitaplar").length(); j++) {
                                        JSONObject kitap = ders.getJSONArray("kitaplar").getJSONObject(j);
                                        OdevSecKitapItem kitapItem = new OdevSecKitapItem(kitap.getString("name"), new ArrayList<OdevSecKonuItem>());

                                        for (int k = 0; k < kitap.getJSONArray("konular").length(); k++) {
                                            JSONObject konu = kitap.getJSONArray("konular").getJSONObject(k);
                                            OdevSecKonuItem konuItem = new OdevSecKonuItem(konu.getString("name"), new ArrayList<OdevSecTestItem>());

                                            for (int m = 0; m < konu.getJSONArray("testler").length(); m++) {
                                                JSONObject test = konu.getJSONArray("testler").getJSONObject(m);
                                                OdevSecTestItem testItem = new OdevSecTestItem(test.getString("name"), test.getInt("soru"), test.getInt("id"), test.getInt("status"));

                                                konuItem.getTestler().add(testItem);
                                            }
                                            kitapItem.getKonular().add(konuItem);
                                        }
                                        dersItem.getKitaplar().add(kitapItem);
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

    public void isEmpty() {
        if (dersler.isEmpty()) {
            ortakKitapYokView.setVisibility(View.VISIBLE);
            view_seciliSoru.setVisibility(View.GONE);
            fab_kitapSec.setVisibility(View.GONE);
        } else{
            ortakKitapYokView.setVisibility(View.GONE);
            view_seciliSoru.setVisibility(View.VISIBLE);
            fab_kitapSec.setVisibility(View.VISIBLE);
        }

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

