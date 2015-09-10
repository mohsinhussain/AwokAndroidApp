package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.awok.moshin.awok.Models.ProductSpecification;
import com.awok.moshin.awok.R;

import java.util.List;

/**
 * Created by shon on 9/10/2015.
 */
public class ProductSpecificationAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<ProductSpecification> prodSpec;













    public ProductSpecificationAdapter(Activity activity,List<ProductSpecification> prodSpec)
    {
        this.activity=activity;
        this.prodSpec=prodSpec;
    }


    @Override
    public int getCount() {
        return prodSpec.size();
    }

    @Override
    public Object getItem(int position) {
        return prodSpec.get(position);
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
            convertView = inflater.inflate(R.layout.product_spec_list, null);

        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.WHITE);
        } else {
            convertView.setBackgroundColor(Color.parseColor("#E0E0E0"));
        }
        TextView prodSpecTitle=(TextView)convertView.findViewById(R.id.prodSpecTitle);
        TextView prodSpecValue=(TextView)convertView.findViewById(R.id.prodSpecValue);



        Typeface title = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface value = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Light.ttf");

prodSpecTitle.setTypeface(title);
        prodSpecValue.setTypeface(value);


        ProductSpecification prodSpecData=prodSpec.get(position);
        prodSpecTitle.setText(prodSpecData.getSpecTitle());
        prodSpecValue.setText(prodSpecData.getSpecValue());


        return convertView;
    }
}
