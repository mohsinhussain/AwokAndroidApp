package com.awok.moshin.awok.Activities;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import android.support.v4.app.FragmentPagerAdapter;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awok.moshin.awok.Fragments.HotDealsFragment;
import com.awok.moshin.awok.Fragments.ProductDescriptionFragment;
import com.awok.moshin.awok.Fragments.ProductOverViewFragment;

import com.awok.moshin.awok.Fragments.ReviewsFragment;
import com.awok.moshin.awok.Fragments.ShippingDeliveryFrag;
import com.awok.moshin.awok.Fragments.StoreRatingFragment;
import com.awok.moshin.awok.Models.DescriptionModel;
import com.awok.moshin.awok.Models.OrderSummary;
import com.awok.moshin.awok.Models.ProductDetailsModel;
import com.awok.moshin.awok.Models.ProductOverview;
import com.awok.moshin.awok.Models.ProductRatingModel;
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
    ArrayList<ProductDetailsModel> productDetailsList = new ArrayList<ProductDetailsModel>();
    ProductDetailsModel productDetails=new ProductDetailsModel();
    ProductOverview productOverview=new ProductOverview();
    private TextView prodNewPrice,prodOldPrice;
    private Button buyNow,save;
    Map<String,String> productSpec = new HashMap<String,String>();
    private String imageData;
    String productId,productName, newPrice, oldPrice, image, description,rating,ratingCount;
    String catId;
    JSONObject dataToSend;
    JSONArray jsonDescriptionData;
    JSONArray jsonRatingArray;
    MenuItem searchItem;
    SearchView searchView;
    DescriptionModel descData=new DescriptionModel();
    ProductRatingModel prodRatingData=new ProductRatingModel();
    private List<DescriptionModel> descModel = new ArrayList<DescriptionModel>();
    private List<ProductRatingModel> prodRating = new ArrayList<ProductRatingModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        /*final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);*/
        Intent i=new Intent();
        productId =getIntent().getExtras().getString(Constants.PRODUCT_ID_INTENT);
        productName=getIntent().getExtras().getString(Constants.PRODUCT_NAME_INTENT);
        rating=getIntent().getExtras().getString(Constants.PRODUCT_RATING);
        System.out.println("RATING" + rating.toString());
        ratingCount=getIntent().getExtras().getString(Constants.PRODUCT_RATING_COUNT);
        catId =getIntent().getExtras().getString(Constants.CAT_ID_INTENT);
        newPrice=getIntent().getExtras().getString(Constants.PRODUCT_PRICE_NEW_INTENT);
        oldPrice =getIntent().getExtras().getString(Constants.PRODUCT_PRICE_OLD_INTENT);
        image =getIntent().getExtras().getString(Constants.PRODUCT_IMAGE_INTENT);
        description =getIntent().getExtras().getString(Constants.PRODUCT_DESCRIPTION_INTENT);
        Log.v("Product DetailView", productId);
        progressBar = (ProgressBar) findViewById(R.id.marker_progress);
        progressBar.setVisibility(View.GONE);

        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new APIClient(ProductDetailsActivity.this, getApplicationContext(),  new GetProductDetailsCallback()).productDetailsAPICall(productId);
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
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyNow.setEnabled(false);
                buyNow.setBackgroundColor(getResources().getColor(R.color.border));
                buyNow.setTextColor(getResources().getColor(R.color.button_text));
                HashMap<String,Object> addToCartData=new HashMap<String, Object>();



                addToCartData.put("user_id", "55f6a9462f17f64a9b5f5ce4");

                //addToCartData.put("user_id", "55f6a9e52f17f64a9b5f5ce5");
                addToCartData.put("product_id", productId);
                addToCartData.put("shipping_method","55f6ab5e2f17f64a9b5f5ce6");
                addToCartData.put("quantity",1);



                 dataToSend=new JSONObject(addToCartData);

System.out.println(dataToSend.toString());
                new APIClient(ProductDetailsActivity.this, getApplicationContext(),  new GetAddToCartCallBack()).addToCartAPICall(dataToSend.toString());


            }
        });
        prodNewPrice=(TextView)findViewById(R.id.prod_price);
        prodOldPrice=(TextView)findViewById(R.id.prod_discountPrice);
        prodOldPrice.setPaintFlags(prodOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        prodNewPrice.setText(newPrice);
        prodOldPrice.setText(oldPrice);
        productDetails.setName(productName);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
       // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
      //  ab.setTitle(productName);
        ab.setTitle("Details");



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






    }
public void setUpTab()
{
    final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
    if (viewPager != null) {
        setupViewPager(viewPager);
    }

    tabLayout = (TabLayout) findViewById(R.id.tabs);
    //tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#cd2127"));
    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    tabLayout.setTabTextColors(getResources().getColor(R.color.normal_text), getResources().getColor(R.color.header_text));
    tabLayout.setupWithViewPager(viewPager);
}



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {





        switch (item.getItemId()) {
            case android.R.id.home:
                //mDrawerLayout.openDrawer(GravityCompat.START);
                onBackPressed();
                return true;

          /*  case R.id.app_cart:
            {

            }*/
        }

        return super.onOptionsItemSelected(item);


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
    }



    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ProductOverViewFragment(productId, productName,image), "Overview");

        adapter.addFragment(new HotDealsFragment(catId), "Related");
        //adapter.addFragment(new ProductDescriptionFragment(description), "Description");
        adapter.addFragment(new ProductDescriptionFragment(descModel), "Description");
        //adapter.addFragment(new ReviewsFragment(productName,image,rating,ratingCount),"Product Rating");
        adapter.addFragment(new ReviewsFragment(prodRating),"Product Rating");
        adapter.addFragment(new ShippingDeliveryFrag(),"Shipping Info");
        adapter.addFragment(new StoreRatingFragment(productName,image,rating,ratingCount),"Store Rating");










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


    static class Adapter extends FragmentPagerAdapter {
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
    static Button notifCount;
    static int mNotifCount = 2;


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
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        ImageView closeButton = (ImageView)searchView.findViewById(R.id.search_close_btn);
        searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));


        return true;
    }


    public class GetAddToCartCallBack extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject mMembersJSON;
                mMembersJSON = new JSONObject(response);
                System.out.println(mMembersJSON);

                Intent i=new Intent(ProductDetailsActivity.this,CheckOutActivity.class);
                startActivity(i);

                progressBar.setVisibility(View.GONE);


            } catch (JSONException e) {
                e.printStackTrace();
                /*Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();*/
                Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Data could not be Loaded", Snackbar.LENGTH_LONG)
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
            progressBar.setVisibility(View.VISIBLE);
        }
    }









    @Override
    public void onResume(){
        super.onResume();
        buyNow.setEnabled(true);
        buyNow.setBackgroundColor(getResources().getColor(R.color.button_bg));

    }



    public class GetProductDetailsCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {

                JSONObject mMembersJSON;
                mMembersJSON = new JSONObject(response);
                System.out.println("COOLGBDJH" + mMembersJSON);
        /*        System.out.println(mMembersJSON.getString("name"));

                String ratingsCount=mMembersJSON.getJSONObject("rating").getString("average");
                String count=mMembersJSON.getJSONObject("rating").getString("sum");
//                prodNewPrice.setText(Integer.toString(mMembersJSON.getInt("new_price")) + " " + "AED");
//                prodOldPrice.setText(Integer.toString(mMembersJSON.getInt("original_price")) + " " + "AED");
//                System.out.println("COOLGBDJH" + productDetails.getName());
//                String prodDesc=mMembersJSON.getString("description");
//                productOverview.setOverViewTitle(prodDesc);
                image=mMembersJSON.getString("image");
                JSONArray imagesStringData=mMembersJSON.getJSONArray("images");
                for(int i=0;i<imagesStringData.length();i++)
                {
                    //JSONObject data=imagesStringData.getJSONObject(i);
                    String jsonData=imagesStringData.get(i).toString();


                }*/
                 jsonDescriptionData=mMembersJSON.getJSONArray("description");
                System.out.println("JDATA" + jsonDescriptionData.toString());
                for(int i=0;i<jsonDescriptionData.length();i++)
                {


                    JSONObject data=jsonDescriptionData.getJSONObject(i);
                    //String jsonData=imagesStringData.get(i).toString();
                    descData.setDescHeader(data.getString("head"));
                    descData.setDescData(data.getString("content"));
System.out.println(data.getString("head"));
                    System.out.println(data.getString("content"));
descModel.add(descData);
                }

                jsonRatingArray=mMembersJSON.getJSONArray("comments");
                for(int j=0;j<jsonRatingArray.length();j++)
                {


                    JSONObject dataRating=jsonRatingArray.getJSONObject(j);
                    //String jsonData=imagesStringData.get(i).toString();
                    prodRatingData.setContent(dataRating.getString("content"));
                    prodRatingData.setRate(dataRating.getString("rate"));
                    prodRatingData.setUsername(dataRating.getString("username"));

                    prodRating.add(prodRatingData);
                }


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
