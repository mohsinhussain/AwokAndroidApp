package com.awok.moshin.awok.Activities;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.ShippingAddressModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;

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
    ArrayAdapter<String> dataAdapter;
    ArrayAdapter<String> stateDataAdapter;
    ArrayAdapter<String> cityDataAdapter;
    private LinearLayout stateLay;
    private LinearLayout cityLay;
    private Button clear,save;
    private TextInputLayout inputLayoutName, inputAddress1, inputAddress2,pinLayout;
    int check=0;
    int stateCheck=0;
    private EditText name,add1,add2,pin;
    private Spinner countrySpinner,stateSpinner,citySpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);


        countrySpinner=(Spinner)findViewById(R.id.country);
        stateSpinner=(Spinner)findViewById(R.id.state);
        citySpinner=(Spinner)findViewById(R.id.city);
        name=(EditText)findViewById(R.id.name);
        add1=(EditText)findViewById(R.id.address1);
                add2=(EditText)findViewById(R.id.address2);
                pin=(EditText)findViewById(R.id.zipPostal);

        name.addTextChangedListener(new MyTextWatcher(name));
        add1.addTextChangedListener(new MyTextWatcher(add1));
        add2.addTextChangedListener(new MyTextWatcher(add2));
        pin.addTextChangedListener(new MyTextWatcher(pin));


        stateLay=(LinearLayout)findViewById(R.id.stateLay);
        cityLay=(LinearLayout)findViewById(R.id.cityLay);
        inputLayoutName = (TextInputLayout) findViewById(R.id.nameLayout);
        inputAddress1 = (TextInputLayout) findViewById(R.id.addressLay);
        inputAddress2 = (TextInputLayout) findViewById(R.id.address2Lay);
        pinLayout = (TextInputLayout) findViewById(R.id.zipLay);
clear=(Button)findViewById(R.id.clear);
        save=(Button)findViewById(R.id.save);







        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateName()&&!validateAddress1()&&!validateAddress2()&&!validPin()) {
                    HashMap<String,Object> addNewAddress=new HashMap<String, Object>();



                    addNewAddress.put("name", name.getText().toString());

                    //addToCartData.put("user_id", "55f6a9e52f17f64a9b5f5ce5");
                    addNewAddress.put("address_line1", add1.getText().toString());
                    addNewAddress.put("address_line2",add2.getText().toString());
                    addNewAddress.put("city",citySpinner.getSelectedItem().toString());
                    addNewAddress.put("state",stateSpinner.getSelectedItem().toString());
                    addNewAddress.put("country",countrySpinner.getSelectedItem().toString());
                    addNewAddress.put("postal_code",pin.getText().toString());
                    addNewAddress.put("phone_number","473538547fgfdg64");

                    JSONObject dataToSend=new JSONObject(addNewAddress);


                    ConnectivityManager connMgr = (ConnectivityManager)
                            getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        new APIClient(AddNewAddress.this, getApplicationContext(), new AddNewAddressCallBack()).addAddressCallBack("55f6a9e52f17f64a9b5f5ce5",dataToSend.toString());


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
                //countrySpinner.requestFocus();
                //countrySpinner.setFocusable(true);
                //countrySpinner.setFocusableInTouchMode(true);
                check = check + 1;
                if (check > 1) {
                    listCity.clear();
                    citySpinner.setAdapter(cityDataAdapter);
                    String country_text = countrySpinner.getSelectedItem().toString();
                    System.out.println("jhgvjhvjngv" + countrySpinner.getSelectedItem().toString());
                    ConnectivityManager connMgr = (ConnectivityManager)
                            getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    String valueCountry = countryId.get(country_text);
                    System.out.println("jhgvjhvjngv" + valueCountry);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        new APIClient(AddNewAddress.this, getApplicationContext(), new GetStateCallBack()).StateCallBack(valueCountry);


                    } else {

                        Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .show();

                    }
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
                        if(stateCheck>1)
                        {
                            listCity.clear();
                            citySpinner.setAdapter(cityDataAdapter);
                            String state_text=stateSpinner.getSelectedItem().toString();
                            System.out.println("jhgvjhvjngv" + stateSpinner.getSelectedItem().toString());
                            ConnectivityManager connMgr = (ConnectivityManager)
                                    getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                            String valueState = stateId.get(state_text);
                            System.out.println("jhgvjhvjngv" + valueState);
                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                            if (networkInfo != null && networkInfo.isConnected()) {

                                new APIClient(AddNewAddress.this, getApplicationContext(), new GetCityCallBack()).CityCallBack(valueState);
                               // System.out.println("jhgvjhvjngv" + valueState);


                            } else {

                                Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                                        .setActionTextColor(Color.RED)
                                        .show();

                            }
                        }





            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_address, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
                System.out.println(jsonObjectData.toString());
                if (jsonObjectData.getString("status").equals("0")) {

                } else {


                    JSONArray country=jsonObjectData.getJSONArray("locations");


                    for(int i=0;i<country.length();i++)
                    {
                        JSONObject jData=country.getJSONObject(i);
                        list.add(jData.getString("name"));
                        countryId.put(jData.getString("name"),jData.getString("id"));


System.out.println(list);
                    }

                }
                countrySpinner.setAdapter(dataAdapter);

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

        }

    }


    public class AddNewAddressCallBack extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {

                System.out.println(response);

                JSONObject jsonObjectData;
                jsonObjectData = new JSONObject(response);
                System.out.println(jsonObjectData.toString());



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
                   validPin();
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


        if (pin.getText().toString().trim().isEmpty()) {


            pinLayout.setError(getString(R.string.err_msg_phone));
            requestFocus(pin);
            return false;

        }
        else
        {
            Pattern pattern = Pattern.compile("\\d");
            Matcher matcher = pattern.matcher(pin.getText().toString());

            if (matcher.matches()) {

            } else {
                pinLayout.setError(getString(R.string.err_msg_phone));
            }
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
}
