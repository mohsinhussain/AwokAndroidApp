package com.awok.moshin.awok.Models;

/**
 * Created by shon on 2/3/2016.
 */
public class ShippingReturnModel {
    public String specTitle;
    public String specValue;


    public ShippingReturnModel()
    {

    }



    public ShippingReturnModel(String specTitle, String specValue) {
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
