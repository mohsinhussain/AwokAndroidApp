package com.awok.moshin.awok.Fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awok.moshin.awok.Activities.ProductDetailsActivity;
import com.awok.moshin.awok.Models.ProductDetailsModel;
import com.awok.moshin.awok.Models.ProductRatingModel;
import com.awok.moshin.awok.R;

import java.util.List;

public class ShippingDeliveryFrag extends Fragment
{

    TextView shipPrice,availability,estiShipping,shipsFrom,returnPolicy;
    String returnPolicies,estimatedPrice,country,estimatedDays,shipFrom;
    public ShippingDeliveryFrag() {

    }

 /*   public ShippingDeliveryFrag(String returnPolicies,String estimatedPrice,String country,String estimatedDays,String shipFrom) {
        this.estimatedPrice=estimatedPrice;
        this.country=country;
        this.estimatedDays=estimatedDays;
        this.shipFrom=shipFrom;
        this.returnPolicies=returnPolicies;

    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.activity_shipping_delivery_frag, container, false);





         shipPrice=(TextView)mView.findViewById(R.id.shipPrice);
         availability=(TextView)mView.findViewById(R.id.availability);
         estiShipping=(TextView)mView.findViewById(R.id.estiShipping);
         shipsFrom=(TextView)mView.findViewById(R.id.shipsFrom);
         returnPolicy=(TextView)mView.findViewById(R.id.returnPolicy);
        shipPrice.setText(estimatedPrice);
        availability.setText(country);
        estiShipping.setText(estimatedDays);
        shipsFrom.setText(shipFrom);
        returnPolicy.setText(returnPolicies);

        /*shipPrice,availability,estiShipping,shipsFrom,returnPolicy*/
                /*shipPrice.setText(estimatedPrice);
        availability.setText(country);
        estiShipping.setText(estimatedDays);
        shipsFrom.setText(shipFrom);
        returnPolicy.setText(returnPolicies);*/

        return mView;
    }

    /*@Override
    public void onArticleSelected(int position) {
        System.out.println(position);
    }*/


    public void call(String returnPolicies,String estimatedPrice,String country,String estimatedDays,String shipFrom)
    {
        this.estimatedPrice=estimatedPrice;
        this.country=country;
        this.estimatedDays=estimatedDays;
        this.shipFrom=shipFrom;
        this.returnPolicies=returnPolicies;

        System.out.println("MOHSIN/SHON HAS DONE IT");
    }
}
