package com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fethi.sinavzauygulama.R;

import java.util.List;

public class ListItemHorizontalAdapter extends RecyclerView.Adapter<ListItemHorizontalAdapter.MyViewHolder> {

    List<String> horizontalList;
    Context context;


    public ListItemHorizontalAdapter(List<String> horizontalList, Context context) {
        this.horizontalList = horizontalList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtview;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtview = itemView.findViewById(R.id.txtview);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizantaltext_tercihyap, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txtview.setText(horizontalList.get(position));

    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }


}
