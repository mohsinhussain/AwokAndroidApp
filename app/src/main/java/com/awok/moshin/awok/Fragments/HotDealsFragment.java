package com.awok.moshin.awok.Fragments;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.awok.moshin.awok.Adapters.HotDealsAdapter;
import com.awok.moshin.awok.Models.Items;
import com.awok.moshin.awok.Models.Person;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class HotDealsFragment extends Fragment {

    RecyclerView mRecyclerView;
    HotDealsAdapter mAdapter;
    View mView;
    ProgressBar progressBar;
    ArrayList<Items> itemsArrayList;
    private String TAG = "Hot Deals Fragment";
    public HotDealsFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_hot_deals, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.dealsRecyclerView);
        progressBar = (ProgressBar) mView.findViewById(R.id.marker_progress);
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);


        itemsArrayList = new ArrayList<Items>();

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new APIClient(getActivity(), getActivity(),  new GetTestCallback()).testAPICall();
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }
        return mView;
    }


    public class GetTestCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject mMembersJSON;
                mMembersJSON = new JSONObject(response);
                JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_PRODUCT_LIST_NAME);
                int length = jsonArray.length();

                for(int i=0;i<length;i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Items item = new Items();
                    item.setId(jsonObject.getString("ID"));
                    item.setName(jsonObject.getString("NAME"));
                    item.setImage(jsonObject.getString("IMAGE"));
                    JSONObject priceObject = jsonObject.getJSONObject("PRICE");
                    item.setPriceNew(priceObject.getInt("PRICE_NEW"));
                    item.setPriceOld(priceObject.getInt("PRICE_OLD"));
                    if (priceObject.getInt("PRICE_OLD")!=0){
                        item.setDiscount(priceObject.getInt("DISCOUNT"));
                        item.setDiscPercent(priceObject.getInt("PERCENT"));
                        item.setY(priceObject.getJSONObject("TIMER").getString("Y"));
                        item.setM(priceObject.getJSONObject("TIMER").getString("M"));
                        item.setD(priceObject.getJSONObject("TIMER").getString("D"));
                        item.setH(priceObject.getJSONObject("TIMER").getString("H"));
                        item.setI(priceObject.getJSONObject("TIMER").getString("I"));
                        item.setS(priceObject.getJSONObject("TIMER").getString("S"));
                        item.setInDays(priceObject.getJSONObject("TIMER").getInt("IN_DAYS"));
                        item.setEndTime(priceObject.getInt("END_TIME"));
                    }
                    itemsArrayList.add(item);
                }

                if(getActivity()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
                    progressBar.startAnimation(animation);
                }
                progressBar.setVisibility(View.GONE);
                initializeData();
            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
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
            progressBar.setVisibility(View.VISIBLE);
        }
    }


//    private class getDataTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected String doInBackground(String... urls) {
//
//            // params comes from the execute() call: params[0] is the url.
//            try {
////                return downloadUrl(urls[0]);
//            } catch (IOException e) {
//                return "Unable to retrieve web page. URL may be invalid.";
//            }
//        }
//        // onPostExecute displays the results of the AsyncTask.
//        @Override
//        protected void onPostExecute(String result) {
//            Log.v("Hot Deals Fragment", result);
//            try {
//                itemsArrayList.clear();
//                JSONObject obj = new JSONObject(result);
//
//                JSONArray jsonArray = obj.getJSONArray("PRODUCTS");
//                for(int i=0;i<jsonArray.length();i++){
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    Items item = new Items();
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
//                    itemsArrayList.add(item);
//                }
//
//                Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
//                progressBar.startAnimation(animation);
//                progressBar.setVisibility(View.GONE);
//                initializeData();
//            } catch (JSONException e) {
//                e.printStackTrace();
//                Snackbar.make(getActivity().findViewById(android.R.id.content), "Some thing is wrong with API"+e.toString(), Snackbar.LENGTH_INDEFINITE)
//                        .setActionTextColor(Color.RED)
//                        .show();
//            }
//        }
//    }

//    // Given a URL, establishes an HttpUrlConnection and retrieves
//// the web page content as a InputStream, which it returns as
//// a string.
//    private String downloadUrl(String myurl) throws IOException {
//        InputStream is = null;
//        // Only display the first 500 characters of the retrieved
//        // web page content.
//        int len = 500;
//
//        try {
//            URL url = new URL(myurl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(10000 /* milliseconds */);
//            conn.setConnectTimeout(15000 /* milliseconds */);
//            conn.setRequestMethod("GET");
//            conn.setDoInput(true);
//            // Starts the query
//            conn.connect();
//            int response = conn.getResponseCode();
//            Log.d("Hot Deals Fragment", "The response is: " + response);
//            is = conn.getInputStream();
//
//            // Convert the InputStream into a string
//            String contentAsString = readIt(is);
//            return contentAsString;
//
//            // Makes sure that the InputStream is closed after the app is
//            // finished using it.
//        } finally {
//            if (is != null) {
//                is.close();
//            }
//        }
//    }


    private void initializeData(){
        mAdapter = new HotDealsAdapter(getActivity(), itemsArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }







}
