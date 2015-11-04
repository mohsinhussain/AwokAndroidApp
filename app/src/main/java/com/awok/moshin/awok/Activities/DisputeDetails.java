package com.awok.moshin.awok.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisputeDetails extends AppCompatActivity {
String cart_id;
    private TextView disputeNo,disputeStatus,rem;
    private LinearLayout progressLayout,main;
    private String is_received,goods_condition,ship_goods_back,dispute_request,disputeId;
    String additional_details,user_id,send_status,send_productName,send_price,send_currency,prod_id;
    private Button modify,cancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispute_details);

        cancel=(Button)findViewById(R.id.cancel_dispute);
disputeNo=(TextView)findViewById(R.id.disputeNo);
        disputeStatus=(TextView)findViewById(R.id.disputeStatus);
        rem=(TextView)findViewById(R.id.rem);
main=(LinearLayout)findViewById(R.id.main);
        main.setVisibility(View.GONE);
modify=(Button)findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DisputeDetails.this,ModifyDispute.class);
                i.putExtra("user_id",user_id);
                i.putExtra("dispute_id",disputeId);
                i.putExtra("is_received",is_received);
                i.putExtra("goods_condition",goods_condition);
                i.putExtra("ship_goods_back",ship_goods_back);
                i.putExtra("dispute_request",dispute_request);
                i.putExtra("additional_details",additional_details);
                i.putExtra("send_status",send_status);
                i.putExtra("send_productName",send_productName);
                i.putExtra("send_currency",send_currency);
                i.putExtra("send_price",send_price);
                i.putExtra("prod_id", prod_id);
                i.putExtra("cart_id",cart_id);








                startActivity(i);
            }
        });
        progressLayout=(LinearLayout)findViewById(R.id.progressLayout);
        progressLayout.setVisibility(View.GONE);

cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            new APIClient(DisputeDetails.this, getApplicationContext(), new GetDisputeCancel()).disputeCancelCallBack(disputeId);


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
        Intent i=getIntent();
        cart_id=i.getStringExtra("cart_id");
        prod_id=i.getStringExtra("prod_id");
        System.out.println("pid" + prod_id);



        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            new APIClient(DisputeDetails.this, getApplicationContext(), new GetDisputeDetails()).disputeDetailsCallBack(cart_id);


        } else {




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
        getMenuInflater().inflate(R.menu.menu_dispute_details, menu);
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

    public class GetDisputeCancel extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {


                System.out.println(response);

                JSONObject jsonObjectData;
                jsonObjectData = new JSONObject(response);
                System.out.println(jsonObjectData.toString());


               /* JSONArray jsonArray;

                disputeId=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("id");
                String disputeStatusTxt=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("dispute_status");
                String reminder=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("note");



                is_received=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("is_received");
                goods_condition=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("goods_condition");
                ship_goods_back=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("ship_goods_back");
                dispute_request=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("dispute_request");
                additional_details=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("additional_details");
                user_id=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("user_id");

                //=additional_details
                send_status=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("dispute_status");
                send_productName=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getJSONObject("product").getString("product_name");
                send_currency=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getJSONObject("product").getString("currency");
                send_price=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getJSONObject("product").getString("product_price");


                disputeNo.setText(disputeId);
                disputeStatus.setText(disputeStatusTxt);
                rem.setText(reminder);*/

                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);

                    progressLayout.startAnimation(animation);
                }
                progressLayout.setVisibility(View.GONE);
                main.setVisibility(View.VISIBLE);

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

    public class GetDisputeDetails extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {


                System.out.println(response);

                JSONObject jsonObjectData;
                jsonObjectData = new JSONObject(response);
                System.out.println(jsonObjectData.toString());


                JSONArray jsonArray;

                disputeId=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("id");
                String disputeStatusTxt=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("dispute_status");
                String reminder=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("note");



                is_received=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("is_received");
                goods_condition=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("goods_condition");
                ship_goods_back=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("ship_goods_back");
                dispute_request=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("dispute_request");
                additional_details=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("additional_details");
                user_id=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("user_id");

                //=additional_details
                send_status=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("dispute_status");
                send_productName=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getJSONObject("product").getString("product_name");
                send_currency=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getJSONObject("product").getString("currency");
                send_price=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getJSONObject("product").getString("product_price");


                disputeNo.setText(disputeId);
                disputeStatus.setText(disputeStatusTxt);
                rem.setText(reminder);

                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);

                    progressLayout.startAnimation(animation);
                }
                progressLayout.setVisibility(View.GONE);
                main.setVisibility(View.VISIBLE);

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








