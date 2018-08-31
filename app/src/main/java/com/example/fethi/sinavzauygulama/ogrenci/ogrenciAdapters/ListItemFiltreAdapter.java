package com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fethi.sinavzauygulama.R;

import java.util.ArrayList;

public class ListItemFiltreAdapter extends RecyclerView.Adapter<ListItemFiltreAdapter.ViewHolder> {

    Context c;
    ArrayList<ListItemFiltre> filtreList;

    public ListItemFiltreAdapter(Context ctx, ArrayList<ListItemFiltre> filtres){
        this.c = ctx;
        this.filtreList = filtres;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filtre_model,parent,false);


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

    public void filterList(ArrayList<ListItemFiltre> filtreList) {
        this.filtreList = filtreList;
        notifyDataSetChanged();
    }
}
