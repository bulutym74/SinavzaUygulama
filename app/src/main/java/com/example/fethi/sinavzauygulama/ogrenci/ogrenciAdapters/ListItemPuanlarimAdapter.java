package com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.fethi.sinavzauygulama.R;

import java.text.SimpleDateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class ListItemPuanlarimAdapter extends BaseAdapter {

    Realm realm = Realm.getDefaultInstance();
    Context c;
    RealmResults<ListItemPuanlarim> puanlarim;
    LayoutInflater inflater;

    public ListItemPuanlarimAdapter(Context c, RealmResults<ListItemPuanlarim> puanAdim) {
        this.c = c;
        this.puanlarim = puanAdim;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return puanlarim.size();
    }

    @Override
    public Object getItem(int position) {
        return puanlarim.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position,View convertView, ViewGroup parent) {
        
        Holder holder;

        if (convertView == null){

            convertView = LayoutInflater.from(c).inflate(R.layout.list_item_puanlarim,parent,false);
        }

        holder = new Holder();

        holder.puanadiTxt = convertView.findViewById(R.id.tv1);
        holder.tarihTxt = convertView.findViewById(R.id.tv2);
        holder.saatTxt = convertView.findViewById(R.id.tv3);
        holder.delete = convertView.findViewById(R.id.kayit_sil);

        final ListItemPuanlarim p = (ListItemPuanlarim) this.getItem(position);

        SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");   // for current date
        SimpleDateFormat time1 = new SimpleDateFormat("kk:mm");        // for current time

        final String dateString = date1.format(p.getTarih());
        final String timeString1 = time1.format(p.getTarih());

        holder.puanadiTxt.setText(p.getPuanAdi());
        holder.tarihTxt.setText(dateString);
        holder.saatTxt.setText(timeString1);


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowConfirmDialog(c, puanlarim.get(position));

            }
        });

        return convertView;
    }

    private void ShowConfirmDialog(Context context, final ListItemPuanlarim seciliPuan) {
        
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage("Bu kayıt silinecek!")
                .setCancelable(true)
                .setPositiveButton("SİL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                                RealmResults<ListItemPuanlarim> results = realm.where(ListItemPuanlarim.class).equalTo("puanAdi", seciliPuan.getPuanAdi()).findAll();
                                results.deleteAllFromRealm();
                            }
                        });
                    }
                })
                .setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    class Holder {

        TextView puanadiTxt;
        TextView tarihTxt;
        TextView saatTxt;
        Button delete;
    }
}
