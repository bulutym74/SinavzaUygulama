package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import android.content.Context;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
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
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import java.util.ArrayList;

public class OgretmenExpLVAdapterKitapDetay extends BaseExpandableListAdapter {

    ArrayList<KitapEkleDersItem> dersler;
    public Context context;
    public LayoutInflater inflater;
    ImageView img_arrow;
    boolean visible;

    public OgretmenExpLVAdapterKitapDetay(Context context,ArrayList<KitapEkleDersItem> dersler)
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
        KitapEkleDersItem dersItem = (KitapEkleDersItem)getGroup(groupPosition);

        final GroupViewHolder viewHolder;
        if(view == null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.o_listview_header_kitap_detay,null);
        }
        viewHolder = new GroupViewHolder();

        viewHolder.textGroup = view.findViewById(R.id.txt_parent);
        img_arrow = view.findViewById(R.id.img_arrow);

        viewHolder.textGroup.setText(dersItem.getDersAdi());

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
        final KitapEkleKitapItem kitapItem = (KitapEkleKitapItem)getChild(groupPosition, childPosition);

        final ChildViewHolder viewHolder;
        if(view==null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.o_listview_child_kitap_detay, null);

        }
        viewHolder = new ChildViewHolder();

        viewHolder.transitionsContainer = view.findViewById(R.id.transitions_container);
        viewHolder.add = view.findViewById(R.id.add_img);
        viewHolder.check = view.findViewById(R.id.check_img);
        viewHolder.img_half = view.findViewById(R.id.img_half);
        viewHolder.img_full = view.findViewById(R.id.img_full);
        viewHolder.img_empty = view.findViewById(R.id.img_empty);
        viewHolder.kitapAdi = view.findViewById(R.id.tv_kitapAdi);
        viewHolder.yayinAdi = view.findViewById(R.id.tv_yayinAdi);
        viewHolder.ISBN = view.findViewById(R.id.tv_ISBN);
        viewHolder.baski = view.findViewById(R.id.tv_baski);
        viewHolder.icerdigiDersler = view.findViewById(R.id.tv_icerdigiDersler);

        viewHolder.kitapAdi.setText(kitapItem.getKitapAdi());
        viewHolder.yayinAdi.setText(kitapItem.getYayinAdi());
        viewHolder.ISBN.setText(kitapItem.getISBN());
        viewHolder.baski.setText(kitapItem.getBaski());
        viewHolder.icerdigiDersler.setText(kitapItem.getIcerdigiDersler());

        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kitapItem.setSelected(true);

                TransitionSet set = new TransitionSet()
                        .addTransition(new Scale(0.7f))
                        .addTransition(new Fade())
                        .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                                new FastOutLinearInInterpolator());

                set.setDuration(150);
                TransitionManager.beginDelayedTransition(viewHolder.transitionsContainer, set);
                viewHolder.add.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                viewHolder.check.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);

                notifyDataSetChanged();
            }
        });
        viewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kitapItem.setSelected(false);

                TransitionSet set = new TransitionSet()
                        .addTransition(new Scale(0.7f))
                        .addTransition(new Fade())
                        .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                                new FastOutLinearInInterpolator());

                set.setDuration(150);
                TransitionManager.beginDelayedTransition(viewHolder.transitionsContainer, set);
                viewHolder.check.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                viewHolder.add.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);

                notifyDataSetChanged();

            }
        });

        switch (kitapItem.getStatus()) {
            case 0:
                viewHolder.img_empty.setVisibility(View.VISIBLE);
                viewHolder.img_half.setVisibility(View.GONE);
                viewHolder.img_full.setVisibility(View.GONE);
                break;
            case 1:
                viewHolder.img_half.setVisibility(View.VISIBLE);
                viewHolder.img_empty.setVisibility(View.GONE);
                viewHolder.img_full.setVisibility(View.GONE);
                break;
            case 2:
                viewHolder.img_full.setVisibility(View.VISIBLE);
                viewHolder.img_half.setVisibility(View.GONE);
                viewHolder.img_empty.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        if (kitapItem.isSelected()) {
            viewHolder.add.setVisibility(View.GONE);
            viewHolder.check.setVisibility(View.VISIBLE);

        } else {
            viewHolder.add.setVisibility(View.VISIBLE);
            viewHolder.check.setVisibility(View.GONE);
        }

        return view;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupViewHolder {
        TextView textGroup;
    }

    static class ChildViewHolder {
        ImageView add, check;
        ImageView img_half, img_full, img_empty;
        TextView kitapAdi,yayinAdi,ISBN,baski,icerdigiDersler;
        ViewGroup transitionsContainer;

    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
