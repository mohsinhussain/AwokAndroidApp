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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.awok.moshin.awok.Adapters.CheckOutAdapter;
import com.awok.moshin.awok.Adapters.OrderHistoryAdapter;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.OrderHistoryModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.awok.moshin.awok.Util.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private TextView orderCount,orderText;
    ProgressBar progressBar;
    private TextView errorText;
    private Spinner spinnerOrder,statusAll;
    private String id="";
            private String from="";
    private String to="";
    private LinearLayout mainLay;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<OrderHistoryModel> orderHistoryData = new ArrayList<OrderHistoryModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);


        mRecyclerView = (RecyclerView) findViewById(R.id.overViewRecyclerView);

progressBar=(ProgressBar)findViewById(R.id.marker_progress);
        progressBar.setVisibility(View.GONE);

mainLay=(LinearLayout)findViewById(R.id.bottomLay);
      //  mainLay.setVisibility(View.GONE);

        spinnerOrder = (Spinner) findViewById(R.id.orderStatus);
        statusAll = (Spinner) findViewById(R.id.showall);

errorText=(TextView)findViewById(R.id.error_text);


        List<String> list = new ArrayList<String>();
        list.add("Order Completed");
        list.add("Order Pending");
        list.add("Order Canceled");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);



        spinnerOrder.setAdapter(dataAdapter);


        List<String> listShow = new ArrayList<String>();
        listShow.add("Show All");
        listShow.add("Show Others");




        ArrayAdapter<String> dataAdapterShow = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,listShow);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        statusAll.setAdapter(dataAdapter);
//orderCount=(TextView)findViewById(R.id.headTitle);



        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new OrderHistoryAdapter(OrderHistory.this,orderHistoryData);
        //mRecyclerView.setAdapter(mAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Order History");

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(OrderHistory.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(OrderHistory.this, "Product Name: " + orderHistoryData.get(position).getOrderId(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(OrderHistory.this, OrderHistoryDetailsPage.class);
                        i.putExtra("OrderId", orderHistoryData.get(position).getOrderId());

                        startActivity(i);

                    }
                })
        );


        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            /*if(categoryId==null){
                new APIClient(getApplicationContext(), getApplicationContext(),  new GetProductsCallback()).allProductsAPICall(pageCount);
            }
            else{*/
            new APIClient(this, getApplicationContext(),  new GetHistoryCallback()).OrderHistoryItemsCallBack("55f6a9462f17f64a9b5f5ce4",id,from,to);
            // }

        } else {
            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
            errorText.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_history, menu);
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



            case R.id.app_cart:
                //mDrawerLayout.openDrawer(GravityCompat.START);
                Intent i=new Intent(OrderHistory.this,OrderSummaryFilter.class);
                startActivityForResult(i, 1);

                return true;



          /*  case R.id.app_cart:
            {

            }*/
        }

        return super.onOptionsItemSelected(item);
    }







    public class GetHistoryCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
orderHistoryData.clear();
                System.out.println(response);
                JSONObject jsonObjectData;
                jsonObjectData=new JSONObject(response);
                System.out.println(jsonObjectData.toString());
                JSONObject dataJson=jsonObjectData.getJSONObject("data");
JSONArray data=dataJson.getJSONArray("grouped_orders");

//                System.out.println(jsonObjectData.getJSONArray("data").length());
//orderCount.setText("Your Last "+jsonObjectData.getJSONArray("data").length()+" Orders");
                for(int i=0;i<data.length();i++) {
                    //JSONArray jsonArrayData=jsonObjectData.getJSONArray("data");
                    JSONObject jsonCart=data.getJSONObject(i);
                  //  System.out.println(jsonObjectData.getJSONArray("data").getJSONObject(i).getJSONArray("cart").toString());

                  //  orderData.setIsHeader(true);
                    for(int j=0;j<jsonCart.getJSONArray("orders").length();j++) {
                        OrderHistoryModel orderData=new OrderHistoryModel();

                        orderData.setHeader(jsonCart.getString("timeframe"));
JSONObject jsonOrders=jsonCart.getJSONArray("orders").getJSONObject(j);
                        orderData.setOrderId(jsonOrders.getString("_id"));
                        //orderData.setPrice(jsonObjectData.getString("price"));
                        orderData.setOrderNo(jsonOrders.getString("number"));
                        orderData.setDateTime(jsonOrders.getString("time_created_unix"));
                        if (j==0)
                        {
                            System.out.println("SHOW");
                            orderData.setIsHeader(true);
                        }
                        else
                        {
                            System.out.println("SHOW NO");
                            orderData.setIsHeader(false);
                        }
                        orderHistoryData.add(orderData);
                        //JSONObject jsonObjData=jsonObjectData.getJSONObject(jsonArrayData);
                        //for(int j=0;j<jsonArrayData.getJSONArray("cart").length();j++)

                    }


                    /*for(int j=0;j<jsonCart.getJSONArray("cart").length();j++)
                    {
                        JSONObject jsonCartData=jsonCart.getJSONArray("cart").getJSONObject(j);
                        OrderHistoryModel orderData=new OrderHistoryModel();
                        orderData.setTitle(jsonCartData.getString("product_name"));
                        orderData.setQuantity(jsonCartData.getString("quantity"));
                        orderData.setSeller(jsonCartData.getString("seller_name"));
                        orderData.setPrice(jsonCartData.getString("total_price"));
                        orderData.setImageData(jsonCartData.getString("image"));
                        orderData.setShipping("NA");
                        orderData.setTotalPrice("na");
                        orderData.setOrderId(jsonCartData.getString("order_id"));
                        orderHistoryData.add(orderData);

                    }*/

                    System.out.println("CARE" +orderHistoryData.toString());
                   // System.out.println("CARE" +orderData.toString());



                    //System.out.println("RESPONSE" + );
                }


                if(getApplicationContext()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                   // progressBar.startAnimation(animation);
                }
                progressBar.setVisibility(View.GONE);
                //mainLay.setVisibility(View.VISIBLE);
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

    @Override
    public void onBackPressed()
    {
        Intent i=new Intent(OrderHistory.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(i);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            if (data.getExtras().containsKey("statusId")) {
                System.out.println("Status" + data.getExtras().getString("statusId").toString());
                id = data.getExtras().getString("statusId").toString();

            }
            if (data.getExtras().containsKey("From")) {
                System.out.println("From" + data.getExtras().getString("From").toString());
                from = data.getExtras().getString("From").toString();
            }
            if (data.getExtras().containsKey("To")) {


                System.out.println("To" + data.getExtras().getString("To").toString());
                to = data.getExtras().getString("To").toString();


            }
            ConnectivityManager connMgr = (ConnectivityManager)
                    getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
            /*if(categoryId==null){
                new APIClient(getApplicationContext(), getApplicationContext(),  new GetProductsCallback()).allProductsAPICall(pageCount);
            }
            else{*/
                new APIClient(this, getApplicationContext(),  new GetHistoryCallback()).OrderHistoryItemsCallBack("55f6a9462f17f64a9b5f5ce4",id,from,to);
                // }

            } else {
                Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .show();
                errorText.setVisibility(View.VISIBLE);
            }
        }
        if (resultCode == RESULT_CANCELED)
        {

        }
    }

}
