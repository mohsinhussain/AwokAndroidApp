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
    int imageWidth;
    boolean loader = false;
    private String xtraImage;
    private boolean isCached;
    int years, months, days, hours, minutes, seconds;
    private String outOfStock;

    public Products() {
        this.loader = false;
        isCached = false;
        years = 0;
        days = 0;
        hours = 0;
        minutes = 0;
        seconds = 0;
    }

    public Products(boolean loader) {
        this.loader = loader;
        isCached = false;
        years = 0;
        days = 0;
        hours = 0;
        minutes = 0;
        seconds = 0;
    }

    public Products(String id, String name, String image, int priceNew, int priceOld, int discPercent, String categoryId, String description,String rating,String ratingCount,int imageHeight, int imageWidth, String xtraImage,String outOfStock) {
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
        this.loader = false;
        this.xtraImage=xtraImage;
        this.outOfStock=outOfStock;
        isCached = false;
        years = 0;
        days = 0;
        hours = 0;
        minutes = 0;
        seconds = 0;
        this.imageWidth = imageWidth;
    }

    public int getImageHeight(Context mContext) {
//        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
//        int densityDpi = (int)(metrics.density * 160f);
//        Log.v("Products", "desnsity: " + densityDpi);
//        int dp = Math.round(imageHeight / (metrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public boolean isCached() {
        return isCached;
    }

    public void setIsCached(boolean isCached) {
        this.isCached = isCached;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public boolean isLoader() {
        return loader;
    }

    public void setLoader(boolean loader) {
        this.loader = loader;
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

    public void setXtraImage(String xtraImage) {
        this.xtraImage = xtraImage;
    }




    public String getXtraImage() {
        return xtraImage;
    }


    public void setOutOfStock(String outOfStock) {
        this.outOfStock = outOfStock;
    }

    public String getOutOfStock() {
        return outOfStock;
    }
}
