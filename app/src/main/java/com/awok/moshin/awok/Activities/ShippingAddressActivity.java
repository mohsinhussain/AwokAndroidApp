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
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        list=(RecyclerView)findViewById(R.id.recyclerAddress);
        progressBar = (ProgressBar) findViewById(R.id.marker_progress);
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
    protected void onResume() {
        super.onResume();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_shipping_address, menu);
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

    public void editAddress(int position){

        Intent i = new Intent(ShippingAddressActivity.this, AddNewAddress.class);
        i.putExtra("id", overViewList.get(position).getId());
        i.putExtra("name", overViewList.get(position).getName());
        i.putExtra("address1", overViewList.get(position).getAddress1());
        i.putExtra("address2", overViewList.get(position).getAddress2());
        i.putExtra("country", overViewList.get(position).getCountry());
        i.putExtra("state", overViewList.get(position).getState());
        i.putExtra("city", overViewList.get(position).getCity());
        i.putExtra("zip", overViewList.get(position).getPin());
        i.putExtra("mobile1", overViewList.get(position).getPhone1());
        i.putExtra("mobile2", overViewList.get(position).getPhone2());

        startActivity(i);


//        ConnectivityManager connMgr = (ConnectivityManager)
//                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            new APIClient(this, getApplicationContext(), new EditAddresssCallback()).editAddressAPICall(addressId);
//
//
//        } else {
//
//            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
//                    .setActionTextColor(Color.RED)
//                    .show();
//        }
    }

    public void removeAddress(String addressId){
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new APIClient(this, getApplicationContext(), new RemoveAddressCallback()).removeAddressAPICall(addressId);


        } else {

            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();

        }
    }

    public void setPrimaryAddress(String addressId){
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new APIClient(this, getApplicationContext(), new GetAddressCallback()).setPrimaryAddressAPICall(addressId);


        } else {

            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();

        }
    }


    public class EditAddresssCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {

                System.out.println(response);

                JSONObject jsonObjectData;
                jsonObjectData = new JSONObject(response);
                System.out.println(jsonObjectData.toString());
                if (jsonObjectData.getString("status").equals("0")) {

                } else {
                    Snackbar.make(findViewById(android.R.id.content), jsonObjectData.getString("title"), Snackbar.LENGTH_INDEFINITE)
                            .setActionTextColor(Color.RED)
                            .show();
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


    public class RemoveAddressCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {

                System.out.println(response);

                JSONObject jsonObjectData;
                jsonObjectData = new JSONObject(response);
                System.out.println(jsonObjectData.toString());
                if (jsonObjectData.getString("status").equals("0")) {

                } else {

                    Snackbar.make(findViewById(android.R.id.content), jsonObjectData.getString("title"), Snackbar.LENGTH_INDEFINITE)
                            .setActionTextColor(Color.RED)
                            .show();
                }



                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    //progressBar.startAnimation(animation);
                }
                progressBar.setVisibility(View.GONE);


            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();
                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    // progressBar.startAnimation(animation);
                }
                  progressBar.setVisibility(View.GONE);

            }
        }

        @Override
        public void onTaskCancelled() {
        }

        @Override
        public void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }
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
                    overViewList.clear();
                    JSONArray address=jsonObjectData.getJSONArray("address");
                    for(int i=0;i<address.length();i++)
                        {
                            JSONObject jData=address.getJSONObject(i);
                            ShippingAddressModel addressModel=new ShippingAddressModel();
                            addressModel.setName(jData.getString("name"));
                            addressModel.setId(jData.getString("id"));
                            addressModel.setAddress1(jData.getJSONObject("address").getString("address_line1"));
                            addressModel.setAddress2(jData.getJSONObject("address").getString("address_line2"));
                            addressModel.setState(jData.getString("state"));
                            addressModel.setCity(jData.getString("city"));
                            addressModel.setCountry(jData.getString("country"));
                            addressModel.setPin(jData.getString("postal_code"));
                            addressModel.setPhone1(jData.getString("phone_number1"));
                            addressModel.setPhone2(jData.getString("phone_number2"));
                            addressModel.setIsSelected(jData.getBoolean("is_primary"));
                            overViewList.add(addressModel);
                        }
                    list.setAdapter(mAdapter);
                }


                progressBar.setVisibility(View.GONE);


            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();

                  progressBar.setVisibility(View.GONE);

            }
        }

        @Override
        public void onTaskCancelled() {
        }

        @Override
        public void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }
    }









}
