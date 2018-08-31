package com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters;

import java.util.ArrayList;

public class KitapItem {

    private String kitapAdi;
    private ArrayList<KonuItem> konular;
    private int id;
    private int soruSayisi;
    private int cozulenSoru;

    public KitapItem(String kitapAdi,ArrayList<KonuItem> konular) {
        this.kitapAdi = kitapAdi;
        this.konular = konular;
    }

    public KitapItem(String kitapAdi, int soruSayisi, int cozulenSoru) {
        this.kitapAdi = kitapAdi;
        this.soruSayisi = soruSayisi;
        this.cozulenSoru = cozulenSoru;
    }

    public KitapItem(String kitapAdi, int soruSayisi) {
        this.kitapAdi = kitapAdi;
        this.soruSayisi = soruSayisi;
    }

    public String getKitapAdi() {
        return kitapAdi;
    }

    public void setKitapAdi(String kitapAdi) {
        this.kitapAdi = kitapAdi;
    }

    public ArrayList<KonuItem> getKonular() {
        return konular;
    }

    public void setKonular(ArrayList<KonuItem> konular) {
        this.konular = konular;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoruSayisi() {
        return soruSayisi;
    }

    public void setSoruSayisi(int soruSayisi) {
        this.soruSayisi = soruSayisi;
    }

    public int getCozulenSoru() {
        return cozulenSoru;
    }

    public void setCozulenSoru(int cozulenSoru) {
        this.cozulenSoru = cozulenSoru;
    }
}
