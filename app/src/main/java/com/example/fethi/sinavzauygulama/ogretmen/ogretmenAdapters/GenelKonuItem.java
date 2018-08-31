package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import java.util.ArrayList;

public class GenelKonuItem {
    private String konuAdi = "";
    private ArrayList<GenelKitapItem> bulunduguKitaplar = new ArrayList<>();

    public GenelKonuItem(String konuAdi) {
        this.konuAdi = konuAdi;
    }

    public String getKonuAdi() { return konuAdi; }

    public void setKonuAdi(String konuAdi) {
        this.konuAdi = konuAdi;
    }

    public ArrayList<GenelKitapItem> getBulunduguKitaplar() {
        return bulunduguKitaplar;
    }

    public void setBulunduguKitaplar(ArrayList<GenelKitapItem> bulunduguKitaplar) {
        this.bulunduguKitaplar = bulunduguKitaplar;
    }
}
