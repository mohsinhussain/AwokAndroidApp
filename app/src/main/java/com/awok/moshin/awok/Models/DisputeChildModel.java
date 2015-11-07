package com.awok.moshin.awok.Models;

/**
 * Created by shon on 11/5/2015.
 */
public class DisputeChildModel {
    public String date;
    public String initiator;
    public String isReceived;
    public String refundAmount;
    private String shipGoods;
    private String action;
    private String note;


    public DisputeChildModel()
    {

    }

    public DisputeChildModel(String date,String initiator,String isReceived,String refundAmount,String shipGoods, String action,String note ) {
        this.date = date;
        this.initiator = initiator;
        this.isReceived=isReceived;
        this.refundAmount=refundAmount;
        this.shipGoods=shipGoods;
        this.action=action;
        this.note=note;


    }



    public void setDate(String date)
    {
        this.date=date;
    }



    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public void setIsReceived(String isReceived)
    {
        this.isReceived=isReceived;
    }

    public void setShipGoods(String shipGoods)
    {
        this.shipGoods=shipGoods;
    }

    public void setAction(String action)
    {
        this.action=action;
    }



    public void setNote(String note)
    {
        this.note=note;
    }


    public void setRefundAmount(String refundAmount)
    {
        this.refundAmount=refundAmount;
    }





    public String getDate()
    {
        return date;
    }


    public String getInitiator()
    {
        return initiator;
    }





    public String getIsReceived()
    {
        return isReceived;
    }












    public String getRefundAmount()
    {
        return refundAmount;
    }








    public String getShipGoods()
    {
        return shipGoods;
    }






    public String getAction()
    {
        return action;
    }







    public String getNote()
    {
        return note;
    }







}

