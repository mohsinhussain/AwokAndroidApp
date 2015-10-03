package com.awok.moshin.awok.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.awok.moshin.awok.Fragments.HotDealsFragment;
import com.awok.moshin.awok.Fragments.ProductOverViewFragment;
import com.awok.moshin.awok.Fragments.ProductSpecificationFragment;
import com.awok.moshin.awok.Fragments.productDescription;

import com.awok.moshin.awok.Models.ProductDetailsModel;
import com.awok.moshin.awok.Models.ProductOverview;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shon on 9/9/2015.
 */
public class ProductDetailsView extends AppCompatActivity{
    private DrawerLayout mDrawerLayout;
    private TabLayout tabLayout;
    ProgressBar progressBar;
    ArrayList<ProductDetailsModel> productDetailsList = new ArrayList<ProductDetailsModel>();
    ProductDetailsModel productDetails=new ProductDetailsModel();
    ProductOverview productOverview=new ProductOverview();
    private TextView prodNewPrice,prodOldPrice;
    private Button buyNow;
    Map<String,String> productSpec = new HashMap<String,String>();
    private String imageData;
    String productId,productName;
    String catId;
    JSONObject dataToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        /*final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);*/
        Intent i=new Intent();
        productId =getIntent().getExtras().getString("id");
        productName=getIntent().getExtras().getString("productName");
        catId =getIntent().getExtras().getString(Constants.CAT_ID_INTENT);
        Log.v("Product DetailView", productId);
        progressBar = (ProgressBar) findViewById(R.id.marker_progress);
        progressBar.setVisibility(View.GONE);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new APIClient(ProductDetailsView.this, getApplicationContext(),  new GetProductDetailsCallback()).productDetailsAPICall(productId);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }

        //productD.setName("COOL");
        //productDetails.setName("COLD");
        buyNow=(Button)findViewById(R.id.prod_buyNow);
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyNow.setEnabled(false);
                HashMap<String,Object> addToCartData=new HashMap<String, Object>();



                addToCartData.put("user_id", "55f6a9462f17f64a9b5f5ce4");
                addToCartData.put("product_id", productId);
                addToCartData.put("shipping_method","55f6ab5e2f17f64a9b5f5ce6");
                addToCartData.put("quantity",1);



                 dataToSend=new JSONObject(addToCartData);

System.out.println(dataToSend.toString());
                new APIClient(ProductDetailsView.this, getApplicationContext(),  new GetAddToCartCallBack()).addToCartAPICall(dataToSend.toString());


            }
        });
        prodNewPrice=(TextView)findViewById(R.id.prod_price);
        prodOldPrice=(TextView)findViewById(R.id.prod_discountPrice);
        prodOldPrice.setPaintFlags(prodOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
       // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(productName);



        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


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
                Intent i=new Intent(getApplicationContext(),ProductDetailsView.class);
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
    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    tabLayout.setupWithViewPager(viewPager);
}

    public class GetProductDetailsCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject mMembersJSON;
                mMembersJSON = new JSONObject(response);
                System.out.println(mMembersJSON.getString("name"));
                //JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_CATEGORY_LIST_NAME);
                //int length = jsonArray.length();

                //for(int i=0;i<length;i++){
                    //JSONObject jsonObject = jsonArray.getJSONObject(i);
         //           productDetailsList.add(new ProductDetailsModel(mMembersJSON.getString("name")));

               // }
                /*ProductDetailsModel productDetails=new ProductDetailsModel();
                productDetails.setName(mMembersJSON.getString("name"));
                productDetailsList.add(productDetails);

                System.out.println("COOLGBDJH"+productDetails.getName());*/
               /* System.out.println("COOLGBDJH");
                productDetailsList.add(new ProductDetailsModel("COOL"));*/
productDetails.setName(mMembersJSON.getString("name"));
                prodNewPrice.setText(Integer.toString(mMembersJSON.getInt("new_price")) + " " + "AED");
                prodOldPrice.setText(Integer.toString(mMembersJSON.getInt("original_price")) + " " + "AED");
                //productDetails.setDiscPercent(mMembersJSON.getInt("discount_percentage"));
                System.out.println("COOLGBDJH" + productDetails.getName());
              /*  JSONObject prodSpecJson=mMembersJSON.getJSONObject("properties").getJSONObject("properties");
                System.out.println("COOLGBDJH" + prodSpecJson);

                Iterator iter = prodSpecJson.keys();
                while(iter.hasNext()){
                    String key = (String)iter.next();
                    String value = prodSpecJson.getString(key);
                    productSpec.put(key,value);
                    System.out.println("COOLGBDJH" + productSpec);
                }*/
               // productD.setName("COOL");

                String prodDesc=mMembersJSON.getString("description");
                productOverview.setOverViewTitle(prodDesc);


 imageData=mMembersJSON.getString("image");
                setUpTab();
                if(getApplicationContext()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    progressBar.startAnimation(animation);
                }
                progressBar.setVisibility(View.GONE);
               // initializeData();
            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
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
            progressBar.setVisibility(View.VISIBLE);
        }
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
        adapter.addFragment(new productDescription(productDetails,imageData), "Overview");
       // adapter.addFragment(new ProductSpecificationFragment(productSpec), "Specifications");
        //adapter.addFragment(new ProductSpecificationFragment(), "Specifications");
        adapter.addFragment(new ProductOverViewFragment(productOverview), "Description");
        adapter.addFragment(new HotDealsFragment(catId), "Related Products");





        viewPager.setAdapter(adapter);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_productdetails, menu);

//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));


        return true;
    }


    public class GetAddToCartCallBack extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject mMembersJSON;
                mMembersJSON = new JSONObject(response);
                System.out.println(mMembersJSON);

                Intent i=new Intent(ProductDetailsView.this,CheckOutActivity.class);
                startActivity(i);




            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
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
//            progressBar.setVisibility(View.VISIBLE);
        }
    }









    @Override
    public void onResume(){
        super.onResume();
        buyNow.setEnabled(true);

    }





}
