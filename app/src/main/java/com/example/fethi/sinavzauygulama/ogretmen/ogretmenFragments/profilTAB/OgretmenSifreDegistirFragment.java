package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.profilTAB;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.activities.UserInfoItem;
import com.example.fethi.sinavzauygulama.diger.Islevsel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenSifreDegistirFragment extends Fragment {

    LinearLayout linearLayout;

    AppCompatEditText yeniSifre,yeniSifreOnayla;
    TextInputLayout yeniSifre_layout,yeniSifreOnayla_layout;

    FloatingActionButton btn_devam;

    JSONObject res;
    String token;
    Realm realm = Realm.getDefaultInstance();
    String code;
    String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sifre_degistir, container, false);

        yeniSifre = view.findViewById(R.id.yeniSifre);
        yeniSifreOnayla = view.findViewById(R.id.yeniSifreOnayla);

        yeniSifre_layout = view.findViewById(R.id.yeniSifre_layout);
        yeniSifreOnayla_layout = view.findViewById(R.id.yeniSifreOnayla_layout);

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

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        token = realm.where(UserInfoItem.class).findAll().get(0).getToken();

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Islevsel.sifreDegistirURL,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        try {
                            if (response.getBoolean("isFailed")) {
                                Log.e("FAILED : ", response.getString("message"));

                                Toast toast = Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                toast.show();

                            } else {
                                res = response;

                                code = res.getString("code");
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR ", error.toString());

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

        yeniSifre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                yeniSifre_layout.setPasswordVisibilityToggleEnabled(true);
            }
        });
        yeniSifreOnayla.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                yeniSifreOnayla_layout.setPasswordVisibilityToggleEnabled(true);
            }
        });

        linearLayout = view.findViewById(R.id.activity);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //klavye gizle
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(linearLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        btn_devam = view.findViewById(R.id.btn_devam);
        btn_devam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (yeniSifre.getText().toString().isEmpty()) {
                    yeniSifre_layout.setPasswordVisibilityToggleEnabled(false);
                    yeniSifre.setError("Yeni şifrenizi giriniz");
                    return;
                }if (yeniSifre.getText().toString().length() < 6) {
                    yeniSifre_layout.setPasswordVisibilityToggleEnabled(false);
                    yeniSifre.setError("En az 6 karakter");
                    return;
                } else {
                    String sifre = yeniSifre.getText().toString();

                    for (int i = 0; i < sifre.length() - 2; i++) {
                        if (sifre.charAt(i) == sifre.charAt(i + 1) && sifre.charAt(i + 1) == sifre.charAt(i + 2)) {
                            yeniSifre_layout.setPasswordVisibilityToggleEnabled(false);
                            yeniSifre.setError("Aynı karakter 3 kez üst üste olamaz");
                            return;
                        }
                    }

                }
                if (yeniSifreOnayla.getText().toString().isEmpty()) {
                    yeniSifreOnayla_layout.setPasswordVisibilityToggleEnabled(false);
                    yeniSifreOnayla.setError("Yeni şifrenizi tekrar giriniz");
                    return;
                }
                if (yeniSifre.getText().toString().length() > 5 && !yeniSifreOnayla.getText().toString().matches(yeniSifre.getText().toString())){
                    yeniSifreOnayla_layout.setPasswordVisibilityToggleEnabled(false);
                    yeniSifreOnayla.setError("Yeni şifreniz eşleşmiyor");
                    return;
                }

                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Yükleniyor", "Lütfen bekleyin...", true);
                dialog.show();

                final JSONObject parameters = new JSONObject();


                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        email = realm.where(UserInfoItem.class).findAll().get(0).getEmail();
                    }
                });

                try {
                    parameters.accumulate("password", yeniSifre.getText().toString());
                    parameters.accumulate("confirmPassword", yeniSifreOnayla.getText().toString());
                    parameters.accumulate("email", email);
                    parameters.accumulate("code", code);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                token = realm.where(UserInfoItem.class).findAll().get(0).getToken();

                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        Islevsel.sifreDegistirURL,
                        parameters,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {
                                dialog.dismiss();
                                try {
                                    if (response.getBoolean("isFailed")) {
                                        Log.e("FAILED : ", response.getString("message"));

                                        Toast toast = Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                                        toast.show();
                                    } else {
                                        res = response;

                                        realm.executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                realm.where(UserInfoItem.class).findAll().get(0).setPassword(yeniSifre.getText().toString());
                                            }
                                        });

                                        Toast toast = Toast.makeText(getActivity(), "Şifreniz değiştirildi", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                                        toast.show();
                                    }

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("ERROR ", error.toString());

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
