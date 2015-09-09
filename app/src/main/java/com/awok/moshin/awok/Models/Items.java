package com.awok.moshin.awok.Models;

/**
 * Created by moshin on 9/7/2015.
 */

public class Items {

    String id;
    String name;
    String image;
    int priceNew;
    int priceOld;
    int discount;
    int discPercent;
    String y;
    String m;
    String d;
    String h;
    String i;
    String s;
    int inDays;
    int endTime;

    public Items() {
    }

    public Items(String id, String name, String image, int priceNew, int priceOld, int discount, int discPercent, String y, String m,
                 String d, String h, String i, String s, int inDays, int endTime) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.priceNew = priceNew;
        this.priceOld = priceOld;
        this.discount = discount;
        this.discPercent = discPercent;
        this.y = y;
        this.m = m;
        this.d = d;
        this.h = h;
        this.i = i;
        this.s = s;
        this.inDays = inDays;
        this.endTime = endTime;

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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDiscPercent() {
        return discPercent;
    }

    public void setDiscPercent(int discPercent) {
        this.discPercent = discPercent;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public int getInDays() {
        return inDays;
    }

    public void setInDays(int inDays) {
        this.inDays = inDays;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}
