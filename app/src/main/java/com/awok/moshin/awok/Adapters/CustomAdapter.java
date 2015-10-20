package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shon on 10/20/2015.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private List<Checkout> mDataSet = new ArrayList<Checkout>();
    private final int NO_SHOW = 0, SHOW = 1  ,SHOW_FOOTER=2;
    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView prodOverviewTitle,prodOverViewText,sellerLabel,stock,totalPrice,newPrice,sellerNameText,remove;
        public Spinner countOfProducts;
        public LinearLayout sellerMainLay,footer;
        public View seperator;
        private RatingBar main_prodRatingBar;

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

            prodOverviewTitle = (TextView) itemLayoutView
                    .findViewById(R.id.OverViewTitle);
            totalPrice=(TextView)itemLayoutView
                    .findViewById(R.id.totalPrice);
            prodOverViewText = (TextView) itemLayoutView
                    .findViewById(R.id.overviewText);

/*footer=(LinearLayout)itemLayoutView
        .findViewById(R.id.footer);*/
            sellerMainLay=(LinearLayout)itemLayoutView
                    .findViewById(R.id.sellerMainLay);
            remove=(TextView)itemLayoutView
                    .findViewById(R.id.remove);
            sellerLabel=(TextView)itemLayoutView
                    .findViewById(R.id.sellerLabel);
            seperator=(View)itemLayoutView
                    .findViewById(R.id.sellerDivider);
            productImg=(ImageView)itemLayoutView

                    .findViewById(R.id.mainImg);
            sellerNameText=(TextView)itemLayoutView
                    .findViewById(R.id.sellerNameText);
            main_prodRatingBar=(RatingBar)itemLayoutView
                    .findViewById(R.id.main_prodRatingBar);
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




        }


    }


    @Override
    public int getItemViewType(int position) {
if(position>0) {
    if ((mDataSet.get(position).getSellerLabel().equalsIgnoreCase(mDataSet.get(position - 1).getSellerLabel()))) {


        return NO_SHOW;
    }

   /* else if(position==mDataSet.size() || (mDataSet.get(position).getSellerLabel().equalsIgnoreCase(mDataSet.get(position + 1).getSellerLabel())))
    {
        return SHOW_FOOTER;
    }*/

    else {


        return SHOW;
    }
}
/*else if(position==mDataSet.size())
{
    return SHOW_FOOTER;
}*/
        else
{
    return SHOW;
}

    }

    public CustomAdapter(Activity activity,List<Checkout> overViewList) {
        mDataSet = overViewList;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.checkoutlayout, viewGroup, false);

        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        switch (viewHolder.getItemViewType()) {



            case NO_SHOW:

                viewHolder.sellerMainLay.setVisibility(View.GONE);
                break;


            /*case SHOW_FOOTER:
                viewHolder.footer.setVisibility(View.VISIBLE);*/

            case SHOW:
                viewHolder.sellerMainLay.setVisibility(View.VISIBLE);
                break;
        }
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.newPrice.setText("AED " + mDataSet.get(position).getOverViewText());
        viewHolder.prodOverViewText.setText("AED " + mDataSet.get(position).getOldPrice());
        viewHolder.prodOverviewTitle.setText(mDataSet.get(position).getOverViewTitle());
        viewHolder.prodOverviewTitle.setTag(mDataSet.get(position).getProductId());
        viewHolder.totalPrice.setText("Total Price :" + mDataSet.get(position).getTotalPrice() + " AED");
        viewHolder.quantity.setText(mDataSet.get(position).getQuantity());

        viewHolder.stock.setText("Free Shipping");
        viewHolder.sellerNameText.setText("Seller :" + mDataSet.get(position).getSellerLabel());
        viewHolder.stock.setTag(mDataSet.get(position).getRemainingStock());


        viewHolder.main_prodRatingBar.setRating(4);

    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
