package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.KitapItem;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.KonuItem;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.TestItem;

public class OgretmenExpLVAdapterOdevGecmisiKonular extends BaseExpandableListAdapter {

    private KitapItem seciliKitap;
    public Context context;
    public LayoutInflater inflater;

    public OgretmenExpLVAdapterOdevGecmisiKonular(Context context, KitapItem seciliKitap) {
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
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        KonuItem konuItem = (KonuItem) getGroup(groupPosition);

        final GroupViewHolder viewHolder;

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_header_konular, null);
        }
        viewHolder = new GroupViewHolder();

        viewHolder.son_group = view.findViewById(R.id.son_eleman_group);

        int b = dpToPx(10);
        if (groupPosition == 0) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) viewHolder.son_group.getLayoutParams();
            params.topMargin = b;
        }


        viewHolder.txt_parent_konular = view.findViewById(R.id.txt_parent_konular);
        viewHolder.img_arrow = view.findViewById(R.id.img_arrow);

        viewHolder.txt_parent_konular.setText(konuItem.getKonuAdi());

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

        final ChildViewHolder viewHolder;

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.o_listview_child_odev_gecmisi_konular, null);
        }

        viewHolder = new ChildViewHolder();

        viewHolder.txt_items = view.findViewById(R.id.txt_items);
        viewHolder.img_done = view.findViewById(R.id.img_done);
        viewHolder.img_cancel = view.findViewById(R.id.img_cancel);
        viewHolder.img_spinner = view.findViewById(R.id.img_spinner);

        viewHolder.txt_items.setText(testItem.getTestAdi());

        switch (testItem.getStatus()) {
            case 0:
                viewHolder.img_cancel.setVisibility(View.VISIBLE);
                viewHolder.img_done.setVisibility(View.GONE);
                viewHolder.img_spinner.setVisibility(View.GONE);
                break;
            case 1:
                viewHolder.img_done.setVisibility(View.VISIBLE);
                viewHolder.img_cancel.setVisibility(View.GONE);
                viewHolder.img_spinner.setVisibility(View.GONE);
                break;
            case 2:
                viewHolder.img_spinner.setVisibility(View.VISIBLE);
                viewHolder.img_done.setVisibility(View.GONE);
                viewHolder.img_cancel.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return false;
    }

    static class GroupViewHolder {
        TextView txt_parent_konular;
        ImageView img_arrow;
        LinearLayout son_group;
    }

    static class ChildViewHolder {
        TextView txt_items;
        ImageView img_done,img_cancel,img_spinner;

    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


}
