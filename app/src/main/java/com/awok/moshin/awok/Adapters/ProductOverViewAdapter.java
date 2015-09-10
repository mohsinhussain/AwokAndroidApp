package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awok.moshin.awok.Models.ProductOverview;

import com.awok.moshin.awok.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shon on 9/10/2015.
 */
public class ProductOverViewAdapter extends RecyclerView.Adapter<ProductOverViewAdapter.ViewHolder> {
    private List<ProductOverview> OverViewList = new ArrayList<ProductOverview>();
    private Activity activity;



        // Provide a suitable constructor (depends on the kind of dataset)
        public ProductOverViewAdapter(Activity activity,List<ProductOverview> overViewList) {
            OverViewList = overViewList;
            activity=activity;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ProductOverViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
            // create a new view
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.prod_features, null);

            // create ViewHolder

            ViewHolder viewHolder = new ViewHolder(itemLayoutView);
            return viewHolder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {

            // - get data from your itemsData at this position
            // - replace the contents of the view with that itemsData

            viewHolder.prodOverViewText.setText(OverViewList.get(position).getOverViewText());
            viewHolder.prodOverviewTitle.setText(OverViewList.get(position).getOverViewTitle());

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return OverViewList.size();
        }

        // inner class to hold a reference to each item of RecyclerView
        public static class ViewHolder extends RecyclerView.ViewHolder {

            public TextView prodOverviewTitle,prodOverViewText;

            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);
                prodOverviewTitle = (TextView) itemLayoutView
                        .findViewById(R.id.OverViewTitle);
                prodOverViewText = (TextView) itemLayoutView
                        .findViewById(R.id.overviewText);

            }
        }


}
