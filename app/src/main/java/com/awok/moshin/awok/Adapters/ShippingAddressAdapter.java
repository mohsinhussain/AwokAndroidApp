package com.awok.moshin.awok.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.Activities.CheckOutActivity;
import com.awok.moshin.awok.Activities.ShippingAddressActivity;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.ShippingAddressModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.larvalabs.svgandroid.SVG;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shon on 10/11/2015.
 */
public class ShippingAddressAdapter extends RecyclerView.Adapter<ShippingAddressAdapter.ViewHolder> {
    private List<ShippingAddressModel> OverViewList = new ArrayList<ShippingAddressModel>();
    private ShippingAddressActivity activity_main;
    View customView;

    private ArrayAdapter<CharSequence> adapter;
    NetworkInfo networkInfo;
    Context context;
    Context mContext;
    SVG svg;
    String sellerCheck="";
    LinearLayout.LayoutParams lp;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ShippingAddressAdapter(ShippingAddressActivity activity,List<ShippingAddressModel> overViewList) {
        OverViewList = overViewList;
        activity_main = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ShippingAddressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.shipping_custom_layout, null);

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
        viewHolder.rb.setChecked(true);
        viewHolder.name.setText(OverViewList.get(position).getName());
        viewHolder.address.setText(OverViewList.get(position).getAddress1());
        if(OverViewList.get(position).getState().equalsIgnoreCase("")){
            viewHolder.state.setText(OverViewList.get(position).getCity());
        }
        else{
            viewHolder.state.setText(OverViewList.get(position).getCity()+", "+OverViewList.get(position).getState());
        }

        viewHolder.country.setText(OverViewList.get(position).getCountry());
        viewHolder.pin.setText(OverViewList.get(position).getPin());
        viewHolder.phone.setTag(OverViewList.get(position).getPhone1());
        viewHolder.rb.setChecked(OverViewList.get(position).isSelected());

        viewHolder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_main.removeAddress(OverViewList.get(pos).getId());
                OverViewList.remove(pos);
                notifyDataSetChanged();
            }
        });

        viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_main.editAddress(pos);
            }
        });

        viewHolder.rb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RadioButton rb = (RadioButton) v;
                //ShippingAddressModel address = (ShippingAddressModel) rb.getTag();
                activity_main.setPrimaryAddress(OverViewList.get(pos).getId());
                //address.setIsSelected(rb.isChecked());
                for(int i=0;i<OverViewList.size();i++) {
                    ShippingAddressModel country = OverViewList.get(i);
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

        public TextView name,address,state,country,pin,phone;
        private RadioButton rb;
        Button editButton, removeButton;


        //public CheckoutDataObjects data;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            name = (TextView) itemLayoutView
                    .findViewById(R.id.name);
            address=(TextView)itemLayoutView
                    .findViewById(R.id.address);
            state = (TextView) itemLayoutView
                    .findViewById(R.id.state);
            rb=(RadioButton)itemLayoutView
                    .findViewById(R.id.radioAddress);
            editButton = (Button)itemLayoutView.findViewById(R.id.editButton);
            removeButton = (Button)itemLayoutView.findViewById(R.id.removeAddressButton);

            country = (TextView) itemLayoutView
                    .findViewById(R.id.country);
            pin = (TextView) itemLayoutView
                    .findViewById(R.id.pin);




            phone = (TextView) itemLayoutView
                    .findViewById(R.id.phone);

        }
        @Override
        public void onClick(View view) {
            System.out.println("onClick " + getPosition());
        }
    }
    public void add(ShippingAddressModel item, int position) {
        OverViewList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Checkout item) {
        int position = OverViewList.indexOf(item);
        OverViewList.remove(position);
        notifyItemRemoved(position);
    }









}
