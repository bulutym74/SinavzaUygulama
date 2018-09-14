package com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class ExpLVAdapterYapilmamisKonular extends BaseExpandableListAdapter {

    private KitapItem seciliKitap;
    public Context context;
    public LayoutInflater inflater;

    private String token;

    public ExpLVAdapterYapilmamisKonular(Context context, KitapItem seciliKitap) {
        this.context = context;
        this.seciliKitap = seciliKitap;
    }

    @Override
    public int getGroupCount() {
        return seciliKitap.getKonular().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return seciliKitap.getKonular().get(groupPosition).getTestler().size();

    }

    @Override
    public Object getGroup(int groupPosition) {

        return seciliKitap.getKonular().get(groupPosition);

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return seciliKitap.getKonular().get(groupPosition).getTestler().get(childPosition);

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
        KonuItem konuItem = (KonuItem) getGroup(groupPosition);

        final GroupViewHolder viewHolder;

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_header_yapilmamis_konular, null);
        }
        viewHolder = new GroupViewHolder();

        viewHolder.txt_parent_yapilmamis_konular = view.findViewById(R.id.txt_parent_konular);
        viewHolder.img_arrow = view.findViewById(R.id.img_arrow);

        viewHolder.txt_parent_yapilmamis_konular.setText(konuItem.getKonuAdi());

        if (isExpanded) {
            viewHolder.img_arrow.setImageResource(R.drawable.ic_arrow_up);
        } else {
            viewHolder.img_arrow.setImageResource(R.drawable.ic_arrow_down);
        }

        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View view, final ViewGroup parent) {
        final TestItem testItem = (TestItem) getChild(groupPosition, childPosition);
        final KonuItem konuItem = (KonuItem) getGroup(groupPosition);

        final ChildViewHolder viewHolder;

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_child_yapilmamis_konular, null);
        }

        viewHolder = new ChildViewHolder();

        viewHolder.row = view.findViewById(R.id.row_layout);

        viewHolder.txt_items_yapilmamis_konular = view.findViewById(R.id.txt_items_konular);
        viewHolder.tv_soru = view.findViewById(R.id.tv_soru);
        viewHolder.tv_tarih = view.findViewById(R.id.tv_tarih);
        viewHolder.et_dogru = view.findViewById(R.id.et_dogru);
        viewHolder.et_yanlis = view.findViewById(R.id.et_yanlis);
        viewHolder.btn_gonder = view.findViewById(R.id.btn_gonder);
        viewHolder.img = view.findViewById(R.id.img_cancel_outlined);

        if (testItem.getStatus() == 0)
            viewHolder.img.setVisibility(View.VISIBLE);
        else
            viewHolder.img.setVisibility(View.GONE);

        try {
            viewHolder.et_dogru.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            viewHolder.et_yanlis.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.et_dogru.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals(""))
                    testItem.setDogru(Integer.valueOf(editable.toString()));
                else
                    testItem.setDogru(0);

            }
        });
        viewHolder.et_yanlis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals(""))
                    testItem.setYanlis(Integer.valueOf(editable.toString()));
                else
                    testItem.setYanlis(0);
            }
        });


        viewHolder.btn_gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (viewHolder.et_dogru.getText().toString().isEmpty() && viewHolder.et_yanlis.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Doğru ve yanlış sayılarını giriniz", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 300);
                    toast.show();
                    return;
                }

                if (viewHolder.et_dogru.getText().toString().isEmpty() && viewHolder.et_yanlis.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Doğru ve yanlış sayılarını giriniz", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 300);
                    toast.show();
                    return;
                }

                if (viewHolder.et_yanlis.getText().length() > 0 || viewHolder.et_dogru.getText().length() > 0){
                    int dogru = 0;
                    int yanlis = 0;
                    try {
                        dogru = Integer.valueOf(viewHolder.et_dogru.getText().toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    try {
                        yanlis = Integer.valueOf(viewHolder.et_yanlis.getText().toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if ((dogru + yanlis) > testItem.getSoruSayisi()){

                        Toast toast = Toast.makeText(context, "Doğru-Yanlış Toplamı "+testItem.getSoruSayisi()+" olmalıdır", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                        toast.show();
                        return;
                    }
                }

                final JSONObject parameters = new JSONObject();

                try {
                    parameters.accumulate("Dogru", testItem.getDogru());
                    parameters.accumulate("Yanlis", testItem.getYanlis());
                    parameters.accumulate("OdevId", testItem.getId());
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
                        Islevsel.odevYollaURL,
                        parameters,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {

                                Animation animation = AnimationUtils.loadAnimation(context, R.anim.exit_to_right);
                                animation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {

                                        Toast toast = Toast.makeText(context, konuItem.getTestler().get(childPosition).getTestAdi() + " gönderildi", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                                        toast.show();

                                        konuItem.getTestler().remove(childPosition);

                                        if (konuItem.getTestler().isEmpty()) {

                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                public void run() {
                                                    Toast toast1 = Toast.makeText(context, konuItem.getKonuAdi() + " tamamlandı", Toast.LENGTH_SHORT);
                                                    toast1.setGravity(Gravity.BOTTOM, 0, 300);
                                                    toast1.show();
                                                }
                                            }, 1000);

                                            seciliKitap.getKonular().remove(groupPosition);
                                        }

                                        notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });


                                viewHolder.row.startAnimation(animation);


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
        });

        viewHolder.tv_soru.setText("" + testItem.getSoruSayisi() + " Soru");
        viewHolder.tv_tarih.setText(testItem.getSonTarih());
        viewHolder.txt_items_yapilmamis_konular.setText(testItem.getTestAdi());
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return false;
    }

    static class GroupViewHolder {
        TextView txt_parent_yapilmamis_konular;
        ImageView img_arrow;
    }

    static class ChildViewHolder {
        TextView txt_items_yapilmamis_konular;
        TextView tv_soru, tv_tarih;
        EditText et_dogru, et_yanlis;
        Button btn_gonder;
        LinearLayout row;
        ImageView img;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
