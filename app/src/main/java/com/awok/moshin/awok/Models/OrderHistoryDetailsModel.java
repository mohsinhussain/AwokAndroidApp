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
    private String seller;
    private String shipping;
    private String shippingStatus;
    private String productName;
    private String productUniqueId;
    private String estimated_days_from;
    private String estimated_days_to;
    private String intentPrice;


    public OrderHistoryDetailsModel()
    {

    }

    public OrderHistoryDetailsModel(String image,String estimated_days_from  ,String intentPrice,String estimated_days_to, String price,String quantity,String title,String seller, String shipping,String shippingStatus,String delTime,String productName,String productUniqueId) {
        this.image = image;
        this.price = price;
        this.quantity=quantity;
        this.title=title;
        this.orderTime=orderTime;
        this.delTime=delTime;
        this.seller=seller;
        this.shipping=shipping;
        this.shippingStatus=shippingStatus;
        this.delTime=delTime;
        this.productName=productName;
        this.productUniqueId=productUniqueId;
        this.estimated_days_to=estimated_days_to;
        this.intentPrice=intentPrice;







        this.estimated_days_from=estimated_days_from;


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




    public void setSeller(String seller) {
        this.seller = seller;
    }






    public String getSeller()
    {
        return seller;
    }




    public void setShipping(String shipping) {
        this.shipping = shipping;
    }






    public String getShipping()
    {
        return shipping;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }







    public String getShippingStatus()
    {
        return shippingStatus;
    }

    public void setDelTime(String delTime) {
        this.delTime = delTime;
    }

    public String getDelTime()
    {
        return delTime;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductUniqueId(String productUniqueId) {
        this.productUniqueId = productUniqueId;
    }

    public String getProductUniqueId()
    {
        return productUniqueId;
    }

    public void setEstimated_days_from(String estimated_days_from) {
        this.estimated_days_from = estimated_days_from;
    }







    public String getEstimated_days_from()
    {
        return estimated_days_from;
    }

    public void setEstimated_days_to(String estimated_days_to) {
        this.estimated_days_to = estimated_days_to;
    }

    public String getEstimated_days_to()
    {
        return estimated_days_to;
    }

    public void setIntentPrice(String intentPrice) {
        this.intentPrice = intentPrice;
    }





    public String getIntentPrice()
    {
        return intentPrice;
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
