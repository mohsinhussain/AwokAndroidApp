package com.awok.moshin.awok.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;

public class LauncherActivity extends AppCompatActivity {

    SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);

//        // custom dialog
//        final Dialog dialog = new Dialog(this, R.style.AppCompatAlertDialogStyle);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_input_number);
//        final EditText mobileEditText = (EditText) dialog.findViewById(R.id.mobileEditText);
//        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
//        // if button is clicked, close the custom dialog
//        dialogButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mobileEditText.getText().toString().equalsIgnoreCase("")){
//                    Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
//                            .setActionTextColor(Color.RED)
//                            .show();
//                }
//                else{
//                    SharedPreferences.Editor editor = mSharedPrefs.edit();
//                    editor.putString(Constants.USER_MOBILE_PREFS, mobileEditText.getText().toString());
//                    editor.commit();
//                    Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                    dialog.dismiss();
//                }
//            }
//        });
//
//        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launcher, menu);
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
