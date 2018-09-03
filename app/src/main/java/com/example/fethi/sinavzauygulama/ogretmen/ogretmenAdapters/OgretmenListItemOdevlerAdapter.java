package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fethi.sinavzauygulama.R;

import java.util.List;

public class OgretmenListItemOdevlerAdapter extends RecyclerView.Adapter<OgretmenListItemOdevlerAdapter.ViewHolder> {

    private List<GenelKonuItem> genelKonular;
    private Context context;

    public OgretmenListItemOdevlerAdapter(List<GenelKonuItem> genelKonular, Context context) {
        this.genelKonular = genelKonular;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.o_list_item_odevler,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GenelKonuItem genelKonuItem = genelKonular.get(position);

        holder.konu.setText(genelKonuItem.getKonuAdi());
        int testSayisi = 0;
        for (GenelKitapItem kitap : genelKonuItem.getBulunduguKitaplar())
            testSayisi += kitap.getTestSayisi();
        holder.test.setText(""+testSayisi+" Test");

    }

    @Override
    public int getItemCount() {
        return genelKonular.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView konu,test;
        private LinearLayout son;

        public ViewHolder(View itemView) {
            super(itemView);

            konu = itemView.findViewById(R.id.tv_konu);
            test = itemView.findViewById(R.id.tv_test);
            son = itemView.findViewById(R.id.son_eleman);
        }
    }
}
