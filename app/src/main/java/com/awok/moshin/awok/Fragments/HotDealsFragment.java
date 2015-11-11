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
    HotDealsAdapter mAdapter;
    AlphaInAnimationAdapter alphaAdapter;
    View mView;
    LinearLayout progressLayout;
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
    private boolean shouldLoadMore = false;
    private LinearLayout mainLayout;
    private boolean isSearch = false;
    private ImageButton gotoTopButton;
    LinearLayout.LayoutParams params;
    private String searchString = null;
    int firstVisibleItem, visibleItemCount, totalItemCount, lastVisibleItem;
    ArrayList<String> tagsFilterArray = new ArrayList<String>();
    ArrayList<String> colorFilterArray = new ArrayList<String>();
    ArrayList<String> priceFilterArray = new ArrayList<String>();
    ArrayList<String> sizeFilterArray = new ArrayList<String>();
    ArrayList<String> brandFilterArray = new ArrayList<String>();
    ArrayList<String> ratingsFilterArray = new ArrayList<String>();
    StaggeredGridLayoutManager mLayoutManager;
    String filterString = "";
    boolean isFilter = false;
    LinearLayout filterButtonLayout;
    public HotDealsFragment(){}

    public HotDealsFragment(String categoryId)
    {
        this.categoryId = categoryId;
    }

   /* public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                System.out.println("CLICLED ITEM");
                return true;
            case R.id.app_cart:
                System.out.println("CLICLED ITEM");
                return true;

        }
        return super.onOptionsItemSelected(item);
    }*/

    public HotDealsFragment(String searchString, boolean isSearch)
    {
        this.isSearch = isSearch;
        this.searchString = searchString;
        this.searchString = "search="+searchString;
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

    public HotDealsFragment(String categoryId, ArrayList<String> colorArray, ArrayList<String> tagArray, ArrayList<String> price,  ArrayList<String> sizeArray, ArrayList<String> brandArray,
                            ArrayList<String> ratingsArray,boolean isSearch, boolean isFilter)
    {
        this.categoryId = categoryId;
        this.isSearch = isSearch;
        this.tagsFilterArray = tagArray;
        this.colorFilterArray = colorArray;
        this.priceFilterArray = price;
        this.sizeFilterArray = sizeArray;
        this.brandFilterArray = brandArray;
        this.ratingsFilterArray = ratingsArray;
        this.isFilter = isFilter;
        makeSearchString();
    }


    private void makeSearchString(){
        String colorString = "";
        if(colorFilterArray.size()>0){
            if(categoryId!=null){
                colorString = "&color=";
            }else{
                colorString = "color=";
            }
            for(int i=0;i<colorFilterArray.size();i++){
                colorString = colorString+colorFilterArray.get(i)+",";
            }
        }

        String tagString = "";
        if(tagsFilterArray.size()>0){
            tagString = "&tags=";
            for(int i=0;i<tagsFilterArray.size();i++){
                tagString = tagString+tagsFilterArray.get(i)+",";
            }
        }

        String priceString = "";
        if(priceFilterArray.size()>0){
            priceString = "&price=";
            for(int i=0;i<priceFilterArray.size();i++){
                priceString = priceString+priceFilterArray.get(i)+",";
            }
        }


        String sizeString = "";
        if(sizeFilterArray.size()>0){
            sizeString = "&size=";
            for(int i=0;i<sizeFilterArray.size();i++){
                sizeString = sizeString+sizeFilterArray.get(i)+",";
            }
        }

        String ratingsString = "";
        if(ratingsFilterArray.size()>0){
            ratingsString = "&price=";
            for(int i=0;i<ratingsFilterArray.size();i++){
                ratingsString = ratingsString+ratingsFilterArray.get(i)+",";
            }
        }

        String brandString = "";
        if(brandFilterArray.size()>0){
            brandString = "&price=";
            for(int i=0;i<brandFilterArray.size();i++){
                brandString = brandString+brandFilterArray.get(i)+",";
            }
        }

        String filterString = colorString+tagString+priceString+sizeString+ratingsString+brandString;


        if(categoryId!=null){
            searchString = "category_id="+categoryId+filterString;
        }
        else{
            searchString = filterString;
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
     //   params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,    ViewGroup.LayoutParams.WRAP_CONTENT);
       /* params.leftMargin = 2;
        params.rightMargin = 2;
        params.topMargin = 2;*/
    //    params.bottomMargin = 20;
    //    mainLayout.setLayoutParams(params);
      /*  lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.topMargin = 20;
        mainLayout.setLayoutParams(lp);*/



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
                        ActivityOptionsCompat options =
                                ActivityOptionsCompat.makeSceneTransitionAnimation(
                                        getActivity());
                        Intent i=new Intent(getContext(), ProductDetailsActivity.class);
                        i.putExtra(Constants.PRODUCT_ID_INTENT,productsArrayList.get(position).getId());
                        i.putExtra(Constants.PRODUCT_RATING,productsArrayList.get(position).getRating());
                        i.putExtra(Constants.PRODUCT_RATING_COUNT,productsArrayList.get(position).getRatingCount());

                        i.putExtra(Constants.PRODUCT_NAME_INTENT,productsArrayList.get(position).getName());
                        i.putExtra(Constants.PRODUCT_DISCOUNT_PERCENTAGE_INTENT,productsArrayList.get(position).getDiscPercent());
                        i.putExtra(Constants.PRODUCT_IMAGE_INTENT,productsArrayList.get(position).getImage());
                        i.putExtra(Constants.PRODUCT_PRICE_NEW_INTENT,productsArrayList.get(position).getPriceNew());
                        i.putExtra(Constants.PRODUCT_PRICE_OLD_INTENT,productsArrayList.get(position).getPriceOld());
                        i.putExtra(Constants.PRODUCT_DESCRIPTION_INTENT,productsArrayList.get(position).getDescription());
                        i.putExtra(Constants.CAT_ID_INTENT, productsArrayList.get(position).getCategoryId());
                       // ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle();
                        //startActivity(i);





                        ActivityCompat.startActivity(getActivity(), i, options.toBundle());

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

                Log.i(TAG, "lastVisibleItem: " + lastVisibleItem);
                Log.i(TAG, "lastProductItem: " + (productsArrayList.size()-1));

                if(shouldLoadMore && lastVisibleItem==(productsArrayList.size()-1)){
//                    DragonBallFooter footer = new DragonBallFooter("Loading...");
//                    mAdapter.setFooter(footer);
//                    mAdapter.notifyDataSetChanged();
//                    Products item = new Products(true);
//                    productsArrayList.add(item);
//                    mAdapter.notifyDataSetChanged();
                }

                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {


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
        if(isFilter){
            filterButtonLayout.setVisibility(View.VISIBLE);
            for (int i = 0; i < tagsFilterArray.size(); i++) {
                final Button t = (Button) inflater.inflate(R.layout.filter_button_tag, container, false);
                t.setText(tagsFilterArray.get(i));
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
                                                tagsFilterArray.set(index, "");
                                                if(filterButtonLayout.getChildCount()==0){
                                                    searchString = "";
                                                    filterButtonLayout.setVisibility(View.GONE);
                                                    isFilter = false;
                                                    isSearch = false;
                                                    loading = true;
                                                    pageCount = 1;
                                                    previousTotal = 0;
                                                    refreshContent();
                                                    return;
                                                }
//                                                    System.out.println("Child View Count: "+);
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


            /**PopulateColors*****************/
            for (int i = 0; i < colorFilterArray.size(); i++) {
                final Button t = (Button) inflater.inflate(R.layout.filter_button_tag, container, false);
                t.setText(colorFilterArray.get(i));
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
                                                colorFilterArray.set(index, "");
                                                if(filterButtonLayout.getChildCount()==0){
                                                    searchString = "";
                                                    filterButtonLayout.setVisibility(View.GONE);
                                                    isFilter = false;
                                                    isSearch = false;
                                                    loading = true;
                                                    pageCount = 1;
                                                    previousTotal = 0;
                                                    refreshContent();
                                                    return;
                                                }
//                                                    System.out.println("Child View Count: "+);
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



            /**PopulatePrice*****************/
            for (int i = 0; i < priceFilterArray.size(); i++) {
                final Button t = (Button) inflater.inflate(R.layout.filter_button_tag, container, false);
                t.setText(priceFilterArray.get(i));
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
                                                priceFilterArray.set(index, "");
                                                if(filterButtonLayout.getChildCount()==0){
                                                    searchString = "";
                                                    filterButtonLayout.setVisibility(View.GONE);
                                                    isFilter = false;
                                                    isSearch = false;
                                                    loading = true;
                                                    pageCount = 1;
                                                    previousTotal = 0;
                                                    refreshContent();
                                                    return;
                                                }
//                                                    System.out.println("Child View Count: "+);
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





            /**PopulateBRAND*****************/
            for (int i = 0; i < brandFilterArray.size(); i++) {
                final Button t = (Button) inflater.inflate(R.layout.filter_button_tag, container, false);
                t.setText(brandFilterArray.get(i));
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
                                                brandFilterArray.set(index, "");
                                                if(filterButtonLayout.getChildCount()==0){
                                                    searchString = "";
                                                    filterButtonLayout.setVisibility(View.GONE);
                                                    isFilter = false;
                                                    isSearch = false;
                                                    loading = true;
                                                    pageCount = 1;
                                                    previousTotal = 0;
                                                    refreshContent();
                                                    return;
                                                }
//                                                    System.out.println("Child View Count: "+);
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



            /**PopulateSIZE*****************/
            for (int i = 0; i < sizeFilterArray.size(); i++) {
                final Button t = (Button) inflater.inflate(R.layout.filter_button_tag, container, false);
                t.setText(sizeFilterArray.get(i));
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
                                                sizeFilterArray.set(index, "");
                                                if(filterButtonLayout.getChildCount()==0){
                                                    searchString = "";
                                                    filterButtonLayout.setVisibility(View.GONE);
                                                    isFilter = false;
                                                    isSearch = false;
                                                    loading = true;
                                                    pageCount = 1;
                                                    previousTotal = 0;
                                                    refreshContent();
                                                    return;
                                                }
//                                                    System.out.println("Child View Count: "+);
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


            /**PopulateRATINGS*****************/
            for (int i = 0; i < ratingsFilterArray.size(); i++) {
                final Button t = (Button) inflater.inflate(R.layout.filter_button_tag, container, false);
                t.setText(ratingsFilterArray.get(i));
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
                                                ratingsFilterArray.set(index, "");
                                                if(filterButtonLayout.getChildCount()==0){
                                                    searchString = "";
                                                    filterButtonLayout.setVisibility(View.GONE);
                                                    isFilter = false;
                                                    isSearch = false;
                                                    loading = true;
                                                    pageCount = 1;
                                                    previousTotal = 0;
                                                    refreshContent();
                                                    return;
                                                }
//                                                    System.out.println("Child View Count: "+);
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
        else{
            filterButtonLayout.setVisibility(View.GONE);
            loading = true;
            pageCount = 1;
            previousTotal = 0;
        }
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
                JSONObject mainObject  = null;
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

                    if(obj.getInt("status")==200){
//                        itemCount.setText("We found "+obj.getInt("total_products")+" search results for '"+searchString+"'");
                        jsonArray = obj.getJSONArray("items");
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
                    mainObject = new JSONObject(response);
                    jsonArray = mainObject.getJSONObject("data").getJSONObject("server").getJSONArray("items");
                }

//                JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_PRODUCT_LIST_NAME);
                int length = jsonArray.length();
                if(isSearch){
                    if(isFilter) {
                        itemCount.setVisibility(View.GONE);
                    }
                    else{
                        itemCount.setVisibility(View.VISIBLE);
                        itemCount.setText("We found " + obj.getInt("total_items") + " search results for '" + searchString + "'");
                    }
                }

                if(length>0){
//                    mAdapter.hideFooter();
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
                        item.setId(jsonObject.getJSONObject("_id").getString("$id"));
                        item.setName(jsonObject.getString("name"));
                        item.setImage(jsonObject.getString("image"));
                        item.setImageHeight(jsonObject.getInt("image_height"));
                        item.setCategoryId(jsonObject.getString("category_id"));
                        item.setPriceNew(jsonObject.getJSONObject("discount").getInt("discount_price"));
                        item.setPriceOld(jsonObject.getInt("price"));
                        item.setRating(jsonObject.getJSONObject("rating").getString("rating_average"));
                        item.setRatingCount(jsonObject.getJSONObject("rating").getString("number_of_ratings"));
//                        item.setDescription(jsonObject.getString("description"));
                        item.setDiscPercent(jsonObject.getJSONObject("discount").getInt("discount_percentage"));
//                    if (priceObject.getInt("PRICE_OLD")!=0){
//                        item.setDiscPercent(priceObject.getInt("PERCENT"));
//                    }
                        productsArrayList.add(item);
                    }
                }
                else{
                    Toast.makeText(getActivity(), "There is no further", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "There is no further", Toast.LENGTH_SHORT).show();
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
//            mAdapter = new DragonBallAdapter(getActivity(), productsArrayList);
            mAdapter = new HotDealsAdapter(getActivity(), productsArrayList);
            alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
            mRecyclerView.setAdapter(alphaAdapter);
            mRecyclerView.setItemAnimator(null);
            mLayoutManager = new StaggeredGridLayoutManager(2,  1);
            mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
//            mLayoutManager.offsetChildrenVertical(0);
            mRecyclerView.setLayoutManager(mLayoutManager);
        }
        else{
            mAdapter.notifyDataSetChanged();
            alphaAdapter.notifyDataSetChanged();
        }

    }

}
