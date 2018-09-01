package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.bransOgretmeniTAB.odevlendir;

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

import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OdevSecKitapItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgretmenExpLVAdapterTestSec;
import com.example.fethi.sinavzauygulama.R;

public class OgretmenBraTestSecFragment extends Fragment {

    public OdevSecKitapItem seciliKitap;
    public OgretmenExpLVAdapterTestSec expand_adapter;

    public ExpandableListView expand_lv_testsec;

    private TextView title;
    public TextView secili_soru;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_testsec, container, false);

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
        secili_soru = view.findViewById(R.id.secili_soru);

        expand_lv_testsec = view.findViewById(R.id.expand_lv_testSec);
        title = view.findViewById(R.id.title);
        title.setText(seciliKitap.getKitapAdi());

        expand_adapter = new OgretmenExpLVAdapterTestSec(getActivity(), seciliKitap, this,0);
        expand_lv_testsec.setAdapter(expand_adapter);
        expand_adapter.updateSoru();

        return view;
    }

    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
