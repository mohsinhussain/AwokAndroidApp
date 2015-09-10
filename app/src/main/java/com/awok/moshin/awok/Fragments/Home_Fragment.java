package com.awok.moshin.awok.Fragments;


import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.awok.moshin.awok.R;

public class Home_Fragment extends Fragment {
View mView;
    public Home_Fragment(){}

    public Home_Fragment(Activity activity) {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
        mView = inflater.inflate(R.layout.product_detail_page, container, false);
        //TextView b=(TextView)mView.findViewById(R.id.button3);
        //b.setPaintFlags(b.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );

        return mView;
    }







}
