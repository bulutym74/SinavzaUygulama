package com.example.fethi.sinavzauygulama.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.BuildConfig;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.diger.Islevsel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class LoginFragment extends Fragment {

    FloatingActionButton btn_giris;
    private int index;
    FrameLayout frameLayout;
    Button btn_uye_ol, btn_sifremi_unuttum,btn_guncelle;
    LinearLayout guncelleLayout;

    AppCompatEditText email, password;

    Realm realm = Realm.getDefaultInstance();
    JSONObject res;
    String token;
    Spinner spinner;

    String versionNumber,guncelleUrl,myVersion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment_login, container, false);

        spinner = view.findViewById(R.id.spinner);
        guncelleLayout = view.findViewById(R.id.guncelleLayout);
        btn_guncelle = view.findViewById(R.id.btn_guncelle);
        btn_giris = view.findViewById(R.id.button_giris);

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.gruplar, android.R.layout.simple_spinner_item);
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

        frameLayout = view.findViewById(R.id.activity_login);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //klavye gizle
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(frameLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        parseJSON();

        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            myVersion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        btn_guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(guncelleUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btn_giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (index == 2) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Yakında Kullanıma Açılacaktır", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 300);
                    toast.show();
                    return;
                }

                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Yükleniyor", "Lütfen bekleyin...", true);
                dialog.show();

                final JSONObject parameters = new JSONObject();

                try {
                    parameters.accumulate("Email", email.getText().toString().trim());
                    parameters.accumulate("Password", password.getText().toString());
                    parameters.accumulate("Tur", index + 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("PARAMETERS", parameters.toString());

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        Islevsel.loginURL,
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

                                        if (realm.where(UserInfoItem.class).findAll().isEmpty()) {

                                            final UserInfoItem user = new UserInfoItem(response.getString("token"), parameters.getString("Email"), parameters.getString("Password"),parameters.getInt("Tur")-1);
                                            user.setId(response.getInt("id"));
                                            realm.executeTransaction(new Realm.Transaction() {
                                                @Override
                                                public void execute(Realm realm) {
                                                    realm.copyToRealm(user);
                                                    gecis();
                                                }
                                            });
                                        } else {

                                            realm.executeTransaction(new Realm.Transaction() {
                                                @Override
                                                public void execute(Realm realm) {
                                                    try {
                                                        realm.where(UserInfoItem.class).findAll().get(0).setEmail(parameters.getString("Email"));
                                                        realm.where(UserInfoItem.class).findAll().get(0).setPassword(parameters.getString("Password"));
                                                        realm.where(UserInfoItem.class).findAll().get(0).setToken(response.getString("token"));
                                                        realm.where(UserInfoItem.class).findAll().get(0).setTur(parameters.getInt("Tur")-1);
                                                        realm.where(UserInfoItem.class).findAll().get(0).setId(response.getInt("id"));
                                                        gecis();

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        }


                                    }
                                } catch (JSONException e) {
                                    dialog.dismiss();
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
                );
                requestQueue.add(objectRequest);
                objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        60000,
                        3,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            }
        });

        btn_uye_ol = view.findViewById(R.id.btn_uye_ol);
        btn_uye_ol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UyeOlActivity.class));
            }
        });

        btn_sifremi_unuttum = view.findViewById(R.id.btn_sifremi_unuttum);
        btn_sifremi_unuttum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SifremiUnuttumActivity.class));
            }
        });

        return view;
    }
    public void parseJSON() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        try {
            Realm realm = Realm.getDefaultInstance();
            token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Islevsel.getVersionURL,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onResponse(final JSONObject response) {

                        try {
                            res = response;
                            versionNumber = res.get("versionNumber").toString();
                            guncelleUrl = res.get("url").toString();

                            if (myVersion.compareTo(versionNumber) < 0){
                                guncelleLayout.setVisibility(View.VISIBLE);
                                btn_giris.setVisibility(View.GONE);
                            }
                            else if (!realm.where(UserInfoItem.class).findAll().isEmpty()){

                                UserInfoItem user = realm.where(UserInfoItem.class).findAll().get(0);
                                email.setText(user.getEmail());
                                password.setText(user.getPassword());
                                spinner.setSelection(user.getTur());
                                index = user.getTur();

                                btn_giris.performClick();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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

    public void gecis() {

        try {
            if (res.getInt("id") == 0) {

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


                try {
                    Realm realm = Realm.getDefaultInstance();
                    token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        Islevsel.profilTamamlaURL,
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

                                        ArrayList<OgrenciSinifSec> siniflarOgrenci;
                                        ArrayList<OgretmenListItemFiltre> siniflarOgretmen;
                                        switch (index) {
                                            case 0:

                                                if (res.getBoolean("kurumaBagli")) {

                                                    siniflarOgrenci = new ArrayList<>();
                                                    for (int i = 0; i < res.getJSONArray("siniflar").length(); i++)
                                                        siniflarOgrenci.add(new OgrenciSinifSec(res.getJSONArray("siniflar").getJSONObject(i).getString("name"), res.getJSONArray("siniflar").getJSONObject(i).getInt("id")));

                                                    OgrenciUyeOlKurumluFragment nextFrag = new OgrenciUyeOlKurumluFragment();
                                                    nextFrag.siniflar = siniflarOgrenci;
                                                    getActivity().getSupportFragmentManager().beginTransaction()
                                                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                                                            .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                                                            .addToBackStack(null)
                                                            .commit();

                                                } else {

                                                    siniflarOgrenci = new ArrayList<>();
                                                    for (int i = 0; i < res.getJSONArray("siniflar").length(); i++)
                                                        siniflarOgrenci.add(new OgrenciSinifSec(res.getJSONArray("siniflar").getJSONObject(i).getString("name"), res.getJSONArray("siniflar").getJSONObject(i).getInt("id")));

                                                    OgrenciUyeOlKurumsuzFragment nextFrag = new OgrenciUyeOlKurumsuzFragment();
                                                    nextFrag.siniflar = siniflarOgrenci;
                                                    nextFrag.ogretmeneBagli = res.getBoolean("ogretmeneBagli");
                                                    getActivity().getSupportFragmentManager().beginTransaction()
                                                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                                                            .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                                                            .addToBackStack(null)
                                                            .commit();

                                                }

                                                break;
                                            case 1:

                                                if (res.getBoolean("bagli")) {

                                                    siniflarOgretmen = new ArrayList<>();
                                                    for (int i = 0; i < res.getJSONArray("siniflar").length(); i++)
                                                        siniflarOgretmen.add(new OgretmenListItemFiltre(res.getJSONArray("siniflar").getJSONObject(i).getString("name"), res.getJSONArray("siniflar").getJSONObject(i).getInt("id")));

                                                    OgretmenUyeOlKurumluFragment nextFrag = new OgretmenUyeOlKurumluFragment();
                                                    nextFrag.siniflar = siniflarOgretmen;
                                                    getActivity().getSupportFragmentManager().beginTransaction()
                                                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                                                            .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                                                            .addToBackStack(null)
                                                            .commit();
                                                } else {
                                                    OgretmenUyeOlKurumsuzFragment nextFrag = new OgretmenUyeOlKurumsuzFragment();
                                                    getActivity().getSupportFragmentManager().beginTransaction()
                                                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                                                            .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                                                            .addToBackStack(null)
                                                            .commit();

                                                }

                                                break;
                                            case 2:
                                                break;
                                            default:
                                                break;
                                        }

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

            } else {

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                try {
                    Realm realm = Realm.getDefaultInstance();
                    token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        Islevsel.isApprovedURL,
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

                                        if (res.getInt("isApproved") != 1) {
                                            Bundle bundle = new Bundle();

                                            bundle.putBoolean("ret", res.getInt("isApproved") == 0);
                                            bundle.putInt("tur", index);

                                            Intent myIntent = new Intent(getApplicationContext(), OnayBekleniyor.class);
                                            myIntent.putExtras(bundle);
                                            startActivity(myIntent);

                                        } else {
                                            Islevsel.updateURL();

                                            switch (index) {

                                                case 0:
                                                    startActivity(new Intent(getApplicationContext(), OgrenciAnasayfaActivity.class));
                                                    break;
                                                case 1:
                                                    startActivity(new Intent(getApplicationContext(), OgretmenAnasayfaActivity.class));
                                                    break;
                                                case 2:
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
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
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
