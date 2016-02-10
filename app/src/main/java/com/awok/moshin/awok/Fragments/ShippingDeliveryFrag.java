package com.awok.moshin.awok.Fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.awok.moshin.awok.Activities.ProductDetailsActivity;
import com.awok.moshin.awok.Adapters.ProductSpecificationAdapter;
import com.awok.moshin.awok.Adapters.ShippingReturnAdapter;
import com.awok.moshin.awok.Models.ProductDetailsModel;
import com.awok.moshin.awok.Models.ProductRatingModel;
import com.awok.moshin.awok.Models.ProductSpecification;
import com.awok.moshin.awok.Models.ShippingReturnModel;
import com.awok.moshin.awok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShippingDeliveryFrag extends Fragment
{

    TextView shipPrice,availability,estiShipping,shipsFrom,returnPolicy;
    String returnPolicies,estimatedPrice,country,estimatedDays,shipFrom;
   List<ShippingReturnModel> shipModel=new ArrayList<>();
    private ShippingReturnAdapter adapter;
    ListView prodSpecList;
    String data="";
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


         prodSpecList = (ListView) mView.findViewById(R.id.ShippingList);
        adapter = new ShippingReturnAdapter(getActivity(), shipModel);
        prodSpecList.setAdapter(adapter);

         /*shipPrice=(TextView)mView.findViewById(R.id.shipPrice);
         availability=(TextView)mView.findViewById(R.id.availability);
         estiShipping=(TextView)mView.findViewById(R.id.estiShipping);
         shipsFrom=(TextView)mView.findViewById(R.id.shipsFrom);
         returnPolicy=(TextView)mView.findViewById(R.id.returnPolicy);
        shipPrice.setText(estimatedPrice);
        availability.setText(country);
        estiShipping.setText(estimatedDays);
        shipsFrom.setText(shipFrom);
        returnPolicy.setText(returnPolicies);*/

        shipModel.clear();
        int i = 0;
        if (data.equals("")) {

        } else {


            try {
                JSONArray jArray = new JSONArray(data);
                for (i = 0; i < jArray.length(); i++) {
                    ShippingReturnModel shipData = new ShippingReturnModel();



                                        JSONObject data=jArray.getJSONObject(i);
                    shipData.setSpecTitle(data.getString("TITLE"));
                    shipData.setSpecValue(data.getString("DESCRIPTION"));

                    shipModel.add(shipData);

                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
            return mView;
        }



/*    public void call(String returnPolicies,String estimatedPrice,String country,String estimatedDays,String shipFrom)
    {
        this.estimatedPrice=estimatedPrice;
        this.country=country;
        this.estimatedDays=estimatedDays;
        this.shipFrom=shipFrom;
        this.returnPolicies=returnPolicies;


if(shipPrice!=null||availability!=null||estiShipping!=null||shipsFrom!=null||returnPolicy!=null) {
    shipPrice.setText(estimatedPrice);
    availability.setText(country);
    estiShipping.setText(estimatedDays);
    shipsFrom.setText(shipFrom);
    returnPolicy.setText(returnPolicies);

}


        System.out.println("MOHSIN/SHON HAS DONE IT");
    }*/


    public void call(String data)
    {
        this.data=data;
        if(prodSpecList!=null)
        {

            adapter = new ShippingReturnAdapter(getActivity(), shipModel);
            prodSpecList.setAdapter(adapter);
            shipModel.clear();
            int i = 0;
            if (data.equals("")) {

            } else {


                try {
                    JSONArray jArray = new JSONArray(data);
                    for (i = 0; i < jArray.length(); i++) {
                        ShippingReturnModel shipData = new ShippingReturnModel();



                        JSONObject dataEase=jArray.getJSONObject(i);
                        shipData.setSpecTitle(dataEase.getString("TITLE"));
                        shipData.setSpecValue(dataEase.getString("DESCRIPTION"));

                        shipModel.add(shipData);

                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


/*
            adapter = new ShippingReturnAdapter(getActivity(), shipModel);
            prodSpecList.setAdapter(adapter);*/
          //  adapter.notifyDataSetChanged();
        }
        else {

        }
        //adapter.notifyDataSetChanged();
    }
}
