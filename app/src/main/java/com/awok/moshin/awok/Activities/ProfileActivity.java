package com.awok.moshin.awok.Activities;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awok.moshin.awok.Adapters.OrderSummaryAdapter;
import com.awok.moshin.awok.Models.OrderSummary;
import com.awok.moshin.awok.Models.ProductRatingModel;
import com.awok.moshin.awok.Models.ProductRatingPageModel;
import com.awok.moshin.awok.Models.productOverviewRating;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    public static final String AVATAR_URL = "http://lorempixel.com/200/200/people/1/";
    private ImageView profileImage;
    SharedPreferences mSharedPrefs;
    String userId;
    TextView fullNameTextView;
    RelativeLayout profileParentLayout,paymentGrid;
    RelativeLayout addressContentLayout;
    TextView myOrdersValueTextView,paymentValueTextView;
    TextView name,address,state,country,phone,addressCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
       // setContentView(R.layout.profile_view);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        if ((mSharedPrefs.contains(Constants.USER_ID_PREFS)))
        {
            userId = mSharedPrefs.getString(Constants.USER_ID_PREFS, null);
        }
        profileImage = (ImageView)findViewById(R.id.avatar);
        progressBar = (ProgressBar) findViewById(R.id.marker_progress);
        progressBar.setVisibility(View.GONE);
        profileImage = (ImageView)findViewById(R.id.avatar);
        fullNameTextView=(TextView)findViewById(R.id.fullNameTextView);
        addressContentLayout=(RelativeLayout)findViewById(R.id.addressContentLayout);
        addressCount=(TextView)findViewById(R.id.addressCount);
        paymentGrid=(RelativeLayout)findViewById(R.id.paymentGrid);
        //mRecyclerView.setAdapter(mAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        profileParentLayout = (RelativeLayout) findViewById(R.id.profileParentLayout);
        myOrdersValueTextView=(TextView)findViewById(R.id.myOrdersValueTextView);
        paymentValueTextView=(TextView)findViewById(R.id.paymentValueTextView);

name=(TextView)findViewById(R.id.name);
        address=(TextView)findViewById(R.id.address);
        state=(TextView)findViewById(R.id.state);
        country=(TextView)findViewById(R.id.country);
        phone=(TextView)findViewById(R.id.phone);

        profileParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
            }
        });

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Profile");
       Picasso.with(this).load(R.drawable.default_img).transform(new CircleTransformation()).into(profileImage);




        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // new APIClient(ProductDetailsActivity.this, getApplicationContext(),  new GetProductDetailsCallback()).productDetailsAPICall(productId);
            new APIClient(ProfileActivity.this, getApplicationContext(),  new GetProfileCallback()).getDashboardDetails(userId);
        } else {
            /*Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
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




    public class GetProfileCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {

                JSONObject mMembersJSON;

                mMembersJSON = new JSONObject(response);


                if(mMembersJSON.getJSONObject("STATUS").getInt("CODE")==400)
                {
                    Snackbar.make(findViewById(android.R.id.content), mMembersJSON.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                else if(mMembersJSON.getJSONObject("STATUS").getInt("CODE")==405)
                {
                    Snackbar.make(findViewById(android.R.id.content), mMembersJSON.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }

               else if(mMembersJSON.getJSONObject("STATUS").getInt("CODE")==200) {

                    System.out.println("bdhmgmdcv" + mMembersJSON);
                    if (mMembersJSON.has("ERROR")) {
                        Snackbar.make(findViewById(android.R.id.content), "Error Occured .. Please try again later", Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .show();
                    } else {

                       if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PRIMARY_ADDRESS").length()==0)
                       {
                           addressContentLayout.setVisibility(View.GONE);
address.setVisibility(View.GONE);
                           state.setVisibility(View.GONE);
                           country.setVisibility(View.GONE);
                           phone.setVisibility(View.GONE);
                           name.setVisibility(View.GONE);

                       }
                        else
                       {
                           addressContentLayout.setVisibility(View.VISIBLE);
                        /*if (mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getString("FULL_NAME").equals("")) {

                        } else {
                            name.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("FULL_NAME").getString("NAME")
                                    +" "+mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("FULL_NAME").getString("LAST_NAME"));
                        }*/


                        if (mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PRIMARY_ADDRESS").getString("Personal_Street").equals("")) {
address.setVisibility(View.GONE);
                        } else {
                            address.setVisibility(View.VISIBLE);
                            address.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PRIMARY_ADDRESS").getString("Personal_Street"));
                        }


                        if (mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PRIMARY_ADDRESS").getString("Personal_Apt_Villa_No").equals("")) {
address.setVisibility(View.GONE);
                        } else {
                            address.setVisibility(View.VISIBLE);
                            address.append(", " + mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PRIMARY_ADDRESS").getString("Personal_Apt_Villa_No"));
                        }


                        if (mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PRIMARY_ADDRESS").getJSONObject("LOCATION").getString("CITY").equals("")) {
state.setVisibility(View.GONE);
                        } else {
                            state.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PRIMARY_ADDRESS").getJSONObject("LOCATION").getString("CITY"));
                        }


                        if (mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PRIMARY_ADDRESS").getJSONObject("LOCATION").getString("STATE").equals("")) {
country.setVisibility(View.GONE);
                        } else {
                            country.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PRIMARY_ADDRESS").getJSONObject("LOCATION").getString("CITY"));
                        }


                        if (mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PRIMARY_ADDRESS").getJSONObject("LOCATION").getString("COUNTRY").equals("")) {
country.setVisibility(View.GONE);
                        } else {
                            country.setVisibility(View.VISIBLE);
                            country.append(", " + mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PRIMARY_ADDRESS").getJSONObject("LOCATION").getString("CITY"));
                        }


                       /* if (mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PRIMARY_ADDRESS").getString("Personal_Telephone").equals("")) {
phone.setVisibility(View.GONE);
                        } else {

                            phone.setVisibility(View.VISIBLE);


                            phone.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PRIMARY_ADDRESS").getString("Personal_Telephone"));
                        }*/
                    }
                        if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PERSONAL_PHOTO").length()>0) {
                            String photoUrl=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PERSONAL_PHOTO").getString("SRC");
                            Picasso.with(ProfileActivity.this).load("http://" + photoUrl).transform(new CircleTransformation()).into(profileImage);
                        }

                        if (mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getString("ORDERS_COUNT").equals("")) {
                            myOrdersValueTextView.setText("No Orders Found");
                        } else {
                            if (mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getString("ORDERS_COUNT").equals("0")) {
                                myOrdersValueTextView.setText("No Orders Found");
                            } else {
                                myOrdersValueTextView.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getString("ORDERS_COUNT"));
                            }
                        }



                        if (mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getString("ADDRESSES_COUNT").equals("") ||  mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getString("ADDRESSES_COUNT").equals("0")) {


                            addressCount.setText("No Address Found");
                            } else {
                            addressCount.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getString("ADDRESSES_COUNT"));
                            }

                        if (mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PAYMENT_METHOD").getString("NAME").equals("")) {

                          //  paymentGrid.setVisibility(View.GONE);
                            paymentValueTextView.setText("");

                        } else {
                            paymentValueTextView.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PAYMENT_METHOD").getString("NAME"));
                        }


                   /* if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getString("ORDERS_COUNT").equals(""))
                    {
                        myOrdersValueTextView.setText("No Orders Found");
                    }*/


                        if (mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getString("FULL_NAME").equals("")) {

                        } else {
                            fullNameTextView.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("FULL_NAME").getString("NAME")+" "+mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("FULL_NAME").getString("LAST_NAME"));
                        }


                    }

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
//            progressBar.setVisibility(View.VISIBLE);
        }
    }




}
