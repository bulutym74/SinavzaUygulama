package com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters;

import java.util.ArrayList;

public class DersItem {

    private String dersAdi;
    private ArrayList<KitapItem> kitaplar;
    private int id;

    public DersItem(String dersAdi,ArrayList<KitapItem> kitaplar,int id) {
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

    public ArrayList<KitapItem> getKitaplar() {
        return kitaplar;
    }

    public void setKitaplar(ArrayList<KitapItem> kitaplar) {
        this.kitaplar = kitaplar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
