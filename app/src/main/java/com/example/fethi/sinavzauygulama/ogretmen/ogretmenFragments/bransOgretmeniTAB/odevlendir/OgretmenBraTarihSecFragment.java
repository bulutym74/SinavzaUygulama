package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.bransOgretmeniTAB.odevlendir;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OdevSecDersItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.SinifItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenBraTarihSecFragment extends Fragment {

    TextView tv_baslama, tv_bitis;
    LinearLayout sec_baslama, sec_bitis;
    FloatingActionButton fab_tarihSec;
    Bundle bundle;
    Calendar c;
    SimpleDateFormat df;

    public ArrayList<SinifItem> seciliSiniflar = new ArrayList<>();
    public ArrayList<OdevSecDersItem> seciliDersler = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_tarihsec, container, false);

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

        bundle = new Bundle();
        c = Calendar.getInstance();

        tv_baslama = view.findViewById(R.id.tv_baslama);
        sec_baslama = view.findViewById(R.id.sec_baslama);
        tv_bitis = view.findViewById(R.id.tv_bitis);
        sec_bitis = view.findViewById(R.id.sec_bitis);
        fab_tarihSec = view.findViewById(R.id.fab_tarihSec);

        df = new SimpleDateFormat("dd/MM/yyyy");// HH:mm:ss");
        final String reg_date = df.format(c.getTime());
        tv_baslama.setText(reg_date);

        c.add(Calendar.DATE, 6);  // number of days to add
        String end_date = df.format(c.getTime());
        tv_bitis.setText(end_date);
        c.add(Calendar.DATE, -6);

        sec_baslama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] str = tv_baslama.getText().toString().split("/");
                int yil = Integer.parseInt(str[2]);
                int ay = Integer.parseInt(str[1]);
                int gun = Integer.parseInt(str[0]);
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        tv_baslama.setText(formatDate(year, month, dayOfMonth));
                        tv_bitis.setText(formatDate(year, month, dayOfMonth + 6));
                        tv_baslama.setTextColor(getResources().getColor(R.color.lightgreen));
                    }
                }, yil, ay-1, gun);

                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Seç", dpd);
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", dpd);
                dpd.show();

            }
        });
        sec_bitis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] str = tv_bitis.getText().toString().split("/");
                int yil = Integer.parseInt(str[2]);
                int ay = Integer.parseInt(str[1]);
                int gun = Integer.parseInt(str[0]);
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        tv_bitis.setText(formatDate(year, month, dayOfMonth));
                        tv_bitis.setTextColor(getResources().getColor(R.color.lightgreen));
                    }
                },  yil, ay-1, gun);

                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Seç", dpd);
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", dpd);
                dpd.show();

            }
        });

        fab_tarihSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Date baslama;
                    Date bitis;

                    String start = tv_baslama.getText().toString();
                    String finish = tv_bitis.getText().toString();

                    baslama = df.parse(start);
                    bitis = df.parse(finish);


                    if (baslama.getTime() + 86400000 < System.currentTimeMillis()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Başlama tarihini kontrol ediniz", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                        toast.show();
                        return;
                    }

                    if (bitis.compareTo(baslama) < 0) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Başlama tarihi bitiş tarihinden sonra olamaz", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                        toast.show();
                        return;
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }

                bundle.putString("baslama", tv_baslama.getText().toString());
                bundle.putString("bitis", tv_bitis.getText().toString());

                OgretmenBraOdevOzetiFragment nextFrag = new OgretmenBraOdevOzetiFragment();
                nextFrag.setArguments(bundle);
                nextFrag.seciliSiniflar = seciliSiniflar;
                nextFrag.seciliDersler = seciliDersler;

                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;
    }

    private static String formatDate(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        return sdf.format(date);
    }

    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
    private Date stringToDate(String aDate,String aFormat) {

        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }
}
