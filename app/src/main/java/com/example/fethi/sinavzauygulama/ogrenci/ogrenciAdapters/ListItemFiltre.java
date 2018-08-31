package com.example.fethi.sinavzauygulama.ogrenci.ogrenciAdapters;

public class ListItemFiltre {

    private String name;
    private boolean selected = false;

    public ListItemFiltre(String name){
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
