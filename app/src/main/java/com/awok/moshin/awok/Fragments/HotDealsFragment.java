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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.awok.moshin.awok.Activities.SubCategoriesActivity;
import com.awok.moshin.awok.Adapters.HotDealsAdapter;
import com.awok.moshin.awok.Models.Products;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.awok.moshin.awok.Util.EndlessRecyclerOnScrollListener;
import com.awok.moshin.awok.Util.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class HotDealsFragment extends Fragment {

    RecyclerView mRecyclerView;
    HotDealsAdapter mAdapter;
    View mView;
    ProgressBar progressBar;
    ArrayList<Products> productsArrayList;
    String categoryId = null;
    private String TAG = "Hot Deals Fragment";
    SwipeRefreshLayout mSwipeRefreshLayout;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    private  int pageCount = 1;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    public HotDealsFragment(){}

    public HotDealsFragment(String categoryId)
    {
        this.categoryId = categoryId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_hot_deals, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.dealsRecyclerView);
        progressBar = (ProgressBar) mView.findViewById(R.id.marker_progress);
        progressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageCount=1;
                refreshContent();
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
                        Toast.makeText(getActivity(), "Product Name: " +productsArrayList.get(position).getId(), Toast.LENGTH_SHORT).show();
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

//        boolean loading = true;
//        int pastVisiblesItems, visibleItemCount, totalItemCount;

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = mRecyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached

                    Log.i("...", "end called");

                    // Do something
                    pageCount++;
                    refreshContent();

                    loading = true;
                }
            }
        });

//        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(
//                mLayoutManager) {
//            @Override
//            public void onLoadMore(int current_page) {
//                // do somthing...
//
//                loadMoreData(current_page);
//
//            }
//
//        });



        refreshContent();

//            ConnectivityManager connMgr = (ConnectivityManager)
//                    getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//            if(networkInfo!=null&&networkInfo.isConnected())
//
//            {
//                if (categoryId == null) {
//                    new APIClient(getActivity(), getActivity(), new GetProductsCallback()).allProductsAPICall(pageCount);
//                } else {
//                    new APIClient(getActivity(), getActivity(), new GetProductsCallback()).productsFromCategoryAPICall(categoryId);
//                }
//
//            }
//
//            else
//
//            {
//                Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
//                        .setActionTextColor(Color.RED)
//                        .show();
//            }

            return mView;
        }


    // adding 10 object creating dymically to arraylist and updating recyclerview when ever we reached last item
//    private void loadMoreData(int current_page) {
//
//        // I have not used current page for showing demo, if u use a webservice
//        // then it is useful for every call request
//
//        Log.v(TAG, "S=Current Page: "+current_page);
//        refreshContent();
//
//    }

    private void refreshContent(){
        if(pageCount==1){
            productsArrayList = new ArrayList<Products>();
        }

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if(categoryId==null){
                new APIClient(getActivity(), getActivity(),  new GetProductsCallback()).allProductsAPICall(pageCount);
            }
            else{
                new APIClient(getActivity(), getActivity(),  new GetProductsCallback()).productsFromCategoryAPICall(categoryId, pageCount);
            }

        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }
    }

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
                    progressBar.startAnimation(animation);
                }
                progressBar.setVisibility(View.GONE);
                initializeData();
                if (mSwipeRefreshLayout!=null && mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();
                if (mSwipeRefreshLayout!=null && mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
        }
    }



    private void initializeData(){
        mAdapter = new HotDealsAdapter(getActivity(), productsArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }

}
