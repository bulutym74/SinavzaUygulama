package com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.ozetTAB;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fethi.sinavzauygulama.activities.UserInfoItem;
import com.example.fethi.sinavzauygulama.diger.Islevsel;
import com.example.fethi.sinavzauygulama.diger.RecyclerTouchListener;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemOzet;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemOzetAdapter;
import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.dahaFazlaTAB.cozulenKitaplar.CozulenlerFragment;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.odevlerTAB.OdevlerFragment;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.dahaFazlaTAB.yapilmamisOdevler.YapilmamisOdevlerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OzetFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recycleView_ozet;
    private RecyclerView.Adapter adapter;
    private List<ListItemOzet> listItemOzets = new ArrayList<>();

    TextView cozulenSoru, yapilmamisTest, yapilmamisSoru, enYakinOdev, enYakinOdevTarih, yuzde;

    ProgressBar progressBar;

    BottomNavigationView bottomNav;
    LinearLayout card1, card2, card3;
    LinearLayout progressView;
    LinearLayout odevYokView;

    SwipeRefreshLayout refreshLayout;
    JSONObject res;
    String token;
    Realm realm = Realm.getDefaultInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ozet, container, false);

        final BottomNavigationView navigation = getActivity().findViewById(R.id.navigation_ogrenci);

        progressBar = view.findViewById(R.id.progressBar);

        cozulenSoru = view.findViewById(R.id.cozulenSoru);
        yapilmamisTest = view.findViewById(R.id.yapilmamisTest);
        yapilmamisSoru = view.findViewById(R.id.yapilmamisSoru);
        enYakinOdev = view.findViewById(R.id.enYakinOdev);
        enYakinOdevTarih = view.findViewById(R.id.enYakinOdevTarih);
        yuzde = view.findViewById(R.id.txt_yuzde);

        recycleView_ozet = view.findViewById(R.id.recycleView_ozet);
        recycleView_ozet.setHasFixedSize(true);
        recycleView_ozet.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new ListItemOzetAdapter(listItemOzets, getActivity());
        recycleView_ozet.setAdapter(adapter);

        card1 = view.findViewById(R.id.card1);
        card2 = view.findViewById(R.id.card2);
        card3 = view.findViewById(R.id.card3);
        progressView = view.findViewById(R.id.progressView);
        odevYokView = view.findViewById(R.id.odevYokView);

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        ViewGroup.LayoutParams params = card1.getLayoutParams();
        int a = dpToPx(12);
        params.width = width / 2 - a;

        card1.setLayoutParams(params);
        card2.setLayoutParams(params);
        card3.setLayoutParams(params);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        navigation.setSelectedItemId(R.id.nav_dahaFazla);
                        CozulenlerFragment nextFrag = new CozulenlerFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, nextFrag, "frag")
                                .addToBackStack(null)
                                .commit();
                    }
                }, 300);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        navigation.setSelectedItemId(R.id.nav_dahaFazla);
                        YapilmamisOdevlerFragment nextFrag = new YapilmamisOdevlerFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, nextFrag, "frag")
                                .addToBackStack(null)
                                .commit();
                    }
                }, 300);
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        navigation.setSelectedItemId(R.id.nav_odevler);
                        OdevlerFragment nextFrag = new OdevlerFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, nextFrag, "frag")
                                .addToBackStack(null)
                                .commit();
                    }
                }, 300);
            }
        });
        /*progressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        navigation.setSelectedItemId(R.id.nav_dahaFazla);
                        CozulenlerFragment nextFrag = new CozulenlerFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, nextFrag, "frag")
                                .addToBackStack(null)
                                .commit();
                    }
                }, 300);
            }
        });*/

        refreshLayout = view.findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.primaryPurple),
                getResources().getColor(R.color.primaryOrange),
                getResources().getColor(R.color.lightgreen));

        if (listItemOzets.size()==0)
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                parseJSON();
            }
        });

        bottomNav = view.findViewById(R.id.nav_odevler);

        recycleView_ozet.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recycleView_ozet, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        navigation.setSelectedItemId(R.id.nav_odevler);
                        OdevlerFragment nextFrag = new OdevlerFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, nextFrag, "frag")
                                .addToBackStack(null)
                                .commit();
                    }
                }, 300);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        return view;

    }

    public void parseJSON() {

        refreshLayout.setRefreshing(true);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
        Log.e("URL", Islevsel.ogrenciOzetURL);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                Islevsel.ogrenciOzetURL,
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
                                listItemOzets.clear();
                                for (int i = 0; i < res.getJSONArray("odevler").length(); i++)
                                    listItemOzets.add(new ListItemOzet(res.getJSONArray("odevler").getJSONObject(i).getString("name"), res.getJSONArray("odevler").getJSONObject(i).getInt("test") + " Test", res.getJSONArray("odevler").getJSONObject(i).getInt("soru") + " Soru", res.getJSONArray("odevler").getJSONObject(i).getInt("id")));

                                cozulenSoru.setText(res.getInt("cozulenSoru") + "");
                                yapilmamisTest.setText(res.getInt("yapilmamisTest") + " Test");
                                yapilmamisSoru.setText(res.getInt("yapilmamisSoru") + " Soru");
                                enYakinOdev.setText(res.getString("enYakinDers"));
                                enYakinOdevTarih.setText(res.getString("enYakinTarih"));
                                progressBar.setProgress(res.getInt("cozulenYuzde"));
                                yuzde.setText("%" + res.getInt("cozulenYuzde"));

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
                        Log.e("ERROR ", error.toString());

                        Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                        toast.show();
                        isEmpty();
                        refreshLayout.setRefreshing(false);
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

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public void isEmpty() {
        if (listItemOzets.isEmpty()) {
            odevYokView.setVisibility(View.VISIBLE);
        } else
            odevYokView.setVisibility(View.GONE);
    }

}