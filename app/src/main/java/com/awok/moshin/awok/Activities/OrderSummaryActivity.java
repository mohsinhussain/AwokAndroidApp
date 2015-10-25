package com.awok.moshin.awok.Activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
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
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awok.moshin.awok.Adapters.CheckOutAdapter;
import com.awok.moshin.awok.Adapters.OrderSummaryAdapter;
import com.awok.moshin.awok.Adapters.OrderSummaryCustomAdapter;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.OrderSummary;
import com.awok.moshin.awok.Models.ShippingAddressModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderSummaryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TextView errorText,totalValueTextView,totalPriceText,allPriceText;
    private Button prod_buyNow;
    private RelativeLayout mainLay;
//    private TextView total,shippingAmount,itemAmount;
    private RecyclerView.Adapter mAdapter;
 //   private RecyclerView.LayoutManager mLayoutManager;
    private List<OrderSummary> overViewList = new ArrayList<OrderSummary>();
    private ProgressBar progressBar;
    private Button addEditAddressButton;
    LinearLayout addressDetailLayout;
    TextView nameTextView, addressTextView, cityTextView, countryTextView, postalcodeTextView, mobileNumberTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        progressBar = (ProgressBar) findViewById(R.id.marker_progress);
        progressBar.setVisibility(View.VISIBLE);
//        total=(TextView)findViewById(R.id.order_total_value);
//        shippingAmount=(TextView)findViewById(R.id.estimated_shipping_price);
//        itemAmount=(TextView)findViewById(R.id.items_total_price);
        mainLay=(RelativeLayout)findViewById(R.id.orderSummaryMain);
        mainLay.setVisibility(View.GONE);
        errorText=(TextView)findViewById(R.id.error_text);
        addressDetailLayout = (LinearLayout) findViewById(R.id.addressDetailLayout);
        addressDetailLayout.setVisibility(View.GONE);
        totalPriceText=(TextView)findViewById(R.id.totalItemsText);
                allPriceText=(TextView)findViewById(R.id.allItems);
        totalValueTextView=(TextView)findViewById(R.id.totalValueTextView);
        addEditAddressButton = (Button)findViewById(R.id.addEditAddressButton);


        nameTextView=(TextView)findViewById(R.id.nameTextView);
        addressTextView=(TextView)findViewById(R.id.addressTextView);
        cityTextView=(TextView)findViewById(R.id.cityTextView);
        countryTextView=(TextView)findViewById(R.id.countryTextView);
        postalcodeTextView=(TextView)findViewById(R.id.postalcodeTextView);
        mobileNumberTextView=(TextView)findViewById(R.id.mobileNumberTextView);




        addEditAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderSummaryActivity.this, ShippingAddressActivity.class);
                startActivity(i);
            }
        });


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
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.hasNestedScrollingParent();
        mRecyclerView.setHasFixedSize(false);

        // getSupportActionBar().setIcon(R.drawable.ic_launcher);

        // getSupportActionBar().setTitle("Android Versions");

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
       // mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
      // mLayoutManager = new LinearLayoutManager(getApplicationContext());
       // //int viewHeight = 100 * overViewList.size();
       //mRecyclerView.getLayoutParams().height = viewHeight;
       //MyLinearLayoutManager mLayoutManager=new MyLinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,true);
        mRecyclerView.setLayoutManager(new MyLinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
    // mRecyclerView.setLayoutManager(mLayoutManager);
       /* int viewHeight = 100 * overViewList.size();
        mRecyclerView.getLayoutParams().height = viewHeight;*/

       // mRecyclerView.setNestedScrollingEnabled(true);

        mAdapter = new OrderSummaryCustomAdapter(OrderSummaryActivity.this,overViewList);
        //mRecyclerView.setAdapter(mAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(getString(R.string.order_summary));
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
            new APIClient(this, getApplicationContext(),  new GetCartCallback()).cartItemsCallBack("55f6a9462f17f64a9b5f5ce4");
            new APIClient(this, getApplicationContext(),  new GetAddressCallback()).getPrimaryAddressAPICall("55f6a9462f17f64a9b5f5ce4");
        } else {
            errorText.setVisibility(View.VISIBLE);
            /*Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();*/


            Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new APIClient(this, getApplicationContext(),  new GetCartCallback()).cartItemsCallBack("55f6a9462f17f64a9b5f5ce4");
            new APIClient(this, getApplicationContext(),  new GetAddressCallback()).getPrimaryAddressAPICall("55f6a9e52f17f64a9b5f5ce5");
        } else {
            errorText.setVisibility(View.VISIBLE);
            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);




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



    public class GetAddressCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {

                System.out.println(response);

                JSONObject jsonObjectData;
                jsonObjectData = new JSONObject(response);
                System.out.println(jsonObjectData.toString());
                if (jsonObjectData.getString("status").equals("0")) {

                } else {
                    addEditAddressButton.setText(getString(R.string.change_address));
//                    addEditAddressButton.setTextColor(getColor(R.color.input_hint));
                    addEditAddressButton.setTextColor(getResources().getColor(R.color.input_hint));
                    addressDetailLayout.setVisibility(View.VISIBLE);
                    JSONObject jData=jsonObjectData.getJSONObject("address");
                    ShippingAddressModel addressModel=new ShippingAddressModel();
                    nameTextView.setText(jData.getString("name"));
                    addressTextView.setText(jData.getJSONObject("address").getString("address_line1"));
                    cityTextView.setText(jData.getString("city"));
                    countryTextView.setText(jData.getString("country"));
                    postalcodeTextView.setText(jData.getString("postal_code"));
                    mobileNumberTextView.setText(jData.getString("phone_number1"));
                }



                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    //progressBar.startAnimation(animation);
                }
                //progressBar.setVisibility(View.GONE);


            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();
                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    // progressBar.startAnimation(animation);
                }
                //  progressBar.setVisibility(View.GONE);

            }
        }

        @Override
        public void onTaskCancelled() {
        }

        @Override
        public void onPreExecute() {

        }
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

//                    shippingAmount.setText((jsonObjectData.getJSONObject("data").getString("shipping")+" AED"));
//
//                    itemAmount.setText(jsonObjectData.getJSONObject("data").getString("total")+" AED");
//                    total.setText(jsonObjectData.getJSONObject("data").getString("total")+" AED");
                    int length = jsonObjectData.getJSONObject("data").getJSONArray("seller_cart").length();
                    for(int i=0;i<length;i++){
                        JSONObject jsonObject = jsonObjectData.getJSONObject("data").getJSONArray("seller_cart").getJSONObject(i);
totalPriceText.setText(jsonObjectData.getJSONObject("data").getString("total_items"));
                        allPriceText.setText("AED "+jsonObjectData.getJSONObject("data").getString("total"));
                        totalValueTextView.setText(jsonObjectData.getJSONObject("data").getString("total"));
                        JSONArray productDetails=jsonObject.getJSONArray("products");
                        int lengthOfProducts = productDetails.length();
                        for(int j=0;j<lengthOfProducts;j++)
                        {
                            OrderSummary listData=new OrderSummary();
                            /*JSONObject jsonObjectProductDetails=productDetails.getJSONObject(j);
                            listData.setOverViewText(jsonObjectProductDetails.getString("unit_price"));
                            //listData.setOverViewText(jsonObject.getString("total_price"));
                            listData.setStatusId(jsonObjectData.getString("status"));
                            listData.setOverViewTitle(jsonObjectProductDetails.getString("product_name"));
                            listData.setSellerLabel(jsonObject.getString("seller_name"));
                            listData.setImageBitmapString(jsonObjectProductDetails.getString("image"));
                            listData.setProductId(jsonObjectProductDetails.getString("_id"));
                            listData.setQuantity(jsonObjectProductDetails.getString("quantity"));
                            listData.setRemainingStock(jsonObjectProductDetails.getString("total_quantity"));*/
                            listData.setSellerSubTotal(jsonObject.getString("subtotal"));

                            if (jsonObject.getString("shipping").equals("0"))
                            {
                                listData.setSellerShipping("Free");
                            } else {
                                listData.setSellerShipping("AED " +jsonObject.getString("shipping"));
                            }


                            //listData.setSellerShipping(jsonObject.getString("shipping"));
                            listData.setSellerTotal(jsonObject.getString("total"));
                            if(j==0)
                            {
                                listData.setIsHeader(true);
                            }
                            else
                            {
                                listData.setIsHeader(false);
                            }

                            if(j==lengthOfProducts-1)
                            {
                                listData.setIsFooter(true);
                            }
                            else
                            {
                                listData.setIsFooter(false);
                            }

                            listData.setStatusId(jsonObjectData.getString("status"));

                            JSONObject jsonObjectProductDetails = productDetails.getJSONObject(j);

                            listData.setOverViewText(jsonObjectProductDetails.getString("unit_price"));
                            listData.setTotalPrice(jsonObjectProductDetails.getString("total_price"));

                            if(j==0)
                            {
                                listData.setIsHeader(true);
                            }
                            else
                            {
                                listData.setIsHeader(false);
                            }

                            if(j==lengthOfProducts-1)
                            {
                                listData.setIsFooter(true);
                            }
                            else
                            {
                                listData.setIsFooter(false);
                            }

                            listData.setStatusId(jsonObjectData.getString("status"));
                            listData.setOverViewTitle(jsonObjectProductDetails.getString("product_name"));
                            listData.setDiscount(jsonObjectProductDetails.getString("discount_percentage"));
                            listData.setSellerLabel(jsonObjectProductDetails.getString("seller_name"));
                            listData.setImageBitmapString(jsonObjectProductDetails.getString("image"));
                            listData.setOldPrice(jsonObjectProductDetails.getString("old_price"));
                            listData.setProductId(jsonObjectProductDetails.getString("_id"));
                            listData.setQuantity(jsonObjectProductDetails.getString("quantity"));
                            listData.setRemainingStock(jsonObjectProductDetails.getString("total_quantity"));


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
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public void onTaskComplete(String response) {
            try {
System.out.println("RESPONSE"+response);
                JSONObject jsonObjectData;
                jsonObjectData=new JSONObject(response);
                System.out.println("RESPONSE"+jsonObjectData.getString("status"));


                final Dialog cartAlertDialog = new Dialog(OrderSummaryActivity.this, R.style.AppCompatAlertDialogStyle);
                cartAlertDialog.setCancelable(true);
                cartAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                cartAlertDialog.setContentView(R.layout.dialog_cart_alert);
                TextView messageTextView = (TextView)cartAlertDialog.findViewById(R.id.msgTextView);
                Button okButton = (Button) cartAlertDialog.findViewById(R.id.okButton);
                if(Integer.parseInt(jsonObjectData.getString("status"))==1)
                {
                    messageTextView.setText(getString(R.string.order_placed_successfully));
                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cartAlertDialog.cancel();
                            Intent i=new Intent(OrderSummaryActivity.this,OrderHistory.class);
                            finish();
                            startActivity(i);
                        }
                    });
                }
                else{
                    messageTextView.setText(getString(R.string.order_placed_failure));
                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cartAlertDialog.cancel();

                        }
                    });
                }


                cartAlertDialog.show();
                cartAlertDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                if(getApplicationContext()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    progressBar.startAnimation(animation);
                }
                progressBar.setVisibility(View.GONE);
                //initializeData();


            } catch (JSONException e) {
                e.printStackTrace();
               /* Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();*/
                Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Data could not be loaded", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED);

                View snackbarView = snackbar.getView();

                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
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
    /*public class MyLinearLayoutManager extends LinearLayoutManager {

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
    }*/

/*public void onResume()
{
    super.onResume();
    mainLay.setVisibility(View.VISIBLE);
}*/
public void refreshData() {
    new APIClient(this, getApplicationContext(), new GetCartCallback()).cartItemsCallBack("55f6a9462f17f64a9b5f5ce4");
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





    public class WrappingLinearLayoutManager extends LinearLayoutManager
    {

        public WrappingLinearLayoutManager(Context context) {
            super(context);
        }

        private int[] mMeasuredDimension = new int[2];

        @Override
        public boolean canScrollVertically() {
            return false;
        }

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
                if (getOrientation() == HORIZONTAL) {
                    measureScrapChild(recycler, i,
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                            heightSpec,
                            mMeasuredDimension);

                    width = width + mMeasuredDimension[0];
                    if (i == 0) {
                        height = mMeasuredDimension[1];
                    }
                } else {
                    measureScrapChild(recycler, i,
                            widthSpec,
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                            mMeasuredDimension);

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
            if (view.getVisibility() == View.GONE) {
                measuredDimension[0] = 0;
                measuredDimension[1] = 0;
                return;
            }
            // For adding Item Decor Insets to view
            super.measureChildWithMargins(view, 0, 0);
            RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
            int childWidthSpec = ViewGroup.getChildMeasureSpec(
                    widthSpec,
                    getPaddingLeft() + getPaddingRight() + getDecoratedLeft(view) + getDecoratedRight(view),
                    p.width);
            int childHeightSpec = ViewGroup.getChildMeasureSpec(
                    heightSpec,
                    getPaddingTop() + getPaddingBottom() + getDecoratedTop(view) + getDecoratedBottom(view),
                    p.height);
            view.measure(childWidthSpec, childHeightSpec);

            // Get decorated measurements
            measuredDimension[0] = getDecoratedMeasuredWidth(view) + p.leftMargin + p.rightMargin;
            measuredDimension[1] = getDecoratedMeasuredHeight(view) + p.bottomMargin + p.topMargin;
            recycler.recycleView(view);
        }
    }

}
