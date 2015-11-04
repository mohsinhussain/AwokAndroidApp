package com.awok.moshin.awok.Models;

/**
 * Created by mohsin on 11/1/2015.
 */
public class ShippingMethod {
    Double shippingCost;
    int savings;
    String name;
    int estFrom;
    int estTo;
    String profileId;
    boolean isSelected;

    public ShippingMethod(Double shippingCost, int savings, String name, int estFrom, int estTo, String profileId, boolean isSelected){
        this.shippingCost = shippingCost;
        this.savings = savings;
        this.name = name;
        this.estFrom = estFrom;
        this.estTo = estTo;
        this.profileId = profileId;
        this.isSelected = isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getShippingCost() {
        return shippingCost;
    }

    public String getDeliveryTime(){
        return Integer.toString(estFrom) + " - " + Integer.toString(estTo) + " days";
    }

    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public int getSavings() {
        return savings;
    }

    public void setSavings(int savings) {
        this.savings = savings;
    }

    public int getEstFrom() {
        return estFrom;
    }

    public void setEstFrom(int estFrom) {
        this.estFrom = estFrom;
    }

    public int getEstTo() {
        return estTo;
    }

    public void setEstTo(int estTo) {
        this.estTo = estTo;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
}
