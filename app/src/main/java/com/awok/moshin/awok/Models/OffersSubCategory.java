package com.awok.moshin.awok.Models;

/**
 * Created by shon on 1/30/2016.
 */
public class OffersSubCategory {

    private String title;
    private String key;
    private String value;

    public OffersSubCategory()
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

    public OffersSubCategory(String title,String key,String value)
    {
                this.title=title;
        this.key=key;
        this.value=value;

    }
}
