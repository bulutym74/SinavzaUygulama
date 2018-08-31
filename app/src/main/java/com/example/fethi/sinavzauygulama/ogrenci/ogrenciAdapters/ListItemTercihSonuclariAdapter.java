package com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fethi.sinavzauygulama.R;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

public class ListItemTercihSonuclariAdapter extends RecyclerView.Adapter<ListItemTercihSonuclariAdapter.ViewHolder> {

    Realm realm = Realm.getDefaultInstance();
    private RealmResults<ListItemTercihSonucları> listItems;
    private Context context;
    boolean visible;

    public ListItemTercihSonuclariAdapter(RealmResults<ListItemTercihSonucları> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tercihsonuclari,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        ListItemTercihSonucları listItemTercihSonuclari = listItems.get(position);

        holder.tv_uni.setText(listItemTercihSonuclari.getUni());
        holder.tv_sehir.setText(listItemTercihSonuclari.getSehir());
        holder.tv_bolum.setText(listItemTercihSonuclari.getBolum());
        holder.tv_unitur.setText(listItemTercihSonuclari.getUniTur());
        holder.tv_alan.setText(listItemTercihSonuclari.getAlan());
        holder.tv_puan.setText(""+listItemTercihSonuclari.getPuan());
        holder.tv_sıralama.setText(""+listItemTercihSonuclari.getSıralama());

        if (listItems.get(position).isSelected()) {

            holder.imgadd.setVisibility(View.INVISIBLE);
            holder.imgcheck.setVisibility(View.VISIBLE);
        }else {

            holder.imgadd.setVisibility(View.VISIBLE);
            holder.imgcheck.setVisibility(View.INVISIBLE);
        }

        holder.imgadd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               degistir(holder,position);
               Toast toast = Toast.makeText(context, "Tercih Listenize Eklendi", Toast.LENGTH_SHORT);
               toast.setGravity(Gravity.BOTTOM, 0, 300);
               toast.show();
           }
       });
        holder.imgcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                degistir(holder,position);
                Toast toast = Toast.makeText(context, "Tercih Listenizden Çıkarıldı", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 300);
                toast.show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_uni,tv_sehir,tv_bolum,tv_unitur,tv_alan,tv_puan,tv_sıralama;
        ImageView imgadd,imgcheck;
        ViewGroup transitionsContainer;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_uni = itemView.findViewById(R.id.tv_uni);
            tv_sehir = itemView.findViewById(R.id.tv_sehir);
            tv_bolum = itemView.findViewById(R.id.tv_bolum);
            tv_unitur = itemView.findViewById(R.id.tv_unitur);
            tv_alan = itemView.findViewById(R.id.tv_alan);
            tv_puan = itemView.findViewById(R.id.tv_puan);
            tv_sıralama = itemView.findViewById(R.id.tv_sıralama);

            imgadd = itemView.findViewById(R.id.playlist_add_img);
            imgcheck = itemView.findViewById(R.id.playlist_check_img);
            transitionsContainer = itemView.findViewById(R.id.transitions_container);
        }
    }

    private void degistir(ViewHolder holder, final int position){
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    try {
                        listItems.get(position).selected = !listItems.get(position).selected;
                    }catch (RealmException e){
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (listItems.get(position).isSelected()) {

            TransitionSet set = new TransitionSet()
                    .addTransition(new Scale(0.7f))
                    .addTransition(new Fade())
                    .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                            new FastOutLinearInInterpolator());

            set.setDuration(250);
            TransitionManager.beginDelayedTransition(holder.transitionsContainer, set);
            holder.imgadd.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
            holder.imgcheck.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);

        } else {

            TransitionSet set = new TransitionSet()
                    .addTransition(new Scale(0.7f))
                    .addTransition(new Fade())
                    .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                            new FastOutLinearInInterpolator());

            set.setDuration(250);
            TransitionManager.beginDelayedTransition(holder.transitionsContainer, set);
            holder.imgcheck.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
            holder.imgadd.setVisibility(visible ? View.INVISIBLE : View.VISIBLE);

        }
    }
}
