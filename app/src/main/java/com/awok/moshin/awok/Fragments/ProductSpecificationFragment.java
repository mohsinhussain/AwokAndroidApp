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
import java.util.List;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_product_specification, container, false);
        ListView prodSpecList = (ListView) mView.findViewById(R.id.productSpecList);
        adapter=new ProductSpecificationAdapter(getActivity(),specList);
        prodSpecList.setAdapter(adapter);

        int i=0;
        for(i=0;i<=20;i++)
        {
           ProductSpecification listData=new ProductSpecification();
            listData.setSpecTitle("Spec" + " " + i);
            listData.setSpecValue("VALUES" + " " + i);

            specList.add(listData);

        }
adapter.notifyDataSetChanged();
        return mView;
    }

}
