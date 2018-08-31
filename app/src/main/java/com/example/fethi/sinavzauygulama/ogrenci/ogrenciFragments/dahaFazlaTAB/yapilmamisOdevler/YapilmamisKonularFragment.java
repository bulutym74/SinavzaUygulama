package com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.dahaFazlaTAB.yapilmamisOdevler;

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
import android.widget.TextView;

import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ExpLVAdapterYapilmamisKonular;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.KitapItem;
import com.example.fethi.sinavzauygulama.R;

public class YapilmamisKonularFragment extends Fragment {

    public KitapItem seciliKitap;
    public ExpLVAdapterYapilmamisKonular expand_adapter_konular;
    public ExpandableListView expandlist_view_konular;
    private TextView title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yapilmamis_konular, container, false);

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
        title = view.findViewById(R.id.title);
        title.setText(seciliKitap.getKitapAdi());


        expand_adapter_konular = new ExpLVAdapterYapilmamisKonular(getActivity(),seciliKitap);
        expandlist_view_konular.setAdapter(expand_adapter_konular);
        expandlist_view_konular.setClickable(true);



        return view;

    }
    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

}

