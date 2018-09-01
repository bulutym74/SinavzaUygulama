package com.example.fethi.sinavzauygulama.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.fethi.sinavzauygulama.diger.Islevsel;
import com.example.fethi.sinavzauygulama.diger.RecyclerTouchListener;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemHorizontalAdapter;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenUyeOlKurumluFragment extends Fragment {

    LinearLayout normal_ekran;
    View back;
    CardView search_ekrani;
    LinearLayout sec_sinifi;
    FloatingActionButton btn_devam;
    private int index = 0;
    Realm realm = Realm.getDefaultInstance();
    Button iv_done, iv_copkutusu;

    AppCompatEditText isim,soyisim,tel,tcNo;

    RecyclerView rv_tum_siniflar;
    OgretmenListItemFiltreAdapter adapter;

    EditText et_search;
    TextView tv_secim;
    FrameLayout arkaplan;
    Toolbar toolbar;
    ImageView log_out;

    ViewGroup transitionsContainer;
    boolean visible;

    ArrayList<OgretmenListItemFiltre> siniflar;
    ArrayList<OgretmenListItemFiltre> secili_sinifList = new ArrayList<>();

    RecyclerView rv_siniflar;
    ListItemHorizontalAdapter horizontalAdapter;

    TextView sayi_sinif;
    Button copkutusu_sinif;
    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment_ogretmen_uyeol_kurumlu, container, false);

        normal_ekran = view.findViewById(R.id.normal_ekran);
        back = view.findViewById(R.id.back);
        search_ekrani = view.findViewById(R.id.search_ekrani);
        btn_devam = view.findViewById(R.id.btn_devam);
        iv_done = view.findViewById(R.id.iv_done);
        iv_copkutusu = view.findViewById(R.id.iv_copkutusu);
        rv_tum_siniflar = view.findViewById(R.id.rv_tum_siniflar);
        et_search = view.findViewById(R.id.et_search);
        tv_secim = view.findViewById(R.id.tv_secim);
        arkaplan = view.findViewById(R.id.arkaplan);
        transitionsContainer = arkaplan;
        toolbar = view.findViewById(R.id.toolbar);
        isim = view.findViewById(R.id.et_isim);
        soyisim = view.findViewById(R.id.et_soyisim);
        tel = view.findViewById(R.id.et_tel);
        tcNo = view.findViewById(R.id.et_tcNo);
        rv_siniflar = view.findViewById(R.id.rv_siniflar);
        sayi_sinif = view.findViewById(R.id.sayi_sinif);
        copkutusu_sinif = view.findViewById(R.id.copkutusu_sinif);

        log_out = view.findViewById(R.id.log_out);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.where(UserInfoItem.class).findAll().deleteAllFromRealm();
                    }
                });

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        normal_ekran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isim.clearFocus();
                soyisim.clearFocus();
                tel.clearFocus();
                tcNo.clearFocus();
                //klavye gizle
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(normal_ekran.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        rv_tum_siniflar.setLayoutManager(new LinearLayoutManager(getContext()));

        sec_sinifi = view.findViewById(R.id.sec_sinifi);
        sec_sinifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animShow = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.view_show);
                search_ekrani.startAnimation(animShow);
                search_ekrani.setVisibility(View.VISIBLE);

                back.setVisibility(View.VISIBLE);
                btn_devam.setVisibility(View.GONE);

                adapter = new OgretmenListItemFiltreAdapter(getApplicationContext(), siniflar);
                if (secili_sinifList.size() != 0)
                    tv_secim.setText(secili_sinifList.size() + " Seçili");
                else
                    tv_secim.setText("Seçim Yok");
                rv_tum_siniflar.setAdapter(adapter);
            }
        });
        iv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animHide = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.view_hide);
                search_ekrani.startAnimation(animHide);
                search_ekrani.setVisibility(View.GONE);

                back.setVisibility(View.GONE);
                btn_devam.setVisibility(View.VISIBLE);

                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

                List<String> data = new ArrayList<>();
                for (OgretmenListItemFiltre sinif : secili_sinifList) {
                    data.add(sinif.getName());
                }
                horizontalAdapter = new ListItemHorizontalAdapter(data, getApplicationContext());

                rv_siniflar.setLayoutManager(horizontalLayoutManager);
                rv_siniflar.setAdapter(horizontalAdapter);
                rv_siniflar.setVisibility(View.VISIBLE);

                if (data.size() != 0) {
                    sayi_sinif.setText("" + data.size());
                    sayi_sinif.setVisibility(View.VISIBLE);
                    copkutusu_sinif.setVisibility(View.VISIBLE);
                } else {
                    sayi_sinif.setVisibility(View.INVISIBLE);
                    copkutusu_sinif.setVisibility(View.INVISIBLE);
                }


            }
        });
        iv_copkutusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (OgretmenListItemFiltre bolum : siniflar) {
                    bolum.setSelected(false);
                }

                secili_sinifList.clear();

                rv_tum_siniflar.setAdapter(adapter);
                tv_secim.setText("Seçim Yok");
            }
        });
        rv_tum_siniflar.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv_tum_siniflar, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int i) {
                ImageView add_img = view.findViewById(R.id.add_img);
                ImageView check_img = view.findViewById(R.id.check_img);

                siniflar.get(i).changeSelected();
                if (siniflar.get(i).isSelected()) {

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

                    secili_sinifList.add(siniflar.get(i));
                    tv_secim.setText(secili_sinifList.size() + " Seçili");
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

                    secili_sinifList.remove(secili_sinifList.indexOf(siniflar.get(i)));
                    if (secili_sinifList.size() != 0)
                        tv_secim.setText(secili_sinifList.size() + " Seçili");
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

        copkutusu_sinif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        copkutusu_sinif.setVisibility(View.INVISIBLE);
                        sayi_sinif.setVisibility(View.INVISIBLE);

                        for (OgretmenListItemFiltre sinif : siniflar) {

                            if (sinif.isSelected()) {
                                sinif.changeSelected();
                                rv_tum_siniflar.setAdapter(adapter);
                            }
                            secili_sinifList.remove(sinif);
                        }
                        tv_secim.setText("Seçim Yok");
                        rv_siniflar.setAdapter(horizontalAdapter);
                        rv_siniflar.setVisibility(View.GONE);
                    }
                }, 200);


            }
        });

        btn_devam = view.findViewById(R.id.btn_devam);
        btn_devam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isim.getText().toString().isEmpty()){
                    isim.setError("İsim giriniz");
                    return;
                }
                if (soyisim.getText().toString().isEmpty()){
                    soyisim.setError("Soyisim giriniz");
                    return;
                }
                if (tcNo.getText().toString().isEmpty()){
                    tcNo.setError("T.C. kimlik no giriniz");
                    return;
                }
                if (tel.getText().toString().isEmpty()){
                    tel.setError("Telefon numarası giriniz");
                    return;
                }

                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Yükleniyor", "Lütfen bekleyin...", true);
                dialog.show();

                final JSONObject parameters = new JSONObject();
                JSONArray siniflarID = new JSONArray();
                for (OgretmenListItemFiltre sinif :  secili_sinifList)
                    siniflarID.put(sinif.getId());

                try {
                    parameters.accumulate("name", isim.getText().toString());
                    parameters.accumulate("lastname", soyisim.getText().toString());
                    parameters.accumulate("mobile", tel.getText().toString());
                    parameters.accumulate("TCNo", tcNo.getText().toString());
                    parameters.accumulate("siniflar", siniflarID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
                    }
                });

                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        Islevsel.ogretmenTamamlaURL,
                        parameters,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {
                                dialog.dismiss();
                                try {
                                    if (response.getBoolean("isFailed")) {
                                        Log.e("FAILED : ", response.getString("message"));
                                    } else {
                                        Log.e("RESPONSE : ", response.toString());

                                        realm.executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                try {
                                                    realm.where(UserInfoItem.class).findAll().get(0).setId(response.getInt("id"));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        Intent intent = new Intent(getActivity(), OnayBekleniyor.class);
                                        intent.putExtra("tur",1);
                                        intent.putExtra("ret",false);
                                        startActivity(intent);
                                    }

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

        return view;
    }

    private void filter(String text) {

        ArrayList<OgretmenListItemFiltre> filteredList = new ArrayList<>();

        for (OgretmenListItemFiltre s : siniflar) {
            if (s.getName().toLowerCase().contains(text.toLowerCase()))
                filteredList.add(s);

        }
        adapter.filterList(filteredList);
    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
