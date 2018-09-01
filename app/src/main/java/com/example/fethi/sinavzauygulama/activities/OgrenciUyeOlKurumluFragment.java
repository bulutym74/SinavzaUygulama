package com.example.fethi.sinavzauygulama.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.diger.App;
import com.example.fethi.sinavzauygulama.diger.Islevsel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgrenciUyeOlKurumluFragment extends Fragment {

    FloatingActionButton btn_devam;
    private int index = 0;
    LinearLayout linearLayout;
    View back;
    LinearLayout sec_sinifi;
    CardView card;
    AppCompatEditText isim, soyisim, tcNo,tel,ogrNo;
    TextView secildimi;
    boolean visible;
    ViewGroup transitionsContainer;
    ImageView log_out;

    private RecyclerView rv_sinifsec;
    private RecyclerView.Adapter adapter;
    public List<OgrenciSinifSec> siniflar;

    int seciliSinifID;
    Realm realm = Realm.getDefaultInstance();
    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment_ogrenci_uyeol_kurumlu, container, false);

        transitionsContainer = view.findViewById(R.id.transitionsContainer);

        rv_sinifsec = view.findViewById(R.id.rv_sinifSec);
        rv_sinifsec.setHasFixedSize(true);
        rv_sinifsec.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new OgrenciSinifSecAdapter(siniflar, getContext(), this,0);
        rv_sinifsec.setAdapter(adapter);

        card = view.findViewById(R.id.card);
        isim = view.findViewById(R.id.et_isim);
        soyisim = view.findViewById(R.id.et_soyisim);
        tcNo = view.findViewById(R.id.et_tcNo);
        tel = view.findViewById(R.id.et_tel);
        ogrNo = view.findViewById(R.id.et_ogrNu);
        btn_devam = view.findViewById(R.id.button_devam);
        secildimi = view.findViewById(R.id.secildimi);
        back = view.findViewById(R.id.back);

        log_out = view.findViewById(R.id.log_out);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.where(UserInfoItem.class).findAll().deleteAllFromRealm();
                    }
                });

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        Spinner spinner = view.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.gruplar_alan, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        linearLayout = view.findViewById(R.id.activity);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.setVisibility(View.GONE);
                back.setVisibility(View.GONE);
                btn_devam.setVisibility(View.VISIBLE);
                isim.clearFocus();
                soyisim.clearFocus();
                tcNo.clearFocus();
                tel.clearFocus();
                ogrNo.clearFocus();
                //klavye gizle
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(linearLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        sec_sinifi = view.findViewById(R.id.sec_sinifi);
        sec_sinifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animShow = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.view_show);
                card.startAnimation(animShow);
                card.setVisibility(View.VISIBLE);

                isim.clearFocus();
                soyisim.clearFocus();
                tcNo.clearFocus();
                tel.clearFocus();
                ogrNo.clearFocus();
                btn_devam.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);

            }
        });

        btn_devam = view.findViewById(R.id.button_devam);
        btn_devam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isim.getText().toString().isEmpty()){
                    isim.setError("İsim giriniz");
                    return;
                }
                if (soyisim.getText().toString().isEmpty()){
                    soyisim.setError("Soyisim giriniz");
                    return;
                }
                if (tcNo.getText().toString().isEmpty()){
                    tcNo.setError("T.C. kimlik no giriniz");
                    return;
                }
                if (tel.getText().toString().isEmpty()){
                    tel.setError("Telefon numarası giriniz");
                    return;
                }
                String sinif = secildimi.getText().toString();
                if (sinif.equals("Seçilmedi")){
                    Toast.makeText(getActivity(),"Lütfen sınıfınızı seçin",Toast.LENGTH_LONG).show();
                    return;
                }

                int bolum;
                if (index == 3)
                    bolum = 6;
                else
                    bolum = index + 1;

                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Yükleniyor", "Lütfen bekleyin...", true);
                dialog.show();

                final JSONObject parameters = new JSONObject();

                try {
                    parameters.accumulate("name", isim.getText().toString());
                    parameters.accumulate("lastname", soyisim.getText().toString());
                    parameters.accumulate("mobile", tel.getText().toString());
                    parameters.accumulate("tcno", tcNo.getText().toString());
                    parameters.accumulate("sinif", seciliSinifID);
                    parameters.accumulate("no", ogrNo.getText().toString());
                    parameters.accumulate("bolum", bolum);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
                    }
                });

                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        Islevsel.ogrenciTamamlaURL,
                        parameters,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {

                                dialog.dismiss();
                                try {
                                    if (response.getBoolean("isFailed")) {
                                        Log.e("FAILED : ", response.getString("message"));
                                    } else {
                                        Log.e("RESPONSE : ", response.toString());

                                        realm.executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                try {
                                                    realm.where(UserInfoItem.class).findAll().get(0).setId(response.getInt("id"));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });

                                        Intent intent = new Intent(getActivity(), OnayBekleniyor.class);
                                        intent.putExtra("tur",0);
                                        intent.putExtra("ret",false);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("ERROR : ", error.toString());

                                dialog.dismiss();
                                Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                toast.show();
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        final Map<String, String> headers = new HashMap<>();
                        headers.putAll(super.getHeaders());

                        headers.put("Authorization", "Bearer " + token);

                        return headers;
                    }
                };

                requestQueue.add(objectRequest);



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
