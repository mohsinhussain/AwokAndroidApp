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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.awok.moshin.awok.Activities.ProductDetailsActivity;
import com.awok.moshin.awok.Adapters.HotDealsAdapter;
import com.awok.moshin.awok.Models.Products;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.awok.moshin.awok.Util.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HotDealsFragment extends Fragment {

    RecyclerView mRecyclerView;
    HotDealsAdapter mAdapter;
    View mView;
    ProgressBar progressBar;
    ProgressBar loadMore;
    ArrayList<Products> productsArrayList;
    String categoryId = null;
    private String TAG = "Hot Deals Fragment";
    SwipeRefreshLayout mSwipeRefreshLayout;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    private  int pageCount = 1;
    private TextView itemCount;
    private boolean isSearch = false;
    private Button gotoTopButton;
    private String searchString = null;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    public HotDealsFragment(){}

    public HotDealsFragment(String categoryId)
    {
        this.categoryId = categoryId;
    }


    public HotDealsFragment(String searchString, boolean isSearch)
    {
        this.isSearch = isSearch;
        this.searchString = searchString;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_hot_deals, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.dealsRecyclerView);
        progressBar = (ProgressBar) mView.findViewById(R.id.marker_progress);
        itemCount = (TextView) mView.findViewById(R.id.itemCountTextView);
        gotoTopButton = (Button) mView.findViewById(R.id.goToTopButton);
        progressBar.setVisibility(View.GONE);
        loadMore=(ProgressBar)mView.findViewById(R.id.load_progress_bar);
        loadMore.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loading = true;
                pageCount = 1;
                previousTotal = 0;
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
                        Intent i=new Intent(getContext(), ProductDetailsActivity.class);
                        i.putExtra(Constants.PRODUCT_ID_INTENT,productsArrayList.get(position).getId());
                        i.putExtra(Constants.PRODUCT_NAME_INTENT,productsArrayList.get(position).getName());
                        i.putExtra(Constants.PRODUCT_DISCOUNT_PERCENTAGE_INTENT,productsArrayList.get(position).getDiscPercent());
                        i.putExtra(Constants.PRODUCT_IMAGE_INTENT,productsArrayList.get(position).getImage());
                        i.putExtra(Constants.PRODUCT_PRICE_NEW_INTENT,productsArrayList.get(position).getPriceNew());
                        i.putExtra(Constants.PRODUCT_PRICE_OLD_INTENT,productsArrayList.get(position).getPriceOld());
                        i.putExtra(Constants.PRODUCT_DESCRIPTION_INTENT,productsArrayList.get(position).getDescription());
                        i.putExtra(Constants.CAT_ID_INTENT, productsArrayList.get(position).getCategoryId());
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

        gotoTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayoutManager.smoothScrollToPosition(mRecyclerView, null, 0);
            }
        });

//        boolean loading = true;
//        int pastVisiblesItems, visibleItemCount, totalItemCount;

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                progressBar.setVisibility(View.GONE);
                visibleItemCount = mRecyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if(firstVisibleItem<=6){
                    //Hide GO TO TOP
                    gotoTopButton.setVisibility(View.GONE);
                }
                else{
                    //show GO TO TOP
                    gotoTopButton.setVisibility(View.VISIBLE);
                }

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached

                    pageCount++;
                    Log.i("Hot Deals Fragment", "pageCount: " + pageCount);

                    // Do something
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

        loading = true;
        pageCount = 1;
        previousTotal = 0;

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
        else
        {
            loadMore.setVisibility(View.VISIBLE);
            progressBar
                    .setVisibility(View.GONE);
        }

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if(isSearch && searchString!=null){
                new APIClient(getActivity(), getActivity(),  new GetProductsCallback()).productsFromSearchAPICall(searchString, pageCount);
            }
            else if(categoryId!=null){
                new APIClient(getActivity(), getActivity(),  new GetProductsCallback()).productsFromCategoryAPICall(categoryId, pageCount);
            }
            else{
                new APIClient(getActivity(), getActivity(),  new GetProductsCallback()).allProductsAPICall(pageCount);
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
                JSONArray jsonArray = null;
                if (isSearch){
                    JSONObject obj = new JSONObject(response);
                    itemCount.setVisibility(View.VISIBLE);
                    if(obj.getInt("status")==1){
                        itemCount.setText("We found "+obj.getInt("total_products")+" search results for '"+searchString+"'");
                        jsonArray = obj.getJSONArray("items");
                    }
                    else{
                        if(pageCount>1){
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "No further data", Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }
                        else{
                            itemCount.setText("We found 0 search results for '"+searchString+"'");
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "No such object found", Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.RED)
                                    .show();
//                            Snackbar.make(getActivity().findViewById(android.R.id.content), obj.getString(obj.getString("title")), Snackbar.LENGTH_LONG)
//                                    .setActionTextColor(Color.RED)
//                                    .show();
                        }
                        /*if(getActivity()!=null){
                            Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
                            progressBar.startAnimation(animation);
                        }*/
                        progressBar.setVisibility(View.GONE);
                        loadMore.setVisibility(View.GONE);
                        if (mSwipeRefreshLayout!=null && mSwipeRefreshLayout.isRefreshing()){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        return;
                    }
                }
                else{
                    jsonArray = new JSONArray(response);
                }

//                JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_PRODUCT_LIST_NAME);
                int length = jsonArray.length();

                if(length>0){
                    for(int i=0;i<length;i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Products item = new Products();
                        item.setId(jsonObject.getString("id"));
                        item.setName(jsonObject.getString("name"));
                        item.setImage(jsonObject.getString("image"));
                        item.setCategoryId(jsonObject.getString("category_id"));
                        item.setPriceNew(jsonObject.getInt("new_price"));
                        item.setPriceOld(jsonObject.getInt("original_price"));
                        item.setDescription(jsonObject.getString("description"));
                        item.setDiscPercent(jsonObject.getInt("discount_percentage"));
//                    if (priceObject.getInt("PRICE_OLD")!=0){
//                        item.setDiscPercent(priceObject.getInt("PERCENT"));
//                    }
                        productsArrayList.add(item);
                    }
                }
                else{
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "No further items", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }



               /* if(getActivity()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
                    progressBar.startAnimation(animation);
                }*/
                progressBar.setVisibility(View.GONE);
                loadMore.setVisibility(View.GONE);
                initializeData();
                if (mSwipeRefreshLayout!=null && mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();
                /*if(getActivity()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
                    progressBar.startAnimation(animation);
                }*/
                progressBar.setVisibility(View.GONE);
                loadMore.setVisibility(View.GONE);
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
            if(!mSwipeRefreshLayout.isRefreshing()){
                progressBar.setVisibility(View.VISIBLE);
            }
           /* else
            {
                //progressBar.setVisibility(View.GONE);
                loadMore.setVisibility(View.VISIBLE);
            }*/

        }
    }



    private void initializeData(){
        if (pageCount==1){
            mAdapter = new HotDealsAdapter(getActivity(), productsArrayList);
            mRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.notifyDataSetChanged();
        }

    }

}
