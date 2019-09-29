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
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.diger.Islevsel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenUyeOlKurumsuzFragment extends Fragment {

    FloatingActionButton btn_devam;
    LinearLayout linearLayout;
    AppCompatEditText isim,soyisim,tel;
    ImageView log_out;

    Realm realm = Realm.getDefaultInstance();
    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment_ogretmen_uyeol_kurumsuz, container, false);

        isim = view.findViewById(R.id.et_isim);
        soyisim = view.findViewById(R.id.et_soyisim);
        tel = view.findViewById(R.id.et_tel);
        btn_devam = view.findViewById(R.id.button_devam);

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

        linearLayout = view.findViewById(R.id.activity);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isim.clearFocus();
                soyisim.clearFocus();
                tel.clearFocus();
                //klavye gizle
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(linearLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        btn_devam = view.findViewById(R.id.btn_devam);
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
                if (tel.getText().toString().isEmpty()){
                    tel.setError("Telefon numarası giriniz");
                    return;
                }
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Yükleniyor", "Lütfen bekleyin...", true);
                dialog.show();

                final JSONObject parameters = new JSONObject();

                try {
                    parameters.accumulate("name", isim.getText().toString());
                    parameters.accumulate("lastname", soyisim.getText().toString());
                    parameters.accumulate("mobile", tel.getText().toString());
                    parameters.accumulate("tcno","");
                    parameters.accumulate("siniflar", new JSONArray());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                try {
                    Realm realm = Realm.getDefaultInstance();
                    token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        Islevsel.ogretmenTamamlaURL,
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
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
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
                objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        60000,
                        3,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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
