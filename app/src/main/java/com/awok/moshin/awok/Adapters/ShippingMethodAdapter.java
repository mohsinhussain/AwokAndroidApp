package com.awok.moshin.awok.Adapters;

import android.content.Context;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.awok.moshin.awok.Activities.ShippingAddressActivity;
import com.awok.moshin.awok.Activities.ShippingMethodActivity;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.ShippingAddressModel;
import com.awok.moshin.awok.Models.ShippingMethod;
import com.awok.moshin.awok.R;
import com.larvalabs.svgandroid.SVG;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shon on 10/11/2015.
 */
public class ShippingMethodAdapter extends RecyclerView.Adapter<ShippingMethodAdapter.ViewHolder> {
    private List<ShippingMethod> OverViewList = new ArrayList<ShippingMethod>();
    private ShippingMethodActivity activity_main;
    View customView;
    Context context;
    Context mContext;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ShippingMethodAdapter(ShippingMethodActivity activity, List<ShippingMethod> overViewList) {
        OverViewList = overViewList;
        activity_main = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ShippingMethodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.shipping_method_item, null);

        // create ViewHolder
        //sellerCheck="";
        customView=itemLayoutView;

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {



        final int pos = position;
        viewHolder.rb.setChecked(OverViewList.get(position).isSelected());
        viewHolder.nameTextView.setText(OverViewList.get(position).getName());
        viewHolder.costTextView.setText("AED " + Double.toString(OverViewList.get(position).getShippingCost()));
        viewHolder.deliveryTimeTextView.setText(OverViewList.get(position).getDeliveryTime());


        viewHolder.rb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RadioButton rb = (RadioButton) v;
                //ShippingAddressModel address = (ShippingAddressModel) rb.getTag();
                activity_main.setPrimaryShippingMethod(OverViewList.get(pos).getName(), OverViewList.get(pos).getShippingCost());
                //address.setIsSelected(rb.isChecked());
                for(int i=0;i<OverViewList.size();i++) {
//                    ShippingMethod method = OverViewList.get(i);
                   /* if (country.getIsSelected()) {
                        responseText.append("\n" + country.getName());
                    }*/
                    OverViewList.get(i).setIsSelected(false);
                }
                OverViewList.get(pos).setIsSelected(true);

                notifyDataSetChanged();

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
        return OverViewList.size();
    }




    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        public TextView nameTextView,costTextView,deliveryTimeTextView;
        private RadioButton rb;
        Button editButton, removeButton;


        //public CheckoutDataObjects data;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            nameTextView = (TextView) itemLayoutView
                    .findViewById(R.id.nameTextView);
            costTextView=(TextView)itemLayoutView
                    .findViewById(R.id.costTextView);
            deliveryTimeTextView = (TextView) itemLayoutView
                    .findViewById(R.id.deliveryTimeEditText);
            rb=(RadioButton)itemLayoutView
                    .findViewById(R.id.radioAddress);

        }
        @Override
        public void onClick(View view) {
            System.out.println("onClick " + getPosition());
        }
    }
//    public void add(ShippingMethod item, int position) {
//        OverViewList.add(position, item);
//        notifyItemInserted(position);
//    }
//
//    public void remove(Checkout item) {
//        int position = OverViewList.indexOf(item);
//        OverViewList.remove(position);
//        notifyItemRemoved(position);
//    }









}
