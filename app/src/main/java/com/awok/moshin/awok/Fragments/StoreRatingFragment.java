package com.awok.moshin.awok.Fragments;

import android.content.Context;
import android.content.Intent;
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
import com.awok.moshin.awok.Adapters.ProductRatingAdapter;
import com.awok.moshin.awok.Adapters.StoreRatingAdapter;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.DescriptionModel;
import com.awok.moshin.awok.Models.StoreRatingModel;
import com.awok.moshin.awok.R;

import java.util.List;

/**
 * Created by shon on 10/20/2015.
 */
public class StoreRatingFragment extends Fragment{
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";
  String  storeName,storeSum,storeAverage,storeImage,storeUrl;
        // TODO: Rename and change types of parameters
        private String productName,image,rating,ratingCount;
        private String mParam2;
        private RatingBar prod_reviewRating;
List<StoreRatingModel> model;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private LinearLayout reviewsLayout;
        public StoreRatingFragment() {
            // Required empty public constructor
        }

       /* public StoreRatingFragment(List<StoreRatingModel> model,String  storeName,String storeSum,String storeAverage,String storeImage,String storeUrl) {
         //   this.productName=productName;
          //  this.image=image;
            this.model=model;
            this.storeName=storeName;
            this.storeSum=storeSum;
            this.storeAverage=storeAverage;
            this.storeImage=storeImage;
            this.storeUrl=storeUrl;
          //  ratingCount=ratingCountValue;

        }*/


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View mView = inflater.inflate(R.layout.fragment_store_rating, container, false);

            TextView productNameView=(TextView)mView.findViewById(R.id.productTitle);
            //RatingBar ratingBar=(RatingBar)mView.findViewById(R.id.main_prodRatingBar);
            TextView ratingCounttxt=(TextView)mView.findViewById(R.id.product_reviewCount);
            final TextView visitStore=(TextView)mView.findViewById(R.id.textView4);
            final ImageView imgMain=(ImageView)mView.findViewById(R.id.mainImg);
            final ProgressBar progressBar = (ProgressBar) mView.findViewById(R.id.load_progress_bar);
productNameView.setText(storeName);
            ratingCounttxt.setText("("+storeSum+")");
            visitStore.setTag(storeUrl);
            visitStore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(visitStore.getTag().toString()));
                    startActivity(i);
                }
            });

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            imageLoader.get(storeImage, new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    imgMain.setImageDrawable(getResources().getDrawable(R.drawable.default_img));
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

      //      productNameView.setText(productName);
         /*   ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            imageLoader.get(storeImage, new ImageLoader.ImageListener() {
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
            });*/


            mRecyclerView = (RecyclerView) mView.findViewById(R.id.overViewRecyclerView);

            mRecyclerView.setNestedScrollingEnabled(false);
            mRecyclerView.setHasFixedSize(false);


            mRecyclerView.setLayoutManager(new MyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

//
            mAdapter = new StoreRatingAdapter(getActivity(),model);
            mRecyclerView.setAdapter(mAdapter);


        /*    System.out.println(storeName.toString());

            System.out.println(storeSum.toString());
            System.out.println(storeAverage.toString());
            System.out.println(storeImage.toString());
            System.out.println(storeUrl.toString());*/
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
            prod_reviewRating.setRating(Float.parseFloat(storeAverage));
      //      ratingCounttxt.setText("("+ratingCount+")");
            LayerDrawable mainRatingColor = (LayerDrawable) prod_reviewRating.getProgressDrawable();
            mainRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
            mainRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
            mainRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);

            reviewsLayout = (LinearLayout) mView.findViewById(R.id.reviews);
            if(model.size()>0){
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
    public void call(List<StoreRatingModel> model,String  storeName,String storeSum,String storeAverage,String storeImage,String storeUrl)
    {
        this.model=model;
        this.storeName=storeName;
        this.storeSum=storeSum;
        this.storeAverage=storeAverage;
        this.storeImage=storeImage;
        this.storeUrl=storeUrl;
    }

}
