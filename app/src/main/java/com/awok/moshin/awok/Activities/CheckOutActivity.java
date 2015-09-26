package com.awok.moshin.awok.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.awok.moshin.awok.Adapters.CheckOutAdapter;
import com.awok.moshin.awok.Adapters.ProductOverViewAdapter;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.ProductOverview;
import com.awok.moshin.awok.Models.Products;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shon on 9/14/2015.
 */
public class CheckOutActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Checkout> overViewList = new ArrayList<Checkout>();
    String[] schoolbag = new String[]{"SHON","SHON","SONU","SHON","SONU"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        mRecyclerView = (RecyclerView) findViewById(R.id.overViewRecyclerView);

        // getSupportActionBar().setIcon(R.drawable.ic_launcher);

        // getSupportActionBar().setTitle("Android Versions");

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CheckOutAdapter(CheckOutActivity.this,overViewList);
        mRecyclerView.setAdapter(mAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Mobiles");
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            /*if(categoryId==null){
                new APIClient(getApplicationContext(), getApplicationContext(),  new GetProductsCallback()).allProductsAPICall(pageCount);
            }
            else{*/
                new APIClient(this, getApplicationContext(),  new GetCartCallback()).cartItemsCallBack("55f6a9462f17f64a9b5f5ce4");
           // }

        } else {
            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }

      /*  int i=0;
        for(i=0;i<=4;i++)
        {
            Checkout listData=new Checkout();
            listData.setOverViewText(getResources().getString(R.string.check_out_price));
            listData.setOverViewTitle(getResources().getString(R.string.checkout_head));
            listData.setSellerLabel(schoolbag[i].toString());

            overViewList.add(listData);

        }*/
        System.out.println("COOL" + overViewList.toString());
        /*mAdapter.notifyDataSetChanged();*/


        Button b=(Button)findViewById(R.id.checkout);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CheckOutActivity.this,OrderSummaryActivity.class);
                startActivity(i);
            }
        });






    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {





        switch (item.getItemId()) {
            case android.R.id.home:
                //mDrawerLayout.openDrawer(GravityCompat.START);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);


    }


    public class GetCartCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                System.out.println(response);
                /*JSONArray jsonArray;
                jsonArray = new JSONArray(response);*/
//                JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_PRODUCT_LIST_NAME);
                JSONObject jsonObjectData;
                jsonObjectData=new JSONObject(response);
                System.out.println(jsonObjectData.toString());
               // int length = jsonObjectData.length();
                System.out.println(jsonObjectData.getJSONObject("data"));
                System.out.println(jsonObjectData.getJSONObject("data").getJSONArray("seller_cart"));

                JSONArray jsonArray;
                jsonArray=jsonObjectData.getJSONObject("data").getJSONArray("seller_cart");
                int length = jsonObjectData.getJSONObject("data").getJSONArray("seller_cart").length();
                //String sellerName=jsonArray.getString("seller").toString();

                for(int i=0;i<length;i++){
                    JSONObject jsonObject = jsonObjectData.getJSONObject("data").getJSONArray("seller_cart").getJSONObject(i);

                    JSONArray productDetails=jsonObject.getJSONArray("products");
                    int lengthOfProducts = productDetails.length();
                    for(int j=0;j<lengthOfProducts;j++)
                    {
                        Checkout listData=new Checkout();
                        JSONObject jsonObjectProductDetails=productDetails.getJSONObject(j);
                        listData.setOverViewText(jsonObjectProductDetails.getString("unit_price"));
                        listData.setOverViewTitle(jsonObjectProductDetails.getString("product_name"));
                        listData.setSellerLabel(jsonObject.getString("seller"));
                        listData.setImageBitmapString(jsonObjectProductDetails.getString("image"));
                        overViewList.add(listData);
                    }
                    /*listData.setOverViewText(getResources().getString(R.string.check_out_price));
                    listData.setOverViewTitle(getResources().getString(R.string.checkout_head));
                    listData.setSellerLabel(schoolbag[i].toString());*/
                    /*listData.setOverViewText(jsonObject.getString("seller"));
                    listData.setOverViewTitle(jsonObject.getString("seller"));
                    listData.setSellerLabel(jsonObject.getString("seller"));*/

                }

                mAdapter.notifyDataSetChanged();

                if(getApplicationContext()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    //progressBar.startAnimation(animation);
                }
                //progressBar.setVisibility(View.GONE);
                //initializeData();

            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();
                if(getApplicationContext()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    //progressBar.startAnimation(animation);
                }
                //progressBar.setVisibility(View.GONE);
                /*if (mSwipeRefreshLayout!=null && mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }*/
            }
        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
//            if(!mSwipeRefreshLayout.isRefreshing()){
//                progressBar.setVisibility(View.VISIBLE);
//            }

        }
    }


}
