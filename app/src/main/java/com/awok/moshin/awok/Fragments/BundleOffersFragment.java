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

import com.awok.moshin.awok.Adapters.HotDealsAdapter;
import com.awok.moshin.awok.Models.Person;
import com.awok.moshin.awok.R;

import java.util.ArrayList;
import java.util.List;

public class BundleOffersFragment extends Fragment {

    RecyclerView mRecyclerView;
    HotDealsAdapter mAdapter;
    View mView;
    public BundleOffersFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_hot_deals, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.dealsRecyclerView);

        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            initializeData();
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet is not connected!", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }
        return mView;
    }




    //Stub Class to populate data


    private List<Person> persons;

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    private void initializeData(){
//        persons = new ArrayList<>();
//        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.emma));
//        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.lavery));
//        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.lillie));
//
//        mAdapter = new HotDealsAdapter(getActivity(), persons);
//        mRecyclerView.setAdapter(mAdapter);
    }







}
