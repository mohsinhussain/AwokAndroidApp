package com.awok.moshin.awok.Models;

/**
 * Created by shon on 9/15/2015.
 */
public class OrderSummary {


        public String OverViewTitle;
        public String OverViewText;

        public OrderSummary()
        {

        }

        public OrderSummary(String OverViewTitle, String OverViewText) {
            this.OverViewTitle = OverViewTitle;
            this.OverViewText = OverViewText;

        }


        public void setOverViewTitle(String OverViewTitle)
        {
            this.OverViewTitle=OverViewTitle;
        }





        public void setOverViewText(String OverViewText)
        {
            this.OverViewText=OverViewText;
        }





        public String getOverViewTitle()
        {
            return OverViewTitle;
        }


        public String getOverViewText()
        {
            return OverViewText;
        }





}
