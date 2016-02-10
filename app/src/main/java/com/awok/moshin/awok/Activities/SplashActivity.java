package com.awok.moshin.awok.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.awok.moshin.awok.Models.Products;
import com.awok.moshin.awok.Models.Profile_CountryModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.CSVReader;
import com.awok.moshin.awok.Util.Constants;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mohsin on 9/19/2015.
 */
public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    private static int LOGO_POSITION_OFFSET = -350;
    private static int ANIMATION_TIME_OUT = 500;
    SharedPreferences mSharedPrefs;
    private String sendValue="";
    boolean isNumberChecked = false;
    CountDownTimer timerLoop;
    Button verifyButton, skipButton, loginButton, registerButton, forgotPasswordButton, countryCodeButton;
    LinearLayout  formLayout, loginLayout, registerLayout;
    ImageView logoImageView;
   // Button resend;
  //  TextView timer;
    private String valueValidation="";
    String TAG = "SplashActivity";
    private CountDownTimer countDownTimer;
    Dialog verifyDialog;
    String userId="";
    String emailId = "";
    CallbackManager callbackManager;
    LinearLayout divider;
    Button fbButton;
    String code="";
    String phoneNumber="";
    String password = "";
    String countryCodeString = "";
    EditText emailEditText, passwordEditText, regPasswordEditText, confirmPasswordEditText,regPhoneNumberEditText;
    ProgressBar progressBar;
    ArrayList<String> countryCodes;
    private  long startTime=0;

    private final long interval = 1 * 1000;
    ArrayList<String> countryCodeNumbers;
    List<String[]> list;
//mobileEditText
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_splash);
        callbackManager = CallbackManager.Factory.create();
        LoginButton fbloginButton = (LoginButton) findViewById(R.id.fbButton);
  /*      fbloginButton.setReadPermissions("public_profile");
        fbloginButton.setReadPermissions("public_profile");*/
        fbloginButton.setReadPermissions("user_friends","email","public_profile");

        fbloginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                System.out.println("LOGIN" + loginResult.getAccessToken().getToken());


                ConnectivityManager connMgr = (ConnectivityManager)
                        getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    new APIClient(SplashActivity.this, getApplicationContext(), new loginHash()).loginFb(loginResult.getAccessToken().getToken().toString());


                } else {

                    Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();

                }


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


        logoImageView = (ImageView)findViewById(R.id.imgLogo);
        formLayout = (LinearLayout)findViewById(R.id.formLayout);
        loginLayout = (LinearLayout)findViewById(R.id.loginLayout);
        progressBar = (ProgressBar)findViewById(R.id.marker_progress);
        registerLayout = (LinearLayout)findViewById(R.id.registerLayout);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        progressBar.setVisibility(View.GONE);
      ////////  countryCodeButton = (Button) findViewById(R.id.countryCodeButton);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        regPasswordEditText = (EditText) findViewById(R.id.regPasswordEditText);
        confirmPasswordEditText = (EditText) findViewById(R.id.regConfirmPasswordEditText);
        regPhoneNumberEditText=(EditText)findViewById(R.id.regPhoneNumberEditText);
        verifyButton = (Button) findViewById(R.id.nextButton);
        skipButton = (Button) findViewById(R.id.skipButton);
        divider=(LinearLayout)findViewById(R.id.divider);
        fbButton=(Button)findViewById(R.id.fbButton);
        forgotPasswordButton = (Button) findViewById(R.id.forgotPasswordButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        // if button is clicked, close the custom dialog

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int densityDpi = (int)(metrics.density * 160f);
        Log.v(TAG, "desnsity: "+densityDpi);
        switch (densityDpi){
            case 120:
                LOGO_POSITION_OFFSET = -150;
                break;
            case 240:
                LOGO_POSITION_OFFSET = -200;
                break;
            case 320:
                LOGO_POSITION_OFFSET = -350;
                break;
            case 480:
                LOGO_POSITION_OFFSET = -600;
                break;
            case 560:
                LOGO_POSITION_OFFSET = -700;
                break;
            case 640:
                LOGO_POSITION_OFFSET = -800;
                break;
            default:
                LOGO_POSITION_OFFSET = -250;
                break;
        }

      /*  populateCountryCodes();
        countryCodeButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                //show login dialog
                final Dialog countryCodeDialog = new Dialog(SplashActivity.this, R.style.AppCompatAlertDialogStyle);
                countryCodeDialog.setCancelable(true);
                countryCodeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                countryCodeDialog.setContentView(R.layout.dialog_country_code);
                final ListView mList = (ListView)countryCodeDialog.findViewById(R.id.codeListView);

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        SplashActivity.this,
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
        });*/






        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordEditText.getText().toString().equalsIgnoreCase("")) {
                    passwordEditText.setError(getString(R.string.enter_password));

                }
                else if (passwordEditText.getText().length()<6) {
                    passwordEditText.setError(getString(R.string.enter_min_char));
                }

                else{
                    password = passwordEditText.getText().toString();
                    HashMap<String, Object> userData = new HashMap<String, Object>();
                    userData.put("email_id", emailId);
                    userData.put("access_key", password);


                    JSONObject dataToSend = new JSONObject(userData);
                    valueValidation="login";
                    new APIClient(SplashActivity.this, SplashActivity.this, new loginAndRegisterUserCallback()).userLoginAPICall(emailId, password);
                }
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (regPasswordEditText.getText().toString().equalsIgnoreCase("")) {
                    regPasswordEditText.setError(getString(R.string.enter_password));

                }

                else if (confirmPasswordEditText.getText().toString().equalsIgnoreCase("")) {
                    confirmPasswordEditText.setError(getString(R.string.please_confirm_password));
                }
                else if (regPhoneNumberEditText.getText().toString().equalsIgnoreCase("")) {
                    regPhoneNumberEditText.setError("Please Enter Phone Number");
                }
                else if(regPasswordEditText.getText().length()<6)
                {
                    regPasswordEditText.setError(getString(R.string.enter_min_char));
                }

                else if(!android.util.Patterns.PHONE.matcher(regPhoneNumberEditText.getText().toString()).matches()||regPhoneNumberEditText.getText().length()<10)
                {
                    regPhoneNumberEditText.setError("Incorrect Phone Number");
                }

                else if(confirmPasswordEditText.getText().length()<6)
                {
                    confirmPasswordEditText.setError(getString(R.string.enter_min_char));
                }


                else if (!regPasswordEditText.getText().toString().equalsIgnoreCase(confirmPasswordEditText.getText().toString())) {
                    confirmPasswordEditText.setError(getString(R.string.password_do_not_match));
                }



                else{
                    password = regPasswordEditText.getText().toString();
                    HashMap<String, Object> userData = new HashMap<String, Object>();

                    userData.put("email", emailId);
                    userData.put("password", password);
                    userData.put("cpassword", password);
                    userData.put("phone",regPhoneNumberEditText.getText().toString());


                    JSONObject dataToSend = new JSONObject(userData);
                    valueValidation="register";
                    new APIClient(SplashActivity.this, SplashActivity.this, new loginAndRegisterUserCallback()).useRegisterAPICall(dataToSend.toString());
                }

            }
        });


        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailEditText.getText().toString().equalsIgnoreCase("")) {
                    emailEditText.setError(getString(R.string.enter_emailId));
                } else {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText()).matches()) {
                        Log.v(TAG, "Email is correct");
                       // emailId =countryCodeButton.getText().toString().replace(" ", "")+emailEditText.getText().toString();
                        emailId =emailEditText.getText().toString();
                        new APIClient(SplashActivity.this, SplashActivity.this,  new CheckUserCallback()).userCheckAPICall(emailId);
                    }
                    else{
                        emailEditText.setError(getString(R.string.incorrect_email));
                    }

                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> userData = new HashMap<String, Object>();
                userData.put("email_id", emailId);


                JSONObject dataToSend = new JSONObject(userData);
                new APIClient(SplashActivity.this, SplashActivity.this, new ForgotPasswordUserCallback()).userForgotPassword(dataToSend.toString());
            }
        });

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(mSharedPrefs.contains(Constants.USER_ID_PREFS)){
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{

                    TranslateAnimation anim = new TranslateAnimation(0, 0, 0,LOGO_POSITION_OFFSET);
                    anim.setDuration(ANIMATION_TIME_OUT);
                    anim.setFillAfter(true);
                    anim.setInterpolator(new DecelerateInterpolator());
//                    anim.setInterpolator();
                    logoImageView.startAnimation(anim);

                    anim.setAnimationListener(new TranslateAnimation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            Animation fadeIn = new AlphaAnimation(0, 1);
                            fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                            fadeIn.setDuration(ANIMATION_TIME_OUT);
                            fadeIn.setFillAfter(true);
                            formLayout.setAnimation(fadeIn);

                        }
                    });
                }

            }
        }, SPLASH_TIME_OUT);
    }

    public class CheckUserCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
              /*  if(obj.getInt("status")==200){
                    if(obj.getJSONObject("message").getBoolean("register")==true){
                        showRegisteration();
                    }
                    else{
                        showLogin();
                    }
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
                            showRegisteration();
                        }
                        else if(obj.getJSONObject("STATUS").getInt("CODE")==409)
                        {
                            showLogin();
                        }
                    }
                System.out.println(obj.toString());
            /*    if(obj.has("ERROR"))
                {
                    showLogin();
            }
                else
                {
                    showRegisteration();
                }*/
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

    public class loginAndRegisterUserCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
                System.out.println("OUTPU" + obj.toString());
              /*  if(obj.has("ERROR")) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("OUTPUT").getJSONArray("MESSAGE").getString(0).toString(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                else{
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.putString(Constants.USER_EMAIL_PREFS, emailId);
                    editor.putString(Constants.USER_AUTH_TOKEN_PREFS, obj.getJSONObject("OUTPUT").getJSONObject("DATA").getString("LOGIN_ACCESS_TOKEN"));
                    editor.putString(Constants.USER_ID_PREFS, obj.getJSONObject("OUTPUT").getJSONObject("DATA").getString("USER_ID"));
                    editor.commit();
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }*/



                if(obj.getJSONObject("STATUS").getInt("CODE")==400) {
                   /* Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();*/
                    for(int i=0;i<obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").length();i++)
                    {
                        JSONObject verificationCheck=obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(i);

                            if (verificationCheck.getString("FIELD").equals("PASSWORD")) {
                                regPasswordEditText.setError(verificationCheck.getString("MESSAGE"));
                            }
                            if (verificationCheck.getString("FIELD").equals("EMAIL")) {
                                Snackbar.make(findViewById(android.R.id.content), verificationCheck.getString("MESSAGE"), Snackbar.LENGTH_LONG)
                                        .setActionTextColor(Color.RED)
                                        .show();
                            }

                    }
                    emailEditText.setError(obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE"));
                }
                else if(obj.getJSONObject("STATUS").getInt("CODE")==404)
                {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }

                else if(obj.getJSONObject("STATUS").getInt("CODE")==201) {
                 ///   userId=obj.getJSONObject("OUTPUT").getJSONObject("DATA").getString("USER_ID");
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.putString(Constants.USER_EMAIL_PREFS, emailId);
                    editor.putString(Constants.USER_AUTH_TOKEN_PREFS, obj.getJSONObject("OUTPUT").getJSONObject("DATA").getString("TOKEN"));
                    //editor.putString(Constants.USER_ID_PREFS, obj.getJSONObject("OUTPUT").getJSONObject("DATA").getString("USER_ID"));
                    editor.putString(Constants.USER_ID_PREFS, "Y");
                    editor.commit();
                    if(valueValidation.equals("register"))
                    {
                        if(obj.getJSONObject("OUTPUT").getJSONObject("DATA").has("CODE_EXP_TIME")) {
startTime=obj.getJSONObject("OUTPUT").getJSONObject("DATA").getInt("CODE_EXP_TIME") * 1000;
                            verifyDialog();
                        }
                    }
                    else {
                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }

                else if(obj.getJSONObject("STATUS").getInt("CODE")==409) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                else
                {
                    Snackbar.make(findViewById(android.R.id.content), "Server Error Occured", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }

                progressBar.setVisibility(View.GONE);
                valueValidation="";
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

    public class ForgotPasswordUserCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
                if(obj.getBoolean("errors")) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getString("message"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                else{
                    passwordEditText.setText(obj.getString("message"));
                    loginButton.performClick();
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

    public void showLogin(){
        Animation fadOut = new AlphaAnimation(1, 0);
        fadOut.setInterpolator(new DecelerateInterpolator()); //add this
        fadOut.setDuration(ANIMATION_TIME_OUT);
        fadOut.setFillAfter(true);
        formLayout.startAnimation(fadOut);
        fadOut.setAnimationListener(new TranslateAnimation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                fadeIn.setDuration(ANIMATION_TIME_OUT);
                fadeIn.setFillAfter(true);
                loginLayout.setAnimation(fadeIn);
                isNumberChecked = true;
                loginLayout.setVisibility(View.VISIBLE);
                emailEditText.setVisibility(View.GONE);
                skipButton.setVisibility(View.GONE);
                fbButton.setVisibility(View.GONE);
                divider.setVisibility(View.GONE);
                verifyButton.setVisibility(View.GONE);
                formLayout.setVisibility(View.GONE);
            }
        });

    }

    public void showRegisteration(){
        Animation fadOut = new AlphaAnimation(1, 0);
        fadOut.setInterpolator(new DecelerateInterpolator()); //add this
        fadOut.setDuration(ANIMATION_TIME_OUT);
        fadOut.setFillAfter(true);
        formLayout.startAnimation(fadOut);
        fadOut.setAnimationListener(new TranslateAnimation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                fadeIn.setDuration(ANIMATION_TIME_OUT);
                fadeIn.setFillAfter(true);
                registerLayout.setAnimation(fadeIn);
                isNumberChecked = true;
                registerLayout.setVisibility(View.VISIBLE);
                emailEditText.setVisibility(View.GONE);
                skipButton.setVisibility(View.GONE);
              //  registerButton.setVisibility(View.VISIBLE);
                fbButton.setVisibility(View.GONE);
                verifyButton.setVisibility(View.GONE);
                formLayout.setVisibility(View.GONE);
            }
        });
    }

    public void showFormLayout(){
        Animation fadOut = new AlphaAnimation(1, 0);
        fadOut.setInterpolator(new DecelerateInterpolator()); //add this
        fadOut.setDuration(ANIMATION_TIME_OUT);
        fadOut.setFillAfter(true);
        if(registerLayout.getVisibility()==View.VISIBLE){
            registerLayout.startAnimation(fadOut);
            registerLayout.setVisibility(View.GONE);
        }
        else if(loginLayout.getVisibility()==View.VISIBLE){
            loginLayout.startAnimation(fadOut);
            loginLayout.setVisibility(View.GONE);
        }
        fadOut.setAnimationListener(new TranslateAnimation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                fadeIn.setDuration(ANIMATION_TIME_OUT);
                fadeIn.setFillAfter(true);
                formLayout.setAnimation(fadeIn);
                isNumberChecked = false;
                fbButton.setVisibility(View.VISIBLE);
                divider.setVisibility(View.VISIBLE);
                formLayout.setVisibility(View.VISIBLE);
                verifyButton.setVisibility(View.VISIBLE);
                emailEditText.setVisibility(View.VISIBLE);
                skipButton.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(isNumberChecked){
            showFormLayout();
        }
        else{
            super.onBackPressed();
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


        changePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode.setHint("Enter New Phone Number");
                nextButton.setText("Send");
                sendValue="changeNumber";
//sendData(sendValue);
            }
        });
skip.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        verifyDialog.dismiss();
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(i);
        finish();
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




/*    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            timer.setText("Code Expired");
            resend.setEnabled(true);
            resend.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
        }

        @Override
        public void onTick(long millisUntilFinished) {
            timer.setText("" + millisUntilFinished / 1000);
        }
    }*/




    public void sendData(String value)
    {
        if(value.equals("verify"))
        {
            HashMap<String,String > verifyData=new HashMap<String ,String >();
         //   verifyData.put("user_id",userId);
         //   verifyData.put("code", code);
        //    verifyData.put("mode","verify-sms-code");

            JSONObject dataVerify=new JSONObject(verifyData);
            new APIClient(SplashActivity.this, SplashActivity.this, new verifyCode()).verifySms(code);
        }
           else if(value.equals("changeNumber"))
        {



            HashMap<String,String > resendData=new HashMap<String ,String >();
         //   resendData.put("user_id",userId);
            resendData.put("phone", phoneNumber);
          //  resendData.put("mode","send-verify-sms");

            JSONObject dataResend=new JSONObject(resendData);

            new APIClient(SplashActivity.this, SplashActivity.this, new changePhone()).reSendCallBack(dataResend.toString());
        }
            else if(value.equals("resend"))
        {
            HashMap<String,String > resendSmsData=new HashMap<String ,String >();
      //      resendSmsData.put("user_id",userId);

           // resendSmsData.put("mode","resend-verify-sms");

            JSONObject dataSmsResend=new JSONObject(resendSmsData);

            new APIClient(SplashActivity.this, SplashActivity.this, new reSendSms()).reSendSms(dataSmsResend.toString());
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
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else if (obj.getJSONObject("STATUS").getInt("CODE")==201) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("STATUS").getString("MESSAGE").toString(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
verifyDialog.dismiss();
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
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




    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }



    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode);
        System.out.println(resultCode);
        System.out.println(data);
    }




    public class loginHash extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
                if(obj.getJSONObject("STATUS").getInt("CODE")==400) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("STATUS").getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();



                }
                else if(obj.getJSONObject("STATUS").getInt("CODE")==404)
                {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("STATUS").getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }

                else if(obj.getJSONObject("STATUS").getInt("CODE")==201) {
                    ///   userId=obj.getJSONObject("OUTPUT").getJSONObject("DATA").getString("USER_ID");
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.putString(Constants.USER_EMAIL_PREFS, emailId);
                    editor.putString(Constants.USER_AUTH_TOKEN_PREFS, obj.getJSONObject("OUTPUT").getJSONObject("DATA").getString("TOKEN"));
                    //editor.putString(Constants.USER_ID_PREFS, obj.getJSONObject("OUTPUT").getJSONObject("DATA").getString("USER_ID"));
                    editor.putString(Constants.USER_ID_PREFS, "Y");
                    editor.commit();

                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();

                }

                else if(obj.getJSONObject("STATUS").getInt("CODE")==409) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("STATUS").getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                else
                {
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
