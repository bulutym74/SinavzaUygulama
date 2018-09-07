package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

import java.util.ArrayList;

public class SinifItem {

    private String sinifAdi;
    private ArrayList<OgrenciItem> ogrenciler;
    private int ogrenciSayisi;
    private int id;
    private Boolean selected = false;

    public SinifItem(String sinifAdi, ArrayList<OgrenciItem> ogrenci) {
        this.sinifAdi = sinifAdi;
        this.ogrenciler = ogrenci;
    }
    public SinifItem(String sinifAdi, ArrayList<OgrenciItem> ogrenci,int id) {
        this.sinifAdi = sinifAdi;
        this.ogrenciler = ogrenci;
        this.id = id;
    }

    public String getSinifAdi() {
        return sinifAdi;
    }

    public void setSinifAdi(String sinifAdi) {
        this.sinifAdi = sinifAdi;
    }

    public ArrayList<OgrenciItem> getOgrenciler() {
        return ogrenciler;
    }

    public void setOgrenciler(ArrayList<OgrenciItem> ogrenciler) {
        this.ogrenciler = ogrenciler;
    }

    public int getOgrenciSayisi() {
        return ogrenciSayisi;
    }

    public void setOgrenciSayisi(int ogrenciSayisi) {
        this.ogrenciSayisi = ogrenciSayisi;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
