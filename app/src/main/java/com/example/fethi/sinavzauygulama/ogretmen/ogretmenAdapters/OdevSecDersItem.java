package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import java.util.ArrayList;

public class OdevSecDersItem {
    private String dersAdi="";
    private ArrayList<OdevSecKitapItem> kitaplar;
    private int id = 0;

    public OdevSecDersItem(String dersAdi, ArrayList<OdevSecKitapItem> kitaplar) {
        this.dersAdi = dersAdi;
        this.kitaplar = kitaplar;
    }

    public String getDersAdi() {
        return dersAdi;
    }

    public void setDersAdi(String dersAdi) {
        this.dersAdi = dersAdi;
    }

    public ArrayList<OdevSecKitapItem> getKitaplar() {
        return kitaplar;
    }

    public void setKitaplar(ArrayList<OdevSecKitapItem> kitaplar) {
        this.kitaplar = kitaplar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
