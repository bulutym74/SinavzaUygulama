package com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.dahaFazlaTAB.tercihListem;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemTercihListemAdapter;
import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemTercihSonucları;

import io.realm.Realm;
import io.realm.RealmResults;

public class TercihListemFragment extends Fragment {

    private Realm realm = Realm.getDefaultInstance();

    private RecyclerView recycleView;
    private RecyclerView.Adapter adapter;

    private RealmResults<ListItemTercihSonucları> tercihListem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tercihlistem,container,false);

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

        recycleView = view.findViewById(R.id.recycleView_terLis);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        tercihListem = realm.where(ListItemTercihSonucları.class).equalTo("selected",true).findAll();
        adapter = new ListItemTercihListemAdapter(tercihListem,getActivity());
        recycleView.setAdapter(adapter);


        return view;
    }
    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

}
