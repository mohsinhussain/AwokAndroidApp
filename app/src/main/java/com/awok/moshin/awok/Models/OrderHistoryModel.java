package com.awok.moshin.awok.Models;

/**
 * Created by shon on 9/29/2015.
 */
public class OrderHistoryModel {
    public String title;
    public String quantity;
    public String seller;
    public String price;
    private String totalPrice;
    private String shipping;
    private String imageData;


    public OrderHistoryModel()
    {

    }

    public OrderHistoryModel(String title, String quantity,String seller,String price,String totalPrice,String shipping,String imageData) {
        this.title = title;
        this.quantity = quantity;
        this.seller=seller;
        this.price=price;
        this.totalPrice=totalPrice;
        this.shipping=shipping;
        this.imageData=imageData;



    }


    public void setTitle(String title)
    {
        this.title=title;
    }





    public void setSeller(String seller)
    {
        this.seller=seller;
    }





    public String getTitle()
    {
        return title;
    }


    public String getSeller()
    {
        return seller;
    }







    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    public String getQuantity()
    {
        return quantity;
    }


    public void setShipping(String shipping) {
        this.shipping = shipping;
    }



    public String getShipping()
    {
        return shipping;
    }


    public void setPrice(String price) {
        this.price = price;
    }


    public String getPrice()
    {
        return price;
    }







    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }


    public String getTotalPrice()
    {
        return totalPrice;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }





    public String getImageData()
    {
        return imageData;
    }



}
