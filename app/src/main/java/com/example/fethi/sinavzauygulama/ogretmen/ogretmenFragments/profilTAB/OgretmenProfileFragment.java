package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.profilTAB;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemHorizontalAdapter;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgrenciItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.SinifItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenProfileFragment extends Fragment {

    ImageView log_out;
    FrameLayout sifre_degistir;
    FrameLayout sec_siniflar;

    TextView isim, kurum, email, kod;
    Button link;

    RecyclerView rv_siniflar;
    ListItemHorizontalAdapter horizontalAdapter;

    ArrayList<SinifItem> siniflar = new ArrayList<>();

    JSONObject res;
    String token;
    Realm realm = Realm.getDefaultInstance();

    List<String> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_profile, container, false);

        isim = view.findViewById(R.id.isim);
        kurum = view.findViewById(R.id.kurum);
        email = view.findViewById(R.id.email);
        kod = view.findViewById(R.id.kod);

        parseJSON();

        rv_siniflar = view.findViewById(R.id.rv_siniflar);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        horizontalAdapter = new ListItemHorizontalAdapter(data, getContext());

        rv_siniflar.setLayoutManager(horizontalLayoutManager);
        rv_siniflar.setAdapter(horizontalAdapter);


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
        sifre_degistir = view.findViewById(R.id.sifre_degistir);
        sifre_degistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenSifreDegistirFragment nextFrag = new OgretmenSifreDegistirFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        sec_siniflar = view.findViewById(R.id.sec_siniflar);
        sec_siniflar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OgretmenSinifDegistirFragment nextFrag = new OgretmenSinifDegistirFragment();
                nextFrag.siniflar = siniflar;
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        link = view.findViewById(R.id.btn_link);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.sinavza.com/privacy-policy");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        return view;
    }

    public void parseJSON() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        token = realm.where(UserInfoItem.class).findAll().get(0).getToken();

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Islevsel.ogretmenProfilURL,
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
                                siniflar.clear();
                                data.clear();

                                isim.setText(res.getString("name"));
                                kurum.setText(res.getString("kurum"));
                                email.setText(res.getString("eposta"));
                                kod.setText(res.getString("kod"));

                                for (int i = 0; i < res.getJSONArray("siniflar").length(); i++) {
                                    SinifItem sinifItem = new SinifItem(res.getJSONArray("siniflar").getJSONObject(i).getString("name"), new ArrayList<OgrenciItem>(),
                                            res.getJSONArray("siniflar").getJSONObject(i).getInt("id"));
                                    for (int j = 0; j < res.getJSONArray("siniflar").getJSONObject(i).getJSONArray("ogrenciler").length(); j++)
                                        sinifItem.getOgrenciler().add(new OgrenciItem(res.getJSONArray("siniflar").getJSONObject(i).getJSONArray("ogrenciler").getJSONObject(j).getString("name")
                                                        , res.getJSONArray("siniflar").getJSONObject(i).getJSONArray("ogrenciler").getJSONObject(j).getInt("id")
                                                )
                                        );

                                    siniflar.add(sinifItem);
                                    data.add(sinifItem.getSinifAdi());
                                }

                            }
                            horizontalAdapter.notifyDataSetChanged();
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
}
