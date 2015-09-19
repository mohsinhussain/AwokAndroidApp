package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.CheckoutDataObjects;
import com.awok.moshin.awok.Models.ProductOverview;
import com.awok.moshin.awok.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shon on 9/14/2015.
 */
//
public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.ViewHolder> {
    private List<Checkout> OverViewList = new ArrayList<Checkout>();
    private Activity activity;

    private ArrayAdapter<CharSequence> adapter;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CheckOutAdapter(Activity activity,List<Checkout> overViewList) {
        OverViewList = overViewList;
        activity=activity;
        adapter = ArrayAdapter.createFromResource(activity, R.array.object_array, android.R.layout.simple_spinner_item);

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.spinner_drop_down);



    }

    // Create new views (invoked by the layout manager)
    @Override
    public CheckOutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.checkoutlayout, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        final Checkout item = OverViewList.get(position);
        viewHolder.prodOverViewText.setText(OverViewList.get(position).getOverViewText());
        viewHolder.prodOverviewTitle.setText(OverViewList.get(position).getOverViewTitle());
      //  viewHolder.data = new CheckoutDataObjects(activity);
        viewHolder.countOfProducts.setAdapter(adapter);



viewHolder.prodOverviewTitle.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        remove(item);
    }
});


        /*viewHolder.countOfProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Snackbar.make(view.findViewById(android.R.id.content), "Clicked", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .show();

            }

        });*/

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return OverViewList.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        public TextView prodOverviewTitle,prodOverViewText;
        public Spinner countOfProducts;

        //public CheckoutDataObjects data;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            prodOverviewTitle = (TextView) itemLayoutView
                    .findViewById(R.id.OverViewTitle);
            prodOverViewText = (TextView) itemLayoutView
                    .findViewById(R.id.overviewText);
            countOfProducts=(Spinner)itemLayoutView
                    .findViewById(R.id.spinner);

        }
        @Override
        public void onClick(View view) {
            System.out.println("onClick " + getPosition());
        }
    }
    public void add(Checkout item, int position) {
        OverViewList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Checkout item) {
        int position = OverViewList.indexOf(item);
        OverViewList.remove(position);
        notifyItemRemoved(position);
    }

}
