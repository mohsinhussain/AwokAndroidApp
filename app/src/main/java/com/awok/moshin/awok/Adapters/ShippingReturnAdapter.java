package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awok.moshin.awok.Models.ProductSpecification;
import com.awok.moshin.awok.Models.ShippingReturnModel;
import com.awok.moshin.awok.R;

import java.util.List;

/**
 * Created by shon on 2/3/2016.
 */
public class ShippingReturnAdapter  extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ShippingReturnModel> shipModel;













    public ShippingReturnAdapter(Activity activity,List<ShippingReturnModel> shipModel)
    {
        this.activity=activity;
        this.shipModel=shipModel;
    }


    @Override
    public int getCount() {
        return shipModel.size();
    }

    @Override
    public Object getItem(int position) {
        return shipModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.shipping_return_row, null);


        TextView prodSpecTitle=(TextView)convertView.findViewById(R.id.prodSpecTitle);

        TextView prodSubTitle=(TextView)convertView.findViewById(R.id.prodSubTitle);









        Typeface title = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface value = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Light.ttf");

        prodSpecTitle.setTypeface(title);



        ShippingReturnModel prodSpecData=shipModel.get(position);
        prodSpecTitle.setText(prodSpecData.getSpecTitle());

        prodSubTitle.setText(prodSpecData.getSpecValue());



        return convertView;
    }
}
