package com.awok.moshin.awok.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.awok.moshin.awok.Adapters.ProductSpecificationAdapter;
import com.awok.moshin.awok.Models.ProductSpecification;
import com.awok.moshin.awok.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by shon on 9/10/2015.
 */
public class ProductSpecificationFragment extends Fragment{
    private TextView productTitle,product_reviewCount,prod_warranty,prod_color,prod_color_default,prod_colorSecondary,prod_shipping,prod_shippingCost,prod_delivery,prod_deliveryTime,prod_reviews,quickDeliveryTxt,
            prod_price,prod_discountPrice;

    private Button prod_buyNow;
    private RatingBar prodRatingBar,prod_reviewRating;
    private ProductSpecificationAdapter adapter;
    private List<ProductSpecification> specList = new ArrayList<ProductSpecification>();
    Map<String,String> prodSpecData=new HashMap<String, String>();


   /* public ProductSpecificationFragment(Map<String, String> productSpec) {
        prodSpecData=productSpec;
    }*/
   public ProductSpecificationFragment() {

   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_product_specification, container, false);
        ListView prodSpecList = (ListView) mView.findViewById(R.id.productSpecList);
        adapter=new ProductSpecificationAdapter(getActivity(),specList);
        prodSpecList.setAdapter(adapter);
        specList.clear();
        int i=0;
        for(i=0;i<=prodSpecData.size();i++)
        {
           ProductSpecification listData=new ProductSpecification();
            listData.setSpecTitle(prodSpecData.get(i));
            listData.setSpecValue("VALUES" + " " + i);

            specList.add(listData);

        }

       /* for (Map.Entry<String, String> entry : prodSpecData.entrySet()) {
            *//*String key = entry.getKey();
            Object value = entry.getValue();*//*
            ProductSpecification listData=new ProductSpecification();
            listData.setSpecTitle(entry.getKey());
            listData.setSpecValue(entry.getValue());
System.out.println(entry.getValue());
            specList.add(listData);


        }*/
adapter.notifyDataSetChanged();
        return mView;
    }

}
