package com.awok.moshin.awok.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.awok.moshin.awok.Adapters.CheckOutAdapter;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shon on 9/14/2015.
 */
public class CheckOutActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private LinearLayout bottomLay;
    private TextView cartEmptyText,prodPrice;
    private RecyclerView.Adapter mAdapter;
    ProgressBar progressBar;
    String TAG = "CartActivity";
    SharedPreferences mSharedPrefs;
    private RecyclerView.LayoutManager mLayoutManager;
    String mobileNumber = "";
    String password = "";
    private List<Checkout> overViewList = new ArrayList<Checkout>();
    String[] schoolbag = new String[]{"SHON","SHON","SONU","SHON","SONU"};
    ProgressBar progressBarForm;
    ProgressBar progressBarLogin;
    Dialog inputNumberDialog;
    Dialog loginDialog;
    Dialog registerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        mRecyclerView = (RecyclerView) findViewById(R.id.overViewRecyclerView);
bottomLay=(LinearLayout)findViewById(R.id.bottomLay);
        bottomLay.setVisibility(View.GONE);
        cartEmptyText=(TextView)findViewById(R.id.cartEmptyText);
        cartEmptyText.setVisibility(View.GONE);
        prodPrice=(TextView)findViewById(R.id.prod_discountPrice);
        progressBar = (ProgressBar) findViewById(R.id.marker_progress);
        progressBar.setVisibility(View.GONE);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        // getSupportActionBar().setIcon(R.drawable.ic_launcher);

        // getSupportActionBar().setTitle("Android Versions");

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CheckOutAdapter(CheckOutActivity.this,overViewList);
        //mRecyclerView.setAdapter(mAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Cart");
        if(mSharedPrefs.contains(Constants.USER_MOBILE_PREFS)){
            initializeCart();
        }
        else{
            //show login dialog
            inputNumberDialog = new Dialog(this, R.style.AppCompatAlertDialogStyle);
            inputNumberDialog.setCancelable(false);
            inputNumberDialog.setContentView(R.layout.dialog_input_number);
            final EditText mobileEditText = (EditText) inputNumberDialog.findViewById(R.id.mobileEditText);
            Button cancelButton = (Button) inputNumberDialog.findViewById(R.id.cancelButton);
            Button nextButton = (Button) inputNumberDialog.findViewById(R.id.nextButton);
            progressBarForm = (ProgressBar)inputNumberDialog.findViewById(R.id.load_progress_bar);
            // if button is clicked, close the custom dialog
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   finish();
                }
            });
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    LayoutInflater inflater = dialog.getLayoutInflater();
//                    final View mDialogView = inflater.inflate(R.layout.dialog_input_number, null);
                    if (mobileEditText.getText().toString().equalsIgnoreCase("")) {
                        Snackbar.make(findViewById(android.R.id.content),
                                "Please enter your mobile number", Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .show();

                    } else {
                        if (mobileEditText.getText().toString().matches("^[+]?[0-9]{10,13}$")) {
                            Log.v(TAG, "phone number is correct");
                            mobileNumber = mobileEditText.getText().toString();
                            new APIClient(CheckOutActivity.this, CheckOutActivity.this,  new CheckUserCallback()).userCheckAPICall(mobileNumber);
                        }
                        else{
                            Snackbar.make(findViewById(android.R.id.content), "Phone number is incorrect", Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }

                    }
                }
            });

            inputNumberDialog.show();
        }


      /*  int i=0;
        for(i=0;i<=4;i++)
        {
            Checkout listData=new Checkout();
            listData.setOverViewText(getResources().getString(R.string.check_out_price));
            listData.setOverViewTitle(getResources().getString(R.string.checkout_head));
            listData.setSellerLabel(schoolbag[i].toString());

            overViewList.add(listData);

        }*/
        System.out.println("COOL" + overViewList.toString());
        /*mAdapter.notifyDataSetChanged();*/


        Button b=(Button)findViewById(R.id.checkout);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CheckOutActivity.this,OrderSummaryActivity.class);
                startActivity(i);
            }
        });






    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if ((inputNumberDialog != null)) {
//            inputNumberDialog.dismiss();
//            inputNumberDialog = null;
//        }
//    }

    public void initializeCart(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            /*if(categoryId==null){
                new APIClient(getApplicationContext(), getApplicationContext(),  new GetProductsCallback()).allProductsAPICall(pageCount);
            }
            else{*/
            new APIClient(this, getApplicationContext(),  new GetCartCallback()).cartItemsCallBack("55f6a9462f17f64a9b5f5ce4");
            // }

        } else {
            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }
    }

    public void showLogin(){
        //show login dialog
        loginDialog = new Dialog(this, R.style.AppCompatAlertDialogStyle);
        loginDialog.setCancelable(true);
        loginDialog.setContentView(R.layout.dialog_login);
        final EditText passwordEditText = (EditText) loginDialog.findViewById(R.id.passwordEditText);
        Button cancelButton = (Button) loginDialog.findViewById(R.id.cancelButton);
        Button nextButton = (Button) loginDialog.findViewById(R.id.nextButton);
        progressBarLogin = (ProgressBar)loginDialog.findViewById(R.id.load_progress_bar);
        // if button is clicked, close the custom dialog
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog.dismiss();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordEditText.getText().toString().equalsIgnoreCase("")) {
                    Snackbar.make(findViewById(android.R.id.content), "Please Enter Password", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();

                }
                else{
                    password = passwordEditText.getText().toString();
                    HashMap<String, Object> userData = new HashMap<String, Object>();
                    userData.put("phone_number", mobileNumber);
                    userData.put("access_key", password);


                    JSONObject dataToSend = new JSONObject(userData);
                    new APIClient(CheckOutActivity.this, CheckOutActivity.this, new loginAndRegisterUserCallback()).userLoginAPICall(dataToSend.toString());
                }            }
        });

        loginDialog.show();
    }

    public void showRegister(){
        //show login dialog
        registerDialog = new Dialog(this, R.style.AppCompatAlertDialogStyle);
        registerDialog.setCancelable(true);
        registerDialog.setContentView(R.layout.dialog_register);
        final EditText passwordEditText = (EditText) registerDialog.findViewById(R.id.passwordEditText);
        final EditText confirmPasswordEditText = (EditText) registerDialog.findViewById(R.id.confirmPasswordEditText);
        Button cancelButton = (Button) registerDialog.findViewById(R.id.cancelButton);
        Button nextButton = (Button) registerDialog.findViewById(R.id.nextButton);
        progressBarLogin = (ProgressBar)registerDialog.findViewById(R.id.load_progress_bar);
        // if button is clicked, close the custom dialog
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerDialog.dismiss();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordEditText.getText().toString().equalsIgnoreCase("")) {
                    Snackbar.make(findViewById(android.R.id.content), "Please Enter Password", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();

                }
                else if (passwordEditText.getText().toString().equalsIgnoreCase("")) {
                    Snackbar.make(findViewById(android.R.id.content), "Please Confirm Your Password", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();

                }
                else if (!passwordEditText.getText().toString().equalsIgnoreCase(confirmPasswordEditText.getText().toString())) {
                    Snackbar.make(findViewById(android.R.id.content), "Your password and confirm password are not same", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                else{
                    password = passwordEditText.getText().toString();
                    HashMap<String, Object> userData = new HashMap<String, Object>();
                    userData.put("phone_number", mobileNumber);
                    userData.put("access_key", password);


                    JSONObject dataToSend = new JSONObject(userData);
                    new APIClient(CheckOutActivity.this, CheckOutActivity.this, new loginAndRegisterUserCallback()).useRegisterAPICall(dataToSend.toString());
                }
            }
        });

        registerDialog.show();
    }


    public class loginAndRegisterUserCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
                if(obj.getBoolean("errors")) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getString("message"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                else{
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.putString(Constants.USER_MOBILE_PREFS, mobileNumber);
                    editor.putInt(Constants.USER_AUTH_TOKEN_PREFS, obj.getJSONObject("message").getInt("user_token"));
                    editor.putString(Constants.USER_ID_PREFS, obj.getJSONObject("message").getString("id_user"));
                    editor.commit();
                    //Hide dialogs and proceed with cart
                    if ((loginDialog != null)) {
                        loginDialog.dismiss();
                        loginDialog = null;
                    }
                    if ((registerDialog != null)) {
                        registerDialog.dismiss();
                        registerDialog = null;
                    }
                    if ((inputNumberDialog != null)) {
                        inputNumberDialog.dismiss();
                        inputNumberDialog = null;
                    }
                    initializeCart();
                }
                progressBarLogin.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
                progressBarLogin.setVisibility(View.GONE);
            }

        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            progressBarLogin.setVisibility(View.VISIBLE);

        }
    }


    public class CheckUserCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
                if(obj.getInt("status")==200){
                    if(obj.getJSONObject("message").getBoolean("register")==true){
                        showRegister();
                    }
                    else{
                        showLogin();
                    }
                }
                progressBarForm.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
                progressBarForm.setVisibility(View.GONE);
            }

        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            progressBarForm.setVisibility(View.VISIBLE);

        }
    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_productdetails, menu);




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
bottomLay.setVisibility(View.GONE);
    cartEmptyText.setVisibility(View.VISIBLE);
}

                else
{
bottomLay.setVisibility(View.VISIBLE);
    cartEmptyText.setVisibility(View.GONE);
    int length = jsonObjectData.getJSONObject("data").getJSONArray("seller_cart").length();
                for(int i=0;i<length;i++){
                    JSONObject jsonObject = jsonObjectData.getJSONObject("data").getJSONArray("seller_cart").getJSONObject(i);

                    JSONArray productDetails=jsonObject.getJSONArray("products");
                    int lengthOfProducts = productDetails.length();
                    for(int j=0;j<lengthOfProducts;j++)
                    {
                        Checkout listData=new Checkout();
                        JSONObject jsonObjectProductDetails=productDetails.getJSONObject(j);
                        listData.setOverViewText(jsonObjectProductDetails.getString("unit_price"));
                        //listData.setOverViewText(jsonObject.getString("total_price"));
                        listData.setStatusId(jsonObjectData.getString("status"));
                        listData.setOverViewTitle(jsonObjectProductDetails.getString("product_name"));
                        listData.setSellerLabel(jsonObject.getString("seller"));
                        listData.setImageBitmapString(jsonObjectProductDetails.getString("image"));
                        listData.setProductId(jsonObjectProductDetails.getString("_id"));
                        listData.setQuantity(jsonObjectProductDetails.getString("quantity"));
                        listData.setRemainingStock(jsonObjectProductDetails.getString("remaining_quantity"));
                        prodPrice.setText((jsonObjectData.getJSONObject("data").getString("total")) + " AED");
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

public void refreshData()
{
    new APIClient(this, getApplicationContext(),  new GetCartCallback()).cartItemsCallBack("55f6a9462f17f64a9b5f5ce4");
}
}
