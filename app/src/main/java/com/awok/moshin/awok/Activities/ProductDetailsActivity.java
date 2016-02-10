package com.awok.moshin.awok.Activities;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ParseError;
import com.awok.moshin.awok.Fragments.HotDealsFragment;
import com.awok.moshin.awok.Fragments.ProductDescriptionFragment;

import com.awok.moshin.awok.Fragments.ProductOverViewFragment;
import com.awok.moshin.awok.Fragments.ProductRecommendedRelatedFragment;
import com.awok.moshin.awok.Fragments.ProductSpecificationFragment;
import com.awok.moshin.awok.Fragments.ReviewsFragment;
import com.awok.moshin.awok.Fragments.ShippingDeliveryFrag;
import com.awok.moshin.awok.Fragments.StoreRatingFragment;
import com.awok.moshin.awok.Models.DescriptionModel;
import com.awok.moshin.awok.Models.ProductDetailsModel;
import com.awok.moshin.awok.Models.ProductOverview;
import com.awok.moshin.awok.Models.ProductRatingModel;
import com.awok.moshin.awok.Models.ProductRatingPageModel;
import com.awok.moshin.awok.Models.StoreRatingModel;
import com.awok.moshin.awok.Models.productOverviewRating;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shon on 9/9/2015.
 */
public class ProductDetailsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private DrawerLayout mDrawerLayout;
    private TabLayout tabLayout;
    ProgressBar progressBar;
    private RelativeLayout swipe;
    JSONArray spec=new JSONArray();
    JSONArray shipReturnData=new JSONArray();
    private int lengthComment=0;
    JSONArray recommendedArray=new JSONArray();
    LinearLayout progressLayout;
    String condition="";
    ArrayList<ProductDetailsModel> productDetailsList = new ArrayList<ProductDetailsModel>();
    ProductDetailsModel productDetails=new ProductDetailsModel();
    ProductOverview productOverview=new ProductOverview();
    private TextView prodNewPrice,prodOldPrice;
    private Button buyNow,save;
    Map<String,String> productSpec = new HashMap<String,String>();
    private String imageData,imageProductRating,boughtBy ,savedBy,htmlDescription;
    String productId,productName, newPrice, oldPrice, image, description,rating,ratingCount,storeId;
    String catId="";
    private  ViewPager viewPager;
    JSONObject dataToSend;
    JSONArray jsonDescriptionData;
    JSONArray jsonRatingArray;
    MenuItem searchItem;
    ArrayList<String> imageString = new ArrayList<String>();
    String returnPolicies,estimatedPrice,country,estimatedDays,shipFrom;
    SearchView searchView;
    private int imagesCount;
    private String urlRecommended;
    private String ProductType;
    SharedPreferences mSharedPrefs;
    View logView;
    ViewPager calls;
    private String dataArray="";
    //productOverviewRating prodORating=new productOverviewRating();
    DescriptionModel descData=new DescriptionModel();
    //ProductRatingModel prodRatingData=new ProductRatingModel();
  //  ProductRatingPageModel prodRatingPageData=new ProductRatingPageModel();
    StoreRatingModel storeRatingModel=new StoreRatingModel();
    private List<DescriptionModel> descModel = new ArrayList<DescriptionModel>();
    private List<ProductRatingModel> prodRating = new ArrayList<ProductRatingModel>();
    private List<ProductRatingPageModel> prodPageRating = new ArrayList<ProductRatingPageModel>();
    private List<StoreRatingModel> storeRatingData = new ArrayList<StoreRatingModel>();
    private List<productOverviewRating> prodOverViewRating=new ArrayList<productOverviewRating>();
    String storeName,storeSum,storeAverage,storeImage,storeUrl;
    private Button mCounter;
    ProductOverViewFragment productOverViewFragment;
    ShippingDeliveryFrag shippingDeliveryFrag;
    ProductDescriptionFragment productDescriptionFragment;
    ReviewsFragment reviewsFragment;
    ProductSpecificationFragment prodSpecification;
    ShippingDeliveryFrag shipDeliveryReturn;
    private String  SKU,UID,SSID;
    StoreRatingFragment storeRatingFragment;
    String variantsArrayString, specsArrayStrin;
private String color="";
    private String outOfStock="";


    Adapter adapter = new Adapter(getSupportFragmentManager());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        /*final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);*/
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
        Intent i=new Intent();
        productId =getIntent().getExtras().getString(Constants.PRODUCT_ID_INTENT);
        ProductType=getIntent().getExtras().getString("type");
        urlRecommended=getIntent().getExtras().getString("typeCid");
        System.out.println("HdcgshDCAS"+urlRecommended);
        System.out.println("ID" + productId.toString());
        //productId="5502";
        productName=getIntent().getExtras().getString(Constants.PRODUCT_NAME_INTENT);
        System.out.println("productName" + productName.toString());
  //      rating=getIntent().getExtras().getString(Constants.PRODUCT_RATING);
//        System.out.println("RATING" + rating.toString());
    //    ratingCount=getIntent().getExtras().getString(Constants.PRODUCT_RATING_COUNT);
  //      rating="2";

 //       ratingCount="64";

        catId =getIntent().getExtras().getString(Constants.CAT_ID_INTENT);
        newPrice=getIntent().getExtras().getString(Constants.PRODUCT_PRICE_NEW_INTENT);
        oldPrice =getIntent().getExtras().getString(Constants.PRODUCT_PRICE_OLD_INTENT);
        System.out.println("variantsArrayString: " + oldPrice);
        image =getIntent().getExtras().getString(Constants.PRODUCT_IMAGE_INTENT);
        outOfStock=getIntent().getExtras().getString(Constants.PRODUCT_OUT_STOCK);
        System.out.println(image + " fjkgxfjkhkjfhkjfh");
        description =getIntent().getExtras().getString(Constants.PRODUCT_DESCRIPTION_INTENT);
        Log.v("Product DetailView", productId);


   /*     productId ="3653";
        productName="Apple iPhone 4s 64GB, White";
        rating="2";

        ratingCount="64";
        catId ="5601583e3b90a71c150000aa";
        newPrice="200";
        oldPrice ="500";
        image ="http://dev.alifca.com//upload/resize_cache/iblock/93e/120_200_1/93e97fd36ceeb9238f4fdff94af9ead8.jpg";

        description ="call";*/

        progressBar = (ProgressBar) findViewById(R.id.marker_progress);
        progressLayout = (LinearLayout) findViewById(R.id.progressLayout);
        progressLayout.setVisibility(View.GONE);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);

swipe=(RelativeLayout)findViewById(R.id.swipe);
swipe.setVisibility(View.GONE);



       // onAttach();

        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
           // new APIClient(ProductDetailsActivity.this, getApplicationContext(),  new GetProductDetailsCallback()).productDetailsAPICall(productId);
            if(ProductType.equals("BP"))
            {
                new APIClient(ProductDetailsActivity.this, getApplicationContext(),  new GetProductDetailsCallback()).productBundleDetailsAPICall(productId);
            }
            else {
                new APIClient(ProductDetailsActivity.this, getApplicationContext(),  new GetProductDetailsCallback()).productDetailsAPICall(productId);
            }
          //  new APIClient(ProductDetailsActivity.this, getApplicationContext(),  new GetProductDetailsCallback()).productDetailsAPICall(productId);
        } else {
            /*Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();*/

            Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }

        //productD.setName("COOL");
        //productDetails.setName("COLD");
        buyNow=(Button)findViewById(R.id.prod_buyNow);
        buyNow.setTransformationMethod(null);
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ProductDetailsActivity.this, ProductOptionsActivity.class);
                i.putExtra(Constants.PRODUCT_ID_INTENT, productId);
                i.putExtra(Constants.PRODUCT_NAME_INTENT, productName);
                i.putExtra(Constants.PRODUCT_PRICE_NEW_INTENT, newPrice);
                i.putExtra(Constants.PRODUCT_IMAGE_INTENT, image);
                i.putExtra("SKU",SKU);
                i.putExtra("UID",UID);
                i.putExtra("SSID",SSID);



                System.out.println("variantsArrayString: " + variantsArrayString);
                if(variantsArrayString!=null){
                    i.putExtra(Constants.PRODUCT_VARIANTS_INTENT, variantsArrayString);
                }
                else{
                    variantsArrayString = "";
                    Toast.makeText(ProductDetailsActivity.this, "Variants are not coming from server!", Toast.LENGTH_LONG).show();
                    i.putExtra(Constants.PRODUCT_VARIANTS_INTENT, variantsArrayString);
                }

                System.out.println("specsArrayString: "+specsArrayStrin);
                if(specsArrayStrin!=null){
                    i.putExtra(Constants.PRODUCT_SPECS_INTENT, specsArrayStrin);
                }
                else{
                    specsArrayStrin = "";
                    Toast.makeText(ProductDetailsActivity.this, "SPECS are not coming from server!", Toast.LENGTH_LONG).show();
                    i.putExtra(Constants.PRODUCT_SPECS_INTENT, specsArrayStrin);
                }
                startActivityForResult(i, 1);


//                buyNow.setEnabled(false);
//                buyNow.setBackgroundColor(getResources().getColor(R.color.border));
//                buyNow.setTextColor(getResources().getColor(R.color.button_text));
//                HashMap<String,Object> addToCartData=new HashMap<String, Object>();
//
//
//
//                addToCartData.put("user_id", "55f6a9462f17f64a9b5f5ce4");
//
//                //addToCartData.put("user_id", "55f6a9e52f17f64a9b5f5ce5");
//                addToCartData.put("product_id", productId);
//                addToCartData.put("shipping_method","55f6ab5e2f17f64a9b5f5ce6");
//                addToCartData.put("quantity",1);
//
//
//
//                 dataToSend=new JSONObject(addToCartData);
//
//System.out.println(dataToSend.toString());
//                new APIClient(ProductDetailsActivity.this, getApplicationContext(),  new GetAddToCartCallBack()).addToCartAPICall(dataToSend.toString());


            }
        });
        prodNewPrice=(TextView)findViewById(R.id.prod_price);
        prodOldPrice=(TextView)findViewById(R.id.prod_discountPrice);
        prodOldPrice.setPaintFlags(prodOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        if(oldPrice.equals(null)||oldPrice.equals("")||oldPrice.equals(" AED 0")||oldPrice.equals("AED 0")) {

            //prodOldPrice.setText("OUT OF STOCK");
            prodOldPrice.setVisibility(View.GONE);
        }
        else
        {
            prodOldPrice.setText(oldPrice);
        }
        if(outOfStock.equals("Y"))
        {
            buyNow.setEnabled(false);
            buyNow.setBackgroundColor(getResources().getColor(R.color.border));
            buyNow.setTextColor(getResources().getColor(R.color.button_text));
        }
        if(newPrice.equals(null)||newPrice.equals("")||newPrice.equals(" AED 0")) {

            prodNewPrice.setText("OUT OF STOCK");
            prodOldPrice.setVisibility(View.GONE);
        }
        else
        {
            prodNewPrice.setText(newPrice);
        }
      //  prodOldPrice.setText(oldPrice);
        productDetails.setName(productName);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
       // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
      //  ab.setTitle(productName);
        ab.setTitle("Details");

//        ab.setIcon(R.drawable.back_button);
        ab.setHomeAsUpIndicator(R.drawable.back_button);



        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setUpTab();


       /* NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }*/



      // // CollapsingToolbarLayout collapsingToolbar =
     //  //         (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //collapsingToolbar.setExpandedTitleColor(Color.parseColor("#3e3e3e"));

    //    //collapsingToolbar.setTitle("Mobiles");
//        collapsingToolbar.setTitleEnabled(false);

        /*FloatingActionButton floatButton=(FloatingActionButton)findViewById(R.id.floatButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ProductDetailsActivity.class);
                startActivity(i);
            }
        });*/



        /*buyNow.setEnabled(false);

       /* buyNow.setEnabled(false);
>>>>>>> 5b056ff3820f54cf3441ca332b26a526f1f5a246
        buyNow.setBackgroundColor(getResources().getColor(R.color.border));
        buyNow.setTextColor(getResources().getColor(R.color.button_text));*/



    }


    public void goTo()
    {
        calls.setCurrentItem(4);
    }


public void setUpTab()
{
    viewPager = (ViewPager) findViewById(R.id.viewpager);
    if (viewPager != null) {
        setupViewPager(viewPager);
    }

    tabLayout = (TabLayout) findViewById(R.id.tabs);
    //tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#cd2127"));
    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    tabLayout.setTabTextColors(getResources().getColor(R.color.normal_text), getResources().getColor(R.color.header_text));
    tabLayout.setupWithViewPager(viewPager);
    //tabLayout.setupWithViewPager(viewPager);
    //tabLayout.setEnabled(false);



}





    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }



    private void setupViewPager(ViewPager viewPager) {
        calls=viewPager;



        productOverViewFragment=new ProductOverViewFragment(productId, productName,image,prodRating,estimatedDays,estimatedPrice,condition);
        System.out.println("GVDH"+estimatedDays);
        System.out.println("GVDH" + estimatedPrice);
        //productOverViewFragment=new ProductOverViewFragment();
        //shippingDeliveryFrag=new ShippingDeliveryFrag(returnPolicies,estimatedPrice,country,estimatedDays,shipFrom);
      ///////////////////  shippingDeliveryFrag=new ShippingDeliveryFrag();
        productDescriptionFragment=new ProductDescriptionFragment();
      //////////////////  reviewsFragment=new ReviewsFragment();
        prodSpecification=new ProductSpecificationFragment();
        shipDeliveryReturn=new ShippingDeliveryFrag();
      ////  storeRatingFragment=new StoreRatingFragment();
        adapter.addFragment(productOverViewFragment, "Overview");

      ////////  adapter.addFragment(new HotDealsFragment(catId), "Related");
        if(dataArray.equals(""))
        {
            adapter.addFragment(new ProductRecommendedRelatedFragment(urlRecommended,catId,ProductType,dataArray), "Related");
        }
        else
        {
            adapter.addFragment(new ProductRecommendedRelatedFragment(urlRecommended,catId,ProductType,dataArray), "Recommended");
        }



        //adapter.addFragment(new ProductDescriptionFragment(description), "Description");
        if(!ProductType.equals("BP")) {
            adapter.addFragment(productDescriptionFragment, "Description");
            adapter.addFragment(prodSpecification,"Specification");
        }

        adapter.addFragment(shipDeliveryReturn,"Shipping & Return");
        //adapter.addFragment(new ReviewsFragment(productName,image,rating,ratingCount),"Product Rating");
 ////////////////       adapter.addFragment(reviewsFragment,"Product Rating");
    //////////    adapter.addFragment(shippingDeliveryFrag,"Shipping Info");
        //adapter.addFragment(new StoreRatingFragment(productName,image,rating,ratingCount),"Store Rating");
  ////////      adapter.addFragment(storeRatingFragment,"Store Rating");

        //adapter.addFragment(new ProductDescriptionFragment(description), "Description");
        //adapter.addFragment(new ReviewsFragment(productName,image,rating,ratingCount),"Product Rating");
    ////////////////////////    adapter.addFragment(new ShippingDeliveryFrag(), "Shipping Info");
        //adapter.addFragment(new StoreRatingFragment(productName, image, rating, ratingCount), "Store Rating");











        viewPager.setAdapter(adapter);



    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public boolean onQueryTextSubmit(String query) {
        if(query.equalsIgnoreCase("")){
            /*Snackbar.make(ProductDetailsActivity.this.findViewById(android.R.id.content), "Please type some thing to search Awok", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();*/
            Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Please type some thing to search Awok", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
        else{
            Intent i = new Intent(ProductDetailsActivity.this, SearchActivity.class);
            i.putExtra(Constants.SEARCH_FILTER_INTENT, query);
            startActivity(i);
            searchItem.collapseActionView();
        }


        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public void goToView() {
        calls.setCurrentItem(3);
    }


    static class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //mDrawerLayout.openDrawer(GravityCompat.START);
                onBackPressed();
                return true;



            case R.id.app_cart:
            {
                Intent i = new Intent(ProductDetailsActivity.this, CheckOutActivity.class);
                startActivity(i);
            }


            case R.id.badge:
            {
                Intent i = new Intent(ProductDetailsActivity.this, DisputeList.class);
                i.putExtra("user_id","55f6a9462f17f64a9b5f5ce4");
                startActivity(i);
            }




        }

        return super.onOptionsItemSelected(item);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_productdetails, menu);
        //View count = menu.findItem(R.id.action_search).getActionView();
        /*notifCount = (Button) count.findViewById(R.id.notif_count);
        notifCount.setText("2");*/
        /*MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.testcount);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);

        TextView tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        tv.setText("12");*/
        /*MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.testcount);
        notifCount = (Button)MenuItemCompat.getActionView(item);
        notifCount.setText(String.valueOf(mNotifCount));*/
        /*int badgeCount=5;
        if (badgeCount > 0) {
            ActionItemBadge.update(this, menu.findItem(R.id.item_samplebadge), getDrawable(R.drawable.search_icon), ActionItemBadge.BadgeStyles.DARK_GREY, badgeCount);
        } else {
            ActionItemBadge.hide(menu.findItem(R.id.item_samplebadge));
        }*/

        //If you want to add your ActionItem programmatically you can do this too. You do the following:
       ////////// new ActionItemBadgeAdder().act(this).menu(menu).title("COOL").itemDetails(0, 2, 1).showAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS).add(badgeCount);
        /*View count = menu.findItem(R.id.badge).getActionView();
       Button notifCount = (Button) count.findViewById(R.id.notif_count);*/

        RelativeLayout badgeLayout = (RelativeLayout) menu.findItem(R.id.app_cart).getActionView();
         mCounter = (Button) badgeLayout.findViewById(R.id.button);
        badgeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductDetailsActivity.this, CheckOutActivity.class);
                startActivity(i);
            }
        });

        if (mSharedPrefs.contains(Constants.APP_CART_COUNT)) {

            String countCart=mSharedPrefs.getString(Constants.APP_CART_COUNT,"");

            mCounter.setText(countCart);
        }
        else
        {
            mCounter.setText("0");
        }

        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        ImageView closeButton = (ImageView)searchView.findViewById(R.id.search_close_btn);
        searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));


        return true;
    }


//    public class GetAddToCartCallBack extends AsyncCallback {
//        public void onTaskComplete(String response) {
//            try {
//                JSONObject mMembersJSON;
//                mMembersJSON = new JSONObject(response);
//                System.out.println(mMembersJSON);
//
//                Intent i=new Intent(ProductDetailsActivity.this,CheckOutActivity.class);
//                startActivity(i);
//
//                progressLayout.setVisibility(View.GONE);
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//                /*Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
//                        .setActionTextColor(Color.RED)
//                        .show();*/
//                Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Data could not be Loaded", Snackbar.LENGTH_LONG)
//                        .setActionTextColor(Color.RED);
//
//                View snackbarView = snackbar.getView();
//
//                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                textView.setTextColor(Color.WHITE);
//                snackbar.show();
//            }
//        }
//        @Override
//        public void onTaskCancelled() {
//        }
//        @Override
//        public void onPreExecute() {
//            // TODO Auto-generated method stub
//            progressLayout.setVisibility(View.VISIBLE);
//        }
//    }









    @Override
    public void onResume(){
        super.onResume();
        invalidateOptionsMenu();
//        buyNow.setEnabled(true);
//        buyNow.setBackgroundColor(getResources().getColor(R.color.button_bg));

    }



    public class GetProductDetailsCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {










                JSONObject mMembersJSON;
                mMembersJSON = new JSONObject(response);
                System.out.println("response: " + mMembersJSON);










                if(mMembersJSON.getJSONObject("STATUS").getInt("CODE")==(200))


                {



                    JSONObject dataObj = mMembersJSON.getJSONObject("OUTPUT");
                    if(dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("SPECIFICATIONS").length()>0) {
                         spec = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("SPECIFICATIONS");
                    }
                    System.out.println("SPEC"+spec);
                    if(dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("SHIPPING_AND_RETURNS").length()>0) {
                         shipReturnData = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("SHIPPING_AND_RETURNS");
                    }
                    System.out.println("SPEC"+spec);
                    if(dataObj.getJSONObject("DATA").getJSONArray("RECOMMENDED_PRODUCTS").length()>0) {
                        recommendedArray = dataObj.getJSONObject("DATA").getJSONArray("RECOMMENDED_PRODUCTS");
                        dataArray=recommendedArray.toString();







                    }

                    System.out.println("SPEC"+spec);
                    //  jsonDescriptionData=dataObj.getJSONArray("description");
                    ////////////////////////////////               variantsArrayString = dataObj.getJSONArray("variants").toString();
                    //////////////////////////////////////               specsArrayStrin = dataObj.getJSONObject("specs").toString();
                    if(dataObj.getJSONObject("DATA").getJSONObject("DETAILS").has("VARIANTS")) {
                        if (dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("VARIANTS").getJSONObject("COMBINATIONS").toString().isEmpty()) {

                        } else {
                            variantsArrayString = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("VARIANTS").getJSONObject("COMBINATIONS").toString();
                        }


                        specsArrayStrin = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("VARIANTS").toString();
                        System.out.println("variantsArrayString" + variantsArrayString);
                        System.out.println("specsArrayStrin" + specsArrayStrin);
//                System.out.println("JDATA" + jsonDescriptionData.toString());
                    }
                    if(dataObj.getJSONObject("DATA").getJSONObject("DETAILS").has("DESCRIPTION")) {
                        if (dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getString("DESCRIPTION").equals("")) {
                            htmlDescription = "";
                        } else {
                            htmlDescription = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getString("DESCRIPTION");
                        }
                    }
                    SKU = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("CART_DATA").getString("SKU");
                    UID = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("CART_DATA").getString("UID");
                    SSID = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("CART_DATA").getString("SSID");

              /*  for(int i=0;i<jsonDescriptionData.length();i++)
                {
                    JSONObject data=jsonDescriptionData.getJSONObject(i);
                    //String jsonData=imagesStringData.get(i).toString();
                    descData.setDescHeader(data.getString("head"));
                    descData.setDescData(data.getString("content"));
System.out.println(data.getString("head"));
                    System.out.println(data.getString("content"));
descModel.add(descData);
                }*/
                    //imageProductRating=mMembersJSON.getString("image");
                    if(dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PROPERTIES").has("SOLD_COUNT"))
                    {
                        boughtBy = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PROPERTIES").getString("SOLD_COUNT");
                    }
                    ////////              savedBy=dataObj.getString("saved_by");
/////////////////////////////////////////////////////////////////////////////////////  ////////////////                  System.out.println("OBJ" + dataObj.getJSONObject("DATA").getJSONObject("PRODUCT_REVIEWS").toString());
////////                System.out.println("MESSAGES"+dataObj.getJSONObject("DATA").getJSONObject("PRODUCT_REVIEWS").getJSONArray("MESSAGES").toString());
                    /////////////////////////////////////////////////////////////////////////////////////////////////////////////                   JSONObject jsonRatingObject = dataObj.getJSONObject("DATA").getJSONObject("PRODUCT_REVIEWS").optJSONObject("MESSAGES");
              /*  if(jsonRatingObject == null) {
                    jsonRatingArray=dataObj.getJSONObject("DATA").getJSONObject("PRODUCT_REVIEWS").optJSONArray("MESSAGES");
                }
                else
                {
                    System.out.println("NULLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
                }*/
/////////////////////                    estimatedDays = (dataObj.getJSONObject("DATA").getJSONObject("SHIPPING").getString("PERIOD_FROM")) + " - " + (dataObj.getJSONObject("DATA").getJSONObject("SHIPPING").getString("PERIOD_TO")) + " days";
//////////////////////                    estimatedPrice = "AED " + (dataObj.getJSONObject("DATA").getJSONObject("SHIPPING").getString("PRICE"));
                    if(dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PROPERTIES").has("CONDITION"))
                    {
                        condition = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PROPERTIES").getString("CONDITION");
                    }
                    imagesCount = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("IMAGES").length();
                    JSONArray imagesStringData = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("IMAGES");
                    for (int i = 0; i < imagesStringData.length(); i++) {
                        //JSONObject data=imagesStringData.getJSONObject(i);
                        String jsonData = imagesStringData.get(i).toString();
                        imageString.add(jsonData);
                        System.out.println("pfbhgvxc" + imageString);
                        //baseImage=imagesStringData.get(i).toString();

                    }
                    String ratingMain = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("RATING").getString("RATING");
                    String reviewCount = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("RATING").getString("COUNT");

                    int colorLength = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("SPECIFICATIONS").length();
                    JSONArray colorArray = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("SPECIFICATIONS");
                    ArrayList<String> colorArrayList = new ArrayList<>();
                    for (int j = 0; j < colorLength; j++) {
                        JSONObject colorData = colorArray.getJSONObject(j);
                        if (colorData.getString("CODE").equals("COLOR")) {
                            //  prod_color_default.setText(colorData.getString("VALUE").toString());
                            color = colorData.getString("VALUE").toString();
                        }


                    }
                    if(ProductType.equals("BP")) {
                        if (dataObj.getJSONObject("DATA").getJSONObject("DETAILS").has("CHILDREN")) {
                             lengthComment = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("CHILDREN").getJSONArray("ITEMS").length();
                        }
                    }
                            prodRating.add(new ProductRatingModel(productId, productName, image, estimatedDays, estimatedPrice, reviewCount, ratingMain, imageString, lengthComment, Integer.toString(imagesCount), true));

                    if(ProductType.equals("BP")) {
                        if (dataObj.getJSONObject("DATA").getJSONObject("DETAILS").has("CHILDREN")) {
                            JSONArray bundleData = dataObj.getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("CHILDREN").getJSONArray("ITEMS");
                            int bundleLength = bundleData.length();
                            for (int k = 0; k < bundleLength; k++) {
                                ProductRatingModel prodRatingData = new ProductRatingModel();

                                JSONObject jObj = bundleData.getJSONObject(k);


                                prodRatingData.setBundleName(jObj.getString("NAME"));
                                prodRatingData.setBundleId(jObj.getString("ID"));
                                prodRatingData.setBundleWarranty(jObj.getString("WARRANTY"));
                                prodRatingData.setBundleProperties(jObj.getJSONArray("PROPERTIES").toString());
                                prodRatingData.setBundleNewPrice(jObj.getJSONObject("PRICES").getString("PRICE_NEW"));
                                prodRatingData.setBundleOldPrice(jObj.getJSONObject("PRICES").getString("PRICE_OLD"));
                                prodRatingData.setBundleDiscount(jObj.getJSONObject("PRICES").getString("DISCOUNT"));
                                prodRatingData.setBundlePercent(jObj.getJSONObject("PRICES").getString("PERCENT"));
                                prodRatingData.setBundleImage(jObj.getJSONObject("IMAGES").getString("SRC"));
                                prodRatingData.setBundleRating(jObj.getJSONObject("RATING").getString("RATING"));
                               // prodRatingData.setBundleLength(bundleLength);


                                prodRating.add(prodRatingData);

System.out.println("Bundle");
                            }
                        }
                    }


                    prodRating.add(new ProductRatingModel(color, estimatedDays, estimatedPrice,condition, true, "footer"));

                    if(dataObj.getJSONObject("DATA").has("PRODUCT_REVIEWS"))
                    {
                    jsonRatingArray = dataObj.getJSONObject("DATA").getJSONObject("PRODUCT_REVIEWS").getJSONArray("MESSAGES");
                    int commentsValueSize = dataObj.getJSONObject("DATA").getJSONObject("PRODUCT_REVIEWS").getJSONArray("MESSAGES").length();
            ///////////////////        prodRating.add(new ProductRatingModel(productId, productName, image, estimatedDays, estimatedPrice, reviewCount, ratingMain, imageString, commentsValueSize, Integer.toString(imagesCount), true));
                    ////////// prodPageRating.add(new ProductRatingPageModel(productName,image,rating,ratingCount,boughtBy,savedBy,true));
                    prodPageRating.add(new ProductRatingPageModel(productName, image, ratingMain, reviewCount, boughtBy, commentsValueSize, true));
                    ///////              storeId=dataObj.getJSONObject("store_id").getString("$id");
                    for (int j = 0; j < jsonRatingArray.length(); j++) {
                        productOverviewRating prodORating = new productOverviewRating();
                        ProductRatingPageModel prodRatingPageData = new ProductRatingPageModel();
                        ProductRatingModel prodRatingData = new ProductRatingModel();
                        JSONObject dataRating = jsonRatingArray.getJSONObject(j);
                        //String jsonData=imagesStringData.get(i).toString();
                        prodRatingData.setContent(dataRating.getString("MESSAGE"));
                        /////////////////////          prodRatingData.setRate(dataRating.getJSONObject("data").getString("rate"));
                        prodRatingData.setUsername(dataRating.getString("NAME"));


                        prodRatingPageData.setContent(dataRating.getString("MESSAGE"));
                        ///////////           prodRatingPageData.setRate(dataRating.getJSONObject("data").getString("rate"));
                        prodRatingPageData.setUsername(dataRating.getString("NAME"));


                   /* if(dataRating.has("days")){
                        prodRatingPageData.setDays(dataRating.getString("days"));
                    }
                    else{
                        prodRatingPageData.setDays(dataRating.getString("created_at"));
                    }*/
                        //prodRatingData.setDays(dataRating.getString("days"));
                        prodRatingData.setDays(dataRating.getString("DATE_UPDATED"));
                        prodRatingData.setDays("2");
                        prodORating.setMainText(dataRating.getString("MESSAGE"));
                        prodORating.setName(dataRating.getString("NAME"));
                        //////////    prodORating.setRating(dataRating.getJSONObject("data").getString("rate"));
                        prodOverViewRating.add(prodORating);
                        prodRating.add(prodRatingData);
                        prodPageRating.add(prodRatingPageData);
                    }
    //////////                prodRating.add(new ProductRatingModel(color, estimatedDays, estimatedPrice, true, "footer"));
                }
               /*  storeName=dataObj.getJSONObject("store").getString("name");
                JSONObject storeRating=dataObj.getJSONObject("store").getJSONObject("rating");
                storeSum=storeRating.getString("number_of_ratings");
                         storeAverage=storeRating.getString("rating_average");
                 storeImage=dataObj.getJSONObject("store").getString("image");
                 storeUrl=dataObj.getJSONObject("store").getString("url");
                JSONArray storeComments=dataObj.getJSONObject("store").getJSONArray("comments");
                for(int k=0;k<storeComments.length();k++)
                {

                    JSONObject jsonStroreData=storeComments.getJSONObject(k);
                    storeRatingModel.setUsername(jsonStroreData.getString("username"));
                    storeRatingModel.setContent(jsonStroreData.getJSONObject("data").getString("content"));
                    storeRatingModel.setRate(jsonStroreData.getJSONObject("data").getString("rate"));
                    if(jsonStroreData.has("days")){
                        storeRatingModel.setDays(jsonStroreData.getString("days"));
                    }
                    else{
                        storeRatingModel.setDays(jsonStroreData.getString("created_at"));
                    }











                    storeRatingData.add(storeRatingModel);



                }*/


          /*      JSONObject shippingData=dataObj.getJSONObject("shipping_data");
                 estimatedPrice=shippingData.getString("shipping_cost");
                         country=shippingData.getString("location_name");
                                 estimatedDays=shippingData.getString("est_from") +" to " + shippingData.getString("est_to");

                         shipFrom=   shippingData.getString("ships_from");
                         returnPolicies=shippingData.getString("return_policy");*/


////////////////////                    shipFrom = dataObj.getJSONObject("DATA").getJSONObject("SHIPPING").getString("SHIPS_FROM");
                    ///////////////////////////////////////////////  country = dataObj.getJSONObject("DATA").getJSONObject("SHIPPING").getString("SHIPS_FROM");


//ShippingDeliveryFrag sf=new ShippingDeliveryFrag();

                    //      mCallback.onArticleSelected(2);


                    ////////////  shippingDeliveryFrag.call(returnPolicies, estimatedPrice, country, estimatedDays, shipFrom);
///////////                    shippingDeliveryFrag.call("Hello world", estimatedPrice, country, estimatedDays, shipFrom);
                    ////////////////////// reviewsFragment.call(prodPageRating,productName,image,rating,ratingCount,boughtBy,savedBy,productId);

                    shipDeliveryReturn.call(shipReturnData.toString());
/////////////////                    reviewsFragment.call(prodPageRating, productName, image, ratingMain, reviewCount, boughtBy,productId);
                    //productDescriptionFragment.call(descModel);
                    if(!ProductType.equals("BP")) {
                        productDescriptionFragment.call(htmlDescription, productName);
                        prodSpecification.call(spec.toString());
                    }
                    /////////            storeRatingFragment.call(storeRatingData, storeName, storeSum, storeAverage, storeImage, storeUrl,storeId);

//adapter.notifyDataSetChanged();

                    swipe.setVisibility(View.VISIBLE);
                    Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_up);
// Start animation
                    swipe.startAnimation(slide_up);

                }


           //     tabLayout.setEnabled(true);


               /* buyNow.setEnabled(true);
                buyNow.setBackgroundColor(getResources().getColor(R.color.red_base));
                buyNow.setTextColor(getResources().getColor(R.color.button_text));*/




              //  productOverViewFragment.call(prodOverViewRating,productId, productName,image);

                //mResources=

//                if(getApplicationContext()!=null){
//                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
//                    progressBar.startAnimation(animation);
//                }
//                progressBar.setVisibility(View.GONE);
                // initializeData();
            } catch (JSONException e) {
                e.printStackTrace();
                /*Snackbar.make(getActivity().findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();*/

                Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Data could not be loaded", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED);

                View snackbarView = snackbar.getView();

                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
            }
        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
//            progressBar.setVisibility(View.VISIBLE);
        }
    }

}
