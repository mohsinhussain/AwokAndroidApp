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
import android.widget.TextView;

import com.awok.moshin.awok.Models.Country;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.R.drawable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;


public class CountryListAdapter extends BaseAdapter {

	private Context context;
	String countrySelection;
	List<Country> countries;
	LayoutInflater inflater;

	private int getResId(String drawableName) {

		try {
			Class<drawable> res = R.drawable.class;
			Field field = res.getField(drawableName);
			int drawableId = field.getInt(null);
			return drawableId;
		} catch (Exception e) {
			Log.e("CountryCodePicker", "Failure to get drawable id.", e);
		}
		return -1;
	} 
	
	public CountryListAdapter(Context context, List<Country> countries,String  country) {
		super();
		this.context = context;
		this.countrySelection=country;
		this.countries = countries;
		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return countries.size();
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
		Country country = countries.get(position);

		if (convertView == null) {
			cell = new Cell();
			cellView = inflater.inflate(R.layout.country_row, null);
			cell.textView = (TextView) cellView.findViewById(R.id.row_title);
			cell.imageView = (ImageView) cellView.findViewById(R.id.row_icon);
			cell.mainView=(LinearLayout)cellView.findViewById(R.id.main);
			cellView.setTag(cell);
		} else {
			cell = (Cell) cellView.getTag();
		}

		if(country.getName().equals(countrySelection))
		{
			cell.mainView.setBackgroundColor(Color.parseColor("#de352d"));
			cell.textView.setText(country.getName());

			String drawableName = "flag_"
					+ country.getCode().toLowerCase(Locale.ENGLISH);
			cell.imageView.setImageResource(getResId(drawableName));
		}
		else
		{
			cell.mainView.setBackgroundColor(Color.parseColor("#ffffff"));
			cell.textView.setText(country.getName());

			String drawableName = "flag_"
					+ country.getCode().toLowerCase(Locale.ENGLISH);
			cell.imageView.setImageResource(getResId(drawableName));
		}

		return cellView;
	}
 
	static class Cell {
		public TextView textView;
		public ImageView imageView;
		private LinearLayout mainView;
	}

}