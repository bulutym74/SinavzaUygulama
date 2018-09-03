package com.example.fethi.sinavzauygulama.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemFiltre;
import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.ListItemFiltreAdapter;

import java.util.ArrayList;

public class OgretmenListItemFiltreAdapter extends RecyclerView.Adapter<OgretmenListItemFiltreAdapter.ViewHolder> {

    Context c;
    ArrayList<OgretmenListItemFiltre> filtreList;

    public OgretmenListItemFiltreAdapter(Context c, ArrayList<OgretmenListItemFiltre> filtreList){
        this.c = c;
        this.filtreList = filtreList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.a_ogretmen_sinifsec_filtre_model,parent,false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(filtreList.get(position).getName());

        if(filtreList.get(position).isSelected()) {

            holder.add_img.setVisibility(View.INVISIBLE);
            holder.check_img.setVisibility(View.VISIBLE);
        }else {
            holder.add_img.setVisibility(View.VISIBLE);
            holder.check_img.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return filtreList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView add_img;
        ImageView check_img;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            add_img = itemView.findViewById(R.id.add_img);
            check_img = itemView.findViewById(R.id.check_img);
        }
    }

    public void filterList(ArrayList<OgretmenListItemFiltre> filtreList) {
        this.filtreList = filtreList;
        notifyDataSetChanged();
    }
}
