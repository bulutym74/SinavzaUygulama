package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

public class KitapEkleKitapItem {
    private String kitapAdi="";
    private String yayinAdi="";
    private String ISBN="";
    private String baski="";
    private String icerdigiDersler="";
    private int id =0;
    private boolean selected = false;

    public KitapEkleKitapItem(String kitapAdi, String yayinAdi, String ISBN, String baski, String icerdigiDersler,int id) {
        this.kitapAdi = kitapAdi;
        this.yayinAdi = yayinAdi;
        this.ISBN = ISBN;
        this.baski = baski;
        this.icerdigiDersler = icerdigiDersler;
        this.id = id;
    }

    public String getKitapAdi() {
        return kitapAdi;
    }

    public void setKitapAdi(String kitapAdi) {
        this.kitapAdi = kitapAdi;
    }

    public String getYayinAdi() {
        return yayinAdi;
    }

    public void setYayinAdi(String yayinAdi) {
        this.yayinAdi = yayinAdi;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getBaski() {
        return baski;
    }

    public void setBaski(String baski) {
        this.baski = baski;
    }

    public String getIcerdigiDersler() {
        return icerdigiDersler;
    }

    public void setIcerdigiDersler(String icerdigiDersler) {
        this.icerdigiDersler = icerdigiDersler;
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
