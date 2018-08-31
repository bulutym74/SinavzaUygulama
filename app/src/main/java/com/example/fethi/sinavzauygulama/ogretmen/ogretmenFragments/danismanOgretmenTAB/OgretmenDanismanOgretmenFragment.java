package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.fethi.sinavzauygulama.activities.UserInfoItem;
import com.example.fethi.sinavzauygulama.diger.Islevsel;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.danismanOdevlendir.OgretmenDanOdevlendirFragment;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.danismanOgrencilerim.OgretmenDanOgrencilerimFragment;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.kitapEkle.OgretmenDanKitapEkleFragment;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.sinifDegistirenOgrenciler.OgretmenDanSinifDegistirenOgrencilerFragment;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.yeniOgrenciOnayla.OgretmenDanYeniOgrenciOnaylaFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenDanismanOgretmenFragment extends Fragment {

    TextView ogr_onayla_circle, sinif_degistiren_circle;
    JSONObject res;
    String token;
    Realm realm = Realm.getDefaultInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_danisman_ogretmen, container, false);

        ogr_onayla_circle = view.findViewById(R.id.ogr_onayla_circle);
        sinif_degistiren_circle = view.findViewById(R.id.sinif_degistiren_circle);


        view.findViewById(R.id.danısman_odevlendir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenDanOdevlendirFragment nextFrag = new OgretmenDanOdevlendirFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        view.findViewById(R.id.kitap_ekle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenDanKitapEkleFragment nextFrag = new OgretmenDanKitapEkleFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        view.findViewById(R.id.yeni_ogrenci_onayla).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenDanYeniOgrenciOnaylaFragment nextFrag = new OgretmenDanYeniOgrenciOnaylaFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        view.findViewById(R.id.sinif_degistiren_ogrenciler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenDanSinifDegistirenOgrencilerFragment nextFrag = new OgretmenDanSinifDegistirenOgrencilerFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        view.findViewById(R.id.danısman_ogrencilerim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenDanOgrencilerimFragment nextFrag = new OgretmenDanOgrencilerimFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });


        parseJSON();

        return view;
    }

    public void parseJSON() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        token = realm.where(UserInfoItem.class).findAll().get(0).getToken();

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Islevsel.danismanMenuURL,
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

                                if (res.getInt("yeniOgrenciler") != 0){
                                    ogr_onayla_circle.setVisibility(View.VISIBLE);
                                    ogr_onayla_circle.setText(res.getInt("yeniOgrenciler") + "");
                                }

                                if (res.getInt("sinifDegistirenler") != 0){
                                    sinif_degistiren_circle.setVisibility(View.VISIBLE);
                                    sinif_degistiren_circle.setText(res.getInt("sinifDegistirenler") + "");
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
    }
}
