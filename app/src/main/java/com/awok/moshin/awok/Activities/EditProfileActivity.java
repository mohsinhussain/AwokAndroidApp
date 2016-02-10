package com.awok.moshin.awok.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.Adapters.InterestLanguageAdapter;
import com.awok.moshin.awok.Adapters.IntersetListAdapter;
import com.awok.moshin.awok.Adapters.ProfileCountryAdapter;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.Interest_LanguageModel;
import com.awok.moshin.awok.Models.Interest_Model;
import com.awok.moshin.awok.Models.Profile_CountryModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.iwf.photopicker.PhotoPagerActivity;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class EditProfileActivity extends AppCompatActivity {

    private DatePickerDialog fromDatePickerDialog;
    //private ProgressBar progressBar;
    private LinearLayout progressBar;
    public static final String AVATAR_URL = "http://lorempixel.com/200/200/people/1/";
    private ImageView profileImage;
    RelativeLayout profileParentLayout;
    private EditText inputName, inputEmail, inputMobileNumber;
    private TextView inputDateOfBirth, inputGender;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutMobileNumber, inputLayoutDateOfBirth, inputLayoutGender;
    private Button saveButton;
    String loginTxt;
    Map<Integer, String> listSel = new HashMap<Integer, String>();
    Map<Integer, String> listLangSel = new HashMap<Integer, String>();

    SharedPreferences mSharedPrefs;
    ArrayList<Interest_Model> interestData=new ArrayList<Interest_Model>();
    ArrayList<Interest_LanguageModel> interestLangData=new ArrayList<Interest_LanguageModel>();
    String mobileNumber;
    private Dialog dialogInterest,langDialog,countryDialog;
    ArrayList<String> finalListId=new ArrayList<>();
    ArrayList<String> finalLangListId=new ArrayList<>();
    private LinearLayout dobLay, genderLay,interestLay,langLay,countryLay;
    int year_x, month_x, day_x;
    private String userId;
    private TextView language,interestTxt;
    Calendar newCalendar;
    List<String> listData = new ArrayList<String>();
    TextView country;
    List<String> listCountry = new ArrayList<String>();
    List<String> listLanguage = new ArrayList<String>();
    HashMap<String, String> listIdData = new HashMap<String, String>();
    DateFormat dateFormatter;
    ProfileCountryAdapter countryAdapter;
    ArrayList<Profile_CountryModel> countryPopulateData=new ArrayList<>();
    //SharedPreferences mSharedPrefs;
    static final int DIALOG_ID = 0;
    Calendar currentDate;
    TextView profilePictureTextView;
    IntersetListAdapter adapter;
    String countryName="";
    //ArrayList<String> arrayLanguages=new ArrayList<>();
    static final int REQUEST_CODE = 69;
    InterestLanguageAdapter langAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        if ((mSharedPrefs.contains(Constants.USER_ID_PREFS))) {
            userId = mSharedPrefs.getString(Constants.USER_ID_PREFS, null);
        }
        dateFormatter = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        //progressBar = (ProgressBar) findViewById(R.id.marker_progress);
        progressBar = (LinearLayout) findViewById(R.id.progressLayout);

        progressBar.setVisibility(View.GONE);
        profileImage = (ImageView) findViewById(R.id.avatar);
        language=(TextView)findViewById(R.id.language);
        //mRecyclerView.setAdapter(mAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        profileParentLayout = (RelativeLayout) findViewById(R.id.profileParentLayout);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_full_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutMobileNumber = (TextInputLayout) findViewById(R.id.input_layout_phone);
        // inputLayoutDateOfBirth = (TextInputLayout) findViewById(R.id.input_layout_dob);
        //  inputLayoutGender = (TextInputLayout) findViewById(R.id.input_layout_gender);
        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        interestTxt=(TextView)findViewById(R.id.interest);
        country=(TextView)findViewById(R.id.country);
        inputMobileNumber = (EditText) findViewById(R.id.input_phone);
        inputDateOfBirth = (TextView) findViewById(R.id.date);
        inputGender = (TextView) findViewById(R.id.gender);
        saveButton = (Button) findViewById(R.id.btn_save);
        langLay=(LinearLayout)findViewById(R.id.langLay);
        profilePictureTextView=(TextView)findViewById(R.id.profilePictureTextView);
        profilePictureTextView.requestFocus();
        countryLay=(LinearLayout)findViewById(R.id.countryLay);
langLay.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        selectLanguage();
    }
});
         newCalendar = Calendar.getInstance();
        dobLay = (LinearLayout) findViewById(R.id.dobLay);
        genderLay = (LinearLayout) findViewById(R.id.genderLay);


                    interestLay=(LinearLayout)findViewById(R.id.interestLay);



        interestLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAlertDialogWithListview();
            }
        });

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Edit Profile");
        Picasso.with(this).load(R.drawable.default_img).transform(new CircleTransformation()).into(profileImage);


        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        /*inputMobileNumber.addTextChangedListener(new MyTextWatcher(inputMobileNumber));*/
//        inputDateOfBirth.addTextChangedListener(new MyTextWatcher(inputDateOfBirth));
//        inputGender.addTextChangedListener(new MyTextWatcher(inputGender));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

     adapter = new IntersetListAdapter(EditProfileActivity.this, interestData, getApplicationContext());
        langAdapter = new InterestLanguageAdapter(EditProfileActivity.this,interestLangData,getApplicationContext());





        countryAdapter=new ProfileCountryAdapter(EditProfileActivity.this,countryPopulateData,getApplicationContext());

     /*   mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        mobileNumber = mSharedPrefs.getString(Constants.USER_MOBILE_PREFS, null);
        inputMobileNumber.setText(mobileNumber);*/

    /*    inputDateOfBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    System.out.println("Show Calender Dialog");
                    showDialogOnDateFocus();
                }
            }
        });*/

        dobLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialogOnDateFocus();
                newDate();
            }
        });






        countryLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryPopup();
            }
        });


        final Integer[] selection = {-1};

        final String[] genderItems = getResources().getStringArray(R.array.gender_array);
        final ArrayAdapter genderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, genderItems);

       /* inputGender.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        });*/


        genderLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
         currentDate = Calendar.getInstance();
/*        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);*/

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


        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // new APIClient(ProductDetailsActivity.this, getApplicationContext(),  new GetProductDetailsCallback()).productDetailsAPICall(productId);
            new APIClient(EditProfileActivity.this, getApplicationContext(), new GetProfileCallback()).getProfileDetails(userId);
        } else {
            /*Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();*/

            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);

                System.out.println("Image data: " + photos.get(0));
                File f = new File(photos.get(0));
                String pathFile=f.getAbsolutePath().toString();

progressBar.setVisibility(View.VISIBLE);
                Bitmap bitmap = BitmapFactory.decodeFile(pathFile);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                String baseImg= Base64.encodeToString(byteArray, Base64.DEFAULT);
                System.out.println("IMAGE" + baseImg);



progressBar.setVisibility(View.GONE);


                Picasso.with(this).load(f).transform(new CircleTransformation()).into(profileImage);
                JSONObject imgData = new JSONObject();


                try {

                    imgData.put("USER_IMAGE",baseImg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    // new APIClient(ProductDetailsActivity.this, getApplicationContext(),  new GetProductDetailsCallback()).productDetailsAPICall(productId);
                    new APIClient(EditProfileActivity.this, getApplicationContext(), new GetUpdateProfilePictureCallback()).getUpdateProfilePicture(userId, imgData.toString());
                } else {
            /*Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();*/

                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED);

                    View snackbarView = snackbar.getView();

                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();
                }

            }
        }
    }


//    public void tintWidget(View view, int color) {
//        Drawable wrappedDrawable = DrawableCompat.wrap(view.getBackground());
//        DrawableCompat.setTint(wrappedDrawable, getResources().getColor(color));
//        view.setBackgroundDrawable(wrappedDrawable);
//    }

   /* public void showDialogOnDateFocus() {
        showDialog(DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        } else {
            return null;
        }
    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            inputDateOfBirth.setText(year_x + "/" + month_x + "/" + day_x);
        }
    };*/

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




        HashMap<String,String> sendMainJson=new HashMap<String,String>();
        sendMainJson.put("NAME",inputName.getText().toString());
        sendMainJson.put("EMAIL",inputEmail.getText().toString());
        sendMainJson.put("PERSONAL_MOBILE",inputMobileNumber.getText().toString());
        sendMainJson.put("LOGIN",loginTxt);




        if(inputGender.getText().toString().equalsIgnoreCase("Female"))
        {
            sendMainJson.put("PERSONAL_GENDER","F");
        }
        else
        {
            sendMainJson.put("PERSONAL_GENDER","M");
        }

        sendMainJson.put("PERSONAL_STATE",country.getText().toString());

        sendMainJson.put("save", "Save");



        HashMap<String,String> dateData=new HashMap<>();
        dateData.put("day",Integer.toString(day_x));
                dateData.put("month",Integer.toString(month_x));
        dateData.put("year",Integer.toString(year_x));
JSONObject dateObj=new JSONObject(dateData);
      /*  "EMAIL": "khalidumar03@gmail.com",
                "LOGIN": "khalidumar03",
                "PERSONAL_GENDER": "M",
                "date_": {
            "day": "15",
                    "month": "07",
                    "year": "1993"
        },
        "PERSONAL_STATE": "United Arab Emirates",
                "PERSONAL_PAGER": ["English", "Afrikaans", "Pashto"],
        "PERSONAL_NOTES": ["582", "999"],
        "save": "Save"*/
        JSONObject newz=new JSONObject(sendMainJson);
        JSONArray listLang = new JSONArray(finalLangListId);
        JSONArray listNotes = new JSONArray(finalListId);
        try {
            newz.put("PERSONAL_PAGER",listLang);
            newz.put("PERSONAL_NOTES",listNotes);
            newz.put("date_",dateObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //sendMainJson.put("PERSONAL_PAGER", listLang);
        //sendMainJson.put("PERSONAL_NOTES", listNotes);

System.out.println("JDBcjkdgb" + newz.toString());
        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();




        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // new APIClient(ProductDetailsActivity.this, getApplicationContext(),  new GetProductDetailsCallback()).productDetailsAPICall(productId);
            new APIClient(EditProfileActivity.this, getApplicationContext(), new GetUpdateProfileCallback()).getUpdateProfile(userId,newz.toString());
        } else {
            /*Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();*/

            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }



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
        if (inputMobileNumber.getText().toString().matches("^\\+[0-9]{10,13}$") || inputMobileNumber.getText().toString().trim().isEmpty()) {
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


    public class GetProfileCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                profilePictureTextView.requestFocus();
                JSONObject mMembersJSON = new JSONObject(response);
                if (mMembersJSON.has("ERROR")) {
                    Snackbar.make(findViewById(android.R.id.content), "Error Occured .. Please try again later", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                } else {

loginTxt=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("LOGIN");
              if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("NAME").equals(null)||mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("NAME").equals(""))
                    {

                    }
                    else
                    {
                        inputName.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("NAME"));

                    }

                    if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_MOBILE").equals(null)||
                            mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_MOBILE").equals(""))
                    {

                    }
                    else
                    {
                        inputMobileNumber.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_MOBILE"));

                    }

                    if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("EMAIL").equals(null)||
                            mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("EMAIL").equals(""))
                    {

                    }
                    else
                    {
                        inputEmail.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("EMAIL"));

                    }







                  /*  if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_GENDER").equals(null)||
                            mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_GENDER").equals(""))
                    {

                    }
                    else
                    {
                        inputGender.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_GENDER"));

                    }*/
                    if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_GENDER").equals(null)||
                            mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_GENDER").equals(""))
                    {

                    }
                    else
                    {
                        if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_GENDER").equalsIgnoreCase("M")) {
                            //   inputGender.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_GENDER"));
                            inputGender.setText("Male");
                        }
                        else
                        {
                            inputGender.setText("Female");
                        }

                    }

                    if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_BIRTHDAY").equals(null)||
                            mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_BIRTHDAY").equals(""))
                    {

                    }
                    else
                    {
                        inputDateOfBirth.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_BIRTHDAY"));

                    }




                    System.out.println("bdhmgmdcv" + mMembersJSON);
                    int lengthList = mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("INTEREST_LIST").length();
                    int personalNotesArraySize=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_NOTES").length();
                    if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_NOTES").length()>0) {
                        interestTxt.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_NOTES").length() + " Selected");

                    }
                    for (int i = 0; i < lengthList; i++) {
                        Interest_Model im=new Interest_Model();
                        JSONObject data = mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("INTEREST_LIST").getJSONObject(i);
                        listData.add(data.getString("VALUE"));
                        listIdData.put(data.getString("ID"), data.getString("VALUE"));
                        im.setName(data.getString("VALUE"));
                        im.setId(data.getInt("ID"));
                        if(data.getBoolean("SELECTED"))
                        {
                            im.setSelected(true);
                            listSel.put(i, data.getString("ID"));
                            System.out.println("dvbfjhgxjfgxjhfvb" + listSel);
                            finalListId.add(data.getString("ID"));
                        }
                        else {
                            im.setSelected(false);
                        }



                   /*     if(personalNotesArraySize>0)
                        {
                            for(int l=0;l<personalNotesArraySize;l++)
                            {
                                String valueInterest=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_NOTES").getString(l);
                                System.out.println("dgvbjdfkd"+valueInterest);

                                if (data.getString("ID").equals(valueInterest))
                                {
                                    im.setSelected(true);
                                }
                                else
                                {
                                    im.setSelected(false);
                                }
                            }
                        }*/



                        interestData.add(im);
                    }
                 /*   int personalNotesArraySize=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_NOTES").length();

                    if(personalNotesArraySize>0)
                    {
                        for(int l=0;l<personalNotesArraySize;l++)
                        {
                            String valueInterest=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_NOTES").getString(l);
                            for(int m=0;m<interestData.size();m++)
                            {
                                if(interestData.get(m).contains(valueInterest))
                                {
                                    listData.set(interestData).set
                                }
                            }
                        }
                    }*/

String countryName=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_STATE");
                    int lengthCountry = mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("COUNTRY_LIST").length();

                            if(!mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_STATE").equals(""))
                            {
                                country.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_STATE"));
                            }
                    for (int j = 0; j < lengthCountry; j++) {
                        //JSONObject dataCountry=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("INETEREST_LIST").getJSONObject(j);
                        listCountry.add(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("COUNTRY_LIST").getString(j));
                        Profile_CountryModel countryData=new Profile_CountryModel();
                        if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("COUNTRY_LIST").getString(j).equals(countryName))
                        {
                            countryData.setSelected(true);
                        }
                        else {
                            countryData.setSelected(false);
                        }
                        countryData.setName(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("COUNTRY_LIST").getString(j));


                        countryPopulateData.add(countryData);


                    }


                    int lengthLanguage = mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("LANGUAGE_LIST").length();



                   ArrayList<String> dataMainCountry=new ArrayList<>();
                    if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_PAGER").length()>0) {
                        for(int z=0;z<mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_PAGER").length();z++)
                        {

                            dataMainCountry.add(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_PAGER").getString(z));
                        }
                        StringBuilder builderMain = new StringBuilder();
                        for (String details : dataMainCountry) {
                            if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_PAGER").length()>1) {
                                builderMain.append(details + " ,");
                            }
                            else
                            {
                                builderMain.append(details);
                            }
                        }
                        language.setText(builderMain.toString());
                    }







                   /* ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                    imageLoader.get("http://"+photoUrl, new ImageLoader.ImageListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            profileImage.setImageDrawable(getResources().getDrawable(R.drawable.default_img));

                        }
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                            if (response.getBitmap() != null) {
                                // load image into imageview
                                profileImage.setImageBitmap(response.getBitmap());
                                Picasso.with(EditProfileActivity.this).load()

                            }
                        }
                    });*/
                    if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_PHOTO").length()>0) {
                        String photoUrl=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_PHOTO").getString("SRC");
                        Picasso.with(EditProfileActivity.this).load("http://" + photoUrl).transform(new CircleTransformation()).into(profileImage);
                    }



                    for (int k = 0; k < lengthLanguage; k++) {
                        //JSONObject dataCountry=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("INETEREST_LIST").getJSONObject(j);
                        listLanguage.add(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("LANGUAGE_LIST").getString(k));
                        Interest_LanguageModel langDataList=new Interest_LanguageModel();
                        JSONObject data = mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("LANGUAGE_LIST").getJSONObject(k);
                        /*listData.add(data.getString("VALUE"));
                        listIdData.put(data.getString("ID"), data.getString("VALUE"));*/
                        langDataList.setName(data.getString("VALUE"));
//                        langDataList.setId(data.getInt("ID"));
                        if(data.getBoolean("SELECTED"))
                        {
                            langDataList.setSelected(true);
                            listLangSel.put(k, data.getString("VALUE"));
                            System.out.println("dvbfjhgxjfgxjhfvb" + listLangSel);
                            finalLangListId.add(data.getString("VALUE"));
                        }
                        else {
                            langDataList.setSelected(false);
                        }





                        interestLangData.add(langDataList);
                    }


                    if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_BIRTHDAY_FORMATED").getString("DAY").equals("")||
                    mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_BIRTHDAY_FORMATED").getString("MONTH").equals("")||
                    mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_BIRTHDAY_FORMATED").getString("YEAR").equals(""))
                    {
                        year_x = currentDate.get(Calendar.YEAR);
                        month_x = currentDate.get(Calendar.MONTH);
                        day_x = currentDate.get(Calendar.DAY_OF_MONTH);
                    }
                    else
                    {
                         currentDate.set(Calendar.MONTH, mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_BIRTHDAY_FORMATED").getInt("MONTH")); // Months are 0-based!
                         currentDate.set(Calendar.DAY_OF_MONTH, mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_BIRTHDAY_FORMATED").getInt("MONTH")); // Clearer than DATE
                         currentDate.set(Calendar.YEAR, mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_BIRTHDAY_FORMATED").getInt("YEAR"));

                        year_x = currentDate.get(Calendar.YEAR);
                        month_x = currentDate.get(Calendar.MONTH);
                        day_x = currentDate.get(Calendar.DAY_OF_MONTH);
                    }


                    //final Calendar cal = Calendar.getInstance();




                    System.out.println("list" + listData);
                    System.out.println("lisDATA" + listIdData);
                    System.out.println("country" + listCountry);
                    System.out.println("language" + listLanguage);


                }
                progressBar.setVisibility(View.GONE);
                profilePictureTextView.requestFocus();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onTaskCancelled() {
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
        }
    }


    public void newDate() {
//        Calendar newCalendar = Calendar.getInstance();
        /*fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                inputDateOfBirth.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));*/
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                year_x=year;
                month_x=monthOfYear+1;
                day_x=dayOfMonth;
                inputDateOfBirth.setText(dateFormatter.format(newDate.getTime()));
            }

        }, year_x, month_x, day_x);
        fromDatePickerDialog.show();
    }


    public void ShowAlertDialogWithListview() {

        //Create sequence of items

        dialogInterest = new Dialog(this);
        dialogInterest.setCancelable(true);
        dialogInterest.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogInterest.setContentView(R.layout.dialog_profile_interest);
        Button cancelButton = (Button) dialogInterest.findViewById(R.id.cancelButton);
        Button nextButton = (Button) dialogInterest.findViewById(R.id.nextButton);
        final ListView interest = (ListView) dialogInterest.findViewById(R.id.interest);
        interest.setAdapter(adapter);
        interest.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView name = (TextView) view.findViewById(R.id.title);
                //ImageView i=(ImageView)view.findViewById(R.id.ee);
                // selected item

               /* c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {*/
                Interest_Model m = interestData.get(position);
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

                if (c.getVisibility() == View.VISIBLE) {
                    interestData.get(position).setSelected(false);
                    c.setVisibility(View.GONE);
                    listSel.remove(position);
                    System.out.println("dn" + listSel);

                } else {
                    c.setVisibility(View.VISIBLE);
                    interestData.get(position).setSelected(true);

                    listSel.put(position, name.getTag().toString());
                    System.out.println("dn" + listSel);
                }
            }

        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalListId.clear();
                for(Map.Entry<Integer,String> map : listSel.entrySet()){

                    finalListId.add(map.getValue());
                    System.out.println("LIST" + finalListId);

                }
                interestTxt.setText(finalListId.size()+ " Selected");
                dialogInterest.dismiss();
            }
        });

                dialogInterest.show();
        dialogInterest.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }





    private void selectLanguage() {
        langDialog = new Dialog(this);
        langDialog.setCancelable(true);
        langDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        langDialog.setContentView(R.layout.dialog_profile_language);
        Button cancelButton = (Button) langDialog.findViewById(R.id.cancelButton);
        Button nextButton = (Button) langDialog.findViewById(R.id.nextButton);
        final ListView langList = (ListView) langDialog.findViewById(R.id.languageList);
        langList.setAdapter(langAdapter);




        langList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView name = (TextView) view.findViewById(R.id.title);
                //ImageView i=(ImageView)view.findViewById(R.id.ee);
                // selected item

               /* c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {*/
                Interest_LanguageModel m = interestLangData.get(position);
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

                if (c.getVisibility() == View.VISIBLE) {
                    interestLangData.get(position).setSelected(false);
                    c.setVisibility(View.GONE);
                    listLangSel.remove(position);
                    System.out.println("dn" + listLangSel);

                } else {
                    c.setVisibility(View.VISIBLE);
                    interestLangData.get(position).setSelected(true);

                    listLangSel.put(position, name.getText().toString());
                    System.out.println("dn" + listLangSel);
                }
            }

        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalLangListId.clear();
                for (Map.Entry<Integer, String> map : listLangSel.entrySet()) {

                    finalLangListId.add(map.getValue());
                    System.out.println("LIST" + finalLangListId);

                }
                String listString = "";

                /*for (String s : finalListId)
                {
                    listString +=s;
                }*/
                /*String array[] = new String[finalListId.size()];
                for(int j =0;j<finalListId.size();j++){
                    array[j] = finalListId.get(j);
                }
                for (String s : array)
                {
                    listString +=s + "\t";
                    System.out.println(s);
                }

                System.out.println("lisdt"+listString);
                language.setText(listString);*/
                StringBuilder builder = new StringBuilder();
                for (String details : finalLangListId) {
                    builder.append(details + " ,");
                }
language.setText(builder.toString());
                langDialog.dismiss();
            }
        });


        langDialog.show();
        langDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void countryPopup() {
        countryDialog = new Dialog(this);
        countryDialog.setCancelable(true);
        countryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        countryDialog.setContentView(R.layout.dialog_profile_country);
        Button cancelButton = (Button) countryDialog.findViewById(R.id.cancelButton);
        Button nextButton = (Button) countryDialog.findViewById(R.id.nextButton);
        final ListView countryList = (ListView) countryDialog.findViewById(R.id.countryList);
        countryList.setAdapter(countryAdapter);





        countryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView name = (TextView) view.findViewById(R.id.title);
                //ImageView i=(ImageView)view.findViewById(R.id.ee);
                // selected item

               /* c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {*/
                Profile_CountryModel m = countryPopulateData.get(position);
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




                for(int y=0;y<countryPopulateData.size();y++)
                {
                    countryPopulateData.get(y).setSelected(false);
                }


                countryPopulateData.get(position).setSelected(true);
                 countryName=name.getText().toString();
countryAdapter.notifyDataSetChanged();
            }

        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*finalLangListId.clear();
                for (Map.Entry<Integer, String> map : listLangSel.entrySet()) {

                    finalLangListId.add(map.getValue());
                    System.out.println("LIST" + finalLangListId);

                }
                String listString = "";*/

                /*for (String s : finalListId)
                {
                    listString +=s;
                }*/
                /*String array[] = new String[finalListId.size()];
                for(int j =0;j<finalListId.size();j++){
                    array[j] = finalListId.get(j);
                }
                for (String s : array)
                {
                    listString +=s + "\t";
                    System.out.println(s);
                }

                System.out.println("lisdt"+listString);
                language.setText(listString);*/
               /* StringBuilder builder = new StringBuilder();
                for (String details : finalLangListId) {
                    builder.append(details + "\n");
                }*/
                country.setText(countryName);
                countryDialog.dismiss();
            }
        });




        countryDialog.show();
        countryDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }















    public class GetUpdateProfileCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {


                profilePictureTextView.requestFocus();
                    JSONObject mMembersJSON = new JSONObject(response);
                    if (mMembersJSON.has("ERROR")) {
                        Snackbar.make(findViewById(android.R.id.content), "Error Occured .. Please try again later", Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .show();
                    } else {
                       /* Snackbar.make(findViewById(android.R.id.content), mMembersJSON.getJSONObject("OUTPUT").getString("MESSAGE"), Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .show();*/
                        loginTxt=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("LOGIN");
                        if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("NAME").equals(null)||mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("NAME").equals(""))
                        {

                        }
                        else
                        {
                            inputName.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("NAME"));

                        }

                        if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_MOBILE").equals(null)||
                                mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_MOBILE").equals(""))
                        {

                        }
                        else
                        {
                            inputMobileNumber.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_MOBILE"));

                        }

                        if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("EMAIL").equals(null)||
                                mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("EMAIL").equals(""))
                        {

                        }
                        else
                        {
                            inputEmail.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("EMAIL"));

                        }







                        if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_GENDER").equals(null)||
                                mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_GENDER").equals(""))
                        {

                        }
                        else
                        {
                            if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_GENDER").equalsIgnoreCase("M")) {
                             //   inputGender.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_GENDER"));
                                inputGender.setText("Male");
                            }
                            else
                            {
                                inputGender.setText("Female");
                            }

                        }


                        if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_BIRTHDAY").equals(null)||
                                mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_BIRTHDAY").equals(""))
                        {

                        }
                        else
                        {
                            inputDateOfBirth.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_BIRTHDAY"));

                        }




                        System.out.println("bdhmgmdcv" + mMembersJSON);
                        int lengthList = mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("INTEREST_LIST").length();
                        int personalNotesArraySize=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_NOTES").length();
                        if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_NOTES").length()>0) {
                            interestTxt.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_NOTES").length() + " Selected");

                        }
                        for (int i = 0; i < lengthList; i++) {
                            Interest_Model im=new Interest_Model();
                            JSONObject data = mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("INTEREST_LIST").getJSONObject(i);
                            listData.add(data.getString("VALUE"));
                            listIdData.put(data.getString("ID"), data.getString("VALUE"));
                            im.setName(data.getString("VALUE"));
                            im.setId(data.getInt("ID"));
                            if(data.getBoolean("SELECTED"))
                            {
                                im.setSelected(true);
                                listSel.put(i, data.getString("ID"));
                                System.out.println("dvbfjhgxjfgxjhfvb" + listSel);
                                finalListId.add(data.getString("ID"));
                            }
                            else {
                                im.setSelected(false);
                            }



                   /*     if(personalNotesArraySize>0)
                        {
                            for(int l=0;l<personalNotesArraySize;l++)
                            {
                                String valueInterest=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_NOTES").getString(l);
                                System.out.println("dgvbjdfkd"+valueInterest);

                                if (data.getString("ID").equals(valueInterest))
                                {
                                    im.setSelected(true);
                                }
                                else
                                {
                                    im.setSelected(false);
                                }
                            }
                        }*/



                            interestData.add(im);
                        }
                 /*   int personalNotesArraySize=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_NOTES").length();

                    if(personalNotesArraySize>0)
                    {
                        for(int l=0;l<personalNotesArraySize;l++)
                        {
                            String valueInterest=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_NOTES").getString(l);
                            for(int m=0;m<interestData.size();m++)
                            {
                                if(interestData.get(m).contains(valueInterest))
                                {
                                    listData.set(interestData).set
                                }
                            }
                        }
                    }*/

/*
                        String photoUrl=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_PHOTO").getString("src");

                        Picasso.with(EditProfileActivity.this).load("http://"+photoUrl).transform(new CircleTransformation()).into(profileImage);*/


                        if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_PHOTO").length()>0) {
                            String photoUrl=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_PHOTO").getString("SRC");
                            Picasso.with(EditProfileActivity.this).load("http://" + photoUrl).transform(new CircleTransformation()).into(profileImage);
                        }

                        String countryName=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_STATE");
                        int lengthCountry = mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("COUNTRY_LIST").length();

                        if(!mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_STATE").equals(""))
                        {
                            country.setText(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getString("PERSONAL_STATE"));
                        }
                        for (int j = 0; j < lengthCountry; j++) {
                            //JSONObject dataCountry=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("INETEREST_LIST").getJSONObject(j);
                            listCountry.add(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("COUNTRY_LIST").getString(j));
                            Profile_CountryModel countryData=new Profile_CountryModel();
                            if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("COUNTRY_LIST").getString(j).equals(countryName))
                            {
                                countryData.setSelected(true);
                            }
                            else {
                                countryData.setSelected(false);
                            }
                            countryData.setName(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("COUNTRY_LIST").getString(j));


                            countryPopulateData.add(countryData);


                        }


                        int lengthLanguage = mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("LANGUAGE_LIST").length();



                        ArrayList<String> dataMainCountry=new ArrayList<>();
                        if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_PAGER").length()>0) {
                            for(int z=0;z<mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_PAGER").length();z++)
                            {

                                dataMainCountry.add(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_PAGER").getString(z));
                            }
                            StringBuilder builderMain = new StringBuilder();
                            for (String details : dataMainCountry) {
                                if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONArray("PERSONAL_PAGER").length()>1) {
                                    builderMain.append(details + " ,");
                                }
                                else
                                {
                                    builderMain.append(details);
                                }
                            }
                            language.setText(builderMain.toString());
                        }











                        for (int k = 0; k < lengthLanguage; k++) {
                            //JSONObject dataCountry=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("INETEREST_LIST").getJSONObject(j);
                            listLanguage.add(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("LANGUAGE_LIST").getString(k));
                            Interest_LanguageModel langDataList=new Interest_LanguageModel();
                            JSONObject data = mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PROFILE_DATA").getJSONArray("LANGUAGE_LIST").getJSONObject(k);
                        /*listData.add(data.getString("VALUE"));
                        listIdData.put(data.getString("ID"), data.getString("VALUE"));*/
                            langDataList.setName(data.getString("VALUE"));
//                        langDataList.setId(data.getInt("ID"));
                            if(data.getBoolean("SELECTED"))
                            {
                                langDataList.setSelected(true);
                                listLangSel.put(k, data.getString("VALUE"));
                                System.out.println("dvbfjhgxjfgxjhfvb" + listLangSel);
                                finalLangListId.add(data.getString("VALUE"));
                            }
                            else {
                                langDataList.setSelected(false);
                            }





                            interestLangData.add(langDataList);
                        }


                        if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_BIRTHDAY_FORMATED").getString("DAY").equals("")||
                                mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_BIRTHDAY_FORMATED").getString("MONTH").equals("")||
                                mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_BIRTHDAY_FORMATED").getString("YEAR").equals(""))
                        {
                            year_x = currentDate.get(Calendar.YEAR);
                            month_x = currentDate.get(Calendar.MONTH);
                            day_x = currentDate.get(Calendar.DAY_OF_MONTH);
                        }
                        else
                        {
                            currentDate.set(Calendar.MONTH, mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_BIRTHDAY_FORMATED").getInt("MONTH")); // Months are 0-based!
                            currentDate.set(Calendar.DAY_OF_MONTH, mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_BIRTHDAY_FORMATED").getInt("MONTH")); // Clearer than DATE
                            currentDate.set(Calendar.YEAR, mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_BIRTHDAY_FORMATED").getInt("YEAR"));

                            year_x = currentDate.get(Calendar.YEAR);
                            month_x = currentDate.get(Calendar.MONTH);
                            day_x = currentDate.get(Calendar.DAY_OF_MONTH);
                        }


                        //final Calendar cal = Calendar.getInstance();




                        System.out.println("list" + listData);
                        System.out.println("lisDATA" + listIdData);
                        System.out.println("country" + listCountry);
                        System.out.println("language" + listLanguage);


                    }
                progressBar.setVisibility(View.GONE);
                profilePictureTextView.requestFocus();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onTaskCancelled() {
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
        }
    }






    public class GetUpdateProfilePictureCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {

                profilePictureTextView.requestFocus();
                JSONObject mMembersJSON = new JSONObject(response);
                if (mMembersJSON.has("ERROR")) {
                    Snackbar.make(findViewById(android.R.id.content), "Error Occured .. Please try again later", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                } else {
                    /*Snackbar.make(findViewById(android.R.id.content), mMembersJSON.getJSONObject("OUTPUT").getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();*/
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.putString(Constants.USER_PROFILE_PIC, mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("DETAILS").getJSONObject("PERSONAL_PHOTO").getString("SRC"));

                    editor.commit();
                }


                progressBar.setVisibility(View.GONE);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onTaskCancelled() {
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
        }
    }






}