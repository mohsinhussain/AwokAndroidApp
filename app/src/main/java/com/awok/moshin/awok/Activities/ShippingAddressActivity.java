package com.awok.moshin.awok.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awok.moshin.awok.Adapters.CheckOutAdapter;
import com.awok.moshin.awok.Adapters.ShippingAddressAdapter;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.ShippingAddressModel;
import com.awok.moshin.awok.Models.productOverviewRating;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShippingAddressActivity extends AppCompatActivity {
private RecyclerView list;


    private RecyclerView.Adapter mAdapter;
    private RelativeLayout add;
    private List<ShippingAddressModel> overViewList = new ArrayList<ShippingAddressModel>();
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        list=(RecyclerView)findViewById(R.id.recyclerAddress);
        add=(RelativeLayout)findViewById(R.id.buttonAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ShippingAddressActivity.this,AddNewAddress.class);
                startActivity(i);
            }
        });

        list.setHasFixedSize(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Shipping Address");
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(mLayoutManager);

        mAdapter = new ShippingAddressAdapter(ShippingAddressActivity.this, overViewList);
       // setDate();

        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            new APIClient(this, getApplicationContext(), new GetAddressCallback()).addressCallBack("55f6a9e52f17f64a9b5f5ce5");


        } else {

            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();

        }




    }
   /* public void setDate()
    {
        for(int i=0;i<10;i++)
        {
            ShippingAddressModel address=new ShippingAddressModel();
            address.setName("SHON PRINSON");
            address.setAddress("Mazaya AA1");
            address.setState("Dubai");
            address.setCountry("Dubai, AE");
            address.setPin("867675268768");
            address.setPhone("145674543574");
            address.setIsSelected(false);
overViewList.add(address);


        }
        list.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shipping_address, menu);
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
                    JSONArray address=jsonObjectData.getJSONArray("address");


                            for(int i=0;i<address.length();i++)
                            {
                                JSONObject jData=address.getJSONObject(i);
                                System.out.println("city"+jData.getString("city"));
                                System.out.println("country"+jData.getString("country"));
                                System.out.println("postal_code" + jData.getString("postal_code"));

                                //JSONArray addressLine=jData.getJSONArray("address");













                                    System.out.println("add1" + jData.getJSONObject("address").getString("address_line1"));
                                    System.out.println("add2" + jData.getJSONObject("address").getString("address_line2"));
                                    System.out.println("number" + jData.getString("phone_number1"));


                                ShippingAddressModel addressModel=new ShippingAddressModel();
                                addressModel.setName(jData.getString("name"));
                                addressModel.setAddress(jData.getJSONObject("address").getString("address_line1"));
                                addressModel.setState(jData.getJSONObject("address").getString("address_line2"));
                                addressModel.setCountry(jData.getString("country"));
                                addressModel.setPin(jData.getString("postal_code"));
                                addressModel.setPhone(jData.getString("phone_number1"));
                                addressModel.setIsSelected(false);
                                overViewList.add(addressModel);

                            }
                    list.setAdapter(mAdapter);
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









}
