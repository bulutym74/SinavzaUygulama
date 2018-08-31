package com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.tercihYapTAB;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.fethi.sinavzauygulama.diger.RecyclerTouchListener;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemFiltre;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemHorizontalAdapter;
import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemTercihSonucları;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemFiltreAdapter;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.exceptions.RealmException;

public class TercihYapFragment extends Fragment {

    Realm realm = Realm.getDefaultInstance();

    RadioGroup rg;
    RadioButton rb_siralama;
    RadioButton rb_puan;
    EditText e1;
    LinearLayout normal_ekran;
    View back;
    CardView search_ekrani;
    LinearLayout bolum_filtrele, uni_filtrele, sehir_filtrele, uniTur_filtrele, puantur_filtrele;
    FloatingActionButton fab;
    Button iv_done, iv_copkutusu;

    RecyclerView rv_tercihyap;
    ListItemFiltreAdapter adapter = new ListItemFiltreAdapter(getActivity(), getFiltres());

    EditText et_search;
    TextView tv_secim;
    int durum = 0;

    TextView sayi_bolum, sayi_uni, sayi_sehir, sayi_uniTur, sayi_puantur;
    Button copkutusu_bolum, copkutusu_uni, copkutusu_sehir, copkutusu_uniTur, copkutusu_puantur;
    FrameLayout arkaplan;
    Toolbar toolbar;

    RecyclerView recycler_view_bolum;
    RecyclerView recycler_view_uni;
    RecyclerView recycler_view_sehir;
    RecyclerView recycler_view_uniTur;
    RecyclerView recycler_view_puantur;

    ListItemHorizontalAdapter horizontalAdapter;
    List<String> data;

    ViewGroup transitionsContainer;
    boolean visible;

    ArrayList<ListItemFiltre> bolumList = new ArrayList<>();
    ArrayList<ListItemFiltre> uniList = new ArrayList<>();
    ArrayList<ListItemFiltre> sehirList = new ArrayList<>();
    ArrayList<ListItemFiltre> uniTurList = new ArrayList<>();
    ArrayList<ListItemFiltre> puanTurList = new ArrayList<>();

    ArrayList<ListItemFiltre> secili_bolumList = new ArrayList<>();
    ArrayList<ListItemFiltre> secili_uniList = new ArrayList<>();
    ArrayList<ListItemFiltre> secili_sehirList = new ArrayList<>();
    ArrayList<ListItemFiltre> secili_uniTurList = new ArrayList<>();
    ArrayList<ListItemFiltre> secili_puanTurList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tercihyap, container, false);
        rg = view.findViewById(R.id.rg);
        rb_siralama = view.findViewById(R.id.rb_sıralama);
        rb_puan = view.findViewById(R.id.rb_puan);
        e1 = view.findViewById(R.id.e1);
        normal_ekran = view.findViewById(R.id.normal_ekran);
        back = view.findViewById(R.id.back);
        search_ekrani = view.findViewById(R.id.search_ekrani);
        bolum_filtrele = view.findViewById(R.id.tv_bolum_filtrele);
        uni_filtrele = view.findViewById(R.id.tv_uni_filtrele);
        sehir_filtrele = view.findViewById(R.id.tv_sehir_filtrele);
        uniTur_filtrele = view.findViewById(R.id.tv_uniTur_filtrele);
        puantur_filtrele = view.findViewById(R.id.tv_puantur_filtrele);
        fab = view.findViewById(R.id.fab_tercihyap);
        iv_done = view.findViewById(R.id.iv_done);
        iv_copkutusu = view.findViewById(R.id.iv_copkutusu);
        rv_tercihyap = view.findViewById(R.id.rv_tercihyap);
        et_search = view.findViewById(R.id.et_search);
        tv_secim = view.findViewById(R.id.tv_secim);
        recycler_view_bolum = view.findViewById(R.id.recycler_view_bolum);
        recycler_view_uni = view.findViewById(R.id.recycler_view_uni);
        recycler_view_sehir = view.findViewById(R.id.recycler_view_sehir);
        recycler_view_uniTur = view.findViewById(R.id.recycler_view_uniTur);
        recycler_view_puantur = view.findViewById(R.id.recycler_view_puantur);
        sayi_bolum = view.findViewById(R.id.sayi_bolum);
        sayi_uni = view.findViewById(R.id.sayi_uni);
        sayi_sehir = view.findViewById(R.id.sayi_sehir);
        sayi_uniTur = view.findViewById(R.id.sayi_uniTur);
        sayi_puantur = view.findViewById(R.id.sayi_puantur);
        copkutusu_bolum = view.findViewById(R.id.copkutusu_bolum);
        copkutusu_uni = view.findViewById(R.id.copkutusu_uni);
        copkutusu_sehir = view.findViewById(R.id.copkutusu_sehir);
        copkutusu_uniTur = view.findViewById(R.id.copkutusu_uniTur);
        copkutusu_puantur = view.findViewById(R.id.copkutusu_puantur);
        arkaplan = view.findViewById(R.id.arkaplan);
        transitionsContainer = arkaplan;
        toolbar = view.findViewById(R.id.toolbar);


        if (realm.where(ListItemTercihSonucları.class).count() == 0) {

            for (int i = 0; i < 5; i++) {
                final ListItemTercihSonucları t = new ListItemTercihSonucları("Boğaziçi Üniversitesi", "İstanbul", "Bilgisayar Mühendisliği", "Devlet", "MF", 525.563, 5816);
                final ListItemTercihSonucları t1 = new ListItemTercihSonucları("İstanbul Teknik Üniversitesi", "İstanbul", "Endüstri Mühendisliği", "Vakıf", "MF", 453.537, 4556444);

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        try {
                            try {
                                realm.copyToRealm(t);
                                realm.copyToRealm(t1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (RealmException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }
        for (int i = 0; i < 25; i++) {
            bolumList.add(new ListItemFiltre("Bilgisayar Mühendisliği"));
            bolumList.add(new ListItemFiltre("Endüstri Mühendisliği"));
            uniList.add(new ListItemFiltre("Boğaziçi Üniversitesi"));
            uniList.add(new ListItemFiltre("İstanbul Teknik Üniversitesi"));
            sehirList.add(new ListItemFiltre("İstanbul"));
            sehirList.add(new ListItemFiltre("Ankara"));
            uniTurList.add(new ListItemFiltre("Devlet"));
            uniTurList.add(new ListItemFiltre("Vakıf"));
            puanTurList.add(new ListItemFiltre("TM"));
            puanTurList.add(new ListItemFiltre("MF"));
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_sıralama:
                        e1.setHint("Sıralama");
                        break;
                    case R.id.rb_puan:
                        rb_siralama.setChecked(false);
                        e1.setHint("Puan");
                        break;
                }
            }
        });

        rv_tercihyap.setLayoutManager(new LinearLayoutManager(getActivity()));

        bolum_filtrele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animShow = AnimationUtils.loadAnimation( getContext(), R.anim.view_show);
                search_ekrani.startAnimation(animShow);
                search_ekrani.setVisibility(View.VISIBLE);

                back.setVisibility(View.VISIBLE);
                fab.setVisibility(View.GONE);
                et_search.setHint("Bölüm Ara");

                durum = 0;
                adapter = new ListItemFiltreAdapter(getActivity(), getFiltres());
                if (secili_bolumList.size() != 0)
                    tv_secim.setText(secili_bolumList.size() + " Seçili");
                else
                    tv_secim.setText("Seçim Yok");
                rv_tercihyap.setAdapter(adapter);
            }
        });
        uni_filtrele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animShow = AnimationUtils.loadAnimation( getContext(), R.anim.view_show);
                search_ekrani.startAnimation(animShow);
                search_ekrani.setVisibility(View.VISIBLE);

                back.setVisibility(View.VISIBLE);
                fab.setVisibility(View.GONE);
                et_search.setHint("Üniversite Ara");

                durum = 1;
                adapter = new ListItemFiltreAdapter(getActivity(), getFiltres());
                if (secili_uniList.size() != 0)
                    tv_secim.setText(secili_uniList.size() + " Seçili");
                else
                    tv_secim.setText("Seçim Yok");
                rv_tercihyap.setAdapter(adapter);

            }
        });
        sehir_filtrele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animShow = AnimationUtils.loadAnimation( getContext(), R.anim.view_show);
                search_ekrani.startAnimation(animShow);
                search_ekrani.setVisibility(View.VISIBLE);

                back.setVisibility(View.VISIBLE);
                fab.setVisibility(View.GONE);
                et_search.setHint("Şehir Ara");

                durum = 2;
                adapter = new ListItemFiltreAdapter(getActivity(), getFiltres());

                if (secili_sehirList.size() != 0)
                    tv_secim.setText(secili_sehirList.size() + " Seçili");
                else
                    tv_secim.setText("Seçim Yok");
                rv_tercihyap.setAdapter(adapter);
            }
        });
        uniTur_filtrele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animShow = AnimationUtils.loadAnimation( getContext(), R.anim.view_show);
                search_ekrani.startAnimation(animShow);
                search_ekrani.setVisibility(View.VISIBLE);

                back.setVisibility(View.VISIBLE);
                fab.setVisibility(View.GONE);
                et_search.setHint("Üniversite Türü Ara");

                durum = 3;
                adapter = new ListItemFiltreAdapter(getActivity(), getFiltres());

                if (secili_uniTurList.size() != 0)
                    tv_secim.setText(secili_uniTurList.size() + " Seçili");
                else
                    tv_secim.setText("Seçim Yok");
                rv_tercihyap.setAdapter(adapter);
            }
        });
        puantur_filtrele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animShow = AnimationUtils.loadAnimation( getContext(), R.anim.view_show);
                search_ekrani.startAnimation(animShow);
                search_ekrani.setVisibility(View.VISIBLE);

                back.setVisibility(View.VISIBLE);
                fab.setVisibility(View.GONE);
                et_search.setHint("Puan Türü Ara");

                durum = 4;
                adapter = new ListItemFiltreAdapter(getActivity(), getFiltres());

                if (secili_puanTurList.size() != 0)
                    tv_secim.setText(secili_puanTurList.size() + " Seçili");
                else
                    tv_secim.setText("Seçim Yok");
                rv_tercihyap.setAdapter(adapter);
            }
        });
        iv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animHide = AnimationUtils.loadAnimation( getContext(), R.anim.view_hide);
                search_ekrani.startAnimation(animHide);
                search_ekrani.setVisibility(View.GONE);

                back.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);

                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

                data = fill_with_data();
                horizontalAdapter = new ListItemHorizontalAdapter(data, getActivity());

                if (durum == 0) {
                    recycler_view_bolum.setLayoutManager(horizontalLayoutManager);
                    recycler_view_bolum.setAdapter(horizontalAdapter);
                    recycler_view_bolum.setVisibility(View.VISIBLE);

                    if (data.size() != 0) {
                        sayi_bolum.setText("" + data.size());
                        sayi_bolum.setVisibility(View.VISIBLE);
                        copkutusu_bolum.setVisibility(View.VISIBLE);
                    } else {
                        sayi_bolum.setVisibility(View.INVISIBLE);
                        copkutusu_bolum.setVisibility(View.INVISIBLE);
                    }

                } else if (durum == 1) {
                    recycler_view_uni.setLayoutManager(horizontalLayoutManager);
                    recycler_view_uni.setAdapter(horizontalAdapter);
                    recycler_view_uni.setVisibility(View.VISIBLE);

                    if (data.size() != 0) {
                        sayi_uni.setText("" + data.size());
                        sayi_uni.setVisibility(View.VISIBLE);
                        copkutusu_uni.setVisibility(View.VISIBLE);
                    } else {
                        sayi_uni.setVisibility(View.INVISIBLE);
                        copkutusu_uni.setVisibility(View.INVISIBLE);
                    }
                } else if (durum == 2) {
                    recycler_view_sehir.setLayoutManager(horizontalLayoutManager);
                    recycler_view_sehir.setAdapter(horizontalAdapter);
                    recycler_view_sehir.setVisibility(View.VISIBLE);

                    if (data.size() != 0) {
                        sayi_sehir.setText("" + data.size());
                        sayi_sehir.setVisibility(View.VISIBLE);
                        copkutusu_sehir.setVisibility(View.VISIBLE);
                    } else {
                        sayi_sehir.setVisibility(View.INVISIBLE);
                        copkutusu_sehir.setVisibility(View.INVISIBLE);
                    }
                } else if (durum == 3) {
                    recycler_view_uniTur.setLayoutManager(horizontalLayoutManager);
                    recycler_view_uniTur.setAdapter(horizontalAdapter);
                    recycler_view_uniTur.setVisibility(View.VISIBLE);

                    if (data.size() != 0) {
                        sayi_uniTur.setText("" + data.size());
                        sayi_uniTur.setVisibility(View.VISIBLE);
                        copkutusu_uniTur.setVisibility(View.VISIBLE);
                    } else {
                        sayi_uniTur.setVisibility(View.INVISIBLE);
                        copkutusu_uniTur.setVisibility(View.INVISIBLE);
                    }
                } else {
                    recycler_view_puantur.setLayoutManager(horizontalLayoutManager);
                    recycler_view_puantur.setAdapter(horizontalAdapter);
                    recycler_view_puantur.setVisibility(View.VISIBLE);

                    if (data.size() != 0) {
                        sayi_puantur.setText("" + data.size());
                        sayi_puantur.setVisibility(View.VISIBLE);
                        copkutusu_puantur.setVisibility(View.VISIBLE);
                    } else {
                        sayi_puantur.setVisibility(View.INVISIBLE);
                        copkutusu_puantur.setVisibility(View.INVISIBLE);
                    }

                }
            }
        });
        iv_copkutusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (durum == 0) {

                    for (ListItemFiltre bolum : bolumList) {

                        if (bolum.isSelected()) {
                            bolum.changeSelected();
                            rv_tercihyap.setAdapter(adapter);
                        }
                        secili_bolumList.remove(bolum);
                    }
                    tv_secim.setText("Seçim Yok");
                }
                if (durum == 1) {

                    for (ListItemFiltre uni : uniList) {

                        if (uni.isSelected()) {
                            uni.changeSelected();
                            rv_tercihyap.setAdapter(adapter);
                        }
                        secili_uniList.remove(uni);
                    }
                    tv_secim.setText("Seçim Yok");

                }
                if (durum == 2) {

                    for (ListItemFiltre sehir : sehirList) {

                        if (sehir.isSelected()) {
                            sehir.changeSelected();
                            rv_tercihyap.setAdapter(adapter);
                        }
                        secili_sehirList.remove(sehir);
                    }
                    tv_secim.setText("Seçim Yok");

                }
                if (durum == 3) {

                    for (ListItemFiltre unitur : uniTurList) {

                        if (unitur.isSelected()) {
                            unitur.changeSelected();
                            rv_tercihyap.setAdapter(adapter);
                        }
                        secili_uniTurList.remove(unitur);
                    }
                    tv_secim.setText("Seçim Yok");
                }
                if (durum == 4) {

                    for (ListItemFiltre puantur : puanTurList) {

                        if (puantur.isSelected()) {
                            puantur.changeSelected();
                            rv_tercihyap.setAdapter(adapter);
                        }
                        secili_puanTurList.remove(puantur);
                    }
                    tv_secim.setText("Seçim Yok");

                }

            }
        });

        rv_tercihyap.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rv_tercihyap, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int i) {
                ImageView add_img = view.findViewById(R.id.add_img);
                ImageView check_img = view.findViewById(R.id.check_img);

                ArrayList<ListItemFiltre> temp;
                ArrayList<ListItemFiltre> seciliTemp;

                if (durum == 0) {
                    temp = bolumList;
                    seciliTemp = secili_bolumList;

                } else if (durum == 1) {
                    temp = uniList;
                    seciliTemp = secili_uniList;
                } else if (durum == 2) {
                    temp = sehirList;
                    seciliTemp = secili_sehirList;
                } else if (durum == 3) {
                    temp = uniTurList;
                    seciliTemp = secili_uniTurList;
                } else {
                    temp = puanTurList;
                    seciliTemp = secili_puanTurList;
                }

                temp.get(i).changeSelected();
                if (temp.get(i).isSelected()) {

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

                    seciliTemp.add(temp.get(i));
                    tv_secim.setText(seciliTemp.size() + " Seçili");
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

                    seciliTemp.remove(seciliTemp.indexOf(temp.get(i)));
                    if (seciliTemp.size() != 0)
                        tv_secim.setText(seciliTemp.size() + " Seçili");
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

        copkutusu_bolum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        copkutusu_bolum.setVisibility(View.INVISIBLE);
                        sayi_bolum.setVisibility(View.INVISIBLE);

                        for (ListItemFiltre bolum : bolumList) {

                            if (bolum.isSelected()) {
                                bolum.changeSelected();
                                rv_tercihyap.setAdapter(adapter);
                            }
                            secili_bolumList.remove(bolum);
                        }
                        tv_secim.setText("Seçim Yok");
                        recycler_view_bolum.setAdapter(horizontalAdapter);
                        recycler_view_bolum.setVisibility(View.GONE);
                    }
                }, 200);


            }
        });
        copkutusu_uni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        copkutusu_uni.setVisibility(View.INVISIBLE);
                        sayi_uni.setVisibility(View.INVISIBLE);

                        for (ListItemFiltre uni : uniList) {

                            if (uni.isSelected()) {
                                uni.changeSelected();
                                rv_tercihyap.setAdapter(adapter);
                            }
                            secili_uniList.remove(uni);
                        }
                        tv_secim.setText("Seçim Yok");
                        recycler_view_uni.setAdapter(horizontalAdapter);
                        recycler_view_uni.setVisibility(View.GONE);
                    }
                }, 200);
            }
        });
        copkutusu_sehir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        copkutusu_sehir.setVisibility(View.INVISIBLE);
                        sayi_sehir.setVisibility(View.INVISIBLE);

                        for (ListItemFiltre sehir : sehirList) {

                            if (sehir.isSelected()) {
                                sehir.changeSelected();
                                rv_tercihyap.setAdapter(adapter);
                            }
                            secili_sehirList.remove(sehir);
                        }
                        tv_secim.setText("Seçim Yok");
                        recycler_view_sehir.setAdapter(horizontalAdapter);
                        recycler_view_sehir.setVisibility(View.GONE);
                    }
                }, 200);
            }
        });
        copkutusu_uniTur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        copkutusu_uniTur.setVisibility(View.INVISIBLE);
                        sayi_uniTur.setVisibility(View.INVISIBLE);

                        for (ListItemFiltre unitur : uniTurList) {

                            if (unitur.isSelected()) {
                                unitur.changeSelected();
                                rv_tercihyap.setAdapter(adapter);
                            }
                            secili_uniTurList.remove(unitur);
                        }
                        tv_secim.setText("Seçim Yok");
                        recycler_view_uniTur.setAdapter(horizontalAdapter);
                        recycler_view_uniTur.setVisibility(View.GONE);
                    }
                }, 200);
            }
        });
        copkutusu_puantur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        copkutusu_puantur.setVisibility(View.INVISIBLE);
                        sayi_puantur.setVisibility(View.INVISIBLE);

                        for (ListItemFiltre puantur : puanTurList) {

                            if (puantur.isSelected()) {
                                puantur.changeSelected();
                                rv_tercihyap.setAdapter(adapter);
                            }
                            secili_puanTurList.remove(puantur);
                        }
                        tv_secim.setText("Seçim Yok");
                        recycler_view_puantur.setAdapter(horizontalAdapter);
                        recycler_view_puantur.setVisibility(View.GONE);
                    }
                }, 200);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TercihSonuclariFragment nextFrag = new TercihSonuclariFragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void filter(String text) {

        ArrayList<ListItemFiltre> filteredList = new ArrayList<>();
        ArrayList<ListItemFiltre> list = new ArrayList<>();
        switch (durum) {
            case 0:
                list = bolumList;
                break;
            case 1:
                list = uniList;
                break;
            case 2:
                list = sehirList;
                break;
            case 3:
                list = uniTurList;
                break;
            case 4:
                list = puanTurList;
                break;
        }

        for (ListItemFiltre s : list) {
            if (s.getName().toLowerCase().contains(text.toLowerCase()))
                filteredList.add(s);

        }
        adapter.filterList(filteredList);
    }

    private ArrayList<ListItemFiltre> getFiltres() {

        if (durum == 0)
            return bolumList;
        else if (durum == 1)
            return uniList;
        else if (durum == 2)
            return sehirList;
        else if (durum == 3)
            return uniTurList;
        else
            return puanTurList;
    }

    public List<String> fill_with_data() {

        List<String> data = new ArrayList<>();

        if (durum == 0) {

            for (ListItemFiltre bolum : secili_bolumList) {
                data.add(bolum.getName());
            }

        } else if (durum == 1) {

            for (ListItemFiltre uni : secili_uniList) {
                data.add(uni.getName());
            }

        } else if (durum == 2) {

            for (ListItemFiltre sehir : secili_sehirList) {
                data.add(sehir.getName());
            }

        } else if (durum == 3) {

            for (ListItemFiltre unitur : secili_uniTurList) {
                data.add(unitur.getName());
            }

        } else {

            for (ListItemFiltre puantur : secili_puanTurList) {
                data.add(puantur.getName());
            }

        }
        return data;
    }


}
