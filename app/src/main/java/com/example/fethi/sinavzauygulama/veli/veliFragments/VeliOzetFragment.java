package com.example.fethi.sinavzauygulama.veli.veliFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.veli.veliAdapters.VeliListItemOzet;
import com.example.fethi.sinavzauygulama.veli.veliAdapters.VeliListItemOzetAdapter;
import com.example.fethi.sinavzauygulama.veli.veliAdapters.VeliListItemOzetHorizontal;
import com.example.fethi.sinavzauygulama.veli.veliAdapters.VeliListItemOzetHorizontalAdapter;

import java.util.ArrayList;
import java.util.List;

public class VeliOzetFragment extends Fragment {

    private RecyclerView recycleView_ozet_horizontal,recyclerView;
    private RecyclerView.Adapter adapter;
    private List<VeliListItemOzetHorizontal> listItemOzetHorizontals;
    private List<VeliListItemOzet> listItemOzets;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.v_fragment_ozet, container, false);

        recycleView_ozet_horizontal = view.findViewById(R.id.recycleView_ozet_horizontal);
        recycleView_ozet_horizontal.setHasFixedSize(true);
        recycleView_ozet_horizontal.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));


        listItemOzetHorizontals = new ArrayList<>();

        listItemOzetHorizontals.add(new VeliListItemOzetHorizontal(R.drawable.icon_analiz,"Analiz Sayısı","3"));
        listItemOzetHorizontals.add(new VeliListItemOzetHorizontal(R.drawable.icon_alarm,"Deneme Sayısı","4"));
        listItemOzetHorizontals.add(new VeliListItemOzetHorizontal(R.drawable.icon_tarih,"En Acil Ödev","4"));
        listItemOzetHorizontals.add(new VeliListItemOzetHorizontal(R.drawable.icon_deneme,"Analiz Sayısı","4"));
        listItemOzetHorizontals.add(new VeliListItemOzetHorizontal(R.drawable.icon_analiz,"Deneme Sayısı","4"));

        adapter = new VeliListItemOzetHorizontalAdapter(listItemOzetHorizontals,getActivity());
        recycleView_ozet_horizontal.setAdapter(adapter);

        /////////////

        recyclerView = view.findViewById(R.id.recycleView_ozet);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        listItemOzets = new ArrayList<>();

        listItemOzets.add(new VeliListItemOzet("Türkçe",11,6));
        listItemOzets.add(new VeliListItemOzet("Matematik",55,545));
        listItemOzets.add(new VeliListItemOzet("Fizik",6,70));
        listItemOzets.add(new VeliListItemOzet("Kimya",2,21));
        listItemOzets.add(new VeliListItemOzet("Biyoloji",3,34));
        listItemOzets.add(new VeliListItemOzet("Türkçe",1,12));
        listItemOzets.add(new VeliListItemOzet("Türkçe",3,60));
        listItemOzets.add(new VeliListItemOzet("Türkçe",4,60));

        adapter = new VeliListItemOzetAdapter(listItemOzets,getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }
}
