package com.awok.moshin.awok.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.awok.moshin.awok.Adapters.CheckOutAdapter;
import com.awok.moshin.awok.Adapters.ProductOverViewAdapter;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.ProductOverview;
import com.awok.moshin.awok.R;

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


        int i=0;
        for(i=0;i<=4;i++)
        {
            Checkout listData=new Checkout();
            listData.setOverViewText(getResources().getString(R.string.check_out_price));
            listData.setOverViewTitle(getResources().getString(R.string.checkout_head));

            overViewList.add(listData);

        }
        System.out.println("COOL" + overViewList.toString());
        mAdapter.notifyDataSetChanged();


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





}
