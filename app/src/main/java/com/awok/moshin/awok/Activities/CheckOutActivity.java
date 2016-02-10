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
import android.os.CountDownTimer;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.awok.moshin.awok.Adapters.CheckOutAdapter;
import com.awok.moshin.awok.Adapters.ContactsAdapter;
import com.awok.moshin.awok.Adapters.CustomAdapter;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.Checkout_Model;
import com.awok.moshin.awok.Models.Payment_Pick;
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
    JSONObject dataSpinner;
    private TextView cartEmptyText, prodPrice;
    private RecyclerView.Adapter mAdapter;
    private TextView errorText;
    private String sendValue="";
    Dialog verifyDialog;
    JSONObject sendJson;
    ProgressBar progressBar;
    LinearLayout progressLayout;
    private TextView itemsCount,totalText,subTotalText,shippingText;
    String TAG = "CartActivity";
    SharedPreferences mSharedPrefs;
    private String valueValidation="";
    private RecyclerView.LayoutManager mLayoutManager;
    String emailId = "";
    String password = "";
    String sendLocId="";
    List<String> listCountry = new ArrayList<String>();
    private ArrayList<Checkout_Model> overViewList = new ArrayList<Checkout_Model>();
    String[] schoolbag = new String[]{"SHON", "SHON", "SONU", "SHON", "SONU"};
    ProgressBar progressBarForm;
    ProgressBar progressBarLogin;
    Dialog inputNumberDialog;
    ArrayAdapter<String> dataAdapter;
    ArrayAdapter<String> stateDataAdapter;
    EditText emailEditText;
    ArrayAdapter<String> cityDataAdapter;
    Snackbar toast;
   // private String shippingText;
   private  long startTime=0;
    Dialog shippingDialog;

    Dialog loginDialog;
    Dialog registerDialog;
    TextView loginErrorTextView, registrationErrorTextView, inputNumberErrorTextView;
    ArrayList<String> countryCodes;
    ArrayList<String> countryCodeNumbers;
    CountDownTimer timerLoop;
    private String countryKey;
    String code="";
    List<String[]> list;
    List<String> listState = new ArrayList<String>();
    List<String> listCity = new ArrayList<String>();
    HashMap<String, String> countryId=new HashMap<String, String>();
    HashMap<String, String> stateId=new HashMap<String, String>();
    HashMap<String, String> locationId=new HashMap<String, String>();
    EditText confirmPasswordEditText;
    //LinearLayout totalLay;
     ActionBar ab;
    String userId="";
    String phoneNumber="";
    String totalItems;
    boolean allowUpdates = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        if ((mSharedPrefs.contains(Constants.USER_ID_PREFS)))
        {
            userId = mSharedPrefs.getString(Constants.USER_ID_PREFS, null);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.overViewRecyclerView);
        bottomLay = (LinearLayout) findViewById(R.id.bottomLay);
        bottomLay.setVisibility(View.GONE);
        cartEmptyText = (TextView) findViewById(R.id.cartEmptyText);
        cartEmptyText.setVisibility(View.GONE);
        //shippingText=(TextView)findViewById(R.id.shippingText);
        prodPrice = (TextView) findViewById(R.id.prod_discountPrice);
        progressBar = (ProgressBar) findViewById(R.id.marker_progress);
        progressLayout = (LinearLayout) findViewById(R.id.progressLayout);
        progressLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        errorText=(TextView)findViewById(R.id.error_text);
        totalText=(TextView)findViewById(R.id.totalText);
        /*totalLay=(LinearLayout)findViewById(R.id.totalLay);
        totalLay.setVisibility(View.GONE);
        totalText=(TextView)findViewById(R.id.totalText);
        subTotalText=(TextView)findViewById(R.id.subTotal);
        shippingText=(TextView)findViewById(R.id.shippingText);
        itemsCount=(TextView)findViewById(R.id.totalItemsCount);
        allowUpdates = true;*/
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
        mRecyclerView.setHasFixedSize(true);

        //mRecyclerView.setLayoutManager(new MyLinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setLayoutManager(new com.awok.moshin.awok.Util.LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
       /* int viewHeight = 40 * overViewList.size();
        mRecyclerView.getLayoutParams().height = viewHeight;*/
    ////////////    mAdapter = new CustomAdapter(CheckOutActivity.this, overViewList);
        mAdapter = new CustomAdapter(this,overViewList);
        //mRecyclerView.setAdapter(mAdapter);
        //////////////////////////mRecyclerView.setAdapter(mAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

         ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.back_button);
        //initializeCart();




 /*       shippingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    new APIClient(CheckOutActivity.this, getApplicationContext(), new GetCountryCallBack()).countryCallBack();


                } else {

                    Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();

                }
            }
        });*/


      /*  if (mSharedPrefs.contains(Constants.USER_ID_PREFS)) {
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
                    if (emailEditText.getText().toString().equalsIgnoreCase("")) {
                        emailEditText.setError("Please enter your Email Address");
                    } else {
                        if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText()).matches()) {
                            Log.v(TAG, "phone number is correct");
                            emailId =emailEditText.getText().toString();
                            new APIClient(CheckOutActivity.this, CheckOutActivity.this, new CheckUserCallback()).userCheckAPICall(emailId);
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
        }*/
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
                    final Dialog countryCodeDialog = new Dialog(CheckOutActivity.this, R.style.AppCompatAlertDialogStyle);
                    countryCodeDialog.setCancelable(true);
                    countryCodeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    countryCodeDialog.setContentView(R.layout.dialog_country_code);
                    final ListView mList = (ListView)countryCodeDialog.findViewById(R.id.codeListView);

                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            CheckOutActivity.this,
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
                            new APIClient(CheckOutActivity.this, CheckOutActivity.this, new CheckUserCallback()).userCheckAPICall(emailId);
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
                i.putExtra("userId",userId);
                i.putExtra("locationId",sendLocId);
                i.putExtra("model",sendJson.toString());
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
          //  new APIClient(this, getApplicationContext(), new GetCartCallback()).cartItemsCallBack("55f6a9462f17f64a9b5f5ce4");
            new APIClient(this, getApplicationContext(), new GetCartCallback()).cartItemsCallBack(userId,sendLocId);

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
                    userData.put("phone_number", emailId);
                    userData.put("access_key", password);


                    JSONObject dataToSend = new JSONObject(userData);
                    new APIClient(CheckOutActivity.this, CheckOutActivity.this, new loginAndRegisterUserCallback()).userLoginAPICall(emailId,password);
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

public void populate()
{

    ConnectivityManager connMgr = (ConnectivityManager)
            getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {

        new APIClient(CheckOutActivity.this, getApplicationContext(), new GetCountryCallBack()).countryCallBack();


    } else {

        Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .show();

    }


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
                sendJson=jsonObjectData;
                System.out.println(jsonObjectData.toString());
                // int length = jsonObjectData.length();
/////////                System.out.println(jsonObjectData.getJSONObject("data"));
///                System.out.println(jsonObjectData.getJSONObject("data").getJSONArray("seller_cart"));

                JSONArray jsonArray;
                /////              jsonArray=jsonObjectData.getJSONObject("data").getJSONArray("seller_cart");

                //String sellerName=jsonArray.getString("seller").toString();
              /*  if (jsonObjectData.has("ERROR")) {
                    if (jsonObjectData.getString("ERROR").equals("true")) {
                        bottomLay.setVisibility(View.GONE);
                        cartEmptyText.setVisibility(View.VISIBLE);
             ////////           totalLay.setVisibility(View.GONE);
                        ab.setTitle("Shopping Cart");
                    }
                }else {*/
              ///////////  totalLay.setVisibility(View.VISIBLE);
                    if(jsonObjectData.getJSONObject("STATUS").getInt("CODE")==200) {
                bottomLay.setVisibility(View.VISIBLE);
                cartEmptyText.setVisibility(View.GONE);
                //   totalItems=jsonObjectData.getJSONObject("data").getString("total_items");
                totalItems = jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("SUMMARY").getString("COUNT");
                //totalItems = "404";
                String subtotal = jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("SUMMARY").getString("SUM");
                String total = jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("SUMMARY").getString("TOTAL");
                String shipping = jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("SUMMARY").getString("DELIVERY");
                ab.setTitle("Shopping Cart (" + totalItems + ")");
                    ab.setHomeAsUpIndicator(R.drawable.back_button);
                    totalText.setText("AED " + total);
            /////////////////////////////////////////////////    itemsCount.setText(totalItems);
                /*subTotalText.setText("AED " + subtotal);
                totalText.setText("AED " + total);*/
               /* if (shipping.equals("0")) {
                    shippingText.setText("FREE");
                } else {
                    shippingText.setText("AED " + shipping);
                }*/
                int length = jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("BASKET").length();
                JSONArray jArray = jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("BASKET");


                for (int i = 0; i < length; i++) {
                    Checkout_Model listData = new Checkout_Model();
                    JSONObject jObj = jArray.getJSONObject(i);


                    listData.setId(jObj.optString("ID"));
                    listData.setName(jObj.optString("NAME"));
                    listData.setProduct_id(jObj.optString("PRODUCT_ID"));
                    listData.setOld_price(jObj.optString("OLD_PRICE"));
                    listData.setPrice(jObj.optString("PRICE"));
                    listData.setCurrency(jObj.optString("CURRENCY"));
                    String temp=jObj.optString("PERCENTAGE").toString();
                    String output = temp.substring(0, temp.indexOf('.'));

                    listData.setDiscount(output);
                    //listData.setDiscount(jObj.optString("PERCENTAGE"));
                    listData.setQuantity(jObj.optString("QUANTITY"));
                    listData.setImage(jObj.optString("IMAGE"));
                    listData.setIsHeader(true);
                    listData.setIsEditable(true);


                    overViewList.add(listData);

                }
overViewList.add(new Checkout_Model(jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("SUMMARY").getString("COUNT"),jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("SUMMARY").getString("SUM"),
        jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("SUMMARY").getString("TOTAL"),jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("SUMMARY").getString("DELIVERY"),true));

System.out.println("SIZE" + overViewList.size());
            }
                    else if(jsonObjectData.getJSONObject("STATUS").getInt("CODE")==204)
                    {
                        bottomLay.setVisibility(View.GONE);
                        cartEmptyText.setVisibility(View.VISIBLE);
                        ////////           totalLay.setVisibility(View.GONE);
                        ab.setTitle("Shopping Cart");
                    }
                else {
                        bottomLay.setVisibility(View.GONE);
                        cartEmptyText.setVisibility(View.VISIBLE);
                        ////////           totalLay.setVisibility(View.GONE);
                        ab.setTitle("Shopping Cart");
                        Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Server Error Occured ", Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED);

                        View snackbarView = snackbar.getView();

                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);
                        snackbar.show();
                    }


                  /*  for (int i = 0; i < length; i++) {
                        JSONObject jsonObject = jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("BASKET").getJSONObject(i);

                        JSONArray productDetails = jsonObject.getJSONArray("items");
                        int lengthOfProducts = productDetails.length();
                        for (int j = 0; j < lengthOfProducts; j++) {


                            Checkout listData = new Checkout();
                            JSONObject jsonObjectProductDetails = productDetails.getJSONObject(j);
                            totalFlag=jsonObjectProductDetails.getString("seller_name").toString();
                            listData.setOverViewText(jsonObjectProductDetails.getString("unit_price"));
//                            listData.setTotalPrice(jsonObjectProductDetails.getString("total_price"));
                            listData.setTotalPrice(jsonObjectProductDetails.getString("total"));
                            //listData.setTotalPrice("response needed");
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
                            listData.setRemainingStock(jsonObjectProductDetails.getString("total_quantity"));
                            //listData.setRemainingStock("20");
                            listData.setIsEditable(true);
//                            prodPrice.setText((jsonObjectData.getJSONObject("data").getString("total")) + " AED");
                            overViewList.add(listData);

                            System.out.println("adhghnf" + listData.getIsHeader());
                            System.out.println("adhghnf" +listData.getIsFooter());
                        }
                    *//*listData.setOverViewText(getResources().getString(R.string.check_out_price));
                    listData.setOverViewTitle(getResources().getString(R.string.checkout_head));
                    listData.setSellerLabel(schoolbag[i].toString());*//*
                    *//*listData.setOverViewText(jsonObject.getString("seller"));
                    listData.setOverViewTitle(jsonObject.getString("seller"));
                    listData.setSellerLabel(jsonObject.getString("seller"));*//*

                    }*/


                    // mAdapter.notifyAll();

            //    }

                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    progressLayout.startAnimation(animation);
                }
                progressLayout.setVisibility(View.GONE);
                //initializeData();
                /////////////////mRecyclerView.setAdapter(mAdapter);
//                allowUpdates = true;
//                mAdapter.setAllowUpdate();
                mRecyclerView.setAdapter(mAdapter);
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
      //  new APIClient(this, getApplicationContext(), new GetCartCallback()).cartItemsCallBack("55f6a9462f17f64a9b5f5ce4");
        new APIClient(this, getApplicationContext(), new GetCartCallback()).cartItemsCallBack(userId,sendLocId);
    }


public void shipAdd(final JSONObject dataSpinner) {



    final JSONObject dataCountry=dataSpinner;
    shippingDialog = new Dialog(this);
    shippingDialog.setCancelable(true);
    shippingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    shippingDialog.setContentView(R.layout.dialog_shipping_select);
    Button cancelButton = (Button) shippingDialog.findViewById(R.id.cancelButton);
    Button nextButton = (Button) shippingDialog.findViewById(R.id.nextButton);
    final Spinner country=(Spinner)shippingDialog.findViewById(R.id.country);
            final Spinner state=(Spinner)shippingDialog.findViewById(R.id.state);
                    final Spinner area=(Spinner)shippingDialog.findViewById(R.id.area);

    dataAdapter = new ArrayAdapter<String>(CheckOutActivity.this,
            android.R.layout.simple_spinner_item, listCountry);
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    stateDataAdapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, listState);
    stateDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    cityDataAdapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, listCity);
    cityDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);









    area.setAdapter(cityDataAdapter);



    state.setAdapter(stateDataAdapter);
    country.setAdapter(dataAdapter);

    country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

      /*      // On selecting a spinner item
            String item = listCountry.get(position);

            // Showing selected spinner item
            Toast.makeText(CheckOutActivity.this,
                    "Selected Country : " + item, Toast.LENGTH_LONG).show();*/
            listCity.clear();
            area.setAdapter(cityDataAdapter);
            String country_text = country.getSelectedItem().toString();
            System.out.println("jhgvjhvjngv" + country.getSelectedItem().toString());
                    /*ConnectivityManager connMgr = (ConnectivityManager)
                            getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);*/
            String valueCountry = countryId.get(country_text);
            countryKey = valueCountry;
            System.out.println("jhgvjhvjngv" + valueCountry);


            try {
                if (dataSpinner.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("CITIES").has(valueCountry)) {
                    JSONArray jCity = dataSpinner.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("CITIES").getJSONArray(valueCountry);
                    for (int i = 0; i < jCity.length(); i++) {
                        JSONObject jData = jCity.getJSONObject(i);
                        listState.add(jData.getString("NAME"));
                        stateId.put(jData.getString("NAME"), jData.getString("ID"));

                        System.out.println(listState);
                    }
                    state.setAdapter(stateDataAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });


    state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //countrySpinner.requestFocus();
            //countrySpinner.setFocusable(true);
            //countrySpinner.setFocusableInTouchMode(true);

                listCity.clear();
                area.setAdapter(cityDataAdapter);
                String state_text=state.getSelectedItem().toString();
                System.out.println("jhgvjhvjngv" + state.getSelectedItem().toString());
                           /* ConnectivityManager connMgr = (ConnectivityManager)
                                    getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);*/
                String valueState = stateId.get(state_text);
                System.out.println("jhgvjhvjngv" + valueState);
                try {
                    if(dataSpinner.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("AREAS").getJSONObject(countryKey).has(valueState)) {
                        JSONArray jCity = dataSpinner.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("AREAS").getJSONObject(countryKey).getJSONArray(valueState);
                        for (int i = 0; i < jCity.length(); i++) {
                            JSONObject jData = jCity.getJSONObject(i);
                            listCity.add(jData.getString("NAME"));
                            locationId.put(jData.getString("NAME"), jData.getString("ID"));


                            System.out.println(listState);
                        }
                        area.setAdapter(cityDataAdapter);
                   }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                               /* NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                                if (networkInfo != null && networkInfo.isConnected()) {

                                    new APIClient(AddNewAddress.this, getApplicationContext(), new GetCityCallBack()).CityCallBack(valueState);
                                    // System.out.println("jhgvjhvjngv" + valueState);


                                } else {

                                    Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                                            .setActionTextColor(Color.RED)
                                            .show();

                                }*/
            }








        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });

    cancelButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            shippingDialog.cancel();
        }
    });


    nextButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String locTxt = area.getSelectedItem().toString();



            sendLocId=locationId.get(locTxt);
            System.out.print("LN"+sendLocId);
            shippingDialog.dismiss();
            refreshData();


        }
    });

    System.out.print("LN" + sendLocId);
    shippingDialog.show();
    shippingDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
}











    public class GetCountryCallBack extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {

                System.out.println(response);
                System.out.print("LN" + sendLocId);


                dataSpinner = new JSONObject(response);
                //JSONObject jDataMain=jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA");
                System.out.println(dataSpinner.toString());
               /* if (jsonObjectData.getString("status").equals("0")) {

                } else {*/


                JSONArray country=dataSpinner.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("COUNTRIES");


                for(int i=0;i<country.length();i++)
                {
                    JSONObject jData=country.getJSONObject(i);
                    listCountry.add(jData.getString("NAME"));
                    countryId.put(jData.getString("NAME"), jData.getString("ID"));


                    System.out.println(list);
                    System.out.println("COOL"+countryId.toString());
                }
                shipAdd(dataSpinner);
                // }
          //      countrySpinner.setAdapter(dataAdapter);

             /*   if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    //progressBar.startAnimation(animation);
                }
                //progressBar.setVisibility(View.GONE);
                if(getIntent().getExtras()!=null){
                    countryValue = getIntent().getExtras().getString("country");
                    if (spinnerCountUpdate == 0) {
                        if (!countryValue.equals(null)) {
                            int spinnerPosition = dataAdapter.getPosition(countryValue);
                            countrySpinner.setSelection(spinnerPosition);
                        }
                    }
                }*/





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
        TextView skip=(TextView)verifyDialog.findViewById(R.id.skipButton);
        skip.setVisibility(View.VISIBLE);
        final EditText verifyCode=(EditText)verifyDialog.findViewById(R.id.verifyCode);
        resend.setEnabled(false);

        // countDownTimer = new MyCountDownTimer(startTime, interval);

        sendValue="verify";

skip.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        verifyDialog.dismiss();
        initializeCart();
    }
});
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
            new APIClient(CheckOutActivity.this, CheckOutActivity.this, new verifyCode()).verifySms(code);
        }
        else if(value.equals("changeNumber"))
        {



            HashMap<String,String > resendData=new HashMap<String ,String >();
            //   resendData.put("user_id",userId);
            resendData.put("phone", phoneNumber);
            //  resendData.put("mode","send-verify-sms");

            JSONObject dataResend=new JSONObject(resendData);

            new APIClient(CheckOutActivity.this, CheckOutActivity.this, new changePhone()).reSendCallBack(dataResend.toString());
        }
        else if(value.equals("resend"))
        {
            HashMap<String,String > resendSmsData=new HashMap<String ,String >();
            //      resendSmsData.put("user_id",userId);

            // resendSmsData.put("mode","resend-verify-sms");

            JSONObject dataSmsResend=new JSONObject(resendSmsData);

            new APIClient(CheckOutActivity.this, CheckOutActivity.this, new reSendSms()).reSendSms(dataSmsResend.toString());
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
