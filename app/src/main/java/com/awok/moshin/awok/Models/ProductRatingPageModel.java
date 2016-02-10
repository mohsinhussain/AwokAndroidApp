package com.awok.moshin.awok.Models;

/**
 * Created by shon on 11/12/2015.
 */
public class ProductRatingPageModel {

    private String rate;
    private String content;
    private String username;
    private String productName;
    private String image;
    private String rating;
    private String ratingCount;
    private String boughtBy;
    private String savedBy;
    private String days;
    private String message;
    private boolean headerView=false;
    private boolean loader=false;


    private int commentsValueSize;


    public ProductRatingPageModel()
    {

    }


    public boolean isLoader() {
        return loader;
    }

    public void setLoader(boolean loader) {
        this.loader = loader;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProductRatingPageModel(boolean loader, String message) {
        this.loader = loader;

        this.message=message;
        this.headerView=false;
    }



    public String getBoughtBy() {
        return boughtBy;
    }

    public void setBoughtBy(String boughtBy) {
        this.boughtBy = boughtBy;
    }

    public String getSavedBy() {
        return savedBy;
    }

    public void setSavedBy(String savedBy) {
        this.savedBy = savedBy;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getHeaderView() {
        return headerView;
    }

    public void setHeaderView(Boolean headerView) {
        this.headerView = headerView;
    }

    public int getCommentsValueSize() {
        return commentsValueSize;
    }

    public void setCommentsValueSize(int commentsValueSize) {
        this.commentsValueSize = commentsValueSize;
    }

    public boolean isHeaderView() {
        return headerView;
    }

    public void setHeaderView(boolean headerView) {
        this.headerView = headerView;
    }

    public ProductRatingPageModel(String productName, String image, String rating, String ratingCount, String boughtBy,int commentsValueSize, boolean headerView)
{
    this.productName=productName;

    this.image=image;
    this.rating=rating;
    this.commentsValueSize=commentsValueSize;

    this.boughtBy=boughtBy;
   // this.savedBy=savedBy;
    this.ratingCount=ratingCount;
    this.headerView=headerView;
    this.loader=false;

}
    public ProductRatingPageModel(String rate, String content, String username, String days)
    {
        this.rate=rate;
        this.content=content;
        this.username=username;
        this.days=days;
        this.headerView=false;
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
}
