package com.awok.moshin.awok.Activities;

import android.annotation.TargetApi;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.awok.moshin.awok.Adapters.PaymentPickAdapter;
import com.awok.moshin.awok.Adapters.ProductSpecificationAdapter;
import com.awok.moshin.awok.Models.Payment_Pick;
import com.awok.moshin.awok.Models.ProductSpecification;
import com.awok.moshin.awok.Models.Profile_CountryModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Pick_Payment_ACtivity extends AppCompatActivity {
ListView paymentList;
    String orderId="";
    ActionBar ab;
    private Button nxt;
    Dialog payDialog;
    private String sendValue="";
    private  long startTime=120000;
    String userId="";
    CountDownTimer timerLoop;
    Dialog successDialog;
    JSONObject jsonObjectData;
    Dialog sendDialog;
    String payId="";
    Dialog verifyDialog;
    private ProgressBar progressBar;
    SharedPreferences mSharedPrefs;
    PaymentPickAdapter payAdapter;
    JSONObject jsonObj;

    String code="";
    private String verified="";
    private String phoneNumber="";
    private String before="";
    private String after="";
    ArrayList<Payment_Pick> payModel=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick__payment__activity);
        nxt=(Button)findViewById(R.id.checkout);
        progressBar=(ProgressBar)findViewById(R.id.dia_progress);
orderId=getIntent().getStringExtra("orderId");
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        if ((mSharedPrefs.contains(Constants.USER_ID_PREFS)))
        {
            userId = mSharedPrefs.getString(Constants.USER_ID_PREFS, null);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.back_button);
        ab.setTitle("Payment Method");

        try {
            jsonObj=new JSONObject(getIntent().getStringExtra("dataPay"));
            System.out.println("DATA" + jsonObj);

int length=jsonObj.getJSONArray("METHODS").length();
            JSONArray jArray=jsonObj.getJSONArray("METHODS");
            for(int i=0;i<length;i++) {
                Payment_Pick payData = new Payment_Pick();
JSONObject jO=jArray.getJSONObject(i);
                payData.setName(jO.getString("NAME"));
                payData.setId(jO.getString("ID"));
                if(jO.has("CHECKED"))
                {
                    payData.setSelected(true);
                }


                payModel.add(payData);

            }
verified=getIntent().getStringExtra("verified");
            phoneNumber=getIntent().getStringExtra("phone");
            before=getIntent().getStringExtra("before");
            after=getIntent().getStringExtra("after");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        paymentList=(ListView)findViewById(R.id.paymentList);
        paymentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView name = (TextView) view.findViewById(R.id.title);
                //ImageView i=(ImageView)view.findViewById(R.id.ee);
                // selected item

               /* c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {*/
                Payment_Pick m = payModel.get(position);
                final ImageView c = (ImageView) view.findViewById(R.id.im);


               /* final ImageView c = (ImageView) view.findViewById(R.id.im);
                if(c.getVisibility()==View.VISIBLE)
                {
                    list.put(position,name.getTag().toString());
                }
                else
                {
                    list.remove(position);
                    }
                */

            /*    if (c.getVisibility() == View.VISIBLE) {
                    interestLangData.get(position).setSelected(false);
                    c.setVisibility(View.GONE);
                    listLangSel.remove(position);
                    System.out.println("dn" + listLangSel);

                } else {
                    c.setVisibility(View.VISIBLE);
                    interestLangData.get(position).setSelected(true);

                    listLangSel.put(position, name.getText().toString());
                    System.out.println("dn" + listLangSel);
                }*/


                for (int y = 0; y < payModel.size(); y++) {
                    payModel.get(y).setSelected(false);
                }


                payModel.get(position).setSelected(true);
                System.out.println(name.getText().toString());
                payId = name.getTag().toString();
                payAdapter.notifyDataSetChanged();
            }
        });


nxt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            new APIClient(Pick_Payment_ACtivity.this, getApplicationContext(), new GetCheckOutCallBack()).PayCallBackCallBack(orderId, userId, payId);
            //new APIClient(this, getApplicationContext(),  new GetAddressCallback()).getPrimaryAddressAPICall("55f6a9e52f17f64a9b5f5ce5");
        } else {

            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }
    }
});

        payAdapter=new PaymentPickAdapter(Pick_Payment_ACtivity.this, payModel, getApplicationContext());

        paymentList.setAdapter(payAdapter);
        payId=payModel.get(0).getId();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pick__payment__activity, menu);
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



    public class GetCheckOutCallBack extends AsyncCallback {
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public void onTaskComplete(String response) {
            try {
                System.out.println("RESPONSE"+response);

                jsonObjectData=new JSONObject(response);
                //      System.out.println("RESPONSE"+jsonObjectData.getString("status"));

                if(jsonObjectData.getJSONObject("STATUS").getInt("CODE")==200) {
if(jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PAYMENT_DATA").length()>0)
{
    String url=jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PAYMENT_DATA").getString("URL");
    clickPay(url);

}
                    else {
    Snackbar.make(findViewById(android.R.id.content), "Order Placed Successfully", Snackbar.LENGTH_LONG)
            .setActionTextColor(Color.RED)
            .show();
   /* Intent i = new Intent(Pick_Payment_ACtivity.this, OrderDetailSummary.class);
    i.putExtra("data", jsonObjectData.toString());
    startActivity(i);*/
    clickSuccess("SUCCESS","Payment Updated");
}

                    if (getApplicationContext() != null) {
                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
      /////////////                  progressLayout.startAnimation(animation);
                    }
       /////////////////////             progressLayout.setVisibility(View.GONE);
                    //initializeData();
                }

            } catch (JSONException e) {
                e.printStackTrace();
               /* Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();*/
                Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Data could not be loaded", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED);

                View snackbarView = snackbar.getView();

                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
                if(getApplicationContext()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
           ////////////////////         progressLayout.startAnimation(animation);
                }
         //////////////////       progressLayout.setVisibility(View.GONE);
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
         /////////////////////////////////   progressLayout.setVisibility(View.VISIBLE);
//            }

        }
    }





    private void clickPay(String   url) {

        payDialog = new Dialog(Pick_Payment_ACtivity.this);
        payDialog.setCancelable(true);
        payDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        payDialog.setContentView(R.layout.payment_web_layout);
        final WebView web=(WebView)payDialog.findViewById(R.id.webview);

        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(url);


        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

web.addJavascriptInterface(new WebAppInterface(Pick_Payment_ACtivity.this), "Android");






        payDialog.show();
        payDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast,String message) {
            /*Snackbar.make(findViewById(android.R.id.content), toast+" "+message, Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();*/



payDialog.dismiss();
                clickSuccess(toast,message);



        }
    }




    private void clickSuccess(final String status,String message) {

        successDialog = new Dialog(Pick_Payment_ACtivity.this);
        successDialog.setCancelable(true);
        successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        successDialog.setContentView(R.layout.dialog_success_payment_popup);
        final TextView headerTextView=(TextView)successDialog.findViewById(R.id.headerTextView);
        final TextView messageTxt=(TextView)successDialog.findViewById(R.id.message);
         Button ok=(Button)successDialog.findViewById(R.id.ok);






        ok=(Button)successDialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status.equals("SUCCESS"))
                {
if(verified.equals("N"))
{
//verifyDialog();
    enterPhone();
}
                    else
{
    Intent i = new Intent(Pick_Payment_ACtivity.this, OrderDetailSummary.class);
    i.putExtra("data", jsonObjectData.toString());
    startActivity(i);
}
                }
                else
                {
                    successDialog.dismiss();
                }
            }
        });




headerTextView.setText(status);
        messageTxt.setText(message);



        successDialog.show();
        successDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void enterPhone() {
        sendDialog = new Dialog(this);
        ///  verifyDialog.setCancelable(true);
        sendDialog.setCanceledOnTouchOutside(false);
    //    sendDialog.setTitle(before);
        sendDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sendDialog.setContentView(R.layout.payment_send_phone_number);
        final EditText numberPhone=(EditText)sendDialog.findViewById(R.id.numberPhone);
        final TextView headerTextView=(TextView)sendDialog.findViewById(R.id.headerTextView);
        Button buttonSend=(Button)sendDialog.findViewById(R.id.changePhone);


        headerTextView.setText(before);

if(phoneNumber.equals(""))
{

}
        else
{
    numberPhone.setText(phoneNumber);
}



        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberPhone.getText().equals(""))
                {
                    numberPhone.setError("Enter Phone Number");
                }
                else
                {

                    HashMap<String,String > resendData=new HashMap<String ,String >();
                    //   resendData.put("user_id",userId);
                    resendData.put("phone", numberPhone.getText().toString());
                    //  resendData.put("mode","send-verify-sms");

                    JSONObject dataResend=new JSONObject(resendData);
                    new APIClient(Pick_Payment_ACtivity.this, Pick_Payment_ACtivity.this, new changePhone()).paymentReSendCallBack(dataResend.toString());
                }
            }
        });


        sendDialog.show();
        sendDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }


    private void verifyDialog() {
        verifyDialog = new Dialog(this);
        ///  verifyDialog.setCancelable(true);
       // verifyDialog.setTitle(after);
        verifyDialog.setCanceledOnTouchOutside(false);
        verifyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        verifyDialog.setContentView(R.layout.dialog_phone_verification);

        final TextView headerTextView=(TextView)verifyDialog.findViewById(R.id.headerTextView);
      //  headerTextView.setVisibility(View.GONE);
        final Button resend = (Button) verifyDialog.findViewById(R.id.resend);
        final Button nextButton = (Button) verifyDialog.findViewById(R.id.verify);
        final TextView timer=(TextView)verifyDialog.findViewById(R.id.timer);
      //  final TextView headerTextView=(TextView)verifyDialog.findViewById(R.id.headerTextView);
        //progressBar=(ProgressBar)verifyDialog.findViewById(R.id.dia_progress);



headerTextView.setText(after);
        Button changePhone=(Button)verifyDialog.findViewById(R.id.changePhone);
        changePhone.setVisibility(View.GONE);


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
                Intent i = new Intent(Pick_Payment_ACtivity.this, OrderDetailSummary.class);
                i.putExtra("data", jsonObjectData.toString());
                startActivity(i);
                finish();
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
                    } else {
                        code = verifyCode.getText().toString();
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
                        sendValue = "verify";
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
            new APIClient(Pick_Payment_ACtivity.this, Pick_Payment_ACtivity.this, new verifyCode()).paymentVerifySms(code,orderId);
        }
        else if(value.equals("changeNumber"))
        {



            HashMap<String,String > resendData=new HashMap<String ,String >();
            //   resendData.put("user_id",userId);
            resendData.put("phone", phoneNumber);
            //  resendData.put("mode","send-verify-sms");

            JSONObject dataResend=new JSONObject(resendData);

            new APIClient(Pick_Payment_ACtivity.this, Pick_Payment_ACtivity.this, new changePhone()).paymentReSendCallBack(dataResend.toString());
        }
        else if(value.equals("resend"))
        {
            HashMap<String,String > resendSmsData=new HashMap<String ,String >();
            //      resendSmsData.put("user_id",userId);

            // resendSmsData.put("mode","resend-verify-sms");

            JSONObject dataSmsResend=new JSONObject(resendSmsData);

            new APIClient(Pick_Payment_ACtivity.this, Pick_Payment_ACtivity.this, new reSendSms()).paymentReSendSms(dataSmsResend.toString());
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
                    Intent i = new Intent(Pick_Payment_ACtivity.this, OrderDetailSummary.class);
                    i.putExtra("data", jsonObjectData.toString());
                    startActivity(i);
                    finish();
                }
                else if (obj.getJSONObject("STATUS").getInt("CODE")==201) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("STATUS").getString("MESSAGE").toString(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                    verifyDialog.dismiss();
                    Intent i = new Intent(Pick_Payment_ACtivity.this, OrderDetailSummary.class);
                    i.putExtra("data", jsonObjectData.toString());
                    startActivity(i);
                    finish();
                }
                else if(obj.getJSONObject("STATUS").getInt("CODE")==400)
                {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE").toString(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                else{
                    Snackbar.make(findViewById(android.R.id.content), "Server Error Occured", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
             ///   progressBar.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
            //    progressBar.setVisibility(View.GONE);
            }

        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
         //   progressBar.setVisibility(View.VISIBLE);

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
         //       progressBar.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
         //       progressBar.setVisibility(View.GONE);
            }

        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
         //   progressBar.setVisibility(View.VISIBLE);

        }
    }




    public class changePhone extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
                if(obj.getJSONObject("STATUS").getInt("CODE")==201) {
                 /*   Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("STATUS").getString("MESSAGE").toString(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();*/
                    sendDialog.dismiss();
                    verifyDialog();
                }
                else if(obj.getJSONObject("STATUS").getInt("CODE")==200) {
                   /* Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("STATUS").getString("MESSAGE").toString(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();*/
                    sendDialog.dismiss();
                    verifyDialog();
                }
                else{
                    Snackbar.make(findViewById(android.R.id.content), "Server Error Occured", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
           //     progressBar.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
            //    progressBar.setVisibility(View.GONE);
            }

        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
//            progressBar.setVisibility(View.VISIBLE);

        }
    }


}
