package com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.sinifDegistirenOgrenciler;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.fethi.sinavzauygulama.activities.UserInfoItem;
import com.example.fethi.sinavzauygulama.diger.Islevsel;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.ListItemSinifDegistirenOgrenciler;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.ListItemSinifDegistirenOgrencilerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenDanSinifDegistirenOgrencilerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    public ArrayList<ListItemSinifDegistirenOgrenciler> sinifDegistirenler = new ArrayList<>();

    SwipeRefreshLayout refreshLayout;
    JSONObject res;
    String token;

    LinearLayout yeniOgrenciYokView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.o_fragment_sinif_degistiren_ogrenciler, container, false);

        yeniOgrenciYokView = view.findViewById(R.id.ogrenciYokView2);

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

        recyclerView = view.findViewById(R.id.rv_yeniOgrenciler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new ListItemSinifDegistirenOgrencilerAdapter(sinifDegistirenler, getActivity(),this);
        recyclerView.setAdapter(adapter);

        refreshLayout = view.findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.primaryPurple),
                getResources().getColor(R.color.primaryOrange),
                getResources().getColor(R.color.lightgreen));

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                parseJSON();

            }
        });

        return view;
    }

    public void parseJSON() {

        refreshLayout.setRefreshing(true);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        try {
            Realm realm = Realm.getDefaultInstance();
            token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Islevsel.sinifDegistirOnayURL,
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
                                sinifDegistirenler.clear();
                                for (int i = 0; i < res.getJSONArray("ogrenciler").length(); i++)
                                    sinifDegistirenler.add(new ListItemSinifDegistirenOgrenciler(res.getJSONArray("ogrenciler").getJSONObject(i).getString("name"),
                                            res.getJSONArray("ogrenciler").getJSONObject(i).getString("sinif1"),
                                            res.getJSONArray("ogrenciler").getJSONObject(i).getString("sinif2"),
                                            res.getJSONArray("ogrenciler").getJSONObject(i).getInt("id")));
                            }
                            isEmpty();
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            isEmpty();
                            e.printStackTrace();
                        }

                        refreshLayout.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR : ", error.toString());
                        refreshLayout.setRefreshing(false);

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

    @Override
    public void onRefresh() {
        parseJSON();
    }

    public void isEmpty() {
        if (sinifDegistirenler.isEmpty()) {
            yeniOgrenciYokView.setVisibility(View.VISIBLE);
        }else
            yeniOgrenciYokView.setVisibility(View.GONE);
    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
