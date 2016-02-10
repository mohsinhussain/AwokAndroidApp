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
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.awok.moshin.awok.Adapters.CheckOutAdapter;
import com.awok.moshin.awok.Adapters.OrderHistoryAdapter;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.OrderHistoryModel;
import com.awok.moshin.awok.Models.ProductRatingPageModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.awok.moshin.awok.Util.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderHistory extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private TextView orderCount,orderText;
    String code="";
    private  long startTime=0;
    ProgressBar progressBar;
    private TextView errorText,noOrders;
    private Spinner spinnerOrder,statusAll;
    private String id="";
    private int previousTotal = 0;

    private String sendValue="";
    Dialog inputNumberDialog;
   //////////// TextView inputNumberErrorTextView;
    ProgressBar progressBarLogin;
    Snackbar toast;
    EditText confirmPasswordEditText;
    EditText emailEditText;
    ProgressBar progressBarForm;
    Dialog loginDialog;
    CountDownTimer timerLoop;
    private String valueValidation="";
    Dialog registerDialog;
    Dialog verifyDialog;
    TextView loginErrorTextView, registrationErrorTextView, inputNumberErrorTextView;
    private int current_page = 1;
    String emailId = "";
            private String from="";
    String password = "";
    private int totalPages;
    ArrayList<String> modelData=new ArrayList<String>();
    private String oldTimeframe="";
    private boolean loading = true;
    //OrderHistoryModel orderData = new OrderHistoryModel();
    private boolean fetchInitial=true;
    private String to="";


    String phoneNumber="";
    SharedPreferences mSharedPrefs;
    String userId="";
    private LinearLayout mainLay;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<OrderHistoryModel> orderHistoryData = new ArrayList<OrderHistoryModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        if ((mSharedPrefs.contains(Constants.USER_ID_PREFS)))
        {
            userId = mSharedPrefs.getString(Constants.USER_ID_PREFS, null);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.overViewRecyclerView);

progressBar=(ProgressBar)findViewById(R.id.marker_progress);
        progressBar.setVisibility(View.GONE);

mainLay=(LinearLayout)findViewById(R.id.bottomLay);
      //  mainLay.setVisibility(View.GONE);
        noOrders=(TextView)findViewById(R.id.noOrders);
        spinnerOrder = (Spinner) findViewById(R.id.orderStatus);
        statusAll = (Spinner) findViewById(R.id.showall);

errorText=(TextView)findViewById(R.id.error_text);


        List<String> list = new ArrayList<String>();
        list.add("Order Completed");
        list.add("Order Pending");
        list.add("Order Canceled");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);



        spinnerOrder.setAdapter(dataAdapter);


        List<String> listShow = new ArrayList<String>();
        listShow.add("Show All");
        listShow.add("Show Others");




        ArrayAdapter<String> dataAdapterShow = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,listShow);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        statusAll.setAdapter(dataAdapter);
//orderCount=(TextView)findViewById(R.id.headTitle);



        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new OrderHistoryAdapter(OrderHistory.this,orderHistoryData);
        mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.setAdapter(mAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Order History");

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(OrderHistory.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(OrderHistory.this, "Product Name: " + orderHistoryData.get(position).getOrderId(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(OrderHistory.this, OrderHistoryDetailsPage.class);
                        i.putExtra("OrderId", orderHistoryData.get(position).getOrderId());
          //              i.putExtra("JsonObject",)
System.out.println("JSON"+orderHistoryData.get(position).getJsonData() );
                        i.putExtra("JsonObject", orderHistoryData.get(position).getJsonData());
                        startActivity(i);

                    }
                })
        );
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                System.out.println("nb,nbmnbmnb");
                super.onScrolled(recyclerView, dx, dy);
//                    StaggeredGridLayoutManager layoutManager = ((StaggeredGridLayoutManager) mRecyclerView.getLayoutManager());
                int visibleItemCount, totalItemCount, firstVisibleItem;
//
                visibleItemCount = mRecyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();


                firstVisibleItem = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();




                System.out.println("firstVisibleItem"+firstVisibleItem);
                System.out.println("totalItemCount" + totalItemCount);
                System.out.println("visibleItemCount"+visibleItemCount);
                System.out.println("previousTotal"+previousTotal);



                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + 5)) {
                    // End has been reached

                    // Do something
                   /*     for (int j=0;j<20;j++)
                        {
                            Model mm=new Model();


                            mm.setText("I am "+j);
                            mm.setColor("Color is" + j);


                            model.add(mm);

                        }*/
                    current_page++;
                    if(totalPages>=current_page) {
                        System.out.println("NDjkdjkhnzdklmcf");
                        onLoadMore();
//                        mRecAdapter.notifyDataSetChanged();

                        System.out.println("previousTotal" + "dhmgfdmgcf");
                        loading = true;
                    }
                }
            }
        });




        if (mSharedPrefs.contains(Constants.USER_ID_PREFS)) {
            initializeCart();
        } else {
            //show login dialog
            inputNumberDialog = new Dialog(this, R.style.AppCompatAlertDialogStyle);
            inputNumberDialog.setCancelable(true);
            inputNumberDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            inputNumberDialog.setContentView(R.layout.dialog_input_number);
            inputNumberErrorTextView = (TextView)inputNumberDialog.findViewById(R.id.errorMessageTextView);
            emailEditText = (EditText) inputNumberDialog.findViewById(R.id.emailEditText);
            Button cancelButton = (Button) inputNumberDialog.findViewById(R.id.cancelButton);
            Button nextButton = (Button) inputNumberDialog.findViewById(R.id.nextButton);
            final Button countryCodeButton = (Button) inputNumberDialog.findViewById(R.id.countryCodeButton);
            progressBarForm = (ProgressBar) inputNumberDialog.findViewById(R.id.load_progress_bar);
            // if button is clicked, close the custom dialog

            //populateCountryCodes();
            countryCodeButton.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public void onClick(View v) {
                    //show login dialog
                    final Dialog countryCodeDialog = new Dialog(OrderHistory.this, R.style.AppCompatAlertDialogStyle);
                    countryCodeDialog.setCancelable(true);
                    countryCodeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    countryCodeDialog.setContentView(R.layout.dialog_country_code);
                    final ListView mList = (ListView)countryCodeDialog.findViewById(R.id.codeListView);

                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            OrderHistory.this,
                            android.R.layout.simple_list_item_1);
                    //  arrayAdapter.addAll(countryCodes);
                   /* mList.setAdapter(arrayAdapter);
                    mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            countryCodeButton.setText(countryCodeNumbers.get(position));
                            countryCodeDialog.cancel();
                        }
                    });*/

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
                    if (emailEditText.getText().toString().equalsIgnoreCase("")) {
                        emailEditText.setError("Please enter your mobile number");
                    } else {
                        if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText()).matches()) {
                            //  Log.v(TAG, "phone number is correct");
                            emailId =emailEditText.getText().toString();
                            new APIClient(OrderHistory.this, OrderHistory.this, new CheckUserCallback()).userCheckAPICall(emailId);
                        } else {
                            toast.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.RED)
                                    .show();

                            emailEditText.setError("Email Id is incorrect");
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




       /* ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            *//*if(categoryId==null){
                new APIClient(getApplicationContext(), getApplicationContext(),  new GetProductsCallback()).allProductsAPICall(pageCount);
            }
            else{*//*
            new APIClient(this, getApplicationContext(),  new GetHistoryCallback()).OrderHistoryItemsCallBack(userId,id,from,to,current_page);
            // }

        } else {
            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
            errorText.setVisibility(View.VISIBLE);
        }*/


    }

    private void initializeCart() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            /*if(categoryId==null){
                new APIClient(getApplicationContext(), getApplicationContext(),  new GetProductsCallback()).allProductsAPICall(pageCount);
            }
            else{*/
            new APIClient(this, getApplicationContext(),  new GetHistoryCallback()).OrderHistoryItemsCallBack(userId,id,from,to,current_page);
            // }

        } else {
            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
            errorText.setVisibility(View.VISIBLE);
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

        switch (item.getItemId()) {
            case android.R.id.home:
                //mDrawerLayout.openDrawer(GravityCompat.START);
                onBackPressed();
                return true;



            case R.id.app_cart:
                //mDrawerLayout.openDrawer(GravityCompat.START);
                Intent i=new Intent(OrderHistory.this,OrderSummaryFilter.class);
                startActivityForResult(i, 1);

                return true;



          /*  case R.id.app_cart:
            {

            }*/
        }

        return super.onOptionsItemSelected(item);
    }



    private void onLoadMore() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if(!orderHistoryData.get(orderHistoryData.size()-1).isLoader()){
                orderHistoryData.add(new OrderHistoryModel(true, "Loading More..."));

                mAdapter.notifyItemInserted((orderHistoryData.size() - 1));
                System.out.println("previousTotal" + "dfsdfSDFzcfzc");
            }
            else{
                //rating.remove(new ProductRatingPageModel(true, "Loading More..."));
                //mAdapter.notifyItemRemoved((rating.size() - 1));
            }
            new APIClient(this, getApplicationContext(),  new GetHistoryCallback()).OrderHistoryItemsCallBack(userId, id, from, to,current_page);

        } else {

            /*Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();*/


            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }



    public class GetHistoryCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {

                JSONObject jsonObjectData;
                jsonObjectData=new JSONObject(response);
                System.out.println(jsonObjectData.toString());

                /*if(jsonObjectData.getString("errors").equals("true"))
                {
                    noOrders.setVisibility(View.VISIBLE);
                }

                else {*/


                if(jsonObjectData.getJSONObject("STATUS").getInt("CODE")==401)
                {
                    Snackbar.make(findViewById(android.R.id.content), jsonObjectData.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                    progressBar.setVisibility(View.GONE);
                    noOrders.setVisibility(View.VISIBLE);
                    orderHistoryData.clear();
                    mAdapter.notifyDataSetChanged();
                }
                else
                if(jsonObjectData.getJSONObject("STATUS").getInt("CODE")==204)
                {
                    /*Snackbar.make(findViewById(android.R.id.content), jsonObjectData.getJSONObject("STATUS").getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();*/


                    noOrders.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    orderHistoryData.clear();
                    mAdapter.notifyDataSetChanged();
                }



else if(jsonObjectData.getJSONObject("STATUS").getInt("CODE")==200)

                {

                    if (fetchInitial) {
                        orderHistoryData.clear();
                        fetchInitial = false;
                    } else {
                        orderHistoryData.remove((orderHistoryData.size() - 1));
                    }
                    // orderHistoryData.clear();
                    System.out.println(response);
                    noOrders.setVisibility(View.GONE);
                    System.out.println(jsonObjectData.toString());
                    JSONObject dataJson = jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA");
                    JSONArray data = dataJson.getJSONArray("ORDERS");

//                System.out.println(jsonObjectData.getJSONArray("data").length());
//orderCount.setText("Your Last "+jsonObjectData.getJSONArray("data").length()+" Orders");
                    for (int i = 0; i < data.length(); i++) {
                        //JSONArray jsonArrayData=jsonObjectData.getJSONArray("data");
                        JSONObject jsonCart = data.getJSONObject(i);
                        //  System.out.println(jsonObjectData.getJSONArray("data").getJSONObject(i).getJSONArray("cart").toString());

                        //  orderData.setIsHeader(true);
                        for (int j = 0; j < jsonCart.getJSONArray("ORDERS").length(); j++) {
                            OrderHistoryModel orderData = new OrderHistoryModel();
                            if (oldTimeframe.equals(jsonCart.getString("TIMEFRAME"))) {
                                orderData.setIsHeader(false);
                            } else {
                                orderData.setHeader(jsonCart.getString("TIMEFRAME"));
                                if (j == 0) {
                                    System.out.println("SHOW");
                                    orderData.setIsHeader(true);
                                } else {
                                    System.out.println("SHOW NO");
                                    orderData.setIsHeader(false);
                                }
                            }
                            oldTimeframe = jsonCart.getString("TIMEFRAME");
                            JSONObject jsonOrders = jsonCart.getJSONArray("ORDERS").getJSONObject(j);
                            //modelData.add(jsonOrders.toString());
                            orderData.setOrderId(jsonOrders.getString("ID"));
                            //orderData.setPrice(jsonObjectData.getString("price"));
                            orderData.setOrderNo(jsonOrders.getString("NUMBER"));
                            orderData.setDateTime(jsonOrders.getString("DATE_INSERT"));
                            orderData.setStatus(jsonOrders.getString("STATUS"));
                            orderData.setJsonData(jsonOrders.toString());

                            /*if (j == 0) {
                                System.out.println("SHOW");
                                orderData.setIsHeader(true);
                            } else {
                                System.out.println("SHOW NO");
                                orderData.setIsHeader(false);
                            }*/
                            orderHistoryData.add(orderData);
                            //JSONObject jsonObjData=jsonObjectData.getJSONObject(jsonArrayData);
                            //for(int j=0;j<jsonArrayData.getJSONArray("cart").length();j++)

                        }


                    /*for(int j=0;j<jsonCart.getJSONArray("cart").length();j++)
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
                        orderData.setOrderId(jsonCartData.getString("order_id"));
                        orderHistoryData.add(orderData);

                    }*/

                    //    System.out.println("CARE" + orderHistoryData.toString());
                        // System.out.println("CARE" +orderData.toString());


                        //System.out.println("RESPONSE" + );
                    }
                    totalPages = (int) Math.ceil((jsonObjectData.getJSONObject("OUTPUT").getJSONObject("NAVIGATION").getDouble("TOTAL") / jsonObjectData.getJSONObject("OUTPUT").getJSONObject("NAVIGATION").getDouble("COUNT")));
                    System.out.println("total pages" + totalPages);
                    System.out.println(Math.ceil(jsonObjectData.getJSONObject("OUTPUT").getJSONObject("NAVIGATION").getDouble("TOTAL") / jsonObjectData.getJSONObject("OUTPUT").getJSONObject("NAVIGATION").getDouble("COUNT")));
                    //  }
                    if (getApplicationContext() != null) {
                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                        // progressBar.startAnimation(animation);
                    }
                    progressBar.setVisibility(View.GONE);
                }
                //mainLay.setVisibility(View.VISIBLE);
                //initializeData();
            ////////////    mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                //current_page++;

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

    @Override
    public void onBackPressed()
    {
        Intent i=new Intent(OrderHistory.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(i);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            if (data.getExtras().containsKey("statusId")) {
                System.out.println("Status" + data.getExtras().getString("statusId").toString());
                id = data.getExtras().getString("statusId").toString();

            }
            if (data.getExtras().containsKey("From")) {
                System.out.println("From" + data.getExtras().getString("From").toString());
                from = data.getExtras().getString("From").toString();
            }
            if (data.getExtras().containsKey("To")) {


                System.out.println("To" + data.getExtras().getString("To").toString());
                to = data.getExtras().getString("To").toString();


            }
            ConnectivityManager connMgr = (ConnectivityManager)
                    getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
            /*if(categoryId==null){
                new APIClient(getApplicationContext(), getApplicationContext(),  new GetProductsCallback()).allProductsAPICall(pageCount);
            }
            else{*/
                current_page=1;
                fetchInitial=true;
                previousTotal = 0;
                oldTimeframe="";
                new APIClient(this, getApplicationContext(),  new GetHistoryCallback()).OrderHistoryItemsCallBack(userId,id,from,to,1);
                // }
                /*current_page=1;
                fetchInitial=true;
                previousTotal = 0;
                oldTimeframe="";*/

            } else {
                Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .show();
                errorText.setVisibility(View.VISIBLE);
            }
        }
        if (resultCode == RESULT_CANCELED)
        {

        }
    }




    public class CheckUserCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
             /*   if (obj.getInt("status") == 200) {
                    inputNumberErrorTextView.setVisibility(View.GONE);
                    if (obj.getJSONObject("message").getBoolean("register") == true) {
                        showRegister();
                    } else {
                        showLogin();
                    }
                }*/
             /*   inputNumberErrorTextView.setVisibility(View.GONE);
                if(obj.has("ERROR"))
                {
                    showLogin();
                }
                else
                {
                    showRegister();
                }*/
                if(obj.getJSONObject("STATUS").getInt("CODE")==400) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                    emailEditText.setError(obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE"));
                }
                else {
                    if(obj.getJSONObject("STATUS").getInt("CODE")==204)
                    {
                        showRegister();
                    }
                    else if(obj.getJSONObject("STATUS").getInt("CODE")==409)
                    {
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
                    userData.put("phone_number", emailId);
                    userData.put("access_key", password);


                    JSONObject dataToSend = new JSONObject(userData);
                    new APIClient(OrderHistory.this, OrderHistory.this, new loginAndRegisterUserCallback()).userLoginAPICall(emailId,password);
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
        final EditText regPhoneNumberEditText=   (EditText) registerDialog.findViewById(R.id.regPhoneNumberEditText);

        final EditText passwordEditText = (EditText) registerDialog.findViewById(R.id.passwordEditText);
        confirmPasswordEditText = (EditText) registerDialog.findViewById(R.id.confirmPasswordEditText);
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
                }
                else if (regPhoneNumberEditText.getText().toString().equalsIgnoreCase("")) {
                    regPhoneNumberEditText.setError("Please Enter Phone Number");
                }
                else if(!android.util.Patterns.PHONE.matcher(regPhoneNumberEditText.getText().toString()).matches()||regPhoneNumberEditText.getText().length()<10)
                {
                    regPhoneNumberEditText.setError("Incorrect Phone Number");
                }
                else if (!passwordEditText.getText().toString().equalsIgnoreCase(confirmPasswordEditText.getText().toString())) {
                    confirmPasswordEditText.setError(getString(R.string.password_do_not_match));
//                    Snackbar.make(findViewById(android.R.id.content), "Your password and confirm password are not same", Snackbar.LENGTH_LONG)
//                            .setActionTextColor(Color.RED)
//                            .show();
                } else {
                    password = passwordEditText.getText().toString();
                    HashMap<String, Object> userData = new HashMap<String, Object>();
                   /* userData.put("phone_number", emailId);
                    userData.put("access_key", password);*/
                    userData.put("email", emailId);
                    userData.put("password", password);
                    userData.put("cpassword", password);
                    userData.put("phone",regPhoneNumberEditText.getText().toString());
                    JSONObject dataToSend = new JSONObject(userData);
                    valueValidation="register";
                    new APIClient(OrderHistory.this, OrderHistory.this, new loginAndRegisterUserCallback()).useRegisterAPICall(dataToSend.toString());
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
              /*  if (obj.getBoolean("errors")) {
                    if ((loginDialog != null)) {
                        loginErrorTextView.setVisibility(View.VISIBLE);
                        loginErrorTextView.setText(obj.getString("message"));
                    }
                    else if ((registerDialog != null)) {
                        registrationErrorTextView.setVisibility(View.VISIBLE);
                        registrationErrorTextView.setText(obj.getString("message"));
                    }
                }*/

                if(obj.getJSONObject("STATUS").getInt("CODE")==400) {
                   /* Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();*/
                    for(int i=0;i<obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").length();i++)
                    {
                        JSONObject verificationCheck=obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(i);

                        if (verificationCheck.getString("FIELD").equals("PASSWORD")) {
                            confirmPasswordEditText.setError(verificationCheck.getString("MESSAGE"));
                        }
                        if (verificationCheck.getString("FIELD").equals("EMAIL")) {
                            Snackbar.make(findViewById(android.R.id.content), verificationCheck.getString("MESSAGE"), Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }

                    }
                    emailEditText.setError(obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE"));
                }
                else

                if(obj.getJSONObject("STATUS").getInt("CODE")==404)
                {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                else if(obj.getJSONObject("STATUS").getInt("CODE")==409)
                {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }

                else if(obj.getJSONObject("STATUS").getInt("CODE")==201) {

                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.putString(Constants.USER_MOBILE_PREFS, emailId);

                    editor.putString(Constants.USER_AUTH_TOKEN_PREFS, obj.getJSONObject("OUTPUT").getJSONObject("DATA").getString("TOKEN"));
                    //editor.putString(Constants.USER_ID_PREFS, obj.getJSONObject("OUTPUT").getJSONObject("DATA").getString("USER_ID"));
                    editor.putString(Constants.USER_ID_PREFS, "Y");
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
                    if(valueValidation.equals("register"))
                    {
                        if(obj.getJSONObject("OUTPUT").getJSONObject("DATA").has("CODE_EXP_TIME")) {
                            startTime=obj.getJSONObject("OUTPUT").getJSONObject("DATA").getInt("CODE_EXP_TIME") * 1000;
                            verifyDialog();
                        }
                    }
                    else {
                        initializeCart();
                    }
                }

                else
                {
                    Snackbar.make(findViewById(android.R.id.content), "Server Error Occured", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
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



    private void verifyDialog() {
        verifyDialog = new Dialog(this);
        ///  verifyDialog.setCancelable(true);
        verifyDialog.setCanceledOnTouchOutside(false);
        verifyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        verifyDialog.setContentView(R.layout.dialog_phone_verification);
        final Button resend = (Button) verifyDialog.findViewById(R.id.resend);
        final Button nextButton = (Button) verifyDialog.findViewById(R.id.verify);
        final TextView timer=(TextView)verifyDialog.findViewById(R.id.timer);
        Button changePhone=(Button)verifyDialog.findViewById(R.id.changePhone);
        final EditText verifyCode=(EditText)verifyDialog.findViewById(R.id.verifyCode);
        resend.setEnabled(false);

        // countDownTimer = new MyCountDownTimer(startTime, interval);

        sendValue="verify";


        changePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode.setHint("Enter New Phone Number");
                nextButton.setText("Send");
                sendValue="changeNumber";
//sendData(sendValue);
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendValue="resend";
                //verifyDialog.dismiss();
                sendData(sendValue);
                timerLoop.cancel();
                timerLoop.start();
                sendValue="verify";
               /* new CountDownTimer(startTime, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer.setText("" + millisUntilFinished / 1000);
                    }

                    @Override
                    public void onFinish() {
                        timer.setText("Code Expired");
                        resend.setEnabled(true);
                        resend.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                    }
                }.start();*/
            }
        });

        timerLoop=new CountDownTimer(startTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                timer.setText("Code Expired");
                resend.setEnabled(true);
                resend.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
            }
        }.start();





        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sendValue.equals("verify")) {
                    if (verifyCode.getText().toString().equalsIgnoreCase("")) {
                        verifyCode.setError("Please Enter Code");
                    }
                    else
                    {
                        code=verifyCode.getText().toString();
                        // verifyDialog.dismiss();
                        sendData(sendValue);
                    }


                } else if (sendValue.equals("changeNumber")) {
                    if (verifyCode.getText().toString().equalsIgnoreCase("")) {
                        verifyCode.setError("Please Enter Phone Number");
                    } else if (!android.util.Patterns.PHONE.matcher(verifyCode.getText().toString()).matches() || verifyCode.getText().length() < 10) {
                        verifyCode.setError("Incorrect Phone Number");
                    } else {
                        phoneNumber = verifyCode.getText().toString();
                        verifyCode.setText("");
                        verifyCode.setHint("Enter Verification Code");
                        sendValue = "changeNumber";
                        //phoneNumber = verifyCode.getText().toString();
                        //   verifyDialog.dismiss();
                        sendData(sendValue);
                        sendValue="verify";
                        nextButton.setText("Verify");
                        timerLoop.cancel();
                        timerLoop.start();
                      /*  new CountDownTimer(startTime, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                timer.setText("" + millisUntilFinished / 1000);
                            }

                            @Override
                            public void onFinish() {
                                timer.setText("Code Expired");
                                resend.setEnabled(true);
                                resend.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                            }
                        }.start();*/
                    }
                }
            }
        });




        verifyDialog.show();
        verifyDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }


    public void sendData(String value)
    {
        if(value.equals("verify"))
        {
            HashMap<String,String > verifyData=new HashMap<String ,String >();
            //   verifyData.put("user_id",userId);
            //   verifyData.put("code", code);
            //    verifyData.put("mode","verify-sms-code");

            JSONObject dataVerify=new JSONObject(verifyData);
            new APIClient(OrderHistory.this, OrderHistory.this, new verifyCode()).verifySms(code);
        }
        else if(value.equals("changeNumber"))
        {



            HashMap<String,String > resendData=new HashMap<String ,String >();
            //   resendData.put("user_id",userId);
            resendData.put("phone", phoneNumber);
            //  resendData.put("mode","send-verify-sms");

            JSONObject dataResend=new JSONObject(resendData);

            new APIClient(OrderHistory.this, OrderHistory.this, new changePhone()).reSendCallBack(dataResend.toString());
        }
        else if(value.equals("resend"))
        {
            HashMap<String,String > resendSmsData=new HashMap<String ,String >();
            //      resendSmsData.put("user_id",userId);

            // resendSmsData.put("mode","resend-verify-sms");

            JSONObject dataSmsResend=new JSONObject(resendSmsData);

            new APIClient(OrderHistory.this, OrderHistory.this, new reSendSms()).reSendSms(dataSmsResend.toString());
        }

    }


    public class verifyCode extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
                System.out.println("OUTPUT" + obj);
                if(obj.getJSONObject("STATUS").getInt("CODE")==200) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("STATUS").getString("MESSAGE").toString(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                    verifyDialog.dismiss();
                    initializeCart();

                  /*  Intent i = new Intent(ProductOptionsActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();*/
                }
                else if (obj.getJSONObject("STATUS").getInt("CODE")==201) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("STATUS").getString("MESSAGE").toString(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();

                    /*Intent i = new Intent(ProductOptionsActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();*/
                    initializeCart();
                }
                else if(obj.getJSONObject("STATUS").getInt("CODE")==400)
                {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE").toString(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                    verifyDialog.dismiss();
                }
                else{
                    Snackbar.make(findViewById(android.R.id.content), "Server Error Occured", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                progressBar.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
                progressBar.setVisibility(View.GONE);
            }

        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);

        }
    }


    public class reSendSms extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
                if(obj.getJSONObject("STATUS").getInt("CODE")==201) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("STATUS").getString("MESSAGE").toString(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                else if(obj.getJSONObject("STATUS").getInt("CODE")==200) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("STATUS").getString("MESSAGE").toString(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                else{
                    Snackbar.make(findViewById(android.R.id.content), "Server Error Occured", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                progressBar.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
                progressBar.setVisibility(View.GONE);
            }

        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);

        }
    }




    public class changePhone extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
                if(obj.getJSONObject("STATUS").getInt("CODE")==201) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("STATUS").getString("MESSAGE").toString(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                else if(obj.getJSONObject("STATUS").getInt("CODE")==200) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("STATUS").getString("MESSAGE").toString(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                else{
                    Snackbar.make(findViewById(android.R.id.content), "Server Error Occured", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                progressBar.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
                progressBar.setVisibility(View.GONE);
            }

        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);

        }
    }

}
