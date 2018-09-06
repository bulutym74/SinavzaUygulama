package com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.dahaFazlaTAB.profil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
import com.example.fethi.sinavzauygulama.activities.OgrenciSinifSec;
import com.example.fethi.sinavzauygulama.activities.OgrenciSinifSecAdapter;
import com.example.fethi.sinavzauygulama.activities.UserInfoItem;
import com.example.fethi.sinavzauygulama.diger.Islevsel;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemOzet;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemSinifDegistirAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class SinifDegistirFragment extends Fragment {

    RecyclerView rv_sinifListesi;
    RecyclerView.Adapter adapter;
    ArrayList<String> ogrenciler = new ArrayList<>();
    RecyclerView rv_sinifsec;
    public List<OgrenciSinifSec> siniflar = new ArrayList<>();

    ImageView sinifDegistir;
    public TextView sinifSec,txt_talep;
    public TextView title;

    public CardView card,cardTalep;
    public View back;

    public Button gonder;
    String baslik;

    JSONObject res;
    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sinif_degistir, container, false);

        sinifSec = view.findViewById(R.id.txt_sinifSec);
        txt_talep = view.findViewById(R.id.txt_talep);
        sinifSec.setText("Yeni Sınıfınızı Seçin");
        title = view.findViewById(R.id.title);
        card = view.findViewById(R.id.card);
        cardTalep = view.findViewById(R.id.cardTalep);
        back = view.findViewById(R.id.back);
        gonder = view.findViewById(R.id.btn_gonder);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String sinif = bundle.getString("sinif", "Sınıf Değiştir");
            title.setText(sinif);
        }


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

        rv_sinifListesi = view.findViewById(R.id.rv_sinifListesi);
        rv_sinifListesi.setHasFixedSize(true);
        rv_sinifListesi.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new ListItemSinifDegistirAdapter(ogrenciler,getActivity());
        rv_sinifListesi.setAdapter(adapter);

        //////////////

        rv_sinifsec = view.findViewById(R.id.rv_sinifSec);
        rv_sinifsec.setHasFixedSize(true);
        rv_sinifsec.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new OgrenciSinifSecAdapter(siniflar, getContext(), this,2);
        rv_sinifsec.setAdapter(adapter);


      back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              card.setVisibility(View.GONE);
              cardTalep.setVisibility(View.GONE);
              back.setVisibility(View.GONE);
          }
      });

        sinifDegistir = view.findViewById(R.id.sinifDegistir);
        sinifDegistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Yükleniyor", "Lütfen bekleyin...", true);
                dialog.show();

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                try(Realm realm = Realm.getDefaultInstance()){
                    token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
                }

                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        Islevsel.sinifDegistirURL,
                        new JSONObject(),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {
                                siniflar.clear();
                                dialog.dismiss();
                                try {
                                    if (response.getBoolean("isFailed")) {
                                        Log.e("FAILED : ", response.getString("message"));

                                        Toast toast = Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                                        toast.show();
                                    } else {
                                        res = response;

                                        if (res.getBoolean("talep")){

                                            Animation animShow = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.view_show);
                                            cardTalep.startAnimation(animShow);
                                            cardTalep.setVisibility(View.VISIBLE);
                                            back.setVisibility(View.VISIBLE);
                                            txt_talep.setText("Halihazırda sınıf değişim talebiniz bulunmaktadır");
                                        }else{
                                            for (int i = 0; i < res.getJSONArray("siniflar").length(); i++)
                                                siniflar.add(new OgrenciSinifSec(((JSONObject)res.getJSONArray("siniflar").get(i)).getString("name"),((JSONObject)res.getJSONArray("siniflar").get(i)).getInt("id")));

                                            Animation animShow = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.view_show);
                                            card.startAnimation(animShow);
                                            card.setVisibility(View.VISIBLE);
                                            back.setVisibility(View.VISIBLE);
                                        }
                                    }

                                    adapter.notifyDataSetChanged();
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
            }
        });

        return view;
    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
