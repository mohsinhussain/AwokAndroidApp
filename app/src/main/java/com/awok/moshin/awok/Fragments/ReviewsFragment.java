package com.awok.moshin.awok.Fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.awok.moshin.awok.R;


public class ReviewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String productName,image;
    private String mParam2;
    private RatingBar prod_reviewRating;




    public ReviewsFragment() {
        // Required empty public constructor
    }

    public ReviewsFragment(String productName,String image) {
        this.productName=productName;
        this.image=image;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_reviews, container, false);

        TextView productNameView=(TextView)mView.findViewById(R.id.productTitle);
        ImageView imgMain=(ImageView)mView.findViewById(R.id.mainImg);

        productNameView.setText(productName);
        imgMain.setImageBitmap(base64ToBitmap(image));
        prod_reviewRating=(RatingBar)mView.findViewById(R.id.main_prodRatingBar);
        LayerDrawable mainRatingColor = (LayerDrawable) prod_reviewRating.getProgressDrawable();
        mainRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        mainRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        mainRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);

        return mView;
    }

    private Bitmap base64ToBitmap(String imageString) {
        byte[] imageAsBytes = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

    }



}
