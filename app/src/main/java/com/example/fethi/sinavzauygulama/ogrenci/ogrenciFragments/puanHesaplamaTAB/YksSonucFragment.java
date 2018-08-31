package com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.puanHesaplamaTAB;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemPuanlarim;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.exceptions.RealmException;

public class YksSonucFragment extends Fragment {

    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14,tv15,tv16;
    private Button tyt,mf,tm,ts,dil;

    Realm realm = Realm.getDefaultInstance();
    EditText et_puanAdi;
    Button kaydet;
    ListItemPuanlarim p = new ListItemPuanlarim();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yks_sonuc,container,false);

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

        Bundle bundle = getArguments();
        final Double OBP = bundle.getDouble("OBP");
        final Double tytnet = bundle.getDouble("tytnet");
        final Double mfnet = bundle.getDouble("mfnet");
        final Double tmnet = bundle.getDouble("tmnet");
        final Double tsnet = bundle.getDouble("tsnet");
        final Double dilnet = bundle.getDouble("dilnet");

        final Double tytham = bundle.getDouble("tytham");
        final Double mfham = bundle.getDouble("mfham");
        final Double tmham = bundle.getDouble("tmham");
        final Double tsham = bundle.getDouble("tsham");
        final Double dilham = bundle.getDouble("dilham");
        final Double tytyer = bundle.getDouble("tytyer");
        final Double mfyer = bundle.getDouble("mfyer");
        final Double tmyer = bundle.getDouble("tmyer");
        final Double tsyer = bundle.getDouble("tsyer");
        final Double dilyer = bundle.getDouble("dilyer");

        tyt = view.findViewById(R.id.btn_tyt);
        mf = view.findViewById(R.id.btn_mf);
        tm = view.findViewById(R.id.btn_tm);
        ts = view.findViewById(R.id.btn_ts);
        dil = view.findViewById(R.id.btn_dil);

        tv1  = view.findViewById(R.id.tv_OBP);
        tv2  = view.findViewById(R.id.tv_TYTNet);
        tv3  = view.findViewById(R.id.tv_MFNet);
        tv4  = view.findViewById(R.id.tv_TMNet);
        tv5  = view.findViewById(R.id.tv_TSNet);
        tv6  = view.findViewById(R.id.tv_DILNet);
        tv7  = view.findViewById(R.id.tv_tytham_puan);
        tv8  = view.findViewById(R.id.tv_mfham_puan);
        tv9  = view.findViewById(R.id.tv_tmham_puan);
        tv10 = view.findViewById(R.id.tv_tsham_puan);
        tv11 = view.findViewById(R.id.tv_dilham_puan);
        tv12 = view.findViewById(R.id.tv_tytyer_puan);
        tv13 = view.findViewById(R.id.tv_mfyer_puan);
        tv14 = view.findViewById(R.id.tv_tmyer_puan);
        tv15 = view.findViewById(R.id.tv_tsyer_puan);
        tv16 = view.findViewById(R.id.tv_dilyer_puan);

        tv1.setText(String.format("%.2f",OBP));
        tv2.setText(String.valueOf(tytnet));
        tv3.setText(String.valueOf(mfnet));
        tv4.setText(String.valueOf(tmnet));
        tv5.setText(String.valueOf(tsnet));
        tv6.setText(String.valueOf(dilnet));
        tv7.setText(String.format("%.3f",tytham));
        tv8.setText(String.format("%.3f",mfham));
        tv9.setText(String.format("%.3f",tmham));
        tv10.setText(String.format("%.3f",tsham));
        tv11.setText(String.format("%.3f",dilham));
        tv12.setText(String.format("%.3f",tytyer));
        tv13.setText(String.format("%.3f",mfyer));
        tv14.setText(String.format("%.3f",tmyer));
        tv15.setText(String.format("%.3f",tsyer));
        tv16.setText(String.format("%.3f",dilyer));


        kaydet = view.findViewById(R.id.btn_kaydet);
        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog d = new Dialog(getActivity());
                d.setContentView(R.layout.input_dialog);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                et_puanAdi = d.findViewById(R.id.et_puanAdi);
                Button btniptal = d.findViewById(R.id.btn_iptal);
                btniptal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });
                Button btnkaydet = d.findViewById(R.id.btn_kaydet);
                btnkaydet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //GET DATA
                        String puanadi = et_puanAdi.getEditableText().toString();
                        System.out.print(puanadi);

                            if (puanadi.length() == 0){
                                Toast toast = Toast.makeText(getActivity(), "Puan Adı Boş Olamaz", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                toast.show();
                            }
                            else if (realm.where(ListItemPuanlarim.class).equalTo("puanAdi", puanadi).count() != 0) {
                                Toast toast = Toast.makeText(getActivity(), puanadi + " zaten kayıtlı", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                toast.show();
                            }
                            else {
                                p.setPuanAdi(et_puanAdi.getText().toString().trim());
                                p.setTarih(Calendar.getInstance().getTime());
                                p.setObp(OBP);
                                p.setTytnet(tytnet);
                                p.setMfnet(mfnet);
                                p.setTmnet(tmnet);
                                p.setTsnet(tsnet);
                                p.setDilnet(dilnet);
                                p.setTytham(tytham);
                                p.setMfham(mfham);
                                p.setTmham(tmham);
                                p.setTsham(tsham);
                                p.setDilham(dilham);
                                p.setTytyer(tytyer);
                                p.setMfyer(mfyer);
                                p.setTmyer(tmyer);
                                p.setTsyer(tsyer);
                                p.setDilyer(dilyer);

                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        try {
                                            try {
                                                realm.copyToRealm(p);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }catch (RealmException e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                et_puanAdi.setText("");
                                Toast toast = Toast.makeText(getActivity(), "Kaydedildi", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                toast.show();
                                d.dismiss();
                            }

                        }

                });

                d.show();

            }
        });



        return view;
    }
    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

}
