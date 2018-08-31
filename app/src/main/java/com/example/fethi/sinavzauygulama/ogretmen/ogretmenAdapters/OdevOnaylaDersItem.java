package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import java.util.ArrayList;

public class OdevOnaylaDersItem {

    private String dersAdi="";
    private ArrayList<OdevOnaylaOdevItem> odevler;
    private int id = 0;
    private Boolean selected = false;

    public OdevOnaylaDersItem(String dersAdi, ArrayList<OdevOnaylaOdevItem> odevler) {
        this.dersAdi = dersAdi;
        this.odevler = odevler;
    }

    public String getDersAdi() {
        return dersAdi;
    }

    public void setDersAdi(String dersAdi) {
        this.dersAdi = dersAdi;
    }

    public ArrayList<OdevOnaylaOdevItem> getOdevler() {
        return odevler;
    }

    public void setOdevler(ArrayList<OdevOnaylaOdevItem> odevler) {
        this.odevler = odevler;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
