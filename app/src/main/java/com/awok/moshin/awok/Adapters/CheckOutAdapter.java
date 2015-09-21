package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.CheckoutDataObjects;
import com.awok.moshin.awok.Models.ProductOverview;
import com.awok.moshin.awok.Models.Products;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    String sellerCheck="";

    // Provide a suitable constructor (depends on the kind of dataset)
    public CheckOutAdapter(Activity activity,List<Checkout> overViewList) {
        OverViewList = overViewList;
        activity=activity;
        adapter = ArrayAdapter.createFromResource(activity, R.array.object_array, android.R.layout.simple_spinner_item);

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.spinner_drop_down);
        ConnectivityManager connMgr = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);
         networkInfo = connMgr.getActiveNetworkInfo();
        context=activity.getApplicationContext();


    }

    // Create new views (invoked by the layout manager)
    @Override
    public CheckOutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.checkoutlayout, null);

        // create ViewHolder

        customView=itemLayoutView;

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.topMargin = 20;
        final Checkout item = OverViewList.get(position);
        System.out.println("GOAL"+sellerCheck);
        System.out.println(OverViewList.get(position).getSellerLabel());
        if(sellerCheck.equals(OverViewList.get(position).getSellerLabel()))
        {


            viewHolder.prodOverViewText.setText(OverViewList.get(position).getOverViewText());
            viewHolder.prodOverviewTitle.setText(OverViewList.get(position).getOverViewTitle());
            viewHolder.sellerLabel.setText(OverViewList.get(position).getSellerLabel());
            viewHolder.sellerMainLay.setVisibility(View.GONE);
            viewHolder.seperator.setVisibility(View.VISIBLE);
            sellerCheck=OverViewList.get(position).getSellerLabel();

        }
        else
        {
            customView.setLayoutParams(lp);
            viewHolder.prodOverViewText.setText(OverViewList.get(position).getOverViewText());
            viewHolder.prodOverviewTitle.setText(OverViewList.get(position).getOverViewTitle());
            viewHolder.sellerLabel.setText("Seller Name :"+OverViewList.get(position).getSellerLabel());
            viewHolder.sellerMainLay.setVisibility(View.VISIBLE);
            viewHolder.seperator.setVisibility(View.GONE);
            //customView.setLayoutParams(lp);

            sellerCheck=OverViewList.get(position).getSellerLabel();
        }

      //  viewHolder.data = new CheckoutDataObjects(activity);
        viewHolder.countOfProducts.setAdapter(adapter);



viewHolder.prodOverviewTitle.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        if (networkInfo != null && networkInfo.isConnected()) {


                new APIClient(activity, context,  new RemoveProductCallBack()).removeProductFromCartCall("55ffc54c1a7da7681500002a");


        } else {
            Snackbar.make(activity.findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }


        remove(item);
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

        public TextView prodOverviewTitle,prodOverViewText,sellerLabel;
        public Spinner countOfProducts;
        public LinearLayout sellerMainLay;
        public View seperator;

        //public CheckoutDataObjects data;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            prodOverviewTitle = (TextView) itemLayoutView
                    .findViewById(R.id.OverViewTitle);
            prodOverViewText = (TextView) itemLayoutView
                    .findViewById(R.id.overviewText);
            countOfProducts=(Spinner)itemLayoutView
                    .findViewById(R.id.spinner);
            sellerMainLay=(LinearLayout)itemLayoutView
                    .findViewById(R.id.sellerMainLay);
            sellerLabel=(TextView)itemLayoutView
                    .findViewById(R.id.sellerLabel);
            seperator=(View)itemLayoutView
                    .findViewById(R.id.sellerDivider);

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
                System.out.println("RESPONSE"+response);
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
}
