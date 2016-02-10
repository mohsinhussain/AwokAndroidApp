package com.awok.moshin.awok.Models;

import android.widget.TextView;

/**
 * Created by shon on 9/10/2015.
 */




public class ProductSpecification {
    public String specTitle;
    public String specValue;
    public String subTitle;
    public String subData;

public ProductSpecification()
{

}

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSubData() {
        return subData;
    }

    public void setSubData(String subData) {
        this.subData = subData;
    }

    public ProductSpecification(String specTitle, String specValue,String subTitle,String subData) {
        this.specTitle = specTitle;
        this.specValue = specValue;
        this.subData=subData;
        this.subTitle=subTitle;


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


