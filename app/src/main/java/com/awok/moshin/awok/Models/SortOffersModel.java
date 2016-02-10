package com.awok.moshin.awok.Models;

/**
 * Created by shon on 2/1/2016.
 */
public class SortOffersModel {
    private String title;
    private String key;
    private String value;

    public SortOffersModel()
    {

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SortOffersModel(String title,String key,String value)
    {
        this.title=title;
        this.key=key;
        this.value=value;



    }
}
