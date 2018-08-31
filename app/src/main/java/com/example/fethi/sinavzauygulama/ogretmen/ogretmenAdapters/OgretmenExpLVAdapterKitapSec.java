package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.DersItem;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.KitapItem;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.KonuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OgretmenExpLVAdapterKitapSec extends BaseExpandableListAdapter {

    ArrayList<OdevSecDersItem> dersler;
    public Context context;
    public LayoutInflater inflater;
    ImageView img_arrow;

    public OgretmenExpLVAdapterKitapSec(Context context, ArrayList<OdevSecDersItem> dersler) {
        this.context = context;
        this.dersler = dersler;
    }

    @Override
    public int getGroupCount() {
        return dersler.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return dersler.get(groupPosition).getKitaplar().size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return dersler.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return dersler.get(groupPosition).getKitaplar().get(childPosition);
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
        OdevSecDersItem dersItem = (OdevSecDersItem) getGroup(groupPosition);

        final GroupViewHolder viewHolder;

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.o_listview_header_kitapsec, null);
        }
        viewHolder = new GroupViewHolder();

        viewHolder.son_group = view.findViewById(R.id.son_eleman_group);
        int b = dpToPx(10);
        if (groupPosition == 0) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) viewHolder.son_group.getLayoutParams();
            params.topMargin = b;
        }

        viewHolder.txt = view.findViewById(R.id.txt_parent);
        img_arrow = view.findViewById(R.id.img_arrow);

        viewHolder.txt.setText(dersItem.getDersAdi());

        if (isExpanded) {
            img_arrow.setImageResource(R.drawable.ic_arrow_up);
        } else {
            img_arrow.setImageResource(R.drawable.ic_arrow_down);
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        OdevSecKitapItem kitapItem = (OdevSecKitapItem) getChild(groupPosition, childPosition);

        final ChildViewHolder viewHolder;

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.o_listview_child_kitapsec, null);

        }

        viewHolder = new ChildViewHolder();

        viewHolder.txt_child = view.findViewById(R.id.txt_items);
        viewHolder.testSayiKitap = view.findViewById(R.id.testSayiKitap);

        int seciliTest = 0;
        for (OdevSecKonuItem konu : kitapItem.getKonular()) {
            for (OdevSecTestItem test : konu.getTestler()) {
                if (test.isSelected()) {
                    seciliTest++;
                }
            }
        }
        if (seciliTest != 0) {
            viewHolder.testSayiKitap.setText(seciliTest + " Test");
        } else {
            viewHolder.testSayiKitap.setText("");
        }

        viewHolder.txt_child.setText(kitapItem.getKitapAdi());
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    static class GroupViewHolder {
        TextView txt;
        LinearLayout son_group;
    }

    static class ChildViewHolder {
        TextView txt_child;
        TextView testSayiKitap;

    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
