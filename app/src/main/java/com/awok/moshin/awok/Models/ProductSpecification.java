package com.awok.moshin.awok.Models;

import android.widget.TextView;

/**
 * Created by shon on 9/10/2015.
 */




public class ProductSpecification {
    public String specTitle;
    public String specValue;

public ProductSpecification()
{

}

    public ProductSpecification(String specTitle, String specValue) {
        this.specTitle = specTitle;
        this.specValue = specValue;

    }


    public void setSpecTitle(String specTitle)
    {
        this.specTitle=specTitle;
    }





    public void setSpecValue(String specValue)
    {
        this.specValue=specValue;
    }





    public String getSpecTitle()
    {
        return specTitle;
    }


    public String getSpecValue()
    {
        return specValue;
    }
}


