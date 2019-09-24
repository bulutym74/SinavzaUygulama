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
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

public class ListItemTercihListemAdapter extends RecyclerView.Adapter<ListItemTercihListemAdapter.ViewHolder> {

    private Realm realm = Realm.getDefaultInstance();

    private RealmResults<ListItemTercihSonucları> listItems;
    Context context;


    public ListItemTercihListemAdapter(RealmResults<ListItemTercihSonucları> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ListItemTercihListemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tercihsonuclari, parent, false);
        return new ListItemTercihListemAdapter.ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        ListItemTercihSonucları tercihItem = listItems.get(position);

        holder.tv_uni.setText(tercihItem.getUni());
        holder.tv_sehir.setText(tercihItem.getSehir());
        holder.tv_bolum.setText(tercihItem.getBolum());
        holder.tv_unitur.setText(tercihItem.getUniTur());
        holder.tv_alan.setText(tercihItem.getAlan());
        holder.tv_puan.setText("" + tercihItem.getPuan());
        holder.tv_siralama.setText("" + tercihItem.getSiralama());

        if (listItems.get(position).isSelected()) {
            holder.imgadd.setVisibility(View.INVISIBLE);
            holder.imgcheck.setVisibility(View.VISIBLE);
        } else {
            holder.imgadd.setVisibility(View.VISIBLE);
            holder.imgcheck.setVisibility(View.INVISIBLE);
        }

        holder.imgadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                degistir(holder, position);
            }
        });
        holder.imgcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                degistir(holder, position);
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_uni, tv_sehir, tv_bolum, tv_unitur, tv_alan, tv_puan, tv_siralama;
        ImageView imgadd, imgcheck;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_uni = itemView.findViewById(R.id.tv_uni);
            tv_sehir = itemView.findViewById(R.id.tv_sehir);
            tv_bolum = itemView.findViewById(R.id.tv_bolum);
            tv_unitur = itemView.findViewById(R.id.tv_unitur);
            tv_alan = itemView.findViewById(R.id.tv_alan);
            tv_puan = itemView.findViewById(R.id.tv_puan);
            tv_siralama = itemView.findViewById(R.id.tv_siralama);

            imgadd = itemView.findViewById(R.id.playlist_add_img);
            imgcheck = itemView.findViewById(R.id.playlist_check_img);
        }
    }

    private void degistir(ViewHolder holder, final int position) {
        holder.imgadd.setVisibility(View.VISIBLE);
        holder.imgcheck.setVisibility(View.INVISIBLE);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    listItems.get(position).selected = !listItems.get(position).selected;
                } catch (RealmException e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();
            }
        });
    }
}
