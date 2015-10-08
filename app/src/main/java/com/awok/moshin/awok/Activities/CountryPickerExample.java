package com.awok.moshin.awok.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Toast;

import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.awok.moshin.awok.Util.CountryPicker;
import com.awok.moshin.awok.Util.CountryPickerListener;

import java.util.Locale;

public class CountryPickerExample extends FragmentActivity {

    SharedPreferences mSharedPrefs;
    String imageIco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_picker);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        if(mSharedPrefs.contains(Constants.USER_SETTING_COUNTRY)){
            System.out.println("YES");

            imageIco=mSharedPrefs.getString(Constants.USER_COUNTRY_IMAGE_ID,null);
        }
        else
        {
            System.out.println("No");
        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        CountryPicker picker = new CountryPicker();
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode) {
                Toast.makeText(
                        CountryPickerExample.this,
                        "Country Name: " + name + " - Code: " + code
                                + " - Currency: "
                                + CountryPicker.getCurrencyCode(code) + " - Dial Code: " + dialCode,
                        Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = mSharedPrefs.edit();
                editor.putString(Constants.USER_COUNTRY_IMAGE_ID, (("flag_"+code.toLowerCase(Locale.ENGLISH))));
                //editor.putString(Constants.USER_COUNTRY_IMAGE_ID, ("flag_"+"aq"));
                editor.putString(Constants.USER_SETTING_COUNTRY,name);
                editor.commit();
onBackPressed();

            }
        });

        transaction.replace(R.id.home, picker);
        transaction.commit();

    }


}
