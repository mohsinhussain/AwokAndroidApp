package com.awok.moshin.awok.Fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awok.moshin.awok.Models.ProductRatingModel;
import com.awok.moshin.awok.R;

import java.util.List;

public class ShippingDeliveryFrag extends Fragment
{


    String returnPolicies,estimatedPrice,country,estimatedDays,shipFrom;
    public ShippingDeliveryFrag() {
        
    }

    public ShippingDeliveryFrag(String returnPolicies,String estimatedPrice,String country,String estimatedDays,String shipFrom) {
        this.estimatedPrice=estimatedPrice;
        this.country=country;
        this.estimatedDays=estimatedDays;
        this.shipFrom=shipFrom;
        this.returnPolicies=returnPolicies;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.activity_shipping_delivery_frag, container, false);





        TextView shipPrice=(TextView)mView.findViewById(R.id.shipPrice);
        TextView availability=(TextView)mView.findViewById(R.id.availability);
        TextView estiShipping=(TextView)mView.findViewById(R.id.estiShipping);
        TextView shipsFrom=(TextView)mView.findViewById(R.id.shipsFrom);
        TextView returnPolicy=(TextView)mView.findViewById(R.id.returnPolicy);


        /*shipPrice,availability,estiShipping,shipsFrom,returnPolicy*/
                shipPrice.setText(estimatedPrice);
        availability.setText(country);
        estiShipping.setText(estimatedDays);
        shipsFrom.setText(shipFrom);
        returnPolicy.setText(returnPolicies);

        return mView;
    }
}
