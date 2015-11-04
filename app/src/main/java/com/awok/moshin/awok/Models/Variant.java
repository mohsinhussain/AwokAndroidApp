package com.awok.moshin.awok.Models;

/**
 * Created by mohsin on 11/1/2015.
 */
public class Variant {
    private String color;
    private String storage;
    String id;
    int stock;

    public Variant(String id, String color, String storage, int Stoc){
        this.color = color;
        this.storage = storage;
        this.id = id;
        this.stock = Stoc;
    }

    public String getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }
}
