package com.example.fethi.sinavzauygulama.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
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
import com.example.fethi.sinavzauygulama.diger.Islevsel;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.dahaFazlaTAB.profil.SinifDegistirFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgrenciSinifSecAdapter extends RecyclerView.Adapter<OgrenciSinifSecAdapter.ViewHolder> {

    private List<OgrenciSinifSec> siniflar;
    private Context context;
    private int mSelectedItem = -1;
    Fragment fragment;
    private int durum;

    JSONObject res;
    String token;

    public OgrenciSinifSecAdapter(List<OgrenciSinifSec> siniflar, Context context,Fragment fragment, int durum) {
        this.siniflar = siniflar;
        this.context = context;
        this.fragment = fragment;
        this.durum = durum;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.a_ogrenci_sinifsec_item,parent,false);

        if (durum == 2){
            ((SinifDegistirFragment)fragment).gonder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mSelectedItem != -1){


                        final JSONObject parameters = new JSONObject();

                        try {
                            parameters.accumulate("sinif", siniflar.get(mSelectedItem).getId());

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
                                Islevsel.sinifDegistirURL,
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

                                                ((SinifDegistirFragment)fragment).txt_talep.setText("Sınıf değişim talebiniz işleme alınmıştır");

                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    public void run() {
                                                        ((SinifDegistirFragment)fragment).cardTalep.setVisibility(View.VISIBLE);
                                                    }
                                                }, 200);

                                                handler.postDelayed(new Runnable() {
                                                    public void run() {
                                                        Animation animHide = AnimationUtils.loadAnimation( context, R.anim.view_hide);
                                                        ((SinifDegistirFragment)fragment).cardTalep.startAnimation(animHide);
                                                        ((SinifDegistirFragment)fragment).cardTalep.setVisibility(View.GONE);
                                                        ((SinifDegistirFragment)fragment).card.setVisibility(View.GONE);
                                                        ((SinifDegistirFragment)fragment).back.setVisibility(View.GONE);
                                                    }
                                                }, 1800);
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
            });
        }

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OgrenciSinifSec sinif = siniflar.get(position);

        holder.text.setText(sinif.getSinif());
        holder.mRadio.setChecked(position == mSelectedItem);

    }

    @Override
    public int getItemCount() {
        return siniflar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView text;
        RadioButton mRadio;

        public ViewHolder(View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.text);
            mRadio = itemView.findViewById(R.id.radio);

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            mSelectedItem = getAdapterPosition();

                            if (durum == 0) {
                                ((OgrenciUyeOlKurumluFragment)fragment).secildimi.setText(siniflar.get(mSelectedItem).getSinif());
                                ((OgrenciUyeOlKurumluFragment)fragment).secildimi.setTextColor(context.getResources().getColor(R.color.lightgreen));
                                ((OgrenciUyeOlKurumluFragment)fragment).seciliSinifID = siniflar.get(mSelectedItem).getId();
                            }else if (durum == 1){
                                ((OgrenciUyeOlKurumsuzFragment)fragment).secildimi.setText(siniflar.get(mSelectedItem).getSinif());
                                ((OgrenciUyeOlKurumsuzFragment)fragment).secildimi.setTextColor(context.getResources().getColor(R.color.lightgreen));
                                ((OgrenciUyeOlKurumsuzFragment)fragment).seciliSinifID = siniflar.get(mSelectedItem).getId();
                            }

                            notifyDataSetChanged();
                        }
                    }, 200);

                    Handler handle = new Handler();
                    handle.postDelayed(new Runnable() {
                        public void run() {
                            Animation animHide = AnimationUtils.loadAnimation( context, R.anim.view_hide);

                            if (durum == 0) {
                                ((OgrenciUyeOlKurumluFragment)fragment).card.startAnimation(animHide);
                                ((OgrenciUyeOlKurumluFragment)fragment).card.setVisibility(View.GONE);
                                ((OgrenciUyeOlKurumluFragment)fragment).back.setVisibility(View.GONE);
                                ((OgrenciUyeOlKurumluFragment)fragment).btn_devam.setVisibility(View.VISIBLE);
                            }else if (durum == 1){
                                ((OgrenciUyeOlKurumsuzFragment)fragment).card.startAnimation(animHide);
                                ((OgrenciUyeOlKurumsuzFragment)fragment).card.setVisibility(View.GONE);
                                ((OgrenciUyeOlKurumsuzFragment)fragment).back.setVisibility(View.GONE);
                                ((OgrenciUyeOlKurumsuzFragment)fragment).btn_devam.setVisibility(View.VISIBLE);
                            }
                        }
                    }, 300);


                }
            };
            itemView.setOnClickListener(clickListener);
            mRadio.setOnClickListener(clickListener);
        }
    }
}
