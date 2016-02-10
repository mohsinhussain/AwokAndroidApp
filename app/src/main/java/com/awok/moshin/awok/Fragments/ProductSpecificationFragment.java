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
import com.awok.moshin.awok.Adapters.ShippingReturnAdapter;
import com.awok.moshin.awok.Models.ProductSpecification;
import com.awok.moshin.awok.Models.ShippingReturnModel;
import com.awok.moshin.awok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    String data="";
    private RatingBar prodRatingBar,prod_reviewRating;
    private ProductSpecificationAdapter adapter;
    ListView prodSpecList;
    private List<ProductSpecification> specList = new ArrayList<ProductSpecification>();
    Map<String,String> prodSpecData=new HashMap<String, String>();
    String dataSubTitle,dataSub;


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
         prodSpecList = (ListView) mView.findViewById(R.id.productSpecList);
        adapter=new ProductSpecificationAdapter(getActivity(),specList);
        prodSpecList.setAdapter(adapter);
        specList.clear();
        int i=0;
        if(data.equals("")) {

        }
        else {


            try {
                JSONArray jArray = new JSONArray(data);
                for (i = 0; i < jArray.length(); i++) {
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();
                    ProductSpecification listData = new ProductSpecification();
                    JSONObject jData=jArray.getJSONObject(i);
                    //listData.setSpecTitle(jData.getString("NAME"));
               //     listData.setSpecValue(jData.getString("VALUE"));
                    if(jData.has("VALUE"))
                    {
                 /*       if (jData.get("VALUE") instanceof JSONObject) {
                            listData.setSpecValue("");
                            listData.setSpecTitle(jData.getString("NAME"));
                            *//*JSONObject keyz = jData.getJSONObject("VALUE");
                            listData.setSubTitle(jData.getJSONObject("VALUE"));
                            listData.setSubData(jData.getJSONObject(""));*//*
                            Iterator<String> iter = jData.getJSONObject("VALUE").keys();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                try {
                                    Object value = jData.getJSONObject("VALUE").get(key);
                                        sb.append(key);
                                    sb.append(System.getProperty("line.separator"));
                                    sb2.append(value.toString());
                                    sb2.append(System.getProperty("line.separator"));
                                } catch (JSONException e) {
                                    // Something went wrong!
                                }

                            }
                            System.out.println("DATA"+sb.toString());
                            System.out.println("DATA" + sb2.toString());
                            listData.setSubTitle(sb.toString());
                            listData.setSubData(sb2.toString());
                        }*/
                        if (jData.get("VALUE") instanceof JSONArray) {
                            listData.setSpecValue("");
                            listData.setSpecTitle(jData.getString("NAME"));
                            /*JSONObject keyz = jData.getJSONObject("VALUE");
                            listData.setSubTitle(jData.getJSONObject("VALUE"));
                            listData.setSubData(jData.getJSONObject(""));*/
                    /*        Iterator<String> iter = jData.getJSONObject("VALUE").keys();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                try {
                                    Object value = jData.getJSONObject("VALUE").get(key);
                                    sb.append(key);
                                    sb.append(System.getProperty("line.separator"));
                                    sb2.append(value.toString());
                                    sb2.append(System.getProperty("line.separator"));
                                } catch (JSONException e) {
                                    // Something went wrong!
                                }*/
                            try {

                                int length = jData.getJSONArray("VALUE").length();
                                for (int j = 0; j < length; j++) {
                                    JSONObject jObj=jData.getJSONArray("VALUE").getJSONObject(j);


                                    sb.append(jObj.getString("NAME"));
                                    sb.append(System.getProperty("line.separator"));
                                    sb2.append(jObj.getString("VALUE"));
                                    sb2.append(System.getProperty("line.separator"));

                                    listData.setSubTitle(sb.toString());
                                    listData.setSubData(sb2.toString());
                                }

                            }
                            catch (JSONException e) {
                                // Something went wrong!
                            }



                            System.out.println("DATA"+sb.toString());
                            System.out.println("DATA" + sb2.toString());
                            listData.setSubTitle(sb.toString());
                            listData.setSubData(sb2.toString());
                        }
                        else {
                            listData.setSpecTitle(jData.getString("NAME"));
                            listData.setSpecValue(jData.getString("VALUE"));
                            listData.setSubTitle("");
                            listData.setSubData("");
                        }
                    }

                    specList.add(listData);

                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

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

        return mView;
    }
    public void call(String data)
    {
        this.data=data;

//adapter.notifyDataSetChanged();
        if(prodSpecList!=null)
        {

            adapter=new ProductSpecificationAdapter(getActivity(),specList);
            prodSpecList.setAdapter(adapter);
            specList.clear();
            int i=0;
            if(data.equals("")) {

            }
            else {


                try {
                    JSONArray jArray = new JSONArray(data);
                    for (i = 0; i < jArray.length(); i++) {
                        StringBuilder sb = new StringBuilder();
                        StringBuilder sb2 = new StringBuilder();
                        ProductSpecification listData = new ProductSpecification();
                        JSONObject jData=jArray.getJSONObject(i);
                        //listData.setSpecTitle(jData.getString("NAME"));
                        //     listData.setSpecValue(jData.getString("VALUE"));
                        if(jData.has("VALUE"))
                        {
                 /*       if (jData.get("VALUE") instanceof JSONObject) {
                            listData.setSpecValue("");
                            listData.setSpecTitle(jData.getString("NAME"));
                            *//*JSONObject keyz = jData.getJSONObject("VALUE");
                            listData.setSubTitle(jData.getJSONObject("VALUE"));
                            listData.setSubData(jData.getJSONObject(""));*//*
                            Iterator<String> iter = jData.getJSONObject("VALUE").keys();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                try {
                                    Object value = jData.getJSONObject("VALUE").get(key);
                                        sb.append(key);
                                    sb.append(System.getProperty("line.separator"));
                                    sb2.append(value.toString());
                                    sb2.append(System.getProperty("line.separator"));
                                } catch (JSONException e) {
                                    // Something went wrong!
                                }

                            }
                            System.out.println("DATA"+sb.toString());
                            System.out.println("DATA" + sb2.toString());
                            listData.setSubTitle(sb.toString());
                            listData.setSubData(sb2.toString());
                        }*/
                            if (jData.get("VALUE") instanceof JSONArray) {
                                listData.setSpecValue("");
                                listData.setSpecTitle(jData.getString("NAME"));
                            /*JSONObject keyz = jData.getJSONObject("VALUE");
                            listData.setSubTitle(jData.getJSONObject("VALUE"));
                            listData.setSubData(jData.getJSONObject(""));*/
                    /*        Iterator<String> iter = jData.getJSONObject("VALUE").keys();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                try {
                                    Object value = jData.getJSONObject("VALUE").get(key);
                                    sb.append(key);
                                    sb.append(System.getProperty("line.separator"));
                                    sb2.append(value.toString());
                                    sb2.append(System.getProperty("line.separator"));
                                } catch (JSONException e) {
                                    // Something went wrong!
                                }*/
                                try {

                                    int length = jData.getJSONArray("VALUE").length();
                                    for (int j = 0; j < length; j++) {
                                        JSONObject jObj=jData.getJSONArray("VALUE").getJSONObject(j);


                                        sb.append(jObj.getString("NAME"));
                                        sb.append(System.getProperty("line.separator"));
                                        sb2.append(jObj.getString("VALUE"));
                                        sb2.append(System.getProperty("line.separator"));

                                        listData.setSubTitle(sb.toString());
                                        listData.setSubData(sb2.toString());
                                    }

                                }
                                catch (JSONException e) {
                                    // Something went wrong!
                                }



                                System.out.println("DATA"+sb.toString());
                                System.out.println("DATA" + sb2.toString());
                                listData.setSubTitle(sb.toString());
                                listData.setSubData(sb2.toString());
                            }
                            else {
                                listData.setSpecTitle(jData.getString("NAME"));
                                listData.setSpecValue(jData.getString("VALUE"));
                                listData.setSubTitle("");
                                listData.setSubData("");
                            }
                        }

                        specList.add(listData);

                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        else {

        }




        }
}
