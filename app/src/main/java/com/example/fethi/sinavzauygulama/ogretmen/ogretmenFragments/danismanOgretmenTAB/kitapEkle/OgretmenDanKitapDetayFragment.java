package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.kitapEkle;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
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
import com.example.fethi.sinavzauygulama.activities.OgretmenListItemFiltre;
import com.example.fethi.sinavzauygulama.activities.OgretmenListItemFiltreAdapter;
import com.example.fethi.sinavzauygulama.activities.UserInfoItem;
import com.example.fethi.sinavzauygulama.diger.Islevsel;
import com.example.fethi.sinavzauygulama.diger.RecyclerTouchListener;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.KitapEkleDersItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.KitapEkleKitapItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgrenciItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgretmenExpLVAdapterKitapDetay;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.SinifItem;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenDanKitapDetayFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View back;
    CardView search_ekrani;
    Button iv_done, iv_copkutusu, btn_filtrele;

    RecyclerView rv_tum_yayinevleri;
    OgretmenListItemFiltreAdapter adapter;

    EditText et_search;
    TextView tv_secim;
    FrameLayout arkaplan;

    ViewGroup transitionsContainer;
    boolean visible;

    ArrayList<OgretmenListItemFiltre> yayinevleri = new ArrayList<>();
    ArrayList<OgretmenListItemFiltre> secili_yayineviList = new ArrayList<>();
    private ArrayList<KitapEkleDersItem> derslerFiltreli = new ArrayList<>();
    private ArrayList<KitapEkleDersItem> derslerFiltresiz = new ArrayList<>();

    public OgretmenExpLVAdapterKitapDetay expand_adapter;
    private FloatingActionButton fab_kitapSec;

    public ArrayList<SinifItem> seciliSiniflar = new ArrayList<>();

    SwipeRefreshLayout refreshLayout;
    JSONObject res;
    String token;
    LinearLayout ortakKitapYokView;

    ExpandableListView expandlist_view_kitapsec;
    JSONArray seciliOgrenciler = new JSONArray();

    KitapEkleDersItem seciliDers;
    ArrayList<Integer> seciliKitapId = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_kitap_detay, container, false);

        back = view.findViewById(R.id.back);
        search_ekrani = view.findViewById(R.id.search_ekrani);
        iv_done = view.findViewById(R.id.iv_done);
        iv_copkutusu = view.findViewById(R.id.iv_copkutusu);
        rv_tum_yayinevleri = view.findViewById(R.id.rv_tum_yayinevleri);
        et_search = view.findViewById(R.id.et_search);
        tv_secim = view.findViewById(R.id.tv_secim);
        arkaplan = view.findViewById(R.id.arkaplan);
        transitionsContainer = arkaplan;


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

                for (KitapEkleDersItem ders : derslerFiltreli) {
                    KitapEkleDersItem tempDers = new KitapEkleDersItem(ders.getDersAdi(), new ArrayList<KitapEkleKitapItem>(), ders.getId());

                    for (KitapEkleKitapItem kitap : ders.getKitaplar()) {
                        if (kitap.isSelected()) {
                            tempDers.getKitaplar().add(
                                    new KitapEkleKitapItem(kitap.getKitapAdi(),
                                            kitap.getYayinAdi(),
                                            kitap.getISBN(),
                                            kitap.getBaski(),
                                            kitap.getIcerdigiDersler(),
                                            kitap.getId(),
                                            kitap.getStatus()
                                    )
                            );
                        }
                    }
                    if (!tempDers.getKitaplar().isEmpty())
                        nextFrag.dersler.add(tempDers);
                }

                if (nextFrag.dersler.size() == 0) {
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

        expand_adapter = new OgretmenExpLVAdapterKitapDetay(getActivity(), derslerFiltreli);
        expandlist_view_kitapsec.setAdapter(expand_adapter);
        expandlist_view_kitapsec.setClickable(true);

        expandlist_view_kitapsec.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                seciliDers = derslerFiltresiz.get(groupPosition);

                if (seciliDers.getKitaplar().isEmpty()) {
                    parseJSONafterClick();
                }

                return false;
            }
        });

        //////////

        rv_tum_yayinevleri.setLayoutManager(new LinearLayoutManager(getContext()));

        btn_filtrele = view.findViewById(R.id.iv_filtrele);
        btn_filtrele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (KitapEkleDersItem dersItem : derslerFiltresiz) {
                    for (KitapEkleKitapItem kitapItem : dersItem.getKitaplar()) {
                        if (kitapItem.isSelected()) {
                            seciliKitapId.add(kitapItem.getId());
                        }
                    }
                }

                Animation animShow = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_show);
                search_ekrani.startAnimation(animShow);
                search_ekrani.setVisibility(View.VISIBLE);

                back.setVisibility(View.VISIBLE);
                fab_kitapSec.setVisibility(View.GONE);

                adapter = new OgretmenListItemFiltreAdapter(getApplicationContext(), yayinevleri);
                if (secili_yayineviList.size() != 0)
                    tv_secim.setText(secili_yayineviList.size() + " Seçili");
                else
                    tv_secim.setText("Seçim Yok");
                rv_tum_yayinevleri.setAdapter(adapter);

            }
        });

        iv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secili_yayineviList.clear();
                for (OgretmenListItemFiltre filtre : yayinevleri) {
                    if (filtre.isSelected()) {
                        secili_yayineviList.add(filtre);
                    }
                }
                System.out.println(derslerFiltresiz.get(0).getDersAdi());
                System.out.println(derslerFiltresiz.get(0).getKitaplar().size());
                System.out.println(derslerFiltreli.get(0).getDersAdi());
                System.out.println(derslerFiltreli.get(0).getKitaplar().size());
                for (KitapEkleDersItem dersItem : derslerFiltresiz) {
                    for (KitapEkleDersItem dersItem1 : derslerFiltreli) {
                        if (dersItem1.getId() == dersItem.getId()) {
                            dersItem1.setKitaplar(kitaplariFiltrele(dersItem.getKitaplar()));
                        }
                    }
                }
                System.out.println(derslerFiltresiz.get(0).getDersAdi());
                System.out.println(derslerFiltresiz.get(0).getKitaplar().size());
                System.out.println(derslerFiltreli.get(0).getDersAdi());
                System.out.println(derslerFiltreli.get(0).getKitaplar().size());
                expand_adapter.notifyDataSetChanged();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Animation animHide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_hide);
                        search_ekrani.startAnimation(animHide);
                        search_ekrani.setVisibility(View.GONE);
                        back.setVisibility(View.GONE);
                        fab_kitapSec.setVisibility(View.VISIBLE);
                        et_search.setText("");
                    }
                }, 300);
            }
        });
        iv_copkutusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (OgretmenListItemFiltre bolum : yayinevleri) {
                    bolum.setSelected(false);
                }

                secili_yayineviList.clear();
                tv_secim.setText("Seçim Yok");

                rv_tum_yayinevleri.setAdapter(adapter);
            }
        });
        rv_tum_yayinevleri.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv_tum_yayinevleri, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int i) {

                ImageView add_img = view.findViewById(R.id.add_img);
                ImageView check_img = view.findViewById(R.id.check_img);

                yayinevleri.get(i).changeSelected();
                if (yayinevleri.get(i).isSelected()) {

                    ViewGroup transitionsContainer = view.findViewById(R.id.transitions_container);
                    TransitionSet set = new TransitionSet()
                            .addTransition(new Scale(0.7f))
                            .addTransition(new Fade())
                            .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                                    new FastOutLinearInInterpolator());

                    set.setDuration(150);
                    TransitionManager.beginDelayedTransition(transitionsContainer, set);
                    add_img.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                    check_img.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);

                    secili_yayineviList.add(yayinevleri.get(i));
                    tv_secim.setText(secili_yayineviList.size() + " Seçili");
                } else {

                    ViewGroup transitionsContainer = view.findViewById(R.id.transitions_container);
                    TransitionSet set = new TransitionSet()
                            .addTransition(new Scale(0.7f))
                            .addTransition(new Fade())
                            .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                                    new FastOutLinearInInterpolator());

                    set.setDuration(150);
                    TransitionManager.beginDelayedTransition(transitionsContainer, set);
                    check_img.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                    add_img.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);

                    secili_yayineviList.remove(secili_yayineviList.indexOf(yayinevleri.get(i)));
                    if (secili_yayineviList.size() != 0)
                        tv_secim.setText(secili_yayineviList.size() + " Seçili");
                    else
                        tv_secim.setText("Seçim Yok");
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        refreshLayout = view.findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.primaryPurple),
                getResources().getColor(R.color.primaryOrange),
                getResources().getColor(R.color.lightgreen));


        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                parseJSON();
            }
        });

        return view;
    }

    private void filter(String text) {

        ArrayList<OgretmenListItemFiltre> filteredList = new ArrayList<>();

        for (OgretmenListItemFiltre s : yayinevleri) {
            if (s.getName().toLowerCase().contains(text.toLowerCase()))
                filteredList.add(s);

        }
        adapter.filterList(filteredList);
    }

    //////////

    public ArrayList<KitapEkleKitapItem> kitaplariFiltrele(ArrayList<KitapEkleKitapItem> kitapItems) {
        if (secili_yayineviList.isEmpty()) {
            return kitapItems;
        }

        ArrayList<KitapEkleKitapItem> filteredKitaps = new ArrayList<>();
        for (KitapEkleKitapItem k : kitapItems) {
            if (seciliKitapId.contains(k.getId())) {
                filteredKitaps.add(k);
            } else {
                for (OgretmenListItemFiltre o : secili_yayineviList) {
                    if (o.getName().equals(k.getYayinAdi()))
                        filteredKitaps.add(k);
                }
            }
        }
        return filteredKitaps;
    }

    public void parseJSON() {

        for (int i = 0; i < expand_adapter.getGroupCount(); i++)
            expandlist_view_kitapsec.collapseGroup(i);

        refreshLayout.setRefreshing(true);

        final JSONObject parameters = new JSONObject();

        for (SinifItem sinif : seciliSiniflar) {
            for (OgrenciItem ogrenci : sinif.getOgrenciler()) {
                if (ogrenci.getSelected())
                    seciliOgrenciler.put(ogrenci.getId());
            }
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
                Islevsel.danismanOgretmenKitaplarURL,
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
                                derslerFiltresiz.clear();
                                derslerFiltreli.clear();
                                yayinevleri.clear();
                                secili_yayineviList.clear();

                                for (int i = 0; i < res.getJSONArray("dersler").length(); i++) {
                                    JSONObject dersItem = (JSONObject) res.getJSONArray("dersler").get(i);
                                    derslerFiltresiz.add(new KitapEkleDersItem(dersItem.getString("name"), new ArrayList<KitapEkleKitapItem>(), dersItem.getInt("dersId")));
                                    derslerFiltreli.add(new KitapEkleDersItem(dersItem.getString("name"), new ArrayList<KitapEkleKitapItem>(), dersItem.getInt("dersId")));
                                }
                                for (int i = 0; i < res.getJSONArray("yayinevleri").length(); i++) {
                                    String yayineviName = (String) res.getJSONArray("yayinevleri").get(i);
                                    yayinevleri.add(new OgretmenListItemFiltre(yayineviName));

                                }
                            }
                            expand_adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        refreshLayout.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                        toast.show();
                        Log.e("ERROR : ", error.toString());
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

    public void parseJSONafterClick() {

        final JSONObject parameters = new JSONObject();

        for (SinifItem sinif : seciliSiniflar) {
            for (OgrenciItem ogrenci : sinif.getOgrenciler()) {
                if (ogrenci.getSelected())
                    seciliOgrenciler.put(ogrenci.getId());
            }
        }

        try {
            parameters.accumulate("ogrenciler", seciliOgrenciler);
            parameters.accumulate("kitapId", seciliDers.getId());
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
                Islevsel.dersKitaplarURL,
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

                                ArrayList<KitapEkleKitapItem> kitapList = new ArrayList<>();
                                for (int j = 0; j < res.getJSONArray("kitaplar").length(); j++) {
                                    JSONObject kitapItem = (JSONObject) res.getJSONArray("kitaplar").get(j);
                                    KitapEkleKitapItem tempKitap = new KitapEkleKitapItem(kitapItem.getString("name"),
                                            kitapItem.getString("yayinEvi"),
                                            kitapItem.getString("isbn"),
                                            kitapItem.getString("baski"),
                                            kitapItem.getString("icerdigiDersler"),
                                            kitapItem.getInt("id"),
                                            kitapItem.getInt("status"));
                                    kitapList.add(tempKitap);

                                }
                                for (KitapEkleDersItem dersItem : derslerFiltresiz) {
                                    if (seciliDers.getId() == dersItem.getId()) {
                                        dersItem.setKitaplar(kitapList);
                                    }
                                }
                                for (KitapEkleDersItem dersItem : derslerFiltreli) {
                                    if (seciliDers.getId() == dersItem.getId()) {
                                        dersItem.setKitaplar(kitaplariFiltrele(kitapList));
                                    }
                                }

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
