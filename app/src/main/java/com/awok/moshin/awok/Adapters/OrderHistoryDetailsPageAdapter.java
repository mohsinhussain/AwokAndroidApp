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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
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
public class OrderHistoryDetailsPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OrderHistoryDetailsModel> orderHistoryDetailsData = new ArrayList<OrderHistoryDetailsModel>();
    private Activity activity;
    View customView;
    private  final int TYPE_FOOTER = 2;



    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
/*        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.order_history_details_custom_view, null);


        customView=itemLayoutView;

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);*/
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0) {
            View header = inflater.inflate(R.layout.order_history_details_page_header, parent, false);
            viewHolder = new HeaderViewHolder(header);

        }


        else if (viewType == 2) {
            View footer = inflater.inflate(R.layout.order_history_details_page_footer, parent, false);
            viewHolder = new FooterHolder(footer);
        }

        else {
            View item = inflater.inflate(R.layout.order_history_details_custom_view, parent, false);
            viewHolder = new ViewHolder(item);
        }
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewholder, final int position) {


   /////////////     final OrderHistoryDetailsModel item = orderHistoryDetailsData.get(position);
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
        if (viewholder.getItemViewType() == 0) {
            final HeaderViewHolder headView = (HeaderViewHolder) viewholder;
            headView.orderStatus.setText(orderHistoryDetailsData.get(position).getHeadOrderStatus());
            headView.orderTimeDate.setText(orderHistoryDetailsData.get(position).getHeadOrderTime());
            headView.deliveryEstimate.setText(orderHistoryDetailsData.get(position).getEstimatedDelivery());
            headView.shipmentStatus.setText(orderHistoryDetailsData.get(position).getIsShipped());
            //headView.button.setText(orderHistoryDetailsData.get(position).getHeadOrderMessageCount());
        }


        else if (viewholder.getItemViewType() == 2) {
            final FooterHolder footerView = (FooterHolder) viewholder;
            footerView.shippingPrice.setText(orderHistoryDetailsData.get(position).getFooterShipment());
            footerView.totalPrice.setText(orderHistoryDetailsData.get(position).getTotalAmount());
        }
        else if (viewholder.getItemViewType() == 1) {
            final ViewHolder holder = (ViewHolder) viewholder;
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            imageLoader.get("http://"+orderHistoryDetailsData.get(position).getImage(), new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    holder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.default_img));
                    holder.loadProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        // load image into imageview
                        holder.image.setImageBitmap(response.getBitmap());
                        holder.loadProgressBar.setVisibility(View.GONE);
                    }
                }
            });


            holder.price.setText(orderHistoryDetailsData.get(position).getPrice());
            holder.quantity.setText(orderHistoryDetailsData.get(position).getQuantity() + "X ");
         //   holder.daysData.setText(orderHistoryDetailsData.get(position).getEstimated_days_from() + " days - " + orderHistoryDetailsData.get(position).getEstimated_days_to() + " days");
            holder.title.setText(orderHistoryDetailsData.get(position).getTitle());
          //  holder.seller.setText("Seller: " + orderHistoryDetailsData.get(position).getShipping());
          //  holder.shipping.setTag(orderHistoryDetailsData.get(position).getTitle());

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
        return orderHistoryDetailsData.size();
    }




    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        /*public TextView title,quantity,seller,price,totalPrice,shipping;
        public ImageView image;
        private RelativeLayout main,totalLay;*/
        TextView price,quantity,title,seller,shipping,daysData;
        ImageView image;
        ProgressBar loadProgressBar;




        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            loadProgressBar = (ProgressBar)itemLayoutView.findViewById(R.id.load_progress_bar);
            image=(ImageView)itemLayoutView
                    .findViewById(R.id.mainImg);
daysData=(TextView)itemLayoutView
        .findViewById(R.id.daysData);
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







    @Override
    public int getItemViewType(int position) {

        if (orderHistoryDetailsData.get(position).isHeaderView()) {
            return TYPE_HEADER;

        }



        else if (orderHistoryDetailsData.get(position).isLoader()) {
            return TYPE_FOOTER;

        }


        else if (position > 0) {
            return TYPE_ITEM;
        }

        return 1;
    }









    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView  orderStatus,orderTimeDate,deliveryEstimate,shipmentStatus;
        Button button;


        HeaderViewHolder(View itemView) {
            super(itemView);

            orderStatus=(TextView)itemView.findViewById(R.id.orderStatus);
            orderTimeDate=(TextView)itemView.findViewById(R.id.orderTimeDate);
            deliveryEstimate=(TextView)itemView.findViewById(R.id.deliveryStatus);
            shipmentStatus=(TextView)itemView.findViewById(R.id.shipStatus);
            //RatingBar ratingBar=(RatingBar)mView.findViewById(R.id.main_prodRatingBar);
            button=(Button)itemView.findViewById(R.id.button);

        }
    }


    public static class FooterHolder extends RecyclerView.ViewHolder {

        TextView shippingPrice,totalPrice;

        FooterHolder(View itemView) {
            super(itemView);

            shippingPrice=(TextView)itemView.findViewById(R.id.shippingPrice);
            totalPrice=(TextView)itemView.findViewById(R.id.totalPrice);

        }
    }






}


