package com.awok.moshin.awok.Models;

/**
 * Created by shon on 1/31/2016.
 */
public class OffersCatModel {
    private String id="";
    private String name="";
    private String newPrice="";
    private String oldPrice="";
    private String discount="";
    private String percentage="";
    private String hrs="";
    private boolean loader=false;
    private  String message="";
    private Boolean showTimer;
    private String outOfStock="";
    private String catId="";

    public String getOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(String outOfStock) {
        this.outOfStock = outOfStock;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    private String min="";
    private String sec="";
    private String days="";
    private String img="";


    public OffersCatModel()
    {

    }


    public boolean isLoader() {
        return loader;
    }

    public void setLoader(boolean loader) {
        this.loader = loader;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OffersCatModel(boolean loader, String message) {
        this.loader = loader;

        this.message=message;


    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getHrs() {
        return hrs;
    }

    public void setHrs(String hrs) {
        this.hrs = hrs;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public OffersCatModel(String id,String name,String newPrice,String oldPrice,String discount,String percentage,String days,String hrs,String min,String sec,String img,Boolean showTimer,String outOfStock,String catId)
    {
        this.id=id;
        this.name=name;
        this.newPrice=newPrice;
        this.oldPrice=oldPrice;
        this.discount=discount;
        this.percentage=percentage;
        this.hrs=hrs;
        this.min=min;
        this.sec=sec;
        this.img=img;
        this.days=days;
        this.showTimer=showTimer;
        this.outOfStock=outOfStock;
        this.catId=catId;

    }

    public Boolean getShowTimer() {
        return showTimer;
    }

    public void setShowTimer(Boolean showTimer) {
        this.showTimer = showTimer;
    }
}
