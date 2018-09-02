package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import android.content.Context;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
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

public class OgretmenExpLVAdapterOdevlendir extends BaseExpandableListAdapter {

    private ArrayList<SinifItem> siniflar;

    public Context context;
    public LayoutInflater inflater;
    ImageView img_arrow;
    boolean visible;

    public OgretmenExpLVAdapterOdevlendir(Context context, ArrayList<SinifItem> siniflar) {
        this.context = context;
        this.siniflar = siniflar;
    }

    @Override
    public int getGroupCount() {
        return siniflar.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return siniflar.get(groupPosition).getOgrenciler().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return siniflar.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return siniflar.get(groupPosition).getOgrenciler().get(childPosition);

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
        final SinifItem sinifItem = (SinifItem) getGroup(groupPosition);

        final GroupViewHolder viewHolder;

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.o_listview_header_odevlendir, null);
        }
        viewHolder = new GroupViewHolder();

        viewHolder.son_group = view.findViewById(R.id.son_eleman_group);
        int b = dpToPx(10);
        if (groupPosition == 0) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) viewHolder.son_group.getLayoutParams();
            params.topMargin = b;
        }else {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) viewHolder.son_group.getLayoutParams();
            params.topMargin = 0;
        }

        viewHolder.add_big = view.findViewById(R.id.add_img_big);
        viewHolder.check_big = view.findViewById(R.id.check_img_big);
        viewHolder.textGroup = view.findViewById(R.id.txt_parent);
        viewHolder.sol = view.findViewById(R.id.sol);
        viewHolder.sag = view.findViewById(R.id.sag);
        viewHolder.transitionsContainer = view.findViewById(R.id.transitions_container);

        viewHolder.sag.setText("" + sinifItem.getOgrenciler().size());

        int a = 0;
        for (OgrenciItem ogrenci : sinifItem.getOgrenciler()) {
            if (ogrenci.getSelected()) {
                a++;
            }
        }
        viewHolder.sol.setText("" + a);

        viewHolder.add_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (OgrenciItem ogrenci : sinifItem.getOgrenciler())
                    ogrenci.setSelected(true);

                sinifItem.setSelected(true);

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

                for (OgrenciItem ogrenci : sinifItem.getOgrenciler())
                    ogrenci.setSelected(false);

                sinifItem.setSelected(false);

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

        viewHolder.textGroup.setText(sinifItem.getSinifAdi());

        if (isExpanded) {
            img_arrow.setImageResource(R.drawable.ic_arrow_up);
        } else {
            img_arrow.setImageResource(R.drawable.ic_arrow_down);
        }

        if (sinifItem.getSelected()) {
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
        final OgrenciItem ogrenciItem = (OgrenciItem) getChild(groupPosition, childPosition);
        final SinifItem sinifItem = siniflar.get(groupPosition);

        final ChildViewHolder viewHolder;

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.o_listview_child_odevlendir, null);
        }

        viewHolder = new ChildViewHolder();

        viewHolder.textChild = view.findViewById(R.id.txt_items);
        viewHolder.add = view.findViewById(R.id.add_img);
        viewHolder.check = view.findViewById(R.id.check_img);
        viewHolder.child_ogrenci = view.findViewById(R.id.child_ogrenci);
        viewHolder.son_child = view.findViewById(R.id.son_child);
        viewHolder.transitionsContainer = viewHolder.child_ogrenci;
        viewHolder.child_ogrenci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ogrenciItem.setSelected(!ogrenciItem.getSelected());
                if (ogrenciItem.getSelected()) {
                    Boolean allSelected = true;
                    for (OgrenciItem ogrenci : sinifItem.getOgrenciler()) {
                        if (!ogrenci.getSelected()) {
                            allSelected = false;
                            break;
                        }
                    }
                    if (allSelected) {
                        sinifItem.setSelected(true);
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
                    if (sinifItem.getSelected()) {
                        sinifItem.setSelected(false);
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
                notifyDataSetChanged();
            }
        });


        if (groupPosition == getGroupCount() - 1 && childPosition == getChildrenCount(groupPosition) - 1) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 400);
            viewHolder.son_child.setLayoutParams(params);
        }

        viewHolder.textChild.setText(ogrenciItem.getOgrAdi());

        if (ogrenciItem.getSelected()) {
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
        LinearLayout son_group;
        ViewGroup transitionsContainer;
    }

    static class ChildViewHolder {
        TextView textChild;
        ImageView add, check;
        FrameLayout child_ogrenci;
        ViewGroup transitionsContainer;
        LinearLayout son_child;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}


