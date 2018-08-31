package com.example.fethi.sinavzauygulama.ogretmen.ogretmenAdapters;

public class OgretmenListItemOdevlendir {

    private String name;
    private boolean selected = false;

    public OgretmenListItemOdevlendir(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void changeSelected() {
        selected = !selected;
    }
}
