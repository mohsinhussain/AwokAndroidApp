package com.awok.moshin.awok.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.awok.moshin.awok.Models.Categories;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddNewAddress extends AppCompatActivity {
    List<String> list = new ArrayList<String>();
    List<String> listState = new ArrayList<String>();
    List<String> listCity = new ArrayList<String>();
    HashMap<String, String> countryId=new HashMap<String, String>();
    HashMap<String, String> stateId=new HashMap<String, String>();
    HashMap<String, String> locationId=new HashMap<String, String>();
    ArrayAdapter<String> dataAdapter;
    ArrayAdapter<String> stateDataAdapter;
    ArrayAdapter<String> cityDataAdapter;
    private LinearLayout stateLay;
    private CheckBox primaryCheck;
    private String checkValue;
    LinearLayout progressLayout;
    private String countryKey;
    private LinearLayout cityLay;
    private Button clear,save;
    private TextInputLayout inputLayoutName, inputAddress1, inputAddress2,pinLayout, phone2Lay, phoneLay;
    int check=0;
    SharedPreferences mSharedPrefs;
    String userId="";
    int spinnerCountUpdate = 0;
    String countryValue,stateValue,cityValue;
    int stateCheck=0;
    private EditText name,add1,add2,email, phone1, phone2;
    private Spinner countrySpinner,stateSpinner,citySpinner;
    private JSONObject jData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);


        countrySpinner=(Spinner)findViewById(R.id.country);
        stateSpinner=(Spinner)findViewById(R.id.state);
        citySpinner=(Spinner)findViewById(R.id.city);
        name=(EditText)findViewById(R.id.name);
        phone1=(EditText)findViewById(R.id.phone);
        primaryCheck=(CheckBox)findViewById(R.id.primaryCheck);
        phone2=(EditText)findViewById(R.id.phone2);
        add1=(EditText)findViewById(R.id.address1);
                add2=(EditText)findViewById(R.id.address2);
                email=(EditText)findViewById(R.id.zipPostal);

        name.addTextChangedListener(new MyTextWatcher(name));
        phone1.addTextChangedListener(new MyTextWatcher(phone1));
        phone2.addTextChangedListener(new MyTextWatcher(phone2));
        add1.addTextChangedListener(new MyTextWatcher(add1));
        add2.addTextChangedListener(new MyTextWatcher(add2));
        email.addTextChangedListener(new MyTextWatcher(email));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(getString(R.string.add_new_address_header));
        ab.setHomeAsUpIndicator(R.drawable.back_button);
        stateLay=(LinearLayout)findViewById(R.id.stateLay);
        cityLay=(LinearLayout)findViewById(R.id.cityLay);
        inputLayoutName = (TextInputLayout) findViewById(R.id.nameLayout);
        phoneLay = (TextInputLayout) findViewById(R.id.phoneLay);
        phone2Lay = (TextInputLayout) findViewById(R.id.phone2Lay);
        inputAddress1 = (TextInputLayout) findViewById(R.id.addressLay);
        inputAddress2 = (TextInputLayout) findViewById(R.id.address2Lay);
        progressLayout=(LinearLayout)findViewById(R.id.progressLayout);
        progressLayout.setVisibility(View.GONE);
        pinLayout = (TextInputLayout) findViewById(R.id.zipLay);
clear=(Button)findViewById(R.id.clear);
        save=(Button)findViewById(R.id.save);

primaryCheck.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        System.out.println("txt"+primaryCheck.isChecked());
    }
});
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateName()&&validateAddress1()&&validateAddress2()&&validPin()) {
                    name.setText("");
                    add1.setText("");
                    add2.setText("");
                    countrySpinner.setSelection(0);
                    stateSpinner.setSelection(0);
                    citySpinner.setSelection(0);
                    email.setText("");
                    phone1.setText("");
                    phone2.setText("");

                }
            }
        });

        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        if ((mSharedPrefs.contains(Constants.USER_ID_PREFS))) {
            userId = mSharedPrefs.getString(Constants.USER_ID_PREFS, null);
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateName()&&validateAddress1()&&isValidEmail(email.getText())&&isValidPhone(phone1.getText())) {
                    HashMap<String,Object> addNewAddress=new HashMap<String, Object>();

                    String locTxt = citySpinner.getSelectedItem().toString();

                    String locIdSend = locationId.get(locTxt);

                    addNewAddress.put("65", name.getText().toString());

                    //addToCartData.put("user_id", "55f6a9e52f17f64a9b5f5ce5");
                    addNewAddress.put("67", add1.getText().toString());
                    addNewAddress.put("69",add2.getText().toString());
                    addNewAddress.put("50",locIdSend);
                   /* if (primaryCheck.isChecked())
                    {
                         checkValue="Y";
                    }
                    else
                    {
                         checkValue="N";
                    }*/
                    /*if(stateSpinner.isShown()){
                        addNewAddress.put("state",stateSpinner.getSelectedItem().toString());
                    }
                    else{
                        addNewAddress.put("state","");
                    }*/
                    //addNewAddress.put("country",countrySpinner.getSelectedItem().toString());
                    addNewAddress.put("71",phone2.getText().toString());
                    addNewAddress.put("78",email.getText().toString());
                    //addNewAddress.put("116",checkValue);
                    addNewAddress.put("70",phone1.getText().toString());

                    JSONObject dataToSend=new JSONObject(addNewAddress);


                    ConnectivityManager connMgr = (ConnectivityManager)
                            getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        if(getIntent().hasExtra("name")){
                            //new APIClient(AddNewAddress.this, getApplicationContext(), new AddNewAddressCallBack()).editAddressAPICall(getIntent().getExtras().getString("id"),userId, dataToSend.toString());
                            new APIClient(AddNewAddress.this, getApplicationContext(), new AddNewAddressCallBack()).editAddressAPICall(getIntent().getExtras().getString("id"),dataToSend.toString());

                        }
                        else{
                            //new APIClient(AddNewAddress.this, getApplicationContext(), new AddNewAddressCallBack()).addAddressAPICall(userId, dataToSend.toString());
                            new APIClient(AddNewAddress.this, getApplicationContext(), new AddNewAddressCallBack()).addAddressAPICall(dataToSend.toString());
                        }



                    } else {

                        Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .show();

                    }
                   return;
                    }

               /* if (!validateAddress1()) {
                    return;
                   }

                if (!validateAddress2()) {
                    return;
                    }







                if (!validPin()) {
                    return;
                }*/



            }
        });



        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stateDataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listState);
        stateDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cityDataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listCity);
        cityDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        countrySpinner.setAdapter(dataAdapter);
//        countrySpinner.setSelection(1);



        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            new APIClient(this, getApplicationContext(), new GetCountryCallBack()).countryCallBack();


        } else {

            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();

        }

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                check = check + 1;
                if (check >= 1) {
                    listCity.clear();
                    citySpinner.setAdapter(cityDataAdapter);
                    String country_text = countrySpinner.getSelectedItem().toString();
                    System.out.println("jhgvjhvjngv" + countrySpinner.getSelectedItem().toString());
                    /*ConnectivityManager connMgr = (ConnectivityManager)
                            getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);*/
                    String valueCountry = countryId.get(country_text);
                    countryKey=valueCountry;
                    System.out.println("jhgvjhvjngv" + valueCountry);


                    try {
                        if(jData.getJSONObject("CITIES").has(valueCountry))
                        {
                            JSONArray jCity=jData.getJSONObject("CITIES").getJSONArray(valueCountry);
                            for(int i=0;i<jCity.length();i++)
                            {
                                JSONObject jData=jCity.getJSONObject(i);
                                listState.add(jData.getString("NAME"));
                                stateId.put(jData.getString("NAME"),jData.getString("ID"));

                                System.out.println(listState);
                            }
                            stateSpinner.setAdapter(stateDataAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    /*NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                        if (networkInfo != null && networkInfo.isConnected()) {

                            new APIClient(AddNewAddress.this, getApplicationContext(), new GetStateCallBack()).StateCallBack(valueCountry);


                        } else {

                            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.RED)
                                    .show();

                         } */
                    }

            }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

                stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //countrySpinner.requestFocus();
                        //countrySpinner.setFocusable(true);
                        //countrySpinner.setFocusableInTouchMode(true);
                        stateCheck=stateCheck+1;
                        if(stateCheck>=1)
                        {
                            listCity.clear();
                            citySpinner.setAdapter(cityDataAdapter);
                            String state_text=stateSpinner.getSelectedItem().toString();
                            System.out.println("jhgvjhvjngv" + stateSpinner.getSelectedItem().toString());
                           /* ConnectivityManager connMgr = (ConnectivityManager)
                                    getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);*/
                            String valueState = stateId.get(state_text);
                            System.out.println("jhgvjhvjngv" + valueState);
                            try {
                                if(jData.getJSONObject("AREAS").getJSONObject(countryKey).has(valueState)) {
                                    JSONArray jCity = jData.getJSONObject("AREAS").getJSONObject(countryKey).getJSONArray(valueState);
                                    for (int i = 0; i < jCity.length(); i++) {
                                        JSONObject jData = jCity.getJSONObject(i);
                                        listCity.add(jData.getString("NAME"));
                                        locationId.put(jData.getString("NAME"), jData.getString("ID"));


                                        System.out.println(listState);
                                    }
                                    citySpinner.setAdapter(cityDataAdapter);
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






            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().hasExtra("name")){
//            countrySpinner.setPrompt;
//            stateSpinner=(Spinner)findViewById(R.id.state);
//            citySpinner=(Spinner)findViewById(R.id.city);
            name.setText(getIntent().getExtras().getString("name"));
            add1.setText(getIntent().getExtras().getString("address1"));
            add2.setText(getIntent().getExtras().getString("address2"));
            email.setText(getIntent().getExtras().getString("zip"));
            phone1.setText(getIntent().getExtras().getString("mobile1"));
            if(getIntent().getExtras().getString("mobile2")!=null && !getIntent().getExtras().getString("mobile2").equalsIgnoreCase("null")){
                phone2.setText(getIntent().getExtras().getString("mobile2"));
            }


if(getIntent().getExtras().getBoolean("isPrimary"))
{
    primaryCheck.setChecked(true);
}
            else
{
    primaryCheck.setChecked(false);
}

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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




    public class GetCountryCallBack extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {

                System.out.println(response);

                JSONObject jsonObjectData;
                jsonObjectData = new JSONObject(response);







                jData = jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA");
                System.out.println(jsonObjectData.toString());
               /* if (jsonObjectData.getString("status").equals("0")) {

                } else {*/


                JSONArray country = jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("COUNTRIES");


                for (int i = 0; i < country.length(); i++) {
                    JSONObject jData = country.getJSONObject(i);
                    list.add(jData.getString("NAME"));
                    countryId.put(jData.getString("NAME"), jData.getString("ID"));


                    System.out.println(list);
                    System.out.println("COOL" + countryId.toString());
                }

                // }
                countrySpinner.setAdapter(dataAdapter);

                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    //progressBar.startAnimation(animation);
                }
                //progressBar.setVisibility(View.GONE);
                if (getIntent().getExtras() != null) {
                    countryValue = getIntent().getExtras().getString("country");
                    if (spinnerCountUpdate == 0) {
                        if (!countryValue.equals(null)) {
                            int spinnerPosition = dataAdapter.getPosition(countryValue);
                            countrySpinner.setSelection(spinnerPosition);
                        }
                    }
                }





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






    public class GetStateCallBack extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {

                System.out.println(response);

                JSONObject jsonObjectData;
                jsonObjectData = new JSONObject(response);
                System.out.println(jsonObjectData.toString());
                if (jsonObjectData.getString("status").equals("0")) {

                } else {
String locationType=jsonObjectData.getString("location_type");
                    if(locationType.equals("1"))
                    {
                        stateLay.setVisibility(View.VISIBLE);
                        JSONArray state=jsonObjectData.getJSONArray("locations");


                        for(int i=0;i<state.length();i++)
                        {
                            JSONObject jData=state.getJSONObject(i);
                            listState.add(jData.getString("name"));
                            stateId.put(jData.getString("name"),jData.getString("id"));

                            System.out.println(listState);
                        }
                        stateSpinner.setAdapter(stateDataAdapter);
                    }
                    else
                    if(locationType.equals("2"))
                    {
                        stateLay.setVisibility(View.GONE);
                        JSONArray city=jsonObjectData.getJSONArray("locations");


                        for(int i=0;i<city.length();i++)
                        {
                            JSONObject jData=city.getJSONObject(i);
                            listCity.add(jData.getString("name"));


                            System.out.println(listCity);
                        }
                        citySpinner.setAdapter(cityDataAdapter);
                    }

                    if(getIntent().getExtras()!=null) {
                        stateValue = getIntent().getExtras().getString("state");
                        if (spinnerCountUpdate == 0) {
                            if (getIntent().getExtras() != null) {
                                if (!stateValue.equals(null) && listState.size() > 0) {
                                    int spinnerPosition = stateDataAdapter.getPosition(stateValue);
                                    stateSpinner.setSelection(spinnerPosition);
                                }
                            }
                        }
                    }




                    /*JSONArray country=jsonObjectData.getJSONArray("locations");


                    for(int i=0;i<country.length();i++)
                    {
                        JSONObject jData=country.getJSONObject(i);
                        list.add(jData.getString("name"));


                        System.out.println(list);
                    }*/

                }
                //stateSpinner.setAdapter(dataAdapter);

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



    public class GetCityCallBack extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {

                System.out.println(response);

                JSONObject jsonObjectData;
                jsonObjectData = new JSONObject(response);
                System.out.println(jsonObjectData.toString());
                if (jsonObjectData.getString("status").equals("0")) {

                } else {
                    if (jsonObjectData.getString("location_type").equals("")) {
cityLay.setVisibility(View.GONE);
                    } else {
                        cityLay.setVisibility(View.VISIBLE);
                        JSONArray state = jsonObjectData.getJSONArray("locations");


                        for (int i = 0; i < state.length(); i++) {
                            JSONObject jData = state.getJSONObject(i);
                            listCity.add(jData.getString("name"));


                            System.out.println(listState);
                        }
                        citySpinner.setAdapter(cityDataAdapter);
                        if( getIntent().getExtras() != null)
                        {


                            if(getIntent().getExtras()!=null) {
                                cityValue = getIntent().getExtras().getString("city");


                                if (spinnerCountUpdate == 0) {
                                    if (!cityValue.equals(null)) {
                                        int spinnerPosition = cityDataAdapter.getPosition(cityValue);
                                        citySpinner.setSelection(spinnerPosition);
                                    }
                                    spinnerCountUpdate++;
                                }
                            }

//                        if (spinnerCountUpdate == 0) {
//                            if (!countryValue.equals(null)) {
//                                int spinnerPosition = dataAdapter.getPosition(countryValue);
//                                countrySpinner.setSelection(spinnerPosition);
//                                spinnerCountUpdate++;
//                            }
//                        }

                        }

//                        if( getIntent().getExtras() != null)
//                        {
//                           /* int countrylist.indexOf(getIntent().getExtras().getString("country"));
//                            listState.indexOf(getIntent().getExtras().getString("state"));
//                            listCity.indexOf(getIntent().getExtras().getString("city"));*/
//
//
//                            countrySpinner.setPrompt(getIntent().getExtras().getString("country"));
//                            stateSpinner.setPrompt(getIntent().getExtras().getString("state"));
//                            citySpinner.setPrompt(getIntent().getExtras().getString("city"));
//
//
//                        }

                    /*JSONArray country=jsonObjectData.getJSONArray("locations");


                    for(int i=0;i<country.length();i++)
                    {
                        JSONObject jData=country.getJSONObject(i);
                        list.add(jData.getString("name"));


                        System.out.println(list);
                    }*/

                    }
                }
                //stateSpinner.setAdapter(dataAdapter);

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
           // progressLayout.setVisibility(View.VISIBLE);
        }

    }


    public class AddNewAddressCallBack extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject jsonObjectData;
                jsonObjectData = new JSONObject(response);


                if(jsonObjectData.getJSONObject("STATUS").getInt("CODE")==401)
                {
                    String userId = mSharedPrefs.getString(Constants.USER_ID_PREFS, null);
                    new APIClient(AddNewAddress.this, AddNewAddress.this, new logoutUserCallback()).userLogoutAPICall(userId);
                }

                else if(jsonObjectData.getJSONObject("STATUS").getInt("CODE")==400)
                {
                    for(int i=0;i<jsonObjectData.getJSONObject("OUTPUT").getJSONArray("ERRORS").length();i++)
                    {
                        JSONObject verificationCheck=jsonObjectData.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(i);

                        if (verificationCheck.getString("FIELD").equals("65")) {
                            name.setError(verificationCheck.getString("MESSAGE"));
                        }
                        if (verificationCheck.getString("FIELD").equals("67")) {
                            add1.setError(verificationCheck.getString("MESSAGE"));
                        }
                        if (verificationCheck.getString("FIELD").equals("70")) {
                            phone1.setError(verificationCheck.getString("MESSAGE"));
                        }
                        if (verificationCheck.getString("FIELD").equals("78")) {
                            email.setError(verificationCheck.getString("MESSAGE"));
                        }
                        if (verificationCheck.getString("FIELD").equals("EMAIL")) {
                            Snackbar.make(findViewById(android.R.id.content), verificationCheck.getString("MESSAGE"), Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }

                    }


                }

                else if(jsonObjectData.getJSONObject("STATUS").getInt("CODE")==200) {
                    System.out.println(response);


                    if (jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").has("MESSAGE")) {
                        Snackbar.make(findViewById(android.R.id.content), jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getString("MESSAGE").toString(), Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.RED)
                                .show();
                        finish();
                    }
             /*   else{
                    Snackbar.make(findViewById(android.R.id.content), jsonObjectData.getString("title"), Snackbar.LENGTH_INDEFINITE)
                            .setActionTextColor(Color.RED)
                            .show();
                }*/
                    System.out.println(jsonObjectData.toString());


                    if (getApplicationContext() != null) {
                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                        //progressBar.startAnimation(animation);
                    }
                    //progressBar.setVisibility(View.GONE);
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.putString(Constants.USER_ADDRESS_ID, jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getString("ADDED_ADDRESS_ID"));
                    System.out.println("ADDED " + jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getString("ADDED_ADDRESS_ID"));
                    editor.commit();

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
                //  progressBar.setVisibility(View.GONE);

            }
        }

        @Override
        public void onTaskCancelled() {
            progressLayout.setVisibility(View.GONE);
        }

        @Override
        public void onPreExecute() {
            progressLayout.setVisibility(View.VISIBLE);
        }

    }





    private class MyTextWatcher implements TextWatcher {
                private View view;

              private MyTextWatcher(View view) {
            this.view = view;
            }

                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

               public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

                public void afterTextChanged(Editable editable) {
           switch (view.getId()) {
                case R.id.name:
                    validateName();
                    break;
                case R.id.address1:
                    validateAddress1();
                    break;
                case R.id.address2:
                    validateAddress2();
                    break;
               case R.id.zipPostal:
                  // validPin();
                   isValidEmail(email.getText());
                   break;
               case R.id.phone:
                   // validPin();
                   isValidPhone(phone1.getText());
                   break;
               case R.id.phone2:
                   // validPin();
                   if(phone2.getText().toString().trim().isEmpty())
                   {

                   }
                   else {
                       isValidPhone2(phone2.getText());
                   }
                   break;
               }
           }
       }

    private boolean validateName() {
       if (name.getText().toString().trim().isEmpty()) {
           inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(name);
            return false;
           } else {
            inputLayoutName.setErrorEnabled(false);


            }

       return true;
        }
    public boolean validPin() {


//        if (pin.getText().toString().trim().isEmpty()) {
//
//
//            pinLayout.setError(getString(R.string.err_msg_phone));
//            requestFocus(pin);
//            return false;
//
//        }
//        else
//        {
//            Pattern pattern = Pattern.compile("\\d");
//            Matcher matcher = pattern.matcher(pin.getText().toString());
//
//            if (matcher.matches()) {
//
//            } else {
//                pinLayout.setError(getString(R.string.err_msg_phone));
//            }
//        }





        return true;



       }
    public boolean isValidEmail(CharSequence target) {
        //return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        if (TextUtils.isEmpty(target)) {
            email.setError("Please Enter Email");
            requestFocus(email);
            return false;
        }
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches())
        {
            return true;
        }
        else {
            //return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
            email.setError("Please Enter Valid Email Address");
        }
        return true;
    }

    private boolean validateAddress1() {
        if (add1.getText().toString().trim().isEmpty()) {
            inputAddress1.setError(getString(R.string.err_msg_add));
            requestFocus(add1);
            return false;
        } else {
            inputAddress1.setErrorEnabled(false);

        }

        return true;
    }


private boolean isValidPhone(CharSequence phoneNo)
{
    if (TextUtils.isEmpty(phoneNo)) {
        phone1.setError("Please Enter Phone Number");
        requestFocus(phone1);
        return false;
    }
    if(android.util.Patterns.PHONE.matcher(phoneNo).matches())
    {
        return true;
    }
    else {
        //return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        phone1.setError("Please Enter Valid Phone Number");
}
    return true;
}


    private boolean isValidPhone2(CharSequence phoneNo)
    {
        if (TextUtils.isEmpty(phoneNo)) {
            phone2.setError("Please Enter Phone Number");
            requestFocus(phone2);
            return false;
        }
        if(android.util.Patterns.PHONE.matcher(phoneNo).matches())
        {
            return true;
        }
        else {
            //return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
            phone2.setError("Please Enter Valid Phone Number");
        }
        return true;
    }


    private boolean validateAddress2() {
        if (add2.getText().toString().trim().isEmpty()) {
            inputAddress2.setError(getString(R.string.err_msg_add));
            requestFocus(add2);
            return false;
        } else {
            inputAddress2.setErrorEnabled(false);

        }

        return true;
    }




    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
                    Intent i = new Intent(AddNewAddress.this, SplashActivity.class);
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
