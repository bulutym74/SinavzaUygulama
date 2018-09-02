package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.DersItem;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.KitapItem;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.KonuItem;

import java.util.ArrayList;

public class OgretmenExpLVAdapterGecikenOdevler extends BaseExpandableListAdapter {

    ArrayList<GecikenDersItem> dersler;
    public Context context;
    private TextView txt;
    private TextView txt_child;
    public LayoutInflater inflater;
    ImageView img_arrow;

    TextView sag, top_sag;


    public OgretmenExpLVAdapterGecikenOdevler(Context context, ArrayList<GecikenDersItem> dersler) {
        this.context = context;
        this.dersler = dersler;
    }

    @Override
    public int getGroupCount() {
        return dersler.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return dersler.get(groupPosition).getKonular().size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return dersler.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return dersler.get(groupPosition).getKonular().get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        GecikenDersItem dersItem = (GecikenDersItem) getGroup(groupPosition);

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.o_listview_header_geciken_odevler, null);
        }

        txt = view.findViewById(R.id.txt_parent);
        img_arrow = view.findViewById(R.id.img_arrow);
        top_sag = view.findViewById(R.id.top_sag);

        txt.setText(dersItem.getDersAdi());

        if (isExpanded) {
            img_arrow.setImageResource(R.drawable.ic_arrow_up);
        } else {
            img_arrow.setImageResource(R.drawable.ic_arrow_down);
        }

        int topSag = 0;
        for (GecikenKonuItem konu : dersItem.getKonular()) {
            topSag += konu.getSoruSayisi();
        }
        top_sag.setText(topSag+" Soru");
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        GecikenKonuItem konuItem = (GecikenKonuItem) getChild(groupPosition, childPosition);


        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.o_listview_child_geciken_odevler, null);

        }
        txt_child = view.findViewById(R.id.txt_items);
        txt_child.setText(konuItem.getKonuAdi());

        sag = view.findViewById(R.id.sag);

        sag.setText(konuItem.getSoruSayisi()+" Soru");

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return false;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}

