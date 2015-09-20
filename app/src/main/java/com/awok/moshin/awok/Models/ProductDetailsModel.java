package com.awok.moshin.awok.Models;

/**
 * Created by shon on 9/19/2015.
 */
public class ProductDetailsModel {






        String id;
        String name;
        int oldPrice;
        int newPrice;
        int discPercent;


public ProductDetailsModel() {
        }

public ProductDetailsModel(String id, String name, int newPrice, int oldPrice,   int discPercent) {
        this.id = id;
        this.name = name;
        this.oldPrice=oldPrice;
        this.newPrice=newPrice;
        this.discPercent = discPercent;


        }

       /* public ProductDetailsModel(String name) {

                this.name = name;



        }*/

public String getId() {
        return id;
        }

public void setId(String id) {
        this.id = id;
        }

public String getName() {
        return name;
        }

public void setName(String name) {
        this.name = name;
        }



public String getPriceNew() {
        String price = Integer.toString(newPrice);
        price = price+" AED";
        return price;
        }

public void setPriceNew(int priceNew) {
        this.newPrice = priceNew;
        }

public String getPriceOld() {
        String price = Integer.toString(oldPrice);
        price = price+" AED";
        return price;
        }

public void setPriceOld(int priceOld) {
        this.oldPrice = priceOld;
        }



public int getDiscPercent() {
        return discPercent;
        }

public void setDiscPercent(int discPercent) {
        this.discPercent = discPercent;
        }


        }