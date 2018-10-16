package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.sinifDegistirenOgrenciler.OgretmenDanSinifDegistirenOgrencilerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class ListItemSinifDegistirenOgrencilerAdapter extends RecyclerView.Adapter<ListItemSinifDegistirenOgrencilerAdapter.ViewHolder> {

    private List<ListItemSinifDegistirenOgrenciler> listItemSinifDegistirenler;
    private Context context;
    Fragment fragment;

    JSONObject res;
    String token;

    public ListItemSinifDegistirenOgrencilerAdapter(List<ListItemSinifDegistirenOgrenciler> listItemSinifDegistirenler, Context context, Fragment fragment) {
        this.listItemSinifDegistirenler = listItemSinifDegistirenler;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.o_list_item_sinif_degistiren_ogrenciler,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        ListItemSinifDegistirenOgrenciler yeniOgrenciler = listItemSinifDegistirenler.get(position);

        holder.isim.setText(yeniOgrenciler.getIsim());
        holder.sinif1.setText(yeniOgrenciler.getSinif1());
        holder.sinif2.setText(yeniOgrenciler.getSinif2());

        holder.reddet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowConfirmDialogReddet(context, listItemSinifDegistirenler.get(position));
            }
        });
        holder.onayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowConfirmDialogOnayla(context, listItemSinifDegistirenler.get(position));
            }
        });

    }
    @Override
    public int getItemCount() {
        return listItemSinifDegistirenler.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView isim,sinif1,sinif2;
        private Button onayla,reddet;

        public ViewHolder(View itemView) {
            super(itemView);

            isim = itemView.findViewById(R.id.tv_isim);
            sinif1 = itemView.findViewById(R.id.sinif1);
            sinif2 = itemView.findViewById(R.id.sinif2);
            onayla = itemView.findViewById(R.id.btn_onayla);
            reddet = itemView.findViewById(R.id.btn_reddet);

        }
    }
    private void ShowConfirmDialogReddet(Context context, final ListItemSinifDegistirenOgrenciler seciliOgrenci) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage("Reddetmek istediğinize emin misiniz?")
                .setCancelable(true)
                .setPositiveButton("REDDET", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        final JSONObject parameters = new JSONObject();

                        try {
                            parameters.accumulate("id", seciliOgrenci.getId());
                            parameters.accumulate("onay", false);

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
                                Islevsel.sinifDegistirOnayURL,
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

                                                listItemSinifDegistirenler.remove(seciliOgrenci);
                                            }
                                            ((OgretmenDanSinifDegistirenOgrencilerFragment)fragment).isEmpty();
                                            notifyDataSetChanged();
                                        } catch (JSONException e) {
                                            ((OgretmenDanSinifDegistirenOgrencilerFragment)fragment).isEmpty();
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        ((OgretmenDanSinifDegistirenOgrencilerFragment)fragment).isEmpty();
                                        Log.e("ERROR : ", error.toString());

                                        Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                                        toast.show();
                                    }
                                }
                        ){
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
                })
                .setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void ShowConfirmDialogOnayla(Context context, final ListItemSinifDegistirenOgrenciler seciliOgrenci) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage("Onaylamak istediğinize emin misiniz?")
                .setCancelable(true)
                .setPositiveButton("ONAYLA", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        final JSONObject parameters = new JSONObject();

                        try {
                            parameters.accumulate("id", seciliOgrenci.getId());
                            parameters.accumulate("onay", true);

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
                                Islevsel.sinifDegistirOnayURL,
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

                                                listItemSinifDegistirenler.remove(seciliOgrenci);
                                            }
                                            ((OgretmenDanSinifDegistirenOgrencilerFragment)fragment).isEmpty();
                                            notifyDataSetChanged();
                                        } catch (JSONException e) {
                                            ((OgretmenDanSinifDegistirenOgrencilerFragment)fragment).isEmpty();
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        ((OgretmenDanSinifDegistirenOgrencilerFragment)fragment).isEmpty();
                                        Log.e("ERROR : ", error.toString());

                                        Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                                        toast.show();
                                    }
                                }
                        ){
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
                })
                .setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}

