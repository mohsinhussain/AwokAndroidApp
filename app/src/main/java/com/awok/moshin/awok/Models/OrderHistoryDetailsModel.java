package com.awok.moshin.awok.Models;

/**
 * Created by shon on 9/30/2015.
 */
public class OrderHistoryDetailsModel {


    public String image;
    public String price;
    public String quantity;
    private String title;
    private String    orderTime;
    private String delTime;


    public OrderHistoryDetailsModel()
    {

    }

    public OrderHistoryDetailsModel(String image, String price,String quantity,String title) {
        this.image = image;
        this.price = price;
        this.quantity=quantity;
        this.title=title;
        this.orderTime=orderTime;
        this.delTime=delTime;


    }


    public void setImage(String image)
    {
        this.image=image;
    }





    public void setPrice(String price)
    {
        this.price=price;
    }



    public void setQuantity(String quantity)
    {
        this.quantity=quantity;
    }







    public String getImage()
    {
        return image;
    }


    public String getPrice()
    {
        return price;
    }






    public String getQuantity()
    {
        return quantity;
    }


    public void setTitle(String title) {
        this.title = title;
    }






    public String getTitle()
    {
        return title;
    }

/*    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }




    public String getOrderTime()
    {
        return orderTime;
    }





    public void setDelTime(String delTime) {
        this.delTime = delTime;
    }




    public String getDelTime()
    {
        return delTime;
    }*/




}
