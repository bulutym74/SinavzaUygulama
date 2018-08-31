package com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.dahaFazlaTAB.puanlarim;

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
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemPuanlarim;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemPuanlarimAdapter;
import com.example.fethi.sinavzauygulama.R;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class PuanlarimFragment extends Fragment {

    ListView lv_puanlarim;
    RealmResults<ListItemPuanlarim> puanlarim;

    Realm realm = Realm.getDefaultInstance();
    ListItemPuanlarimAdapter adapter;
    RealmChangeListener realmChangeListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_puanlarim, container, false);

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

        lv_puanlarim = view.findViewById(R.id.lv_puanlarim);
        puanlarim = realm.where(ListItemPuanlarim.class).findAll().sort("tarih", Sort.DESCENDING);

        lv_puanlarim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                PuanDetayFragment nextFrag= new PuanDetayFragment();
                nextFrag.seciliPuan = puanlarim.get(i);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });


        //setup adapter
        adapter = new ListItemPuanlarimAdapter(getActivity() , puanlarim);
        lv_puanlarim.setAdapter(adapter);

        //HANDLE DATA CHANGES EVENTS AND REFRESH
        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                //REFRESH
                adapter = new ListItemPuanlarimAdapter(getActivity(),realm.where(ListItemPuanlarim.class).findAll().sort("tarih",Sort.DESCENDING));
                lv_puanlarim.setAdapter(adapter);
            }
        };

        //ADD CHANGE LISTENER TO REALM
        realm.addChangeListener(realmChangeListener);


        return view;
    }

    //release resources
    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.removeChangeListener(realmChangeListener);
        realm.close();
    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

}
