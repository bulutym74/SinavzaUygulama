package com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.puanHesaplamaTAB;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.fethi.sinavzauygulama.R;

import java.text.DecimalFormat;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class PuanHesaplamaFragment extends Fragment {

    EditText et1,et2,et3,et4,et5,et6,et7,et8,et9,et10,et11,et12,et13,et14,et15,et16,et17,et18,et19;
    TextView net1,net2,net3,net4,net5,net6,net7,net8,net9;
    TextView net1_1,net2_2,net3_3,net4_4,net5_5,net6_6,net7_7,net8_8,net9_9;
    double n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15,n16,n17,n18,n19 = 0.0;
    double add6,add7,add8,add9,add10 = 0.0;
    CheckBox cb;
    FloatingActionButton fab;

    TextWatcher tw1= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et1.getText().length()>0) {
                n1 = Double.parseDouble(et1.getText().toString());
                if (n1>=0 && n1<=40)
                {
                    double a1 = n1 - n2/4 ;
                    net1.setText(new DecimalFormat("#.##").format(a1));
                }
                else{
                    et1.setText("40");
                }
            }else {try{n1 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n1=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {
            if(TextUtils.isEmpty(et1.getText().toString()) && TextUtils.isEmpty(et2.getText().toString())) {
                net1.setText("40");
            }
        }};

    TextWatcher tw2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et2.getText().length()>0) {
                n2 = Double.parseDouble(et2.getText().toString());
                if (n2>=0 && (n1+n2)<=40)
                {
                    double a1 = n1 - n2/4 ;
                    net1.setText(new DecimalFormat("#.##").format(a1));
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Doğru-Yanlış Toplamı 40'ı geçemez", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 300);
                    toast.show();
                    et2.setText("");
                }
            }else {try{n2 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n2=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {}};

    TextWatcher tw3 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et3.getText().length()>0) {
                n3 = Double.parseDouble(et3.getText().toString());
                if (n3>=0 && n3<=40)
                {
                    double a2 = n3 - n4/4 ;
                    net2.setText(new DecimalFormat("#.##").format(a2));
                }
                else{
                    et3.setText("40");
                }
            }else {try{n3 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n3=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {
            if(TextUtils.isEmpty(et3.getText().toString()) && TextUtils.isEmpty(et4.getText().toString())) {
                net2.setText("40");
            }
        }};

    TextWatcher tw4 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et4.getText().length()>0) {
                n4 = Double.parseDouble(et4.getText().toString());
                if (n4>=0 && (n3+n4)<=40)
                {
                    double a2 = n3 - n4/4 ;
                    net2.setText(new DecimalFormat("#.##").format(a2));
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Doğru-Yanlış Toplamı 40'ı geçemez", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 300);
                    toast.show();
                    et4.setText("");
                }
            }else {try{n4 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n4=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {}};

    TextWatcher tw5= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et5.getText().length()>0) {
                n5 = Double.parseDouble(et5.getText().toString());
                if (n5>=0 && n5<=20)
                {
                    double a3 = n5 - n6/4 ;
                    net3.setText(new DecimalFormat("#.##").format(a3));
                }
                else{
                    et5.setText("20");
                }
            }else {try{n5 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n5=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {
            if(TextUtils.isEmpty(et5.getText().toString()) && TextUtils.isEmpty(et6.getText().toString())) {
                net3.setText("20");
            }
        }};

    TextWatcher tw6 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et6.getText().length()>0) {
                n6 = Double.parseDouble(et6.getText().toString());
                if (n6>=0 && (n5+n6)<=20)
                {
                    double a3 = n5 - n6/4 ;
                    net3.setText(new DecimalFormat("#.##").format(a3));
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Doğru-Yanlış Toplamı 20'yi geçemez", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 300);
                    toast.show();
                    et6.setText("");
                }
            }else {try{n6 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n6=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {}};

    TextWatcher tw7 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et7.getText().length()>0) {
                n7 = Double.parseDouble(et7.getText().toString());
                if (n7>=0 && n7<=20)
                {
                    double a4 = n7 - n8/4 ;
                    net4.setText(new DecimalFormat("#.##").format(a4));
                }
                else{
                    et7.setText("20");
                }
            }else {try{n7 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n7=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {
            if(TextUtils.isEmpty(et7.getText().toString()) && TextUtils.isEmpty(et8.getText().toString())) {
                net4.setText("20");
            }
        }};

    TextWatcher tw8 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et8.getText().length()>0) {
                n8 = Double.parseDouble(et8.getText().toString());
                if (n8>=0 && (n7+n8)<=20)
                {
                    double a4 = n7 - n8/4 ;
                    net4.setText(new DecimalFormat("#.##").format(a4));
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Doğru-Yanlış Toplamı 20'yi geçemez", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 300);
                    toast.show();
                    et8.setText("");
                }
            }else {try{n8 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n8=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {}};

    TextWatcher tw9= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et9.getText().length()>0) {
                n9 = Double.parseDouble(et9.getText().toString());
                if (n9>=0 && n9<=40)
                {
                    double a5 = n9 - n10/4 ;
                    net5.setText(new DecimalFormat("#.##").format(a5));
                }
                else{
                    et9.setText("40");
                }
            }else {try{n9= Double.parseDouble(s.toString());}catch (NumberFormatException e){n9=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {
            if(TextUtils.isEmpty(et9.getText().toString()) && TextUtils.isEmpty(et10.getText().toString())) {
                net5.setText("40");
            }
        }};

    TextWatcher tw10 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et10.getText().length()>0) {
                n10 = Double.parseDouble(et10.getText().toString());
                if (n10>=0 && (n9+n10)<=40)
                {
                    double a5 = n9 - n10/4 ;
                    net5.setText(new DecimalFormat("#.##").format(a5));
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Doğru-Yanlış Toplamı 40'ı geçemez", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 300);
                    toast.show();
                    et10.setText("");
                }
            }else {try{n10 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n10=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {}};

    TextWatcher tw11 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et11.getText().length()>0) {
                n11 = Double.parseDouble(et11.getText().toString());
                if (n11>=0 && n11<=40)
                {
                    double a6 = n11 - n12/4 ;
                    net6.setText(new DecimalFormat("#.##").format(a6));
                }
                else{
                    et11.setText("40");
                }
            }else {try{n11 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n11=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {
            if(TextUtils.isEmpty(et11.getText().toString()) && TextUtils.isEmpty(et12.getText().toString())) {
                net6.setText("40");
            }
        }};

    TextWatcher tw12 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et12.getText().length()>0) {
                n12 = Double.parseDouble(et12.getText().toString());
                if (n12>=0 && (n11+n12)<=40)
                {
                    double a6 = n11 - n12/4 ;
                    net6.setText(new DecimalFormat("#.##").format(a6));;
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Doğru-Yanlış Toplamı 40'ı geçemez", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 300);
                    toast.show();
                    et12.setText("");
                }
            }else {try{n12 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n12=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {}};

    TextWatcher tw13= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et13.getText().length()>0) {
                n13 = Double.parseDouble(et13.getText().toString());
                if (n13>=0 && n13<=40)
                {
                    double a7 = n13 - n14/4 ;
                    net7.setText(new DecimalFormat("#.##").format(a7));
                }
                else{
                    et13.setText("40");
                }
            }else {try{n13 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n13=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {
            if(TextUtils.isEmpty(et13.getText().toString()) && TextUtils.isEmpty(et14.getText().toString())) {
                net7.setText("40");
            }
        }};

    TextWatcher tw14 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et14.getText().length()>0) {
                n14 = Double.parseDouble(et14.getText().toString());
                if (n14>=0 && (n13+n14)<=40)
                {
                    double a7 = n13 - n14/4 ;
                    net7.setText(new DecimalFormat("#.##").format(a7));
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Doğru-Yanlış Toplamı 40'ı geçemez", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 300);
                    toast.show();
                    et14.setText("");
                }
            }else {try{n14 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n14=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {}};

    TextWatcher tw15 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et15.getText().length()>0) {
                n15 = Double.parseDouble(et15.getText().toString());
                if (n15>=0 && n15<=40)
                {
                    double a8 = n15 - n16/4 ;
                    net8.setText(new DecimalFormat("#.##").format(a8));
                }
                else{
                    et15.setText("40");
                }
            }else {try{n15 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n15=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {
            if(TextUtils.isEmpty(et15.getText().toString()) && TextUtils.isEmpty(et16.getText().toString())) {
                net8.setText("40");
            }
        }};

    TextWatcher tw16 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et16.getText().length()>0) {
                n16 = Double.parseDouble(et16.getText().toString());
                if (n16>=0 && (n15+n16)<=40)
                {
                    double a8 = n15 - n16/4 ;
                    net8.setText(new DecimalFormat("#.##").format(a8));
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Doğru-Yanlış Toplamı 40'ı geçemez", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 300);
                    toast.show();
                    et16.setText("");
                }
            }else {try{n16 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n16=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {}};

    TextWatcher tw17 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et17.getText().length()>0) {
                n17 = Double.parseDouble(et17.getText().toString());
                if (n17>=0 && n17<=80)
                {
                    double a9 = n17 - n18/4 ;
                    net9.setText(new DecimalFormat("#.##").format(a9));
                }
                else{
                    et17.setText("80");
                }
            }else {try{n17 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n17=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {
            if(TextUtils.isEmpty(et17.getText().toString()) && TextUtils.isEmpty(et18.getText().toString())) {
                net9.setText("80");
            }
        }};

    TextWatcher tw18 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(et18.getText().length()>0) {
                n18 = Double.parseDouble(et18.getText().toString());
                if (n18>=0 && (n17+n18)<=80)
                {
                    double a9 = n17 - n18/4 ;
                    net9.setText(new DecimalFormat("#.##").format(a9));
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Doğru-Yanlış Toplamı 80'i geçemez", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 300);
                    toast.show();
                    et18.setText("");
                }
            }else {try{n18 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n18=0.0;}}}

        @Override
        public void afterTextChanged(Editable s) {}};

    TextWatcher tw19 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (et19.getText().toString().startsWith(".")) {
                et19.setText("");
                return;
            }

            if(et19.getText().length()>1) {
                n19 = Double.parseDouble(et19.getText().toString());
                if (n19>100)
                {
                    Toast.makeText(getActivity(),"Lütfen 50-100 arası değer giriniz",Toast.LENGTH_SHORT).show();
                    et19.setText("");
                }

            }else {try{n19 = Double.parseDouble(s.toString());}catch (NumberFormatException e){n19=50.0;}}}

        @Override
        public void afterTextChanged(Editable s) {}};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_puanhesaplama, container, false);

        fab = view.findViewById(R.id.fab);
        et1 = view.findViewById(R.id.et1);et2 = view.findViewById(R.id.et2);
        et3 = view.findViewById(R.id.et3);et4 = view.findViewById(R.id.et4);
        et5 = view.findViewById(R.id.et5);et6 = view.findViewById(R.id.et6);
        et7 = view.findViewById(R.id.et7);et8 = view.findViewById(R.id.et8);
        et9 = view.findViewById(R.id.et9);et10 = view.findViewById(R.id.et10);
        et11 = view.findViewById(R.id.et11);et12 = view.findViewById(R.id.et12);
        et13 = view.findViewById(R.id.et13);et14 = view.findViewById(R.id.et14);
        et15 = view.findViewById(R.id.et15);et16 = view.findViewById(R.id.et16);
        et17 = view.findViewById(R.id.et17);et18 = view.findViewById(R.id.et18);
        et19 = view.findViewById(R.id.et19);


        net1 = view.findViewById(R.id.net1);
        net2 = view.findViewById(R.id.net2);
        net3 = view.findViewById(R.id.net3);
        net4 = view.findViewById(R.id.net4);
        net5 = view.findViewById(R.id.net5);
        net6 = view.findViewById(R.id.net6);
        net7 = view.findViewById(R.id.net7);
        net8 = view.findViewById(R.id.net8);
        net9 = view.findViewById(R.id.net9);

        net1_1 = view.findViewById(R.id.net1_1);
        net2_2 = view.findViewById(R.id.net2_2);
        net3_3 = view.findViewById(R.id.net3_3);
        net4_4 = view.findViewById(R.id.net4_4);
        net5_5 = view.findViewById(R.id.net5_5);
        net6_6 = view.findViewById(R.id.net6_6);
        net7_7 = view.findViewById(R.id.net7_7);
        net8_8 = view.findViewById(R.id.net8_8);
        net9_9 = view.findViewById(R.id.net9_9);

        et1.addTextChangedListener(tw1);et2.addTextChangedListener(tw2);
        et3.addTextChangedListener(tw3);et4.addTextChangedListener(tw4);
        et5.addTextChangedListener(tw5);et6.addTextChangedListener(tw6);
        et7.addTextChangedListener(tw7);et8.addTextChangedListener(tw8);
        et9.addTextChangedListener(tw9);et10.addTextChangedListener(tw10);
        et11.addTextChangedListener(tw11);et12.addTextChangedListener(tw12);
        et13.addTextChangedListener(tw13);et14.addTextChangedListener(tw14);
        et15.addTextChangedListener(tw15);et16.addTextChangedListener(tw16);
        et17.addTextChangedListener(tw17);et18.addTextChangedListener(tw18);
        et19.addTextChangedListener(tw19);

        cb = view.findViewById(R.id.cb);

        net1.setText("40");net2.setText("40");net3.setText("20");net4.setText("20");net5.setText("40");net6.setText("40");net7.setText("40");net8.setText("40");net9.setText("80");

        net1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                net1_1.setText(net1.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        net2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                net2_2.setText(net2.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        net3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                net3_3.setText(net3.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        net4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                net4_4.setText(net4.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        net5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                net5_5.setText(net5.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        net6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                net6_6.setText(net6.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        net7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                net7_7.setText(net7.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        net8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                net8_8.setText(net8.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        net9.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                net9_9.setText(net9.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(et1.getText().toString()) && TextUtils.isEmpty(et2.getText().toString())) {
                    net1.setText("0");
                    net1_1.setText("40");

                }if(TextUtils.isEmpty(et3.getText().toString()) && TextUtils.isEmpty(et4.getText().toString())) {
                    net2.setText("0");
                    net2_2.setText("40");

                }if(TextUtils.isEmpty(et5.getText().toString()) && TextUtils.isEmpty(et6.getText().toString())) {
                    net3.setText("0");
                    net3_3.setText("20");

                }if(TextUtils.isEmpty(et7.getText().toString()) && TextUtils.isEmpty(et8.getText().toString())) {
                    net4.setText("0");
                    net4_4.setText("20");

                }if(TextUtils.isEmpty(et9.getText().toString()) && TextUtils.isEmpty(et10.getText().toString())) {
                    net5.setText("0");
                    net5_5.setText("40");

                }if(TextUtils.isEmpty(et11.getText().toString()) && TextUtils.isEmpty(et12.getText().toString())) {
                    net6.setText("0");
                    net6_6.setText("40");

                }if(TextUtils.isEmpty(et13.getText().toString()) && TextUtils.isEmpty(et14.getText().toString())) {
                    net7.setText("0");
                    net7_7.setText("40");

                }if(TextUtils.isEmpty(et15.getText().toString()) && TextUtils.isEmpty(et16.getText().toString())) {
                    net8.setText("0");
                    net8_8.setText("40");

                }if(TextUtils.isEmpty(et17.getText().toString()) && TextUtils.isEmpty(et18.getText().toString())) {
                    net9.setText("0");
                    net9_9.setText("80");
                }

                if(TextUtils.isEmpty(et19.getText().toString())) {
                    et19.setError("Diploma Puanı Giriniz");
                    return;

                }if(Double.parseDouble(et19.getText().toString())<50) {
                    et19.setError("Lütfen 50-100 arası değer giriniz");
                    et19.setText("");
                    return;
                }


                String OBP = et19.getText().toString();
                String nt1 = net1.getText().toString();
                String nt2 = net2.getText().toString();
                String nt3 = net3.getText().toString();
                String nt4 = net4.getText().toString();
                String nt5 = net5.getText().toString();
                String nt6 = net6.getText().toString();
                String nt7 = net7.getText().toString();
                String nt8 = net8.getText().toString();
                String nt9 = net9.getText().toString();

                double obp = Double.valueOf(OBP);
                double tytnet = Double.valueOf(nt1) + Double.valueOf(nt2) + Double.valueOf(nt3) + Double.valueOf(nt4);
                double mfnet  = Double.valueOf(nt5) + Double.valueOf(nt6);
                double tmnet  = Double.valueOf(nt5) + Double.valueOf(nt7);
                double tsnet  = Double.valueOf(nt7) + Double.valueOf(nt8);
                double dilnet = Double.valueOf(nt9);

                String a1 = net1.getText().toString().replace(",",".");String a2 = net4.getText().toString().replace(",",".");String a3 = net2.getText().toString().replace(",",".");String a4 = net3.getText().toString().replace(",",".");
                final double add1 = 98+Double.parseDouble(a1)*3.558+Double.parseDouble(a2)*3.484+Double.parseDouble(a3)*3.327+Double.parseDouble(a4)*3.232;

                String b1 = net1.getText().toString().replace(",",".");String b2 = net4.getText().toString().replace(",",".");String b3 = net2.getText().toString().replace(",",".");String b4 = net3.getText().toString().replace(",",".");String b5 = net5.getText().toString().replace(",",".");String b6 = net6.getText().toString().replace(",",".");
                final double add2 = 96+Double.parseDouble(b1)*1.3706+Double.parseDouble(b2)*1.3926+Double.parseDouble(b3)*1.2925+Double.parseDouble(b4)*1.3788+Double.parseDouble(b5)*3.067+Double.parseDouble(b6)*3.235;

                String c1 = net1.getText().toString().replace(",",".");String c2 = net4.getText().toString().replace(",",".");String c3 = net2.getText().toString().replace(",",".");String c4 = net3.getText().toString().replace(",",".");String c5 = net5.getText().toString().replace(",",".");String c6 = net7.getText().toString().replace(",",".");
                final double add3 = 94+Double.parseDouble(c1)*1.3416+Double.parseDouble(c2)*1.3938+Double.parseDouble(c3)*1.2565+Double.parseDouble(c4)*1.2917+Double.parseDouble(c5)*3.3134+Double.parseDouble(c6)*3.2994;

                String d1 = net1.getText().toString().replace(",",".");String d2 = net4.getText().toString().replace(",",".");String d3 = net2.getText().toString().replace(",",".");String d4 = net3.getText().toString().replace(",",".");String d5 = net7.getText().toString().replace(",",".");String d6 = net8.getText().toString().replace(",",".");
                final double add4 = 93+Double.parseDouble(d1)*1.3132+Double.parseDouble(d2)*1.395+Double.parseDouble(d3)*1.27256+Double.parseDouble(d4)*1.3553+Double.parseDouble(d5)*3.4543+Double.parseDouble(d6)*3.5056;

                String e1 = net1.getText().toString().replace(",",".");String e2 = net4.getText().toString().replace(",",".");String e3 = net2.getText().toString().replace(",",".");String e4 = net3.getText().toString().replace(",",".");String e5 = net9.getText().toString().replace(",",".");
                final double add5 = 100+Double.parseDouble(e1)*1.33+Double.parseDouble(e2)*1.33+Double.parseDouble(e3)*1.33+Double.parseDouble(e4)*1.33+Double.parseDouble(e5)*3;

                if (Double.parseDouble(et19.getText().toString()) < 50){
                    if (cb.isChecked()) add6 = add1 +50 * 0.3; else add6 = add1 + 50 * 0.6;}
                else {
                    if (cb.isChecked()) add6 = add1 + Double.parseDouble(et19.getText().toString()) * 0.3 ; else add6 = add1 + Double.parseDouble(et19.getText().toString()) * 0.6;}

                if (Double.parseDouble(et19.getText().toString()) < 50){
                    if (cb.isChecked()) add7 = add2 +50 * 0.3; else add7 = add2 + 50 * 0.6;}
                else {
                    if (cb.isChecked()) add7 = add2 + Double.parseDouble(et19.getText().toString()) * 0.3 ; else add7 = add2 + Double.parseDouble(et19.getText().toString()) * 0.6;}

                if (Double.parseDouble(et19.getText().toString()) < 50){
                    if (cb.isChecked()) add8 = add3 +50 * 0.3; else add8 = add3 + 50 * 0.6;}
                else {
                    if (cb.isChecked()) add8 = add3 + Double.parseDouble(et19.getText().toString()) * 0.3 ; else add8 = add3 + Double.parseDouble(et19.getText().toString()) * 0.6;}

                if (Double.parseDouble(et19.getText().toString()) < 50){
                    if (cb.isChecked()) add9 = add4 +50 * 0.3; else add9 = add4 + 50 * 0.6;}
                else {
                    if (cb.isChecked()) add9 = add4 + Double.parseDouble(et19.getText().toString()) * 0.3 ; else add9 = add4 + Double.parseDouble(et19.getText().toString()) * 0.6;}

                if (Double.parseDouble(et19.getText().toString()) < 50){
                    if (cb.isChecked()) add10 = add5 +50 * 0.3; else add10 = add5 + 50 * 0.6;}
                else {
                    if (cb.isChecked()) add10 = add5 + Double.parseDouble(et19.getText().toString()) * 0.3 ; else add10 = add5 + Double.parseDouble(et19.getText().toString()) * 0.6;}



                    if (cb.isChecked()){
                        obp = obp * 0.3;
                    }else {
                        obp = obp * 0.6;
                    }


                Bundle bundle = new Bundle();
                bundle.putDouble("OBP",obp);
                bundle.putDouble("tytnet",tytnet);
                bundle.putDouble("mfnet",mfnet);
                bundle.putDouble("tmnet",tmnet);
                bundle.putDouble("tsnet",tsnet);
                bundle.putDouble("dilnet",dilnet);

                bundle.putDouble("tytham",add1);
                bundle.putDouble("mfham",add2);
                bundle.putDouble("tmham",add3);
                bundle.putDouble("tsham",add4);
                bundle.putDouble("dilham",add5);
                bundle.putDouble("tytyer",add6);
                bundle.putDouble("mfyer",add7);
                bundle.putDouble("tmyer",add8);
                bundle.putDouble("tsyer",add9);
                bundle.putDouble("dilyer",add10);

                //klavye gizle
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                YksSonucFragment nextFrag= new YksSonucFragment();
                nextFrag.setArguments(bundle);
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
