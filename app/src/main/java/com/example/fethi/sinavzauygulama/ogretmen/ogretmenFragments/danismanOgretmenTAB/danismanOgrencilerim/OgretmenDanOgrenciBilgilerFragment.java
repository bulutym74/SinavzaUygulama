package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.danismanOgrencilerim;

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
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgrenciItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenDanOgrenciBilgilerFragment extends Fragment {

    public OgrenciItem seciliOgrenci;
    private TextView title;
    private TextView txt_onaylanan, txt_reddedilen, txt_beklemede;

    JSONObject res;
    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_ogrenci_bilgiler, container, false);

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
        title = view.findViewById(R.id.title);
        title.setText(seciliOgrenci.getOgrAdi());

        txt_onaylanan = view.findViewById(R.id.txt_onaylanan);
        txt_reddedilen = view.findViewById(R.id.txt_reddedilen);
        txt_beklemede = view.findViewById(R.id.txt_beklemede);

        view.findViewById(R.id.onay_bekleyen_odevler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenDanOnayBekleyenOdevlerFragment nextFrag = new OgretmenDanOnayBekleyenOdevlerFragment();
                nextFrag.seciliOgrenci = seciliOgrenci;
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        view.findViewById(R.id.geciken_odevler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenDanGecikenOdevlerFragment nextFrag = new OgretmenDanGecikenOdevlerFragment();
                nextFrag.seciliOgrenci = seciliOgrenci;
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        view.findViewById(R.id.odev_gecmisi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenDanOdevGecmisiFragment nextFrag = new OgretmenDanOdevGecmisiFragment();
                nextFrag.seciliOgrenci = seciliOgrenci;
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        view.findViewById(R.id.cozulen_kitaplar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenDanCozulenKitaplarFragment nextFrag = new OgretmenDanCozulenKitaplarFragment();
                nextFrag.seciliOgrenci = seciliOgrenci;
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

        final JSONObject parameters = new JSONObject();

        try {
            parameters.accumulate("ogrenciId", seciliOgrenci.getId());
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
                Islevsel.danismanOgrenciOzetURL,
                parameters,
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
                                txt_onaylanan.setText(res.getInt("onaylananlar") + "");
                                txt_reddedilen.setText(res.getInt("reddedilenler") + "");
                                txt_beklemede.setText(res.getInt("beklemede") + "");

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

    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
