package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awok.moshin.awok.Models.DescriptionModel;
import com.awok.moshin.awok.Models.ProductOverview;
import com.awok.moshin.awok.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shon on 10/24/2015.
 */

    public class DescriptionAdapter extends RecyclerView.Adapter<DescriptionAdapter.ViewHolder> {
        private List<DescriptionModel> OverViewList = new ArrayList<DescriptionModel>();
        private Activity activity;



        // Provide a suitable constructor (depends on the kind of dataset)
        public DescriptionAdapter(Activity activity,List<DescriptionModel> overViewList) {
            OverViewList = overViewList;
            activity=activity;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public DescriptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
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

            viewHolder.header.setText(OverViewList.get(position).getDescHeader());
            viewHolder.data.setText(OverViewList.get(position).getDescData());

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return OverViewList.size();
        }

        // inner class to hold a reference to each item of RecyclerView
        public static class ViewHolder extends RecyclerView.ViewHolder {

            public TextView header,data;

            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);
                header = (TextView) itemLayoutView
                        .findViewById(R.id.OverViewTitle);
                data = (TextView) itemLayoutView
                        .findViewById(R.id.overviewText);

            }
        }




}
