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
import android.widget.ExpandableListView;
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
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgrenciItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgretmenExpLVAdapterDanOgrencilerim;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.SinifItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenDanOgrencilerimFragment extends Fragment {

    public OgretmenExpLVAdapterDanOgrencilerim expand_adapter;
    public ExpandableListView expand_lv_siniflar;
    public ArrayList<SinifItem> siniflar = new ArrayList<>();

    JSONObject res;
    String token;
    Realm realm = Realm.getDefaultInstance();

    LinearLayout yokView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_danisman_ogrencilerim, container, false);

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

        yokView = view.findViewById(R.id.yokView);
        expand_lv_siniflar = view.findViewById(R.id.expand_lv_dan_ogr);

        expand_adapter = new OgretmenExpLVAdapterDanOgrencilerim(getActivity(), siniflar);
        expand_lv_siniflar.setAdapter(expand_adapter);

        expand_lv_siniflar.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                OgretmenDanOgrenciBilgilerFragment nextFrag = new OgretmenDanOgrenciBilgilerFragment();
                nextFrag.seciliOgrenci = siniflar.get(groupPosition).getOgrenciler().get(childPosition);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return true;
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
                Islevsel.danismanOgretmenSiniflarURL,
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
                                for (int i = 0; i < res.getJSONArray("siniflar").length(); i++) {
                                    JSONObject sinifItem = (JSONObject) res.getJSONArray("siniflar").get(i);
                                    SinifItem tempSinif = new SinifItem(sinifItem.getString("name"),
                                            new ArrayList<OgrenciItem>());

                                    for (int j = 0; j < sinifItem.getJSONArray("ogrenciler").length(); j++) {
                                        JSONObject ogrenciItem = (JSONObject) sinifItem.getJSONArray("ogrenciler").get(j);
                                        tempSinif.getOgrenciler().add(new OgrenciItem(ogrenciItem.getString("name"), ogrenciItem.getInt("id")));
                                    }
                                    siniflar.add(tempSinif);
                                }
                            }
                            isEmpty();
                            expand_adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            isEmpty();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR : ", error.toString());
                        isEmpty();

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

    public void isEmpty() {
        if (siniflar.isEmpty()) {
            yokView.setVisibility(View.VISIBLE);
        } else
            yokView.setVisibility(View.GONE);
    }


}
