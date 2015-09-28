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
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.awok.moshin.awok.Activities.CheckOutActivity;
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
    private Activity activity;
    View customView;

    private ArrayAdapter<CharSequence> adapter;
    NetworkInfo networkInfo;
    Context context;
    Context mContext;
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
        context=activity;
        activity=activity;
         lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.topMargin = 20;
        mContext=activity.getApplication();
        //svg = SVGParser.getSVGFromResource(activity.getResources(),R.raw.cross);

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


        // Get a drawable from the parsed SVG and set it as the drawable for the ImageView

        //viewHolder.productImg.setImageDrawable(svg.createPictureDrawable());
        final Checkout item = OverViewList.get(position);

        if(sellerCheck.equals(OverViewList.get(position).getSellerLabel()))
        {


            viewHolder.prodOverViewText.setText(OverViewList.get(position).getOverViewText()+" AED");
            viewHolder.prodOverviewTitle.setText(OverViewList.get(position).getOverViewTitle());
            viewHolder.prodOverviewTitle.setTag(OverViewList.get(position).getProductId());
            viewHolder.sellerLabel.setText(OverViewList.get(position).getSellerLabel());
            viewHolder.sellerMainLay.setVisibility(View.GONE);
            viewHolder.quantity.setText(OverViewList.get(position).getQuantity());
            viewHolder.seperator.setVisibility(View.VISIBLE);
            viewHolder.stock.setText("In Stock : " + OverViewList.get(position).getRemainingStock());
            viewHolder.stock.setTag(OverViewList.get(position).getRemainingStock());
            viewHolder.productImg.setImageBitmap(base64ToBitmap(OverViewList.get(position).getImageBitmapString()));
            sellerCheck=OverViewList.get(position).getSellerLabel();

        }
        else {
            //customView.setLayoutParams(lp);
            viewHolder.prodOverViewText.setText(OverViewList.get(position).getOverViewText()+" AED");
            viewHolder.prodOverviewTitle.setText(OverViewList.get(position).getOverViewTitle());
            viewHolder.prodOverviewTitle.setTag(OverViewList.get(position).getProductId());
            viewHolder.stock.setText("In Stock : " + OverViewList.get(position).getRemainingStock());
            viewHolder.quantity.setText(OverViewList.get(position).getQuantity());
            viewHolder.stock.setTag(OverViewList.get(position).getRemainingStock());
            viewHolder.sellerLabel.setText("Seller Name : " + OverViewList.get(position).getSellerLabel());
            viewHolder.sellerMainLay.setVisibility(View.VISIBLE);
            viewHolder.seperator.setVisibility(View.GONE);
            viewHolder.productImg.setImageBitmap(base64ToBitmap(OverViewList.get(position).getImageBitmapString()));
            customView.setLayoutParams(lp);

            sellerCheck=OverViewList.get(position).getSellerLabel();
        }

      //  viewHolder.data = new CheckoutDataObjects(activity);
        /*viewHolder.countOfProducts.setAdapter(adapter);*/
/*viewHolder.quantity.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       // viewHolder.quantity.getText().clear();
    }
});*/
/*viewHolder.quantity.addTextChangedListener(new TextWatcher() {


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
});*/


        viewHolder.quantity.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                //if (actionId == EditorInfo.) {
               //if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
               // System.out.println("AFTER ID"+event.getAction());
                //System.out.println("AFTER CHANGED"+event.getKeyCode());
                System.out.println("AFTER CHANGED"+actionId);
                System.out.println("AFTER CHANGED"+EditorInfo.IME_ACTION_DONE);
                if(actionId == EditorInfo.IME_ACTION_UNSPECIFIED)
                {

                    if(viewHolder.quantity.getText().toString().equals(""))
                    {
                        viewHolder.quantity.setText("1");
                    }
                    else {
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

                        }




                        else {
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
                            if (networkInfo != null && networkInfo.isConnected()) {

                                String updateId = viewHolder.prodOverviewTitle.getTag().toString();
                                //new APIClient(activity, context,  new RemoveProductCallBack()).removeProductFromCartCall("55ffc54c1a7da7681500002a");
                                new APIClient(activity, context, new UpdateCallBack()).updateCart(updateJson.toString(), updateId);


                            } else {
                                Snackbar.make(activity.findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
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

viewHolder.cross.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        //TextView deleteId = (TextView) v.findViewById(R.id.list_content);
        String deleteId=viewHolder.prodOverviewTitle.getTag().toString();
        System.out.println(viewHolder.prodOverviewTitle.getTag());

        if (networkInfo != null && networkInfo.isConnected()) {


                //new APIClient(activity, context,  new RemoveProductCallBack()).removeProductFromCartCall("55ffc54c1a7da7681500002a");
            new APIClient(activity, context,  new RemoveProductCallBack()).removeProductFromCartCall(deleteId);



        } else {
            Snackbar.make(activity.findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
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

        public TextView prodOverviewTitle,prodOverViewText,sellerLabel,stock;
        public Spinner countOfProducts;
        public LinearLayout sellerMainLay;
        public View seperator;
        public ImageView cross,productImg;
        public EditText quantity;

        //public CheckoutDataObjects data;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            prodOverviewTitle = (TextView) itemLayoutView
                    .findViewById(R.id.OverViewTitle);
            prodOverViewText = (TextView) itemLayoutView
                    .findViewById(R.id.overviewText);
            /*countOfProducts=(Spinner)itemLayoutView
                    .findViewById(R.id.spinner);*/
            cross=(ImageView)itemLayoutView
                    .findViewById(R.id.cross);
            sellerMainLay=(LinearLayout)itemLayoutView
                    .findViewById(R.id.sellerMainLay);
            sellerLabel=(TextView)itemLayoutView
                    .findViewById(R.id.sellerLabel);
            seperator=(View)itemLayoutView
                    .findViewById(R.id.sellerDivider);
            productImg=(ImageView)itemLayoutView

                    .findViewById(R.id.mainImg);


            quantity=(EditText)itemLayoutView
                    .findViewById(R.id.quantity);


            stock=(TextView)itemLayoutView
                    .findViewById(R.id.stock);

        }
        @Override
        public void onClick(View view) {
            System.out.println("onClick " + getPosition());
        }
    }
    public void add(Checkout item, int position) {
        OverViewList.add(position, item);
        notifyItemInserted(position);
    }

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
                Snackbar.make(activity.findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
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
                Snackbar.make(activity.findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
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
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

}
}
