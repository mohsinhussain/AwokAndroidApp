package com.awok.moshin.awok.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awok.moshin.awok.Adapters.CheckOutAdapter;
import com.awok.moshin.awok.Adapters.OrderSummaryAdapter;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.OrderSummary;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderSummaryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Button prod_buyNow;
    private RelativeLayout mainLay;
    private TextView total,shippingAmount,itemAmount;
    private RecyclerView.Adapter mAdapter;
    //private RecyclerView.LayoutManager mLayoutManager;
    private List<OrderSummary> overViewList = new ArrayList<OrderSummary>();
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        progressBar = (ProgressBar) findViewById(R.id.marker_progress);
        progressBar.setVisibility(View.GONE);
        total=(TextView)findViewById(R.id.order_total_value);
        shippingAmount=(TextView)findViewById(R.id.estimated_shipping_price);
        itemAmount=(TextView)findViewById(R.id.items_total_price);
        mainLay=(RelativeLayout)findViewById(R.id.orderSummaryMain);
        mainLay.setVisibility(View.GONE);
prod_buyNow=(Button)findViewById(R.id.prod_buyNow);
        prod_buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

JSONObject dataToSend;

                HashMap<String,Object> addToCartData=new HashMap<String, Object>();



                addToCartData.put("user_id", "55f6a9462f17f64a9b5f5ce4");
                addToCartData.put("user_address", "55f69d381a7da73416000058");
                addToCartData.put("payment_method","560001201a7da7681500004c");



                dataToSend=new JSONObject(addToCartData);

                ConnectivityManager connMgr = (ConnectivityManager)
                        getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
            /*if(categoryId==null){
                new APIClient(getApplicationContext(), getApplicationContext(),  new GetProductsCallback()).allProductsAPICall(pageCount);
            }
            else{*/
                    new APIClient(OrderSummaryActivity.this, getApplicationContext(),  new GetCheckOutCallBack()).OrderCheckOutCallBack(dataToSend.toString());
                    // }

                } else {
                    Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.overViewRecyclerView);

        // getSupportActionBar().setIcon(R.drawable.ic_launcher);

        // getSupportActionBar().setTitle("Android Versions");

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
       //mLayoutManager = new LinearLayoutManager(getApplicationContext());
        MyLinearLayoutManager mLayoutManager=new MyLinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new OrderSummaryAdapter(OrderSummaryActivity.this,overViewList);
        mRecyclerView.setAdapter(mAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Order Checkout");
       /* int i=0;
        for(i=0;i<=4;i++)
        {
            OrderSummary listData=new OrderSummary();
            listData.setOverViewText(getResources().getString(R.string.check_out_price));
            listData.setOverViewTitle(getResources().getString(R.string.checkout_head));

            overViewList.add(listData);

        }
        System.out.println("COOL" + overViewList.toString());
        mAdapter.notifyDataSetChanged();*/
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
                overViewList.clear();
                System.out.println(response);
                /*JSONArray jsonArray;
                jsonArray = new JSONArray(response);*/
//                JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_PRODUCT_LIST_NAME);
                JSONObject jsonObjectData;
                jsonObjectData=new JSONObject(response);
                System.out.println(jsonObjectData.toString());
                // int length = jsonObjectData.length();
/////////                System.out.println(jsonObjectData.getJSONObject("data"));
///                System.out.println(jsonObjectData.getJSONObject("data").getJSONArray("seller_cart"));

                JSONArray jsonArray;
                /////              jsonArray=jsonObjectData.getJSONObject("data").getJSONArray("seller_cart");

                //String sellerName=jsonArray.getString("seller").toString();
                if(jsonObjectData.getString("status").equals("0"))
                {
                    //bottomLay.setVisibility(View.GONE);
                    //cartEmptyText.setVisibility(View.VISIBLE);

                }

                else
                {
                    //bottomLay.setVisibility(View.VISIBLE);
                    //cartEmptyText.setVisibility(View.GONE);

                    shippingAmount.setText((jsonObjectData.getJSONObject("data").getString("shipping")+" AED"));

                    itemAmount.setText(jsonObjectData.getJSONObject("data").getString("total")+" AED");
                    total.setText(jsonObjectData.getJSONObject("data").getString("total")+" AED");
                    int length = jsonObjectData.getJSONObject("data").getJSONArray("seller_cart").length();
                    for(int i=0;i<length;i++){
                        JSONObject jsonObject = jsonObjectData.getJSONObject("data").getJSONArray("seller_cart").getJSONObject(i);

                        JSONArray productDetails=jsonObject.getJSONArray("products");
                        int lengthOfProducts = productDetails.length();
                        for(int j=0;j<lengthOfProducts;j++)
                        {
                            OrderSummary listData=new OrderSummary();
                            JSONObject jsonObjectProductDetails=productDetails.getJSONObject(j);
                            listData.setOverViewText(jsonObjectProductDetails.getString("unit_price"));
                            //listData.setOverViewText(jsonObject.getString("total_price"));
                            listData.setStatusId(jsonObjectData.getString("status"));
                            listData.setOverViewTitle(jsonObjectProductDetails.getString("product_name"));
                            listData.setSellerLabel(jsonObject.getString("seller"));
                            listData.setImageBitmapString(jsonObjectProductDetails.getString("image"));
                            listData.setProductId(jsonObjectProductDetails.getString("_id"));
                            listData.setQuantity(jsonObjectProductDetails.getString("quantity"));
                            listData.setRemainingStock(jsonObjectProductDetails.getString("total_quantity"));
                            //prodPrice.setText((jsonObjectData.getJSONObject("data").getString("total")) + " AED");
                            overViewList.add(listData);
                        }
                    /*listData.setOverViewText(getResources().getString(R.string.check_out_price));
                    listData.setOverViewTitle(getResources().getString(R.string.checkout_head));
                    listData.setSellerLabel(schoolbag[i].toString());*/
                    /*listData.setOverViewText(jsonObject.getString("seller"));
                    listData.setOverViewTitle(jsonObject.getString("seller"));
                    listData.setSellerLabel(jsonObject.getString("seller"));*/

                    }


                    // mAdapter.notifyAll();

                }

                if(getApplicationContext()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    progressBar.startAnimation(animation);
                }
                progressBar.setVisibility(View.GONE);
                mainLay.setVisibility(View.VISIBLE);
                //initializeData();
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();
                if(getApplicationContext()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    progressBar.startAnimation(animation);
                }
                progressBar.setVisibility(View.GONE);
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
            progressBar.setVisibility(View.VISIBLE);
//            }

        }
    }



    public class GetCheckOutCallBack extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
System.out.println("RESPONSE"+response);
                JSONObject jsonObjectData;
                jsonObjectData=new JSONObject(response);
                System.out.println("RESPONSE"+jsonObjectData.getString("status"));
                if(Integer.parseInt(jsonObjectData.getString("status"))==1)
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderSummaryActivity.this);

                    // set title
                    alertDialogBuilder.setTitle("Cart Alert");



                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Order Placed Successfully")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    dialog.cancel();
                                    Intent i=new Intent(OrderSummaryActivity.this,OrderHistory.class);
                                    finish();
                                    startActivity(i);

                                }
                            });
                                   /* .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            // the dialog box and do nothing
                                            dialog.cancel();
                                        }
                                    });*/

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
                else
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OrderSummaryActivity.this);

                    // set title
                    alertDialogBuilder.setTitle("Cart Alert");



                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Order Failed !!! Please Try Later")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    dialog.cancel();


                                }
                            });
                                   /* .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            // the dialog box and do nothing
                                            dialog.cancel();
                                        }
                                    });*/

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }


                if(getApplicationContext()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    progressBar.startAnimation(animation);
                }
                progressBar.setVisibility(View.GONE);
                //initializeData();


            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();
                if(getApplicationContext()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    progressBar.startAnimation(animation);
                }
                progressBar.setVisibility(View.GONE);
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
            progressBar.setVisibility(View.VISIBLE);
//            }

        }
    }
    public class MyLinearLayoutManager extends LinearLayoutManager {

        public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout)    {
            super(context, orientation, reverseLayout);
        }

        private int[] mMeasuredDimension = new int[2];

        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state,
                              int widthSpec, int heightSpec) {
            final int widthMode = View.MeasureSpec.getMode(widthSpec);
            final int heightMode = View.MeasureSpec.getMode(heightSpec);
            final int widthSize = View.MeasureSpec.getSize(widthSpec);
            final int heightSize = View.MeasureSpec.getSize(heightSpec);
            int width = 0;
            int height = 0;
            for (int i = 0; i < getItemCount(); i++) {
                measureScrapChild(recycler, i,
                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                        mMeasuredDimension);

                if (getOrientation() == HORIZONTAL) {
                    width = width + mMeasuredDimension[0];
                    if (i == 0) {
                        height = mMeasuredDimension[1];
                    }
                } else {
                    height = height + mMeasuredDimension[1];
                    if (i == 0) {
                        width = mMeasuredDimension[0];
                    }
                }
            }
            switch (widthMode) {
                case View.MeasureSpec.EXACTLY:
                    width = widthSize;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }

            switch (heightMode) {
                case View.MeasureSpec.EXACTLY:
                    height = heightSize;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }

            setMeasuredDimension(width, height);
        }

        private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec,
                                       int heightSpec, int[] measuredDimension) {
            View view = recycler.getViewForPosition(position);
            if (view != null) {
                RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
                int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                        getPaddingLeft() + getPaddingRight(), p.width);
                int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                        getPaddingTop() + getPaddingBottom(), p.height);
                view.measure(childWidthSpec, childHeightSpec);
                measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
                recycler.recycleView(view);
            }
        }
    }

/*public void onResume()
{
    super.onResume();
    mainLay.setVisibility(View.VISIBLE);
}*/


}
