package com.awok.moshin.awok.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.awok.moshin.awok.Activities.MainActivity;
import com.awok.moshin.awok.Models.Filter;
import com.awok.moshin.awok.Models.FilterValue;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.RangeSeekBar;
import com.liangfeizc.flowlayout.FlowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class FilterFragment extends Fragment {

    View mView, filterView;
    private String TAG = "Filter Fragment";
//    FlowLayout colorFlowLayout, tagsFlowLayout, priceFlowLayout, sizeFlowLayout, brandFlowLayout, ratingFlowLayout;
    public FilterFragment(){}
    String catId = null;
    ProgressBar progressBar;
    LinearLayout progressLayout, containerLayout, seekBarContainer;
    TextView minTextView, maxTextView;
//    Button colorClearAllButton, tagsClearAllButton, ratingClearAllButton, priceClearAllButton, sizeClearAllButton, brandClearAllButton;
    LayoutInflater inflater, filterInflator;
    ViewGroup container;
    MainActivity activity;
//    RelativeLayout colorLayout, tagsLayout, priceLayout,sizeLayout,brandLayout, ratingLayout;
    StartCommunication mStartCommunicationListner;
    ArrayList<Filter> filtersArray = new ArrayList<Filter>();
    ArrayList<String>  filterStringArray = new ArrayList<String>();
    ArrayList<String>  filterValuesArray = new ArrayList<String>();
    String minValue = "";
    String maxValue = "";
    Spinner spinner;
    boolean isTwise = false ;
    boolean isEdit = true ;
    EditText edtTag ;
    ArrayList<String> categoryStringArray = new ArrayList<String>();
    ArrayList<String> categoryKeyStringArray = new ArrayList<String>();
    String subCatId="";
    ArrayAdapter<String> adapter;
    String keywordString = "";

    public FilterFragment(MainActivity mainActivity){
        this.activity = mainActivity;
    }

    public FilterFragment(String catId, MainActivity mainActivity){
        this.activity = mainActivity;
        this.catId = catId;
        subCatId = catId;
    }

    public interface StartCommunication
    {
        public void updateFilters(String keywords, String catString, ArrayList<String> filterStringArray, ArrayList<String> filterValueArray, String minPrice, String maxPrice);
//        public void addColor(String color);
//        public void removeColor(String color);
//        public void addTag(String tag);
//        public void removeTag(String tag);
//        public void addPrice(String price);
//        public void removePrice(String price);
//        public void addSize(String size);
//        public void removeSize(String size);
//        public void addBrand(String brand);
//        public void removeBrand(String brand);
//        public void addRating(String ratings);
//        public void removeRating(String ratings);
        public void resetAll();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof StartCommunication){
            mStartCommunicationListner = (StartCommunication) activity;
        }
        else{
            throw new ClassCastException();
        }
    }



    /**
     *
     * Filter keys:
     *Tags
     *colors
     *size
     *brand
     *price
     *ratings
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;
        this.container = container;
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mView = inflater.inflate(R.layout.fragment_filter, container, false);
//        colorClearAllButton = (Button) mView.findViewById(R.id.colorclearButton);
//        tagsClearAllButton = (Button) mView.findViewById(R.id.tagsClearButton);
//        ratingClearAllButton = (Button) mView.findViewById(R.id.ratingClearButton);
//        priceClearAllButton = (Button) mView.findViewById(R.id.priceClearButton);
//        sizeClearAllButton = (Button) mView.findViewById(R.id.sizeClearButton);
//        brandClearAllButton = (Button) mView.findViewById(R.id.brandClearButton);
        progressLayout = (LinearLayout) mView.findViewById(R.id.progressLayout);
        containerLayout = (LinearLayout) mView.findViewById(R.id.containerLayout);
        seekBarContainer = (LinearLayout) mView.findViewById(R.id.seekbarLayout);
        minTextView =(TextView) mView.findViewById(R.id.minPriceTextView);
        maxTextView = (TextView) mView.findViewById(R.id.maxPriceTextView);
        spinner = (Spinner) mView.findViewById(R.id.spinner);
//        colorFlowLayout = (FlowLayout) mView.findViewById(R.id.colorFlowlayout);
//        tagsFlowLayout = (FlowLayout) mView.findViewById(R.id.tagsFlowlayout);
//        priceFlowLayout = (FlowLayout) mView.findViewById(R.id.priceFlowLayout);
//        colorLayout = (RelativeLayout) mView.findViewById(R.id.colorLayout);
//        tagsLayout = (RelativeLayout) mView.findViewById(R.id.tagsLayout);
//        priceLayout = (RelativeLayout) mView.findViewById(R.id.priceLayout);
//        sizeFlowLayout = (FlowLayout) mView.findViewById(R.id.sizeFlowLayout);
//        brandFlowLayout = (FlowLayout) mView.findViewById(R.id.brandFlowLayout);
//        ratingFlowLayout = (FlowLayout) mView.findViewById(R.id.ratingFlowLayout);
//        sizeLayout = (RelativeLayout) mView.findViewById(R.id.sizeLayout);
//        brandLayout = (RelativeLayout) mView.findViewById(R.id.brandLayout);
//        ratingLayout = (RelativeLayout) mView.findViewById(R.id.ratingLayout);
//        colorLayout.setVisibility(View.GONE);
//        tagsLayout.setVisibility(View.GONE);
//        priceLayout.setVisibility(View.GONE);
//        sizeLayout.setVisibility(View.GONE);
//        brandLayout.setVisibility(View.GONE);
//        ratingLayout.setVisibility(View.GONE);
        progressBar = (ProgressBar) mView.findViewById(R.id.marker_progress);
        progressLayout.setVisibility(View.GONE);

        filtersArray = new ArrayList<Filter>();


        adapter =new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_custom, categoryStringArray);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (categoryKeyStringArray.get(position).equalsIgnoreCase("ALL")) {
                    subCatId = catId;
                    mStartCommunicationListner.updateFilters(keywordString, subCatId, filterStringArray, filterValuesArray, minValue, maxValue);
                    ConnectivityManager connMgr = (ConnectivityManager)
                            getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        new APIClient(getActivity(), getActivity(), new GetProductDetailsCallback()).dynamicFiltersAPICall(catId);
                    } else {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                .setActionTextColor(Color.RED)
                                .show();
                    }
                } else {
                    subCatId = categoryKeyStringArray.get(position);
                    mStartCommunicationListner.updateFilters(keywordString, subCatId, filterStringArray, filterValuesArray, minValue, maxValue);
                    ConnectivityManager connMgr = (ConnectivityManager)
                            getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        new APIClient(getActivity(), getActivity(), new GetProductDetailsCallback()).dynamicFiltersAPICall(categoryKeyStringArray.get(position));
                    } else {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                                .setActionTextColor(Color.RED)
                                .show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new APIClient(getActivity(), getActivity(),  new GetProductDetailsCallback()).dynamicFiltersAPICall(catId);
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_SHORT)
                    .setActionTextColor(Color.RED)
                    .show();
        }


        edtTag = (EditText) mView.findViewById(R.id.editText);
        double scaletype =getResources().getDisplayMetrics().density;
        if(scaletype >=3.0){
            isTwise = true ;
        }
        edtTag.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                keywordString = s.toString();
                mStartCommunicationListner.updateFilters(keywordString, subCatId, filterStringArray, filterValuesArray, minValue, maxValue);
                System.out.println("Vale: CharSequence: " + s);
                System.out.println("Vale: start: " + start);
                System.out.println("Vale: before: " + before);
                System.out.println("Vale: count: " + count);
                if (count >= 1 && !isEdit) {
                    if (!Character.isSpaceChar(s.charAt(0))) {
                        if (s.charAt(start) == ' ')
                            setTag(); // generate chips
                    } else {
                        edtTag.getText().clear();
                        edtTag.setSelection(0);
                    }

                } else {
                    System.out.println("Object removed : ON TEXTCHANGED");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isEdit) {
                    setTag();
                } else {
                    System.out.println("Object removed : AFTER TEXTCHANGED");
                }
            }
        });





        return mView;
    }


    public void setTag() {
        if (edtTag.getText().toString().contains(" ")) // check comman in string
        {
            SpannableStringBuilder ssb = new SpannableStringBuilder(edtTag.getText());
            // split string wich comma
            String chips[] =edtTag.getText().toString().trim().split(" ");
            int x = 0;
            for (String c : chips) {
                LayoutInflater lf = (LayoutInflater)getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                TextView textView = (TextView) lf.inflate(
                        R.layout.tag_edittext, null);
                textView.setText(c); // set text
                int spec = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                textView.measure(spec, spec);
                textView.layout(0, 0, textView.getMeasuredWidth(),
                        textView.getMeasuredHeight());
                Bitmap b = Bitmap.createBitmap(textView.getWidth(),
                        textView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(b);
                canvas.translate(-textView.getScrollX(), -textView.getScrollY());
                textView.draw(canvas);
                textView.setDrawingCacheEnabled(true);
                Bitmap cacheBmp = textView.getDrawingCache();
                Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
                textView.destroyDrawingCache(); // destory drawable
                BitmapDrawable bmpDrawable = new BitmapDrawable(viewBmp);
                int width = bmpDrawable.getIntrinsicWidth() ;
                int height = bmpDrawable.getIntrinsicHeight() ;
                if(isTwise){
                    width = width *2 ;
                    height = height *2;
                }
                bmpDrawable.setBounds(0, 0,width ,height);
                ssb.setSpan(new ImageSpan(bmpDrawable), x, x + c.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                x = x + c.length() + 1;
            }
            // set chips span
            isEdit = false ;
            edtTag.setText(ssb);
            // move cursor to last
            edtTag.setSelection(edtTag.getText().length());
        }

    }
    public  int convertDpToPixel(float dp){
        Resources resources = getActivity().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int)px;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
//            if(brandClearAllButton!=null){
//                brandClearAllButton.performClick();
//            }
//            if(colorClearAllButton!=null){
//                colorClearAllButton.performClick();
//            }
//            if(priceClearAllButton!=null){
//                priceClearAllButton.performClick();
//            }
//            if(tagsClearAllButton!=null){
//                tagsClearAllButton.performClick();
//            }
//            if(sizeClearAllButton!=null){
//                sizeClearAllButton.performClick();
//            }
//            if(ratingClearAllButton!=null){
//                ratingClearAllButton.performClick();
//            }
            if(mStartCommunicationListner!=null) {
                mStartCommunicationListner.resetAll();
            }

        }
        else{
            System.out.println("isHidden Called" + catId);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("OnResume Called" + catId);
    }


    public void populateFilter(){
        containerLayout.removeAllViews();

        filterInflator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        filterStringArray.clear();
        filterValuesArray.clear();
        for (int i = 0; i < filtersArray.size(); i++) {
            filterStringArray.add("");
            filterView = filterInflator.inflate(R.layout.dynamic_filter_layout, null);
            RelativeLayout mainLayout = (RelativeLayout) filterView.findViewById(R.id.tagsLayout);
//            ImageView tagsImageView = (ImageView) filterView.findViewById(R.id.tagsImageView);
            TextView tagsTextView = (TextView) filterView.findViewById(R.id.tagsTextView);
            Button tagsClearButton = (Button) filterView.findViewById(R.id.tagsClearButton);
            final FlowLayout tagsFlowlayout = (FlowLayout) filterView.findViewById(R.id.tagsFlowlayout);

            mainLayout.setVisibility(View.VISIBLE);
            tagsFlowlayout.removeAllViews();


            tagsTextView.setText(filtersArray.get(i).getTitle());
//            tagsImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.tag_icon));

            final int finalI1 = i;
            tagsClearButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int x = 0; x < tagsFlowlayout.getChildCount(); x++) {
                        if (((Button) tagsFlowlayout.getChildAt(x)).isSelected()) {
                            ((Button) tagsFlowlayout.getChildAt(x)).setSelected(false);
                            ((Button) tagsFlowlayout.getChildAt(x)).setTextColor(getContext().getResources().getColor(R.color.dialog_button_cancel));
                            for(int m=0;m<filterValuesArray.size();m++){
                                if(filterStringArray.get(finalI1).contains(filterValuesArray.get(m))){
                                    filterValuesArray.remove(m);
                                }
                            }
                            filterStringArray.set(finalI1, "");
                            mStartCommunicationListner.updateFilters(keywordString, subCatId, filterStringArray, filterValuesArray, minValue, maxValue);
//                            mStartCommunicationListner.removeTag(((Button) tagsFlowlayout.getChildAt(i)).getText().toString());
                        }
                    }
                }
            });

            for(int x=0;x<filtersArray.get(i).getValuesArray().size();x++) {

                if (filtersArray.get(i).getType().equalsIgnoreCase("N")) {

                    tagsClearButton.setVisibility(View.GONE);
                    final FilterValue filterValue = filtersArray.get(i).getValuesArray().get(x);
                    if (filterValue.getKey().equalsIgnoreCase("max-price")) {
                        maxValue = filterValue.getValue();
                    } else {
                        minValue = filterValue.getValue();
                    }


                } else {
                    final FilterValue filterValue = filtersArray.get(i).getValuesArray().get(x);
                    Button t = (Button) inflater.inflate(R.layout.filter_button_tag, container, false);
                    t.setText(filterValue.getTitle());
                    t.setSingleLine(true);
                    t.setBackground(getResources().getDrawable(R.drawable.filter_button));
//                final int finalX = x;
                    final int finalI = i;
                    t.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (v.isSelected()) {
                                ((Button) v).setSelected(false);
                                ((Button) v).setTextColor(getContext().getResources().getColor(R.color.dialog_button_cancel));
                                try {

                                    String value = URLEncoder.encode(filterValue.getValue(), "utf-8");
                                    for (int m = 0; m > filterValuesArray.size(); m++) {
                                        if (filterValuesArray.get(m).equalsIgnoreCase(value)) {
                                            filterValuesArray.remove(m);
                                        }
                                    }
                                    if (filterStringArray.get(finalI).contains(value)) {
                                        String temp = filterStringArray.get(finalI);
                                        String str = temp.replaceAll(value + ",", "");
                                        filterStringArray.set(finalI, str);
                                    }
                                    mStartCommunicationListner.updateFilters(keywordString, subCatId, filterStringArray, filterValuesArray, minValue, maxValue);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

//                            mStartCommunicationListner.removeTag(((Button) v).getText().toString());
                            } else {
                                ((Button) v).setSelected(true);
                                ((Button) v).setTextColor(getContext().getResources().getColor(R.color.button_text));
                                if (filterStringArray.get(finalI).equalsIgnoreCase("")) {
                                    try {
                                        String value = URLEncoder.encode(filterValue.getValue(), "utf-8");
                                        filterStringArray.set(finalI, "&" + filterValue.getKey() + "=" + value + ",");
                                        filterValuesArray.add(value);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    String temp = filterStringArray.get(finalI);
                                    try {
                                        String value = URLEncoder.encode(filterValue.getValue(), "utf-8");
                                        filterStringArray.set(finalI, temp + value + ",");
                                        filterValuesArray.add(value);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }

                                }
                                mStartCommunicationListner.updateFilters(keywordString, subCatId, filterStringArray, filterValuesArray, minValue, maxValue);
//                            mStartCommunicationListner.addTag(((Button) v).getText().toString());
                            }

                        }
                    });
                    tagsFlowlayout.
                            addView(t);
                }
            }
            containerLayout.addView(filterView);
        }

        int min = 0;
        int max = 0;
        if(minValue.equalsIgnoreCase("")){
            min = 0;
        }
        else if(minValue.contains(".")){
            Double minDouble = Double.parseDouble(minValue);
           min = (int) Math.round(minDouble);
        }
        else{
            min = (int)Integer.parseInt(minValue);
        }
        if(maxValue.equalsIgnoreCase("")){
            max = 0;
        }
        else if(maxValue.contains(".")){
            Double maxDouble = Double.parseDouble(maxValue);
            max = (int) Math.round(maxDouble);
        }
        else{
            max = (int)Integer.parseInt(maxValue);
        }

        minTextView.setText(min+" AED");
        maxTextView.setText(max+" AED");
        // create RangeSeekBar as Integer range between 20 and 75
        final RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(min, max, getActivity());
        seekBar.SINGLE_COLOR = getActivity().getResources().getColor(R.color.red_base);
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                // handle changed range values
                Log.i(TAG, "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);

                minTextView.setText(minValue.toString()+" AED");
                maxTextView.setText(maxValue.toString()+" AED");
                mStartCommunicationListner.updateFilters(keywordString, subCatId, filterStringArray, filterValuesArray, minValue.toString(), maxValue.toString());
            }
        });

        seekBarContainer.addView(seekBar);


//        ArrayList<String> attStringArray = new ArrayList<String>();
//        for(int i=0;i<attTypes.size();i++){
//            attStringArray.add(attTypes.get(i).getType());
//        }

       adapter.notifyDataSetChanged();


    }



//    public void populateColors(String [] tagsArray){
//
//        colorLayout.setVisibility(View.VISIBLE);
//        colorFlowLayout.removeAllViews();
//        for (int i = 0; i < tagsArray.length; i++) {
//            Button t = (Button) inflater.inflate(R.layout.filter_button_tag, container, false);
//            t.setText(tagsArray[i]);
//            t.setSingleLine(true);
//            t.setBackground(getResources().getDrawable(R.drawable.filter_button));
//            t.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(v.isSelected()){
//                        ((Button)v).setSelected(false);
//                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.dialog_button_cancel));
//                        mStartCommunicationListner.removeColor(((Button) v).getText().toString());
//                    }else{
//                        ((Button)v).setSelected(true);
//                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.button_text));
//                        mStartCommunicationListner.addColor(((Button) v).getText().toString());
//                    }
//
//
//                }
//            });
//            colorFlowLayout.
//                    addView(t);
//        }
//    }
//
//    public void populateTags(String [] tagsArray){
//        tagsLayout.setVisibility(View.VISIBLE);
//        tagsFlowLayout.removeAllViews();
//        for (int i = 0; i < tagsArray.length; i++) {
//            Button t = (Button) inflater.inflate(R.layout.filter_button_tag, container, false);
//            t.setText(tagsArray[i]);
//            t.setSingleLine(true);
//            t.setBackground(getResources().getDrawable(R.drawable.filter_button));
//            t.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(v.isSelected()){
//                        ((Button)v).setSelected(false);
//                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.dialog_button_cancel));
//                        mStartCommunicationListner.removeTag(((Button) v).getText().toString());
//                    }else{
//                        ((Button)v).setSelected(true);
//                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.button_text));
//                        mStartCommunicationListner.addTag(((Button) v).getText().toString());
//                    }
//
//                }
//            });
//            tagsFlowLayout.
//                    addView(t);
//        }
//    }
//
//
//    public void populateRatings(String [] tagsArray){
//        ratingLayout.setVisibility(View.VISIBLE);
//        ratingFlowLayout.removeAllViews();
//        for (int i = 0; i < tagsArray.length; i++) {
//            Button t = (Button) inflater.inflate(R.layout.filter_button_tag, container, false);
//            t.setText(tagsArray[i]);
//            t.setSingleLine(true);
//            t.setBackground(getResources().getDrawable(R.drawable.filter_button));
//            t.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(v.isSelected()){
//                        ((Button)v).setSelected(false);
//                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.dialog_button_cancel));
//                        mStartCommunicationListner.removeRating(((Button) v).getText().toString());
//                    }else{
//                        ((Button)v).setSelected(true);
//                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.button_text));
//                        mStartCommunicationListner.addRating(((Button) v).getText().toString());
//                    }
//
//                }
//            });
//            ratingFlowLayout.
//                    addView(t);
//        }
//    }
//
//    public void populateSize(String [] tagsArray){
//        sizeLayout.setVisibility(View.VISIBLE);
//        sizeFlowLayout.removeAllViews();
//        for (int i = 0; i < tagsArray.length; i++) {
//            Button t = (Button) inflater.inflate(R.layout.filter_button_tag, container, false);
//            t.setText(tagsArray[i]);
//            t.setSingleLine(true);
//            t.setBackground(getResources().getDrawable(R.drawable.filter_button));
//            t.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(v.isSelected()){
//                        ((Button)v).setSelected(false);
//                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.dialog_button_cancel));
//                        mStartCommunicationListner.removeSize(((Button) v).getText().toString());
//                    }else{
//                        ((Button)v).setSelected(true);
//                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.button_text));
//                        mStartCommunicationListner.addSize(((Button) v).getText().toString());
//                    }
//
//                }
//            });
//            sizeFlowLayout.
//                    addView(t);
//        }
//    }
//
//
//    public void populateBrand(String [] tagsArray){
//        brandLayout.setVisibility(View.VISIBLE);
//        brandFlowLayout.removeAllViews();
//        for (int i = 0; i < tagsArray.length; i++) {
//            Button t = (Button) inflater.inflate(R.layout.filter_button_tag, container, false);
//            t.setText(tagsArray[i]);
//            t.setSingleLine(true);
//            t.setBackground(getResources().getDrawable(R.drawable.filter_button));
//            t.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(v.isSelected()){
//                        ((Button)v).setSelected(false);
//                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.dialog_button_cancel));
//                        mStartCommunicationListner.removeBrand(((Button) v).getText().toString());
//                    }else{
//                        ((Button)v).setSelected(true);
//                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.button_text));
//                        mStartCommunicationListner.addBrand(((Button) v).getText().toString());
//                    }
//
//                }
//            });
//            brandFlowLayout.
//                    addView(t);
//        }
//    }
//
//
//    public void populatePrice(String [] tagsArray){
//        priceLayout.setVisibility(View.VISIBLE);
//        priceFlowLayout.removeAllViews();
//        for (int i = 0; i < tagsArray.length; i++) {
//            Button t = (Button) inflater.inflate(R.layout.filter_button_tag, container, false);
//            t.setText(tagsArray[i]);
//            t.setSingleLine(true);
//            t.setBackground(getResources().getDrawable(R.drawable.filter_button));
//            t.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(v.isSelected()){
//                        ((Button)v).setSelected(false);
//                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.dialog_button_cancel));
//                        mStartCommunicationListner.removePrice(((Button) v).getText().toString());
//                    }else{
//                        ((Button)v).setSelected(true);
//                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.button_text));
//                        mStartCommunicationListner.addPrice(((Button) v).getText().toString());
//                    }
//
//                }
//            });
//            priceFlowLayout.
//                    addView(t);
//        }
//    }


    public class GetProductDetailsCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject mMembersJSON;
                mMembersJSON = new JSONObject(response);
                if(mMembersJSON.getJSONObject("STATUS").getInt("CODE")==200){
                    //POPULATE FILTERS HERE
                    JSONObject data = mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA");
                    JSONArray filtersJSONARRAY = data.getJSONArray("FILTERS");
                    filtersArray.clear();
                    int spinnerValue = spinner.getSelectedItemPosition();
                    if(spinnerValue==-1){
                        JSONArray subSectionJSONARRAY = data.getJSONArray("SUB_SECTIONS");
                        categoryStringArray.clear();
                        categoryKeyStringArray.clear();
                        categoryStringArray.add("All");
                        categoryKeyStringArray.add("ALL");
                        for(int i=0;i<subSectionJSONARRAY.length();i++){
                            String titleSubCategory = subSectionJSONARRAY.getJSONObject(i).getString("TITLE");
                            String valueSubCategory = subSectionJSONARRAY.getJSONObject(i).getString("VALUE");
                            categoryStringArray.add(titleSubCategory);
                            categoryKeyStringArray.add(valueSubCategory);
                        }
                    }

                    for(int i=0;i<filtersJSONARRAY.length();i++){
                        JSONArray valuesJSONARRAY = filtersJSONARRAY.getJSONObject(i).getJSONArray("VALUES");
                        ArrayList<FilterValue> valuesArray = new ArrayList<FilterValue>();
                        for(int x=0;x<valuesJSONARRAY.length();x++){
                            valuesArray.add(new FilterValue(valuesJSONARRAY.getJSONObject(x).getString("TITLE"), valuesJSONARRAY.getJSONObject(x).getString("KEY"),
                                    valuesJSONARRAY.getJSONObject(x).getString("VALUE"), "",
                                    valuesJSONARRAY.getJSONObject(x).optInt("SORT")));
                        }
                        filtersArray.add(new Filter(filtersJSONARRAY.getJSONObject(i).getString("TITLE"), filtersJSONARRAY.getJSONObject(i).getString("TYPE"),
                                valuesArray));
                    }

                    populateFilter();


                    /**
                    String tagArrayString;
                    Gson converter;
                    Type type;
                    String[] strArray;
                    List<String> tagsList = new ArrayList<String>();
                    if(mMembersJSON.has("tags") && !mMembersJSON.getString("tags").equalsIgnoreCase("") && !mMembersJSON.getString("tags").equalsIgnoreCase("null") && mMembersJSON.getString("tags")!=null) {
                        tagArrayString = mMembersJSON.getString("tags");
                        converter = new Gson();
                        type = new TypeToken<List<String>>() {
                        }.getType();
                        tagsList = converter.fromJson(tagArrayString, type);
                        //convert List to Array in Java
                        strArray = tagsList.toArray(new String[0]);
                        populateTags(strArray);
                    }

                    if(mMembersJSON.has("ratings") && !mMembersJSON.getString("ratings").equalsIgnoreCase("") && !mMembersJSON.getString("ratings").equalsIgnoreCase("null") && mMembersJSON.getString("ratings")!=null) {
                        tagArrayString = mMembersJSON.getString("ratings");
                        converter = new Gson();
                        type = new TypeToken<List<String>>() {
                        }.getType();
                        tagsList = converter.fromJson(tagArrayString, type);
                        //convert List to Array in Java
                        strArray = tagsList.toArray(new String[0]);
                        populateRatings(strArray);
                    }

                    if(mMembersJSON.has("size") && !mMembersJSON.getString("size").equalsIgnoreCase("") && !mMembersJSON.getString("size").equalsIgnoreCase("null") && mMembersJSON.getString("size")!=null) {
                        tagArrayString = mMembersJSON.getString("size");
                        converter = new Gson();
                        type = new TypeToken<List<String>>() {
                        }.getType();
                        tagsList = converter.fromJson(tagArrayString, type);
                        //convert List to Array in Java
                        strArray = tagsList.toArray(new String[0]);
                        populateSize(strArray);
                    }

                    if(mMembersJSON.has("brand") && !mMembersJSON.getString("brand").equalsIgnoreCase("") && !mMembersJSON.getString("brand").equalsIgnoreCase("null") && mMembersJSON.getString("brand")!=null) {
                        tagArrayString = mMembersJSON.getString("brand");
                        converter = new Gson();
                        type = new TypeToken<List<String>>() {
                        }.getType();
                        tagsList = converter.fromJson(tagArrayString, type);
                        //convert List to Array in Java
                        strArray = tagsList.toArray(new String[0]);
                        populateBrand(strArray);
                    }

                    if(mMembersJSON.has("colors") && !mMembersJSON.getString("colors").equalsIgnoreCase("") && !mMembersJSON.getString("colors").equalsIgnoreCase("null") && mMembersJSON.getString("colors")!=null){
                        tagArrayString = mMembersJSON.getString("colors");
                        converter = new Gson();
                        type = new TypeToken<List<String>>(){}.getType();
                        tagsList = converter.fromJson(tagArrayString, type);
                        //convert List to Array in Java
                        strArray = tagsList.toArray(new String[0]);
                        populateColors(strArray);
                    }


                    if(mMembersJSON.has("price") && !mMembersJSON.getString("price").equalsIgnoreCase("") && !mMembersJSON.getString("price").equalsIgnoreCase("null") && mMembersJSON.getString("price")!=null) {
                        tagArrayString = mMembersJSON.getString("price");
                        converter = new Gson();
                        type = new TypeToken<List<String>>() {
                        }.getType();
                        tagsList = converter.fromJson(tagArrayString, type);
                        //convert List to Array in Java
                        strArray = tagsList.toArray(new String[0]);
                        populatePrice(strArray);
                    }
                     **/
                    progressLayout.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                Snackbar.make(getActivity().findViewById(android.R.id.content), "Dynamic Filters could not be loaded", Snackbar.LENGTH_INDEFINITE)
//                        .setActionTextColor(Color.RED)
//                        .show();
                progressLayout.setVisibility(View.GONE);
            }
        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            progressLayout.setVisibility(View.VISIBLE);
        }
    }


//    public class GetProductsCallback extends AsyncCallback {
//        public void onTaskComplete(String response) {
//            try {
//                JSONObject mMembersJSON;
//                mMembersJSON = new JSONObject(response);
//                JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_PRODUCT_LIST_NAME);
//                int length = jsonArray.length();
//
//                for(int i=0;i<length;i++){
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    Products item = new Products();
//                    item.setId(jsonObject.getString("ID"));
//                    item.setName(jsonObject.getString("NAME"));
//                    item.setImage(jsonObject.getString("IMAGE"));
//                    JSONObject priceObject = jsonObject.getJSONObject("PRICE");
//                    item.setPriceNew(priceObject.getInt("PRICE_NEW"));
//                    item.setPriceOld(priceObject.getInt("PRICE_OLD"));
//                    if (priceObject.getInt("PRICE_OLD")!=0){
//                        item.setDiscount(priceObject.getInt("DISCOUNT"));
//                        item.setDiscPercent(priceObject.getInt("PERCENT"));
//                        item.setY(priceObject.getJSONObject("TIMER").getString("Y"));
//                        item.setM(priceObject.getJSONObject("TIMER").getString("M"));
//                        item.setD(priceObject.getJSONObject("TIMER").getString("D"));
//                        item.setH(priceObject.getJSONObject("TIMER").getString("H"));
//                        item.setI(priceObject.getJSONObject("TIMER").getString("I"));
//                        item.setS(priceObject.getJSONObject("TIMER").getString("S"));
//                        item.setInDays(priceObject.getJSONObject("TIMER").getInt("IN_DAYS"));
//                        item.setEndTime(priceObject.getInt("END_TIME"));
//                    }
//                    productsArrayList.add(item);
//                }
//
//                if(getActivity()!=null){
//                    Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
//                    progressBar.startAnimation(animation);
//                }
//                progressBar.setVisibility(View.GONE);
//                initializeData();
//            } catch (JSONException e) {
//                e.printStackTrace();
//                Snackbar.make(getActivity().findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
//                        .setActionTextColor(Color.RED)
//                        .show();
//            }
//        }
//        @Override
//        public void onTaskCancelled() {
//        }
//        @Override
//        public void onPreExecute() {
//            // TODO Auto-generated method stub
//            progressBar.setVisibility(View.VISIBLE);
//        }
//    }



//    private void initializeData(){
//        mAdapter = new HotDealsAdapter(getActivity(), productsArrayList);
//        mRecyclerView.setAdapter(mAdapter);
//    }

}
