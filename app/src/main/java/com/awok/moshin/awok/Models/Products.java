package com.awok.moshin.awok.Models;

/**
 * Created by moshin on 9/7/2015.
 */

public class Products {

    String id;
    String name;
    String image;
    int priceNew;
    int priceOld;
    int discPercent;

    public Products() {
    }

    public Products(String id, String name, String image, int priceNew, int priceOld, int discPercent) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.priceNew = priceNew;
        this.priceOld = priceOld;
        this.discPercent = discPercent;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPriceNew() {
        String price = Integer.toString(priceNew);
        price = price+" AED";
        return price;
    }

    public void setPriceNew(int priceNew) {
        this.priceNew = priceNew;
    }

    public String getPriceOld() {
        String price = Integer.toString(priceOld);
        price = price+" AED";
        return price;
    }

    public void setPriceOld(int priceOld) {
        this.priceOld = priceOld;
    }


    public int getDiscPercent() {
        return discPercent;
    }

    public void setDiscPercent(int discPercent) {
        this.discPercent = discPercent;
    }


}
