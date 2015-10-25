package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.awok.moshin.awok.Models.DescriptionModel;
import com.awok.moshin.awok.Models.ProductRatingModel;
import com.awok.moshin.awok.R;

import java.util.ArrayList;
import java.util.List;









    public class ProductRatingAdapter extends RecyclerView.Adapter<ProductRatingAdapter.ViewHolder> {
        private List<ProductRatingModel> OverViewList = new ArrayList<ProductRatingModel>();
        private Activity activity;



        // Provide a suitable constructor (depends on the kind of dataset)
        public ProductRatingAdapter(Activity activity,List<ProductRatingModel> overViewList) {
            OverViewList = overViewList;
            activity=activity;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ProductRatingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
            // create a new view
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.store_rating_layout, null);

            // create ViewHolder

            ViewHolder viewHolder = new ViewHolder(itemLayoutView);
            return viewHolder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {

            // - get data from your itemsData at this position
            // - replace the contents of the view with that itemsData
            LayerDrawable reviewRatingColor = (LayerDrawable) viewHolder.main_prodRatingBar.getProgressDrawable();
            reviewRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
            reviewRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
            reviewRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);
            viewHolder.content.setText(OverViewList.get(position).getContent());
            viewHolder.main_prodRatingBar.setRating(Float.parseFloat(OverViewList.get(position).getRate()));
            viewHolder.user.setText(OverViewList.get(position).getUsername());
            viewHolder.days.setText(OverViewList.get(position).getDays());

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return OverViewList.size();
        }

        // inner class to hold a reference to each item of RecyclerView
        public static class ViewHolder extends RecyclerView.ViewHolder {

            public TextView user,content,days;
            public RatingBar main_prodRatingBar;

            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);
                user = (TextView) itemLayoutView
                        .findViewById(R.id.name);
                main_prodRatingBar = (RatingBar) itemLayoutView
                        .findViewById(R.id.main_prodRatingBar);

days=(TextView)itemLayoutView
        .findViewById(R.id.days);

                content = (TextView) itemLayoutView
                        .findViewById(R.id.content);



            }
        }




    }


