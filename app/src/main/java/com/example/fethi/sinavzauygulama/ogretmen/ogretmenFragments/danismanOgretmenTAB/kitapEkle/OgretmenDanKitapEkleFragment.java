package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.kitapEkle;

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
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgretmenExpLVAdapterOdevlendir;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.SinifItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenDanKitapEkleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public OgretmenExpLVAdapterOdevlendir expand_adapter;
    public ExpandableListView expand_lv_odevlendir;
    private FloatingActionButton fab_odevlendir;
    private ArrayList<SinifItem> siniflar = new ArrayList<>();

    SwipeRefreshLayout refreshLayout;
    JSONObject res;
    String token;
    Realm realm = Realm.getDefaultInstance();

    LinearLayout kitapYokView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_odevlendir, container, false);

        kitapYokView = view.findViewById(R.id.kitapYokView);

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

        TextView title = view.findViewById(R.id.title);
        title.setText("Kitap Ekle");

        expand_lv_odevlendir = view.findViewById(R.id.expand_lv_odevlendir);
        fab_odevlendir = view.findViewById(R.id.fab_odevlendir);

        fab_odevlendir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OgretmenDanKitapDetayFragment nextFrag = new OgretmenDanKitapDetayFragment();

                for (SinifItem sinif : siniflar) {

                    SinifItem tempSinif = new SinifItem(sinif.getSinifAdi(), new ArrayList<OgrenciItem>());
                    tempSinif.setOgrenciSayisi(sinif.getOgrenciler().size());
                    for (OgrenciItem ogrenci : sinif.getOgrenciler()) {
                        if (ogrenci.getSelected()) {
                            tempSinif.getOgrenciler().add(ogrenci);
                        }
                    }
                    if (tempSinif.getOgrenciler().size() != 0) {
                        nextFrag.seciliSiniflar.add(tempSinif);
                    }
                }

                if (nextFrag.seciliSiniflar.size() == 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Lütfen öğrenci seçiniz", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 300);
                    toast.show();
                    return;
                }

                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag,"findthisfrag")
                        .addToBackStack(null)
                        .commit();
            }
        });

        expand_adapter = new OgretmenExpLVAdapterOdevlendir(getActivity(), siniflar);
        expand_lv_odevlendir.setAdapter(expand_adapter);

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
                    parseJSON();
                }
            });



        return view;
    }

    public void parseJSON() {

        refreshLayout.setRefreshing(true);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        token = realm.where(UserInfoItem.class).findAll().get(0).getToken();

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
    }

    @Override
    public void onRefresh() {
        parseJSON();
    }

    public void isEmpty() {
        if (siniflar.isEmpty()) {
            kitapYokView.setVisibility(View.VISIBLE);
        } else
            kitapYokView.setVisibility(View.GONE);
    }

    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}