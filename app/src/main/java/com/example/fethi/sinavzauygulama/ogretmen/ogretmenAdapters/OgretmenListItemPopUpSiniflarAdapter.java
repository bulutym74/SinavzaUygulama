package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fethi.sinavzauygulama.R;

import java.util.ArrayList;
import java.util.List;

public class OgretmenListItemPopUpSiniflarAdapter extends RecyclerView.Adapter<OgretmenListItemPopUpSiniflarAdapter.ViewHolder> {

    private ArrayList<OgrenciItem> ogrenciler;
    private Context context;

    public OgretmenListItemPopUpSiniflarAdapter(ArrayList<OgrenciItem> ogrenciler, Context context) {
        this.ogrenciler = ogrenciler;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.o_list_item_popup_siniflar,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OgrenciItem ogrenciItem = ogrenciler.get(position);

        holder.isim.setText(ogrenciItem.getOgrAdi());
    }

    @Override
    public int getItemCount() {
        return ogrenciler.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView isim;

        public ViewHolder(View itemView) {
            super(itemView);

            isim = itemView.findViewById(R.id.tv_popup_siniflar_isim);
        }
    }
}
