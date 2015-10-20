package com.awok.moshin.awok.Fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.awok.moshin.awok.Activities.MainActivity;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liangfeizc.flowlayout.FlowLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FilterFragment extends Fragment {

    View mView;
    private String TAG = "Filter Fragment";
    FlowLayout colorFlowLayout, tagsFlowLayout, priceFlowLayout;
    public FilterFragment(){}
    String catId = null;
//    ProgressBar progressBar;
    LayoutInflater inflater;
    ViewGroup container;
    MainActivity activity;
    RelativeLayout colorLayout, tagsLayout, priceLayout;
    StartCommunication mStartCommunicationListner;

    public FilterFragment(MainActivity mainActivity){
        this.activity = mainActivity;
    }

    public FilterFragment(String catId, MainActivity mainActivity){
        this.activity = mainActivity;
        this.catId = catId;
    }

    public interface StartCommunication
    {
        public void addColor(String color);
        public void removeColor(String color);
        public void addTag(String tag);
        public void removeTag(String tag);
        public void addPrice(String price);
        public void removePrice(String price);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;
        this.container = container;
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mView = inflater.inflate(R.layout.fragment_filter, container, false);
        colorFlowLayout = (FlowLayout) mView.findViewById(R.id.colorFlowlayout);
        tagsFlowLayout = (FlowLayout) mView.findViewById(R.id.tagsFlowlayout);
        priceFlowLayout = (FlowLayout) mView.findViewById(R.id.priceFlowLayout);
        colorLayout = (RelativeLayout) mView.findViewById(R.id.colorLayout);
        tagsLayout = (RelativeLayout) mView.findViewById(R.id.tagsLayout);
        priceLayout = (RelativeLayout) mView.findViewById(R.id.priceLayout);
        colorLayout.setVisibility(View.GONE);
        tagsLayout.setVisibility(View.GONE);
        priceLayout.setVisibility(View.GONE);
//        progressBar = (ProgressBar) mView.findViewById(R.id.marker_progress);
//        progressBar.setVisibility(View.GONE);

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


        return mView;
    }

    public void populateColors(String [] tagsArray){

        colorLayout.setVisibility(View.VISIBLE);
        for (int i = 0; i < tagsArray.length; i++) {
            Button t = (Button) inflater.inflate(R.layout.filter_button_tag, container, false);
            t.setText(tagsArray[i]);
            t.setSingleLine(true);
            t.setBackground(getResources().getDrawable(R.drawable.filter_button));
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v.isSelected()){
                        ((Button)v).setSelected(false);
                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.dialog_button_cancel));
                        mStartCommunicationListner.removeColor(((Button) v).getText().toString());
                    }else{
                        ((Button)v).setSelected(true);
                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.button_text));
                        mStartCommunicationListner.addColor(((Button) v).getText().toString());
                    }


                }
            });
            colorFlowLayout.
                    addView(t);
        }
    }

    public void populateTags(String [] tagsArray){
        tagsLayout.setVisibility(View.VISIBLE);
        for (int i = 0; i < tagsArray.length; i++) {
            Button t = (Button) inflater.inflate(R.layout.filter_button_tag, container, false);
            t.setText(tagsArray[i]);
            t.setSingleLine(true);
            t.setBackground(getResources().getDrawable(R.drawable.filter_button));
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v.isSelected()){
                        ((Button)v).setSelected(false);
                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.dialog_button_cancel));
                        mStartCommunicationListner.removeTag(((Button) v).getText().toString());
                    }else{
                        ((Button)v).setSelected(true);
                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.button_text));
                        mStartCommunicationListner.addTag(((Button) v).getText().toString());
                    }

                }
            });
            tagsFlowLayout.
                    addView(t);
        }
    }

    public void populatePrice(String [] tagsArray){
        priceLayout.setVisibility(View.VISIBLE);
        for (int i = 0; i < tagsArray.length; i++) {
            Button t = (Button) inflater.inflate(R.layout.filter_button_tag, container, false);
            t.setText(tagsArray[i]);
            t.setSingleLine(true);
            t.setBackground(getResources().getDrawable(R.drawable.filter_button));
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v.isSelected()){
                        ((Button)v).setSelected(false);
                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.dialog_button_cancel));
                        mStartCommunicationListner.removePrice(((Button) v).getText().toString());
                    }else{
                        ((Button)v).setSelected(true);
                        ((Button)v).setTextColor(getContext().getResources().getColor(R.color.button_text));
                        mStartCommunicationListner.addPrice(((Button) v).getText().toString());
                    }

                }
            });
            priceFlowLayout.
                    addView(t);
        }
    }


    public class GetProductDetailsCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject mMembersJSON;
                mMembersJSON = new JSONObject(response);
                if(mMembersJSON.getInt("status")==1){
                    String tagArrayString = mMembersJSON.getString("tags");
                    Gson converter = new Gson();
                    Type type = new TypeToken<List<String>>(){}.getType();
                    List<String> tagsList = converter.fromJson(tagArrayString, type );
                    //convert List to Array in Java
                    String [] strArray = tagsList.toArray(new String[0]);
                    populateTags(strArray);

                    if(!mMembersJSON.getString("colors").equalsIgnoreCase("") && !mMembersJSON.getString("colors").equalsIgnoreCase("null") && mMembersJSON.getString("colors")!=null){
                        tagArrayString = mMembersJSON.getString("colors");
                        converter = new Gson();
                        type = new TypeToken<List<String>>(){}.getType();
                        tagsList = converter.fromJson(tagArrayString, type);
                        //convert List to Array in Java
                        strArray = tagsList.toArray(new String[0]);
                        populateColors(strArray);
                    }



                    tagArrayString = mMembersJSON.getString("price");
                    converter = new Gson();
                    type = new TypeToken<List<String>>(){}.getType();
                    tagsList = converter.fromJson(tagArrayString, type);
                    //convert List to Array in Java
                    strArray = tagsList.toArray(new String[0]);
                    populatePrice(strArray);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Dynamic Filters could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();
            }
        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
//            progressBar.setVisibility(View.VISIBLE);
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
