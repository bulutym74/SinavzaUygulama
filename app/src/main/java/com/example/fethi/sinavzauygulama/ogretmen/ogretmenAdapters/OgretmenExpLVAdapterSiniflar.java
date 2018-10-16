package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.fethi.sinavzauygulama.diger.App;
import com.example.fethi.sinavzauygulama.diger.Islevsel;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OgretmenExpLVAdapterSiniflar extends BaseExpandableListAdapter {

    private ArrayList<SinifItem> siniflar;

    public Context context;
    public LayoutInflater inflater;
    ImageView img_arrow;

    JSONObject res;
    String token;

    public OgretmenExpLVAdapterSiniflar(Context context, ArrayList<SinifItem> siniflar) {
        this.context = context;
        this.siniflar = siniflar;
    }

    @Override
    public int getGroupCount() {
        return siniflar.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return siniflar.get(groupPosition).getOgrenciler().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return siniflar.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return siniflar.get(groupPosition).getOgrenciler().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;
    }

    @Override
    public boolean hasStableIds() {

        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        final SinifItem sinifItem = (SinifItem) getGroup(groupPosition);

        final GroupViewHolder viewHolder;

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.o_listview_header_siniflar, null);
        }
        viewHolder = new GroupViewHolder();

        viewHolder.textGroup = view.findViewById(R.id.txt_parent);
        viewHolder.txt_ögrenci_sayi = view.findViewById(R.id.txt_ögrenci_sayi);
        viewHolder.delete = view.findViewById(R.id.img_delete);
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowConfirmDialogSil(context, siniflar.get(groupPosition));
            }
        });

        img_arrow = view.findViewById(R.id.img_arrow);

        viewHolder.textGroup.setText(sinifItem.getSinifAdi());
        if (siniflar.get(groupPosition).getOgrenciler().size() != 0)
            viewHolder.txt_ögrenci_sayi.setText(siniflar.get(groupPosition).getOgrenciler().size() + " Öğr.");
        else
            viewHolder.txt_ögrenci_sayi.setText("Öğr. Yok");


        if (isExpanded) {
            img_arrow.setImageResource(R.drawable.ic_arrow_up);
        } else {
            img_arrow.setImageResource(R.drawable.ic_arrow_down);
        }

        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        final OgrenciItem ogrenciItem = (OgrenciItem) getChild(groupPosition, childPosition);

        final ChildViewHolder viewHolder;

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.o_listview_child_siniflar, null);
        }

        viewHolder = new ChildViewHolder();

        viewHolder.textChild = view.findViewById(R.id.txt_items);
        viewHolder.textChild.setText(ogrenciItem.getOgrAdi());

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return false;
    }

    static class GroupViewHolder {
        TextView textGroup,txt_ögrenci_sayi;
        ImageView delete;

    }

    static class ChildViewHolder {
        TextView textChild;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void ShowConfirmDialogSil(Context context, final SinifItem seciliSinif) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage("Silmek istediğinize emin misiniz?")
                .setCancelable(true)
                .setPositiveButton("SİL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        final JSONObject parameters = new JSONObject();

                        try {
                            parameters.accumulate("silinecekSinif", seciliSinif.getId());

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
                                Islevsel.ogretmenSinifSilURL,
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

                                                siniflar.remove(seciliSinif);

                                                Toast toast = Toast.makeText(getApplicationContext(), seciliSinif.getSinifAdi()+" sınıfı silindi", Toast.LENGTH_SHORT);
                                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                                toast.show();

                                                notifyDataSetChanged();
                                            }

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
                        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                                60000,
                                3,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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


