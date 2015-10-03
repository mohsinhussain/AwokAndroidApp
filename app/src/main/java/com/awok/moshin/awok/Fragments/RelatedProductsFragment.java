package com.awok.moshin.awok.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.awok.moshin.awok.Activities.ProductDetailsActivity;
import com.awok.moshin.awok.Adapters.HotDealsAdapter;
import com.awok.moshin.awok.Models.Products;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RelatedProductsFragment extends Fragment {

    /**
     * Created by shon on 9/12/2015.
     */
    RecyclerView mRecyclerView;
    HotDealsAdapter mAdapter;
    View mView;

    ArrayList<Products> productsArrayList;
    private String TAG = "Hot Deals Fragment";
    SwipeRefreshLayout mSwipeRefreshLayout;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    private  int pageCount = 1;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    public RelatedProductsFragment(){}
    String categoryId = null;



    public RelatedProductsFragment(String categoryId)
    {
        this.categoryId = categoryId;
    }
    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_hot_deals, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.dealsRecyclerView);


        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);


        productsArrayList = new ArrayList<Products>();

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
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_hot_deals, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.dealsRecyclerView);
        //progressBar = (ProgressBar) mView.findViewById(R.id.marker_progress);
        //progressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageCount=1;
//                refreshContent();
            }
        });

        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.CYAN, Color.GREEN,
                Color.YELLOW);
        mRecyclerView.setHasFixedSize(true);

        final LinearLayoutManager mLayoutManager;
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), "Product Name: " + productsArrayList.get(position).getId(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getContext(), ProductDetailsActivity.class);
                        i.putExtra("id", productsArrayList.get(position).getId());
                        startActivity(i);
//                        Intent subCatIntent = new Intent(getActivity(), SubCategoriesActivity.class);
//                        subCatIntent.putExtra(Constants.CAT_ARRAY_INTENT, (Serializable) categoriesArrayList);
////                        subCatIntent.putExtra(Constants.CAT_DEPTH_LEVEL_INTENT, localCategoriesArrayList.get(position).getDepthLevel());
//                        subCatIntent.putExtra(Constants.CAT_PARENT_ID_INTENT, localCategoriesArrayList.get(position).getParentId());
//                        subCatIntent.putExtra(Constants.CAT_NAME_INTENT, localCategoriesArrayList.get(position).getName());
//                        subCatIntent.putExtra(Constants.CAT_ID_INTENT, localCategoriesArrayList.get(position).getId());
//                        startActivity(subCatIntent);
                    }
                })
        );


        productsArrayList = new ArrayList<Products>();

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if(categoryId==null){
//                new APIClient(getActivity(), getActivity(),  new GetProductsCallback()).allProductsAPICall(page);
            }
            else{
//                new APIClient(getActivity(), getActivity(),  new GetProductsCallback()).productsFromCategoryAPICall(categoryId);
            }

        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }
        return mView;
    }


    /*public class GetProductsCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject mMembersJSON;
                mMembersJSON = new JSONObject(response);
                JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_PRODUCT_LIST_NAME);
                int length = jsonArray.length();

                for (int i = 0; i < length; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Products item = new Products();
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
    }*/
    public class GetProductsCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONArray jsonArray;
                jsonArray = new JSONArray(response);
//                JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_PRODUCT_LIST_NAME);
                int length = jsonArray.length();

                for(int i=0;i<length;i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Products item = new Products();
                    item.setId(jsonObject.getString("id"));
                    item.setName(jsonObject.getString("name"));
                    item.setImage(jsonObject.getString("image"));
                    item.setPriceNew(jsonObject.getInt("new_price"));
                    item.setPriceOld(jsonObject.getInt("original_price"));
                    item.setDiscPercent(jsonObject.getInt("discount_percentage"));
//                    if (priceObject.getInt("PRICE_OLD")!=0){
//                        item.setDiscPercent(priceObject.getInt("PERCENT"));
//                    }
                    productsArrayList.add(item);
                }

                if(getActivity()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
                   // progressBar.startAnimation(animation);
                }
               // progressBar.setVisibility(View.GONE);
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
          //  progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void initializeData() {
        mAdapter = new HotDealsAdapter(getActivity(), productsArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }
}