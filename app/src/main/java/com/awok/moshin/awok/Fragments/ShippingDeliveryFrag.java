package com.awok.moshin.awok.Fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import com.awok.moshin.awok.R;

public class ShippingDeliveryFrag extends Fragment
{
    public ShippingDeliveryFrag() {
        
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.activity_shipping_delivery_frag, container, false);

        return mView;
    }
}
