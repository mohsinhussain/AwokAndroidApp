package com.awok.moshin.awok.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.awok.moshin.awok.Adapters.ProductOverViewAdapter;
import com.awok.moshin.awok.Adapters.ProductSpecificationAdapter;
import com.awok.moshin.awok.Models.ProductOverview;
import com.awok.moshin.awok.Models.ProductSpecification;
import com.awok.moshin.awok.R;

import java.util.ArrayList;
import java.util.List;


public class ProductOverViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    /**
     * Created by shon on 9/10/2015.
     */
    public class product_test_frag extends Fragment {
        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        private List<ProductOverview> overViewList = new ArrayList<ProductOverview>();


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View mView = inflater.inflate(R.layout.fragment_product_over_view, container, false);

















            mRecyclerView = (RecyclerView) mView.findViewById(R.id.overViewRecyclerView);

            // getSupportActionBar().setIcon(R.drawable.ic_launcher);

            // getSupportActionBar().setTitle("Android Versions");

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new ProductOverViewAdapter(getActivity(),overViewList);
            mRecyclerView.setAdapter(mAdapter);



            int i=0;
            for(i=0;i<=20;i++)
            {
                ProductOverview listData=new ProductOverview();
                listData.setOverViewText("Spec" + " " + i);
                listData.setOverViewTitle("VALUES" + " " + i);

                overViewList.add(listData);

            }
            mAdapter.notifyDataSetChanged();

            return mView;
        }

    }
}
