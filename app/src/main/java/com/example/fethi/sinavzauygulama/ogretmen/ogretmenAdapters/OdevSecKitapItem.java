package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import java.util.ArrayList;

public class OdevSecKitapItem {
    private String kitapAdi;
    private ArrayList<OdevSecKonuItem> konular;
    private int id =0;
    private boolean selected = false;

    public OdevSecKitapItem(String kitapAdi, ArrayList<OdevSecKonuItem> konular) {
        this.kitapAdi = kitapAdi;
        this.konular = konular;
    }

    public String getKitapAdi() {
        return kitapAdi;
    }

    public void setKitapAdi(String kitapAdi) {
        this.kitapAdi = kitapAdi;
    }

    public ArrayList<OdevSecKonuItem> getKonular() {
        return konular;
    }

    public void setKonular(ArrayList<OdevSecKonuItem> konular) {
        this.konular = konular;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
