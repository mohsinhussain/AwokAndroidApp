package com.awok.moshin.awok.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.CSVReader;
import com.awok.moshin.awok.Util.Constants;

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
    boolean isNumberChecked = false;
    Button verifyButton, skipButton, loginButton, registerButton, forgotPasswordButton, countryCodeButton;
    LinearLayout  formLayout, loginLayout, registerLayout;
    ImageView logoImageView;
    String TAG = "SplashActivity";
    String mobileNumber = "";
    String password = "";
    String countryCodeString = "";
    EditText mobileEditText, passwordEditText, regPasswordEditText, confirmPasswordEditText;
    ProgressBar progressBar;
    ArrayList<String> countryCodes;
    ArrayList<String> countryCodeNumbers;
    List<String[]> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logoImageView = (ImageView)findViewById(R.id.imgLogo);
        formLayout = (LinearLayout)findViewById(R.id.formLayout);
        loginLayout = (LinearLayout)findViewById(R.id.loginLayout);
        progressBar = (ProgressBar)findViewById(R.id.marker_progress);
        registerLayout = (LinearLayout)findViewById(R.id.registerLayout);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        progressBar.setVisibility(View.GONE);
        countryCodeButton = (Button) findViewById(R.id.countryCodeButton);
        mobileEditText = (EditText) findViewById(R.id.mobileEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        regPasswordEditText = (EditText) findViewById(R.id.regPasswordEditText);
        confirmPasswordEditText = (EditText) findViewById(R.id.regConfirmPasswordEditText);
        verifyButton = (Button) findViewById(R.id.nextButton);
        skipButton = (Button) findViewById(R.id.skipButton);
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

        populateCountryCodes();
        countryCodeButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder builderSingle = new AlertDialog.Builder(
//                        SplashActivity.this, R.style.AppCompatAlertDialogStyle);
////                builderSingle.setIcon(R.drawable.ic_launcher);
//                builderSingle.setTitle("Select your country code");
//                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//                        SplashActivity.this,
//                        android.R.layout.simple_list_item_1);
//                arrayAdapter.addAll(countryCodes);
//                builderSingle.setNegativeButton("cancel",
//                        new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//
//                builderSingle.setAdapter(arrayAdapter,
//                        new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                String strName = arrayAdapter.getItem(which);
//                                Log.v("", "Selected Country Code: " + strName);
//                                countryCodeButton.setText(countryCodeNumbers.get(which));
////                                AlertDialog.Builder builderInner = new AlertDialog.Builder(
////                                        RegisterActivity.this);
////                                builderInner.setMessage(strName);
////                                builderInner.setTitle("Your Selected Item is");
////                                builderInner.setPositiveButton("Ok",
////                                        new DialogInterface.OnClickListener() {
////
////                                            @Override
////                                            public void onClick(
////                                                    DialogInterface dialog,
////                                                    int which) {
////                                                dialog.dismiss();
////                                            }
////                                        });
////                                builderInner.show();
//                            }
//                        });
//                builderSingle.show();


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
        });






        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordEditText.getText().toString().equalsIgnoreCase("")) {
                    passwordEditText.setError(getString(R.string.enter_password));

                }
                else{
                    password = passwordEditText.getText().toString();
                    HashMap<String, Object> userData = new HashMap<String, Object>();
                    userData.put("phone_number", mobileNumber);
                    userData.put("access_key", password);


                    JSONObject dataToSend = new JSONObject(userData);
                    new APIClient(SplashActivity.this, SplashActivity.this, new loginAndRegisterUserCallback()).userLoginAPICall(dataToSend.toString());
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
                else if (!regPasswordEditText.getText().toString().equalsIgnoreCase(confirmPasswordEditText.getText().toString())) {
                    confirmPasswordEditText.setError(getString(R.string.password_do_not_match));
                }
                else{
                    password = regPasswordEditText.getText().toString();
                    HashMap<String, Object> userData = new HashMap<String, Object>();
                    userData.put("phone_number", mobileNumber);
                    userData.put("access_key", password);


                    JSONObject dataToSend = new JSONObject(userData);
                    new APIClient(SplashActivity.this, SplashActivity.this, new loginAndRegisterUserCallback()).useRegisterAPICall(dataToSend.toString());
                }

            }
        });


        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobileEditText.getText().toString().equalsIgnoreCase("")) {
                    mobileEditText.setError(getString(R.string.enter_mobile_number));
                } else {
                    if (mobileEditText.getText().toString().matches("^[+]?[0-9]{9,12}$")) {
                        Log.v(TAG, "phone number is correct");
                    mobileNumber =countryCodeButton.getText().toString().replace(" ", "")+mobileEditText.getText().toString();
                        new APIClient(SplashActivity.this, SplashActivity.this,  new CheckUserCallback()).userCheckAPICall(mobileNumber);
                    }
                    else{
                        mobileEditText.setError(getString(R.string.incorrect_mobile));
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
                userData.put("phone_number", mobileNumber);


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
                if(mSharedPrefs.contains(Constants.USER_MOBILE_PREFS)){
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
                if(obj.getInt("status")==200){
                    if(obj.getJSONObject("message").getBoolean("register")==true){
                        showRegisteration();
                    }
                    else{
                        showLogin();
                    }
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


//        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this, R.layout.list_row_country_code_spinner, countryCodes);
//        countryCodeSpinner.setAdapter(countryAdapter);
//
//// adding event to display codes when country is selected
//
//
//        countryCodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int pos, long arg3) {
//                countrycodeString = "+"+list.get(pos)[1];
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//
//            }
//        });
        }

    public class loginAndRegisterUserCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
                if(obj.getBoolean("errors")) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getString("message"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                else{
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.putString(Constants.USER_MOBILE_PREFS, mobileNumber);
                    editor.putInt(Constants.USER_AUTH_TOKEN_PREFS, obj.getJSONObject("message").getInt("user_token"));
                    editor.putString(Constants.USER_ID_PREFS, obj.getJSONObject("message").getString("id_user"));
                    editor.commit();
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
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
                mobileEditText.setVisibility(View.GONE);
                skipButton.setVisibility(View.GONE);
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
                mobileEditText.setVisibility(View.GONE);
                skipButton.setVisibility(View.GONE);
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
                formLayout.setVisibility(View.VISIBLE);
                verifyButton.setVisibility(View.VISIBLE);
                mobileEditText.setVisibility(View.VISIBLE);
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
}
