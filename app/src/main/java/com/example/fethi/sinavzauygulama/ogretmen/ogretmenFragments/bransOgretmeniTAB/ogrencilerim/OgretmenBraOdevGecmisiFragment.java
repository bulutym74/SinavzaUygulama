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
import android.widget.TextView;
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
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.DersItem;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.KitapItem;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.KonuItem;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.TestItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgrenciItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgretmenExpLVAdapterOdevGecmisi;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.danismanOgrencilerim.OgretmenDanOdevGecmisiKonularFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenBraOdevGecmisiFragment  extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ArrayList<DersItem> dersler = new ArrayList<>();

    public OgretmenExpLVAdapterOdevGecmisi expand_adapter;

    SwipeRefreshLayout refreshLayout;
    JSONObject res;
    String token;

    LinearLayout odevYokView;
    public OgrenciItem seciliOgrenci;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_odevler, container, false);

        TextView title = view.findViewById(R.id.title);
        title.setText("Ödev Geçmişi");

        TextView txt_yok = view.findViewById(R.id.txt_yok);
        txt_yok.setText("Ödev geçmişi bulunmamaktadır");

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

        ExpandableListView expandlist_view_odevler = view.findViewById(R.id.expand_lv_odevler);

        odevYokView = view.findViewById(R.id.odevYokView);

        expand_adapter = new OgretmenExpLVAdapterOdevGecmisi(getActivity(), dersler);
        expandlist_view_odevler.setAdapter(expand_adapter);
        expandlist_view_odevler.setClickable(true);

        expandlist_view_odevler.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                OgretmenBraOdevGecmisiKonularFragment nextFrag = new OgretmenBraOdevGecmisiKonularFragment();
                nextFrag.seciliKitap = dersler.get(groupPosition).getKitaplar().get(childPosition);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
            }

        });

        refreshLayout = view.findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.primaryPurple),
                getResources().getColor(R.color.primaryOrange),
                getResources().getColor(R.color.lightgreen));

        if (dersler.size()==0)
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

        try {
            Realm realm = Realm.getDefaultInstance();
            token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST,
                Islevsel.verilenOdevlerURL,
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
                                    JSONObject odevDers = (JSONObject) res.getJSONArray("dersler").get(i);
                                    DersItem dersItem = new DersItem(odevDers.getString("name"), new ArrayList<KitapItem>(), odevDers.getInt("id"));

                                    for (int j = 0; j < odevDers.getJSONArray("kitaplar").length(); j++) {

                                        JSONObject odevKitap = (JSONObject) odevDers.getJSONArray("kitaplar").get(j);
                                        KitapItem kitapItem = new KitapItem(odevKitap.getString("name"), new ArrayList<KonuItem>());

                                        for (int k = 0; k < odevKitap.getJSONArray("konular").length(); k++) {

                                            JSONObject odevKonu = (JSONObject) odevKitap.getJSONArray("konular").get(k);
                                            KonuItem konuItem = new KonuItem(odevKonu.getString("name"), new ArrayList<TestItem>());

                                            for (int l = 0; l < odevKonu.getJSONArray("testler").length(); l++) {

                                                JSONObject odevTest = (JSONObject) odevKonu.getJSONArray("testler").get(l);
                                                TestItem testItem = new TestItem(odevTest.getString("name"), odevTest.getInt("status"));

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
                        Log.e("ERROR", error.toString());

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
            odevYokView.setVisibility(View.VISIBLE);
        } else
            odevYokView.setVisibility(View.GONE);
    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }


}
