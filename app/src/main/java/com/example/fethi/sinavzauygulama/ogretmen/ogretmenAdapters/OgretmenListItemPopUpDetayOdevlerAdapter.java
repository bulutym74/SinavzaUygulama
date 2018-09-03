package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

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

public class OgretmenListItemPopUpDetayOdevlerAdapter extends RecyclerView.Adapter<OgretmenListItemPopUpDetayOdevlerAdapter.ViewHolder> {

    private ArrayList<KitapEkleKitapItem> genelKitaplar;
    public Context context;

    public OgretmenListItemPopUpDetayOdevlerAdapter(ArrayList<KitapEkleKitapItem> genelKitaplar, Context context) {
        this.genelKitaplar = genelKitaplar;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.o_listview_child_kitap_detay, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KitapEkleKitapItem genelKitapItem = genelKitaplar.get(position);

        holder.add_img.setVisibility(View.INVISIBLE);
        holder.kitapAdi.setText(genelKitapItem.getKitapAdi());
        holder.yayinAdi.setText(genelKitapItem.getYayinAdi());
        holder.ISBN.setText(genelKitapItem.getISBN());
        holder.baski.setText(genelKitapItem.getBaski());
        holder.icerdigiDersler.setText(genelKitapItem.getIcerdigiDersler());


        switch (genelKitapItem.getStatus()) {
            case 0:
                holder.img_empty.setVisibility(View.VISIBLE);
                holder.img_half.setVisibility(View.GONE);
                holder.img_full.setVisibility(View.GONE);
                break;
            case 1:
                holder.img_half.setVisibility(View.VISIBLE);
                holder.img_empty.setVisibility(View.GONE);
                holder.img_full.setVisibility(View.GONE);
                break;
            case 2:
                holder.img_full.setVisibility(View.VISIBLE);
                holder.img_half.setVisibility(View.GONE);
                holder.img_empty.setVisibility(View.GONE);
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return genelKitaplar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView kitapAdi, yayinAdi, ISBN, baski, icerdigiDersler;
        ImageView add_img;
        ImageView img_half, img_full, img_empty;

        public ViewHolder(View itemView) {
            super(itemView);

            kitapAdi = itemView.findViewById(R.id.tv_kitapAdi);
            yayinAdi = itemView.findViewById(R.id.tv_yayinAdi);
            ISBN = itemView.findViewById(R.id.tv_ISBN);
            baski = itemView.findViewById(R.id.tv_baski);
            icerdigiDersler = itemView.findViewById(R.id.tv_icerdigiDersler);
            add_img = itemView.findViewById(R.id.add_img);
            img_half = itemView.findViewById(R.id.img_half);
            img_full = itemView.findViewById(R.id.img_full);
            img_empty = itemView.findViewById(R.id.img_empty);

        }
    }
}

