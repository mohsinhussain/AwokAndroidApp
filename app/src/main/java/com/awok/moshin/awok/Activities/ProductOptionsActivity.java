package com.awok.moshin.awok.Activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.Adapters.ColorSpecGridAdapter;
import com.awok.moshin.awok.Adapters.SizeSpecGridAdapter;
import com.awok.moshin.awok.Adapters.StorageSpecGridAdapter;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.ColorSpec;
import com.awok.moshin.awok.Models.ShippingMethod;
import com.awok.moshin.awok.Models.SizeSpec;
import com.awok.moshin.awok.Models.StorageSpec;
import com.awok.moshin.awok.Models.Variant;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.awok.moshin.awok.Util.ExpandableHeightGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductOptionsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    public static final String AVATAR_URL = "http://lorempixel.com/200/200/people/1/";
    private ImageView profileImage;
    String productId;
    ArrayList<ColorSpec> colorArray = new ArrayList<ColorSpec>();
    ArrayList<StorageSpec> storageArray = new ArrayList<StorageSpec>();
    ArrayList<SizeSpec> sizeArray = new ArrayList<SizeSpec>();
    ArrayList<ShippingMethod> shippingMethodArray = new ArrayList<ShippingMethod>();
    LinearLayout progressLayout;
    ExpandableHeightGridView storageRecyclerView;
    ExpandableHeightGridView sizeRecyclerView;
    ExpandableHeightGridView colorRecyclerView;
    LinearLayout colorLayout, storageLayout, sizeLayout;
    ColorSpecGridAdapter colorAdapter;
    StorageSpecGridAdapter storageAdapter;
    SizeSpecGridAdapter sizeAdapter;
    EditText quantity;
    Button increment, decrement;
    private TextView inputNumberErrorTextView;
    ProgressBar progressBarForm;
    String colorString = "";
    String storageString = "";
    String sizeString = "";
    TextView costTextView, shippingHeaderTextView, stockQuantityTextView;
    ArrayList<Variant> variantArray = new ArrayList<Variant>();
    StaggeredGridLayoutManager colorLayoutManager, storageLayoutManager, sizeLayoutManager;
    private String valueQuantity;
    private int stockQuantity;
    private String uid,ssid,sku;
    String variantId = "";
    Button checkOutButton;
    RelativeLayout shippingMethodSelectionLayout;
    TextView errorTextView;
    SharedPreferences mSharedPrefs;
    private ImageView countryImage;
TextView loginErrorTextView,registrationErrorTextView;
    String imageIco;
    int imageSource;
    String code="";
    String phoneNumber="";
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    String savedMethodName = "";
    String savedMethodProfileId = "";
    JSONObject dataToSend;
    Dialog inputNumberDialog;
    String userId="";
    Snackbar toast;
    ImageView productImageView;
    TextView productNameTextView, priceTexView, colorTextView, sizeTextView;
    JSONArray variantsArray;
    JSONObject specsObject;
    String emailId="";
    String password="";
    Dialog shippingDialog;
    Dialog loginDialog;
    private String sendValue="";
EditText passwordEditText;
    CountDownTimer timerLoop;
    private CountDownTimer countDownTimer;
    Dialog verifyDialog;
    EditText emailEditText;
    Dialog registerDialog;
    private JSONObject variantData;
    View topQuantityBorder;
    TextView colorLabelTextView, countryNameTextView;
    String shippingResponse = "";
    TextView outOfStockMessageTextView;
    LinearLayout quantityCountLayout;
    ProgressBar progressBarLogin;
    private  long startTime=0;
    private String valueValidation="";

    private final long interval = 1 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_product_options);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        if ((mSharedPrefs.contains(Constants.USER_ID_PREFS)))
        {
            userId = mSharedPrefs.getString(Constants.USER_ID_PREFS, null);
        }
        checkOutButton = (Button) findViewById(R.id.checkOutButton);
        progressBar = (ProgressBar) findViewById(R.id.marker_progress);
        progressLayout = (LinearLayout) findViewById(R.id.progressLayout);
        colorLayout = (LinearLayout) findViewById(R.id.colorLayout);
        storageLayout = (LinearLayout) findViewById(R.id.storageLayout);
        sizeLayout = (LinearLayout) findViewById(R.id.sizeLayout);
        colorRecyclerView = (ExpandableHeightGridView) findViewById(R.id.colorRecyclerView);
        storageRecyclerView = (ExpandableHeightGridView) findViewById(R.id.storageRecyclerView);
        sizeRecyclerView = (ExpandableHeightGridView) findViewById(R.id.sizeRecyclerView);
        progressLayout.setVisibility(View.GONE);
        quantity = (EditText) findViewById(R.id.quantity);
        profileImage = (ImageView)findViewById(R.id.avatar);
        costTextView = (TextView)findViewById(R.id.costTextView);
        shippingHeaderTextView = (TextView)findViewById(R.id.shippingMethodHeaderTextView);
        stockQuantityTextView = (TextView)findViewById(R.id.stockQuantityTextView);
        outOfStockMessageTextView = (TextView)findViewById(R.id.outOfStockTextView);
        increment = (Button) findViewById(R.id.quantity_inc);
        decrement = (Button) findViewById(R.id.quantity_dec);
        shippingMethodSelectionLayout = (RelativeLayout) findViewById(R.id.shippingMethodSelectionLayout);
        errorTextView = (TextView) findViewById(R.id.errorTextView);
        countryImage=(ImageView)findViewById(R.id.country_image);
        topQuantityBorder = (View) findViewById(R.id.topQuantityBorder);
        productImageView=(ImageView)findViewById(R.id.imagePhotoView);
        productNameTextView = (TextView) findViewById(R.id.nameTextView);
        quantityCountLayout = (LinearLayout) findViewById(R.id.quantity_count);
        colorTextView = (TextView) findViewById(R.id.selectedColorTextView);
        sizeTextView = (TextView) findViewById(R.id.selectedSizeTextView);
        colorLabelTextView = (TextView) findViewById(R.id.colorTextView);
        priceTexView = (TextView) findViewById(R.id.priceTextView);
        countryNameTextView = (TextView) findViewById(R.id.countryNameTextView);
        //mRecyclerView.setAdapter(mAdapter);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);





//        colorRecyclerView.setHasFixedSize(false);
//        sizeRecyclerView.setHasFixedSize(false);
//        storageRecyclerView.setHasFixedSize(false);
//
//        colorLayoutManager = new StaggeredGridLayoutManager(3,  1);
//        colorRecyclerView.setLayoutManager(colorLayoutManager);
//        colorLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
//
//        storageLayoutManager = new StaggeredGridLayoutManager(3,  1);
//        storageRecyclerView.setLayoutManager(storageLayoutManager);
//        storageLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
//
//        sizeLayoutManager = new StaggeredGridLayoutManager(3,  1);
//        sizeRecyclerView.setLayoutManager(sizeLayoutManager);
//        sizeLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);


        quantity.setText("1");

        quantity.clearFocus();


        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
         ab.setHomeAsUpIndicator(R.drawable.back_button);
//        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Product Options");



        productId =getIntent().getExtras().getString(Constants.PRODUCT_ID_INTENT);
        variantId=productId;
        productNameTextView.setText(getIntent().getExtras().getString(Constants.PRODUCT_NAME_INTENT));
        priceTexView.setText(getIntent().getExtras().getString(Constants.PRODUCT_PRICE_NEW_INTENT));
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        String imageURL = getIntent().getExtras().getString(Constants.PRODUCT_IMAGE_INTENT);
        imageLoader.get("http://"+imageURL, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                productImageView.setImageDrawable(getResources().getDrawable(R.drawable.default_img));
//                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    productImageView.setImageBitmap(response.getBitmap());
//                    progressBar.setVisibility(View.GONE);
                }
            }
        });
ssid=getIntent().getExtras().getString("SSID");
        uid=getIntent().getExtras().getString("UID");
        sku=getIntent().getExtras().getString("SKU");





        try {
            //null pointer exception
            if(!getIntent().getExtras().getString(Constants.PRODUCT_SPECS_INTENT).equalsIgnoreCase("")){
                specsObject = new JSONObject(getIntent().getExtras().getString(Constants.PRODUCT_SPECS_INTENT));
            }
            else{
                specsObject = new JSONObject();
            }
            specsObject = new JSONObject(getIntent().getExtras().getString(Constants.PRODUCT_SPECS_INTENT));
///            variantsArray = new JSONArray(getIntent().getExtras().getString(Constants.PRODUCT_VARIANTS_INTENT));

                if(specsObject.has("COLORS")){
                    for(int i=0;i<specsObject.getJSONArray("COLORS").length();i++){
                        if(specsObject.getJSONArray("COLORS").getJSONObject(i).has("PICTURE")) {
                            colorArray.add(new ColorSpec(specsObject.getJSONArray("COLORS").getJSONObject(i).getString("NAME"),
                                    specsObject.getJSONArray("COLORS").getJSONObject(i).getString("PICTURE"), false));
                        }
                        else
                        {
                            colorArray.add(new ColorSpec(specsObject.getJSONArray("COLORS").getJSONObject(i).getString("NAME"),
                                    getIntent().getExtras().getString(Constants.PRODUCT_IMAGE_INTENT), true));
                        }
                    }
                }


                if(specsObject.has("STORAGES")){
                    for(int i=0;i<specsObject.getJSONArray("STORAGES").length();i++){
                        if(specsObject.getJSONArray("STORAGES").getJSONObject(i).has("STOCK")) {
                            storageArray.add(new StorageSpec(specsObject.getJSONArray("STORAGES").getJSONObject(i).getString("NAME"), false));
                        }
                        else
                        {
                            storageArray.add(new StorageSpec(specsObject.getJSONArray("STORAGES").getJSONObject(i).getString("NAME"), true));
                        }
                    }
                }

variantData=new JSONObject(getIntent().getExtras().getString(Constants.PRODUCT_VARIANTS_INTENT));
          /*     for(int i=0;i<variantsArray.length();i++){
                    Variant variant = (new Variant(variantsArray.getJSONObject(i).getJSONObject("_id").getString("$id"),
                            variantsArray.getJSONObject(i).getJSONObject("specs").optString("size"), variantsArray.getJSONObject(i).getInt("quantity"),
                            variantsArray.getJSONObject(i).getJSONObject("pricing").getInt("discount_price")));
                    if(variantsArray.getJSONObject(i).getJSONObject("specs").has("color")){
                        if(variantsArray.getJSONObject(i).getJSONObject("specs").getJSONObject("color").has("color")){
                            variant.setColor(variantsArray.getJSONObject(i).getJSONObject("specs").getJSONObject("color").getString("color"));
                        }
                    }
                    variantArray.add(variant);
                }*/
populateSpecs();
         /*   if(variantArray.size()>1){
                populateSpecs();
            }
           else{
                colorLayout.setVisibility(View.GONE);
                sizeLayout.setVisibility(View.GONE);
                storageLayout.setVisibility(View.GONE);
                if(colorArray.size()>0){
                    colorTextView.setText(colorArray.get(0).getColor());
                }
                else{
                    colorTextView.setVisibility(View.GONE);
                    colorLabelTextView.setVisibility(View.GONE);
                }


                if(storageArray.size()>0){
                    sizeTextView.setText(colorArray.get(0).getColor());
                }
                else{
                    sizeTextView.setVisibility(View.GONE);
                }
                variantId = variantArray.get(0).getId();
                stockQuantity = variantArray.get(0).getStock();
                stockQuantityTextView.setVisibility(View.VISIBLE);
                stockQuantityTextView.setText(stockQuantity + " items left in stock");
            }*/


        } catch (JSONException e) {
            e.printStackTrace();
        }


        shippingMethodSelectionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(outOfStockMessageTextView.getVisibility()==View.GONE) {
                    Intent i = new Intent(ProductOptionsActivity.this, ShippingMethodActivity.class);
                    i.putExtra(Constants.PRODUCT_ID_INTENT, productId);
                    i.putExtra(Constants.QUANTITY_INTENT, quantity.getText().toString());
                    i.putExtra(Constants.STOCK_INTENT, stockQuantity);
                    i.putExtra(Constants.VARIANTID_INTENT, variantId);
                    i.putExtra(Constants.SELECTED_METHOD_INTENT, savedMethodName);
                    i.putExtra(Constants.SHIPPING_RESPONSE_INTENT, shippingResponse);
                    startActivityForResult(i, 1);
                }
            }
        });

        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("VALID"+variantId);
                System.out.println("savedMethodProfileId" + savedMethodProfileId);
                System.out.println("errorTextView"+errorTextView.getText().toString());
checkUser();
             /*   if(!variantId.equalsIgnoreCase("") && !savedMethodProfileId.equalsIgnoreCase("") && errorTextView.getText().toString().equalsIgnoreCase("")){
                    checkOutButton.setEnabled(false);
                    checkOutButton.setBackgroundColor(getResources().getColor(R.color.border));
                    checkOutButton.setTextColor(getResources().getColor(R.color.button_text));
                    HashMap<String,Object> addToCartData=new HashMap<String, Object>();
                    //55f6a9462f17f64a9b5f5ce4
                    addToCartData.put("USER_ID", userId);
                   // addToCartData.put("product_id", productId);
                    //addToCartData.put("shipping_profile_id", savedMethodProfileId);
                    //addToCartData.put("variant_id",variantId);
                    //addToCartData.put("quantity", Integer.parseInt(quantity.getText().toString()));
                    addToCartData.put("SKU",sku);
                    addToCartData.put("UID",uid);
                    addToCartData.put("SSID",ssid);
                    System.out.println("HashMap: "+addToCartData);
                    dataToSend=new JSONObject(addToCartData);
                    System.out.println(dataToSend.toString());
                    new APIClient(ProductOptionsActivity.this, getApplicationContext(),  new GetAddToCartCallBack()).addToCartAPICall(userId,sku,uid,ssid);
                }
                else{
                    Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Please select valid product option", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED);

                    View snackbarView = snackbar.getView();

                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();
                }*/
            }
        });

//        connMgr = (ConnectivityManager)
//                getSystemService(Context.CONNECTIVITY_SERVICE);
//        networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this,  new GetProductSpecsCallBack()).ProductSpecsAPICall(productId);
//        } else {
//            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
//                    .setActionTextColor(Color.RED)
//                    .show();
//        }

        connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String locationId = "4001";
            new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                    variantId);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                    .setActionTextColor(Color.RED)
                    .show();
        }


        setQuantityListeners();


        colorRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(quantity.getText().equals("0"))
                {
                    quantity.setText("1");
                }
                for (int i = 0; i < colorArray.size(); i++) {
                    colorArray.get(i).setIsSelected(false);
                }
                colorArray.get(position).setIsSelected(true);
                colorAdapter = new ColorSpecGridAdapter(ProductOptionsActivity.this, colorArray);

                colorRecyclerView.setAdapter(colorAdapter);
                colorRecyclerView.setExpanded(true);

                colorString = colorArray.get(position).getColor();
                colorTextView.setText(colorString);
                ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                String ImgUrl = colorArray.get(position).getImageUrl();
                imageLoader.get("http://"+ImgUrl, new ImageLoader.ImageListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        productImageView.setImageDrawable(getResources().getDrawable(R.drawable.default_img));
//                progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                        if (response.getBitmap() != null) {
                            // load image into imageview
                            productImageView.setImageBitmap(response.getBitmap());
//                    progressBar.setVisibility(View.GONE);
                        }
                    }
                });
                boolean isMatching = false;
                if(storageArray.size()>0) {
                    if (!colorString.equalsIgnoreCase("") && !storageString.equalsIgnoreCase("")) {
                        //call shipping method API
                        System.out.println("Call shipping method API");


                     //   for (int i = 0; i < variantArray.size(); i++) {
//////////////////////////////////////////////////////******
                        //    if (variantArray.get(i).getColor().equalsIgnoreCase(colorString) && variantArray.get(i).getStorage().equalsIgnoreCase(storageString)) {
                       // if (variantData.optJSONObject(colorString+"-"+storageString).toString().equalsIgnoreCase(colorString+"-"+storageString)) {
                        if(variantData.has(colorString+"-"+storageString)) {
                            JSONObject dataImp=variantData.optJSONObject(colorString+"-"+storageString);
                            isMatching = true;
                            try {
                                variantId = dataImp.getString("ID");
                                stockQuantity=10;
                                JSONObject cart_id=dataImp.getJSONObject("CART_DATA");
                                sku=cart_id.getString("SKU");
                                uid=cart_id.getString("UID");
                                ssid=cart_id.getString("SSID");
                                //stockQuantity = variantArray.get(i).getStock();
                                //stockQuantity=0;
                                //stockQuantityTextView.setVisibility(View.VISIBLE);
                                //stockQuantityTextView.setText(stockQuantity + " items left in stock");
                                priceTexView.setText("AED " + dataImp.getString("PRICE"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (Integer.parseInt(quantity.getText().toString()) > stockQuantity) {
                                //null pointer exception
                                quantity.setText(String.valueOf(stockQuantity));
                                if (networkInfo != null && networkInfo.isConnected()) {
                                    String locationId = "4001";
                                    new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                            variantId);
                                } else {
                                    Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                            .setActionTextColor(Color.RED)
                                            .show();
                                }
                            }
                        }
                       // }
                          //  }
                  //      }
//                        else{
//                            isMatching = true;
//                            if(variantArray.get(i).getColor().equalsIgnoreCase(colorString)){
//                                variantId = variantArray.get(i).getId();
//                                stockQuantity = variantArray.get(i).getStock();
//                                stockQuantityTextView.setText(stockQuantity+" items left in stock");
//                                priceTexView.setText("AED " + variantArray.get(i).getPrice());
//                                if(Integer.parseInt(quantity.getText().toString())>stockQuantity){
//                                    quantity.setText(stockQuantity);
//                                    if (networkInfo != null && networkInfo.isConnected()) {
//                                        String locationId = "560a8eddf26f2e024b8b4690";
//                                        new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
//                                                variantId);
//                                    } else {
//                                        Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
//                                                .setActionTextColor(Color.RED)
//                                                .show();
//                                    }
//                                }
//                            }
//                        }

                    }

                    if (isMatching) {
                        outOfStockMessageTextView.setVisibility(View.GONE);
                        quantityCountLayout.setVisibility(View.VISIBLE);
                        ConnectivityManager connMgr = (ConnectivityManager)
                                getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            String locationId = "4001";
                            new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                    variantId);
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }
                    }
                    else if(!storageString.equalsIgnoreCase("")) {
                        Toast.makeText(getApplicationContext(), "OUT OF STOCK", Toast.LENGTH_SHORT).show();
                        outOfStockMessageTextView.setVisibility(View.VISIBLE);
                        quantityCountLayout.setVisibility(View.GONE);
                        variantId = "";
                        stockQuantityTextView.setVisibility(View.GONE);
                        stockQuantityTextView.setText("");
                        stockQuantity = 0;
                    }
                }
          /*      else{
                    if (!colorString.equalsIgnoreCase("")) {
                        //call shipping method API
                        System.out.println("Call shipping method API");


                        for (int i = 0; i < variantArray.size(); i++) {

                            if (variantArray.get(i).getColor().equalsIgnoreCase(colorString)) {
                                isMatching = true;
                                variantId = variantArray.get(i).getId();
                                stockQuantity = variantArray.get(i).getStock();
                                stockQuantityTextView.setVisibility(View.VISIBLE);
                                stockQuantityTextView.setText(stockQuantity + " items left in stock");
                                priceTexView.setText("AED " + variantArray.get(i).getPrice());
                                if (Integer.parseInt(quantity.getText().toString()) > stockQuantity) {
                                    quantity.setText(stockQuantity);
                                    if (networkInfo != null && networkInfo.isConnected()) {
                                        String locationId = "4001";
                                        new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                                variantId);
                                    } else {
                                        Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                                .setActionTextColor(Color.RED)
                                                .show();
                                    }
                                }
                            }
                        }
                    }

                    if (isMatching) {
                        outOfStockMessageTextView.setVisibility(View.GONE);
                        quantityCountLayout.setVisibility(View.VISIBLE);
                        ConnectivityManager connMgr = (ConnectivityManager)
                                getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            String locationId = "4001";
                            new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                    variantId);
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "OUT OF STOCK", Toast.LENGTH_SHORT).show();
                        outOfStockMessageTextView.setVisibility(View.VISIBLE);
                        quantityCountLayout.setVisibility(View.GONE);
                        variantId = "";
                        stockQuantity = 0;
                        stockQuantityTextView.setVisibility(View.GONE);
                        stockQuantityTextView.setText("");
                    }
                }*/
                    //locationId  = 560a8eddf26f2e024b8b4690
            }
        });


        storageRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(quantity.getText().equals("0"))
                {
                    quantity.setText("1");
                }
                for(int i=0;i<storageArray.size();i++){
                    storageArray.get(i).setIsSelected(false);
                }
                storageArray.get(position).setIsSelected(true);
                storageRecyclerView.setAdapter(storageAdapter);
                storageRecyclerView.setExpanded(true);

                storageString = storageArray.get(position).getColor();
                sizeTextView.setText(storageString);
                if(colorArray.size()>0) {
                    if (!colorString.equalsIgnoreCase("") && !storageString.equalsIgnoreCase("")) {
                        boolean isMatching = false;


                        if(variantData.has(colorString+"-"+storageString))
                        {
                                    JSONObject dataImp=variantData.optJSONObject(colorString+"-"+storageString);
                                    isMatching = true;
                                    try {
                                        variantId = dataImp.getString("ID");
                                        stockQuantity=10;
                                        JSONObject cart_id=dataImp.getJSONObject("CART_DATA");
                                        sku=cart_id.getString("SKU");
                                        uid=cart_id.getString("UID");
                                        ssid=cart_id.getString("SSID");
                                        //stockQuantity = variantArray.get(i).getStock();
                                        //stockQuantity=0;
                                        //stockQuantityTextView.setVisibility(View.VISIBLE);
                                        //stockQuantityTextView.setText(stockQuantity + " items left in stock");
                                        priceTexView.setText("AED " + dataImp.getString("PRICE"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if (Integer.parseInt(quantity.getText().toString()) > stockQuantity) {
                                        quantity.setText(Integer.toString(stockQuantity));
                                        if (networkInfo != null && networkInfo.isConnected()) {
                                            String locationId = "4001";
                                            new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                                    variantId);
                                        } else {
                                            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                                    .setActionTextColor(Color.RED)
                                                    .show();
                                        }
                                    }
                                }
                    //    }

                        if (isMatching) {
                            outOfStockMessageTextView.setVisibility(View.GONE);
                            quantityCountLayout.setVisibility(View.VISIBLE);
                            ConnectivityManager connMgr = (ConnectivityManager)
                                    getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                            if (networkInfo != null && networkInfo.isConnected()) {
                                String locationId = "4001";
                                new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                        variantId);
                            } else {
                                Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                        .setActionTextColor(Color.RED)
                                        .show();
                            }
                        }
                        else if(!colorString.equalsIgnoreCase("")) {
                            Toast.makeText(getApplicationContext(), "OUT OF STOCK", Toast.LENGTH_SHORT).show();
                            outOfStockMessageTextView.setVisibility(View.VISIBLE);
                            quantityCountLayout.setVisibility(View.GONE);
                            variantId = "";
                            stockQuantityTextView.setVisibility(View.GONE);
                            stockQuantityTextView.setText("");
                            stockQuantity = 0;
                        }
                    }
                }
                /*else{
                    if (!storageString.equalsIgnoreCase("")) {
                        boolean isMatching = false;


                        for (int i = 0; i < variantArray.size(); i++) {
                            if (variantArray.get(i).getStorage().equalsIgnoreCase(storageString)) {
                                isMatching = true;
                                variantId = variantArray.get(i).getId();
                                stockQuantity = variantArray.get(i).getStock();
                                stockQuantityTextView.setVisibility(View.VISIBLE);
                                stockQuantityTextView.setText(stockQuantity + " items left in stock");
                                priceTexView.setText("AED " + variantArray.get(i).getPrice());
                                if (Integer.parseInt(quantity.getText().toString()) > stockQuantity) {
                                    quantity.setText(Integer.toString(stockQuantity));
                                    if (networkInfo != null && networkInfo.isConnected()) {
                                        String locationId = "4001";
                                        new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                                variantId);
                                    } else {
                                        Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                                .setActionTextColor(Color.RED)
                                                .show();
                                    }
                                }
                            }
                        }

                        if (isMatching) {
                            outOfStockMessageTextView.setVisibility(View.GONE);
                            quantityCountLayout.setVisibility(View.VISIBLE);
                            ConnectivityManager connMgr = (ConnectivityManager)
                                    getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                            if (networkInfo != null && networkInfo.isConnected()) {
                                String locationId = "4001";
                                new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                        variantId);
                            } else {
                                Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                        .setActionTextColor(Color.RED)
                                        .show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "OUT OF STOCK", Toast.LENGTH_SHORT).show();
                            outOfStockMessageTextView.setVisibility(View.VISIBLE);
                            quantityCountLayout.setVisibility(View.GONE);
                            variantId = "";
                            stockQuantityTextView.setVisibility(View.GONE);
                            stockQuantityTextView.setText("");
                            stockQuantity = 0;
                        }
                    }
                }*/
                }
        });


        sizeRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i=0;i<sizeArray.size();i++){
                    sizeArray.get(i).setIsSelected(false);
                }
                sizeArray.get(position).setIsSelected(true);
                sizeRecyclerView.setAdapter(sizeAdapter);
                sizeRecyclerView.setExpanded(true);
            }
        });

    }

    private void checkUser() {
        if(!variantId.equalsIgnoreCase("") && !savedMethodProfileId.equalsIgnoreCase("") && errorTextView.getText().toString().equalsIgnoreCase("")){
            checkOutButton.setEnabled(false);
            checkOutButton.setBackgroundColor(getResources().getColor(R.color.border));
            checkOutButton.setTextColor(getResources().getColor(R.color.button_text));
            verify();
            /*HashMap<String,Object> addToCartData=new HashMap<String, Object>();
            //55f6a9462f17f64a9b5f5ce4
            addToCartData.put("USER_ID", userId);
            // addToCartData.put("product_id", productId);
            //addToCartData.put("shipping_profile_id", savedMethodProfileId);
            //addToCartData.put("variant_id",variantId);
            //addToCartData.put("quantity", Integer.parseInt(quantity.getText().toString()));
            addToCartData.put("SKU",sku);
            addToCartData.put("UID",uid);
            addToCartData.put("SSID",ssid);
            System.out.println("HashMap: "+addToCartData);
            dataToSend=new JSONObject(addToCartData);
            System.out.println(dataToSend.toString());
            new APIClient(ProductOptionsActivity.this, getApplicationContext(),  new GetAddToCartCallBack()).addToCartAPICall(userId,sku,uid,ssid);*/
        }
        else{
            Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Please select valid product option", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }

    private void verify() {




        if (mSharedPrefs.contains(Constants.USER_ID_PREFS)) {
            initializeCart();
        } else {
            //show login dialog
            inputNumberDialog = new Dialog(this, R.style.AppCompatAlertDialogStyle);
            inputNumberDialog.setCancelable(true);
            inputNumberDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            inputNumberDialog.setContentView(R.layout.dialog_input_number);
            inputNumberErrorTextView = (TextView)inputNumberDialog.findViewById(R.id.errorMessageTextView);
             emailEditText = (EditText) inputNumberDialog.findViewById(R.id.emailEditText);
            Button cancelButton = (Button) inputNumberDialog.findViewById(R.id.cancelButton);
            Button nextButton = (Button) inputNumberDialog.findViewById(R.id.nextButton);
            final Button countryCodeButton = (Button) inputNumberDialog.findViewById(R.id.countryCodeButton);
            progressBarForm = (ProgressBar) inputNumberDialog.findViewById(R.id.load_progress_bar);
            // if button is clicked, close the custom dialog

            //populateCountryCodes();
            countryCodeButton.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public void onClick(View v) {
                    //show login dialog
                    final Dialog countryCodeDialog = new Dialog(ProductOptionsActivity.this, R.style.AppCompatAlertDialogStyle);
                    countryCodeDialog.setCancelable(true);
                    countryCodeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    countryCodeDialog.setContentView(R.layout.dialog_country_code);
                    final ListView mList = (ListView)countryCodeDialog.findViewById(R.id.codeListView);

                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            ProductOptionsActivity.this,
                            android.R.layout.simple_list_item_1);
                  //  arrayAdapter.addAll(countryCodes);
                   /* mList.setAdapter(arrayAdapter);
                    mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            countryCodeButton.setText(countryCodeNumbers.get(position));
                            countryCodeDialog.cancel();
                        }
                    });*/

                    countryCodeDialog.show();
                    countryCodeDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inputNumberDialog.cancel();
                }
            });
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = getWindow().getLayoutInflater();
                    final View mDialogView = inflater.inflate(R.layout.dialog_input_number, null);
                    if (emailEditText.getText().toString().equalsIgnoreCase("")) {
                        emailEditText.setError("Please enter your mobile number");
                    } else {
                        if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText()).matches()) {
                          //  Log.v(TAG, "phone number is correct");
                            emailId =emailEditText.getText().toString();
                            new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new CheckUserCallback()).userCheckAPICall(emailId);
                        } else {
                            toast.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.RED)
                                    .show();

                            emailEditText.setError("Email Id is incorrect");
                        }

                    }
                }
            });
            inputNumberDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });

            inputNumberDialog.show();
            inputNumberDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }









        /*HashMap<String,Object> addToCartData=new HashMap<String, Object>();
        //55f6a9462f17f64a9b5f5ce4
        addToCartData.put("USER_ID", userId);
        // addToCartData.put("product_id", productId);
        //addToCartData.put("shipping_profile_id", savedMethodProfileId);
        //addToCartData.put("variant_id",variantId);
        //addToCartData.put("quantity", Integer.parseInt(quantity.getText().toString()));
        addToCartData.put("SKU",sku);
        addToCartData.put("UID",uid);
        addToCartData.put("SSID",ssid);
        System.out.println("HashMap: "+addToCartData);
        dataToSend=new JSONObject(addToCartData);
        System.out.println(dataToSend.toString());
        new APIClient(ProductOptionsActivity.this, getApplicationContext(),  new GetAddToCartCallBack()).addToCartAPICall(userId,sku,uid,ssid);*/
    }

    private void initializeCart() {
        HashMap<String,Object> addToCartData=new HashMap<String, Object>();
        //55f6a9462f17f64a9b5f5ce4
        if ((mSharedPrefs.contains(Constants.USER_ID_PREFS)))
        {
            userId = mSharedPrefs.getString(Constants.USER_ID_PREFS, null);
        }
        addToCartData.put("USER_ID", userId);
        // addToCartData.put("product_id", productId);
        //addToCartData.put("shipping_profile_id", savedMethodProfileId);
        //addToCartData.put("variant_id",variantId);
        //addToCartData.put("quantity", Integer.parseInt(quantity.getText().toString()));
        addToCartData.put("SKU",sku);
        addToCartData.put("UID",uid);
        addToCartData.put("SSID",ssid);
        System.out.println("HashMap: "+addToCartData);
        dataToSend=new JSONObject(addToCartData);
        System.out.println(dataToSend.toString());
        new APIClient(ProductOptionsActivity.this, getApplicationContext(),  new GetAddToCartCallBack()).addToCartAPICall(userId,sku,uid,ssid);
    }


    public void setQuantityListeners(){
        quantity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                valueQuantity = quantity.getText().toString();
                return false;
            }
        });
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int addQuantity=Integer.parseInt(quantity.getText().toString());
                addQuantity=addQuantity+1;
                quantity.setText(String.valueOf(addQuantity));

                if (quantity.getText().toString().equals("") || quantity.getText().toString().equals("0")) {
                    quantity.setText("1");
                    if (networkInfo != null && networkInfo.isConnected()) {
                        String locationId = "4001";
                        new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                variantId);
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                .setActionTextColor(Color.RED)
                                .show();
                    }
                } else {
                    if (!variantId.equalsIgnoreCase("") && Integer.parseInt(quantity.getText().toString()) > 10) {
                        System.out.println("OUT OF STOCK");

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                ProductOptionsActivity.this);

                        // set title
                        alertDialogBuilder.setTitle("Out of stock");


                        // set dialog message
                        alertDialogBuilder
                                .setMessage("The Quantity is not available in Stock")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        quantity.setText(Integer.toString(stockQuantity));
//                                        if (networkInfo != null && networkInfo.isConnected()) {
//                                            String locationId = "560a8eddf26f2e024b8b4690";
//                                            new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
//                                                    variantId);
//                                        } else {
//                                            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
//                                                    .setActionTextColor(Color.RED)
//                                                    .show();
//                                        }
//                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    } else {
                        View view = getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        ConnectivityManager connMgr = (ConnectivityManager)
                                getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            String locationId = "4001";
                            new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                    variantId);
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }

                    }
                }
            }

        });


        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int addQuantity=Integer.parseInt(quantity.getText().toString());
                addQuantity=addQuantity-1;
                quantity.setText(String.valueOf(addQuantity));

                if (quantity.getText().toString().equals("") || quantity.getText().toString().equals("0")) {
                    quantity.setText("1");
//                    if (networkInfo != null && networkInfo.isConnected()) {
//                        String locationId = "560a8eddf26f2e024b8b4690";
//                        new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
//                                variantId);
//                    } else {
//                        Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
//                                .setActionTextColor(Color.RED)
//                                .show();
//                    }
                } else {
                    if (!variantId.equalsIgnoreCase("") && Integer.parseInt(quantity.getText().toString()) > 10) {
                        System.out.println("OUT OF STOCK");

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                ProductOptionsActivity.this);

                        // set title
                        alertDialogBuilder.setTitle("Out of stock");


                        // set dialog message
                        alertDialogBuilder
                                .setMessage("The Quantity is not available in Stock")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        quantity.setText(Integer.toString(stockQuantity));
                                        if (networkInfo != null && networkInfo.isConnected()) {
                                            String locationId = "4001";
                                            new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                                    variantId);
                                        } else {
                                            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                                    .setActionTextColor(Color.RED)
                                                    .show();
                                        }
                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    } else {
                        View view = getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        ConnectivityManager connMgr = (ConnectivityManager)
                                getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            String locationId = "4001";
                            new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                    variantId);
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }
                    }
                }
            }

        });


        quantity.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("BEFOR CHANGED");
            }

            @Override

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("TEXT CHANGED");
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("AFTER CHANGED");
            }
        });
        quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (!hasFocus) {
                    if (quantity.getText().toString().equals("") || quantity.getText().toString().equals("0")) {
                        quantity.setText("1");
                        if (networkInfo != null && networkInfo.isConnected()) {
                            String locationId = "4001";
                            new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                    variantId);
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }
                    }
                }
            }
        });

        quantity.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                System.out.println("AFTER CHANGED" + EditorInfo.IME_ACTION_DONE);
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED || actionId == EditorInfo.IME_ACTION_DONE) {

                    if (quantity.getText().toString().equals("") || quantity.getText().toString().equals("0")) {
                        quantity.setText("1");
                        if (networkInfo != null && networkInfo.isConnected()) {
                            String locationId = "4001";
                            new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                    variantId);
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }
                    } else {
                        if (!variantId.equalsIgnoreCase("") && Integer.parseInt(quantity.getText().toString()) > stockQuantity) {
                            System.out.println("OUT OF STOCK");

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    ProductOptionsActivity.this);

                            // set title
                            alertDialogBuilder.setTitle("Out of stock");


                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("The Quantity is not available in Stock")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, close
                                            // current activity
                                            quantity.setText(Integer.toString(stockQuantity));
                                            if (networkInfo != null && networkInfo.isConnected()) {
                                                String locationId = "4001";
                                                new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                                        variantId);
                                            } else {
                                                Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                                        .setActionTextColor(Color.RED)
                                                        .show();
                                            }
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();

//                            quantity.setText(valueQuantity);

                        } else {
                            View view = getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                            ConnectivityManager connMgr = (ConnectivityManager)
                                    getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                            if (networkInfo != null && networkInfo.isConnected()) {
                                String locationId = "4001";
                                new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                        variantId);
                            } else {
                                Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                        .setActionTextColor(Color.RED)
                                        .show();
                            }
                        }
                    }


                    return true;
                }
                return false;
            }


        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == ProductOptionsActivity.this.RESULT_OK){
                savedMethodName=data.getStringExtra("method");
                quantity.setText(data.getStringExtra("count"));
                if(data.getStringExtra("errorMessage").equalsIgnoreCase("")){
                    errorTextView.setVisibility(View.GONE);
                }
                else{
                    errorTextView.setVisibility(View.VISIBLE);
                    errorTextView.setText(data.getStringExtra("errorMessage"));
                }

                for(int i=0; i<shippingMethodArray.size();i++){
                    if(shippingMethodArray.get(i).getName().equalsIgnoreCase(savedMethodName)){
                        savedMethodProfileId = shippingMethodArray.get(i).getProfileId();
                        costTextView.setText("AED " + Double.toString(data.getDoubleExtra("cost", 0.0)));
                        break;
                    }
                }
            }
            if (resultCode == ProductOptionsActivity.this.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult



    @Override
    protected void onResume() {
        super.onResume();

        if(colorArray.size()<=1 && storageArray.size()<=1){
            topQuantityBorder.setVisibility(View.GONE);
        }

//        if (mSharedPrefs.contains(Constants.SHIPPING_METHOD_PREFS)) {
//            boolean isSavedMethodInList = false;
//            if(shippingMethodArray.size()>0) {
//                String savedShippingMethod = mSharedPrefs.getString(Constants.SHIPPING_METHOD_PREFS, null);
//                for (int i = 0; i < shippingMethodArray.size(); i++) {
//                    if (shippingMethodArray.get(i).getName().equalsIgnoreCase(savedShippingMethod)) {
//                        isSavedMethodInList = true;
//                        costTextView.setText("AED " + String.valueOf(shippingMethodArray.get(i).getShippingCost()));
//                        break;
//                    }
//                }
//                if (!isSavedMethodInList) {
//                    costTextView.setText("AED " + String.valueOf(shippingMethodArray.get(0).getShippingCost()));
//                    SharedPreferences.Editor editor = mSharedPrefs.edit();
//                    editor.putString(Constants.SHIPPING_METHOD_PREFS, shippingMethodArray.get(0).getName());
//                    editor.commit();
//                }
//            }
//            else{
//                costTextView.setText("FREE SHIPPING");
//            }
//        }

        if(mSharedPrefs.contains(Constants.USER_SETTING_COUNTRY)){
            System.out.println("YES");
            countryNameTextView.setText(mSharedPrefs.getString(Constants.USER_SETTING_COUNTRY, null));
            imageIco=mSharedPrefs.getString(Constants.USER_COUNTRY_IMAGE_ID, null);

            imageSource = getResources().getIdentifier(imageIco , "drawable", getPackageName());
            countryImage.setImageDrawable(getResources().getDrawable(imageSource));
//            countryImage.setVisibility(View.VISIBLE);
        }
        else
        {
            System.out.println("No");
        }

    }

    private void populateSpecs(){
        if(colorArray.size()==0){
            colorLayout.setVisibility(View.GONE);
        }
        else{
            colorAdapter = new ColorSpecGridAdapter(ProductOptionsActivity.this, colorArray);

            colorRecyclerView.setAdapter(colorAdapter);
            colorRecyclerView.setExpanded(true);
        }

        if(storageArray.size()==0){
            storageLayout.setVisibility(View.GONE);
        }
        else{
            storageAdapter = new StorageSpecGridAdapter(ProductOptionsActivity.this, storageArray);

            storageRecyclerView.setAdapter(storageAdapter);
            storageRecyclerView.setExpanded(true);
        }

        if(sizeArray.size()==0){
            sizeLayout.setVisibility(View.GONE);
        }
        else{
            sizeAdapter = new SizeSpecGridAdapter(ProductOptionsActivity.this, sizeArray);
            sizeRecyclerView.setAdapter(sizeAdapter);
            sizeRecyclerView.setExpanded(true);
        }
    }

    public void pupolateShippingMethod(){
        if(shippingMethodArray.size()>0) {
//            shippingHeaderTextView.setText(shippingMethodArray.get(0).getName());
            boolean isSavedMethodInList = false;
            if(!savedMethodName.equalsIgnoreCase("")){
                for(int i=0; i<shippingMethodArray.size();i++){
                    if(shippingMethodArray.get(i).getName().equalsIgnoreCase(savedMethodName)){
                        isSavedMethodInList = true;
                        costTextView.setText("AED " + String.valueOf(shippingMethodArray.get(i).getShippingCost()));
                        savedMethodProfileId = shippingMethodArray.get(i).getProfileId();
                        break;
                    }
                }
                if(!isSavedMethodInList){
                    costTextView.setText("AED " + String.valueOf(shippingMethodArray.get(0).getShippingCost()));
                    savedMethodProfileId = shippingMethodArray.get(0).getProfileId();
                    savedMethodName = shippingMethodArray.get(0).getName();
//                    SharedPreferences.Editor editor = mSharedPrefs.edit();
//                    editor.putString(Constants.SHIPPING_METHOD_PREFS, shippingMethodArray.get(0).getName());
//                    editor.commit();
                }
            }
            else{
                costTextView.setText("AED " + String.valueOf(shippingMethodArray.get(0).getShippingCost()));
                savedMethodProfileId = shippingMethodArray.get(0).getProfileId();
                savedMethodName = shippingMethodArray.get(0).getName();
//                SharedPreferences.Editor editor = mSharedPrefs.edit();
//                editor.putString(Constants.SHIPPING_METHOD_PREFS, shippingMethodArray.get(0).getName());
//                editor.commit();
            }


//            if (mSharedPrefs.contains(Constants.SHIPPING_METHOD_PREFS)) {
//                String savedShippingMethod = mSharedPrefs.getString(Constants.SHIPPING_METHOD_PREFS, null);
//                for(int i=0; i<shippingMethodArray.size();i++){
//                    if(shippingMethodArray.get(i).getName().equalsIgnoreCase(savedShippingMethod)){
//                        isSavedMethodInList = true;
//                        costTextView.setText("AED " + String.valueOf(shippingMethodArray.get(i).getShippingCost()));
//                        savedMethodProfileId = shippingMethodArray.get(i).getProfileId();
//                        break;
//                    }
//                }
//                if(!isSavedMethodInList){
//                    costTextView.setText("AED " + String.valueOf(shippingMethodArray.get(0).getShippingCost()));
//                    savedMethodProfileId = shippingMethodArray.get(0).getProfileId();
//                    SharedPreferences.Editor editor = mSharedPrefs.edit();
//                    editor.putString(Constants.SHIPPING_METHOD_PREFS, shippingMethodArray.get(0).getName());
//                    editor.commit();
//                }
//            }
//            else{
//                costTextView.setText("AED " + String.valueOf(shippingMethodArray.get(0).getShippingCost()));
//                savedMethodProfileId = shippingMethodArray.get(0).getProfileId();
//                SharedPreferences.Editor editor = mSharedPrefs.edit();
//                editor.putString(Constants.SHIPPING_METHOD_PREFS, shippingMethodArray.get(0).getName());
//                editor.commit();
//            }
        }
    }


    public class GetAddToCartCallBack extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject mMembersJSON;
                System.out.println("Add to cart Post response: " + response);
                mMembersJSON = new JSONObject(response);
                System.out.println(mMembersJSON);

                Intent i=new Intent(ProductOptionsActivity.this,CheckOutActivity.class);
                startActivity(i);

                checkOutButton.setEnabled(true);
                checkOutButton.setBackgroundColor(getResources().getColor(R.color.red_base));
                checkOutButton.setTextColor(getResources().getColor(R.color.button_text));

                progressLayout.setVisibility(View.GONE);


            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Data could not be Loaded", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED);

                View snackbarView = snackbar.getView();

                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
            }
        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            progressLayout.setVisibility(View.VISIBLE);
        }
    }

    public class GetShippingsCallBack extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                shippingResponse = response;
                shippingMethodArray.clear();
                JSONObject issueObj = new JSONObject(response);
                JSONObject dataObj = null;
                JSONObject varObj;
                System.out.println("SHIPPING"+dataObj);
//                , sizesArray, storageArray;
            //    if(!issueObj.getBoolean("errors")){
                    dataObj =  issueObj.getJSONObject("OUTPUT").getJSONObject("DATA");
                    if(dataObj.has("SHIPPING")){
                       /* for(int i=0;i<dataObj.getJSONArray("SHIPPING").length();i++){
                            shippingMethodArray.add(new ShippingMethod(dataObj.getJSONArray("server").getJSONObject(i).getDouble("shipping_cost"),
                                    dataObj.getJSONArray("server").getJSONObject(i).getInt("savings"), dataObj.getJSONArray("server").getJSONObject(i).getString("name"),
                                    dataObj.getJSONArray("server").getJSONObject(i).getInt("est_from"), dataObj.getJSONArray("server").getJSONObject(i).getInt("est_to"),
                                    dataObj.getJSONArray("server").getJSONObject(i).getString("profile_id"), false));
                        }*/
                        shippingMethodArray.add(new ShippingMethod(dataObj.getJSONObject("SHIPPING").getDouble("PRICE"),
                                1, dataObj.getJSONObject("SHIPPING").getString("NAME"),
                                dataObj.getJSONObject("SHIPPING").getInt("PERIOD_FROM"), dataObj.getJSONObject("SHIPPING").getInt("PERIOD_TO"),
                                dataObj.getJSONObject("SHIPPING").getString("ID"), false));
                    }
                    errorTextView.setVisibility(View.GONE);
                    errorTextView.setText("");
         /*       }
                else{
                    dataObj =  issueObj.getJSONObject("data");
                    errorTextView.setVisibility(View.VISIBLE);
                    errorTextView.setText(dataObj.getString("public"));
                }*/
                progressLayout.setVisibility(View.GONE);

                pupolateShippingMethod();

            } catch (JSONException e) {
                e.printStackTrace();
                /*Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();*/
                Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Data could not be Loaded", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED);

                View snackbarView = snackbar.getView();

                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
                progressLayout.setVisibility(View.GONE);
            }
        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            progressLayout.setVisibility(View.VISIBLE);
        }
    }


//    public class GetProductSpecsCallBack extends AsyncCallback {
//        public void onTaskComplete(String response) {
//            try {
//                JSONObject issueObj = new JSONObject(response);
//                JSONObject dataObj;
//                JSONArray varObj;
////                , sizesArray, storageArray;
//                if(issueObj.getInt("status")==200){
//                    dataObj =  issueObj.getJSONObject("data").getJSONObject("specs");
//                    varObj =  issueObj.getJSONObject("data").getJSONArray("variants");
//                    if(dataObj.has("colors")){
//                        for(int i=0;i<dataObj.getJSONArray("colors").length();i++){
//                            colorArray.add(new ColorSpec(dataObj.getJSONArray("colors").getJSONObject(i).getString("color"),
//                                    dataObj.getJSONArray("colors").getJSONObject(i).getString("image"), false));
//                        }
//                    }
//
//
//                    if(dataObj.has("storages")){
//                        for(int i=0;i<dataObj.getJSONArray("storages").length();i++){
//                            storageArray.add(new StorageSpec(dataObj.getJSONArray("storages").getJSONObject(i).getString("storage"), false));
//                        }
//                    }
//
//
//                    if(dataObj.has("sizes")){
//                        for(int i=0;i<dataObj.getJSONArray("sizes").length();i++){
//                            sizeArray.add(new SizeSpec(dataObj.getJSONArray("sizes").getJSONObject(i).getString("size"), false));
//                        }
//                    }
//
//
//                        for(int i=0;i<varObj.length();i++){
//                            variantArray.add(new Variant(varObj.getJSONObject(i).getJSONObject("_id").getString("$id"),
//                                    varObj.getJSONObject(i).getJSONObject("specs").getJSONObject("color").getString("color"),
//                                    varObj.getJSONObject(i).getJSONObject("specs").getString("storage"), varObj.getJSONObject(i).getInt("quantity")));
//                        }
//
//
//                }
//                progressLayout.setVisibility(View.GONE);
//
//                populateSpecs();
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//                /*Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
//                        .setActionTextColor(Color.RED)
//                        .show();*/
//                Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Data could not be Loaded", Snackbar.LENGTH_LONG)
//                        .setActionTextColor(Color.RED);
//
//                View snackbarView = snackbar.getView();
//
//                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                textView.setTextColor(Color.WHITE);
//                snackbar.show();
//            }
//        }
//        @Override
//        public void onTaskCancelled() {
//        }
//        @Override
//        public void onPreExecute() {
//            // TODO Auto-generated method stub
//            progressLayout.setVisibility(View.VISIBLE);
//        }
//    }


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



    public class CheckUserCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
             /*   if (obj.getInt("status") == 200) {
                    inputNumberErrorTextView.setVisibility(View.GONE);
                    if (obj.getJSONObject("message").getBoolean("register") == true) {
                        showRegister();
                    } else {
                        showLogin();
                    }
                }*/
                inputNumberErrorTextView.setVisibility(View.GONE);
                /*if(obj.has("ERROR"))
                {
                    showLogin();
                }
                else
                {
                    showRegister();
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
                        showRegister();
                    }
                    else if(obj.getJSONObject("STATUS").getInt("CODE")==409)
                    {
                        showLogin();
                    }
                }
                progressBarForm.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
                inputNumberErrorTextView.setVisibility(View.VISIBLE);
                inputNumberErrorTextView.setText("Please check you connectivity");
                progressBarForm.setVisibility(View.GONE);
            }

        }

        @Override
        public void onTaskCancelled() {
        }

        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            progressBarForm.setVisibility(View.VISIBLE);

        }
    }









    public void showLogin() {
        //show login dialog
        loginDialog = new Dialog(this, R.style.AppCompatAlertDialogStyle);
        loginDialog.setCancelable(true);
        loginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loginDialog.setContentView(R.layout.dialog_login);
        loginErrorTextView = (TextView)loginDialog.findViewById(R.id.errorMessageTextView);
        final EditText passwordEditText = (EditText) loginDialog.findViewById(R.id.passwordEditText);
        Button cancelButton = (Button) loginDialog.findViewById(R.id.cancelButton);
        Button nextButton = (Button) loginDialog.findViewById(R.id.nextButton);
        progressBarLogin = (ProgressBar) loginDialog.findViewById(R.id.load_progress_bar);
        // if button is clicked, close the custom dialog
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog.dismiss();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordEditText.getText().toString().equalsIgnoreCase("")) {
                    passwordEditText.setError("Please Enter Password");
//                    Snackbar.make(findViewById(android.R.id.content), "Please Enter Password", Snackbar.LENGTH_LONG)
//                            .setActionTextColor(Color.RED)
//                            .show();

                } else {
                    password = passwordEditText.getText().toString();
                    HashMap<String, Object> userData = new HashMap<String, Object>();
                    userData.put("phone_number", emailId);
                    userData.put("access_key", password);

                    valueValidation="login";
                    JSONObject dataToSend = new JSONObject(userData);
                    new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new loginAndRegisterUserCallback()).userLoginAPICall(emailId,password);
                }
            }
        });

        loginDialog.show();
        loginDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void showRegister() {
        //show login dialog
        registerDialog = new Dialog(this, R.style.AppCompatAlertDialogStyle);
        registerDialog.setCancelable(true);
        registerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        registerDialog.setContentView(R.layout.dialog_register);
        registrationErrorTextView = (TextView)registerDialog.findViewById(R.id.errorMessageTextView);
        final EditText regPhoneNumberEditText=   (EditText) registerDialog.findViewById(R.id.regPhoneNumberEditText);
        passwordEditText = (EditText) registerDialog.findViewById(R.id.passwordEditText);
        final EditText confirmPasswordEditText = (EditText) registerDialog.findViewById(R.id.confirmPasswordEditText);
        Button cancelButton = (Button) registerDialog.findViewById(R.id.cancelButton);
        Button nextButton = (Button) registerDialog.findViewById(R.id.nextButton);
        LinearLayout buttonLayout = (LinearLayout) registerDialog.findViewById(R.id.buttonLayout);
        progressBarLogin = (ProgressBar) registerDialog.findViewById(R.id.load_progress_bar);
        // if button is clicked, close the custom dialog
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerDialog.dismiss();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordEditText.getText().toString().equalsIgnoreCase("")) {
                    passwordEditText.setError(getString(R.string.enter_password));
//                    Snackbar.make(findViewById(android.R.id.content), "Please Enter Password", Snackbar.LENGTH_LONG)
//                            .setActionTextColor(Color.RED)
//                            .show();

                } else if (confirmPasswordEditText.getText().toString().equalsIgnoreCase("")) {
                    confirmPasswordEditText.setError(getString(R.string.confirm_password));
//                    Snackbar.make(findViewById(android.R.id.content), "Please Confirm Your Password", Snackbar.LENGTH_LONG)
//                            .setActionTextColor(Color.RED)
//                            .show();
                }

                else if (regPhoneNumberEditText.getText().toString().equalsIgnoreCase("")) {
                    regPhoneNumberEditText.setError("Please Enter Phone Number");
                }
                else if(!android.util.Patterns.PHONE.matcher(regPhoneNumberEditText.getText().toString()).matches()||regPhoneNumberEditText.getText().length()<10)
                {
                    regPhoneNumberEditText.setError("Incorrect Phone Number");
                }
                else if (!passwordEditText.getText().toString().equalsIgnoreCase(confirmPasswordEditText.getText().toString())) {
                    confirmPasswordEditText.setError(getString(R.string.password_do_not_match));
//                    Snackbar.make(findViewById(android.R.id.content), "Your password and confirm password are not same", Snackbar.LENGTH_LONG)
//                            .setActionTextColor(Color.RED)
//                            .show();
                } else {
                    password = passwordEditText.getText().toString();
                    HashMap<String, Object> userData = new HashMap<String, Object>();
                   /* userData.put("phone_number", emailId);
                    userData.put("access_key", password);*/
                    userData.put("email", emailId);
                    userData.put("password", password);
                    userData.put("cpassword", password);
                    userData.put("phone",regPhoneNumberEditText.getText().toString());
                    JSONObject dataToSend = new JSONObject(userData);
                    valueValidation="register";
                    new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new loginAndRegisterUserCallback()).useRegisterAPICall(dataToSend.toString());
                }
            }
        });

        registerDialog.show();
        registerDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }


    public class loginAndRegisterUserCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
              /*  if (obj.getBoolean("errors")) {
                    if ((loginDialog != null)) {
                        loginErrorTextView.setVisibility(View.VISIBLE);
                        loginErrorTextView.setText(obj.getString("message"));
                    }
                    else if ((registerDialog != null)) {
                        registrationErrorTextView.setVisibility(View.VISIBLE);
                        registrationErrorTextView.setText(obj.getString("message"));
                    }
                }*/



                if(obj.getJSONObject("STATUS").getInt("CODE")==400) {
                   /* Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();*/
                    for(int i=0;i<obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").length();i++)
                    {
                        JSONObject verificationCheck=obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(i);

                        if (verificationCheck.getString("FIELD").equals("PASSWORD")) {
                            passwordEditText.setError(verificationCheck.getString("MESSAGE"));
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
                else if(obj.getJSONObject("STATUS").getInt("CODE")==409)
                {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }

                else if(obj.getJSONObject("STATUS").getInt("CODE")==201){
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.putString(Constants.USER_MOBILE_PREFS, emailId);

                    editor.putString(Constants.USER_AUTH_TOKEN_PREFS, obj.getJSONObject("OUTPUT").getJSONObject("DATA").getString("TOKEN"));
                    //editor.putString(Constants.USER_ID_PREFS, obj.getJSONObject("OUTPUT").getJSONObject("DATA").getString("USER_ID"));
                    editor.putString(Constants.USER_ID_PREFS, "Y");

                    editor.commit();
                    //Hide dialogs and proceed with cart
                    if ((loginDialog != null)) {
                        loginDialog.dismiss();
                        loginDialog = null;
                    }
                    if ((registerDialog != null)) {
                        registerDialog.dismiss();
                        registerDialog = null;
                    }
                    if ((inputNumberDialog != null)) {
                        inputNumberDialog.dismiss();
                        inputNumberDialog = null;
                    }
                    if(valueValidation.equals("register"))
                    {
                        if(obj.getJSONObject("OUTPUT").getJSONObject("DATA").has("CODE_EXP_TIME")) {
                            startTime=obj.getJSONObject("OUTPUT").getJSONObject("DATA").getInt("CODE_EXP_TIME") * 1000;
                            verifyDialog();
                        }
                    }
                    else {
                        initializeCart();
                    }
                }
                progressBarLogin.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
                progressBarLogin.setVisibility(View.GONE);
            }
        }

        @Override
        public void onTaskCancelled() {
        }

        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            progressBarLogin.setVisibility(View.VISIBLE);

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
        initializeCart();
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
            new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new verifyCode()).verifySms(code);
        }
        else if(value.equals("changeNumber"))
        {



            HashMap<String,String > resendData=new HashMap<String ,String >();
            //   resendData.put("user_id",userId);
            resendData.put("phone", phoneNumber);
            //  resendData.put("mode","send-verify-sms");

            JSONObject dataResend=new JSONObject(resendData);

            new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new changePhone()).reSendCallBack(dataResend.toString());
        }
        else if(value.equals("resend"))
        {
            HashMap<String,String > resendSmsData=new HashMap<String ,String >();
            //      resendSmsData.put("user_id",userId);

            // resendSmsData.put("mode","resend-verify-sms");

            JSONObject dataSmsResend=new JSONObject(resendSmsData);

            new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new reSendSms()).reSendSms(dataSmsResend.toString());
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
                    initializeCart();

                  /*  Intent i = new Intent(ProductOptionsActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();*/
                }
                else if (obj.getJSONObject("STATUS").getInt("CODE")==201) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("STATUS").getString("MESSAGE").toString(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();

                    /*Intent i = new Intent(ProductOptionsActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();*/
                    initializeCart();
                }
                else if(obj.getJSONObject("STATUS").getInt("CODE")==400)
                {
                    Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("OUTPUT").getJSONArray("ERRORS").getJSONObject(0).getString("MESSAGE").toString(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                    verifyDialog.dismiss();
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



}
