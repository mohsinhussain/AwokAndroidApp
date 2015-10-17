package com.awok.moshin.awok.Models;

/**
 * Created by shon on 10/10/2015.
 */
public class productOverviewRating {

private String rating,mainText,name;



    public productOverviewRating()
    {

    }

    public productOverviewRating(String rating, String mainText,String name) {
        this.rating = rating;
        this.mainText=mainText;
        this.name=name;


    }


    public void setRating(String rating)
    {
        this.rating=rating;
    }





    public void setMainText(String mainText)
    {
        this.mainText=mainText;
    }





    public void setName(String name)
    {
        this.name=name;
    }




    public String getMainText()
    {
        return mainText;
    }

    public String getName()
    {
        return name;
    }



    public String getRating()
    {
        return rating;
    }

}
