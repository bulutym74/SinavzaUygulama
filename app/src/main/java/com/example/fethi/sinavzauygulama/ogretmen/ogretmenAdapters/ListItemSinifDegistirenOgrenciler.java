package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

public class ListItemSinifDegistirenOgrenciler {
    private String isim;
    private String sinif1;
    private String sinif2;
    private int id;

    public ListItemSinifDegistirenOgrenciler(String isim, String sinif1, String sinif2, int id) {
        this.isim = isim;
        this.sinif1 = sinif1;
        this.sinif2 = sinif2;
        this.id = id;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSinif1() {
        return sinif1;
    }

    public void setSinif1(String sinif1) {
        this.sinif1 = sinif1;
    }

    public String getSinif2() {
        return sinif2;
    }

    public void setSinif2(String sinif2) {
        this.sinif2 = sinif2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
