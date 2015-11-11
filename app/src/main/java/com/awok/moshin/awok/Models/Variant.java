package com.awok.moshin.awok.Models;

/**
 * Created by mohsin on 11/1/2015.
 */
public class Variant {
    private String color;
    private String storage;
    String id;
    int stock;
    int price;

    public Variant(String id, String storage, int Stoc, int price){
        this.storage = storage;
        this.id = id;
        this.stock = Stoc;
        this.price = price;
    }

    public Variant(String id, String color, String storage, int Stoc, int price){
        this.color = color;
        this.storage = storage;
        this.id = id;
        this.stock = Stoc;
        this.price = price;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
