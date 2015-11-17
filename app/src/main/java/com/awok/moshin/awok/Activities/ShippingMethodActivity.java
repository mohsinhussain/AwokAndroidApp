package com.awok.moshin.awok.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awok.moshin.awok.Adapters.ColorSpecGridAdapter;
import com.awok.moshin.awok.Adapters.RelatedProductsAdapter;
import com.awok.moshin.awok.Adapters.ShippingAddressAdapter;
import com.awok.moshin.awok.Adapters.ShippingMethodAdapter;
import com.awok.moshin.awok.Adapters.SizeSpecGridAdapter;
import com.awok.moshin.awok.Adapters.StorageSpecGridAdapter;
import com.awok.moshin.awok.Models.ColorSpec;
import com.awok.moshin.awok.Models.ShippingMethod;
import com.awok.moshin.awok.Models.SizeSpec;
import com.awok.moshin.awok.Models.StorageSpec;
import com.awok.moshin.awok.Models.Variant;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.awok.moshin.awok.Util.ExpandableHeightGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShippingMethodActivity extends AppCompatActivity {

    String productId;
    String quantityString;
    ArrayList<ShippingMethod> shippingMethodArray = new ArrayList<ShippingMethod>();
    LinearLayout progressLayout;
    SharedPreferences mSharedPrefs;
    private RelativeLayout countryPicker;
    private ImageView countryImage;
    String imageIco;
    int imageSource;
    EditText quantity;
    Button increment, decrement;
    private String valueQuantity;
    private int stockQuantity;
    String variantId = "";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView list;
    String savedMethodName = "";
    Double shippingCost = 0.0;
    Button checkOutButton;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    TextView errorTextView, stockQuantityTextView, countryNameTextView;
    boolean loadingFistTime = true;
    String shippingResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_shipping_method);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        progressLayout = (LinearLayout) findViewById(R.id.progressLayout);
        progressLayout.setVisibility(View.GONE);
        quantity = (EditText) findViewById(R.id.quantity);
        increment = (Button) findViewById(R.id.quantity_inc);
        decrement = (Button) findViewById(R.id.quantity_dec);
        countryPicker=(RelativeLayout)findViewById(R.id.countryLayout);
        countryImage=(ImageView)findViewById(R.id.country_image);
        countryNameTextView = (TextView) findViewById(R.id.countryNameTextView);
        list=(RecyclerView)findViewById(R.id.recyclerAddress);
        checkOutButton = (Button) findViewById(R.id.checkOutButton);
        errorTextView = (TextView) findViewById(R.id.errorTextView);
        stockQuantityTextView = (TextView) findViewById(R.id.stockQuantityTextView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);



        quantity.setText("1");
        quantity.clearFocus();


        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
         ab.setHomeAsUpIndicator(R.drawable.back_button);
//        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Shipping Method");


        loadingFistTime = true;
        productId =getIntent().getExtras().getString(Constants.PRODUCT_ID_INTENT);
        quantityString = getIntent().getExtras().getString(Constants.QUANTITY_INTENT);
        stockQuantity = getIntent().getExtras().getInt(Constants.STOCK_INTENT);
        variantId = getIntent().getExtras().getString(Constants.VARIANTID_INTENT);
        savedMethodName = getIntent().getExtras().getString(Constants.SELECTED_METHOD_INTENT);
        shippingResponse = getIntent().getExtras().getString(Constants.SHIPPING_RESPONSE_INTENT);
        quantity.setText(quantityString);

        if(stockQuantity>0){
            stockQuantityTextView.setText(stockQuantity + " items left in stock");
            stockQuantityTextView.setVisibility(View.VISIBLE);
        }

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        list.setLayoutManager(mLayoutManager);

        mAdapter = new ShippingMethodAdapter(ShippingMethodActivity.this, shippingMethodArray);

//        ConnectivityManager connMgr = (ConnectivityManager)
//                getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            new APIClient(ShippingMethodActivity.this, ShippingMethodActivity.this,  new GetProductSpecsCallBack()).ProductSpecsAPICall(productId);
//        } else {
//            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
//                    .setActionTextColor(Color.RED)
//                    .show();
//        }





        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("method",savedMethodName);
                returnIntent.putExtra("count",quantity.getText().toString());
                returnIntent.putExtra("cost",shippingCost);
                returnIntent.putExtra("errorMessage", errorTextView.getText().toString());
                setResult(ShippingMethodActivity.this.RESULT_OK,returnIntent);
                finish();
            }
        });


        countryPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ShippingMethodActivity.this,CountryPickerExample.class);
                startActivity(i);
            }
        });

//        if(mSharedPrefs.contains(Constants.USER_SETTING_COUNTRY)){
//            System.out.println("YES");
//
//            imageIco=mSharedPrefs.getString(Constants.USER_COUNTRY_IMAGE_ID, null);
//
//            imageSource = getResources().getIdentifier(imageIco , "drawable", getPackageName());
//            countryImage.setImageDrawable(getResources().getDrawable(imageSource));
//            countryImage.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            System.out.println("No");
//        }


        setQuantityListeners();

    }


    public void setPrimaryShippingMethod(String methodName, Double shippingCost){

        savedMethodName = methodName;
        this.shippingCost = shippingCost;
//        SharedPreferences.Editor editor = mSharedPrefs.edit();
//        editor.putString(Constants.SHIPPING_METHOD_PREFS, methodName);
//        editor.commit();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(mSharedPrefs.contains(Constants.USER_SETTING_COUNTRY)){
            System.out.println("YES");
            countryNameTextView.setText(mSharedPrefs.getString(Constants.USER_SETTING_COUNTRY, null));
            imageIco=mSharedPrefs.getString(Constants.USER_COUNTRY_IMAGE_ID, null);
            imageSource = getResources().getIdentifier(imageIco , "drawable", getPackageName());
            countryImage.setImageDrawable(getResources().getDrawable(imageSource));
//            countryImage.setVisibility(View.VISIBLE);
        }
        else
        {
            System.out.println("No");
        }

        if(loadingFistTime){
            try {
                shippingMethodArray.clear();
                JSONObject issueObj = new JSONObject(shippingResponse);
                JSONObject dataObj = null;
                JSONObject varObj;
//                , sizesArray, storageArray;
                if(!issueObj.getBoolean("errors")){
                    dataObj =  issueObj.getJSONObject("data");
                    if(dataObj.has("server")){
                        for(int i=0;i<dataObj.getJSONArray("server").length();i++){
                            shippingMethodArray.add(new ShippingMethod(dataObj.getJSONArray("server").getJSONObject(i).getDouble("shipping_cost"),
                                    dataObj.getJSONArray("server").getJSONObject(i).getInt("savings"), dataObj.getJSONArray("server").getJSONObject(i).getString("name"),
                                    dataObj.getJSONArray("server").getJSONObject(i).getInt("est_from"), dataObj.getJSONArray("server").getJSONObject(i).getInt("est_to"),
                                    dataObj.getJSONArray("server").getJSONObject(i).getString("profile_id"), false));
                        }
                    }
                    errorTextView.setText("");
                    errorTextView.setVisibility(View.GONE);
                }
                else{
                    dataObj =  issueObj.getJSONObject("data");
                    errorTextView.setText(dataObj.getString("public"));
                    errorTextView.setVisibility(View.VISIBLE);
                }
                progressLayout.setVisibility(View.GONE);

                pupolateShippingMethod();

            } catch (JSONException e) {
                e.printStackTrace();
                /*Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();*/
                Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Data could not be Loaded", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED);

                View snackbarView = snackbar.getView();

                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
                progressLayout.setVisibility(View.GONE);
            }

            loadingFistTime = false;
        }
        else {

            connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                String locationId = "560a8eddf26f2e024b8b4690";
                new APIClient(ShippingMethodActivity.this, ShippingMethodActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                        variantId);
            } else {
                Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                        .setActionTextColor(Color.RED)
                        .show();
            }
        }

    }


    public void setQuantityListeners(){
        quantity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                valueQuantity = quantity.getText().toString();
                return false;
            }
        });
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int addQuantity=Integer.parseInt(quantity.getText().toString());
                addQuantity=addQuantity+1;
                quantity.setText(String.valueOf(addQuantity));

                if (quantity.getText().toString().equals("") || quantity.getText().toString().equals("0")) {
                    quantity.setText("1");
                    if (networkInfo != null && networkInfo.isConnected()) {
                        String locationId = "560a8eddf26f2e024b8b4690";
                        new APIClient(ShippingMethodActivity.this, ShippingMethodActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                variantId);
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                .setActionTextColor(Color.RED)
                                .show();
                    }
                } else {
                    if (!variantId.equalsIgnoreCase("") && Integer.parseInt(quantity.getText().toString()) > stockQuantity) {
                        System.out.println("OUT OF STOCK");

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                ShippingMethodActivity.this);

                        // set title
                        alertDialogBuilder.setTitle("Out of stock");


                        // set dialog message
                        alertDialogBuilder
                                .setMessage("The Quantity is not available in Stock")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        quantity.setText(Integer.toString(stockQuantity));
//                                        if (networkInfo != null && networkInfo.isConnected()) {
//                                            String locationId = "560a8eddf26f2e024b8b4690";
//                                            new APIClient(ShippingMethodActivity.this, ShippingMethodActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
//                                                    variantId);
//                                        } else {
//                                            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
//                                                    .setActionTextColor(Color.RED)
//                                                    .show();
//                                        }
                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    } else {
                        View view = getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        ConnectivityManager connMgr = (ConnectivityManager)
                                getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            String locationId = "560a8eddf26f2e024b8b4690";
                            new APIClient(ShippingMethodActivity.this, ShippingMethodActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                    variantId);
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }

                    }
                }
            }

        });


        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int addQuantity=Integer.parseInt(quantity.getText().toString());
                addQuantity=addQuantity-1;
                quantity.setText(String.valueOf(addQuantity));

                if (quantity.getText().toString().equals("") || quantity.getText().toString().equals("0")) {
                    quantity.setText("1");
                    if (networkInfo != null && networkInfo.isConnected()) {
                        String locationId = "560a8eddf26f2e024b8b4690";
                        new APIClient(ShippingMethodActivity.this, ShippingMethodActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                variantId);
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                .setActionTextColor(Color.RED)
                                .show();
                    }
                } else {
                    if (!variantId.equalsIgnoreCase("") && Integer.parseInt(quantity.getText().toString()) > stockQuantity) {
                        System.out.println("OUT OF STOCK");

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                ShippingMethodActivity.this);

                        // set title
                        alertDialogBuilder.setTitle("Out of stock");


                        // set dialog message
                        alertDialogBuilder
                                .setMessage("The Quantity is not available in Stock")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        quantity.setText(Integer.toString(stockQuantity));
                                        if (networkInfo != null && networkInfo.isConnected()) {
                                            String locationId = "560a8eddf26f2e024b8b4690";
                                            new APIClient(ShippingMethodActivity.this, ShippingMethodActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                                    variantId);
                                        } else {
                                            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                                    .setActionTextColor(Color.RED)
                                                    .show();
                                        }
                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    } else {
                        View view = getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        ConnectivityManager connMgr = (ConnectivityManager)
                                getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            String locationId = "560a8eddf26f2e024b8b4690";
                            new APIClient(ShippingMethodActivity.this, ShippingMethodActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                    variantId);
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }
                    }
                }
            }

        });


        quantity.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("BEFOR CHANGED");
            }

            @Override

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("TEXT CHANGED");
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("AFTER CHANGED");
            }
        });
        quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (!hasFocus) {
                    if (quantity.getText().toString().equals("") || quantity.getText().toString().equals("0")) {
                        quantity.setText("1");
                        if (networkInfo != null && networkInfo.isConnected()) {
                            String locationId = "560a8eddf26f2e024b8b4690";
                            new APIClient(ShippingMethodActivity.this, ShippingMethodActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                    variantId);
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }
                    }
                }
            }
        });

        quantity.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                System.out.println("AFTER CHANGED" + EditorInfo.IME_ACTION_DONE);
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED || actionId == EditorInfo.IME_ACTION_DONE) {

                    if (quantity.getText().toString().equals("") || quantity.getText().toString().equals("0")) {
                        quantity.setText("1");
                        if (networkInfo != null && networkInfo.isConnected()) {
                            String locationId = "560a8eddf26f2e024b8b4690";
                            new APIClient(ShippingMethodActivity.this, ShippingMethodActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                    variantId);
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }
                    } else {
                        if (!variantId.equalsIgnoreCase("") && Integer.parseInt(quantity.getText().toString()) > stockQuantity) {
                            System.out.println("OUT OF STOCK");

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    ShippingMethodActivity.this);

                            // set title
                            alertDialogBuilder.setTitle("Out of stock");


                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("The Quantity is not available in Stock")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, close
                                            // current activity
                                            quantity.setText(Integer.toString(stockQuantity));
                                            if (networkInfo != null && networkInfo.isConnected()) {
                                                String locationId = "560a8eddf26f2e024b8b4690";
                                                new APIClient(ShippingMethodActivity.this, ShippingMethodActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                                        variantId);
                                            } else {
                                                Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                                        .setActionTextColor(Color.RED)
                                                        .show();
                                            }
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();

//                            quantity.setText(valueQuantity);

                        } else {
                            View view = getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                            ConnectivityManager connMgr = (ConnectivityManager)
                                    getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                            if (networkInfo != null && networkInfo.isConnected()) {
                                String locationId = "560a8eddf26f2e024b8b4690";
                                new APIClient(ShippingMethodActivity.this, ShippingMethodActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                        variantId);
                            } else {
                                Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                        .setActionTextColor(Color.RED)
                                        .show();
                            }
                        }
                    }


                    return true;
                }
                return false;
            }


        });
    }




    public void pupolateShippingMethod(){
        if(shippingMethodArray.size()>0){
           //populate shipping method listing
            if (!savedMethodName.equalsIgnoreCase("")) {
                for(int i=0; i<shippingMethodArray.size();i++){
                    if(shippingMethodArray.get(i).getName().equalsIgnoreCase(savedMethodName)){
                        shippingMethodArray.get(i).setIsSelected(true);
                        shippingCost = shippingMethodArray.get(i).getShippingCost();
                        savedMethodName  = shippingMethodArray.get(i).getName();
                    }
                }
            }
            mAdapter.notifyDataSetChanged();
            list.setAdapter(mAdapter);
        }
    }


    public class GetShippingsCallBack extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                shippingMethodArray.clear();
                JSONObject issueObj = new JSONObject(response);
                JSONObject dataObj = null;
                JSONObject varObj;
//                , sizesArray, storageArray;
                if(!issueObj.getBoolean("errors")){
                    dataObj =  issueObj.getJSONObject("data");
                    if(dataObj.has("server")){
                        for(int i=0;i<dataObj.getJSONArray("server").length();i++){
                            shippingMethodArray.add(new ShippingMethod(dataObj.getJSONArray("server").getJSONObject(i).getDouble("shipping_cost"),
                                    dataObj.getJSONArray("server").getJSONObject(i).getInt("savings"), dataObj.getJSONArray("server").getJSONObject(i).getString("name"),
                                    dataObj.getJSONArray("server").getJSONObject(i).getInt("est_from"), dataObj.getJSONArray("server").getJSONObject(i).getInt("est_to"),
                                    dataObj.getJSONArray("server").getJSONObject(i).getString("profile_id"), false));
                        }
                    }
                    errorTextView.setText("");
                    errorTextView.setVisibility(View.GONE);
                }
                else{
                    dataObj =  issueObj.getJSONObject("data");
                    errorTextView.setVisibility(View.VISIBLE);
                    errorTextView.setText(dataObj.getString("public"));
                }
                progressLayout.setVisibility(View.GONE);

                pupolateShippingMethod();

            } catch (JSONException e) {
                e.printStackTrace();
                /*Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();*/
                Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Data could not be Loaded", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED);

                View snackbarView = snackbar.getView();

                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
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




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);




        return true;
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(ShippingMethodActivity.this.RESULT_CANCELED, returnIntent);
        finish();
        super.onBackPressed();
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
