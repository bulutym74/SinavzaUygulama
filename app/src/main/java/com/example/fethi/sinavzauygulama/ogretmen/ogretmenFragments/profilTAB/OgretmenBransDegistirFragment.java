package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.profilTAB;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
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
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemSinifDegistirAdapter;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.BransAdapter;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.BransItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgretmenExpLVAdapterSiniflar;
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

public class OgretmenBransDegistirFragment extends Fragment {

    View back;
    CardView search_ekrani, search_talepView;
    Button iv_done, iv_copkutusu;

    RecyclerView rv_tum_branslar;
    OgretmenListItemFiltreAdapter adapter;

    EditText et_search;
    TextView tv_secim;
    FrameLayout arkaplan;

    ViewGroup transitionsContainer;
    boolean visible;

    ArrayList<OgretmenListItemFiltre> branslarFiltre = new ArrayList<>();
    ArrayList<OgretmenListItemFiltre> secili_bransList = new ArrayList<>();

    RecyclerView rv_bransListesi;
    RecyclerView.Adapter brans_adapter;
    ArrayList<BransItem> branslar = new ArrayList<>();

    //LinearLayout yokView;

    JSONObject res;
    String token;

    Button btn_ekle;
    TextView txt_talep;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_brans_degistir, container, false);

        back = view.findViewById(R.id.back);
        search_ekrani = view.findViewById(R.id.search_ekrani);
        search_talepView = view.findViewById(R.id.search_talepView);
        iv_done = view.findViewById(R.id.iv_done);
        iv_copkutusu = view.findViewById(R.id.iv_copkutusu);
        rv_tum_branslar = view.findViewById(R.id.rv_tum_branslar);
        et_search = view.findViewById(R.id.et_search);
        tv_secim = view.findViewById(R.id.tv_secim);
        arkaplan = view.findViewById(R.id.arkaplan);
        transitionsContainer = arkaplan;
        txt_talep = view.findViewById(R.id.txt_talep);
        //yokView = view.findViewById(R.id.yokView);

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

        rv_bransListesi = view.findViewById(R.id.rv_bransListesi);
        rv_bransListesi.setHasFixedSize(true);
        rv_bransListesi.setLayoutManager(new LinearLayoutManager(getActivity()));

        brans_adapter = new BransAdapter(branslar,getActivity());
        rv_bransListesi.setAdapter(brans_adapter);

        //isEmpty();

        ///////

        rv_tum_branslar.setLayoutManager(new LinearLayoutManager(getContext()));

        btn_ekle = view.findViewById(R.id.iv_ekle);
        btn_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                try {
                    Realm realm = Realm.getDefaultInstance();
                    token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        Islevsel.ogretmenSinifEkleURL,
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
                                        branslarFiltre.clear();
                                        secili_bransList.clear();
                                        /*if (response.getBoolean("talep")) {

                                            search_talepView.setVisibility(View.VISIBLE);
                                            txt_talep.setText("Halihazırda talep işleminiz bulunmaktadır");

                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                public void run() {
                                                    Animation animHide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_hide);
                                                    search_talepView.startAnimation(animHide);
                                                    search_talepView.setVisibility(View.GONE);
                                                    back.setVisibility(View.GONE);
                                                }
                                            }, 1200);

                                        } else {*/

                                        for (int i = 0; i < res.getJSONArray("branslar").length(); i++) {
                                            branslarFiltre.add(new OgretmenListItemFiltre(res.getJSONArray("branslar").getJSONObject(i).getString("name"),
                                                    res.getJSONArray("branslar").getJSONObject(i).getInt("id")));
                                        }

                                        Animation animShow = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_show);
                                        search_ekrani.startAnimation(animShow);
                                        search_ekrani.setVisibility(View.VISIBLE);

                                        back.setVisibility(View.VISIBLE);

                                        adapter = new OgretmenListItemFiltreAdapter(getApplicationContext(), branslarFiltre);
                                        if (secili_bransList.size() != 0)
                                            tv_secim.setText(secili_bransList.size() + " Seçili");
                                        else
                                            tv_secim.setText("Seçim Yok");
                                        rv_tum_branslar.setAdapter(adapter);
                                    }

                                    // }

                                    brans_adapter.notifyDataSetChanged();
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
        });

        iv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (secili_bransList.size() == 0) {

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Animation animHide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_hide);
                            search_ekrani.startAnimation(animHide);
                            search_ekrani.setVisibility(View.GONE);
                            back.setVisibility(View.GONE);
                        }
                    }, 1200);

                    return;
                }

                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Yükleniyor", "Lütfen bekleyin...", true);
                dialog.show();

                final JSONObject parameters = new JSONObject();

                JSONArray branslarID = new JSONArray();
                for (OgretmenListItemFiltre seciliBrans : secili_bransList) {
                    branslarID.put(seciliBrans.getId());
                }

                try {
                    parameters.accumulate("branslar", branslarID);
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
                        Islevsel.ogretmenSinifYollaURL,
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

                                        search_ekrani.setVisibility(View.GONE);
                                        search_talepView.setVisibility(View.VISIBLE);

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                Animation animHide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_hide);
                                                search_talepView.startAnimation(animHide);
                                                search_talepView.setVisibility(View.GONE);
                                                back.setVisibility(View.GONE);
                                            }
                                        }, 1200);
                                    }

                                    brans_adapter.notifyDataSetChanged();
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

            }
        });
        iv_copkutusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (OgretmenListItemFiltre brans : branslarFiltre) {
                    brans.setSelected(false);
                }

                secili_bransList.clear();

                rv_tum_branslar.setAdapter(adapter);
            }
        });
        rv_tum_branslar.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv_tum_branslar, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int i) {
                ImageView add_img = view.findViewById(R.id.add_img);
                ImageView check_img = view.findViewById(R.id.check_img);

                branslarFiltre.get(i).changeSelected();
                if (branslarFiltre.get(i).isSelected()) {

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

                    secili_bransList.add(branslarFiltre.get(i));
                    tv_secim.setText(secili_bransList.size() + " Seçili");
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

                    secili_bransList.remove(secili_bransList.indexOf(branslarFiltre.get(i)));
                    if (secili_bransList.size() != 0)
                        tv_secim.setText(secili_bransList.size() + " Seçili");
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


        return view;
    }

    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    private void filter(String text) {

        ArrayList<OgretmenListItemFiltre> filteredList = new ArrayList<>();

        for (OgretmenListItemFiltre s : branslarFiltre) {
            if (s.getName().toLowerCase().contains(text.toLowerCase()))
                filteredList.add(s);

        }
        adapter.filterList(filteredList);
    }

    /*public void isEmpty() {
        if (branslar.isEmpty()) {
            yokView.setVisibility(View.VISIBLE);
        } else
            yokView.setVisibility(View.GONE);
    }*/
}
