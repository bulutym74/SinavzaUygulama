package com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters;

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
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters.OgretmenExpLVAdapterOdevlendir;
import com.transitionseverywhere.Rotate;
import com.transitionseverywhere.TransitionManager;

import java.util.ArrayList;

public class ExpLVAdapterOdevler extends BaseExpandableListAdapter  {

    ArrayList<DersItem> dersler;
    public Context context;
    public LayoutInflater inflater;

    public ExpLVAdapterOdevler(Context context,ArrayList<DersItem> dersler)
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

        final GroupViewHolder viewHolder;
        if(view == null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_header_odevler,null);
        }
        viewHolder = new GroupViewHolder();

        viewHolder.son_group = view.findViewById(R.id.son_eleman_group);

        int b = dpToPx(10);
        if(groupPosition == 0){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) viewHolder.son_group.getLayoutParams();
            params.topMargin = b;
        }

        viewHolder.txt_parent = view.findViewById(R.id.txt_parent);
        viewHolder.img_arrow = view.findViewById(R.id.img_arrow);

        viewHolder.txt_parent.setText(dersItem.getDersAdi());


        if (isExpanded) {
            viewHolder.img_arrow.setImageResource(R.drawable.ic_arrow_up);
        }
        else {
            viewHolder.img_arrow.setImageResource(R.drawable.ic_arrow_down);
        }

        return view;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        KitapItem kitapItem = (KitapItem)getChild(groupPosition, childPosition);

        final ChildViewHolder viewHolder;
        if(view==null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_child_odevler, null);

        }

        viewHolder = new ChildViewHolder();

        viewHolder.txt_child = view.findViewById(R.id.txt_items);
        viewHolder.txt_child.setText(kitapItem.getKitapAdi());
        return view;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }
    static class GroupViewHolder {
        TextView txt_parent;
        ImageView img_arrow;
        LinearLayout son_group;

    }

    static class ChildViewHolder {
        TextView txt_child;


    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
