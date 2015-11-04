package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.awok.moshin.awok.Activities.OrderSummaryActivity;
import com.awok.moshin.awok.Activities.OrderSummaryFilter;
import com.awok.moshin.awok.Models.OrderHistoryModel;
import com.awok.moshin.awok.Models.OrderStatusModel;
import com.awok.moshin.awok.Models.ShippingAddressModel;
import com.awok.moshin.awok.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by shon on 10/31/2015.
 */
public class OrderHistoryStatusAdapter extends RecyclerView.Adapter<OrderHistoryStatusAdapter.ViewHolder> {
    private List<OrderStatusModel> orderHistoryData = new ArrayList<OrderStatusModel>();
    private Activity activity;
    View customView;
private String status,id;

    Context mContext;



    // Provide a suitable constructor (depends on the kind of dataset)
    public OrderHistoryStatusAdapter(Activity activity,List<OrderStatusModel> orderHistoryData) {




        this.orderHistoryData=orderHistoryData;

        this.mContext=activity;


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
        if(orderHistoryData.get(position).getIsHeader())

        {

        }


        else
        {

        }
        return -1;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public OrderHistoryStatusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.orders_history_filter, null);




        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {






        final int pos = position;


        final OrderStatusModel item = orderHistoryData.get(position);

        viewHolder.status.setText(orderHistoryData.get(position).getStatusType());
        viewHolder.status.setTag(orderHistoryData.get(position).getStatusId());
        viewHolder.radioStatus.setChecked(orderHistoryData.get(position).isSelected());

        viewHolder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ShippingAddressModel address = (ShippingAddressModel) rb.getTag();

                //address.setIsSelected(rb.isChecked());
                for (int i = 0; i < orderHistoryData.size(); i++) {
                    OrderStatusModel country = orderHistoryData.get(i);
                   /* if (country.getIsSelected()) {
                        responseText.append("\n" + country.getName());
                    }*/
                    orderHistoryData.get(i).setIsSelected(false);
                }
                orderHistoryData.get(pos).setIsSelected(true);

                notifyDataSetChanged();
                status=viewHolder.status.getText().toString();
                id=viewHolder.status.getTag().toString();
                ((OrderSummaryFilter)mContext).getData(status, id);
                System.out.println("DATA" + viewHolder.status.getTag().toString());

            }
        });


        viewHolder.radioStatus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RadioButton rb = (RadioButton) v;
                //ShippingAddressModel address = (ShippingAddressModel) rb.getTag();

                //address.setIsSelected(rb.isChecked());
                for (int i = 0; i < orderHistoryData.size(); i++) {
                    OrderStatusModel country = orderHistoryData.get(i);
                   /* if (country.getIsSelected()) {
                        responseText.append("\n" + country.getName());
                    }*/
                    orderHistoryData.get(i).setIsSelected(false);
                }
                orderHistoryData.get(pos).setIsSelected(true);

                notifyDataSetChanged();
                status=viewHolder.status.getText().toString();
                id=viewHolder.status.getTag().toString();
                ((OrderSummaryFilter)mContext).getData(status, id);
System.out.println("DATA" + viewHolder.status.getTag().toString());
                Toast.makeText(
                        v.getContext(),
                        "Clicked on Checkbox: " + rb.getText() + " is "
                                + rb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });




    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orderHistoryData.size();
    }




    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {


        RadioButton radioStatus;
        TextView status;




        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
          radioStatus=(RadioButton)itemLayoutView
                  .findViewById(R.id.radioStatus);
            status=(TextView)itemLayoutView
                    .findViewById(R.id.status);

        }
        @Override
        public void onClick(View view) {
            System.out.println("onClick " + getPosition());
        }
    }
    public void add(OrderStatusModel item, int position) {
        orderHistoryData.add(position, item);
        notifyItemInserted(position);
    }







    private void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

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

