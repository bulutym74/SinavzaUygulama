package com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.exceptions.RealmException;

public class ListItemTercihSonucları extends RealmObject {

    private String uni;
    private String sehir;
    private String bolum;
    private String uniTur;
    private String alan;
    private Double puan;
    private int siralama;
    public boolean selected = false;

    public ListItemTercihSonucları() {}

    public ListItemTercihSonucları(String uni, String sehir, String bolum, String uniTur, String alan, Double puan, int siralama) {

        this.uni = uni;
        this.sehir = sehir;
        this.bolum = bolum;
        this.uniTur = uniTur;
        this.alan = alan;
        this.puan = puan;
        this.siralama = siralama;
    }

    public String getUni() {
        return uni;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }

    public String getSehir() {
        return sehir;
    }

    public void setSehir(String sehir) {
        this.sehir = sehir;
    }

    public String getBolum() {
        return bolum;
    }

    public void setBolum(String bolum) {
        this.bolum = bolum;
    }

    public String getUniTur() {
        return uniTur;
    }

    public void setUniTur(String uniTur) {
        this.uniTur = uniTur;
    }

    public String getAlan() {
        return alan;
    }

    public void setAlan(String alan) {
        this.alan = alan;
    }

    public Double getPuan() {
        return puan;
    }

    public void setPuan(Double puan) {
        this.puan = puan;
    }

    public int getSiralama() {
        return siralama;
    }

    public void setSiralama(int siralama) {
        this.siralama = siralama;
    }

    public boolean isSelected() {
        return selected;
    }

    public void changeSelected() {
        selected = !selected;

    }


}
