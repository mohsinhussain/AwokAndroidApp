package com.awok.moshin.awok.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.awok.moshin.awok.Adapters.CountryListAdapter;
import com.awok.moshin.awok.Models.Country;
import com.awok.moshin.awok.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class CountryPicker extends DialogFragment implements
		Comparator<Country> {
	SharedPreferences mSharedPrefs;
	String imageIco;
	private EditText searchEditText;
	private ListView countryListView;

	private CountryListAdapter adapter;

	private List<Country> allCountriesList;

	private List<Country> selectedCountriesList;

	private CountryPickerListener listener;

	public void setListener(CountryPickerListener listener) {
		this.listener = listener;
	}

	public EditText getSearchEditText() {
		return searchEditText;
	}

	public ListView getCountryListView() {
		return countryListView;
	}

	public static Currency getCurrencyCode(String countryCode) {
		try {
			return Currency.getInstance(new Locale("en", countryCode));
		} catch (Exception e) {

		}
		return null;
	}

	private List<Country> getAllCountries() {
		if (allCountriesList == null) {
			try {
				allCountriesList = new ArrayList<Country>();
				
				String allCountriesCode = readEncodedJsonString(getActivity());
				
				JSONArray countrArray = new JSONArray(allCountriesCode);

				System.out.println("countryArray = "+allCountriesCode);
				
				for (int i = 0; i < countrArray.length(); i++) {
					JSONObject jsonObject = countrArray.getJSONObject(i);
					String countryName = jsonObject.getString("name");
					String countryDialCode = jsonObject.getString("dial_code");
					String countryCode = jsonObject.getString("code");
					
					Country country = new Country();
					country.setCode(countryCode);
					country.setName(countryName);
					country.setDialCode(countryDialCode);
					allCountriesList.add(country);
				}

				Collections.sort(allCountriesList, this);

				selectedCountriesList = new ArrayList<Country>();
				selectedCountriesList.addAll(allCountriesList);

				// Return
				return allCountriesList;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static String readEncodedJsonString(Context context)
			throws java.io.IOException {
		String base64 = context.getResources().getString(R.string.countries_code);
		byte[] data = Base64.decode(base64, Base64.DEFAULT);
		return new String(data, "UTF-8");
	}

	/**
	 * To support show as dialog
	 * 
	 * @param dialogTitle
	 * @return
	 */
	public static CountryPicker newInstance(String dialogTitle) {
		CountryPicker picker = new CountryPicker();
		Bundle bundle = new Bundle();
		bundle.putString("dialogTitle", dialogTitle);
		picker.setArguments(bundle);
		return picker;
	}
 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.country_picker, null);
 
		getAllCountries();
 
		Bundle args = getArguments();
		if (args != null) {
			String dialogTitle = args.getString("dialogTitle");
			getDialog().setTitle(dialogTitle);

			int width = getResources().getDimensionPixelSize(
					R.dimen.cp_dialog_width);
			int height = getResources().getDimensionPixelSize(
					R.dimen.cp_dialog_height);
			getDialog().getWindow().setLayout(width, height);
		}
		mSharedPrefs = getActivity().getSharedPreferences(Constants.PREFS_NAME, 0);
		if(mSharedPrefs.contains(Constants.USER_SETTING_COUNTRY)){
			System.out.println("YES");

			imageIco=mSharedPrefs.getString(Constants.USER_SETTING_COUNTRY, null);



		}
		else
		{
			System.out.println("No");
		}

		countryListView = (ListView) view
				.findViewById(R.id.country_code_picker_listview);
 
		adapter = new CountryListAdapter(getActivity(), selectedCountriesList,imageIco);
		countryListView.setAdapter(adapter);
 
		countryListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				if (listener != null) {
					Country country = selectedCountriesList.get(position);
					listener.onSelectCountry(country.getName(),
							country.getCode(), country.getDialCode());
				}
			}
		});
 


		return view;
	} 
	

 
	@Override
	public int compare(Country lhs, Country rhs) {
		return lhs.getName().compareTo(rhs.getName());
	}

}
