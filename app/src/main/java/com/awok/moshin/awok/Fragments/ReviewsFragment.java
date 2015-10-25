package com.awok.moshin.awok.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.awok.moshin.awok.Adapters.DescriptionAdapter;
import com.awok.moshin.awok.Adapters.ProductRatingAdapter;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.DescriptionModel;
import com.awok.moshin.awok.Models.ProductRatingModel;
import com.awok.moshin.awok.R;

import java.util.List;


public class ReviewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String productName,image,ratingCount,ratingtxt,boughtBy,savedBy;
    private String mParam2;
    private RatingBar prod_reviewRating;

    List<ProductRatingModel> rating;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayout reviewsLayout;
    public ReviewsFragment() {
        // Required empty public constructor
    }

 /*   public ReviewsFragment(String productName,String image,String ratingValue,String ratingCountValue) {
        this.productName=productName;
        this.image=image;
        rating=ratingValue;
        ratingCount=ratingCountValue;
    }*/

    public ReviewsFragment(List<ProductRatingModel> rating,String productName,String image,String ratingValue,String ratingCountValue,String boughtBy,String savedBy) {
        this.productName=productName;
        this.image=image;
        this.ratingtxt=ratingValue;
        ratingCount=ratingCountValue;
        this.rating=rating;
        this.boughtBy=boughtBy;
        this.savedBy=savedBy;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_reviews, container, false);

        TextView productNameView=(TextView)mView.findViewById(R.id.productTitle);
        //RatingBar ratingBar=(RatingBar)mView.findViewById(R.id.main_prodRatingBar);
        TextView ratingCounttxt=(TextView)mView.findViewById(R.id.product_reviewCount);
        TextView boughtByTxt=(TextView)mView.findViewById(R.id.textView);
        TextView savedByTxt=(TextView)mView.findViewById(R.id.textView2);
        final ImageView imgMain=(ImageView)mView.findViewById(R.id.mainImg);
        final ProgressBar progressBar = (ProgressBar) mView.findViewById(R.id.load_progress_bar);
boughtByTxt.setText("Bought By: "+boughtBy+" people");
        savedByTxt.setText("Saved By: "+savedBy+" people");
        productNameView.setText(productName);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(image, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imgMain.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.default_img));
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    imgMain.setImageBitmap(response.getBitmap());
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
//        if(image!=null && !image.equalsIgnoreCase("")){
//            imgMain.setImageUrl(image, imageLoader);
//        }
//        else{
//            imgMain.setImageDrawable(getActivity().getResources().getDrawable((R.drawable.default_img)));
//        }
//        imgMain.setImageUrl(image, imageLoader);
//        imgMain.setErrorImageResId(R.drawable.default_img);
//        imgMain.setImageBitmap(base64ToBitmap(image));
//        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//        imageLoader.get(image, new ImageLoader.ImageListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("HotDealsAdapter", "Image Load Error: " + error.getMessage());
//            }
//            @Override
//            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
//                if (response.getBitmap() != null) {
//                    // load image into imageview
//                    imgMain.setImageBitmap(response.getBitmap());
//                }
//            }
//        });
        prod_reviewRating=(RatingBar)mView.findViewById(R.id.main_prodRatingBar);
        prod_reviewRating.setRating(Float.parseFloat(ratingtxt));
        ratingCounttxt.setText("("+ratingCount+")");
        LayerDrawable mainRatingColor = (LayerDrawable) prod_reviewRating.getProgressDrawable();
        mainRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        mainRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        mainRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.overViewRecyclerView);
//
//            // getSupportActionBar().setIcon(R.drawable.ic_launcher);
//
//            // getSupportActionBar().setTitle("Android Versions");
//
//            // use this setting to improve performance if you know that changes
//            // in content do not change the layout size of the RecyclerView
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);


        mRecyclerView.setLayoutManager(new MyLinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
//
//            // use a linear layout manager
       /* mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);*/
//
        mAdapter = new ProductRatingAdapter(getActivity(),rating);
        mRecyclerView.setAdapter(mAdapter);
        reviewsLayout = (LinearLayout) mView.findViewById(R.id.reviews);
        if(rating.size()>0){
            reviewsLayout.setVisibility(View.GONE);
        }
        return mView;
    }

    private Bitmap base64ToBitmap(String imageString) {
        byte[] imageAsBytes = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

    }

    public class MyLinearLayoutManager extends LinearLayoutManager {

        public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout)    {
            super(context, orientation, reverseLayout);
        }

        private int[] mMeasuredDimension = new int[2];

        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state,
                              int widthSpec, int heightSpec) {
            final int widthMode = View.MeasureSpec.getMode(widthSpec);
            final int heightMode = View.MeasureSpec.getMode(heightSpec);
            final int widthSize = View.MeasureSpec.getSize(widthSpec);
            final int heightSize = View.MeasureSpec.getSize(heightSpec);
            int width = 0;
            int height = 0;
            for (int i = 0; i < getItemCount(); i++) {
                measureScrapChild(recycler, i,
                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                        mMeasuredDimension);

                if (getOrientation() == HORIZONTAL) {
                    width = width + mMeasuredDimension[0];
                    if (i == 0) {
                        height = mMeasuredDimension[1];
                    }
                } else {
                    height = height + mMeasuredDimension[1];
                    if (i == 0) {
                        width = mMeasuredDimension[0];
                    }
                }
            }
            switch (widthMode) {
                case View.MeasureSpec.EXACTLY:
                    width = widthSize;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }

            switch (heightMode) {
                case View.MeasureSpec.EXACTLY:
                    height = heightSize;
                case View.MeasureSpec.AT_MOST:
                case View.MeasureSpec.UNSPECIFIED:
            }

            setMeasuredDimension(width, height);
        }

        private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec,
                                       int heightSpec, int[] measuredDimension) {
            View view = recycler.getViewForPosition(position);
            if (view != null) {
                RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
                int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                        getPaddingLeft() + getPaddingRight(), p.width);
                int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                        getPaddingTop() + getPaddingBottom(), p.height);
                view.measure(childWidthSpec, childHeightSpec);
                measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
                recycler.recycleView(view);
            }
        }
    }

}
