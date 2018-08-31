package com.example.fethi.sinavzauygulama.veli.veliAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fethi.sinavzauygulama.R;

import java.util.HashMap;
import java.util.List;

public class VeliExpLVAdapterYapilmamisKonular extends BaseExpandableListAdapter {

    private List<String> list_parent;
    private HashMap<String, List<String>> list_child;
    public Context context;
    private TextView txt;
    private TextView txt_child;
    public LayoutInflater inflater;
    ImageView img_arrow;

    @Override
    public int getGroupCount() {
        return list_parent.size();
    }

    public VeliExpLVAdapterYapilmamisKonular(Context context, List<String> list_parent, HashMap<String, List<String>> list_child)
    {
        this.context = context;
        this.list_parent = list_parent;
        this.list_child = list_child;
    }
    @Override
    public int getChildrenCount(int groupPosition) {

        return list_child.get(list_parent.get(groupPosition)).size();
    }
    @Override
    public Object getGroup(int groupPosition) {

        return list_parent.get(groupPosition);
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return list_child.get(list_parent.get(groupPosition)).get(childPosition);
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
        String title_name = (String)getGroup(groupPosition);

        if(view == null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.v_listview_header_yapilmamis_konular,null);
        }

        txt = view.findViewById(R.id.txt_parent_konular);
        img_arrow = view.findViewById(R.id.img_arrow);

        txt.setText(title_name);

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
        // kaçıncı pozisyonda ise başlığımızın elemanı onun ismini alarak string e atıyoruz
        String txt_child_name = (String)getChild(groupPosition, childPosition);
        if(view==null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.v_listview_child_yapilmamis_konular, null);
            // fonksiyon adından da anlaşılacağı gibi parent a bağlı elemanlarının layoutunu inflate ediyoruz böylece o görüntüye ulaşmış oluyoruz
        }
        /*if(getGroup(groupPosition).toString().equals("GALATASARAY"))
        {
            // Eğer başlığımızın ismi GALATASARAY ise elemanlarının yer aldığı arka plan rengini kırmızı yapıyoruz
            view.setBackgroundColor(Color.RED);
        }
        else if(getGroup(groupPosition).toString().equals("FENERBAHCE"))
        {
            // Eğer başlığımızın ismi FENERBAHCE ise elemanlarının yer aldığı arka plan rengini mavi yapıyoruz
            view.setBackgroundColor(Color.BLUE);
        }*/
        // listview_child ulaştığımıza göre içindeki bileşeni de kullanabiliyoruz daha sonradan view olarak return ediyoruz
        txt_child = view.findViewById(R.id.txt_items_konular);
        txt_child.setText(txt_child_name);
        return view;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return false;  // expandablelistview de tıklama yapabilmek için true olmak zorunda
    }

}

