package com.awok.moshin.awok.Models;

/**
 * Created by shon on 10/24/2015.
 */
public class DescriptionModel {

        private String descHeader;
        private String descData;



    public DescriptionModel()
    {

    }


    public DescriptionModel(String descHeader,String descData)
    {
        this.descHeader=descHeader;
        this.descData=descData;
    }


        public String getDescHeader() {
            return descHeader;
        }

        public void setDescHeader(String descHeader) {
            this.descHeader = descHeader;
        }

        public String getDescData() {
            return descData;
        }

        public void setDescData(String descData) {
            this.descData = descData;
        }




}
