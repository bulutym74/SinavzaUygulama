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

public class ExpLVAdapterCozulenler extends BaseExpandableListAdapter {

    ArrayList<DersItem> dersler;
    public Context context;
    private TextView txt;
    private TextView txt_child;
    public LayoutInflater inflater;
    ImageView img_arrow;
    LinearLayout son_group;

    TextView sol, sag, top_sol, top_sag;


    public ExpLVAdapterCozulenler(Context context, ArrayList<DersItem> dersler) {
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
        DersItem dersItem = (DersItem) getGroup(groupPosition);

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_header_cozulenler, null);
        }
        son_group = view.findViewById(R.id.son_eleman_group);

        int b = dpToPx(10);
        if (groupPosition == 0) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) son_group.getLayoutParams();
            params.topMargin = b;
        }

        txt = view.findViewById(R.id.txt_parent);
        img_arrow = view.findViewById(R.id.img_arrow);
        top_sol = view.findViewById(R.id.top_sol);
        top_sag = view.findViewById(R.id.top_sag);

        txt.setText(dersItem.getDersAdi());

        if (isExpanded) {
            img_arrow.setImageResource(R.drawable.ic_arrow_up);
        } else {
            img_arrow.setImageResource(R.drawable.ic_arrow_down);
        }
        int topSol = 0;
        int topSag = 0;
        for (KitapItem kitap : dersItem.getKitaplar()) {
            topSol += kitap.getCozulenSoru();
            topSag += kitap.getSoruSayisi();
        }
        top_sol.setText("" + topSol);
        top_sag.setText("" + topSag);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        KitapItem kitapItem = (KitapItem) getChild(groupPosition, childPosition);


        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_child_cozulenler, null);

        }
        txt_child = view.findViewById(R.id.txt_items);
        txt_child.setText(kitapItem.getKitapAdi());

        sol = view.findViewById(R.id.sol);
        sag = view.findViewById(R.id.sag);

        sol.setText("" + kitapItem.getCozulenSoru());
        sag.setText("" + kitapItem.getSoruSayisi());

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

