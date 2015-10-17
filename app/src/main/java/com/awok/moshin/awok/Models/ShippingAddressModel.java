package com.awok.moshin.awok.Models;

/**
 * Created by shon on 10/11/2015.
 */
public class ShippingAddressModel {
    public String name;
    public String address,state;
    private String country,pin;
    private String phone;
    private boolean isSelected;


    public ShippingAddressModel()
    {

    }





    public ShippingAddressModel(String name,String address,String state,String country,String pin,String phone,boolean isSelected)
    {
        this.name=name;
        this.address=address;
        this.state=state;
        this.country=country;
        this.pin=pin;
        this.phone=phone;
        this.isSelected=isSelected;


    }














    public void setName(String name) {
        this.name = name;
    }



    public String getName()
    {
        return name;
    }




    public void setAddress(String address) {
        this.address = address;
    }



    public String getAddress()
    {
        return address;
    }



    public void setState(String state) {
        this.state = state;
    }



    public String getState()
    {
        return state;
    }






    public void setCountry(String country) {
        this.country = country;
    }




    public String getCountry()
    {
        return country;
    }



    public void setPin(String pin) {
        this.pin = pin;
    }





    public String getPin()
    {
        return pin;
    }



    public void setPhone(String phone) {
        this.phone=  phone;
    }










    public String getPhone()
    {
        return phone;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }






    public boolean getIsSelected()
    {
        return isSelected;
    }

}
