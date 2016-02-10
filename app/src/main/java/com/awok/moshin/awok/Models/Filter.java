package com.awok.moshin.awok.Models;

import java.util.ArrayList;

/**
 * Created by mohsin on 1/31/2016.
 */
public class Filter {
    String title;
    String type;
    ArrayList<FilterValue> valuesArray = new ArrayList<FilterValue>();


    public Filter(String title, String type, ArrayList<FilterValue> valuesArray){
        this.title = title;
        this.type = type;
        this.valuesArray = valuesArray;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<FilterValue> getValuesArray() {
        return valuesArray;
    }

    public void setValuesArray(ArrayList<FilterValue> valuesArray) {
        this.valuesArray = valuesArray;
    }
}
