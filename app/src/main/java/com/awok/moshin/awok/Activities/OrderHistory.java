package com.awok.moshin.awok.Activities;

import android.content.Context;
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
import android.widget.ProgressBar;

import com.awok.moshin.awok.Adapters.CheckOutAdapter;
import com.awok.moshin.awok.Adapters.OrderHistoryAdapter;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.OrderHistoryModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    ProgressBar progressBar;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<OrderHistoryModel> orderHistoryData = new ArrayList<OrderHistoryModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);


        mRecyclerView = (RecyclerView) findViewById(R.id.overViewRecyclerView);

progressBar=(ProgressBar)findViewById(R.id.marker_progress);
        progressBar.setVisibility(View.GONE);







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




        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            /*if(categoryId==null){
                new APIClient(getApplicationContext(), getApplicationContext(),  new GetProductsCallback()).allProductsAPICall(pageCount);
            }
            else{*/
            new APIClient(this, getApplicationContext(),  new GetHistoryCallback()).OrderHistoryItemsCallBack("55f6a9462f17f64a9b5f5ce4");
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
        getMenuInflater().inflate(R.menu.menu_order_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }







    public class GetHistoryCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {

                System.out.println(response);
                JSONObject jsonObjectData;
                jsonObjectData=new JSONObject(response);
JSONArray data=jsonObjectData.getJSONArray("data");
                System.out.println(jsonObjectData.getJSONArray("data").length());

                for(int i=0;i<jsonObjectData.getJSONArray("data").length();i++)
                {
                    //JSONArray jsonArrayData=jsonObjectData.getJSONArray("data");
                    JSONObject jsonCart=jsonObjectData.getJSONArray("data").getJSONObject(i);
                    System.out.println(jsonObjectData.getJSONArray("data").getJSONObject(i).getJSONArray("cart").toString());
                    //JSONObject jsonObjData=jsonObjectData.getJSONObject(jsonArrayData);
                    //for(int j=0;j<jsonArrayData.getJSONArray("cart").length();j++)




                    for(int j=0;j<jsonCart.getJSONArray("cart").length();j++)
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
                        orderHistoryData.add(orderData);

                    }

System.out.println("CARE"+orderHistoryData.toString());



                    //System.out.println("RESPONSE" + );
                }


                if(getApplicationContext()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                   // progressBar.startAnimation(animation);
                }
               // progressBar.setVisibility(View.GONE);
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
                 //   progressBar.startAnimation(animation);
                }
              //  progressBar.setVisibility(View.GONE);
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
           // progressBar.setVisibility(View.VISIBLE);
//            }

        }
    }



}
