package com.example.fethi.sinavzauygulama.veli.veliFragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;

import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.veli.veliAdapters.VeliExpLVAdapterYapilmamisKonular;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VeliYapilmamisKonularFragment extends Fragment {

    public List<String> list_parent;
    public VeliExpLVAdapterYapilmamisKonular expand_adapter_konular;
    public HashMap<String, List<String>> list_child;
    public ExpandableListView expandlist_view_konular;
    public List<String> temelkavramlar_list;
    public List<String> trigonometri_list;
    public List<String> logaritma_list;
    public List<String> limit_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.v_fragment_yapilmamis_konular, container, false);

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

        expandlist_view_konular = view.findViewById(R.id.expand_lv_yapilmamis_konular);

        Hazırla(); // expandablelistview içeriğini hazırlamak için

        // ListItemFiltreAdapter sınıfımızı oluşturmak için başlıklardan oluşan listimizi ve onlara bağlı olan elemanlarımızı oluşturmak için HaspMap türünü yolluyoruz
        expand_adapter_konular = new VeliExpLVAdapterYapilmamisKonular(getActivity(), list_parent, list_child);
        expandlist_view_konular.setAdapter(expand_adapter_konular);  // oluşturduğumuz adapter sınıfını set ediyoruz
        expandlist_view_konular.setClickable(true);


        return view;

    }

    public void Hazırla() {
        list_parent = new ArrayList<>();  // başlıklarımızı listemelek için oluşturduk
        list_child = new HashMap<>(); // başlıklara bağlı elemenları tutmak için oluşturduk

        list_parent.add("Kavramlar");  // ilk başlığı giriyoruz
        list_parent.add("Trigonometri");   // ikinci başlığı giriyoruz
        list_parent.add("Logaritma");   // ikinci başlığı giriyoruz
        list_parent.add("Limit");   // ikinci başlığı giriyoruz


        temelkavramlar_list = new ArrayList<>();  // ilk başlık için info2 elemanları tanımlıyoruz
        temelkavramlar_list.add("Test 1");

        trigonometri_list = new ArrayList<>();  // ilk başlık için info2 elemanları tanımlıyoruz
        trigonometri_list.add("Test 2");
        trigonometri_list.add("Test 7");
        trigonometri_list.add("Test 8");


        logaritma_list = new ArrayList<>();  // ilk başlık için info2 elemanları tanımlıyoruz
        logaritma_list.add("Test 2");

        limit_list = new ArrayList<>();  // ilk başlık için info2 elemanları tanımlıyoruz
        limit_list.add("Test 3");


        list_child.put(list_parent.get(0), temelkavramlar_list); // ilk başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz
        list_child.put(list_parent.get(1), trigonometri_list); // ilk başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz
        list_child.put(list_parent.get(2), logaritma_list); // ilk başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz
        list_child.put(list_parent.get(3), limit_list); // ilk başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz


    }
    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}


