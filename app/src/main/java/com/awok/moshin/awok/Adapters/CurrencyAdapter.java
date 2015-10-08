package com.awok.moshin.awok.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.awok.moshin.awok.Models.Country;
import com.awok.moshin.awok.Models.CurrencyModel;
import com.awok.moshin.awok.R;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

/**
 * Created by shon on 10/7/2015.
 */
public class CurrencyAdapter extends BaseAdapter{
    private Context context;
    String countrySelection;
    List<CurrencyModel> currency;
    LayoutInflater inflater;



    public CurrencyAdapter(Context context, List<CurrencyModel> currency,String  country) {
        super();
        this.context = context;

        this.currency = currency;
        inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return currency.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View cellView = convertView;
        Cell cell;
        CurrencyModel currencyData = currency.get(position);

        if (convertView == null) {
            cell = new Cell();
            cellView = inflater.inflate(R.layout.currency_row, null);
            cell.textView = (TextView) cellView.findViewById(R.id.row_title);
            cell.imageView = (ImageView) cellView.findViewById(R.id.row_icon);
            cell.mainView=(LinearLayout)cellView.findViewById(R.id.main);
            cellView.setTag(cell);
        } else {
            cell = (Cell) cellView.getTag();
        }

        if(currency.get(position).getCurrencyName().equals(countrySelection))
        {
            cell.mainView.setBackgroundColor(Color.parseColor("#de352d"));
            cell.textView.setText(currency.get(position).getCurrencyName());

            /*String drawableName = "flag_"
                    + country.getCode().toLowerCase(Locale.ENGLISH);
            cell.imageView.setImageResource(getResId(drawableName));*/
        }
        else
        {
            cell.mainView.setBackgroundColor(Color.parseColor("#ffffff"));
            cell.textView.setText(currency.get(position).getCurrencyName());

           /* String drawableName = "flag_"
                    + country.getCode().toLowerCase(Locale.ENGLISH);
            cell.imageView.setImageResource(getResId(drawableName));*/
        }

        return cellView;
    }

    static class Cell {
        public TextView textView;
        public ImageView imageView;
        private LinearLayout mainView;
        private RadioButton radioButton;
    }
}
