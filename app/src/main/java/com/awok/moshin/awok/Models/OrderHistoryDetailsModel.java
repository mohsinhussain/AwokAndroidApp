package com.awok.moshin.awok.Models;

/**
 * Created by shon on 9/30/2015.
 */
public class OrderHistoryDetailsModel {


    public String image;
    public String price;
    public String quantity;
    private String title;
    private String    orderTime;
    private String delTime;
    private String seller;
    private String shipping;
    private String shippingStatus;
    private String productName;
    private String productUniqueId;
    private String estimated_days_from;
    private String estimated_days_to;
    private String intentPrice;
    private String headOrderMessageCount;
    private String headOrderStatus;
    private String headOrderTime;
    private String footerShipment;
    private String totalAmount;
    private boolean headerView=false;
    private boolean loader=false;


    public boolean isLoader() {
        return loader;
    }

    public void setLoader(boolean loader) {
        this.loader = loader;
    }

    public String getHeadOrderTime() {
        return headOrderTime;
    }

    public void setHeadOrderTime(String headOrderTime) {
        this.headOrderTime = headOrderTime;
    }

    public String getHeadOrderStatus() {
        return headOrderStatus;
    }

    public void setHeadOrderStatus(String headOrderStatus) {
        this.headOrderStatus = headOrderStatus;
    }

    public String getHeadOrderMessageCount() {
        return headOrderMessageCount;
    }

    public void setHeadOrderMessageCount(String headOrderMessageCount) {
        this.headOrderMessageCount = headOrderMessageCount;
    }

    public boolean isHeaderView() {
        return headerView;
    }

    public void setHeaderView(boolean headerView) {
        this.headerView = headerView;
    }

    public String getFooterShipment() {
        return footerShipment;
    }

    public void setFooterShipment(String footerShipment) {
        this.footerShipment = footerShipment;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderHistoryDetailsModel(String footerShipment,String totalAmount,boolean loader)
    {
        this.footerShipment=footerShipment;
        this.totalAmount=totalAmount;
        this.headerView=false;

        this.loader=loader;

    }


    public OrderHistoryDetailsModel(String headOrderMessageCount,String headOrderStatus,String headOrderTime,boolean headerView)
    {
        this.headOrderMessageCount=headOrderMessageCount;
        this.headOrderStatus=headOrderStatus;
        this.headOrderTime=headOrderTime;
        this.headerView=headerView;
        this.loader=false;
    }



    public OrderHistoryDetailsModel()
    {

    }

    public OrderHistoryDetailsModel(String image,String estimated_days_from  ,String intentPrice,String estimated_days_to, String price,String quantity,String title,String seller, String shipping,String shippingStatus,String delTime,String productName,String productUniqueId) {
        this.image = image;
        this.price = price;
        this.quantity=quantity;
        this.title=title;
        this.orderTime=orderTime;
        this.delTime=delTime;
        this.seller=seller;
        this.shipping=shipping;
        this.shippingStatus=shippingStatus;
        this.delTime=delTime;
        this.productName=productName;
        this.productUniqueId=productUniqueId;
        this.estimated_days_to=estimated_days_to;
        this.intentPrice=intentPrice;







        this.estimated_days_from=estimated_days_from;
        this.headerView=false;
        this.loader=false;


    }


    public void setImage(String image)
    {
        this.image=image;
    }





    public void setPrice(String price)
    {
        this.price=price;
    }



    public void setQuantity(String quantity)
    {
        this.quantity=quantity;
    }







    public String getImage()
    {
        return image;
    }


    public String getPrice()
    {
        return price;
    }






    public String getQuantity()
    {
        return quantity;
    }


    public void setTitle(String title) {
        this.title = title;
    }






    public String getTitle()
    {
        return title;
    }




    public void setSeller(String seller) {
        this.seller = seller;
    }






    public String getSeller()
    {
        return seller;
    }




    public void setShipping(String shipping) {
        this.shipping = shipping;
    }






    public String getShipping()
    {
        return shipping;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }







    public String getShippingStatus()
    {
        return shippingStatus;
    }

    public void setDelTime(String delTime) {
        this.delTime = delTime;
    }

    public String getDelTime()
    {
        return delTime;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductUniqueId(String productUniqueId) {
        this.productUniqueId = productUniqueId;
    }

    public String getProductUniqueId()
    {
        return productUniqueId;
    }

    public void setEstimated_days_from(String estimated_days_from) {
        this.estimated_days_from = estimated_days_from;
    }







    public String getEstimated_days_from()
    {
        return estimated_days_from;
    }

    public void setEstimated_days_to(String estimated_days_to) {
        this.estimated_days_to = estimated_days_to;
    }

    public String getEstimated_days_to()
    {
        return estimated_days_to;
    }

    public void setIntentPrice(String intentPrice) {
        this.intentPrice = intentPrice;
    }





    public String getIntentPrice()
    {
        return intentPrice;
    }

/*    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }




    public String getOrderTime()
    {
        return orderTime;
    }





    public void setDelTime(String delTime) {
        this.delTime = delTime;
    }




    public String getDelTime()
    {
        return delTime;
    }*/




}
