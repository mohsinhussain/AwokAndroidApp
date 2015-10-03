package com.awok.moshin.awok.Activities;

import android.content.Context;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.awok.moshin.awok.Adapters.OrderHistoryAdapter;
import com.awok.moshin.awok.Adapters.OrderHistoryDetailsPageAdapter;
import com.awok.moshin.awok.Models.OrderHistoryDetailsModel;
import com.awok.moshin.awok.Models.OrderHistoryModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.awok.moshin.awok.Util.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class OrderHistoryDetailsPage extends AppCompatActivity {
private RecyclerView mRecyclerView;
private String orderId;
    ProgressBar progressBar;
    private RecyclerView.Adapter mAdapter;
    private TextView orderTime,delTime,orderStatus,shippingAmount,totalAmount,sellerStore,sellerName;

    //private RecyclerView.LayoutManager mLayoutManager;
    private List<OrderHistoryDetailsModel> orderHistoryDetailsData = new ArrayList<OrderHistoryDetailsModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_details_page);

        mRecyclerView = (RecyclerView) findViewById(R.id.overViewRecyclerView);
        progressBar = (ProgressBar) findViewById(R.id.marker_progress);
        progressBar.setVisibility(View.GONE);

orderId=getIntent().getExtras().getString("OrderId");

    orderTime=(TextView)findViewById(R.id.orderTimeDate);
        delTime=(TextView)findViewById(R.id.delTimeDate);
        orderStatus=(TextView)findViewById(R.id.orderStatus);
        shippingAmount=(TextView)findViewById(R.id.shippingPrice);
        totalAmount=(TextView)findViewById(R.id.totalPrice);
        sellerStore=(TextView)findViewById(R.id.sellerStore);
        sellerName=(TextView)findViewById(R.id.sellerName);




        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        //mLayoutManager = new LinearLayoutManager(getApplicationContext());
        MyLinearLayoutManager mLayoutManager=new MyLinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new OrderHistoryDetailsPageAdapter(OrderHistoryDetailsPage.this,orderHistoryDetailsData);
        //mRecyclerView.setAdapter(mAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Order History Details");




        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            /*if(categoryId==null){
                new APIClient(getApplicationContext(), getApplicationContext(),  new GetProductsCallback()).allProductsAPICall(pageCount);
            }
            else{*/
            new APIClient(this, getApplicationContext(),  new GetHistoryDetailsCallback()).OrderHistoryDetailsItemsCallBack(orderId);
            // }

        } else {
            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }
    }


   /* @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_history_details_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


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















    public class GetHistoryDetailsCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {

                System.out.println(response);
                JSONObject jsonObjectData;
                jsonObjectData=new JSONObject(response);

                //JSONArray data=jsonObjectData.getJSONArray("data");



                    //JSONArray jsonArrayData=jsonObjectData.getJSONArray("data");
                    JSONObject jsonCart = jsonObjectData.getJSONObject("data");
                System.out.println(jsonCart.toString());
                    for(int j=0;j<jsonCart.getJSONArray("cart").length();j++) {
                        JSONObject jsonCartData = jsonCart.getJSONArray("cart").getJSONObject(j);

                        System.out.println(jsonCartData.toString());
                        OrderHistoryDetailsModel orderData=new OrderHistoryDetailsModel();
                        orderData.setImage(jsonCartData.getString("image"));
                        orderData.setPrice(jsonCartData.getString("total_price"));
                        orderData.setQuantity(jsonCartData.getString("quantity"));
                        orderData.setTitle(jsonCartData.getString("product_name"));
                        /*orderData.setOrderTime(jsonCartData.getString("time_created_unix"));
                        orderData.setDelTime(jsonCartData.getString("time_updated_unix"));*/
                        orderTime.setText(date(jsonCartData.getString("time_created_unix")));
                        delTime.setText(date(jsonCartData.getString("time_updated_unix")));


                        totalAmount.setText(jsonCart.getString("price"));

                        sellerName.setText(jsonCartData.getString("seller_name"));



                        orderHistoryDetailsData.add(orderData);
                        System.out.println(orderHistoryDetailsData.toString());

                    }


                if(getApplicationContext()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                     progressBar.startAnimation(animation);
                }
                 progressBar.setVisibility(View.GONE);
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

    public String date(String date)
    {

        long time = Long.parseLong(date)   * (long) 1000;
        Date date_value = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        Log.d("date", format.format(date_value));
        return format.format(date_value).toString();
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

}
