package com.awok.moshin.awok.Models;

/**
 * Created by shon on 10/11/2015.
 */
public class ShippingAddressModel {
    private String name;
    private String address1, address2,state;
    private String country,pin, city;
    private String phone1, phone2;
    private boolean isSelected;
    private String id;


    public ShippingAddressModel()
    {

    }





    public ShippingAddressModel(String id, String name,String address1,String address2,String state,String country,String pin,String phone1, String phone2, boolean isSelected)
    {
        this.id = id;
        this.name=name;
        this.address1=address1;
        this.address2=address2;
        this.state=state;
        this.country=country;
        this.pin=pin;
        this.phone1=phone1;
        this.phone2=phone2;
        this.isSelected=isSelected;


    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getState() {
        if(state.equalsIgnoreCase("")){
            return "";
        }
        else{
            return ", "+state;
        }
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
