package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters.TestItem;

import java.util.ArrayList;

public class GecikenKonuItem {

    private String konuAdi;
    private int soruSayisi;

    public GecikenKonuItem(String konuAdi, int soruSayisi) {
        this.konuAdi = konuAdi;
        this.soruSayisi = soruSayisi;
    }

    public String getKonuAdi() {
        return konuAdi;
    }

    public void setKonuAdi(String konuAdi) {
        this.konuAdi = konuAdi;
    }

    public int getSoruSayisi() {
        return soruSayisi;
    }

    public void setSoruSayisi(int soruSayisi) {
        this.soruSayisi = soruSayisi;
    }
}
