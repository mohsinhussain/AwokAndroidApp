package com.awok.moshin.awok.Models;

import java.util.ArrayList;

/**
 * Created by shon on 10/24/2015.
 */




    public class ProductRatingModel {

        private String rate;
        private String content;
    private String username;
    private String imagesCount;
    private String days;
    private boolean header=false;
    private String condition="";








    private String bundleId="";
    private String bundleName="";
    private String bundleWarranty="";
    private String bundleProperties="";
    private String bundleNewPrice="";
    private String bundleOldPrice="";
    private String bundleDiscount="";
    private String bundlePercent="";
    private int bundleLength=0;
    private String bundleImage="";
    private String bundleRating="";







    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    private boolean loader=false;
private String rating;
    private String productId;
    private String productName;
    ArrayList<String> imageString = new ArrayList<String>();
    private String image;
    private String estimatedDays;
    private String estimatedPrice;
    private String reviewCount;
    private String message;
    private int commentSize;
    private String color;

    public ProductRatingModel()
        {
            this.header=false;
            this.loader=false;
        }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public String getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(String estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public String getEstimatedDays() {
        return estimatedDays;
    }

    public void setEstimatedDays(String estimatedDays) {
        this.estimatedDays = estimatedDays;
    }

    public String getImagesCount() {
        return imagesCount;
    }

    public void setImagesCount(String imagesCount) {
        this.imagesCount = imagesCount;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public ArrayList<String> getImageString() {
        return imageString;
    }

    public void setImageString(ArrayList<String> imageString) {
        this.imageString = imageString;
    }

    public int getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(int commentSize) {
        this.commentSize = commentSize;
    }

    public ProductRatingModel(String productId, String productName, String image, String estimatedDays, String estimatedPrice , String reviewCount,String rating,ArrayList<String> imageString,int commentSize,String imagesCount , boolean header)
    {
        this.productId=productId;
        this.productName=productName;
this.reviewCount=reviewCount;
        this.rating=rating;
        this.imageString=imageString;

this.commentSize=commentSize;

        this.image=image;
      //  this.estimatedDays=estimatedDays;
      //  this.estimatedPrice=estimatedPrice;
        this.header=header;
        this.loader=false;

        this.imagesCount=imagesCount;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ProductRatingModel(String color,String estimatedDays, String estimatedPrice,String condition,boolean loader, String message) {
        this.loader = loader;
        this.estimatedDays=estimatedDays;
          this.estimatedPrice=estimatedPrice;
        this.message=message;
        this.color=color;
        this.header=false;
        this.condition=condition;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isLoader() {
        return loader;
    }

    public void setLoader(boolean loader) {
        this.loader = loader;
    }

    public ProductRatingModel(String rate, String content, String username, String days)
        {
            this.rate=rate;
            this.content=content;
            this.username=username;
            this.days=days;
            this.header=false;
            this.loader=false;

        }


        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setDays(String days) {
        this.days = days;
    }


    public String getDays() {
        return days;
    }


    public int getBundleLength() {
        return bundleLength;
    }

    public void setBundleLength(int bundleLength) {
        this.bundleLength = bundleLength;
    }

    public ProductRatingModel(String bundleId,String bundleName,String bundleWarranty,String bundleProperties,String bundleNewPrice,String bundleOldPrice,String bundleDiscount,
         String bundlePercent, String bundleImage, String bundleRating,int bundleLength)
{
    this.bundleId=bundleId;
    this.bundleName=bundleName;
    this.bundleWarranty=bundleWarranty;
    this.bundleProperties=bundleProperties;
    this.bundleNewPrice=bundleNewPrice;
    this.bundleOldPrice=bundleOldPrice;
    this.bundleDiscount=bundleDiscount;
    this.bundlePercent=bundlePercent;
    this.bundleImage=bundleImage;
    this.bundleRating=bundleRating;
    this.bundleLength=bundleLength;
}


    public String getBundleRating() {
        return bundleRating;
    }

    public void setBundleRating(String bundleRating) {
        this.bundleRating = bundleRating;
    }

    public String getBundleImage() {
        return bundleImage;
    }

    public void setBundleImage(String bundleImage) {
        this.bundleImage = bundleImage;
    }

    public String getBundlePercent() {
        return bundlePercent;
    }

    public void setBundlePercent(String bundlePercent) {
        this.bundlePercent = bundlePercent;
    }

    public String getBundleDiscount() {
        return bundleDiscount;
    }

    public void setBundleDiscount(String bundleDiscount) {
        this.bundleDiscount = bundleDiscount;
    }

    public String getBundleOldPrice() {
        return bundleOldPrice;
    }

    public void setBundleOldPrice(String bundleOldPrice) {
        this.bundleOldPrice = bundleOldPrice;
    }

    public String getBundleNewPrice() {
        return bundleNewPrice;
    }

    public void setBundleNewPrice(String bundleNewPrice) {
        this.bundleNewPrice = bundleNewPrice;
    }

    public String getBundleProperties() {
        return bundleProperties;
    }

    public void setBundleProperties(String bundleProperties) {
        this.bundleProperties = bundleProperties;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getBundleWarranty() {
        return bundleWarranty;
    }

    public void setBundleWarranty(String bundleWarranty) {
        this.bundleWarranty = bundleWarranty;
    }

    public String getBundleName() {
        return bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }


}


