package com.example.fethi.sinavzauygulama.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.internal.IOException;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class UyeOlFragment extends Fragment {

    FloatingActionButton btn_devam;
    private int index = 0;
    FrameLayout frameLayout, bagliLayout;
    CheckBox cb;
    TextInputLayout et_kod;
    TextView text_bagliyim;
    AppCompatEditText email, password, code;
    TextInputLayout inputLayout;

    Realm realm = Realm.getDefaultInstance();
    JSONObject res;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment_uyeol, container, false);

        cb = view.findViewById(R.id.cbox);
        et_kod = view.findViewById(R.id.et_kod);
        bagliLayout = view.findViewById(R.id.bagliLayout);

        email = view.findViewById(R.id.username_TextField);
        password = view.findViewById(R.id.password_TextField);
        code = view.findViewById(R.id.code);

        inputLayout = view.findViewById(R.id.password_TextInputLayout);
        text_bagliyim = view.findViewById(R.id.text_bagliyim);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                inputLayout.setPasswordVisibilityToggleEnabled(true);
            }
        });

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        getActivity().onBackPressed();
                    }
                }, 100);
            }
        });

        Spinner spinner = view.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.gruplar_uyeOl, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                if (index == 0) {
                    et_kod.setVisibility(View.VISIBLE);
                    bagliLayout.setVisibility(View.GONE);
                } else {
                    text_bagliyim.setText("Kuruma Bağlıyım");
                    et_kod.setVisibility(View.INVISIBLE);
                    bagliLayout.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb.isChecked()) {
                    et_kod.setVisibility(View.VISIBLE);
                } else
                    et_kod.setVisibility(View.INVISIBLE);
            }
        });

        frameLayout = view.findViewById(R.id.activity_uyeOl);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //klavye gizle
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(frameLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        btn_devam = view.findViewById(R.id.button_devam);
        btn_devam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email.getText().toString().isEmpty()) {
                    email.setError("Bu alan boş olamaz");
                    return;
                }
                if (!EMAIL_ADDRESS_PATTERN.matcher(email.getText().toString()).matches()) {
                    email.setError("Geçerli bir e-mail giriniz");
                    return;
                }

                String sifre = password.getText().toString();

                if (sifre.isEmpty()) {
                    inputLayout.setPasswordVisibilityToggleEnabled(false);
                    password.setError("Bu alan boş olamaz");
                    return;
                }
                if (sifre.length() < 6) {
                    inputLayout.setPasswordVisibilityToggleEnabled(false);
                    password.setError("En az 6 karakter");
                    return;
                }

                Set<Character> mySet = new HashSet();

                for (char c : sifre.toCharArray())
                    mySet.add(c);

                if (mySet.size() < 3) {
                    inputLayout.setPasswordVisibilityToggleEnabled(false);
                    password.setError("En az 3 farklı karakter kullanılmalı!");
                    return;
                }

                if (code.getText().toString().length() < 6 && index==0) {
                    code.setError("Hatalı kod");
                    return;
                }
                if (code.getText().toString().length() < 6 && cb.isChecked()) {
                    code.setError("Hatalı kod");
                    return;
                }

                boolean bagli = false;
                if (index == 0)
                    bagli = true;
                else if (index == 1 && cb.isChecked())
                    bagli = true;

                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Yükleniyor", "Lütfen bekleyin...", true);
                dialog.show();

                final JSONObject parameters = new JSONObject();

                try {
                    parameters.accumulate("Email", email.getText().toString().trim());
                    parameters.accumulate("Password", password.getText().toString());
                    parameters.accumulate("Bagli", bagli);
                    parameters.accumulate("Code", code.getText().toString());
                    parameters.accumulate("Tur", index + 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        Islevsel.signupURL,
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

                                                        gecis();

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
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
                                Log.e("ERROR", error.toString());

                                dialog.dismiss();
                                Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                toast.show();
                            }
                        }
                );
                requestQueue.add(objectRequest);
            }
        });

        return view;
    }


    public void gecis() {
        try {
            if (index == 0) {

                if (res.getBoolean("bagli")) {

                    List<OgrenciSinifSec> siniflar = new ArrayList<>();
                    for (int i = 0; i < res.getJSONArray("siniflar").length(); i++) {
                        JSONObject sinif = (JSONObject) (res.getJSONArray("siniflar").get(i));
                        siniflar.add(new OgrenciSinifSec(sinif.getString("name"), sinif.getInt("id")));
                    }

                    OgrenciUyeOlKurumluFragment nextFrag = new OgrenciUyeOlKurumluFragment();
                    nextFrag.siniflar = siniflar;

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                            .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();
                } /*else {

                    List<OgrenciSinifSec> siniflar = new ArrayList<>();
                    for (int i = 0; i < res.getJSONArray("siniflar").length(); i++) {
                        JSONObject sinif = (JSONObject) (res.getJSONArray("siniflar").get(i));
                        siniflar.add(new OgrenciSinifSec(sinif.getString("name"), sinif.getInt("id")));
                    }

                    OgrenciUyeOlKurumsuzFragment nextFrag = new OgrenciUyeOlKurumsuzFragment();
                    nextFrag.ogretmeneBagli = cb.isChecked();
                    nextFrag.siniflar = siniflar;

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                            .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                            .addToBackStack(null)
                            .commit();

                }*/
            } else if (index == 1) {
                if (cb.isChecked()) {

                    ArrayList<OgretmenListItemFiltre> siniflar = new ArrayList<>();
                    for (int i = 0; i < res.getJSONArray("siniflar").length(); i++) {
                        JSONObject sinif = (JSONObject) (res.getJSONArray("siniflar").get(i));
                        siniflar.add(new OgretmenListItemFiltre(sinif.getString("name"), sinif.getInt("id")));
                    }

                    OgretmenUyeOlKurumluFragment nextFrag = new OgretmenUyeOlKurumluFragment();
                    nextFrag.siniflar = siniflar;
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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

}
