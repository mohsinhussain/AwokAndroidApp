package com.awok.moshin.awok.Models;

/**
 * Created by shon on 9/14/2015.
 */
public class Checkout {

    public String OverViewTitle;
    public String OverViewText;
    public String SellerLabel;
    public String imageBitmapString;

    public Checkout()
    {

    }

    public Checkout(String OverViewTitle, String OverViewText,String SellerLabel,String imageBitmapString) {
        this.OverViewTitle = OverViewTitle;
        this.OverViewText = OverViewText;
        this.SellerLabel=SellerLabel;
        this.imageBitmapString=imageBitmapString;

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


}
