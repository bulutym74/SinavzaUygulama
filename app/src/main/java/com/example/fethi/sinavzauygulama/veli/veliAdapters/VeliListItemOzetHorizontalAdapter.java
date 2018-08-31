package com.example.fethi.sinavzauygulama.veli.veliAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fethi.sinavzauygulama.R;

import java.util.List;

public class VeliListItemOzetHorizontalAdapter extends RecyclerView.Adapter<VeliListItemOzetHorizontalAdapter.ViewHolder> {

    private List<VeliListItemOzetHorizontal> listItemOzetHorizontals;
    private Context context;


    public VeliListItemOzetHorizontalAdapter(List<VeliListItemOzetHorizontal> listItemOzetHorizontals, Context context) {
        this.listItemOzetHorizontals = listItemOzetHorizontals;
        this.context = context;
    }

    @NonNull
    @Override
    public VeliListItemOzetHorizontalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.v_list_item_ozet_horizontal,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final VeliListItemOzetHorizontalAdapter.ViewHolder holder, int position) {
        VeliListItemOzetHorizontal listItemOzetHorizontal = listItemOzetHorizontals.get(position);

        holder.img.setImageResource(listItemOzetHorizontal.getImg());
        holder.ust.setText(listItemOzetHorizontal.getUst());
        holder.alt.setText(listItemOzetHorizontal.getAlt());

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        ViewGroup.LayoutParams params = holder.layout.getLayoutParams();
        int a=dpToPx(20);
        params.width = width/2-a;
        holder.layout.setLayoutParams(params);

    }

    @Override
    public int getItemCount() {
        return listItemOzetHorizontals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;
        public TextView ust,alt;
        public LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_icon);
            ust = itemView.findViewById(R.id.ozet_hori_ust);
            alt = itemView.findViewById(R.id.info2);
            layout = itemView.findViewById(R.id.lay);

        }

    }

    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
