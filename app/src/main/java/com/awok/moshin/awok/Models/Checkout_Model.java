package com.awok.moshin.awok.Models;

/**
 * Created by shon on 1/18/2016.
 */
public class Checkout_Model {




    private String id;
    private String name;
    private String product_id;
    private String old_price;
    private String price;
    private String currency;
    private Boolean isHeader;
    private String discount;
    private String quantity;
    private String image;
    private boolean isEditable;
    private String toatalItems,sum,total,delivery;
    private Boolean isFooter;


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOld_price() {
        return old_price;
    }

    public void setOld_price(String old_price) {
        this.old_price = old_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setIsEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }
public Checkout_Model()

{

}


    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Checkout_Model(String toatlItems,String sum,String total,String delivery,Boolean isFooter)

    {
this.toatalItems=toatlItems;
        this.sum=sum;
        this.total=total;
        this.delivery=delivery;
        this.isFooter=isFooter;
        this.isHeader=false;


    }



    public Boolean getIsHeader() {
        return isHeader;
    }

    public void setIsHeader(Boolean isHeader) {
        this.isHeader = isHeader;
    }

    public String getToatalItems() {
        return toatalItems;
    }

    public void setToatalItems(String toatalItems) {
        this.toatalItems = toatalItems;
    }

    public Boolean getIsFooter() {
        return isFooter;
    }

    public void setIsFooter(Boolean isFooter) {
        this.isFooter = isFooter;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public Checkout_Model(String id,String name,String product_id,String old_price,String price,String currency,String quantity,String image,boolean isEditable,String discount)
    {
        this.id=id;
        this.name=name;
        this.product_id=product_id;
        this.old_price=old_price;
        this.price=price;
        this.currency=currency;
        this.isHeader=isHeader;
        this.quantity=quantity;
        this.image=image;
        this.isFooter=false;
        this.discount=discount;
        this.isEditable=isEditable;


    }


}
