package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.OrderHistoryDetailsModel;
import com.awok.moshin.awok.Models.OrderHistoryModel;
import com.awok.moshin.awok.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by shon on 9/30/2015.
 */
public class OrderHistoryDetailsPageAdapter extends RecyclerView.Adapter<OrderHistoryDetailsPageAdapter.ViewHolder> {
    private List<OrderHistoryDetailsModel> orderHistoryDetailsData = new ArrayList<OrderHistoryDetailsModel>();
    private Activity activity;
    View customView;

    private ArrayAdapter<CharSequence> adapter;
    NetworkInfo networkInfo;
    Context context;
    Context mContext;

    String orderCheck="";
    LinearLayout.LayoutParams lp;

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrderHistoryDetailsPageAdapter(Activity activity,List<OrderHistoryDetailsModel> orderHistoryDetailsData) {


        ConnectivityManager connMgr = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        this.context=activity;
        this.activity=activity;
        this.orderHistoryDetailsData=orderHistoryDetailsData;
        lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.topMargin = 20;
        mContext=activity.getApplication();


    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrderHistoryDetailsPageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.order_history_details_custom_view, null);


        customView=itemLayoutView;

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        final OrderHistoryDetailsModel item = orderHistoryDetailsData.get(position);
/*
        if(orderCheck.equals(orderHistoryData.get(position).getOrderId()))
        {*/

//viewHolder.main.setVisibility(View.GONE);
        //viewHolder.totalLay.setVisibility(View.GONE);
        //viewHolder.title.setText(orderHistoryData.get(position).getTitle());
        // viewHolder.quantity.setText(orderHistoryData.get(position).getQuantity());
        // viewHolder.seller.setText(orderHistoryData.get(position).getSeller());
        //viewHolder.seller.setTag(orderHistoryData.get(position).getOrderId());
        //  viewHolder.price.setText(orderHistoryData.get(position).getPrice());
//viewHolder.image.setImageBitmap(base64ToBitmap(orderHistoryData.get(position).getImageData()));
        /////viewHolder.orderNo.setText("Order #"+orderHistoryData.get(position).getOrderNo()+" -");
        /////viewHolder.dateTime.setText("placed on "+date(orderHistoryData.get(position).getDateTime().toString()));

        // viewHolder.shipping.setText("In Stock : " + orderHistoryData.get(position).getShipping());
        //orderCheck=orderHistoryData.get(position).getOrderId();
//        viewHolder.image.setImageBitmap(base64ToBitmap(orderHistoryDetailsData.get(position).getImage()));
//        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//        viewHolder.image.setImageUrl(orderHistoryDetailsData.get(position).getImage(), imageLoader);
//        viewHolder.image.setErrorImageResId(R.drawable.default_img);
//        if(orderHistoryDetailsData.get(position).getImage()!=null && !orderHistoryDetailsData.get(position).getImage().equalsIgnoreCase("")){
//            viewHolder.image.setImageUrl(orderHistoryDetailsData.get(position).getImage(), imageLoader);
//        }
//        else{
//            viewHolder.image.setImageDrawable(mContext.getResources().getDrawable((R.drawable.default_img)));
//        }

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(orderHistoryDetailsData.get(position).getImage(), new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                viewHolder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.default_img));
                viewHolder.loadProgressBar.setVisibility(View.GONE);
            }
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    viewHolder.image.setImageBitmap(response.getBitmap());
                    viewHolder.loadProgressBar.setVisibility(View.GONE);
                }
            }
        });


        viewHolder.price.setText(orderHistoryDetailsData.get(position).getPrice() + " AED");
        viewHolder.quantity.setText(orderHistoryDetailsData.get(position).getQuantity()+"X ");

        viewHolder.title.setText(orderHistoryDetailsData.get(position).getTitle());
        viewHolder.seller.setText(orderHistoryDetailsData.get(position).getShipping());
        viewHolder.shipping.setTag(orderHistoryDetailsData.get(position).getTitle());

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

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orderHistoryDetailsData.size();
    }




    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        /*public TextView title,quantity,seller,price,totalPrice,shipping;
        public ImageView image;
        private RelativeLayout main,totalLay;*/
        TextView price,quantity,title,seller,shipping;
        ImageView image;
        ProgressBar loadProgressBar;




        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            loadProgressBar = (ProgressBar)itemLayoutView.findViewById(R.id.load_progress_bar);
            image=(ImageView)itemLayoutView
                    .findViewById(R.id.mainImg);

            price = (TextView) itemLayoutView
                    .findViewById(R.id.price);
            quantity = (TextView) itemLayoutView
                    .findViewById(R.id.quantity);
            title=(TextView)itemLayoutView
                    .findViewById(R.id.OverViewTitle);
            seller=(TextView)itemLayoutView
                    .findViewById(R.id.sellerTxt);
            shipping=(TextView)itemLayoutView
                    .findViewById(R.id.shippingTracking);

        }
        @Override
        public void onClick(View view) {
            System.out.println("onClick " + getPosition());
        }
    }
    public void add(OrderHistoryDetailsModel item, int position) {
        orderHistoryDetailsData.add(position, item);
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

   /* public String date(String date)
    {

        long time = Long.parseLong(date)   * (long) 1000;
        Date date_value = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        Log.d("date", format.format(date_value));
        return format.format(date_value).toString();
    }*/
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


