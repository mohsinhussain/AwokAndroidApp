package com.awok.moshin.awok.Models;

/**
 * Created by mohsin on 10/31/2015.
 */
public class SizeSpec {
    String color;
    boolean isSelected = false;

    public SizeSpec(String color, boolean isSelected){
        this.color = color;
        this.isSelected = isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
