package com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.dahaFazlaTAB.profil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.example.fethi.sinavzauygulama.activities.LoginActivity;
import com.example.fethi.sinavzauygulama.activities.UserInfoItem;
import com.example.fethi.sinavzauygulama.diger.Islevsel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class ProfileFragment extends Fragment {

    ImageView log_out;
    FrameLayout sinif_degistir,ogretmen_degistir,bolum_degistir,sifre_degistir;
    TextView isim,sinif,ogretmen_ismi,kurum,bolum,email;

    JSONObject res;
    String token;
    Realm realm = Realm.getDefaultInstance();
    Bundle bundle = new Bundle();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        isim = view.findViewById(R.id.isim);
        sinif = view.findViewById(R.id.sinif);
        ogretmen_ismi = view.findViewById(R.id.ogretmen_ismi);
        kurum = view.findViewById(R.id.kurum);
        bolum = view.findViewById(R.id.bolum);
        email = view.findViewById(R.id.email);

        parseJSON();

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

        sinif_degistir = view.findViewById(R.id.sinif_degistir);
        sinif_degistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SinifDegistirFragment nextFrag= new SinifDegistirFragment();
                    ArrayList<String> ogrenciler = new ArrayList<>();

                    Log.e("RESPONSE JSON", res.getJSONObject("sinif").toString());

                    if (!res.getJSONObject("sinif").isNull("ogrenciler")) {
                        for (int i = 0; i < res.getJSONObject("sinif").getJSONArray("ogrenciler").length(); i++)
                            ogrenciler.add(res.getJSONObject("sinif").getJSONArray("ogrenciler").get(i).toString());
                    }

                    nextFrag.ogrenciler = ogrenciler;
                    nextFrag.baslik = res.getJSONObject("sinif").getString("name");
                    nextFrag.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left)
                            .replace(R.id.fragment_container, nextFrag,"findThisFragment")
                            .addToBackStack(null)
                            .commit();
                } catch (JSONException e) {
                    Log.e("CATCH ", e.getLocalizedMessage());
                }
            }
        });
        ogretmen_degistir = view.findViewById(R.id.ogretmen_degistir);
        ogretmen_degistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenDegistirFragment nextFrag= new OgretmenDegistirFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        bolum_degistir = view.findViewById(R.id.bolum_degistir);
        bolum_degistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BolumDegistirFragment nextFrag= new BolumDegistirFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        sifre_degistir = view.findViewById(R.id.sifre_degistir);
        sifre_degistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SifreDegistirFragment nextFrag= new SifreDegistirFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    public void parseJSON() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        token = realm.where(UserInfoItem.class).findAll().get(0).getToken();

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Islevsel.ogrenciProfilURL,
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

                                isim.setText(res.getString("name"));
                                String ogr = res.getString("ogretmen");
                                if (!ogr.equals(" "))
                                    ogretmen_ismi.setText(res.getString("ogretmen"));
                                else
                                    ogretmen_ismi.setText("Öğretmen Yok");

                                bolum.setText(res.getString("bolum"));
                                if (!res.isNull("kurum"))
                                    kurum.setText(res.getString("kurum"));
                                else
                                kurum.setText("Kurum Yok");

                                sinif.setText(res.getJSONObject("sinif").getString("name"));
                                email.setText(res.getString("eposta"));
                                bundle.putString("sinif",sinif.getText().toString());
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
    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
