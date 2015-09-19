package com.awok.moshin.awok.Models;

/**
 * Created by shon on 9/14/2015.
 */
public class Checkout {

    public String OverViewTitle;
    public String OverViewText;

    public Checkout()
    {

    }

    public Checkout(String OverViewTitle, String OverViewText) {
        this.OverViewTitle = OverViewTitle;
        this.OverViewText = OverViewText;

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




}
