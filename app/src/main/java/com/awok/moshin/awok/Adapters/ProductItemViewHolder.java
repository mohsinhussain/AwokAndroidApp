package com.awok.moshin.awok.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awok.moshin.awok.R;

/**
 * Created by mohsin on 11/11/2015.
 */
public class ProductItemViewHolder extends RecyclerView.ViewHolder {
    CardView mCardView;
    //        TextView nameTextView;
    TextView priceTextView;
    //        TextView oldPriceTextView;
    TextView discountTextView;
    ImageView itemImageView;
    LinearLayout container;
    ProgressBar loadProgressBar;
    RelativeLayout priceLayout;

    ProductItemViewHolder(View itemView) {
        super(itemView);
        mCardView = (CardView)itemView.findViewById(R.id.cv);
//            nameTextView = (TextView)itemView.findViewById(R.id.nameTextView);
        priceTextView = (TextView)itemView.findViewById(R.id.priceTextView);
//            oldPriceTextView = (TextView)itemView.findViewById(R.id.oldPriceTextView);
        discountTextView = (TextView)itemView.findViewById(R.id.percentTextView);
        loadProgressBar = (ProgressBar)itemView.findViewById(R.id.load_progress_bar);
        itemImageView = (ImageView)itemView.findViewById(R.id.itemImageView);
        container = (LinearLayout) itemView.findViewById(R.id.parentPanel);
        priceLayout= (RelativeLayout) itemView.findViewById(R.id.priceLayout);
    }
}
