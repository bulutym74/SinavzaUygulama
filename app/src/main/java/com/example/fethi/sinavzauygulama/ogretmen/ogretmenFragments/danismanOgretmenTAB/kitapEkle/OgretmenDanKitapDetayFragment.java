package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.kitapEkle;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.KitapEkleDersItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.KitapEkleKitapItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgretmenExpLVAdapterKitapDetay;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.SinifItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenDanKitapDetayFragment extends Fragment {

    public ArrayList<KitapEkleDersItem> dersler = new ArrayList<>();
    public OgretmenExpLVAdapterKitapDetay expand_adapter;
    private FloatingActionButton fab_kitapSec;

    public ArrayList<SinifItem> seciliSiniflar = new ArrayList<>();

    JSONObject res;
    String token;
    Realm realm = Realm.getDefaultInstance();
    LinearLayout ortakKitapYokView;

    ExpandableListView expandlist_view_kitapsec;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_kitap_detay, container, false);

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

        expandlist_view_kitapsec = view.findViewById(R.id.exp_lv_kitapSec);

        fab_kitapSec = view.findViewById(R.id.fab_kitapSec);
        fab_kitapSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OgretmenDanKitapDetayOzetFragment nextFrag = new OgretmenDanKitapDetayOzetFragment();
                nextFrag.siniflar = seciliSiniflar;

                for (KitapEkleDersItem ders : dersler) {
                    KitapEkleDersItem tempDers = new KitapEkleDersItem(ders.getDersAdi(), new ArrayList<KitapEkleKitapItem>());

                    for (KitapEkleKitapItem kitap : ders.getKitaplar()) {
                        if (kitap.isSelected()) {
                            tempDers.getKitaplar().add(
                                    new KitapEkleKitapItem(kitap.getKitapAdi(),
                                            kitap.getYayinAdi(),
                                            kitap.getISBN(),
                                            kitap.getBaski(),
                                            kitap.getIcerdigiDersler(),
                                            kitap.getId()
                                    )
                            );
                        }
                    }
                    if (!tempDers.getKitaplar().isEmpty())
                        nextFrag.dersler.add(tempDers);
                }

                if (nextFrag.dersler.size() == 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Lütfen kitap seçiniz", Toast.LENGTH_SHORT);
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

        expand_adapter = new OgretmenExpLVAdapterKitapDetay(getActivity(), dersler);
        expandlist_view_kitapsec.setAdapter(expand_adapter);

        if (dersler.size() == 0)parseJSON();

        return view;
    }

    public void parseJSON() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        token = realm.where(UserInfoItem.class).findAll().get(0).getToken();

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Islevsel.danismanOgretmenKitaplarURL,
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
                                dersler.clear();
                                for (int i = 0; i < res.getJSONArray("dersler").length(); i++) {
                                    JSONObject dersItem = (JSONObject) res.getJSONArray("dersler").get(i);
                                    KitapEkleDersItem tempDers = new KitapEkleDersItem(dersItem.getString("name"), new ArrayList<KitapEkleKitapItem>());

                                    for (int j = 0; j < dersItem.getJSONArray("kitaplar").length(); j++) {
                                        JSONObject kitapItem = (JSONObject) dersItem.getJSONArray("kitaplar").get(j);
                                        KitapEkleKitapItem tempKitap = new KitapEkleKitapItem(kitapItem.getString("name"),
                                                kitapItem.getString("yayinEvi"),
                                                kitapItem.getString("isbn"),
                                                kitapItem.getString("baski"),
                                                kitapItem.getString("icerdigiDersler"),
                                                kitapItem.getInt("id"));

                                        tempDers.getKitaplar().add(tempKitap);
                                    }
                                    dersler.add(tempDers);
                                }
                            }
                            expand_adapter.notifyDataSetChanged();
                            isEmpty();
                        } catch (JSONException e) {
                            isEmpty();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        isEmpty();

                        Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                        toast.show();
                        Log.e("ERROR : ", error.toString());
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
        if (dersler.size() == 0) {
            ortakKitapYokView.setVisibility(View.VISIBLE);
        } else {
            ortakKitapYokView.setVisibility(View.GONE);
            Log.e("dersler", dersler.toString());
        }
    }

    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
