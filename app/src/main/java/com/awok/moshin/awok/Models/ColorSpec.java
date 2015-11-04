package com.awok.moshin.awok.Models;

/**
 * Created by mohsin on 10/31/2015.
 */
public class ColorSpec {
    String color;
    String imageUrl;
    boolean isSelected = false;

    public ColorSpec(String color, String imageUrl, boolean isSelected){
        this.color = color;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
