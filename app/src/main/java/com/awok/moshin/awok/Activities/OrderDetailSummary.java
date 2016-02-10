package com.awok.moshin.awok.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.awok.moshin.awok.R;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderDetailSummary extends AppCompatActivity {
    ActionBar ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.back_button);
        ab.setTitle("Order Summary");
        TextView order_no=(TextView)findViewById(R.id.order_no);
        TextView responseData=(TextView)findViewById(R.id.responseData);
        TextView orderDate=(TextView)findViewById(R.id.orderDate);
        TextView orderStatus=(TextView)findViewById(R.id.orderStatus);
        TextView payType=(TextView)findViewById(R.id.payType);
        TextView orderAmount=(TextView)findViewById(R.id.orderAmount);






        TextView name=(TextView)findViewById(R.id.name);
        TextView phone=(TextView)findViewById(R.id.phone);
        TextView email=(TextView)findViewById(R.id.email);
        TextView emirateCity=(TextView)findViewById(R.id.emirateCity);
        TextView street=(TextView)findViewById(R.id.street);






        try {
            JSONObject data=new JSONObject(getIntent().getStringExtra("data"));
JSONObject dataPopulate=data.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("ORDER_DATA");
order_no.setText(dataPopulate.getString("ORDER_NUMBER"));
            responseData.setText(dataPopulate.getString("DESCRIPTION"));
            orderDate.setText(dataPopulate.getString("ORDER_DATE"));
            orderStatus.setText(dataPopulate.getString("STATUS"));
            payType.setText(dataPopulate.getString("PAYMENT_SYSTEM"));
            orderAmount.setText("AED "+dataPopulate.getString("PRICE"));
            name.setText(dataPopulate.getJSONObject("USER_INFO").getString("NAME"));
            phone.setText(dataPopulate.getJSONObject("USER_INFO").getString("PHONE"));
            email.setText(dataPopulate.getJSONObject("USER_INFO").getString("EMAIL"));
            emirateCity.setText(dataPopulate.getJSONObject("USER_INFO").getString("CITY")+ " ,"+dataPopulate.getJSONObject("USER_INFO").getString("REGION"));
            street.setText(dataPopulate.getJSONObject("USER_INFO").getString("STREET"));




        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_detail_summary, menu);
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
}
