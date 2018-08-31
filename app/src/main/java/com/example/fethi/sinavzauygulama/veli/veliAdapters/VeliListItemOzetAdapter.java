package com.example.fethi.sinavzauygulama.veli.veliAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fethi.sinavzauygulama.R;

import java.util.List;

public class VeliListItemOzetAdapter extends RecyclerView.Adapter<VeliListItemOzetAdapter.ViewHolder> {

    private List<VeliListItemOzet> listItemOzets;
    private Context context;

    public VeliListItemOzetAdapter(List<VeliListItemOzet> listItemOzets, Context context) {
        this.listItemOzets = listItemOzets;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_list_item_ozet,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        VeliListItemOzet listItemOzet = listItemOzets.get(position);

        holder.tv_ders.setText(listItemOzet.getDers());
        holder.tv_test.setText(""+listItemOzet.getTest());
        holder.tv_soru.setText(""+listItemOzet.getSoru());

    }

    @Override
    public int getItemCount() { return listItemOzets.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_ders;
        public TextView tv_test;
        public TextView tv_soru;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_ders = itemView.findViewById(R.id.tv_ders);
            tv_test = itemView.findViewById(R.id.tv_test);
            tv_soru = itemView.findViewById(R.id.tv_soru);
        }
    }
}