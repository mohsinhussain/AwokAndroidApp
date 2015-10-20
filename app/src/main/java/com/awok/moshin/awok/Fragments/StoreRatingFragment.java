package com.awok.moshin.awok.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.R;

/**
 * Created by shon on 10/20/2015.
 */
public class StoreRatingFragment extends Fragment{
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String productName,image,rating,ratingCount;
        private String mParam2;
        private RatingBar prod_reviewRating;




        public StoreRatingFragment() {
            // Required empty public constructor
        }

        public StoreRatingFragment(String productName,String image,String ratingValue,String ratingCountValue) {
            this.productName=productName;
            this.image=image;
            rating=ratingValue;
            ratingCount=ratingCountValue;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View mView = inflater.inflate(R.layout.fragment_store_rating, container, false);

            TextView productNameView=(TextView)mView.findViewById(R.id.productTitle);
            //RatingBar ratingBar=(RatingBar)mView.findViewById(R.id.main_prodRatingBar);
            TextView ratingCounttxt=(TextView)mView.findViewById(R.id.product_reviewCount);
            final ImageView imgMain=(ImageView)mView.findViewById(R.id.mainImg);
            final ProgressBar progressBar = (ProgressBar) mView.findViewById(R.id.load_progress_bar);

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
            prod_reviewRating.setRating(Float.parseFloat(rating));
            ratingCounttxt.setText("("+ratingCount+")");
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
