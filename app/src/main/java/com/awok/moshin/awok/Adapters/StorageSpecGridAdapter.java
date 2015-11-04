package com.awok.moshin.awok.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.ColorSpec;
import com.awok.moshin.awok.Models.StorageSpec;
import com.awok.moshin.awok.R;

import java.util.ArrayList;

/**
 * Created by mohsin on 11/1/2015.
 */
public class StorageSpecGridAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<StorageSpec> mobileValues;
    private LinearLayout parentPanel;

    public StorageSpecGridAdapter(Context context, ArrayList<StorageSpec> mobileValues) {
        this.context = context;
        this.mobileValues = mobileValues;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.storage_spec_item, null);

            parentPanel = (LinearLayout) gridView.findViewById(R.id.parentPanel);

            parentPanel.setBackground(mobileValues.get(position).isSelected() ? context.getResources().getDrawable(R.drawable.spec_cell_bg_selected) : context.getResources().getDrawable(R.drawable.spec_cell_bg_unselected));


            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.colorNameTextView);
            textView.setText(mobileValues.get(position).getColor());
            textView.setTextColor(mobileValues.get(position).isSelected() ? context.getResources().getColor(R.color.red_base) : context.getResources().getColor(R.color.normal_text));


//            String mobile = mobileValues[position];
//
//            if (mobile.equals("Windows")) {
//                imageView.setImageResource(R.drawable.windows_logo);
//            } else if (mobile.equals("iOS")) {
//                imageView.setImageResource(R.drawable.ios_logo);
//            } else if (mobile.equals("Blackberry")) {
//                imageView.setImageResource(R.drawable.blackberry_logo);
//            } else {
//                imageView.setImageResource(R.drawable.android_logo);
//            }

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return mobileValues.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
