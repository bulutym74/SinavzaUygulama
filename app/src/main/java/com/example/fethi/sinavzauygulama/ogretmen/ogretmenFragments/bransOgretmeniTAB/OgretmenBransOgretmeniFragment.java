package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.bransOgretmeniTAB;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.bransOgretmeniTAB.kitapEkle.OgretmenBraKitapEkleFragment;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.bransOgretmeniTAB.odevlendir.OgretmenBraOdevlendirFragment;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.bransOgretmeniTAB.ogrencilerim.OgretmenBraOgrencilerimFragment;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.danismanOgrencilerim.OgretmenDanOgrencilerimFragment;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.kitapEkle.OgretmenDanKitapEkleFragment;

public class OgretmenBransOgretmeniFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_brans_ogretmeni, container, false);

        view.findViewById(R.id.odevlendir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenBraOdevlendirFragment nextFrag = new OgretmenBraOdevlendirFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        view.findViewById(R.id.kitap_ekle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenBraKitapEkleFragment nextFrag = new OgretmenBraKitapEkleFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        view.findViewById(R.id.ogrencilerim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenBraOgrencilerimFragment nextFrag = new OgretmenBraOgrencilerimFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

}