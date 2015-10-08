package com.awok.moshin.awok.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;

public class Settings extends AppCompatActivity {
    SharedPreferences mSharedPrefs;
    private LinearLayout countryPicker,currencyPicker;
    private ImageView countryImage;
    String imageIco;
    int imageSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
countryPicker=(LinearLayout)findViewById(R.id.countryLayout);
        currencyPicker=(LinearLayout)findViewById(R.id.currencyLayout);
        countryImage=(ImageView)findViewById(R.id.country_image);

currencyPicker.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(Settings.this,CurrencyPickActivity.class);
        startActivity(i);
    }
});



        countryPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Settings.this,CountryPickerExample.class);
                startActivity(i);
            }
        });

        if(mSharedPrefs.contains(Constants.USER_SETTING_COUNTRY)){
            System.out.println("YES");

            imageIco=mSharedPrefs.getString(Constants.USER_COUNTRY_IMAGE_ID, null);

            imageSource = getResources().getIdentifier(imageIco , "drawable", getPackageName());
            countryImage.setImageDrawable(getResources().getDrawable(imageSource));
            countryImage.setVisibility(View.VISIBLE);
        }
        else
        {
            System.out.println("No");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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




    @Override
    public void onResume()
    {
        super.onResume();
        if(mSharedPrefs.contains(Constants.USER_SETTING_COUNTRY)){
            System.out.println("YES");

            imageIco=mSharedPrefs.getString(Constants.USER_COUNTRY_IMAGE_ID, null);

            imageSource = getResources().getIdentifier(imageIco , "drawable", getPackageName());
            countryImage.setImageDrawable(getResources().getDrawable(imageSource));
            countryImage.setVisibility(View.VISIBLE);
        }
        else
        {
            System.out.println("No");
        }

    }





}
