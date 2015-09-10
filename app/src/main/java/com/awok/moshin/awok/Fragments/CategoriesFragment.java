package com.awok.moshin.awok.Fragments;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.awok.moshin.awok.Adapters.CategoriesAdapter;
import com.awok.moshin.awok.Models.Categories;
import com.awok.moshin.awok.Models.Products;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment {

    RecyclerView mRecyclerView;
    CategoriesAdapter mAdapter;
    View mView;
    ProgressBar progressBar;
    ArrayList<Categories> categoriesArrayList;
    private String TAG = "Categories Fragment";
    public CategoriesFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_categories, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.caetegoriesRecyclerView);
        progressBar = (ProgressBar) mView.findViewById(R.id.marker_progress);
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);


        categoriesArrayList = new ArrayList<Categories>();

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new APIClient(getActivity(), getActivity(),  new GetCategoriesCallback()).categoriesAPICall();
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }
        return mView;
    }


    public class GetCategoriesCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject mMembersJSON;
                mMembersJSON = new JSONObject(response);
                JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_CATEGORY_LIST_NAME);
                int length = jsonArray.length();

                for(int i=0;i<length;i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    categoriesArrayList.add(new Categories(jsonObject.getString("ID"), jsonObject.getString("NAME"), jsonObject.getString("SORT"), jsonObject.getString("IMAGE"),
                            jsonObject.getString("DEPTH_LEVEL")));
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



    private void initializeData(){
        mAdapter = new CategoriesAdapter(getActivity(), categoriesArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }

}
