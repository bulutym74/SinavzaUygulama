package com.example.fethi.sinavzauygulama.activities;

import org.json.JSONArray;
import org.json.JSONObject;

public class OgretmenListItemFiltre {
    private String name;
    private boolean selected = false;
    private int id;

    public OgretmenListItemFiltre(String name, int id){
        this.name = name;
        this.id  = id;
    }
    public OgretmenListItemFiltre(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void changeSelected() {
        selected = !selected;
    }
    public void setSelected(boolean selected){
        this.selected = selected;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
