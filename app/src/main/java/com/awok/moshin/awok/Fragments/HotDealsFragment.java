package com.awok.moshin.awok.Fragments;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awok.moshin.awok.Activities.FilterActivity;
import com.awok.moshin.awok.Activities.ProductDetailsActivity;
import com.awok.moshin.awok.Adapters.DragonBallAdapter;
import com.awok.moshin.awok.Adapters.HotDealsAdapter;
import com.awok.moshin.awok.Models.Products;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.awok.moshin.awok.Util.RecyclerItemClickListener;
import com.karumi.headerrecyclerview.HeaderRecyclerViewAdapter;
import com.karumi.headerrecyclerview.HeaderSpanSizeLookup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.zip.Inflater;

import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;

public class HotDealsFragment extends Fragment {

    RecyclerView mRecyclerView;
    DragonBallAdapter mAdapter;
    AlphaInAnimationAdapter alphaAdapter;
    View mView;
    LinearLayout progressLayout;
    ProgressBar progressBar;
    ProgressBar loadMore;
    ArrayList<Products> productsArrayList;
    String categoryId = null;
    String mainCategoryId = "";
    private String TAG = "Hot Deals Fragment";
    SwipeRefreshLayout mSwipeRefreshLayout;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    private  int pageCount = 1;
    private TextView itemCount;
    private boolean shouldLoadMore = false;
    private LinearLayout mainLayout;
    private boolean isSearch = false;
    private String xtra_image;
    private ImageButton gotoTopButton;
    LinearLayout.LayoutParams params;
    private String searchString = null;
    private String SearchKey = "";
    int firstVisibleItem, visibleItemCount, totalItemCount, lastVisibleItem;
    ArrayList<String> filtersArray = new ArrayList<String>();
//    ArrayList<String> tagsFilterArray = new ArrayList<String>();
//    ArrayList<String> colorFilterArray = new ArrayList<String>();
//    ArrayList<String> priceFilterArray = new ArrayList<String>();
//    ArrayList<String> sizeFilterArray = new ArrayList<String>();
//    ArrayList<String> brandFilterArray = new ArrayList<String>();
//    ArrayList<String> ratingsFilterArray = new ArrayList<String>();
    StaggeredGridLayoutManager mLayoutManager;
    String filterString = "";
    String keywords = "";
    boolean isFilter = false;
    LinearLayout filterButtonLayout;
    TextView messageTextView;
    public HotDealsFragment(){}

    public HotDealsFragment(String categoryId)
    {
        this.categoryId = categoryId;
        this.mainCategoryId = categoryId;
    }

    public HotDealsFragment(String searchString, boolean isSearch)
    {
        this.isSearch = isSearch;
        this.SearchKey = searchString;
        this.searchString = "category_id=ALL"+"&keywords="+searchString;
    }

    @Override
    public void onPause() {
        super.onPause();
        isSearch = false;
        isFilter = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshContent();
    }

    public HotDealsFragment( String parentCatId, String categoryId, String filterString, ArrayList<String> filterValuesArray, String keywords, boolean isSearch, boolean isFilter)
    {
        this.mainCategoryId = parentCatId;
        this.categoryId = categoryId;
        this.isSearch = isSearch;
        this.filterString = filterString;
        this.filtersArray = filterValuesArray;
        this.isFilter = isFilter;
        this.keywords = keywords;
        makeSearchString();
    }


    private void makeSearchString(){

        if(categoryId!=null){
            searchString = "category_id="+categoryId+filterString;
        }
        else{
            searchString = filterString;
        }
        if(!keywords.equalsIgnoreCase("")){
            searchString = searchString+"&keywords="+keywords;
        }
        System.out.println("SearchString: " + searchString);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_hot_deals, container, false);
        //setHasOptionsMenu(true);
        filterButtonLayout = (LinearLayout)mView.findViewById(R.id.filterButtonLayout);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.dealsRecyclerView);
        mainLayout=(LinearLayout)mView.findViewById(R.id.progressLay);
        progressBar = (ProgressBar) mView.findViewById(R.id.marker_progress);
        progressLayout = (LinearLayout) mView.findViewById(R.id.progressLayout);
        itemCount = (TextView) mView.findViewById(R.id.itemCountTextView);
        messageTextView = (TextView) mView.findViewById(R.id.messageTextView);
        gotoTopButton = (ImageButton) mView.findViewById(R.id.goToTopButton);
        progressLayout.setVisibility(View.GONE);
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

        showFilters(inflater, container);


        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.CYAN, Color.GREEN,
                Color.YELLOW);


        mRecyclerView.setHasFixedSize(false);

        mLayoutManager = new StaggeredGridLayoutManager(2,  1);
//        HeaderSpanSizeLookup headerSpanSizeLookup = new HeaderSpanSizeLookup(adapter, layoutManager);
//        mLayoutManager.setSpanSizeLookup(headerSpanSizeLookup);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if(productsArrayList.size()>0){
                            ActivityOptionsCompat options =
                                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                                            getActivity());
                            Intent i=new Intent(getContext(), ProductDetailsActivity.class);
                       i.putExtra(Constants.PRODUCT_ID_INTENT,productsArrayList.get(position).getId());
                           // i.putExtra(Constants.PRODUCT_ID_INTENT,"3653");
                      //      i.putExtra(Constants.PRODUCT_RATING,productsArrayList.get(position).getRating());
                      //      i.putExtra(Constants.PRODUCT_RATING_COUNT,productsArrayList.get(position).getRatingCount());
                            i.putExtra(Constants.PRODUCT_OUT_STOCK,productsArrayList.get(position).getOutOfStock());
                            i.putExtra(Constants.PRODUCT_NAME_INTENT,productsArrayList.get(position).getName());
                            i.putExtra(Constants.PRODUCT_DISCOUNT_PERCENTAGE_INTENT,productsArrayList.get(position).getDiscPercent());
                            i.putExtra(Constants.PRODUCT_IMAGE_INTENT,productsArrayList.get(position).getImage());
                            i.putExtra(Constants.PRODUCT_PRICE_NEW_INTENT,productsArrayList.get(position).getPriceNew());
                            i.putExtra(Constants.PRODUCT_PRICE_OLD_INTENT,productsArrayList.get(position).getPriceOld());
                            i.putExtra(Constants.PRODUCT_DESCRIPTION_INTENT,productsArrayList.get(position).getDescription());
                            i.putExtra(Constants.CAT_ID_INTENT, productsArrayList.get(position).getCategoryId());
                            i.putExtra("type","SP");
                            i.putExtra("typeCid",productsArrayList.get(position).getCategoryId());
                            System.out.println("catId"+categoryId);
                            ActivityCompat.startActivity(getActivity(), i, options.toBundle());
                        }

                    }
                })
        );

        gotoTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstVisibleItem<=40) {
                    mLayoutManager.smoothScrollToPosition(mRecyclerView, null, 0);
                }
                else{
                    mLayoutManager.scrollToPosition(0);
                }
            }
        });

//        boolean loading = true;
//        int pastVisiblesItems, visibleItemCount, totalItemCount;

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                progressLayout.setVisibility(View.GONE);
                visibleItemCount = mRecyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                int [] x = mLayoutManager.findFirstCompletelyVisibleItemPositions(null);
                firstVisibleItem = x[0];
                int [] m = mLayoutManager.findLastCompletelyVisibleItemPositions(null);
                lastVisibleItem = m[0];

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
                        //mainLayout.setVisibility(View.VISIBLE);
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }

//                Log.i(TAG, "lastVisibleItem: " + lastVisibleItem);
//                Log.i(TAG, "lastProductItem: " + (productsArrayList.size()-1));

                if(shouldLoadMore && lastVisibleItem==(productsArrayList.size()-1)){
                    DragonBallFooter footer = getFooter();
                    mAdapter.setFooter(footer);
//                    alphaAdapter.notifyDataSetChanged();
//                    Products item = new Products(true);
//                    productsArrayList.add(item);
//                    mAdapter.notifyDataSetChanged();
                }

                System.out.println("VISIBLE ITEM COUNT: "+ visibleItemCount);

                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {


                    pageCount++;


//                    Log.i("Hot Deals Fragment", "pageCount: " + pageCount);

                    // Do something
                    refreshContent();

                    System.out.println("totalItemCount - visibleItemCount" + (totalItemCount - visibleItemCount));
                    System.out.println("firstVisibleItem + visibleThreshold" + (firstVisibleItem + visibleThreshold));
                    System.out.println("totalItemCount"+totalItemCount);
                    System.out.println("visibleItemCount" + visibleItemCount);
                    System.out.println("firstVisibleItem" + firstVisibleItem);
                    System.out.println("visibleThreshold" + visibleThreshold);

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

//        loading = true;
//        pageCount = 1;
//        previousTotal = 0;
//
//        refreshContent();

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


    private void showFilters(LayoutInflater inflater, ViewGroup container){
        if(!filterString.equalsIgnoreCase("")){
            filterButtonLayout.setVisibility(View.VISIBLE);
            for (int i = 0; i < filtersArray.size(); i++) {
                if(!filtersArray.get(i).equalsIgnoreCase("")){
                    final Button t = (Button) inflater.inflate(R.layout.filter_button_tag, container, false);
                    t.setText(filtersArray.get(i));
                    t.setSingleLine(true);
                    t.setSelected(true);
                    t.setTag(i);
                    t.setTextColor(getContext().getResources().getColor(R.color.button_text));
                    t.setBackground(getResources().getDrawable(R.drawable.filter_button));
                    t.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            ((Button)v).setTextColor(getContext().getResources().getColor(R.color.dialog_button_cancel));
                            ((Button)v).setBackground(getResources().getDrawable(R.drawable.unselected_filter_button));
                            filterButtonLayout.animate()
                                    .translationY(-100)
                                    .alpha(0.0f)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            filterButtonLayout.removeView(v);
                                            filterButtonLayout.animate()
                                                    .translationY(0)
                                                    .alpha(1.0f).setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    int index = (int) v.getTag();
                                                    if(filterButtonLayout.getChildCount()==0){
                                                        filterString = "";
                                                        filterButtonLayout.setVisibility(View.GONE);
                                                        isFilter = false;
                                                        isSearch = false;
                                                        loading = true;
                                                        pageCount = 1;
                                                        previousTotal = 0;
                                                        categoryId = mainCategoryId;
                                                        refreshContent();
                                                        filtersArray.set(index, "");
                                                        return;
                                                    }
                                                    else{
                                                        if(filterString.contains(filtersArray.get(index))){
                                                            filterString = filterString.replace(filtersArray.get(index)+",","");
                                                        }
                                                        filtersArray.set(index, "");
                                                    }
                                                    makeSearchString();
                                                    loading = true;
                                                    pageCount = 1;
                                                    previousTotal = 0;
                                                    refreshContent();
                                                }
                                            });
                                        }
                                    });



                        }
                    });
                    filterButtonLayout.
                            addView(t);
                }
            }

        }
        else{
//            categoryId = mainCategoryId;
            filterButtonLayout.setVisibility(View.GONE);
            loading = true;
            pageCount = 1;
            previousTotal = 0;
        }
    }


    // adding 10 object creating dymically to arraylist and updating recyclerview when ever we reached last item
    private void loadMoreData(int current_page) {

        // I have not used current page for showing demo, if u use a webservice
        // then it is useful for every call request

        Log.v(TAG, "S=Current Page: "+current_page);
        refreshContent();

    }

    private void refreshContent(){
        if(pageCount==1){
            productsArrayList = new ArrayList<Products>();

        }
//<<<<<<< HEAD
        else {

            shouldLoadMore = true;
            progressLayout
                    .setVisibility(View.GONE);
        }

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (isSearch && searchString != null) {
                if(filterString.equalsIgnoreCase("")){
                    categoryId=mainCategoryId;
                }
                new APIClient(getActivity(), getActivity(), new GetProductsCallback()).productsFromSearchAPICall(searchString, pageCount);
            } else if (categoryId != null) {
                if(filterString.equalsIgnoreCase("")){
                    categoryId=mainCategoryId;
                }
                new APIClient(getActivity(), getActivity(), new GetProductsCallback()).productsFromCategoryAPICall(categoryId, pageCount);
            } else {
                {
                    new APIClient(getActivity(), getActivity(), new GetProductsCallback()).allProductsAPICall(pageCount);
                }

            }
        }
            else {
            Snackbar snackbar =Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }




    public class GetProductsCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject  mainObject = new JSONObject(response);
                JSONObject obj = null;
                JSONArray jsonArray = null;
                if (isSearch){
                    obj = new JSONObject(response);
                    if(isFilter){
                        itemCount.setVisibility(View.GONE);
                    }
                    else{
                        itemCount.setVisibility(View.VISIBLE);
                    }

                    if(obj.getJSONObject("STATUS").getInt("CODE")==200){
//                        itemCount.setText("We found "+obj.getInt("total_products")+" search results for '"+searchString+"'");
                        //jsonArray = obj.getJSONArray("items");
                        mainObject.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("ITEMS");
                        jsonArray = mainObject.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("ITEMS");
                    }
                    else{
                        if(pageCount>1){
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "No further data", Snackbar.LENGTH_SHORT)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }
                        else{
                            if(isFilter) {
                                itemCount.setVisibility(View.GONE);
                            }
                            else{
                                itemCount.setText("We found 0 search results for '" + searchString + "'");
                            }
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "No such object found", Snackbar.LENGTH_SHORT)
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
                        progressLayout.setVisibility(View.GONE);
                        loadMore.setVisibility(View.GONE);
                        mainLayout.setVisibility(View.GONE);
                        shouldLoadMore = false;
                        if (mSwipeRefreshLayout!=null && mSwipeRefreshLayout.isRefreshing()){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        return;
                    }
                }
                else{
                    System.out.println(response);
                    mainObject = new JSONObject(response);
                    //jsonArray = mainObject.getJSONObject("data").getJSONObject("server").getJSONArray("items");
                    // jsonArray = mainObject.getJSONObject("output").getJSONArray("message");
                    jsonArray = mainObject.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("ITEMS");
                }

//                JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_PRODUCT_LIST_NAME);
                int length = jsonArray.length();
                if(isSearch){
                    if(isFilter) {
                        itemCount.setVisibility(View.GONE);
                    }
                    else{
                        itemCount.setVisibility(View.VISIBLE);
                        itemCount.setText("We found " + obj.getJSONObject("OUTPUT").getJSONObject("NAVIGATION").getInt("TOTAL") + " search results for '" + SearchKey + "'");
                    }
                }

                if(length>0){
//                    mAdapter.hideFooter();
//                    alphaAdapter.notifyDataSetChanged();
//                    if(productsArrayList.size()>0){
//                        for(int i=0;i<productsArrayList.size();i++){
//                            if(productsArrayList.get(i).isLoader()){
//                                productsArrayList.remove(i);
//                            }
//                        }
//                        add.notifyDataSetChanged();
//                    }

                    for(int i=0;i<length;i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Products item = new Products();
                        item.setId(jsonObject.getString("ID"));
                        item.setName(jsonObject.getString("NAME"));
                        item.setImage(jsonObject.getJSONObject("IMAGE").getString("SRC"));
                        //  item.setImageHeight(jsonObject.getInt("IMAGE_HEIGHT"));
                        item.setImageHeight(jsonObject.getJSONObject("IMAGE").getInt("HEIGHT"));
                        //  item.setImageWidth(jsonObject.getInt("IMAGE_WIDTH"));
                        item.setImageWidth(jsonObject.getJSONObject("IMAGE").getInt("WIDTH"));
                        item.setCategoryId(jsonObject.getString("CATEGORY_ID"));
                        //item.setPriceNew(jsonObject.getJSONObject("DISCOUNTS").getInt("discount_price"));
                        if(!jsonObject.getJSONObject("PRICES").isNull("PRICE_NEW")&&(!jsonObject.getJSONObject("PRICES").getString("PRICE_NEW").equals(""))) {
                            item.setPriceNew(jsonObject.getJSONObject("PRICES").getInt("PRICE_NEW"));
                        }
                        else
                        {

                            item.setPriceNew(0);
                            //item.setPriceNew(0);
                        }
                        //item.setPriceOld(jsonObject.getInt("PRICE"));
                        if(!jsonObject.getJSONObject("PRICES").isNull("PRICE_OLD")&&(!jsonObject.getJSONObject("PRICES").getString("PRICE_OLD").equals(""))) {
                            item.setPriceOld(jsonObject.getJSONObject("PRICES").getInt("PRICE_OLD"));
                        }
                        else
                        {
                            item.setPriceOld(0);
                        }
                        if(!jsonObject.getJSONObject("PRICES").isNull("PERCENT")&&(!jsonObject.getJSONObject("PRICES").getString("PERCENT").equals(""))) {
                            System.out.println("PERCENT" + jsonObject.getJSONObject("PRICES").getString("PERCENT"));
                            item.setDiscPercent(jsonObject.getJSONObject("PRICES").getInt("PERCENT"));
                        }
                        else
                        {
                            item.setDiscPercent(0);
                        }



                        if(jsonObject.getString("STOCK").equals("Y")||jsonObject.getJSONObject("PRICES").getString("PRICE_NEW").equals(""))
                        {

                            item.setOutOfStock("Y");
                        }
                        else
                        {
                            item.setOutOfStock("N");
                        }


                        //////////////         item.setRating(jsonObject.getJSONObject("RAITING").getString("rating_average"));
                        ///////////////         item.setRatingCount(jsonObject.getJSONObject("RAITING").getString("number_of_ratings"));
//                        item.setDescription(jsonObject.getString("description"));
                        /////////////  item.setDiscPercent(jsonObject.getJSONObject("DISCOUNTS").getInt("discount_percentage"));
             /*           if(!jsonObject.getJSONObject("DISCOUNTS").isNull("timer")){
                            item.setYears(jsonObject.getJSONObject("DISCOUNTS").getJSONObject("timer").getInt("years"));
                            item.setMonths(jsonObject.getJSONObject("DISCOUNTS").getJSONObject("timer").getInt("months"));
                            item.setDays(jsonObject.getJSONObject("DISCOUNTS").getJSONObject("timer").getInt("days"));
                            item.setHours(jsonObject.getJSONObject("DISCOUNTS").getJSONObject("timer").getInt("hours"));
                            item.setMinutes(jsonObject.getJSONObject("DISCOUNTS").getJSONObject("timer").getInt("minutes"));
                            item.setSeconds(jsonObject.getJSONObject("DISCOUNTS").getJSONObject("timer").getInt("seconds"));
                        }*/


//                    if (priceObject.getInt("PRICE_OLD")!=0){
//                        item.setDiscPercent(priceObject.getInt("PERCENT"));
//                    }
                        productsArrayList.add(item);
                    }
                }
                else{
//                    Toast.makeText(getActivity(), "There is no further", Toast.LENGTH_SHORT).show();
                    initializeData();
                    mAdapter.hideFooter();
//                    Snackbar snackbar =Snackbar.make(getActivity().findViewById(android.R.id.content), "There is no further", Snackbar.LENGTH_LONG)
//                            .setActionTextColor(Color.RED);
//
//                    View snackbarView = snackbar.getView();
//
//                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                    textView.setTextColor(Color.WHITE);
//                    snackbar.show();
                }



               /* if(getActivity()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
                    progressBar.startAnimation(animation);
                }*/
                progressLayout.setVisibility(View.GONE);
                loadMore.setVisibility(View.GONE);
                mainLayout.setVisibility(View.GONE);
                shouldLoadMore = false;
                initializeData();
                if (mSwipeRefreshLayout!=null && mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(getActivity(), "There is no further", Toast.LENGTH_SHORT).show();
//                Snackbar.make(getActivity().findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_SHORT)
//                        .setActionTextColor(Color.RED)
//                        .show();
                /*if(getActivity()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
                    progressBar.startAnimation(animation);
                }*/
                progressLayout.setVisibility(View.GONE);
                loadMore.setVisibility(View.GONE);
                mainLayout.setVisibility(View.GONE);
                shouldLoadMore = false;
                initializeData();
                mAdapter.hideFooter();
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
            if(!mSwipeRefreshLayout.isRefreshing() && pageCount == 1) {
                progressLayout.setVisibility(View.VISIBLE);
            }
        }
    }



    private void initializeData(){
        if (pageCount==1){
            mAdapter = new DragonBallAdapter(getActivity());
//            mAdapter = new HotDealsAdapter(getActivity(), productsArrayList);
            alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
            alphaAdapter.setDuration(400);
            DragonBallFooter footer = getFooter();
            mAdapter.setItems(productsArrayList);
            if(productsArrayList.size()==0){
                messageTextView.setVisibility(View.VISIBLE);
            }
            else{
                mAdapter.setFooter(footer);
                messageTextView.setVisibility(View.INVISIBLE);
            }
            mRecyclerView.setAdapter(alphaAdapter);
            mRecyclerView.setItemAnimator(null);
            mLayoutManager = new StaggeredGridLayoutManager(2,  1);
//            mLayoutManager = new GridLayoutManager(getActivity(), 2);
            mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
//            mLayoutManager.offsetChildrenVertical(0);
            mRecyclerView.setLayoutManager(mLayoutManager);


        }
        else{
//            mAdapter.notifyDataSetChanged();
            mAdapter.setItems(productsArrayList);
//            adapter.notifyDataSetChanged();
            alphaAdapter.notifyDataSetChanged();
        }

    }

    public DragonBallFooter getFooter() {
        String loadMoreMessage = "Loading More..";
        return new DragonBallFooter(loadMoreMessage);
    }

}
