package com.awok.moshin.awok.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.Activities.CheckOutActivity;
import com.awok.moshin.awok.Activities.OrderSummaryActivity;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.OrderSummary;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shon on 10/21/2015.
 */
public class OrderSummaryCustomAdapter extends RecyclerView.Adapter<OrderSummaryCustomAdapter.ViewHolder> {

        private static final String TAG = "CustomAdapter";
        View customView;
        private List<OrderSummary> mDataSet = new ArrayList<OrderSummary>();
        //private final int NO_SHOW = 0, SHOW = 1  ,SHOW_FOOTER=2,HIDE_FOOTER=3;
        private final int SHOW_HEADER=0,SHOW_FOOTER=1,SHOW_BOTH=2,HIDE=3;
        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
        /**
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */
        NetworkInfo networkInfo;
        private Activity activity_main;
        private String valueQuantity;
        LinearLayout.LayoutParams lp;
        Context mContext;
        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final TextView prodOverviewTitle,prodOverViewText,sellerLabel,stock,totalPrice,newPrice,sellerNameText,discount,sellerTotal,sellerShipping,sellerSubTotal;
            public Spinner countOfProducts;
            public LinearLayout sellerMainLay;
            RelativeLayout footer;
            public View seperator;
            private RatingBar main_prodRatingBar;
            private TextView remove;
            public ImageView productImg;
            public EditText quantity;
            public ProgressBar loadProgressBar;
            public Button increment,decrement;

            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);
                // Define click listener for the ViewHolder's View.
                itemLayoutView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "Element " + getPosition() + " clicked.");
                    }
                });
                sellerSubTotal=(TextView)itemLayoutView
                        .findViewById(R.id.sellerSubTotal);

                sellerTotal=(TextView)itemLayoutView
                        .findViewById(R.id.sellerTotal);




                sellerShipping=(TextView)itemLayoutView
                        .findViewById(R.id.sellerShipping);
                prodOverviewTitle = (TextView) itemLayoutView
                        .findViewById(R.id.OverViewTitle);
                totalPrice=(TextView)itemLayoutView
                        .findViewById(R.id.totalPrice);
                prodOverViewText = (TextView) itemLayoutView
                        .findViewById(R.id.overviewText);

                footer=(RelativeLayout)itemLayoutView
                        .findViewById(R.id.footer);
                sellerMainLay=(LinearLayout)itemLayoutView
                        .findViewById(R.id.sellerMainLay);
                remove=(TextView)itemLayoutView
                        .findViewById(R.id.remove);
                sellerLabel=(TextView)itemLayoutView
                        .findViewById(R.id.sellerLabel);
                discount=(TextView)itemLayoutView
                        .findViewById(R.id.sellerLabelText);
                seperator=(View)itemLayoutView
                        .findViewById(R.id.sellerDivider);
                productImg=(ImageView)itemLayoutView

                        .findViewById(R.id.mainImg);
                sellerNameText=(TextView)itemLayoutView
                        .findViewById(R.id.sellerNameText);
                //main_prodRatingBar=(RatingBar)itemLayoutView
                // .findViewById(R.id.main_prodRatingBar);
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
                remove=(TextView)itemLayoutView
                        .findViewById(R.id.remove);
                productImg=(ImageView)itemLayoutView

                        .findViewById(R.id.mainImg);

                loadProgressBar = (ProgressBar)itemLayoutView.findViewById(R.id.load_progress_bar);


            }


        }


        @Override
        public int getItemViewType(int position) {
            if(mDataSet.get(position).getIsHeader() && mDataSet.get(position).getIsFooter())

            {
                return SHOW_BOTH;
            }
            else  if(mDataSet.get(position).getIsHeader())
            {
                return SHOW_HEADER;
            }
            else if(mDataSet.get(position).getIsFooter())
            {
                return SHOW_FOOTER;
            }
            else
            {
                return HIDE;
            }




       /* if(position>0) {
            if ((mDataSet.get(position).getSellerLabel().equalsIgnoreCase(mDataSet.get(position - 1).getSellerLabel()))) {
                output=NO_SHOW;
            }
            else {
                output=SHOW;
            }
        }
        else
        {
            output=SHOW;
        }*/

//        if(position==(mDataSet.size()-1)) {
//            output=SHOW_FOOTER;
//        }
//        else
//        {
//            output= HIDE_FOOTER;
//        }
//return output;
        }

        public OrderSummaryCustomAdapter(Activity activity,List<OrderSummary> overViewList) {
            mDataSet = overViewList;
            ConnectivityManager connMgr = (ConnectivityManager)
                    activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo = connMgr.getActiveNetworkInfo();
            lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            lp.topMargin = 20;
            this.mContext=activity;
            this.activity_main=activity;

        }

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
//            View v = LayoutInflater.from(viewGroup.getContext())
//                    .inflate(R.layout.order_summary_layout, viewGroup, false);
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.checkoutlayout, viewGroup, false);
            customView=v;
            return new ViewHolder(v);
        }
        // END_INCLUDE(recyclerViewOnCreateViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");
            switch (viewHolder.getItemViewType()) {



                case SHOW_BOTH:

                    viewHolder.sellerMainLay.setVisibility(View.VISIBLE);
                    viewHolder.footer.setVisibility(View.VISIBLE);
                    //customView.setLayoutParams(lp);
                    break;


            /*case SHOW_FOOTER:
                viewHolder.footer.setVisibility(View.VISIBLE);*/

                case SHOW_HEADER:
                    viewHolder.sellerMainLay.setVisibility(View.VISIBLE);
                    viewHolder.footer.setVisibility(View.GONE);
                    break;

                case SHOW_FOOTER:
                    viewHolder.sellerMainLay.setVisibility(View.GONE);
                    viewHolder.footer.setVisibility(View.VISIBLE);
                    //customView.setLayoutParams(lp);
                    break;

                case HIDE:
                    viewHolder.sellerMainLay.setVisibility(View.GONE);
                    viewHolder.footer.setVisibility(View.GONE);
                    break;


            }
            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.newPrice.setText("AED " + mDataSet.get(position).getOverViewText());
            viewHolder.prodOverViewText.setText("AED " + mDataSet.get(position).getOldPrice());
            viewHolder.prodOverViewText.setPaintFlags(viewHolder.prodOverViewText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.prodOverviewTitle.setText(mDataSet.get(position).getOverViewTitle());
            viewHolder.prodOverviewTitle.setTag(mDataSet.get(position).getProductId());
            viewHolder.totalPrice.setText("Total Price :" + mDataSet.get(position).getTotalPrice() + " AED");
            viewHolder.quantity.setText(mDataSet.get(position).getQuantity());
            viewHolder.discount.setText(mDataSet.get(position).getDiscount() + "%");
            viewHolder.stock.setText("Free Shipping");
            viewHolder.sellerNameText.setText(mDataSet.get(position).getSellerLabel());
            viewHolder.stock.setTag(mDataSet.get(position).getRemainingStock());
            viewHolder.sellerSubTotal.setText(mDataSet.get(position).getSellerSubTotal());
            viewHolder.sellerShipping.setText(mDataSet.get(position).getSellerShipping());
            viewHolder.sellerTotal.setText(mDataSet.get(position).getSellerTotal());
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            imageLoader.get(mDataSet.get(position).getImageBitmapString(), new ImageLoader.ImageListener() {
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







            viewHolder.increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int addQuantity = Integer.parseInt(viewHolder.quantity.getText().toString());
                    addQuantity = addQuantity + 1;
                    viewHolder.quantity.setText(String.valueOf(addQuantity));

                    if (viewHolder.quantity.getText().toString().equals("") || viewHolder.quantity.getText().toString().equals("0")) {
                        viewHolder.quantity.setText("1");
                    } else {
                        if (Integer.parseInt(viewHolder.quantity.getText().toString()) > Integer.parseInt(viewHolder.stock.getTag().toString())) {
                            System.out.println("OUT OF STOCK");

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    mContext);

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
                                new APIClient(activity_main, mContext, new UpdateCallBack()).updateCart(updateJson.toString(), updateId);


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

                    int addQuantity = Integer.parseInt(viewHolder.quantity.getText().toString());
                    addQuantity = addQuantity - 1;
                    viewHolder.quantity.setText(String.valueOf(addQuantity));

                    if (viewHolder.quantity.getText().toString().equals("") || viewHolder.quantity.getText().toString().equals("0")) {
                        viewHolder.quantity.setText("1");
                    } else {
                        if (Integer.parseInt(viewHolder.quantity.getText().toString()) > Integer.parseInt(viewHolder.stock.getTag().toString())) {
                            System.out.println("OUT OF STOCK");

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    mContext);

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
                                new APIClient(activity_main, mContext, new UpdateCallBack()).updateCart(updateJson.toString(), updateId);


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
                                        mContext);

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
                                    new APIClient(activity_main, mContext, new UpdateCallBack()).updateCart(updateJson.toString(), updateId);


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
       /* viewHolder.remove.setOnClickListener(new View.OnClickListener() {
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
        });*/
            viewHolder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //TextView deleteId = (TextView) v.findViewById(R.id.list_content);
                    String deleteId=viewHolder.prodOverviewTitle.getTag().toString();
                    System.out.println(viewHolder.prodOverviewTitle.getTag());

                    if (networkInfo != null && networkInfo.isConnected()) {


                        //new APIClient(activity, context,  new RemoveProductCallBack()).removeProductFromCartCall("55ffc54c1a7da7681500002a");
                        new APIClient(activity_main, mContext,  new RemoveProductCallBack()).removeProductFromCartCall(deleteId);



                    } else {
                        Snackbar.make(activity_main.findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .show();
                    }


                    //  remove(item);
                }
            });

//        viewHolder.main_prodRatingBar.setRating(4);

        }
        // END_INCLUDE(recyclerViewOnBindViewHolder)

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataSet.size();
        }



        public void remove(Checkout item) {
            int position = mDataSet.indexOf(item);
            mDataSet.remove(position);
            notifyItemRemoved(position);
        }
        public class RemoveProductCallBack extends AsyncCallback {
            public void onTaskComplete(String response) {
                try {
                    System.out.println("RESPONSE" + response);
                    //OverViewList.clear();


                    ((OrderSummaryActivity)mContext).refreshData();
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



                    ((OrderSummaryActivity)mContext).refreshData();

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
    }


