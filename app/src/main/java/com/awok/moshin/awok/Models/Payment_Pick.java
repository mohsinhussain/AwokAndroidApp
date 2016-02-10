package com.awok.moshin.awok.Models;

/**
 * Created by shon on 1/26/2016.
 */
public class Payment_Pick {
    private String name;
    private String id;

    boolean selected = false;

    public Payment_Pick() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Payment_Pick(String name,String id,boolean selected) {
        this.name = name;
        this.id=id;


        this.selected=selected;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
