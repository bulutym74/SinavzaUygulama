package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.danismanOgrencilerim.OgretmenDanOnayBekleyenOdevlerFragment;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import java.util.ArrayList;

public class OgretmenExpLVAdapterOnayBekleyenOdevler extends BaseExpandableListAdapter {

    private ArrayList<OdevOnaylaDersItem> dersler;

    public Context context;
    public LayoutInflater inflater;
    ImageView img_arrow;
    boolean visible;

    public OgretmenExpLVAdapterOnayBekleyenOdevler(Context context, ArrayList<OdevOnaylaDersItem> dersler) {
        this.context = context;
        this.dersler = dersler;
    }

    @Override
    public int getGroupCount() {
        return dersler.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return dersler.get(groupPosition).getOdevler().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dersler.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return dersler.get(groupPosition).getOdevler().get(childPosition);

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
        final OdevOnaylaDersItem dersItem = (OdevOnaylaDersItem) getGroup(groupPosition);

        final GroupViewHolder viewHolder;

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.o_listview_header_onay_bekleyen_odevler, null);
        }
        viewHolder = new GroupViewHolder();

        viewHolder.add_big = view.findViewById(R.id.add_img_big);
        viewHolder.check_big = view.findViewById(R.id.check_img_big);
        viewHolder.textGroup = view.findViewById(R.id.txt_parent);
        viewHolder.sol = view.findViewById(R.id.sol);
        viewHolder.sag = view.findViewById(R.id.sag);
        viewHolder.transitionsContainer = view.findViewById(R.id.transitions_container);

        viewHolder.sag.setText("" + dersItem.getOdevler().size());

        int a = 0;
        for (OdevOnaylaOdevItem odev : dersItem.getOdevler()) {
            if (odev.isSelected()) {
                a++;
            }
        }
        viewHolder.sol.setText("" + a);

        viewHolder.add_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (OdevOnaylaOdevItem odev : dersItem.getOdevler())
                    odev.setSelected(true);

                dersItem.setSelected(true);

                TransitionSet set = new TransitionSet()
                        .addTransition(new Scale(0.7f))
                        .addTransition(new Fade())
                        .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                                new FastOutLinearInInterpolator());

                set.setDuration(150);
                TransitionManager.beginDelayedTransition(viewHolder.transitionsContainer, set);
                viewHolder.add_big.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                viewHolder.check_big.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);

                notifyDataSetChanged();
            }
        });
        viewHolder.check_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (OdevOnaylaOdevItem odev : dersItem.getOdevler())
                    odev.setSelected(false);

                dersItem.setSelected(false);

                TransitionSet set = new TransitionSet()
                        .addTransition(new Scale(0.7f))
                        .addTransition(new Fade())
                        .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                                new FastOutLinearInInterpolator());

                set.setDuration(150);
                TransitionManager.beginDelayedTransition(viewHolder.transitionsContainer, set);
                viewHolder.check_big.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                viewHolder.add_big.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);

                notifyDataSetChanged();

            }
        });

        img_arrow = view.findViewById(R.id.img_arrow);

        viewHolder.textGroup.setText(dersItem.getDersAdi());

        if (isExpanded) {
            img_arrow.setImageResource(R.drawable.ic_arrow_up);
        } else {
            img_arrow.setImageResource(R.drawable.ic_arrow_down);
        }

        if (dersItem.getSelected()) {
            viewHolder.add_big.setVisibility(View.GONE);
            viewHolder.check_big.setVisibility(View.VISIBLE);

        } else {
            viewHolder.add_big.setVisibility(View.VISIBLE);
            viewHolder.check_big.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        final OdevOnaylaOdevItem odevItem = (OdevOnaylaOdevItem) getChild(groupPosition, childPosition);
        final OdevOnaylaDersItem dersItem = dersler.get(groupPosition);

        final ChildViewHolder viewHolder;

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.o_listview_child_onay_bekleyen_odevler, null);
        }

        viewHolder = new ChildViewHolder();

        viewHolder.tv_testAdi = view.findViewById(R.id.tv_testAdi);
        viewHolder.tv_kitapAdi = view.findViewById(R.id.tv_kitapAdi);
        viewHolder.tv_konuAdi = view.findViewById(R.id.tv_konuAdi);
        viewHolder.add = view.findViewById(R.id.add_img);
        viewHolder.check = view.findViewById(R.id.check_img);
        viewHolder.transitionsContainer = view.findViewById(R.id.transitions_container);

        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                odevItem.setSelected(true);

                Boolean allSelected = true;
                for (OdevOnaylaOdevItem odev : dersItem.getOdevler()) {
                    if (!odev.isSelected()) {
                        allSelected = false;
                        break;
                    }
                }
                if (allSelected) {
                    dersItem.setSelected(true);
                }

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

                odevItem.setSelected(false);

                if (dersItem.getSelected()) {
                    dersItem.setSelected(false);
                }

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

        viewHolder.tv_testAdi.setText(odevItem.getTestAdi());
        viewHolder.tv_kitapAdi.setText(odevItem.getKitapAdi());
        viewHolder.tv_konuAdi.setText(odevItem.getKonuAdi());

        if (odevItem.isSelected()) {
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

        return true;
    }

    static class GroupViewHolder {
        TextView textGroup;
        ImageView add_big, check_big;
        TextView sol, sag;
        ViewGroup transitionsContainer;
    }

    static class ChildViewHolder {
        TextView tv_testAdi;
        TextView tv_kitapAdi;
        TextView tv_konuAdi;
        ImageView add, check;
        ViewGroup transitionsContainer;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }



}


