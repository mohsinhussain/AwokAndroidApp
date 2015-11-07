package com.awok.moshin.awok.Activities;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.awok.moshin.awok.Adapters.CheckOutAdapter;
import com.awok.moshin.awok.Adapters.CustomAdapter;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.CSVReader;
import com.awok.moshin.awok.Util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shon on 9/14/2015.
 */
public class CheckOutActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinearLayout bottomLay,totalLay;
    private TextView cartEmptyText, prodPrice;
    private RecyclerView.Adapter mAdapter;
    private TextView errorText;
    ProgressBar progressBar;
    LinearLayout progressLayout;
    private TextView itemsCount,totalText,subTotalText,shippingText;
    String TAG = "CartActivity";
    SharedPreferences mSharedPrefs;
    private RecyclerView.LayoutManager mLayoutManager;
    String mobileNumber = "";
    String password = "";
    private List<Checkout> overViewList = new ArrayList<Checkout>();
    String[] schoolbag = new String[]{"SHON", "SHON", "SONU", "SHON", "SONU"};
    ProgressBar progressBarForm;
    ProgressBar progressBarLogin;
    Dialog inputNumberDialog;
    Snackbar toast;
    Dialog loginDialog;
    Dialog registerDialog;
    TextView loginErrorTextView, registrationErrorTextView, inputNumberErrorTextView;
    ArrayList<String> countryCodes;
    ArrayList<String> countryCodeNumbers;
    List<String[]> list;
    //LinearLayout totalLay;
     ActionBar ab;
    String totalItems;
    boolean allowUpdates = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        mRecyclerView = (RecyclerView) findViewById(R.id.overViewRecyclerView);
        bottomLay = (LinearLayout) findViewById(R.id.bottomLay);
        bottomLay.setVisibility(View.GONE);
        cartEmptyText = (TextView) findViewById(R.id.cartEmptyText);
        cartEmptyText.setVisibility(View.GONE);
        prodPrice = (TextView) findViewById(R.id.prod_discountPrice);
        progressBar = (ProgressBar) findViewById(R.id.marker_progress);
        progressLayout = (LinearLayout) findViewById(R.id.progressLayout);
        progressLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        errorText=(TextView)findViewById(R.id.error_text);
        totalLay=(LinearLayout)findViewById(R.id.totalLay);
        totalLay.setVisibility(View.GONE);
        totalText=(TextView)findViewById(R.id.totalText);
        subTotalText=(TextView)findViewById(R.id.subTotal);
        shippingText=(TextView)findViewById(R.id.shippingText);
        itemsCount=(TextView)findViewById(R.id.totalItemsCount);
        allowUpdates = true;
        // getSupportActionBar().setIcon(R.drawable.ic_launcher);
        //totalLay=(LinearLayout)findViewById(R.id.totalLay);
        // getSupportActionBar().setTitle("Android Versions");

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        //mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //MyLinearLayoutManager mLayoutManager=new MyLinearLayoutManager(CheckOutActivity.this,LinearLayoutManager.VERTICAL,false);

        //mRecyclerView.setLayoutManager(mLayoutManager);
      /*  mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);*/
        mRecyclerView.setNestedScrollingEnabled(true);
       // mRecyclerView.hasNestedScrollingParent();
        mRecyclerView.setHasFixedSize(false);

        mRecyclerView.setLayoutManager(new MyLinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
       /* int viewHeight = 40 * overViewList.size();
        mRecyclerView.getLayoutParams().height = viewHeight;*/
        mAdapter = new CustomAdapter(CheckOutActivity.this, overViewList);
        //mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setAdapter(mAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

         ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.back_button);

        if (mSharedPrefs.contains(Constants.USER_MOBILE_PREFS)) {
            initializeCart();
        } else {
            //show login dialog
            inputNumberDialog = new Dialog(this, R.style.AppCompatAlertDialogStyle);
            inputNumberDialog.setCancelable(true);
            inputNumberDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            inputNumberDialog.setContentView(R.layout.dialog_input_number);
            inputNumberErrorTextView = (TextView)inputNumberDialog.findViewById(R.id.errorMessageTextView);
            final EditText mobileEditText = (EditText) inputNumberDialog.findViewById(R.id.mobileEditText);
            Button cancelButton = (Button) inputNumberDialog.findViewById(R.id.cancelButton);
            Button nextButton = (Button) inputNumberDialog.findViewById(R.id.nextButton);
            final Button countryCodeButton = (Button) inputNumberDialog.findViewById(R.id.countryCodeButton);
            progressBarForm = (ProgressBar) inputNumberDialog.findViewById(R.id.load_progress_bar);
            // if button is clicked, close the custom dialog

            populateCountryCodes();
            countryCodeButton.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public void onClick(View v) {
                    //show login dialog
                    final Dialog countryCodeDialog = new Dialog(CheckOutActivity.this, R.style.AppCompatAlertDialogStyle);
                    countryCodeDialog.setCancelable(true);
                    countryCodeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    countryCodeDialog.setContentView(R.layout.dialog_country_code);
                    final ListView mList = (ListView)countryCodeDialog.findViewById(R.id.codeListView);

                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            CheckOutActivity.this,
                            android.R.layout.simple_list_item_1);
                    arrayAdapter.addAll(countryCodes);
                    mList.setAdapter(arrayAdapter);
                    mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            countryCodeButton.setText(countryCodeNumbers.get(position));
                            countryCodeDialog.cancel();
                        }
                    });

                    countryCodeDialog.show();
                    countryCodeDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inputNumberDialog.cancel();
                }
            });
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = getWindow().getLayoutInflater();
                    final View mDialogView = inflater.inflate(R.layout.dialog_input_number, null);
                    if (mobileEditText.getText().toString().equalsIgnoreCase("")) {
                        mobileEditText.setError("Please enter your mobile number");
                    } else {
                        if (mobileEditText.getText().toString().matches("^[+]?[0-9]{9,12}$")) {
                            Log.v(TAG, "phone number is correct");
                            mobileNumber =countryCodeButton.getText().toString().replace(" ", "")+mobileEditText.getText().toString();
                            new APIClient(CheckOutActivity.this, CheckOutActivity.this, new CheckUserCallback()).userCheckAPICall(mobileNumber);
                        } else {
                            toast.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.RED)
                                    .show();

                            mobileEditText.setError("Phone number is incorrect");
                        }

                    }
                }
            });
            inputNumberDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });

            inputNumberDialog.show();
            inputNumberDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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


        Button b = (Button) findViewById(R.id.checkout);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CheckOutActivity.this, OrderSummaryActivity.class);
                startActivity(i);
            }
        });

        b.setTransformationMethod(null);

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if ((inputNumberDialog != null)) {
//            inputNumberDialog.dismiss();
//            inputNumberDialog = null;
//        }
//    }


    private void populateCountryCodes(){
        String next[] = {};
        list = new ArrayList<String[]>();
        countryCodeNumbers = new ArrayList<String>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getAssets().open("countrycodes.csv")));
            for(;;) {
                next = reader.readNext();
                if(next != null) {
                    list.add(next);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> countryNames = new ArrayList<String>();
        ArrayList<String> countryAbber = new ArrayList<String>();
        countryCodes = new ArrayList<String>();

        for(int i=0; i < list.size(); i++)
        {
//            countryNames.add(list.get(i)[0]); // gets name
            countryCodeNumbers.add(" +" +list.get(i)[1]); // gets abbreviation
            countryCodes.add(list.get(i)[0] + " +" + list.get(i)[1]); // gets calling code

        }
    }

    public void initializeCart() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            /*if(categoryId==null){
                new APIClient(getApplicationContext(), getApplicationContext(),  new GetProductsCallback()).allProductsAPICall(pageCount);
            }
            else{*/
            new APIClient(this, getApplicationContext(), new GetCartCallback()).cartItemsCallBack("55f6a9462f17f64a9b5f5ce4");
            //
                 //   new APIClient(this, getApplicationContext(), new GetCartCallback()).cartItemsCallBack("561382e4f26f2e4a43e7601f");
            // }

        } else {
//            toast.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
//                    .setActionTextColor(Color.RED)
//                    .show();
//            toast.getView().bringToFront();
           /* Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();*/



            Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();


            errorText.setVisibility(View.VISIBLE);
        }
    }

    public void showLogin() {
        //show login dialog
        loginDialog = new Dialog(this, R.style.AppCompatAlertDialogStyle);
        loginDialog.setCancelable(true);
        loginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loginDialog.setContentView(R.layout.dialog_login);
        loginErrorTextView = (TextView)loginDialog.findViewById(R.id.errorMessageTextView);
        final EditText passwordEditText = (EditText) loginDialog.findViewById(R.id.passwordEditText);
        Button cancelButton = (Button) loginDialog.findViewById(R.id.cancelButton);
        Button nextButton = (Button) loginDialog.findViewById(R.id.nextButton);
        progressBarLogin = (ProgressBar) loginDialog.findViewById(R.id.load_progress_bar);
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
                    passwordEditText.setError("Please Enter Password");
//                    Snackbar.make(findViewById(android.R.id.content), "Please Enter Password", Snackbar.LENGTH_LONG)
//                            .setActionTextColor(Color.RED)
//                            .show();

                } else {
                    password = passwordEditText.getText().toString();
                    HashMap<String, Object> userData = new HashMap<String, Object>();
                    userData.put("phone_number", mobileNumber);
                    userData.put("access_key", password);


                    JSONObject dataToSend = new JSONObject(userData);
                    new APIClient(CheckOutActivity.this, CheckOutActivity.this, new loginAndRegisterUserCallback()).userLoginAPICall(dataToSend.toString());
                }
            }
        });

        loginDialog.show();
        loginDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void showRegister() {
        //show login dialog
        registerDialog = new Dialog(this, R.style.AppCompatAlertDialogStyle);
        registerDialog.setCancelable(true);
        registerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        registerDialog.setContentView(R.layout.dialog_register);
        registrationErrorTextView = (TextView)registerDialog.findViewById(R.id.errorMessageTextView);
        final EditText passwordEditText = (EditText) registerDialog.findViewById(R.id.passwordEditText);
        final EditText confirmPasswordEditText = (EditText) registerDialog.findViewById(R.id.confirmPasswordEditText);
        Button cancelButton = (Button) registerDialog.findViewById(R.id.cancelButton);
        Button nextButton = (Button) registerDialog.findViewById(R.id.nextButton);
        LinearLayout buttonLayout = (LinearLayout) registerDialog.findViewById(R.id.buttonLayout);
        progressBarLogin = (ProgressBar) registerDialog.findViewById(R.id.load_progress_bar);
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
                    passwordEditText.setError(getString(R.string.enter_password));
//                    Snackbar.make(findViewById(android.R.id.content), "Please Enter Password", Snackbar.LENGTH_LONG)
//                            .setActionTextColor(Color.RED)
//                            .show();

                } else if (confirmPasswordEditText.getText().toString().equalsIgnoreCase("")) {
                    confirmPasswordEditText.setError(getString(R.string.confirm_password));
//                    Snackbar.make(findViewById(android.R.id.content), "Please Confirm Your Password", Snackbar.LENGTH_LONG)
//                            .setActionTextColor(Color.RED)
//                            .show();
                } else if (!passwordEditText.getText().toString().equalsIgnoreCase(confirmPasswordEditText.getText().toString())) {
                    confirmPasswordEditText.setError(getString(R.string.password_do_not_match));
//                    Snackbar.make(findViewById(android.R.id.content), "Your password and confirm password are not same", Snackbar.LENGTH_LONG)
//                            .setActionTextColor(Color.RED)
//                            .show();
                } else {
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
        registerDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }


    public class loginAndRegisterUserCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
                if (obj.getBoolean("errors")) {
                    if ((loginDialog != null)) {
                        loginErrorTextView.setVisibility(View.VISIBLE);
                        loginErrorTextView.setText(obj.getString("message"));
                    }
                    else if ((registerDialog != null)) {
                        registrationErrorTextView.setVisibility(View.VISIBLE);
                        registrationErrorTextView.setText(obj.getString("message"));
                    }
                } else {
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
                if (obj.getInt("status") == 200) {
                    inputNumberErrorTextView.setVisibility(View.GONE);
                    if (obj.getJSONObject("message").getBoolean("register") == true) {
                        showRegister();
                    } else {
                        showLogin();
                    }
                }
                progressBarForm.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
                inputNumberErrorTextView.setVisibility(View.VISIBLE);
                inputNumberErrorTextView.setText("Please check you connectivity");
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



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_productdetails, menu);
//
//
//        return true;
//    }
//
//    @Override
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
                String sellerHeadFlag;
                String totalFlag;
                overViewList.clear();
                System.out.println(response);
                /*JSONArray jsonArray;
                jsonArray = new JSONArray(response);*/
//                JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_PRODUCT_LIST_NAME);
                JSONObject jsonObjectData;
                jsonObjectData = new JSONObject(response);
                System.out.println(jsonObjectData.toString());
                // int length = jsonObjectData.length();
/////////                System.out.println(jsonObjectData.getJSONObject("data"));
///                System.out.println(jsonObjectData.getJSONObject("data").getJSONArray("seller_cart"));

                JSONArray jsonArray;
                /////              jsonArray=jsonObjectData.getJSONObject("data").getJSONArray("seller_cart");

                //String sellerName=jsonArray.getString("seller").toString();
                if (jsonObjectData.getString("errors").equals("true")) {
                    bottomLay.setVisibility(View.GONE);
                    cartEmptyText.setVisibility(View.VISIBLE);
                    totalLay.setVisibility(View.GONE);
                    ab.setTitle("Shopping Cart");
                } else {
                    totalLay.setVisibility(View.VISIBLE);
                    bottomLay.setVisibility(View.VISIBLE);
                    cartEmptyText.setVisibility(View.GONE);
                  //   totalItems=jsonObjectData.getJSONObject("data").getString("total_items");
                    totalItems = "404";
                    String subtotal=jsonObjectData.getJSONObject("data").getJSONObject("public").getString("subtotal");
                    String total=jsonObjectData.getJSONObject("data").getJSONObject("public").getString("total");
                    String shipping=jsonObjectData.getJSONObject("data").getJSONObject("public").getString("shipping_cost");
                    ab.setTitle("Shopping Cart ("+totalItems+")");
                    ab.setHomeAsUpIndicator(R.drawable.back_button);
                    itemsCount.setText(totalItems);
                    subTotalText.setText("AED "+subtotal);
                    totalText.setText("AED "+total);
                    if (shipping.equals("0"))
                    {
                        shippingText.setText("FREE");
                    }
                    else {
                        shippingText.setText("AED " + shipping);
                    }
                    int length = jsonObjectData.getJSONObject("data").getJSONObject("public").getJSONArray("sellers_cart").length();
                    for (int i = 0; i < length; i++) {
                        JSONObject jsonObject = jsonObjectData.getJSONObject("data").getJSONObject("public").getJSONArray("sellers_cart").getJSONObject(i);

                        JSONArray productDetails = jsonObject.getJSONArray("items");
                        int lengthOfProducts = productDetails.length();
                        for (int j = 0; j < lengthOfProducts; j++) {


                            Checkout listData = new Checkout();
                            JSONObject jsonObjectProductDetails = productDetails.getJSONObject(j);
                            totalFlag=jsonObjectProductDetails.getString("seller_name").toString();
                            listData.setOverViewText(jsonObjectProductDetails.getString("unit_price"));
//                            listData.setTotalPrice(jsonObjectProductDetails.getString("total_price"));
                            listData.setTotalPrice("response needed");
                            listData.setSellerSubTotal(jsonObject.getString("subtotal"));

                        if (jsonObject.getString("shipping_cost").equals("0"))
                        {
                            listData.setSellerShipping("Free");
                        }
                        else {
                            listData.setSellerShipping("AED " +jsonObject.getString("shipping_cost"));
                        }


                        //listData.setSellerShipping(jsonObject.getString("shipping"));
                        listData.setSellerTotal(jsonObject.getString("total"));
                            if(j==0)
                            {
                                listData.setIsHeader(true);
                            }
                            else
                            {
                                listData.setIsHeader(false);
                            }

                            if(j==lengthOfProducts-1)
                            {
                                listData.setIsFooter(true);
                            }
                            else
                            {
                                listData.setIsFooter(false);
                            }

//                            listData.setStatusId(jsonObjectData.getString("status"));
                            listData.setStatusId("respnonse needed");
                            listData.setOverViewTitle(jsonObjectProductDetails.getString("product_name"));
                            //listData.setSellerLabel(jsonObject.getString("seller"));
                            listData.setSellerLabel(jsonObjectProductDetails.getString("seller_name"));
                            listData.setImageBitmapString(jsonObjectProductDetails.getString("image"));
                            listData.setDiscount(jsonObjectProductDetails.getString("discount_percentage"));
                            listData.setOldPrice(jsonObjectProductDetails.getString("old_price"));
                            listData.setProductId(jsonObjectProductDetails.getString("id"));
                            listData.setQuantity(jsonObjectProductDetails.getString("quantity"));
                            //  listData.setRemainingStock(jsonObjectProductDetails.getString("total_quantity"));
                            listData.setRemainingStock("20");
                            listData.setIsEditable(true);
//                            prodPrice.setText((jsonObjectData.getJSONObject("data").getString("total")) + " AED");
                            overViewList.add(listData);

                            System.out.println("adhghnf" + listData.getIsHeader());
                            System.out.println("adhghnf" +listData.getIsFooter());
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

                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    progressLayout.startAnimation(animation);
                }
                progressLayout.setVisibility(View.GONE);
                //initializeData();
                /////////////////mRecyclerView.setAdapter(mAdapter);
//                allowUpdates = true;
//                mAdapter.setAllowUpdate();
                mAdapter.notifyDataSetChanged();
                cart_count();

            } catch (JSONException e) {
                e.printStackTrace();
                /*Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();*/


                Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Data could not be loaded", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED);

                View snackbarView = snackbar.getView();

                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    progressLayout.startAnimation(animation);
                }
                progressLayout.setVisibility(View.GONE);
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
            progressLayout.setVisibility(View.VISIBLE);
//            }

        }
    }

    private void cart_count() {
        if (mSharedPrefs.contains(Constants.APP_CART_COUNT)) {
            SharedPreferences.Editor editor = mSharedPrefs.edit();
            editor.putString(Constants.APP_CART_COUNT, totalItems);

            editor.commit();
        }
        else
        {
            SharedPreferences.Editor editor = mSharedPrefs.edit();
            editor.putString(Constants.APP_CART_COUNT, totalItems);

            editor.commit();
        }
    }

    public class MyLinearLayoutManager extends LinearLayoutManager {

        public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout)    {
            super(context, orientation, reverseLayout);
        }

        private int[] mMeasuredDimension = new int[2];

        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state,
                              int widthSpec, int heightSpec) {
            final int widthMode = View.MeasureSpec.getMode(widthSpec);
            final int heightMode = View.MeasureSpec.getMode(heightSpec);
            final int widthSize = View.MeasureSpec.getSize(widthSpec);
            final int heightSize = View.MeasureSpec.getSize(heightSpec);
            int width = 0;
            int height = 0;
            for (int i = 0; i < getItemCount(); i++) {
                measureScrapChild(recycler, i,
                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                        mMeasuredDimension);

                if (getOrientation() == HORIZONTAL) {
                    width = width + mMeasuredDimension[0];
                    if (i == 0) {
                        height = mMeasuredDimension[1];
                    }
                } else {
                    height = height + mMeasuredDimension[1];
                    if (i == 0) {
                        width = mMeasuredDimension[0];
                    }
                }
            }
            switch (widthMode) {
                case View.MeasureSpec.EXACTLY:
                    width = widthSize;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }

            switch (heightMode) {
                case View.MeasureSpec.EXACTLY:
                    height = heightSize;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }

            setMeasuredDimension(width, height);
        }

        private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec,
                                       int heightSpec, int[] measuredDimension) {
            View view = recycler.getViewForPosition(position);
            if (view != null) {
                RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
                int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                        getPaddingLeft() + getPaddingRight(), p.width);
                int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                        getPaddingTop() + getPaddingBottom(), p.height);
                view.measure(childWidthSpec, childHeightSpec);
                measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
                recycler.recycleView(view);
            }
        }
    }
    public void refreshData() {
        new APIClient(this, getApplicationContext(), new GetCartCallback()).cartItemsCallBack("55f6a9462f17f64a9b5f5ce4");
    }
}
