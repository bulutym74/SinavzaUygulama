package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

public class ListItemYeniOgrenciOnayla {

    private String isim;
    private String sinif;
    private String bolum;
    private String ogrNo;
    private int id;

    public ListItemYeniOgrenciOnayla(String isim, String sinif, String bolum, String ogrNo, int id) {
        this.isim = isim;
        this.sinif = sinif;
        this.bolum = bolum;
        this.ogrNo = ogrNo;
        this.id = id;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSinif() {
        return sinif;
    }

    public void setSinif(String sinif) {
        this.sinif = sinif;
    }

    public String getBolum() {
        return bolum;
    }

    public void setBolum(String bolum) {
        this.bolum = bolum;
    }

    public String getOgrNo() {
        return ogrNo;
    }

    public void setOgrNo(String ogrNo) {
        this.ogrNo = ogrNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
