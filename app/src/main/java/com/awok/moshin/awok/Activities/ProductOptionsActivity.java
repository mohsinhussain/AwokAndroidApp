package com.awok.moshin.awok.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    String colorString = "";
    String storageString = "";
    String sizeString = "";
    TextView costTextView, shippingHeaderTextView, stockQuantityTextView;
    ArrayList<Variant> variantArray = new ArrayList<Variant>();
    StaggeredGridLayoutManager colorLayoutManager, storageLayoutManager, sizeLayoutManager;
    private String valueQuantity;
    private int stockQuantity;
    String variantId = "";
    Button checkOutButton;
    RelativeLayout shippingMethodSelectionLayout;
    TextView errorTextView;
    SharedPreferences mSharedPrefs;
    private ImageView countryImage;
    String imageIco;
    int imageSource;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;
    String savedMethodName = "";
    String savedMethodProfileId = "";
    JSONObject dataToSend;
    ImageView productImageView;
    TextView productNameTextView, priceTexView, colorTextView;
    JSONArray variantsArray;
    JSONObject specsObject;
    TextView colorLabelTextView, countryNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_product_options);
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
        increment = (Button) findViewById(R.id.quantity_inc);
        decrement = (Button) findViewById(R.id.quantity_dec);
        shippingMethodSelectionLayout = (RelativeLayout) findViewById(R.id.shippingMethodSelectionLayout);
        errorTextView = (TextView) findViewById(R.id.errorTextView);
        countryImage=(ImageView)findViewById(R.id.country_image);
        productImageView=(ImageView)findViewById(R.id.imagePhotoView);
        productNameTextView = (TextView) findViewById(R.id.nameTextView);
        colorTextView = (TextView) findViewById(R.id.selectedColorTextView);
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
        productNameTextView.setText(getIntent().getExtras().getString(Constants.PRODUCT_NAME_INTENT));
        priceTexView.setText(getIntent().getExtras().getString(Constants.PRODUCT_PRICE_NEW_INTENT));
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        String imageURL = getIntent().getExtras().getString(Constants.PRODUCT_IMAGE_INTENT);
        imageLoader.get(imageURL, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                productImageView.setImageDrawable(getResources().getDrawable(R.drawable.default_img));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    productImageView.setImageBitmap(response.getBitmap());
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        try {
            specsObject = new JSONObject(getIntent().getExtras().getString(Constants.PRODUCT_SPECS_INTENT));
            variantsArray = new JSONArray(getIntent().getExtras().getString(Constants.PRODUCT_VARIANTS_INTENT));

                if(specsObject.has("colors")){
                    for(int i=0;i<specsObject.getJSONArray("colors").length();i++){
                        colorArray.add(new ColorSpec(specsObject.getJSONArray("colors").getJSONObject(i).getString("color"),
                                specsObject.getJSONArray("colors").getJSONObject(i).getString("image"), false));
                    }
                }


                if(specsObject.has("sizes")){
                    for(int i=0;i<specsObject.getJSONArray("sizes").length();i++){
                        storageArray.add(new StorageSpec(specsObject.getJSONArray("sizes").getJSONObject(i).getString("size"), false));
                    }
                }


                for(int i=0;i<variantsArray.length();i++){
                    Variant variant = (new Variant(variantsArray.getJSONObject(i).getJSONObject("_id").getString("$id"),
                            variantsArray.getJSONObject(i).getJSONObject("specs").optString("size"), variantsArray.getJSONObject(i).getInt("quantity"),
                            variantsArray.getJSONObject(i).getJSONObject("pricing").getInt("discount_price")));
                    if(variantsArray.getJSONObject(i).getJSONObject("specs").has("color")){
                        if(variantsArray.getJSONObject(i).getJSONObject("specs").getJSONObject("color").has("color")){
                            variant.setColor(variantsArray.getJSONObject(i).getJSONObject("specs").getJSONObject("color").getString("color"));
                        }
                    }
                    variantArray.add(variant);
                }

            if(variantArray.size()>1){
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
                variantId = variantArray.get(0).getId();
                stockQuantity = variantArray.get(0).getStock();
                stockQuantityTextView.setText(stockQuantity + " items left in stock");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        shippingMethodSelectionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductOptionsActivity.this, ShippingMethodActivity.class);
                i.putExtra(Constants.PRODUCT_ID_INTENT, productId);
                i.putExtra(Constants.QUANTITY_INTENT, quantity.getText().toString());
                i.putExtra(Constants.STOCK_INTENT, stockQuantity);
                i.putExtra(Constants.VARIANTID_INTENT, variantId);
                i.putExtra(Constants.SELECTED_METHOD_INTENT, savedMethodName);
                startActivityForResult(i, 1);
            }
        });

        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!variantId.equalsIgnoreCase("") && stockQuantity>=Integer.parseInt(quantity.getText().toString()) && !savedMethodProfileId.equalsIgnoreCase("") && errorTextView.getText().toString().equalsIgnoreCase("")){
                    checkOutButton.setEnabled(false);
                    checkOutButton.setBackgroundColor(getResources().getColor(R.color.border));
                    checkOutButton.setTextColor(getResources().getColor(R.color.button_text));
                    HashMap<String,Object> addToCartData=new HashMap<String, Object>();
                    addToCartData.put("user_id", "55f6a9462f17f64a9b5f5ce4");
                    addToCartData.put("product_id", productId);
                    addToCartData.put("shipping_profile_id", savedMethodProfileId);
                    addToCartData.put("variant_id",variantId);
                    addToCartData.put("quantity",Integer.parseInt(quantity.getText().toString()));
                    dataToSend=new JSONObject(addToCartData);
                    System.out.println(dataToSend.toString());
                    new APIClient(ProductOptionsActivity.this, getApplicationContext(),  new GetAddToCartCallBack()).addToCartAPICall(dataToSend.toString());
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
            String locationId = "560a8eddf26f2e024b8b4690";
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
                for (int i = 0; i < colorArray.size(); i++) {
                    colorArray.get(i).setIsSelected(false);
                }
                colorArray.get(position).setIsSelected(true);
                colorAdapter = new ColorSpecGridAdapter(ProductOptionsActivity.this, colorArray);

                colorRecyclerView.setAdapter(colorAdapter);
                colorRecyclerView.setExpanded(true);

                colorString = colorArray.get(position).getColor();
                colorTextView.setText(colorString);
                boolean isMatching = false;
                if(storageArray.size()>0) {
                    if (!colorString.equalsIgnoreCase("") && !storageString.equalsIgnoreCase("")) {
                        //call shipping method API
                        System.out.println("Call shipping method API");


                        for (int i = 0; i < variantArray.size(); i++) {

                            if (variantArray.get(i).getColor().equalsIgnoreCase(colorString) && variantArray.get(i).getStorage().equalsIgnoreCase(storageString)) {
                                isMatching = true;
                                variantId = variantArray.get(i).getId();
                                stockQuantity = variantArray.get(i).getStock();
                                stockQuantityTextView.setText(stockQuantity + " items left in stock");
                                priceTexView.setText("AED " + variantArray.get(i).getPrice());
                                if (Integer.parseInt(quantity.getText().toString()) > stockQuantity) {
                                    quantity.setText(stockQuantity);
                                    if (networkInfo != null && networkInfo.isConnected()) {
                                        String locationId = "560a8eddf26f2e024b8b4690";
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
                        ConnectivityManager connMgr = (ConnectivityManager)
                                getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            String locationId = "560a8eddf26f2e024b8b4690";
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
                        variantId = "";
                        stockQuantityTextView.setText("");
                    }
                }
                else{
                    if (!colorString.equalsIgnoreCase("")) {
                        //call shipping method API
                        System.out.println("Call shipping method API");


                        for (int i = 0; i < variantArray.size(); i++) {

                            if (variantArray.get(i).getColor().equalsIgnoreCase(colorString)) {
                                isMatching = true;
                                variantId = variantArray.get(i).getId();
                                stockQuantity = variantArray.get(i).getStock();
                                stockQuantityTextView.setText(stockQuantity + " items left in stock");
                                priceTexView.setText("AED " + variantArray.get(i).getPrice());
                                if (Integer.parseInt(quantity.getText().toString()) > stockQuantity) {
                                    quantity.setText(stockQuantity);
                                    if (networkInfo != null && networkInfo.isConnected()) {
                                        String locationId = "560a8eddf26f2e024b8b4690";
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
                        ConnectivityManager connMgr = (ConnectivityManager)
                                getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            String locationId = "560a8eddf26f2e024b8b4690";
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
                        variantId = "";
                        stockQuantityTextView.setText("");
                    }
                }
                    //locationId  = 560a8eddf26f2e024b8b4690
            }
        });


        storageRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i=0;i<storageArray.size();i++){
                    storageArray.get(i).setIsSelected(false);
                }
                storageArray.get(position).setIsSelected(true);
                storageRecyclerView.setAdapter(storageAdapter);
                storageRecyclerView.setExpanded(true);

                storageString = storageArray.get(position).getColor();
                if(colorArray.size()>0) {
                    if (!colorString.equalsIgnoreCase("") && !storageString.equalsIgnoreCase("")) {
                        boolean isMatching = false;


                        for (int i = 0; i < variantArray.size(); i++) {
                                if (variantArray.get(i).getColor().equalsIgnoreCase(colorString) && variantArray.get(i).getStorage().equalsIgnoreCase(storageString)) {
                                    isMatching = true;
                                    variantId = variantArray.get(i).getId();
                                    stockQuantity = variantArray.get(i).getStock();
                                    stockQuantityTextView.setText(stockQuantity + " items left in stock");
                                    priceTexView.setText("AED " + variantArray.get(i).getPrice());
                                    if (Integer.parseInt(quantity.getText().toString()) > stockQuantity) {
                                        quantity.setText(Integer.toString(stockQuantity));
                                        if (networkInfo != null && networkInfo.isConnected()) {
                                            String locationId = "560a8eddf26f2e024b8b4690";
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
                            ConnectivityManager connMgr = (ConnectivityManager)
                                    getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                            if (networkInfo != null && networkInfo.isConnected()) {
                                String locationId = "560a8eddf26f2e024b8b4690";
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
                            variantId = "";
                            stockQuantityTextView.setText("");
                        }
                    }
                }
                else{
                    if (!storageString.equalsIgnoreCase("")) {
                        boolean isMatching = false;


                        for (int i = 0; i < variantArray.size(); i++) {
                            if (variantArray.get(i).getStorage().equalsIgnoreCase(storageString)) {
                                isMatching = true;
                                variantId = variantArray.get(i).getId();
                                stockQuantity = variantArray.get(i).getStock();
                                stockQuantityTextView.setText(stockQuantity + " items left in stock");
                                priceTexView.setText("AED " + variantArray.get(i).getPrice());
                                if (Integer.parseInt(quantity.getText().toString()) > stockQuantity) {
                                    quantity.setText(Integer.toString(stockQuantity));
                                    if (networkInfo != null && networkInfo.isConnected()) {
                                        String locationId = "560a8eddf26f2e024b8b4690";
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
                            ConnectivityManager connMgr = (ConnectivityManager)
                                    getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                            if (networkInfo != null && networkInfo.isConnected()) {
                                String locationId = "560a8eddf26f2e024b8b4690";
                                new APIClient(ProductOptionsActivity.this, ProductOptionsActivity.this, new GetShippingsCallBack()).ShippingsAPICall(productId, quantity.getText().toString(), locationId,
                                        variantId);
                            } else {
                                Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                        .setActionTextColor(Color.RED)
                                        .show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "OUT OF STOCK", Toast.LENGTH_SHORT).show();
                            variantId = "";
                            stockQuantityTextView.setText("");
                        }
                    }
                }
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
                        String locationId = "560a8eddf26f2e024b8b4690";
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
                                            String locationId = "560a8eddf26f2e024b8b4690";
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
                            String locationId = "560a8eddf26f2e024b8b4690";
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
                    if (networkInfo != null && networkInfo.isConnected()) {
                        String locationId = "560a8eddf26f2e024b8b4690";
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
                                            String locationId = "560a8eddf26f2e024b8b4690";
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
                            String locationId = "560a8eddf26f2e024b8b4690";
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
                            String locationId = "560a8eddf26f2e024b8b4690";
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
                            String locationId = "560a8eddf26f2e024b8b4690";
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
                                                String locationId = "560a8eddf26f2e024b8b4690";
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
                                String locationId = "560a8eddf26f2e024b8b4690";
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
            countryImage.setVisibility(View.VISIBLE);
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
                shippingMethodArray.clear();
                JSONObject issueObj = new JSONObject(response);
                JSONObject dataObj = null;
                JSONObject varObj;
//                , sizesArray, storageArray;
                if(!issueObj.getBoolean("errors")){
                    dataObj =  issueObj.getJSONObject("data");
                    if(dataObj.has("server")){
                        for(int i=0;i<dataObj.getJSONArray("server").length();i++){
                            shippingMethodArray.add(new ShippingMethod(dataObj.getJSONArray("server").getJSONObject(i).getDouble("shipping_cost"),
                                    dataObj.getJSONArray("server").getJSONObject(i).getInt("savings"), dataObj.getJSONArray("server").getJSONObject(i).getString("name"),
                                    dataObj.getJSONArray("server").getJSONObject(i).getInt("est_from"), dataObj.getJSONArray("server").getJSONObject(i).getInt("est_to"),
                                    dataObj.getJSONArray("server").getJSONObject(i).getString("profile_id"), false));
                        }
                    }
                    errorTextView.setVisibility(View.GONE);
                    errorTextView.setText("");
                }
                else{
                    dataObj =  issueObj.getJSONObject("data");
                    errorTextView.setVisibility(View.VISIBLE);
                    errorTextView.setText(dataObj.getString("public"));
                }
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

}
