package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.bransOgretmeniTAB;

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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.activities.UserInfoItem;
import com.example.fethi.sinavzauygulama.diger.Islevsel;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.bransOgretmeniTAB.kitapEkle.OgretmenBraKitapEkleFragment;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.bransOgretmeniTAB.odevlendir.OgretmenBraOdevlendirFragment;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.bransOgretmeniTAB.ogrencilerim.OgretmenBraOgrencilerimFragment;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.danismanOgrencilerim.OgretmenDanOgrencilerimFragment;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.kitapEkle.OgretmenDanKitapEkleFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenBransOgretmeniFragment extends Fragment {

    TextView odev_onayla_circle;
    JSONObject res;
    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_brans_ogretmeni, container, false);

        odev_onayla_circle = view.findViewById(R.id.odev_onayla_circle);

        view.findViewById(R.id.odevlendir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenBraOdevlendirFragment nextFrag = new OgretmenBraOdevlendirFragment();
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
                OgretmenBraKitapEkleFragment nextFrag = new OgretmenBraKitapEkleFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        view.findViewById(R.id.ogrencilerim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenBraOgrencilerimFragment nextFrag = new OgretmenBraOgrencilerimFragment();
                nextFrag.durum = 0;
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        view.findViewById(R.id.odev_onayla).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenBraOgrencilerimFragment nextFrag = new OgretmenBraOgrencilerimFragment();
                nextFrag.durum = 1;
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

        try {
            Realm realm = Realm.getDefaultInstance();
            token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Islevsel.bransMenuURL,
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

                                if (res.getInt("onaylanacakOdev") != 0){
                                    odev_onayla_circle.setVisibility(View.VISIBLE);
                                    odev_onayla_circle.setText(res.getInt("onaylanacakOdev") + "");
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
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}