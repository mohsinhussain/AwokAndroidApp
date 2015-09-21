package com.awok.moshin.awok.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;

/**
 * Created by mohsin on 9/19/2015.
 */
public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3500;
    SharedPreferences mSharedPrefs;
    LinearLayout logoLayout, formLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logoLayout = (LinearLayout)findViewById(R.id.logoLayout);
        formLayout = (LinearLayout)findViewById(R.id.formLayout);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);


        final EditText mobileEditText = (EditText) findViewById(R.id.mobileEditText);
        Button verifyButton = (Button) findViewById(R.id.verifyButton);
        Button skipButton = (Button) findViewById(R.id.skipButton);
        // if button is clicked, close the custom dialog
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobileEditText.getText().toString().equalsIgnoreCase("")) {
                    Snackbar.make(findViewById(android.R.id.content), "Please enter your mobile number", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                } else {
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.putString(Constants.USER_MOBILE_PREFS, mobileEditText.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mobileEditText.getText().toString().equalsIgnoreCase("")) {
//                    Snackbar.make(findViewById(android.R.id.content), "Please enter your mobile number", Snackbar.LENGTH_LONG)
//                            .setActionTextColor(Color.RED)
//                            .show();
//                } else {
//                    SharedPreferences.Editor editor = mSharedPrefs.edit();
//                    editor.putString(Constants.USER_MOBILE_PREFS, mobileEditText.getText().toString());
//                    editor.commit();
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
//                }
            }
        });




        TranslateAnimation anim = new TranslateAnimation(0, 0, 0,300);
        anim.setDuration(0);
        anim.setFillAfter(true);
        logoLayout.startAnimation(anim);
//        Animation fadeIn = new AlphaAnimation(0, 1);
//        fadeIn.setDuration(100);
//        fadeIn.setFillAfter(true);
//        logoLayout.startAnimation(fadeIn);




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

                    TranslateAnimation anim = new TranslateAnimation(0, 0, 300,0);
                    anim.setDuration(1000);
                    anim.setFillAfter(true);
                    anim.setInterpolator(new DecelerateInterpolator());
//                    anim.setInterpolator();
                    logoLayout.startAnimation(anim);

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
                            fadeIn.setDuration(1000);
                            fadeIn.setFillAfter(true);
                            formLayout.setAnimation(fadeIn);

                        }
                    });
//                    Intent i = new Intent(SplashActivity.this, LauncherActivity.class);
//                    startActivity(i);
//                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }

}
