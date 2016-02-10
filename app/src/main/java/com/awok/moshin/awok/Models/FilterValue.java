package com.awok.moshin.awok.Models;

/**
 * Created by mohsin on 1/31/2016.
 */
public class FilterValue {
    String title;
    String key;
    String value;
    String checked;
    int sort;

    public FilterValue(String title, String key, String value, String checked, int sort){
        this.title = title;
        this.key = key;
        this.value = value;
        this.checked = checked;
        this.sort = sort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
