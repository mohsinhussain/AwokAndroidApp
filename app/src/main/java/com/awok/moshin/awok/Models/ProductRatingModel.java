package com.awok.moshin.awok.Models;

/**
 * Created by shon on 10/24/2015.
 */




    public class ProductRatingModel {

        private String rate;
        private String content;
    private String username;
    private String days;


    public ProductRatingModel()
        {

        }


        public ProductRatingModel(String rate,String content,String username,String days)
        {
            this.rate=rate;
            this.content=content;
            this.username=username;
            this.days=days;
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


