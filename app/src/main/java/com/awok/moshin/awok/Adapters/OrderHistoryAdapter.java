package com.awok.moshin.awok.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.awok.moshin.awok.Activities.CheckOutActivity;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.OrderHistoryModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.larvalabs.svgandroid.SVG;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shon on 9/29/2015.
 */
public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
    private List<OrderHistoryModel> orderHistoryData = new ArrayList<OrderHistoryModel>();
    private Activity activity;
    View customView;

    private ArrayAdapter<CharSequence> adapter;
    NetworkInfo networkInfo;
    Context context;
    Context mContext;

    String orderCheck="";
    LinearLayout.LayoutParams lp;

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrderHistoryAdapter(Activity activity,List<OrderHistoryModel> orderHistoryData) {


        ConnectivityManager connMgr = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        this.context=activity;
        this.activity=activity;
        this.orderHistoryData=orderHistoryData;
        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.topMargin = 20;
        mContext=activity.getApplication();


    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.order_history_customlayout, null);


        customView=itemLayoutView;

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        final OrderHistoryModel item = orderHistoryData.get(position);

      /*  if(orderCheck.equals(orderHistoryData.get(position).getOrder()))
        {*/


            viewHolder.title.setText(orderHistoryData.get(position).getTitle());
            viewHolder.quantity.setText(orderHistoryData.get(position).getQuantity());
            viewHolder.seller.setTag(orderHistoryData.get(position).getSeller());
            viewHolder.price.setText(orderHistoryData.get(position).getPrice());
viewHolder.image.setImageBitmap(base64ToBitmap(orderHistoryData.get(position).getImageData()));
            viewHolder.totalPrice.setText(orderHistoryData.get(position).getTotalPrice());

            viewHolder.shipping.setText("In Stock : " + orderHistoryData.get(position).getShipping());



      /*  }
        else {
            //customView.setLayoutParams(lp);
            viewHolder.title.setText(orderHistoryData.get(position).getTitle());
            viewHolder.quantity.setText(orderHistoryData.get(position).getQuantity());
            viewHolder.seller.setTag(orderHistoryData.get(position).getSeller());
            viewHolder.price.setText(orderHistoryData.get(position).getPrice());

            viewHolder.totalPrice.setText(orderHistoryData.get(position).getTotalPrice());

            viewHolder.shipping.setText("In Stock : " + orderHistoryData.get(position).getShipping());
            viewHolder.quantity.setText(orderHistoryData.get(position).getQuantity());



        }*/





    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orderHistoryData.size();
    }




    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        public TextView title,quantity,seller,price,totalPrice,shipping;
        public ImageView image;




        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            title = (TextView) itemLayoutView
                    .findViewById(R.id.productTitle);
            quantity = (TextView) itemLayoutView
                    .findViewById(R.id.quantity);

image=(ImageView)itemLayoutView
        .findViewById(R.id.mainImg);
            seller=(TextView)itemLayoutView
                    .findViewById(R.id.sellerName);


            price=(TextView)itemLayoutView
                    .findViewById(R.id.price);



            totalPrice=(TextView)itemLayoutView
                    .findViewById(R.id.total);


            shipping=(TextView)itemLayoutView
                    .findViewById(R.id.shipping);

        }
        @Override
        public void onClick(View view) {
            System.out.println("onClick " + getPosition());
        }
    }
    public void add(OrderHistoryModel item, int position) {
        orderHistoryData.add(position, item);
        notifyItemInserted(position);
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
