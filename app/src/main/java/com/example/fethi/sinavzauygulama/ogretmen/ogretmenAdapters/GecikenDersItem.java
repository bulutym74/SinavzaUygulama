package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import java.util.ArrayList;

public class GecikenDersItem {

    private String dersAdi;
    private ArrayList<GecikenKonuItem> konular;
    private int soruSayisi;

    public GecikenDersItem(String dersAdi, ArrayList<GecikenKonuItem> konular) {
        this.dersAdi = dersAdi;
        this.konular = konular;
    }

    public String getDersAdi() {
        return dersAdi;
    }

    public void setDersAdi(String dersAdi) {
        this.dersAdi = dersAdi;
    }

    public ArrayList<GecikenKonuItem> getKonular() {
        return konular;
    }

    public void setKonular(ArrayList<GecikenKonuItem> konular) {
        this.konular = konular;
    }

    public int getSoruSayisi() {
        return soruSayisi;
    }

    public void setSoruSayisi(int soruSayisi) {
        this.soruSayisi = soruSayisi;
    }
}
