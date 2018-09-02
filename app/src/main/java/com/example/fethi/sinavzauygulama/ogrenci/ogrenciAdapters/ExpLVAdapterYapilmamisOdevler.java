package com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters;

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

import java.util.ArrayList;

public class ExpLVAdapterYapilmamisOdevler extends BaseExpandableListAdapter {

    ArrayList<DersItem> dersler;
    public Context context;
    private TextView txt;
    private TextView txt_child;
    public LayoutInflater inflater;
    ImageView img_arrow;


    public ExpLVAdapterYapilmamisOdevler(Context context,ArrayList<DersItem> dersler)
    {
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
        DersItem dersItem = (DersItem)getGroup(groupPosition);


        if(view == null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_header_yapilmamis_odevler,null);
        }

        txt = view.findViewById(R.id.txt_parent);
        img_arrow = view.findViewById(R.id.img_arrow);

        txt.setText(dersItem.getDersAdi());

        if (isExpanded) {
            img_arrow.setImageResource(R.drawable.ic_arrow_up);
        }
        else {
            img_arrow.setImageResource(R.drawable.ic_arrow_down);
        }

        return view;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        KitapItem kitapItem = (KitapItem)getChild(groupPosition, childPosition);
        if(view==null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_child_yapilmamis_odevler, null);

        }

        txt_child = view.findViewById(R.id.txt_items);
        txt_child.setText(kitapItem.getKitapAdi());
        return view;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
