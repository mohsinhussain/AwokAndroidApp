package com.awok.moshin.awok.Models;

/**
 * Created by shon on 9/10/2015.
 */
public class ProductOverview {
    public String OverViewTitle;
    public String OverViewText;

    public ProductOverview()
    {

    }

    public ProductOverview(String OverViewTitle, String OverViewText) {
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

