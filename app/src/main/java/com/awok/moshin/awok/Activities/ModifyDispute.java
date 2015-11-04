package com.awok.moshin.awok.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModifyDispute extends AppCompatActivity {

    private Spinner productCondition;
    private Spinner disputeRequest;
    private TextView nameProduct;
    private JSONObject dataToSend;
    private Button disputeOpen;
    private RadioGroup returnRadio,radioButton;
    private RadioButton radioValueButton,radioValueReturnButton;
    private EditText disputeText;
    private LinearLayout disputeShow;
    private String spinnerData,spinnerDataDispute,radioTwo,radioOne,valueProductIdSpinner,sendTxt,disId;
    int selectedId,selectedIdReturn;
    private LinearLayout progressLayout;
    List<String> nameProductList = new ArrayList<String>();

    HashMap<String,String> productId=new HashMap<>();

    private TextView priceTxtMain,shippingStatus,orderId;
    String prod_id,cartId,uniqueId,price,status,send_status,send_productName,send_price,send_currency,goods_condition,dispute_requestData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_dispute);
        orderId=(TextView)findViewById(R.id.order_id);
        shippingStatus=(TextView)findViewById(R.id.shippingStatus);
        priceTxtMain=(TextView)findViewById(R.id.priceTxt);
        Intent i=getIntent();
        disId=i.getStringExtra("dispute_id");
        prod_id=i.getStringExtra("prod_id");
        cartId=i.getStringExtra("cart_id");
        price=i.getStringExtra("price");
        status=i.getStringExtra("status");
        send_status=i.getStringExtra("send_status");
        send_productName=i.getStringExtra("send_productName");
        send_price=i.getStringExtra("send_price");
        send_currency=i.getStringExtra("send_currency");
        sendTxt=i.getStringExtra("additional_details");
        goods_condition=i.getStringExtra("goods_condition");






        dispute_requestData=i.getStringExtra("dispute_request");






        System.out.println("prod_id"+prod_id);
        System.out.println("cartId"+cartId);
        System.out.println("price"+price);
        System.out.println("status" + status);
        System.out.println("send_status" + send_status);
        System.out.println("send_productName" + send_productName);
        System.out.println("send_price" + send_price);
        System.out.println("send_currency" + send_currency);


      /*  productId=(HashMap<String, String>) i.getSerializableExtra("uniqueId");
        for (Map.Entry<String,String> entry : productId.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            nameProductList.add(key);
            System.out.println("After Sending: "+key+ " "+value);
            // do stuff
        }*/

        orderId.setText(prod_id);
        shippingStatus.setText(send_status);
        priceTxtMain.setText(send_currency+" "+send_price);

        productCondition=(Spinner)findViewById(R.id.productCondition);
        disputeRequest=(Spinner)findViewById(R.id.disputeRequest);
        nameProduct=(TextView)findViewById(R.id.nameProduct);
        disputeOpen=(Button)findViewById(R.id.disputeOpen);
        disputeText=(EditText)findViewById(R.id.disputeText);
        disputeShow=(LinearLayout)findViewById(R.id.disputeShow);
        progressLayout=(LinearLayout)findViewById(R.id.progressLayout);
        progressLayout.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        nameProduct.setText(send_productName);
        disputeText.setText(sendTxt);

        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.back_button);
        ab.setTitle("Open Dispute");
        List<String> list = new ArrayList<String>();
        list.add("Good");
        list.add("Damaged");
        list.add("Dead");

        List<String> listDispute = new ArrayList<String>();
        listDispute.add("Refund");
        listDispute.add("Replace");
        listDispute.add("Return");




      /*  ArrayAdapter<String> productAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,nameProductList);

        productAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);





        nameProduct.setAdapter(productAdapter);*/


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> dataAdapterDispute = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,listDispute);

        dataAdapterDispute.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        productCondition.setAdapter(dataAdapter);
        disputeRequest.setAdapter(dataAdapterDispute);



        productCondition.setSelection(dataAdapter.getPosition(goods_condition));
        disputeRequest.setSelection(dataAdapterDispute.getPosition(dispute_requestData));

        radioButton = (RadioGroup) findViewById(R.id.radioSelect);

        // get selected radio button from radioGroup
        selectedId = radioButton.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioValueButton = (RadioButton) findViewById(selectedId);
        radioButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
// get selected radio button from radioGroup
                selectedId = radioButton.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioValueButton = (RadioButton) findViewById(selectedId);

                Toast.makeText(ModifyDispute.this,
                        radioValueButton.getText(), Toast.LENGTH_SHORT).show();

                if (radioValueButton.getText().equals("No")) {
                    disputeShow.setVisibility(View.GONE);
                    spinnerData = "";
                    spinnerDataDispute = "";
                    radioTwo = "";

                } else {


                    disputeShow.setVisibility(View.VISIBLE);

                }
            }


        });


        returnRadio = (RadioGroup) findViewById(R.id.returnRadio);
        // get selected radio button from radioGroup
        selectedIdReturn = returnRadio.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioValueReturnButton = (RadioButton) findViewById(selectedIdReturn);
        returnRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
// get selected radio button from radioGroup
                selectedIdReturn = returnRadio.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioValueReturnButton = (RadioButton) findViewById(selectedIdReturn);

                Toast.makeText(ModifyDispute.this,
                        radioValueReturnButton.getText(), Toast.LENGTH_SHORT).show();
            }


        });















        disputeOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(disputeShow.getVisibility()==View.VISIBLE)
                {
                    spinnerData = productCondition.getSelectedItem().toString();
                    spinnerDataDispute = disputeRequest.getSelectedItem().toString();
                    System.out.println(spinnerData);
                    System.out.println(spinnerDataDispute);
                    radioOne=radioValueButton.getText().toString();
                    radioTwo=radioValueReturnButton.getText().toString();
                    System.out.println(radioOne);
                    System.out.println(radioTwo);
                    System.out.println(disputeText);
                  //  valueProductIdSpinner = (String) productId.get(nameProduct.getSelectedItem());
                    valueProductIdSpinner=prod_id;
                    System.out.println(valueProductIdSpinner);

                }
                else
                {
                    spinnerData = productCondition.getSelectedItem().toString();
                    spinnerDataDispute = "";
                    radioOne=radioValueButton.getText().toString();
                    radioTwo="";
                    valueProductIdSpinner=prod_id;
                    System.out.println(disputeText);
                 //   valueProductIdSpinner = (String) productId.get(nameProduct.getSelectedItem());
                    System.out.println(valueProductIdSpinner);
                }



                HashMap<String,Object> addToCartData=new HashMap<String, Object>();



                addToCartData.put("is_received", radioOne);

                //addToCartData.put("user_id", "55f6a9e52f17f64a9b5f5ce5");
                addToCartData.put("goods_condition", spinnerData);
                addToCartData.put("ship_goods_back", radioTwo);
                addToCartData.put("dispute_request", spinnerDataDispute);
                addToCartData.put("additional_details", disputeText.getText());

                dataToSend=new JSONObject(addToCartData);
                System.out.println(dataToSend.toString());

                ConnectivityManager connMgr = (ConnectivityManager)
                        getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    new APIClient(ModifyDispute.this, getApplicationContext(), new GetDisputeModify()).disputeModifyCallBack(disId, dataToSend.toString());


                } else {




                    Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED);

                    View snackbarView = snackbar.getView();

                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();



                }
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dispute, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                //mDrawerLayout.openDrawer(GravityCompat.START);
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }









    public class GetDisputeModify extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {


                System.out.println(response);

                JSONObject jsonObjectData;
                jsonObjectData = new JSONObject(response);
                System.out.println(jsonObjectData.toString());


                JSONArray jsonArray;



                if(jsonObjectData.getString("errors").equals("true"))
                {

                }
                else {
                    /*Intent i=new Intent(ModifyDispute.this,DisputeDetails.class);
                    i.putExtra("cart_id",valueProductIdSpinner);
                    startActivity(i);*/

                }

                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);

                    progressLayout.startAnimation(animation);
                }
                progressLayout.setVisibility(View.GONE);


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


}