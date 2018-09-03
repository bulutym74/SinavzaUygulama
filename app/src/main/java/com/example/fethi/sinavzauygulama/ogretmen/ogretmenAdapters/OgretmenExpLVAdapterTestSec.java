package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.example.fethi.sinavzauygulama.activities.OgrenciUyeOlKurumluFragment;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.KitapItem;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.TestItem;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.bransOgretmeniTAB.odevlendir.OgretmenBraTestSecFragment;
import com.example.fethi.sinavzauygulama.ogretmen.ogretmenFragments.danismanOgretmenTAB.danismanOdevlendir.OgretmenDanTestSecFragment;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OgretmenExpLVAdapterTestSec extends BaseExpandableListAdapter {

    private OdevSecKitapItem seciliKitap;

    public Context context;
    public LayoutInflater inflater;
    ImageView img_arrow;
    boolean visible;
    private Fragment fragment;
    int durum;

    public OgretmenExpLVAdapterTestSec(Context context, OdevSecKitapItem seciliKitap, Fragment fragment, int durum) {
        this.context = context;
        this.seciliKitap = seciliKitap;
        this.fragment = fragment;
        this.durum = durum;
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
        final OdevSecKonuItem konuItem = (OdevSecKonuItem) getGroup(groupPosition);

        final GroupViewHolder viewHolder;

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.o_listview_header_testsec, null);
        }
        viewHolder = new GroupViewHolder();

        viewHolder.add_big = view.findViewById(R.id.add_img_big);
        viewHolder.check_big = view.findViewById(R.id.check_img_big);
        viewHolder.img_full = view.findViewById(R.id.img_full);
        viewHolder.textGroup = view.findViewById(R.id.txt_parent);
        viewHolder.sol = view.findViewById(R.id.sol);
        viewHolder.sag = view.findViewById(R.id.sag);
        viewHolder.transitionsContainer = view.findViewById(R.id.transitions_container);

        viewHolder.sag.setText("" + konuItem.getTestler().size());

        int a = 0;
        for (OdevSecTestItem test : konuItem.getTestler()) {
            if (test.isSelected()) {
                a++;
            }
        }
        viewHolder.sol.setText("" + a);

        viewHolder.add_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (OdevSecTestItem test : konuItem.getTestler()) {
                    if (test.getStatus() == 2)
                        test.setSelected(false);
                    else
                        test.setSelected(true);
                }


                konuItem.setSelected(true);

                TransitionSet set = new TransitionSet()
                        .addTransition(new Scale(0.7f))
                        .addTransition(new Fade())
                        .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                                new FastOutLinearInInterpolator());

                set.setDuration(150);
                TransitionManager.beginDelayedTransition(viewHolder.transitionsContainer, set);
                viewHolder.add_big.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                viewHolder.check_big.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);

                updateSoru();
                notifyDataSetChanged();

            }
        });
        viewHolder.check_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (OdevSecTestItem test : konuItem.getTestler())
                    test.setSelected(false);

                konuItem.setSelected(false);

                TransitionSet set = new TransitionSet()
                        .addTransition(new Scale(0.7f))
                        .addTransition(new Fade())
                        .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                                new FastOutLinearInInterpolator());

                set.setDuration(150);
                TransitionManager.beginDelayedTransition(viewHolder.transitionsContainer, set);
                viewHolder.check_big.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                viewHolder.add_big.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);

                updateSoru();
                notifyDataSetChanged();

            }
        });


        img_arrow = view.findViewById(R.id.img_arrow);

        viewHolder.textGroup.setText(konuItem.getKonuAdi());

        if (isExpanded) {
            img_arrow.setImageResource(R.drawable.ic_arrow_up);
        } else {
            img_arrow.setImageResource(R.drawable.ic_arrow_down);
        }

        if (konuItem.isSelected()) {
            viewHolder.add_big.setVisibility(View.GONE);
            viewHolder.check_big.setVisibility(View.VISIBLE);
            viewHolder.img_full.setVisibility(View.GONE);

        } else {
            viewHolder.add_big.setVisibility(View.VISIBLE);
            viewHolder.check_big.setVisibility(View.GONE);
            viewHolder.img_full.setVisibility(View.GONE);
        }

        Boolean status2 = true;
        for (OdevSecTestItem test : konuItem.getTestler()) {
            if (test.getStatus() != 2) {
                status2 = false;
                break;
            }
        }
        if (status2) {
            viewHolder.add_big.setVisibility(View.GONE);
            viewHolder.check_big.setVisibility(View.GONE);
            viewHolder.img_full.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        final OdevSecTestItem testItem = (OdevSecTestItem) getChild(groupPosition, childPosition);
        final OdevSecKonuItem konuItem = (OdevSecKonuItem) getGroup(groupPosition);

        final ChildViewHolder viewHolder;

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.o_listview_child_testsec, null);
        }

        viewHolder = new ChildViewHolder();

        viewHolder.textChild = view.findViewById(R.id.txt_items);
        viewHolder.add = view.findViewById(R.id.add_img);
        viewHolder.check = view.findViewById(R.id.check_img);
        viewHolder.testSoru = view.findViewById(R.id.testSoru);
        viewHolder.child_test = view.findViewById(R.id.child_test);
        viewHolder.img_half = view.findViewById(R.id.img_half);
        viewHolder.img_full = view.findViewById(R.id.img_full);
        viewHolder.img_empty = view.findViewById(R.id.img_empty);
        viewHolder.transitionsContainer = viewHolder.child_test;
        viewHolder.child_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                testItem.setSelected(!testItem.isSelected());
                if (testItem.isSelected()) {
                    Boolean allSelected = true;
                    for (OdevSecTestItem test : konuItem.getTestler()) {
                        if (!test.isSelected()) {
                            allSelected = false;
                            break;
                        }
                    }
                    if (allSelected) {
                        konuItem.setSelected(true);
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

                } else {
                    if (konuItem.isSelected()) {
                        konuItem.setSelected(false);
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
                }
                updateSoru();
                notifyDataSetChanged();

            }
        });

        switch (testItem.getStatus()) {
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


        viewHolder.textChild.setText(testItem.getTestAdi());
        viewHolder.testSoru.setText("" + testItem.getSoruSayisi() + " Soru");

        if (testItem.isSelected()) {
            viewHolder.add.setVisibility(View.GONE);
            viewHolder.check.setVisibility(View.VISIBLE);

        } else {
            viewHolder.add.setVisibility(View.VISIBLE);
            viewHolder.check.setVisibility(View.GONE);
        }

        if (testItem.getStatus() == 2) {
            viewHolder.add.setVisibility(View.GONE);
            viewHolder.check.setVisibility(View.GONE);
            viewHolder.child_test.setClickable(false);
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return false;
    }

    static class GroupViewHolder {
        TextView textGroup;
        ImageView add_big, check_big,img_full;
        TextView sol, sag;
        ViewGroup transitionsContainer;
    }

    static class ChildViewHolder {
        TextView textChild;
        ImageView add, check;
        TextView testSoru;
        FrameLayout child_test;
        ViewGroup transitionsContainer;
        ImageView img_half, img_full, img_empty;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public void updateSoru() {
        int count = 0;
        for (OdevSecKonuItem konu : seciliKitap.getKonular()) {
            for (OdevSecTestItem test : konu.getTestler()) {
                if (test.isSelected())
                    count += test.getSoruSayisi();
            }
        }
        if (durum == 0)
            ((OgretmenBraTestSecFragment) fragment).secili_soru.setText("" + count);
        else if (durum == 1)
            ((OgretmenDanTestSecFragment) fragment).secili_soru.setText("" + count);
    }

}


