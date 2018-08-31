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

public class OgretmenListItemPopUpOdevlerAdapter extends RecyclerView.Adapter<OgretmenListItemPopUpOdevlerAdapter.ViewHolder> {

    private ArrayList<GenelKitapItem> genelKitaplar;
    private Context context;

    public OgretmenListItemPopUpOdevlerAdapter(ArrayList<GenelKitapItem> genelKitaplar, Context context) {
        this.genelKitaplar = genelKitaplar;
        this.context = context;
    }

    @NonNull
    @Override
    public OgretmenListItemPopUpOdevlerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.o_list_item_popup_odevler,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OgretmenListItemPopUpOdevlerAdapter.ViewHolder holder, int position) {
        GenelKitapItem genelKitapItem = genelKitaplar.get(position);

        holder.kitap.setText(genelKitapItem.getKitapAdi());
        holder.test.setText(""+genelKitapItem.getTestSayisi());

    }

    @Override
    public int getItemCount() {
        return genelKitaplar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView kitap,test;

        public ViewHolder(View itemView) {
            super(itemView);

            kitap = itemView.findViewById(R.id.tv_popup_odevler_kitapAdı);
            test = itemView.findViewById(R.id.tv_popup_odevler_test);
        }
    }
}

