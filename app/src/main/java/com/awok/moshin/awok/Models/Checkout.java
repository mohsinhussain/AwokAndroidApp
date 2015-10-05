package com.awok.moshin.awok.Models;

/**
 * Created by shon on 9/14/2015.
 */
public class Checkout {

    public String OverViewTitle;
    public String OverViewText;
    public String SellerLabel;
    public String imageBitmapString;
    private String productId;
    private String statusId;
    private String quantity;
    private String remainingStock;
    private String totalPrice;

    public Checkout()
    {

    }

    public Checkout(String OverViewTitle, String OverViewText,String SellerLabel,String imageBitmapString,String productId,String statusId,String quantity,String remainingStock,String totalPrice) {
        this.OverViewTitle = OverViewTitle;
        this.OverViewText = OverViewText;
        this.SellerLabel=SellerLabel;
        this.imageBitmapString=imageBitmapString;
        this.productId=productId;
        this.statusId=statusId;
        this.quantity=quantity;
        this.remainingStock=remainingStock;
        this.totalPrice=totalPrice;

    }


    public void setOverViewTitle(String OverViewTitle)
    {
        this.OverViewTitle=OverViewTitle;
    }





    public void setOverViewText(String OverViewText)
    {
        this.OverViewText=OverViewText;
    }





    public String getOverViewTitle()
    {
        return OverViewTitle;
    }


    public String getOverViewText()
    {
        return OverViewText;
    }





    public String getSellerLabel()
    {
        return SellerLabel;
    }


    public void setSellerLabel(String SellerLabel)
    {
        this.SellerLabel=SellerLabel;
    }


    public String getImageBitmapString()
    {
        return imageBitmapString;
    }


    public void setImageBitmapString(String imageBitmapString)
    {
        this.imageBitmapString=imageBitmapString;
    }


    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId()
    {
        return productId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }



    public String getStatusId()
    {
        return statusId;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    public String getQuantity()
    {
        return quantity;
    }

    public void setRemainingStock(String remainingStock) {
        this.remainingStock = remainingStock;
    }


    public String getRemainingStock()
    {
        return remainingStock;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }


    public String getTotalPrice()
    {
        return totalPrice;
    }
}
