package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.OrderSummary;
import com.awok.moshin.awok.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shon on 9/15/2015.
 */
public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder> {
    private List<OrderSummary> OverViewList = new ArrayList<OrderSummary>();
    private Activity activity;
    String sellerCheck="";
    Context mcontext;
    View customView;
    private ArrayAdapter<CharSequence> adapter;
    LinearLayout.LayoutParams lp;

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrderSummaryAdapter(Activity activity,List<OrderSummary> overViewList) {
        OverViewList = overViewList;
        this.activity=activity;
        this.mcontext=activity;
        //adapter = ArrayAdapter.createFromResource(activity, R.array.object_array, android.R.layout.simple_spinner_item);

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter.setDropDownViewResource(R.layout.spinner_drop_down);

        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.topMargin = 20;
        mcontext=activity.getApplication();

    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrderSummaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.order_summary_layout, null);

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

      /*  viewHolder.prodOverViewText.setText(OverViewList.get(position).getOverViewText());
        viewHolder.prodOverviewTitle.setText(OverViewList.get(position).getOverViewTitle());
        //  viewHolder.data = new CheckoutDataObjects(activity);
        viewHolder.countOfProducts.setAdapter(adapter);*/


        if(sellerCheck.equals(OverViewList.get(position).getSellerLabel()))
        {


            viewHolder.prodOverViewText.setText(OverViewList.get(position).getOverViewText()+" AED");
            viewHolder.prodOverviewTitle.setText(OverViewList.get(position).getOverViewTitle());
            viewHolder.prodOverviewTitle.setTag(OverViewList.get(position).getProductId());
            viewHolder.sellerLabel.setText(OverViewList.get(position).getSellerLabel());
            viewHolder.sellerMainLay.setVisibility(View.GONE);
            viewHolder.quantity.setText(OverViewList.get(position).getQuantity());
            viewHolder.seperator.setVisibility(View.VISIBLE);

            viewHolder.productImg.setImageBitmap(base64ToBitmap(OverViewList.get(position).getImageBitmapString()));
            sellerCheck=OverViewList.get(position).getSellerLabel();

        }
        else {
            //customView.setLayoutParams(lp);
            viewHolder.prodOverViewText.setText(OverViewList.get(position).getOverViewText()+" AED");
            viewHolder.prodOverviewTitle.setText(OverViewList.get(position).getOverViewTitle());
            viewHolder.prodOverviewTitle.setTag(OverViewList.get(position).getProductId());

            viewHolder.quantity.setText(OverViewList.get(position).getQuantity());

            viewHolder.sellerLabel.setText("Seller Name : " + OverViewList.get(position).getSellerLabel());
            viewHolder.sellerMainLay.setVisibility(View.VISIBLE);
            viewHolder.seperator.setVisibility(View.GONE);
            viewHolder.productImg.setImageBitmap(base64ToBitmap(OverViewList.get(position).getImageBitmapString()));
            customView.setLayoutParams(lp);

            sellerCheck=OverViewList.get(position).getSellerLabel();
        }



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
    public static class ViewHolder extends RecyclerView.ViewHolder {

       /* public TextView prodOverviewTitle,prodOverViewText;
        public Spinner countOfProducts;*/
        //public CheckoutDataObjects data;
        public TextView prodOverviewTitle,prodOverViewText,sellerLabel,stock;
        public Spinner countOfProducts;
        public LinearLayout sellerMainLay;
        public View seperator;
        public ImageView cross,productImg;
        public TextView quantity;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
           /* prodOverviewTitle = (TextView) itemLayoutView
                    .findViewById(R.id.OverViewTitle);
            prodOverViewText = (TextView) itemLayoutView
                    .findViewById(R.id.overviewText);
            countOfProducts=(Spinner)itemLayoutView
                    .findViewById(R.id.spinner);*/
            prodOverviewTitle = (TextView) itemLayoutView
                    .findViewById(R.id.OverViewTitle);
            prodOverViewText = (TextView) itemLayoutView
                    .findViewById(R.id.overviewText);
            /*countOfProducts=(Spinner)itemLayoutView
                    .findViewById(R.id.spinner);*/

            sellerMainLay=(LinearLayout)itemLayoutView
                    .findViewById(R.id.sellerMainLay);
            sellerLabel=(TextView)itemLayoutView
                    .findViewById(R.id.sellerLabel);
            seperator=(View)itemLayoutView
                    .findViewById(R.id.sellerDivider);
            productImg=(ImageView)itemLayoutView

                    .findViewById(R.id.mainImg);


            quantity=(TextView)itemLayoutView
                    .findViewById(R.id.quantity);




        }
    }

    private Bitmap base64ToBitmap(String imageString) {
        byte[] imageAsBytes = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

    }
    private void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager)mcontext.getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

    }

}
