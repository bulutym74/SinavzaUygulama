package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import java.util.ArrayList;

public class KitapEkleDersItem {

    private String dersAdi="";
    private ArrayList<KitapEkleKitapItem> kitaplar;
    private int id = 0;

    public KitapEkleDersItem(String dersAdi, ArrayList<KitapEkleKitapItem> kitaplar,int id) {
        this.dersAdi = dersAdi;
        this.kitaplar = kitaplar;
        this.id = id;
    }

    public String getDersAdi() {
        return dersAdi;
    }

    public void setDersAdi(String dersAdi) {
        this.dersAdi = dersAdi;
    }

    public ArrayList<KitapEkleKitapItem> getKitaplar() {
        return kitaplar;
    }

    public void setKitaplar(ArrayList<KitapEkleKitapItem> kitaplar) {
        this.kitaplar = kitaplar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
