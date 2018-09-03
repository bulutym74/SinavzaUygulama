package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fethi.sinavzauygulama.R;

import java.util.List;

public class OgretmenListItemSiniflarAdapter extends RecyclerView.Adapter<OgretmenListItemSiniflarAdapter.ViewHolder> {

    private List<SinifItem> seciliSiniflar;
    private Context context;

    public OgretmenListItemSiniflarAdapter(List<SinifItem> seciliSiniflar, Context context) {
        this.seciliSiniflar = seciliSiniflar;
        this.context = context;
    }

    @NonNull
    @Override
    public OgretmenListItemSiniflarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.o_list_item_siniflar,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final OgretmenListItemSiniflarAdapter.ViewHolder holder, int position) {
        SinifItem sinifItem = seciliSiniflar.get(position);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        ViewGroup.LayoutParams params = holder.card_sinif.getLayoutParams();
        params.width = width/4-20;
        holder.card_sinif.setLayoutParams(params);


        holder.sinif.setText(""+sinifItem.getSinifAdi());
        holder.sol.setText(""+sinifItem.getOgrenciler().size());
        holder.sag.setText(""+sinifItem.getOgrenciSayisi());
    }

    @Override
    public int getItemCount() {
        return seciliSiniflar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView sinif,sol,sag;
        LinearLayout card_sinif;

        public ViewHolder(View itemView) {
            super(itemView);

            sinif = itemView.findViewById(R.id.tv_sinif);
            sol = itemView.findViewById(R.id.tv_sol);
            sag = itemView.findViewById(R.id.tv_sag);
            card_sinif = itemView.findViewById(R.id.card_sinif);
        }

    }
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
