package com.awok.moshin.awok.Models;

/**
 * Created by shon on 10/31/2015.
 */
public class OrderStatusModel {
    public String statusType;

    public String statusId;
    private boolean isSelected;
    private boolean isHeader;


    public OrderStatusModel()
    {

    }

    public OrderStatusModel(String statusType,String statusId,Boolean isSelected,Boolean isHeader) {
        this.statusType = statusType;
        this.statusId=statusId;
        this.isSelected=isSelected;
        this.isHeader=isHeader;


    }








    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }





    public String getStatusType()
    {
        return statusType;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }





    public String getStatusId()
    {
        return statusId;
    }


    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public boolean getIsHeader() {
        return isHeader;
    }





    public void setIsHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }



}
