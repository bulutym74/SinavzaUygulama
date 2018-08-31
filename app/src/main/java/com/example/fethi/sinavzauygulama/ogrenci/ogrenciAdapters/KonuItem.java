package com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters;

import java.util.ArrayList;

public class KonuItem {

    private String konuAdi;
    private ArrayList<TestItem> testler;
    private int id;
    private int soruSayisi;

    public KonuItem(String konuAdi, ArrayList<TestItem> testler) {
        this.konuAdi = konuAdi;
        this.testler = testler;
    }
    public KonuItem(String konuAdi,int soruSayisi, ArrayList<TestItem> testler) {
        this.konuAdi = konuAdi;
        this.soruSayisi = soruSayisi;
        this.testler = testler;
    }

    public int getSoruSayisi() {
        return soruSayisi;
    }

    public void setSoruSayisi(int soruSayisi) {
        this.soruSayisi = soruSayisi;
    }

    public String getKonuAdi() {
        return konuAdi;
    }

    public void setKonuAdi(String konuAdi) {
        this.konuAdi = konuAdi;
    }

    public ArrayList<TestItem> getTestler() {
        return testler;
    }

    public void setTestler(ArrayList<TestItem> testler) {
        this.testler = testler;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
