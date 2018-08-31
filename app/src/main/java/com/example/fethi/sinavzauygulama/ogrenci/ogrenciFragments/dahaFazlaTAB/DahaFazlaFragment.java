package com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.dahaFazlaTAB;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.dahaFazlaTAB.cozulenKitaplar.CozulenlerFragment;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.dahaFazlaTAB.profil.ProfileFragment;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.dahaFazlaTAB.puanlarim.PuanlarimFragment;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.dahaFazlaTAB.tercihListem.TercihListemFragment;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.dahaFazlaTAB.yapilmamisOdevler.YapilmamisOdevlerFragment;

public class DahaFazlaFragment extends Fragment {

    ImageView profile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dahafazla, container, false);

        view.findViewById(R.id.puanlarim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PuanlarimFragment nextFrag= new PuanlarimFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        view.findViewById(R.id.tercih_listem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TercihListemFragment nextFrag= new TercihListemFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        view.findViewById(R.id.yapilmamis_odevler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YapilmamisOdevlerFragment nextFrag= new YapilmamisOdevlerFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        view.findViewById(R.id.cozulen_kitaplar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CozulenlerFragment nextFrag= new CozulenlerFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        profile = view.findViewById(R.id.btn_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment nextFrag= new ProfileFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}
