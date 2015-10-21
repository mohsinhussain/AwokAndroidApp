package com.awok.moshin.awok.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.awok.moshin.awok.Activities.CheckOutActivity;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.CheckoutDataObjects;
import com.awok.moshin.awok.Models.ProductOverview;
import com.awok.moshin.awok.Models.Products;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by shon on 9/14/2015.
 */
//
public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.ViewHolder> {
    private List<Checkout> OverViewList = new ArrayList<Checkout>();
    private Activity activity_main;
    View customView;

    private ArrayAdapter<CharSequence> adapter;
    NetworkInfo networkInfo;
    Context context;
    Context mContext;
    private String valueQuantity;
    public static final int ITEM_WITH_Seller = 1;
    public static final int ITEM_WITHOUT_SELLER = 2;
    SVG svg;
    String sellerCheck="";
    LinearLayout.LayoutParams lp;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CheckOutAdapter(Activity activity,List<Checkout> overViewList) {
        OverViewList = overViewList;
        activity=activity;
        //adapter = ArrayAdapter.createFromResource(activity, R.array.object_array, android.R.layout.simple_spinner_item);
       // sellerCheck="";
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter.setDropDownViewResource(R.layout.spinner_drop_down);
        ConnectivityManager connMgr = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);
         networkInfo = connMgr.getActiveNetworkInfo();
        this.context=activity;
        this.activity_main=activity;
         lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.topMargin = 20;
        mContext=activity.getApplication();
        //svg = SVGParser.getSVGFromResource(activity.getResources(),R.raw.cross);

    }

    int index = -1;

    @Override
    public int getItemViewType(int position) {
        /*Log.v("Checkout adapter", "SellerLabel: "+OverViewList.get(position).getSellerLabel()+"   Seller Check: "+sellerCheck);
        if (OverViewList.get(position).getSellerLabel().equalsIgnoreCase(sellerCheck)) {
            Log.v("Checkout adapter", "case: WITH for position: "+position);
            if(index==position){
                return ITEM_WITHOUT_SELLER;
            }
            return ITEM_WITH_Seller;
        }
        else{
            Log.v("Checkout adapter", "case: WITH for position: "+position);
            index = position;
            return ITEM_WITHOUT_SELLER;
        }*/
        if(OverViewList.get(position).getIsHeader() && OverViewList.get(position).getIsFooter())

        {

        }
        else  if(OverViewList.get(position).getIsHeader())
        {

        }
        else if(OverViewList.get(position).getIsFooter())
        {

        }
        else
        {

        }
            return -1;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public CheckOutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.checkoutlayout, null);

        // create ViewHolder
        //sellerCheck="";
        customView=itemLayoutView;

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        /*LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.topMargin = 20;
        final Checkout item = OverViewList.get(position);

        System.out.println("GOAL"+sellerCheck);
        System.out.println(OverViewList.get(position).getSellerLabel());*/
        LayerDrawable mainRatingColor = (LayerDrawable) viewHolder.main_prodRatingBar.getProgressDrawable();
        mainRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#ffcd00"), PorterDuff.Mode.SRC_ATOP);
        mainRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#ffcd00"), PorterDuff.Mode.SRC_ATOP);
        mainRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#e8e8e8"), PorterDuff.Mode.SRC_ATOP);

        // Get a drawable from the parsed SVG and set it as the drawable for the ImageView

        //viewHolder.productImg.setImageDrawable(svg.createPictureDrawable());
//        final Checkout item = OverViewList.get(position);
        System.out.println("top" + sellerCheck);

        viewHolder.newPrice.setText("AED " + OverViewList.get(position).getOverViewText());
        viewHolder.prodOverViewText.setText("AED " + OverViewList.get(position).getOldPrice());
        viewHolder.prodOverviewTitle.setText(OverViewList.get(position).getOverViewTitle());
        viewHolder.prodOverviewTitle.setTag(OverViewList.get(position).getProductId());
        //viewHolder.sellerLabel.setText(OverViewList.get(position).getSellerLabel());
//        viewHolder.sellerMainLay.setVisibility(View.GONE);
        //viewHolder.sellerLabel.setText("Seller Name");
        viewHolder.totalPrice.setText("Total Price :" + OverViewList.get(position).getTotalPrice() + " AED");
        viewHolder.quantity.setText(OverViewList.get(position).getQuantity());
//        viewHolder.seperator.setVisibility(View.VISIBLE);

        //viewHolder.stock.setText("In Stock : " + OverViewList.get(position).getRemainingStock());
        viewHolder.stock.setText("Free Shipping");
        viewHolder.sellerNameText.setText("Seller :"+OverViewList.get(position).getSellerLabel());
        viewHolder.stock.setTag(OverViewList.get(position).getRemainingStock());
        // viewHolder.productImg.setImageBitmap(base64ToBitmap(OverViewList.get(position).getImageBitmapString()));
        viewHolder.cross.setChecked(false);
        viewHolder.main_prodRatingBar.setRating(4);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(OverViewList.get(position).getImageBitmapString(), new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                viewHolder.productImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.default_img));
                viewHolder.loadProgressBar.setVisibility(View.GONE);
            }
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    viewHolder.productImg.setImageBitmap(response.getBitmap());
                    viewHolder.loadProgressBar.setVisibility(View.GONE);
                }
            }
        });

//            if(OverViewList.get(position).getImageBitmapString()!=null && !OverViewList.get(position).getImageBitmapString().equalsIgnoreCase("")){
//                viewHolder.productImg.setImageUrl(OverViewList.get(position).getImageBitmapString(), imageLoader);
//            }
//            else{
//                viewHolder.productImg.setImageDrawable(mContext.getResources().getDrawable((R.drawable.default_img)));
//            }
//            viewHolder.productImg.setErrorImageResId(R.drawable.default_img);
        sellerCheck=OverViewList.get(position).getSellerLabel();
        final int viewType = getItemViewType(position);
        switch (viewType) {
            case ITEM_WITHOUT_SELLER:
                viewHolder.sellerMainLay.setVisibility(View.VISIBLE);
                viewHolder.seperator.setVisibility(View.GONE);

                break;
            case ITEM_WITH_Seller:
                viewHolder.sellerMainLay.setVisibility(View.GONE);
                viewHolder.seperator.setVisibility(View.VISIBLE);

                break;
            default:
                // Blow up in whatever way you choose.
        }

        /**if(sellerCheck.equals(OverViewList.get(position).getSellerLabel()))
            {

            System.out.println("top" + sellerCheck);
            viewHolder.newPrice.setText("AED " + OverViewList.get(position).getOverViewText());
            viewHolder.prodOverViewText.setText("AED " + OverViewList.get(position).getOldPrice());
            viewHolder.prodOverviewTitle.setText(OverViewList.get(position).getOverViewTitle());
            viewHolder.prodOverviewTitle.setTag(OverViewList.get(position).getProductId());
            //viewHolder.sellerLabel.setText(OverViewList.get(position).getSellerLabel());
            viewHolder.sellerMainLay.setVisibility(View.GONE);
            //viewHolder.sellerLabel.setText("Seller Name");
            viewHolder.totalPrice.setText("Total Price :" + OverViewList.get(position).getTotalPrice() + " AED");
            viewHolder.quantity.setText(OverViewList.get(position).getQuantity());
            viewHolder.seperator.setVisibility(View.VISIBLE);

            //viewHolder.stock.setText("In Stock : " + OverViewList.get(position).getRemainingStock());
            viewHolder.stock.setText("Free Shipping");
            viewHolder.sellerNameText.setText("Seller :"+OverViewList.get(position).getSellerLabel());
            viewHolder.stock.setTag(OverViewList.get(position).getRemainingStock());
             // viewHolder.productImg.setImageBitmap(base64ToBitmap(OverViewList.get(position).getImageBitmapString()));
            viewHolder.cross.setChecked(false);
            viewHolder.main_prodRatingBar.setRating(4);
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            imageLoader.get(OverViewList.get(position).getImageBitmapString(), new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    viewHolder.productImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.default_img));
                    viewHolder.loadProgressBar.setVisibility(View.GONE);
                }
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        // load image into imageview
                        viewHolder.productImg.setImageBitmap(response.getBitmap());
                        viewHolder.loadProgressBar.setVisibility(View.GONE);
                    }
                }
            });

//            if(OverViewList.get(position).getImageBitmapString()!=null && !OverViewList.get(position).getImageBitmapString().equalsIgnoreCase("")){
//                viewHolder.productImg.setImageUrl(OverViewList.get(position).getImageBitmapString(), imageLoader);
//            }
//            else{
//                viewHolder.productImg.setImageDrawable(mContext.getResources().getDrawable((R.drawable.default_img)));
//            }
//            viewHolder.productImg.setErrorImageResId(R.drawable.default_img);
            sellerCheck=OverViewList.get(position).getSellerLabel();

        }
        else if(!sellerCheck.equals(OverViewList.get(position).getSellerLabel()))


        {
            System.out.println("top" + sellerCheck);
            //customView.setLayoutParams(lp);
            viewHolder.newPrice.setText("AED " + OverViewList.get(position).getOverViewText());
            viewHolder.prodOverViewText.setText("AED " + OverViewList.get(position).getOldPrice());
            viewHolder.prodOverviewTitle.setText(OverViewList.get(position).getOverViewTitle());
            viewHolder.prodOverviewTitle.setTag(OverViewList.get(position).getProductId());
            //viewHolder.stock.setText("In Stock : " + OverViewList.get(position).getRemainingStock());
            viewHolder.quantity.setText(OverViewList.get(position).getQuantity());
            viewHolder.stock.setTag(OverViewList.get(position).getRemainingStock());
            //viewHolder.sellerLabel.setText("Seller Name : " + OverViewList.get(position).getSellerLabel());
            //viewHolder.sellerLabel.setText("Seller Name");
            viewHolder.sellerMainLay.setVisibility(View.VISIBLE);
            viewHolder.main_prodRatingBar.setRating(3);
            viewHolder.sellerNameText.setText("Seller :"+OverViewList.get(position).getSellerLabel());
            viewHolder.totalPrice.setText("Total Price :" + OverViewList.get(position).getTotalPrice() + " AED");
            viewHolder.seperator.setVisibility(View.GONE);
            viewHolder.cross.setChecked(false);
            viewHolder.stock.setText("Free Shipping");
//            viewHolder.productImg.setImageBitmap(base64ToBitmap(OverViewList.get(position).getImageBitmapString()));
//            ImageLoader imageLoader = AppController.getInstance().getImageLoader();

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            imageLoader.get(OverViewList.get(position).getImageBitmapString(), new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    viewHolder.productImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.default_img));
                    viewHolder.loadProgressBar.setVisibility(View.GONE);
                }
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        // load image into imageview
                        viewHolder.productImg.setImageBitmap(response.getBitmap());
                        viewHolder.loadProgressBar.setVisibility(View.GONE);
                    }
                }
            });

//            if(OverViewList.get(position).getImageBitmapString()!=null && !OverViewList.get(position).getImageBitmapString().equalsIgnoreCase("")){
//                viewHolder.productImg.setImageUrl(OverViewList.get(position).getImageBitmapString(), imageLoader);
//            }
//            else{
//                viewHolder.productImg.setImageDrawable(mContext.getResources().getDrawable((R.drawable.default_img)));
//            }
//            viewHolder.productImg.setImageUrl(OverViewList.get(position).getImageBitmapString(), imageLoader);
//            viewHolder.productImg.setErrorImageResId(R.drawable.default_img);
            //customView.setLayoutParams(lp);

            sellerCheck=OverViewList.get(position).getSellerLabel();
        }**/

      //  viewHolder.data = new CheckoutDataObjects(activity);
        /*viewHolder.countOfProducts.setAdapter(adapter);*/
viewHolder.quantity.setOnTouchListener(new View.OnTouchListener() {
                                           @Override
                                           public boolean onTouch(View v, MotionEvent event) {
                                               valueQuantity = viewHolder.quantity.getText().toString();
                                              // viewHolder.quantity.setText("");
                                               //viewHolder.quantity.append(newtext);
                                               //viewHolder.quantity.getText().clear();
                                               return false;
                                           }
                                       });
    /*@Override
    public void onClick(View v) {

    }*/
//});




        viewHolder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int addQuantity=Integer.parseInt(viewHolder.quantity.getText().toString());
                addQuantity=addQuantity+1;
                viewHolder.quantity.setText(String.valueOf(addQuantity));

                if (viewHolder.quantity.getText().toString().equals("") || viewHolder.quantity.getText().toString().equals("0")) {
                    viewHolder.quantity.setText("1");
                } else {
                    if (Integer.parseInt(viewHolder.quantity.getText().toString()) > Integer.parseInt(viewHolder.stock.getTag().toString())) {
                        System.out.println("OUT OF STOCK");

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                context);

                        // set title
                        alertDialogBuilder.setTitle("Cart Alert");


                        // set dialog message
                        alertDialogBuilder
                                .setMessage("The Quantity is not available in Stock")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        dialog.cancel();
                                    }
                                });
                                   /* .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            // the dialog box and do nothing
                                            dialog.cancel();
                                        }
                                    });*/

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    } else {
                        HashMap<String, Object> updateString = new HashMap<String, Object>();
                        //updateString.put("quantity", Integer.parseInt(viewHolder.quantity.getText().toString()));
                        updateString.put("quantity", Integer.parseInt(viewHolder.quantity.getText().toString()));


                        JSONObject updateJson = new JSONObject(updateString);
                        System.out.println("AFTER CHANGED+" + updateJson);
                        //submit_btn.performClick();
                   /*System.out.println("AFTER CHANGED");
                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.
                            INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(activity.getParent().getCurrentFocus().getWindowToken(), 0);*/
                        View view = activity_main.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) activity_main.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        if (networkInfo != null && networkInfo.isConnected()) {

                            String updateId = viewHolder.prodOverviewTitle.getTag().toString();
                            //new APIClient(activity, context,  new RemoveProductCallBack()).removeProductFromCartCall("55ffc54c1a7da7681500002a");
                            new APIClient(activity_main, context, new UpdateCallBack()).updateCart(updateJson.toString(), updateId);


                        } else {
                            Snackbar.make(activity_main.findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }
                    }
                    }
                }

        });


        viewHolder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int addQuantity=Integer.parseInt(viewHolder.quantity.getText().toString());
                addQuantity=addQuantity-1;
                viewHolder.quantity.setText(String.valueOf(addQuantity));

                if (viewHolder.quantity.getText().toString().equals("") || viewHolder.quantity.getText().toString().equals("0")) {
                    viewHolder.quantity.setText("1");
                } else {
                    if (Integer.parseInt(viewHolder.quantity.getText().toString()) > Integer.parseInt(viewHolder.stock.getTag().toString())) {
                        System.out.println("OUT OF STOCK");

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                context);

                        // set title
                        alertDialogBuilder.setTitle("Cart Alert");


                        // set dialog message
                        alertDialogBuilder
                                .setMessage("The Quantity is not available in Stock")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        dialog.cancel();
                                    }
                                });
                                   /* .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            // the dialog box and do nothing
                                            dialog.cancel();
                                        }
                                    });*/

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    } else {
                        HashMap<String, Object> updateString = new HashMap<String, Object>();
                        //updateString.put("quantity", Integer.parseInt(viewHolder.quantity.getText().toString()));
                        updateString.put("quantity", Integer.parseInt(viewHolder.quantity.getText().toString()));


                        JSONObject updateJson = new JSONObject(updateString);
                        System.out.println("AFTER CHANGED+" + updateJson);
                        //submit_btn.performClick();
                   /*System.out.println("AFTER CHANGED");
                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.
                            INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(activity.getParent().getCurrentFocus().getWindowToken(), 0);*/
                        View view = activity_main.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) activity_main.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        if (networkInfo != null && networkInfo.isConnected()) {

                            String updateId = viewHolder.prodOverviewTitle.getTag().toString();
                            //new APIClient(activity, context,  new RemoveProductCallBack()).removeProductFromCartCall("55ffc54c1a7da7681500002a");
                            new APIClient(activity_main, context, new UpdateCallBack()).updateCart(updateJson.toString(), updateId);


                        } else {
                            Snackbar.make(activity_main.findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.RED)
                                    .show();
                        }
                    }
                }
            }

        });





viewHolder.quantity.addTextChangedListener(new TextWatcher() {


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        System.out.println("BEFOR CHANGED");
        //valueQuantity=viewHolder.quantity.getText().toString();
//viewHolder.quantity.setText("");

    }

    @Override

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        System.out.println("TEXT CHANGED");
    }

    @Override
    public void afterTextChanged(Editable s) {
        System.out.println("AFTER CHANGED");
        /*if (viewHolder.quantity.getText().toString().equals("0"))
        {
            viewHolder.quantity.setText("1");
        }*/
    }
});
        viewHolder.quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (!hasFocus) {
                    if (viewHolder.quantity.getText().toString().equals("") || viewHolder.quantity.getText().toString().equals("0")) {
                        viewHolder.quantity.setText("1");
                    }
                }
            }
        });

        viewHolder.quantity.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                //if (actionId == EditorInfo.) {
                //if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                // System.out.println("AFTER ID"+event.getAction());
                //System.out.println("AFTER CHANGED"+event.getKeyCode());
//                System.out.println("AFTER CHANGED"+event.getKeyCode());
                System.out.println("AFTER CHANGED" + EditorInfo.IME_ACTION_DONE);
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED || actionId == EditorInfo.IME_ACTION_DONE) {

                    if (viewHolder.quantity.getText().toString().equals("") || viewHolder.quantity.getText().toString().equals("0")) {
                        viewHolder.quantity.setText("1");
                    } else {
                        if (Integer.parseInt(viewHolder.quantity.getText().toString()) > Integer.parseInt(viewHolder.stock.getTag().toString())) {
                            System.out.println("OUT OF STOCK");

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    context);

                            // set title
                            alertDialogBuilder.setTitle("Cart Alert");


                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("The Quantity is not available in Stock")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, close
                                            // current activity
                                            dialog.cancel();
                                        }
                                    });
                                   /* .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            // the dialog box and do nothing
                                            dialog.cancel();
                                        }
                                    });*/

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();

                            viewHolder.quantity.setText(valueQuantity);

                        } else {
                            HashMap<String, Object> updateString = new HashMap<String, Object>();
                            //updateString.put("quantity", Integer.parseInt(viewHolder.quantity.getText().toString()));
                            updateString.put("quantity", Integer.parseInt(viewHolder.quantity.getText().toString()));


                            JSONObject updateJson = new JSONObject(updateString);
                            System.out.println("AFTER CHANGED+" + updateJson);
                            //submit_btn.performClick();
                   /*System.out.println("AFTER CHANGED");
                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.
                            INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(activity.getParent().getCurrentFocus().getWindowToken(), 0);*/
                            View view = activity_main.getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) activity_main.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                            if (networkInfo != null && networkInfo.isConnected()) {

                                String updateId = viewHolder.prodOverviewTitle.getTag().toString();
                                //new APIClient(activity, context,  new RemoveProductCallBack()).removeProductFromCartCall("55ffc54c1a7da7681500002a");
                                new APIClient(activity_main, context, new UpdateCallBack()).updateCart(updateJson.toString(), updateId);


                            } else {
                                Snackbar.make(activity_main.findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                                        .setActionTextColor(Color.RED)
                                        .show();
                            }
                        }
                    }


                    return true;
                }
              /*  if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

                }*/

                /*if (actionId == KeyEvent.KEYCODE_BACK) {
                    // your code
                    if (viewHolder.quantity.getText().toString().equals("") || viewHolder.quantity.getText().toString().equals("0")) {
                        viewHolder.quantity.setText("1");
                    }
                    return true;
                }*/
                /*if (event.getAction() == KeyEvent.KEYCODE_BACK) {
                    // do your stuff
                    if (viewHolder.quantity.getText().toString().equals("") || viewHolder.quantity.getText().toString().equals("0")) {
                        viewHolder.quantity.setText("1");
                    }
                    return false;
                }*/
               /* if (actionId == EditorInfo.) {
                    performSearch();
                    return true;
                }*/


                return false;
            }


        });
viewHolder.cross.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {



                if (viewHolder.cross.isChecked()) {
viewHolder.cross.setVisibility(View.VISIBLE);
                    // do some operations here
                } else if (!viewHolder.cross.isChecked()) {
                    viewHolder.cross.setVisibility(View.VISIBLE);
                    // do some operations here


                }
    }
});
viewHolder.remove.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        //TextView deleteId = (TextView) v.findViewById(R.id.list_content);
        String deleteId=viewHolder.prodOverviewTitle.getTag().toString();
        System.out.println(viewHolder.prodOverviewTitle.getTag());

        if (networkInfo != null && networkInfo.isConnected()) {


                //new APIClient(activity, context,  new RemoveProductCallBack()).removeProductFromCartCall("55ffc54c1a7da7681500002a");
            new APIClient(activity_main, context,  new RemoveProductCallBack()).removeProductFromCartCall(deleteId);



        } else {
            Snackbar.make(activity_main.findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }


      //  remove(item);
    }
});


        /*viewHolder.countOfProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Snackbar.make(view.findViewById(android.R.id.content), "Clicked", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .show();

            }

        });*/

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return OverViewList.size();
    }




    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        public TextView prodOverviewTitle,prodOverViewText,sellerLabel,stock,totalPrice,newPrice,sellerNameText,remove;
        public Spinner countOfProducts;
        public LinearLayout sellerMainLay;
        public View seperator;
        private RatingBar main_prodRatingBar;
        public CheckBox cross;
        public ImageView productImg;
        public EditText quantity;
        public ProgressBar loadProgressBar;
        public Button increment,decrement;

        //public CheckoutDataObjects data;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            prodOverviewTitle = (TextView) itemLayoutView
                    .findViewById(R.id.OverViewTitle);
            totalPrice=(TextView)itemLayoutView
                    .findViewById(R.id.totalPrice);
            prodOverViewText = (TextView) itemLayoutView
                    .findViewById(R.id.overviewText);
            /*countOfProducts=(Spinner)itemLayoutView
                    .findViewById(R.id.spinner);*/
            cross=(CheckBox)itemLayoutView
                    .findViewById(R.id.cross);
            sellerMainLay=(LinearLayout)itemLayoutView
                    .findViewById(R.id.sellerMainLay);
            remove=(TextView)itemLayoutView
                    .findViewById(R.id.remove);
            sellerLabel=(TextView)itemLayoutView
                    .findViewById(R.id.sellerLabel);
            seperator=(View)itemLayoutView
                    .findViewById(R.id.sellerDivider);
            productImg=(ImageView)itemLayoutView

                    .findViewById(R.id.mainImg);
            sellerNameText=(TextView)itemLayoutView
                    .findViewById(R.id.sellerNameText);
            main_prodRatingBar=(RatingBar)itemLayoutView
                    .findViewById(R.id.main_prodRatingBar);
newPrice=(TextView)itemLayoutView
        .findViewById(R.id.newPrice);
            loadProgressBar = (ProgressBar)itemLayoutView.findViewById(R.id.load_progress_bar);
            quantity=(EditText)itemLayoutView
                    .findViewById(R.id.quantity);
increment=(Button)itemLayoutView
        .findViewById(R.id.quantity_inc);
            decrement=(Button)itemLayoutView
                    .findViewById(R.id.quantity_dec);

            stock=(TextView)itemLayoutView
                    .findViewById(R.id.stock);

        }
        @Override
        public void onClick(View view) {
            System.out.println("onClick " + getPosition());
        }
    }
    /*public void add(Checkout item, int position) {
        OverViewList.add(position, item);
        notifyItemInserted(position);
    }*/

    public void remove(Checkout item) {
        int position = OverViewList.indexOf(item);
        OverViewList.remove(position);
        notifyItemRemoved(position);
    }
    public class RemoveProductCallBack extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                System.out.println("RESPONSE" + response);
                //OverViewList.clear();
                sellerCheck="";


                ((CheckOutActivity)context).refreshData();
                //notifyDataSetChanged();
               // notifyDataSetChanged();
                /*Intent myIntent = new Intent(context, CheckOutActivity.class);
                (context).startActivity(myIntent);*/
                //  JSONArray jsonArray;
//                jsonArray = new JSONArray("COLL");



                /*if(activity!=null){
                    Animation animation = AnimationUtils.loadAnimation(activity, android.R.anim.fade_out);
                    //progressBar.startAnimation(animation);
                }*/
                //progressBar.setVisibility(View.GONE);
                //initializeData();
            } catch (Exception e) {
                e.printStackTrace();
                Snackbar.make(activity_main.findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();
            }
        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            //progressBar.setVisibility(View.VISIBLE);
        }
    }

    public class UpdateCallBack extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                System.out.println("RESPONSE" + response);
                sellerCheck="";


                ((CheckOutActivity)context).refreshData();

            } catch (Exception e) {
                e.printStackTrace();
                Snackbar.make(activity_main.findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();
            }
        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            //progressBar.setVisibility(View.VISIBLE);
        }
    }




    private Bitmap base64ToBitmap(String imageString) {
        byte[] imageAsBytes = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

    }
    private void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity_main.getCurrentFocus().getWindowToken(), 0);

}

   /* @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (viewHolder.quantity.getText().toString().equals("")||viewHolder.quantity.getText().toString().equals("0"))
            {
                viewHolder.quantity.setText("1");
            }
            }
        return false;
    }*/

}
