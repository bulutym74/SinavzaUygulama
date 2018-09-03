package com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.dahaFazlaTAB.profil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
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
import com.example.fethi.sinavzauygulama.activities.OgrenciSinifSec;
import com.example.fethi.sinavzauygulama.activities.UserInfoItem;
import com.example.fethi.sinavzauygulama.diger.Islevsel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenDegistirFragment extends Fragment {

    AppCompatEditText code;
    FloatingActionButton btn_devam;
    LinearLayout activity, activityTalep;
    TextView txt_talep;

    JSONObject res;
    String token;
    Realm realm = Realm.getDefaultInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ogretmen_degistir, container, false);

        code = view.findViewById(R.id.code);
        btn_devam = view.findViewById(R.id.btn_devam);
        activity = view.findViewById(R.id.activity);
        activityTalep = view.findViewById(R.id.activityTalep);
        txt_talep = view.findViewById(R.id.txt_talep);

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
        Log.e("URL", Islevsel.ogretmenDegistirURL);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Islevsel.ogretmenDegistirURL,
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

                                if (res.getBoolean("talep")) {
                                    activityTalep.setVisibility(View.VISIBLE);
                                    txt_talep.setText("Halihazırda öğretmen değişim talebiniz bulunmaktadır");
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


        btn_devam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Yükleniyor", "Lütfen bekleyin...", true);
                dialog.show();

                if (code.getText().toString().length() == 6) {

                    final JSONObject parameters = new JSONObject();

                    try {
                        parameters.accumulate("kod", code.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    token = realm.where(UserInfoItem.class).findAll().get(0).getToken();

                    JsonObjectRequest objectRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            Islevsel.ogrenciOgretmenYollaURL,
                            parameters,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(final JSONObject response) {
                                    dialog.dismiss();
                                    try {
                                        if (response.getBoolean("isFailed")) {
                                            Log.e("FAILED : ", response.getString("message"));
                                        } else {
                                            res = response;

                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                public void run() {
                                                    activity.setVisibility(View.INVISIBLE);
                                                    activityTalep.setVisibility(View.VISIBLE);
                                                    btn_devam.setVisibility(View.GONE);
                                                    txt_talep.setText("Öğretmen değiştirme isteğiniz işleme alınmıştır");
                                                }
                                            }, 300);

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
                } else
                    code.setError("Lütfen geçerli bir kod giriniz");
            }
        });

        return view;

    }

    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
