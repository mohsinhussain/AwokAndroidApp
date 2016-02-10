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
import com.awok.moshin.awok.Util.Constants;

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
    LinearLayout progressLayout;
    String userId="";
    SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        list=(RecyclerView)findViewById(R.id.recyclerAddress);
        progressBar = (ProgressBar) findViewById(R.id.marker_progress);
        progressLayout = (LinearLayout) findViewById(R.id.progressLayout);
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
        ab.setHomeAsUpIndicator(R.drawable.back_button);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(mLayoutManager);

        mAdapter = new ShippingAddressAdapter(ShippingAddressActivity.this, overViewList);
       // setDate();


        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        if ((mSharedPrefs.contains(Constants.USER_ID_PREFS))) {
            userId = mSharedPrefs.getString(Constants.USER_ID_PREFS, null);
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
    protected void onResume() {
        super.onResume();
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            new APIClient(this, getApplicationContext(), new GetAddressCallback()).addressCallBack(userId);


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
        i.putExtra("isPrimary",overViewList.get(position).isSelected());

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
            new APIClient(this, getApplicationContext(), new RemoveAddressCallback()).removeAddressAPICall(addressId,userId);


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
            new APIClient(this, getApplicationContext(), new GetAddressCallback()).setPrimaryAddressAPICall(addressId,userId);
            SharedPreferences.Editor editor = mSharedPrefs.edit();
            editor.putString(Constants.USER_ADDRESS_ID, addressId);
            System.out.println("ADDED " + addressId);
            editor.commit();

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
                if(jsonObjectData.getJSONObject("STATUS").getInt("CODE")==401)
                {
                    String userId = mSharedPrefs.getString(Constants.USER_ID_PREFS, null);
                    new APIClient(ShippingAddressActivity.this, ShippingAddressActivity.this, new logoutUserCallback()).userLogoutAPICall(userId);
                }


                else if(jsonObjectData.getJSONObject("STATUS").getInt("CODE")==200) {
                    System.out.println(jsonObjectData.toString());
                    if (jsonObjectData.getJSONObject("OUTPUT").has("MESSAGE")) {

                        Snackbar.make(findViewById(android.R.id.content), jsonObjectData.getJSONObject("OUTPUT").getString("MESSAGE"), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.RED)
                                .show();
                    }
                /*else {

                    Snackbar.make(findViewById(android.R.id.content), jsonObjectData.getString("title"), Snackbar.LENGTH_INDEFINITE)
                            .setActionTextColor(Color.RED)
                            .show();
                }*/
                }


                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    //progressBar.startAnimation(animation);
                }
                progressLayout.setVisibility(View.GONE);


            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();
                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    // progressBar.startAnimation(animation);
                }
                progressLayout.setVisibility(View.GONE);

            }
        }

        @Override
        public void onTaskCancelled() {
        }

        @Override
        public void onPreExecute() {
            progressLayout.setVisibility(View.VISIBLE);
        }
    }






    public class GetAddressCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {

                System.out.println(response);

                JSONObject jsonObjectData;
                jsonObjectData = new JSONObject(response);
                if(jsonObjectData.getJSONObject("STATUS").getInt("CODE")==401)
                {
                    String userId = mSharedPrefs.getString(Constants.USER_ID_PREFS, null);
                    new APIClient(ShippingAddressActivity.this, ShippingAddressActivity.this, new logoutUserCallback()).userLogoutAPICall(userId);
                }


                else if(jsonObjectData.getJSONObject("STATUS").getInt("CODE")==200) {
                    System.out.println(jsonObjectData.toString());
            /*    if (jsonObjectData.getString("status").equals("0")) {

                } else {*/
                    overViewList.clear();
                    JSONArray address = jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("PROFILES");
                    for (int i = 0; i < address.length(); i++) {
                        JSONObject jData = address.getJSONObject(i);


                        ShippingAddressModel addressModel = new ShippingAddressModel();


                        addressModel.setId(jData.optString("ADDRESS_ID"));

                        if (jData.getJSONObject("LOCATION_INFO").has("EMIRATE")) {
                            addressModel.setState(jData.getJSONObject("LOCATION_INFO").getJSONObject("EMIRATE").optString("NAME"));
                            addressModel.setStateId(jData.getJSONObject("LOCATION_INFO").getJSONObject("EMIRATE").optString("ID"));
                            System.out.println(jData.getJSONObject("LOCATION_INFO").getJSONObject("EMIRATE").optString("ID"));
                        }
                        if (jData.getJSONObject("LOCATION_INFO").has("AREA")) {
                            addressModel.setCity(jData.getJSONObject("LOCATION_INFO").getJSONObject("AREA").optString("NAME"));
                            addressModel.setLocationId(jData.getJSONObject("LOCATION_INFO").getJSONObject("EMIRATE").optString("ID"));
                            System.out.println(jData.getJSONObject("LOCATION_INFO").getJSONObject("AREA").optString("ID"));
                        }
                        if (jData.getJSONObject("LOCATION_INFO").has("COUNTRY")) {
                            addressModel.setCountry(jData.getJSONObject("LOCATION_INFO").getJSONObject("COUNTRY").optString("NAME"));
                            addressModel.setCountryId(jData.getJSONObject("LOCATION_INFO").getJSONObject("EMIRATE").optString("ID"));
                            System.out.println(jData.getJSONObject("LOCATION_INFO").getJSONObject("COUNTRY").optString("ID"));
                        }

                        for (int j = 0; j < jData.getJSONArray("FIELDS").length(); j++) {

                            JSONObject jAddData = jData.getJSONArray("FIELDS").getJSONObject(j);
                            if (jAddData.getString("ID").equals("65")) {
                                addressModel.setName(jAddData.optString("VALUE"));
                                System.out.println(jAddData.optString("VALUE"));
                            }
                            if (jAddData.getString("ID").equals("67")) {
                                addressModel.setAddress1(jAddData.optString("VALUE"));
                                System.out.println(jAddData.optString("VALUE"));
                            }
                            if (jAddData.getString("ID").equals("69")) {
                                addressModel.setAddress2(jAddData.optString("VALUE"));
                                System.out.println(jAddData.optString("VALUE"));
                            }

                            if (jAddData.getString("ID").equals("78")) {
                                addressModel.setPin(jAddData.optString("VALUE"));
                                System.out.println(jAddData.optString("VALUE"));
                            }
                            if (jAddData.getString("ID").equals("70")) {
                                addressModel.setPhone1(jAddData.optString("VALUE"));
                                System.out.println(jAddData.optString("VALUE"));
                            }
                            if (jAddData.getString("ID").equals("71")) {
                                addressModel.setPhone2(jAddData.optString("VALUE"));
                                System.out.println(jAddData.optString("VALUE"));

                            }
                            if (jAddData.optString("ID").equals("116")) {
                                if (jAddData.optString("VALUE").equals("Y")) {
                                    addressModel.setIsSelected(true);

                                } else {
                                    addressModel.setIsSelected(false);
                                }

                            }

                        }
                        overViewList.add(addressModel);

                    }


                    list.setAdapter(mAdapter);
                    //  }

                }
                progressLayout.setVisibility(View.GONE);


            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();

                progressLayout.setVisibility(View.GONE);

            }
        }

        @Override
        public void onTaskCancelled() {
        }

        @Override
        public void onPreExecute() {
            progressLayout.setVisibility(View.VISIBLE);
        }
    }


    public class logoutUserCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
                if(obj.getJSONObject("OUTPUT").has("ERRORS")) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                else{
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.clear();
                    editor.commit();
                    Intent i = new Intent(ShippingAddressActivity.this, SplashActivity.class);
                    startActivity(i);
                    finish();
                }
                progressLayout.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
                progressLayout.setVisibility(View.GONE);
            }

        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            progressLayout.setVisibility(View.VISIBLE);

        }
    }






}
