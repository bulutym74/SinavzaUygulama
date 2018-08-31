package com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.dahaFazlaTAB.puanlarim;

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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemPuanlarim;
import com.example.fethi.sinavzauygulama.R;

import java.text.DecimalFormat;

import io.realm.Realm;

public class PuanDetayFragment extends Fragment {

    TextView title;

    public ListItemPuanlarim seciliPuan;
    TextView tv_OBP,tv_TYTNet,tv_MFNet,tv_TMNet,tv_TSNet,tv_DILNet;
    TextView tv_tytham_puan,tv_mfham_puan,tv_tmham_puan,tv_tsham_puan,tv_dilham_puan;
    TextView tv_tytyer_puan,tv_mfyer_puan,tv_tmyer_puan,tv_tsyer_puan,tv_dilyer_puan;
    TextView tv_tytham_sıra,tv_mfham_sıra,tv_tmham_sıra,tv_tsham_sıra,tv_dilham_sıra;
    TextView tv_tytyer_sıra,tv_mfyer_sıra,tv_tmyer_sıra,tv_tsyer_sıra,tv_dilyer_sıra;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_puandetay, container, false);

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

        title = view.findViewById(R.id.title);
        title.setText(seciliPuan.getPuanAdi());


        tv_OBP = view.findViewById(R.id.tv_OBP);
        tv_TYTNet = view.findViewById(R.id.tv_TYTNet);
        tv_MFNet = view.findViewById(R.id.tv_MFNet);
        tv_TMNet = view.findViewById(R.id.tv_TMNet);
        tv_TSNet = view.findViewById(R.id.tv_TSNet);
        tv_DILNet = view.findViewById(R.id.tv_DILNet);


        tv_tytham_puan = view.findViewById(R.id.tv_tytham_puan);
        tv_mfham_puan = view.findViewById(R.id.tv_mfham_puan);
        tv_tmham_puan = view.findViewById(R.id.tv_tmham_puan);
        tv_tsham_puan = view.findViewById(R.id.tv_tsham_puan);
        tv_dilham_puan = view.findViewById(R.id.tv_dilham_puan);

        tv_tytyer_puan = view.findViewById(R.id.tv_tytyer_puan);
        tv_mfyer_puan = view.findViewById(R.id.tv_mfyer_puan);
        tv_tmyer_puan = view.findViewById(R.id.tv_tmyer_puan);
        tv_tsyer_puan = view.findViewById(R.id.tv_tsyer_puan);
        tv_dilyer_puan = view.findViewById(R.id.tv_dilyer_puan);

        tv_tytham_sıra = view.findViewById(R.id.tv_tytham_sıra);
        tv_mfham_sıra = view.findViewById(R.id.tv_mfham_sıra);
        tv_tmham_sıra = view.findViewById(R.id.tv_tmham_sıra);
        tv_tsham_sıra = view.findViewById(R.id.tv_tsham_sıra);
        tv_dilham_sıra = view.findViewById(R.id.tv_dilham_sıra);

        tv_tytyer_sıra = view.findViewById(R.id.tv_tytyer_sıra);
        tv_mfyer_sıra = view.findViewById(R.id.tv_mfyer_sıra);
        tv_tmyer_sıra = view.findViewById(R.id.tv_tmyer_sıra);
        tv_tsyer_sıra = view.findViewById(R.id.tv_tsyer_sıra);
        tv_dilyer_sıra = view.findViewById(R.id.tv_dilyer_sıra);


        Double obp = Double.valueOf(seciliPuan.getObp().toString());
        Double TYTNET = Double.valueOf(seciliPuan.getTytnet().toString());
        Double MFNet = Double.valueOf(seciliPuan.getMfnet().toString());
        Double TMNET = Double.valueOf(seciliPuan.getTmnet().toString());
        Double TSNet = Double.valueOf(seciliPuan.getTsnet().toString());
        Double DILNet = Double.valueOf(seciliPuan.getDilnet().toString());
        Double TYTham = Double.valueOf(seciliPuan.getTytham().toString());
        Double MFham = Double.valueOf(seciliPuan.getMfham().toString());
        Double TMham = Double.valueOf(seciliPuan.getTmham().toString());
        Double TSham = Double.valueOf(seciliPuan.getTsham().toString());
        Double DILham = Double.valueOf(seciliPuan.getDilham().toString());
        Double TYTyer = Double.valueOf(seciliPuan.getTytyer().toString());
        Double MFyer = Double.valueOf(seciliPuan.getMfyer().toString());
        Double TMyer = Double.valueOf(seciliPuan.getTmyer().toString());
        Double TSyer = Double.valueOf(seciliPuan.getTsyer().toString());
        Double DILyer = Double.valueOf(seciliPuan.getDilyer().toString());

        tv_OBP.setText(new DecimalFormat("#.##").format(obp));
        tv_TYTNet.setText(new DecimalFormat("#.##").format(TYTNET));
        tv_MFNet.setText(new DecimalFormat("#.##").format(MFNet));
        tv_TMNet.setText(new DecimalFormat("#.##").format(TMNET));
        tv_TSNet.setText(new DecimalFormat("#.##").format(TSNet));
        tv_DILNet.setText(new DecimalFormat("#.##").format(DILNet));

        tv_tytham_puan.setText(new DecimalFormat("#.##").format(TYTham));
        tv_mfham_puan.setText(new DecimalFormat("#.##").format(MFham));
        tv_tmham_puan.setText(new DecimalFormat("#.##").format(TMham));
        tv_tsham_puan.setText(new DecimalFormat("#.##").format(TSham));
        tv_dilham_puan.setText(new DecimalFormat("#.##").format(DILham));

        tv_tytyer_puan.setText(new DecimalFormat("#.##").format(TYTyer));
        tv_mfyer_puan.setText(new DecimalFormat("#.##").format(MFyer));
        tv_tmyer_puan.setText(new DecimalFormat("#.##").format(TMyer));
        tv_tsyer_puan.setText(new DecimalFormat("#.##").format(TSyer));
        tv_dilyer_puan.setText(new DecimalFormat("#.##").format(DILyer));


        return view;
    }
    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
