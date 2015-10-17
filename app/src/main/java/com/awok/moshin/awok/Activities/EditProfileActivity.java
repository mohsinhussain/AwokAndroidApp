package com.awok.moshin.awok.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import me.iwf.photopicker.PhotoPagerActivity;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class EditProfileActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    public static final String AVATAR_URL = "http://lorempixel.com/200/200/people/1/";
    private ImageView profileImage;
    RelativeLayout profileParentLayout;
    private EditText inputName, inputEmail, inputMobileNumber, inputDateOfBirth, inputGender;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutMobileNumber, inputLayoutDateOfBirth, inputLayoutGender;
    private Button saveButton;
    SharedPreferences mSharedPrefs;
    String mobileNumber;
    int year_x, month_x, day_x;
    static final int DIALOG_ID = 0;
    static final int REQUEST_CODE = 69;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        progressBar = (ProgressBar) findViewById(R.id.marker_progress);
        progressBar.setVisibility(View.GONE);
        profileImage = (ImageView)findViewById(R.id.avatar);
        //mRecyclerView.setAdapter(mAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        profileParentLayout = (RelativeLayout) findViewById(R.id.profileParentLayout);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_full_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutMobileNumber = (TextInputLayout) findViewById(R.id.input_layout_phone);
        inputLayoutDateOfBirth = (TextInputLayout) findViewById(R.id.input_layout_dob);
        inputLayoutGender = (TextInputLayout) findViewById(R.id.input_layout_gender);
        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputMobileNumber = (EditText) findViewById(R.id.input_phone);
        inputDateOfBirth = (EditText) findViewById(R.id.input_dob);
        inputGender = (EditText) findViewById(R.id.input_gender);
        saveButton = (Button) findViewById(R.id.btn_save);



        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Edit Profile");
        Picasso.with(this).load(R.drawable.textimg).transform(new CircleTransformation()).into(profileImage);


        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputMobileNumber.addTextChangedListener(new MyTextWatcher(inputMobileNumber));
//        inputDateOfBirth.addTextChangedListener(new MyTextWatcher(inputDateOfBirth));
//        inputGender.addTextChangedListener(new MyTextWatcher(inputGender));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });



        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        mobileNumber = mSharedPrefs.getString(Constants.USER_MOBILE_PREFS, null);
        inputMobileNumber.setText(mobileNumber);

        inputDateOfBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    System.out.println("Show Calender Dialog");
                    showDialogOnDateFocus();
                }
            }
        });
        final Integer[] selection = {-1};

        final String[] genderItems = getResources().getStringArray(R.array.gender_array);
        final ArrayAdapter genderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, genderItems);

        inputGender.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    System.out.println("Show Gender Dialog");
                    AlertDialog.Builder genderBuilder = new AlertDialog.Builder(EditProfileActivity.this)
                            .setTitle(R.string.gender_prompt)
                            .setSingleChoiceItems(genderAdapter, selection[0], new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    inputGender.setText(genderItems[which]);
                                    selection[0] = which;
                                    dialog.cancel();
                                }
                            });
                    AlertDialog genderAlert = genderBuilder.create();
                    genderAlert.show();
                }
            }
        });


        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

//        tintWidget(inputName, R.color.button_bg);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(EditProfileActivity.this);
                intent.setPhotoCount(1);
                intent.setShowCamera(true);
                intent.setShowGif(true);
                startActivityForResult(intent, REQUEST_CODE);
//                Intent intent = new Intent(EditProfileActivity.this, PhotoPagerActivity.class);
//                intent.putExtra(PhotoPagerActivity.EXTRA_CURRENT_ITEM, position);
//                intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS, photoPaths);
//                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);

                System.out.println("Image data: "+ photos.get(0));
                File f = new File(photos.get(0));

                Picasso.with(this).load(f).transform(new CircleTransformation()).into(profileImage);
            }
        }
    }


//    public void tintWidget(View view, int color) {
//        Drawable wrappedDrawable = DrawableCompat.wrap(view.getBackground());
//        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(color));
//        view.setBackgroundDrawable(wrappedDrawable);
//    }

    public void showDialogOnDateFocus() {
        showDialog(DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id ==  DIALOG_ID){
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        }
        else{
            return null;
        }
    }

    private DatePickerDialog.OnDateSetListener dpickerListener =  new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            inputDateOfBirth.setText(year_x+"/"+month_x+"/"+day_x);
        }
    };

    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validateMobileNumber()) {
            return;
        }

        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.enter_your_full_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.enter_email_address));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMobileNumber() {
        if (inputMobileNumber.getText().toString().matches("^[+]?[0-9]{9,12}$") || inputMobileNumber.getText().toString().trim().isEmpty()) {
            inputLayoutMobileNumber.setError(getString(R.string.enter_mobile_number));
            requestFocus(inputMobileNumber);
            return false;
        } else {
            inputLayoutMobileNumber.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_phone:
                    validateMobileNumber();
                    break;
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {





        switch (item.getItemId()) {
            case android.R.id.home:
                //mDrawerLayout.openDrawer(GravityCompat.START);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);


    }

}
