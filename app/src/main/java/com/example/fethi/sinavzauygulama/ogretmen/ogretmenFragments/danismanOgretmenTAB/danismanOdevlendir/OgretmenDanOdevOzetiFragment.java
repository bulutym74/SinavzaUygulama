package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.danismanOdevlendir;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.example.fethi.sinavzauygulama.diger.EqualSpacingItemDecoration;
import com.example.fethi.sinavzauygulama.diger.Islevsel;
import com.example.fethi.sinavzauygulama.diger.RecyclerTouchListener;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.GenelKitapItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.GenelKonuItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OdevSecDersItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OdevSecKitapItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OdevSecKonuItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OdevSecTestItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgrenciItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgretmenListItemOdevlerAdapter;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgretmenListItemPopUpOdevlerAdapter;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgretmenListItemPopUpSiniflarAdapter;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgretmenListItemSiniflarAdapter;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.SinifItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.bransOgretmeniTAB.odevlendir.OgretmenBraKitapSecFragment;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.OgretmenDanismanOgretmenFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenDanOdevOzetiFragment extends Fragment {

    RecyclerView rv_siniflar, rv_odevler, rv_popup_sınıflar, rv_popup_odevler;
    RecyclerView.Adapter adapter;

    public List<SinifItem> seciliSiniflar = new ArrayList<>();
    public List<OdevSecDersItem> seciliDersler = new ArrayList<>();
    JSONArray seciliTestler = new JSONArray();
    ArrayList<OgrenciItem> ayrintiViewOgrenciler;
    ArrayList<GenelKitapItem> ayrintiViewKitaplar;
    ArrayList<GenelKonuItem> genelKonular = new ArrayList<>();

    JSONObject res;
    String token;

    CardView popupSiniflar, popupOdevler;
    FrameLayout odevOzetiBackground;
    LinearLayout normal_ekran;
    View back;
    TextView tv_sinif, tv_sol, tv_sag;
    TextView tv_konu, tv_toplam_test;
    ImageView cancel_siniflar, cancel_odevler;
    FloatingActionButton fab_odevOzeti;
    TextView trh_baslama, trh_bitis;
    ViewGroup transitionsContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_odev_ozeti, container, false);

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

        popupSiniflar = view.findViewById(R.id.popUpSiniflar);
        popupOdevler = view.findViewById(R.id.popUpOdevler);
        odevOzetiBackground = view.findViewById(R.id.odevOzetiBackground);
        transitionsContainer = odevOzetiBackground;
        normal_ekran = view.findViewById(R.id.normal_ekran);
        back = view.findViewById(R.id.back);
        tv_sinif = view.findViewById(R.id.tv_sinif);
        tv_sol = view.findViewById(R.id.tv_sol);
        tv_sag = view.findViewById(R.id.tv_sag);
        tv_konu = view.findViewById(R.id.tv_konu);
        tv_toplam_test = view.findViewById(R.id.tv_toplam_test);
        cancel_siniflar = view.findViewById(R.id.cancel_siniflar);
        cancel_odevler = view.findViewById(R.id.cancel_odevler);
        fab_odevOzeti = view.findViewById(R.id.fab_odevOzeti);
        trh_baslama = view.findViewById(R.id.trh_baslama);
        trh_bitis = view.findViewById(R.id.trh_bitis);

        cancel_siniflar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_odevOzeti.setVisibility(View.VISIBLE);
                back.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);

                Animation animHide = AnimationUtils.loadAnimation(getContext(), R.anim.view_hide);
                popupSiniflar.startAnimation(animHide);
                popupSiniflar.setVisibility(View.GONE);

            }
        });
        cancel_odevler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_odevOzeti.setVisibility(View.VISIBLE);
                back.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);

                Animation animHide = AnimationUtils.loadAnimation(getContext(), R.anim.view_hide);
                popupOdevler.startAnimation(animHide);
                popupOdevler.setVisibility(View.GONE);
            }
        });

        rv_siniflar = view.findViewById(R.id.rv_sınıflar);
        rv_siniflar.setHasFixedSize(true);
        rv_siniflar.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        adapter = new OgretmenListItemSiniflarAdapter(seciliSiniflar, getActivity());
        rv_siniflar.addItemDecoration(new EqualSpacingItemDecoration(16, EqualSpacingItemDecoration.HORIZONTAL));
        rv_siniflar.setAdapter(adapter);

        rv_siniflar.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rv_siniflar, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                ayrintiViewOgrenciler = seciliSiniflar.get(position).getOgrenciler();

                adapter = new OgretmenListItemPopUpSiniflarAdapter(ayrintiViewOgrenciler, getActivity());
                rv_popup_sınıflar.setAdapter(adapter);

                Animation animShow = AnimationUtils.loadAnimation(getContext(), R.anim.view_show);
                popupSiniflar.startAnimation(animShow);
                popupSiniflar.setVisibility(View.VISIBLE);

                fab_odevOzeti.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        toolbar.setVisibility(View.GONE);
                    }
                }, 300);


                tv_sinif.setText("" + seciliSiniflar.get(position).getSinifAdi());
                tv_sol.setText("" + seciliSiniflar.get(position).getOgrenciler().size());
                tv_sag.setText("" + seciliSiniflar.get(position).getOgrenciSayisi());

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        /////////////////

        rv_odevler = view.findViewById(R.id.rv_odevler);
        rv_odevler.setHasFixedSize(true);
        rv_odevler.setLayoutManager(new LinearLayoutManager(getActivity()));

        for (OdevSecDersItem ders : seciliDersler) {
            for (OdevSecKitapItem kitap : ders.getKitaplar()) {
                for (OdevSecKonuItem konu : kitap.getKonular()) {

                    boolean genelKonularContains = false;
                    for (GenelKonuItem genelKonu : genelKonular) {
                        if (genelKonu.getKonuAdi().equals(konu.getKonuAdi())) {
                            genelKonularContains = true;
                            break;
                        }
                    }
                    if (!genelKonularContains) {
                        genelKonular.add(new GenelKonuItem(konu.getKonuAdi()));
                        for (OdevSecTestItem test : konu.getTestler())
                            seciliTestler.put(test.getId());
                    }
                }
            }
        }

        for (OdevSecDersItem ders : seciliDersler) {
            for (OdevSecKitapItem kitap : ders.getKitaplar()) {
                for (OdevSecKonuItem konu : kitap.getKonular()) {
                    for (GenelKonuItem genelKonu : genelKonular) {

                        if (konu.getKonuAdi().equals(genelKonu.getKonuAdi())) {
                            boolean kitapGenelKonuda = false;
                            for (GenelKitapItem genelKitap : genelKonu.getBulunduguKitaplar()) {
                                if (genelKitap.getKitapAdi().equals(kitap.getKitapAdi())) {
                                    kitapGenelKonuda = true;
                                    break;
                                }
                            }
                            if (!kitapGenelKonuda) {
                                genelKonu.getBulunduguKitaplar().add(new GenelKitapItem(kitap.getKitapAdi(), konu.getTestler().size()));
                            }
                        }
                    }
                }
            }
        }


        adapter = new OgretmenListItemOdevlerAdapter(genelKonular, getActivity());
        rv_odevler.setAdapter(adapter);

        rv_odevler.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rv_odevler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ayrintiViewKitaplar = genelKonular.get(position).getBulunduguKitaplar();

                adapter = new OgretmenListItemPopUpOdevlerAdapter(ayrintiViewKitaplar, getActivity());
                rv_popup_odevler.setAdapter(adapter);

                Animation animShow = AnimationUtils.loadAnimation(getContext(), R.anim.view_show);
                popupOdevler.startAnimation(animShow);
                popupOdevler.setVisibility(View.VISIBLE);

                fab_odevOzeti.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        toolbar.setVisibility(View.GONE);
                    }
                }, 300);

                tv_konu.setText(genelKonular.get(position).getKonuAdi());
                int testSayisi = 0;
                for (GenelKitapItem kitap : genelKonular.get(position).getBulunduguKitaplar())
                    testSayisi += kitap.getTestSayisi();

                tv_toplam_test.setText("" + testSayisi);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //////////////////
        rv_popup_odevler = view.findViewById(R.id.rv_popup_odevler);
        rv_popup_odevler.setHasFixedSize(true);
        rv_popup_odevler.setLayoutManager(new LinearLayoutManager(getActivity()));
        //////////////////
        rv_popup_sınıflar = view.findViewById(R.id.rv_popup_sınıflar);
        rv_popup_sınıflar.setHasFixedSize(true);
        rv_popup_sınıflar.setLayoutManager(new LinearLayoutManager(getActivity()));
        //////////////////

        Bundle bundle = getArguments();
        if (bundle != null) {

            String baslama = bundle.getString("baslama");
            String bitis = bundle.getString("bitis");

            trh_baslama.setText(baslama);
            trh_bitis.setText(bitis);
        }

        fab_odevOzeti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Yükleniyor", "Lütfen bekleyin...", true);
                dialog.show();

                JSONArray seciliOgrenciler = new JSONArray();
                for (SinifItem sinif : seciliSiniflar) {
                    for (OgrenciItem ogrenci : sinif.getOgrenciler()) {
                        seciliOgrenciler.put(ogrenci.getId());
                    }
                }

                final JSONObject parameters = new JSONObject();

                try {
                    parameters.accumulate("ogrenciler", seciliOgrenciler);
                    parameters.accumulate("testler", seciliTestler);
                    parameters.accumulate("baslangicTarihi", trh_baslama.getText().toString());
                    parameters.accumulate("bitisTarihi", trh_bitis.getText().toString());
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
                        Islevsel.odevVerURL,
                        parameters,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {
                                dialog.dismiss();
                                try {
                                    if (response.getBoolean("isFailed")) {
                                        Log.e("FAILED : ", response.getString("message"));

                                        Toast toast = Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                                        toast.show();
                                    } else {
                                        res = response;

                                        Toast toast = Toast.makeText(getApplicationContext(), "Ödevler başarıyla gönderildi", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                                        toast.show();

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                OgretmenDanismanOgretmenFragment nextFrag = new OgretmenDanismanOgretmenFragment();
                                                getActivity().getSupportFragmentManager().beginTransaction()
                                                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                                                        .addToBackStack(null)
                                                        .commit();
                                            }
                                        }, 700);


                                    }
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("ERROR : ", error.toString());

                                dialog.dismiss();
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
                objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        60000,
                        3,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            }
        });
        return view;
    }

    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
