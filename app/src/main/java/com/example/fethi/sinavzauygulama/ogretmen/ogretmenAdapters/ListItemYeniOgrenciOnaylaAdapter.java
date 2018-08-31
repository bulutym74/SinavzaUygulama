package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.yeniOgrenciOnayla.OgretmenDanYeniOgrenciOnaylaFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class ListItemYeniOgrenciOnaylaAdapter extends RecyclerView.Adapter<ListItemYeniOgrenciOnaylaAdapter.ViewHolder> {

    private List<ListItemYeniOgrenciOnayla> listItemYeniOgrenciOnayla;
    private Context context;

    JSONObject res;
    String token;
    Realm realm = Realm.getDefaultInstance();
    OgretmenDanYeniOgrenciOnaylaFragment fragment;

    public ListItemYeniOgrenciOnaylaAdapter(List<ListItemYeniOgrenciOnayla> listItemYeniOgrenciOnayla, Context context, OgretmenDanYeniOgrenciOnaylaFragment fragment) {
        this.listItemYeniOgrenciOnayla = listItemYeniOgrenciOnayla;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.o_list_item_yeni_ogrenciler, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        ListItemYeniOgrenciOnayla yeniOgrenciler = listItemYeniOgrenciOnayla.get(position);

        holder.isim.setText(yeniOgrenciler.getIsim());
        holder.sinif.setText(yeniOgrenciler.getSinif());
        holder.bolum.setText(yeniOgrenciler.getBolum());
        holder.ogrenciNo.setText(yeniOgrenciler.getOgrNo());

        holder.reddet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowConfirmDialogReddet(context, listItemYeniOgrenciOnayla.get(position));
            }
        });
        holder.onayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowConfirmDialogOnayla(context, listItemYeniOgrenciOnayla.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItemYeniOgrenciOnayla.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView isim, sinif, bolum, ogrenciNo;
        private Button onayla, reddet;

        public ViewHolder(View itemView) {
            super(itemView);

            isim = itemView.findViewById(R.id.tv_isim);
            sinif = itemView.findViewById(R.id.tv_sinif);
            bolum = itemView.findViewById(R.id.tv_bolum);
            ogrenciNo = itemView.findViewById(R.id.tv_ogrenciNo);
            onayla = itemView.findViewById(R.id.btn_onayla);
            reddet = itemView.findViewById(R.id.btn_reddet);

        }
    }

    private void ShowConfirmDialogReddet(Context context, final ListItemYeniOgrenciOnayla seciliOgrenci) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage("Reddetmek istediğinize emin misiniz?")
                .setCancelable(true)
                .setPositiveButton("REDDET", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        final JSONObject parameters = new JSONObject();

                        try {
                            parameters.accumulate("id", seciliOgrenci.getId());
                            parameters.accumulate("onay", false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                        token = realm.where(UserInfoItem.class).findAll().get(0).getToken();

                        JsonObjectRequest objectRequest = new JsonObjectRequest(
                                Request.Method.POST,
                                Islevsel.ogrenciOnayRedURL,
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

                                                Toast toast = Toast.makeText(getApplicationContext(), "Ögrenci reddedildi", Toast.LENGTH_SHORT);
                                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                                toast.show();

                                                listItemYeniOgrenciOnayla.remove(seciliOgrenci);
                                            }
                                            fragment.isEmpty();
                                            notifyDataSetChanged();
                                        } catch (JSONException e) {
                                            fragment.isEmpty();
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        fragment.isEmpty();
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
                })
                .setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void ShowConfirmDialogOnayla(final Context context, final ListItemYeniOgrenciOnayla seciliOgrenci) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage("Onaylamak istediğinize emin misiniz?")
                .setCancelable(true)
                .setPositiveButton("ONAYLA", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        final JSONObject parameters = new JSONObject();

                        try {
                            parameters.accumulate("id", seciliOgrenci.getId());
                            parameters.accumulate("onay", true);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                        token = realm.where(UserInfoItem.class).findAll().get(0).getToken();

                        JsonObjectRequest objectRequest = new JsonObjectRequest(
                                Request.Method.POST,
                                Islevsel.ogrenciOnayRedURL,
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

                                                Toast toast = Toast.makeText(getApplicationContext(), "Öğrenci onaylandı", Toast.LENGTH_SHORT);
                                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                                toast.show();

                                                listItemYeniOgrenciOnayla.remove(seciliOgrenci);

                                            }
                                            fragment.isEmpty();
                                            notifyDataSetChanged();
                                        } catch (JSONException e) {
                                            fragment.isEmpty();
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        fragment.isEmpty();
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
                })
                .setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}

