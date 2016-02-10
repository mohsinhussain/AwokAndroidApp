package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.Checkout_Model;
import com.awok.moshin.awok.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shon on 1/19/2016.
 */
public class ContactsAdapter extends
        RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    Context mContext;
    private final int SHOW_HEADER=0,SHOW_FOOTER=1,SHOW_BOTH=2,HIDE=3;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
ArrayList<Checkout_Model> overViewList=new ArrayList<>();

    public ContactsAdapter(Context activity,ArrayList<Checkout_Model> overViewList) {
        this.overViewList = overViewList;
        this.mContext=activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checkoutlayout, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
       /* if (overViewList.get(position).getIsHeader() && overViewList.get(position).getIsFooter())

        {
            return SHOW_BOTH;
        } else*/ if (overViewList.get(position).getIsHeader()) {
            return SHOW_HEADER;
        } /*else if (overViewList.get(position).getIsFooter()) {
            return SHOW_FOOTER;
        }*/ else {
            return HIDE;
        }
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        // - get data from your itemsData at this position
        if (viewHolder.getItemViewType() == 0) {
            // - replace the contents of the view with that itemsData
            viewHolder.newPrice.setText("AED " + overViewList.get(position).getPrice());
            System.out.println("DATA" + "AED " + overViewList.get(position).getPrice());
            viewHolder.prodOverViewText.setText("AED " + overViewList.get(position).getOld_price());
            viewHolder.prodOverViewText.setPaintFlags(viewHolder.prodOverViewText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.prodOverviewTitle.setText(overViewList.get(position).getName());
            //viewHolder.prodOverviewTitle.setTag(mDataSet.get(position).getProduct_id());
            viewHolder.prodOverviewTitle.setTag(overViewList.get(position).getId());
            viewHolder.totalPrice.setText("Total Price :" + overViewList.get(position).getPrice() + " AED");
            viewHolder.quantity.setText(overViewList.get(position).getQuantity());
            //viewHolder.discount.setText(mDataSet.get(position).getDiscount() + "%");
            //viewHolder.stock.setText("Free Shipping");
            viewHolder.stock.setText("11");
            // viewHolder.sellerNameText.setText(mDataSet.get(position).getSellerLabel());
            //viewHolder.stock.setTag(mDataSet.get(position).getRemainingStock());
            viewHolder.stock.setTag(11);
            /*imageLoader.get("http://" +
                            overViewList.get(position).getImage(),
                    new ImageLoader.ImageListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            viewHolder.productImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.default_img));
                            viewHolder.loadProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                            if (response.getBitmap() != null) {
                                // load image into imageview
                                System.out.println("IMAGEfmhgjxhfjx" + overViewList.get(position).getImage());
                                viewHolder.productImg.setImageBitmap(response.getBitmap());
                                viewHolder.loadProgressBar.setVisibility(View.GONE);
                            }
                        }
                    });
            System.out.println("CRAZY" + position);*/
        }


    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView prodOverviewTitle,prodOverViewText,sellerLabel,stock,totalPrice,newPrice,sellerNameText,discount,sellerTotal,sellerShipping,sellerSubTotal;
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


            sellerSubTotal = (TextView) itemLayoutView
                    .findViewById(R.id.sellerSubTotal);

            sellerTotal = (TextView) itemLayoutView
                    .findViewById(R.id.sellerTotal);


            sellerShipping = (TextView) itemLayoutView
                    .findViewById(R.id.sellerShipping);


            prodOverviewTitle = (TextView) itemLayoutView
                    .findViewById(R.id.OverViewTitle);
            totalPrice = (TextView) itemLayoutView
                    .findViewById(R.id.totalPrice);
            prodOverViewText = (TextView) itemLayoutView
                    .findViewById(R.id.overviewText);

            footer = (RelativeLayout) itemLayoutView
                    .findViewById(R.id.footer);
            sellerMainLay = (LinearLayout) itemLayoutView
                    .findViewById(R.id.sellerMainLay);
            remove = (TextView) itemLayoutView
                    .findViewById(R.id.remove);
            sellerLabel = (TextView) itemLayoutView
                    .findViewById(R.id.sellerLabel);
            seperator = (View) itemLayoutView
                    .findViewById(R.id.sellerDivider);
            discount = (TextView) itemLayoutView
                    .findViewById(R.id.sellerLabelText);
            productImg = (ImageView) itemLayoutView

                    .findViewById(R.id.mainImg);
            sellerNameText = (TextView) itemLayoutView
                    .findViewById(R.id.sellerNameText);
            //main_prodRatingBar=(RatingBar)itemLayoutView
            // .findViewById(R.id.main_prodRatingBar);
            newPrice = (TextView) itemLayoutView
                    .findViewById(R.id.newPrice);
            loadProgressBar = (ProgressBar) itemLayoutView.findViewById(R.id.load_progress_bar);
            quantity = (EditText) itemLayoutView
                    .findViewById(R.id.quantity);
            increment = (Button) itemLayoutView
                    .findViewById(R.id.quantity_inc);
            decrement = (Button) itemLayoutView
                    .findViewById(R.id.quantity_dec);

            stock = (TextView) itemLayoutView
                    .findViewById(R.id.stock);
            remove = (TextView) itemLayoutView
                    .findViewById(R.id.remove);
            productImg = (ImageView) itemLayoutView

                    .findViewById(R.id.mainImg);

            loadProgressBar = (ProgressBar) itemLayoutView.findViewById(R.id.load_progress_bar);
        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return overViewList.size();
    }
}