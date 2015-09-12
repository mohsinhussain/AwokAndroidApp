package com.awok.moshin.awok.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.awok.moshin.awok.Adapters.HotDealsAdapter;
import com.awok.moshin.awok.Adapters.ProductOverViewAdapter;
import com.awok.moshin.awok.Adapters.RelatedProductsAdapter;
import com.awok.moshin.awok.Models.ProductOverview;
import com.awok.moshin.awok.Models.Products;
import com.awok.moshin.awok.Models.RelatedProductsModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RelatedProductsFragment extends Fragment {

    /**
     * Created by shon on 9/12/2015.
     */
    RecyclerView mRecyclerView;
    RelatedProductsAdapter mAdapter;
    View mView;

    ArrayList<RelatedProductsModel> productsArrayList;
    private String TAG = "Hot Deals Fragment";
    public RelatedProductsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_related_products, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.dealsRecyclerView);


        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);


        productsArrayList = new ArrayList<RelatedProductsModel>();

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new APIClient(getActivity(), getActivity(), new GetProductsCallback()).productsAPICall();
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }
        return mView;
    }


    public class GetProductsCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject mMembersJSON;
                mMembersJSON = new JSONObject(response);
                JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_PRODUCT_LIST_NAME);
                int length = jsonArray.length();

                for (int i = 0; i < length; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    RelatedProductsModel item = new RelatedProductsModel();
                    item.setId(jsonObject.getString("ID"));
                    item.setName(jsonObject.getString("NAME"));
                    item.setImage(jsonObject.getString("IMAGE"));
                    JSONObject priceObject = jsonObject.getJSONObject("PRICE");
                    item.setPriceNew(priceObject.getInt("PRICE_NEW"));
                    item.setPriceOld(priceObject.getInt("PRICE_OLD"));
                    if (priceObject.getInt("PRICE_OLD") != 0) {
                        item.setDiscount(priceObject.getInt("DISCOUNT"));
                        item.setDiscPercent(priceObject.getInt("PERCENT"));
                        item.setY(priceObject.getJSONObject("TIMER").getString("Y"));
                        item.setM(priceObject.getJSONObject("TIMER").getString("M"));
                        item.setD(priceObject.getJSONObject("TIMER").getString("D"));
                        item.setH(priceObject.getJSONObject("TIMER").getString("H"));
                        item.setI(priceObject.getJSONObject("TIMER").getString("I"));
                        item.setS(priceObject.getJSONObject("TIMER").getString("S"));
                        item.setInDays(priceObject.getJSONObject("TIMER").getInt("IN_DAYS"));
                        item.setEndTime(priceObject.getInt("END_TIME"));
                    }
                    productsArrayList.add(item);
                }

                if (getActivity() != null) {
                    //Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);

                }

                initializeData();
            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();
            }
        }

        @Override
        public void onTaskCancelled() {
        }

        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub

        }
    }


    private void initializeData() {
        mAdapter = new RelatedProductsAdapter(getActivity(), productsArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }
}