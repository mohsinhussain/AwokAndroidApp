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
import android.util.Log;
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
import android.widget.RelativeLayout;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by shon on 9/29/2015.
 */
public class OrderHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OrderHistoryModel> orderHistoryData = new ArrayList<OrderHistoryModel>();
    private Activity activity;
    View customView;
    private final int SHOW_HEADER=0,NO_SHOW=1;
    private ArrayAdapter<CharSequence> adapter;
    NetworkInfo networkInfo;
    Context context;
    Context mContext;
    private  final int TYPE_FOOTER = 2;
    private  final int TYPE_ITEM = 3;
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
      /*  View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.order_history_customlayout, null);


        customView=itemLayoutView;

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;*/
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

     //   Status: Ready for Shipment

         if (viewType == 2) {
            View footer = inflater.inflate(R.layout.footer_product_progressbar, parent, false);
            viewHolder = new FooterHolder(footer);
        }

        else {
            View item = inflater.inflate(R.layout.order_history_customlayout, parent, false);
            viewHolder = new itemHolder(item);
        }
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final  RecyclerView.ViewHolder viewHolder, final int position) {


        final OrderHistoryModel item = orderHistoryData.get(position);
/*
        if(orderCheck.equals(orderHistoryData.get(position).getOrderId()))
        {*/




        if (viewHolder.getItemViewType() == 2) {
            final FooterHolder footer = (FooterHolder) viewHolder;
        }
        else {


            final itemHolder holder = (itemHolder) viewHolder;


        /*switch (viewHolder.getItemViewType()) {


            case SHOW_HEADER:

                viewHolder.headLayMonth.setVisibility(View.VISIBLE);

                break;


            case NO_SHOW:
                viewHolder.headLayMonth.setVisibility(View.GONE);
                break;
        }*/
            System.out.println("ISHEADER" + orderHistoryData.get(position).getIsHeader());

            if (orderHistoryData.get(position).getIsHeader()) {
                holder.headLayMonth.setVisibility(View.VISIBLE);
            } else {
                holder.headLayMonth.setVisibility(View.GONE);
            }


//viewHolder.main.setVisibility(View.GONE);
            //viewHolder.totalLay.setVisibility(View.GONE);
            //viewHolder.title.setText(orderHistoryData.get(position).getTitle());
            // viewHolder.quantity.setText(orderHistoryData.get(position).getQuantity());
            // viewHolder.seller.setText(orderHistoryData.get(position).getSeller());
            //viewHolder.seller.setTag(orderHistoryData.get(position).getOrderId());
            //  viewHolder.price.setText(orderHistoryData.get(position).getPrice());
//viewHolder.image.setImageBitmap(base64ToBitmap(orderHistoryData.get(position).getImageData()));
            holder.orderNo.setText("Order #" + orderHistoryData.get(position).getOrderNo());
            holder.orderNo.setTag(orderHistoryData.get(position).getOrderId());
            //  viewHolder.dateTime.setText("placed on " + date(orderHistoryData.get(position).getDateTime().toString()));
            holder.dateTime.setText("placed on " + orderHistoryData.get(position).getDateTime().toString());
            holder.monthValue.setText(orderHistoryData.get(position).getHeader());
holder.status.setText("Status: "+orderHistoryData.get(position).getStatus());
            // viewHolder.shipping.setText("In Stock : " + orderHistoryData.get(position).getShipping());
            //orderCheck=orderHistoryData.get(position).getOrderId();


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

            //orderCheck=orderHistoryData.get(position).getOrderId();

        }*/


        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orderHistoryData.size();
    }




    // inner class to hold a reference to each item of RecyclerView
    public static class itemHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        /*public TextView title,quantity,seller,price,totalPrice,shipping;
        public ImageView image;
        private RelativeLayout main,totalLay;*/
        TextView orderNo,dateTime;
        LinearLayout headLayMonth;
        TextView monthValue;
        TextView status;




        public itemHolder(View itemLayoutView) {
            super(itemLayoutView);
            /*title = (TextView) itemLayoutView
                    .findViewById(R.id.productTitle);
            quantity = (TextView) itemLayoutView
                    .findViewById(R.id.quantity);

image=(ImageView)itemLayoutView
        .findViewById(R.id.mainImg);
            seller=(TextView)itemLayoutView
                    .findViewById(R.id.sellerName);


            price=(TextView)itemLayoutView
                    .findViewById(R.id.price);

main=(RelativeLayout)itemLayoutView
        .findViewById(R.id.head);




            totalLay=(RelativeLayout)itemLayoutView
                    .findViewById(R.id.totalLay);




            totalPrice=(TextView)itemLayoutView
                    .findViewById(R.id.total);


            shipping=(TextView)itemLayoutView
                    .findViewById(R.id.shipping);*/
            orderNo = (TextView) itemLayoutView
                    .findViewById(R.id.order_no);
            dateTime = (TextView) itemLayoutView
                    .findViewById(R.id.date_time);
            headLayMonth=(LinearLayout)itemLayoutView
                    .findViewById(R.id.headLayMonth);
            monthValue=(TextView)itemLayoutView
                    .findViewById(R.id.monthValue);
            status=(TextView)itemLayoutView
                    .findViewById(R.id.status);
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



    public static class FooterHolder extends RecyclerView.ViewHolder {



        FooterHolder(View itemView) {
            super(itemView);


        }
    }



    private void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

    }
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
      /*  if(orderHistoryData.get(position).getIsHeader())

        {
            System.out.println("SHOW");
return SHOW_HEADER;
        }


        else
        {
            System.out.println("NO SHOW");
return NO_SHOW;
        }*/




         if (orderHistoryData.get(position).isLoader()) {
            return TYPE_FOOTER;

        }


        else if (position >= 0) {
            return TYPE_ITEM;
        }

        return 1;

    }
public String date(String date)
{

    long time = Long.parseLong(date)   * (long) 1000;
    Date date_value = new Date(time);
    SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
   // format.setTimeZone(TimeZone.getTimeZone("GMT"));
   // System.out.println("FJHdSJD" + TimeZone.getTimeZone("GMT"));
    Calendar cal = Calendar.getInstance();
    TimeZone tz = cal.getTimeZone();
    format.setTimeZone(tz);

    System.out.println("Time zone" + tz.getDisplayName());
    Log.d("date", format.format(date_value).toString());
    return format.format(date_value).toString();
}

}
