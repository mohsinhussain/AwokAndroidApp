package com.awok.moshin.awok.Models;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by moshin on 9/7/2015.
 */

public class Products {
String rating;
    String ratingCount;
    String id;
    String name;
    String image;
    int priceNew;
    int priceOld;
    int discPercent;
    String description;
    String categoryId;
    int imageHeight;

    public Products() {
    }

    public Products(String id, String name, String image, int priceNew, int priceOld, int discPercent, String categoryId, String description,String rating,String ratingCount,int imageHeight) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.priceNew = priceNew;
        this.priceOld = priceOld;
        this.description = description;
        this.discPercent = discPercent;
        this.categoryId = categoryId;
        this.rating=rating;
        this.ratingCount=ratingCount;
        this.imageHeight=imageHeight;
    }

    public int getImageHeight(Context mContext) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int densityDpi = (int)(metrics.density * 160f);
        Log.v("Products", "desnsity: " + densityDpi);
        int dp = Math.round(imageHeight / (metrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
        price = " AED "+price;
        return price;
    }

    public void setPriceNew(int priceNew) {
        this.priceNew = priceNew;
    }

    public String getPriceOld() {
        String price = Integer.toString(priceOld);
        price = "AED "+price;
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

    public void setRating(String rating) {
        this.rating = rating;
    }


    public String getRating() {
        return rating;
    }


    public void setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
    }


    public String getRatingCount() {
        return ratingCount;
    }

}
