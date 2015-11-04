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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.awok.moshin.awok.Adapters.OrderHistoryAdapter;
import com.awok.moshin.awok.Models.DisputeListModel;
import com.awok.moshin.awok.Models.OrderHistoryModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisputeList extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
private String user_id;




    ProgressBar progressBar;



    private LinearLayout mainLay;

    private RecyclerView.LayoutManager mLayoutManager;
    private List<DisputeListModel> disputeListData = new ArrayList<DisputeListModel>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispute_list);
        Intent i=getIntent();
        user_id=i.getStringExtra("user_id");


        mRecyclerView = (RecyclerView) findViewById(R.id.overViewRecyclerView);

        progressBar=(ProgressBar)findViewById(R.id.marker_progress);
        progressBar.setVisibility(View.GONE);

        mainLay=(LinearLayout)findViewById(R.id.bottomLay);





        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

   //     mAdapter = new OrderHistoryAdapter(DisputeList.this,orderHistoryData);
        //mRecyclerView.setAdapter(mAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Dispute History");






        /*ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            *//*if(categoryId==null){
                new APIClient(getApplicationContext(), getApplicationContext(),  new GetProductsCallback()).allProductsAPICall(pageCount);
            }
            else{*//*
            new APIClient(this, getApplicationContext(),  new GetDisputeList()).DisputeListCallBack("55f6a9462f17f64a9b5f5ce4");
            // }

        } else {
            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();

        }*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dispute_list, menu);
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









    /*public class GetDisputeList extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                disputeListData.clear();
                System.out.println(response);
                JSONObject jsonObjectData;
                jsonObjectData=new JSONObject(response);
                System.out.println(jsonObjectData.toString());

                DisputeListModel disputeData=new DisputeListModel();

                //disputeData.setHeader(jsonCart.getString("timeframe"));
                JSONObject jsonOrders=jsonCart.getJSONArray("orders").getJSONObject(j);
                disputeData.setOrderId(jsonOrders.getString("_id"));
                //orderData.setPrice(jsonObjectData.getString("price"));
                disputeData.setOrderNo(jsonOrders.getString("number"));
                disputeData.setDateTime(jsonOrders.getString("time_created_unix"));
               *//* if (j==0)
                {
                    System.out.println("SHOW");
                    orderData.setIsHeader(true);
                }
                else
                {
                    System.out.println("SHOW NO");
                    orderData.setIsHeader(false);
                }*//*
                disputeListData.add(disputeData);


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
                *//*if (mSwipeRefreshLayout!=null && mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }*//*
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
    }*/





}
