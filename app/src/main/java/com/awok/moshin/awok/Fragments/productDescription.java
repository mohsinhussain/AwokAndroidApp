package com.awok.moshin.awok.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awok.moshin.awok.Activities.FragmentFullScreenImage;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.ProductDetailsModel;
import com.awok.moshin.awok.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shon on 9/10/2015.
 */
public class productDescription extends Fragment{



    ProductDetailsModel productModel = new ProductDetailsModel();

    static final int NUM_ITEMS = 6;
    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager viewPager;
    int[] mResources = {R.drawable.eagle, R.drawable.horse, R.drawable.bonobo, R.drawable.wolf, R.drawable.owl};


private TextView productTitle,product_reviewCount,prod_warranty,prod_color,prod_color_default,prod_colorSecondary,prod_shipping,prod_shippingCost,prod_delivery,prod_deliveryTime,prod_reviews,quickDeliveryTxt,
        prod_price,prod_discountPrice;

 private Button prod_buyNow;
    private RatingBar prodRatingBar,prod_reviewRating;
    String image;

    public productDescription(ProductDetailsModel productDetails, String imageData) {
        productModel=productDetails;
        image=imageData;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View mView = inflater.inflate(R.layout.product_body_content, container, false);
        productTitle=(TextView)mView.findViewById(R.id.productTitle);
        product_reviewCount=(TextView)mView.findViewById(R.id.product_reviewCount);
        prod_warranty=(TextView)mView.findViewById(R.id.prod_warranty);
        prod_color_default=(TextView)mView.findViewById(R.id.prod_color_default);
        prod_color=(TextView)mView.findViewById(R.id.prod_color);
        prod_colorSecondary=(TextView)mView.findViewById(R.id.prod_colorSecondary);
        prod_shipping=(TextView)mView.findViewById(R.id.prod_shipping);
        prod_shippingCost=(TextView)mView.findViewById(R.id.prod_shippingCost);
        prod_delivery=(TextView)mView.findViewById(R.id.prod_delivery);
        prod_deliveryTime=(TextView)mView.findViewById(R.id.prod_deliveryTime);
        prod_reviews=(TextView)mView.findViewById(R.id.prod_reviews);
        quickDeliveryTxt=(TextView)mView.findViewById(R.id.quickDeliveryTxt);
        //prod_price=(TextView)mView.findViewById(R.id.prod_price);
        //prod_discountPrice=(TextView)mView.findViewById(R.id.prod_discountPrice);
   //  prod_buyNow=(Button)mView.findViewById(R.id.prod_buyNow);

                    prodRatingBar=(RatingBar)mView.findViewById(R.id.main_prodRatingBar);
        prod_reviewRating=(RatingBar)mView.findViewById(R.id.prod_reviewRating);
        LayerDrawable mainRatingColor = (LayerDrawable) prodRatingBar.getProgressDrawable();
        mainRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        mainRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        mainRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);


        LayerDrawable reviewRatingColor = (LayerDrawable) prod_reviewRating.getProgressDrawable();
        reviewRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        reviewRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        reviewRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);

        Typeface mainProdFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf");
        productTitle.setTypeface(mainProdFont);


        Typeface innerFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
        prod_color.setTypeface(innerFont);
        prod_shipping.setTypeface(innerFont);
        prod_delivery.setTypeface(innerFont);
        prod_reviews.setTypeface(innerFont);

        Typeface textFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
        prod_color_default.setTypeface(textFont);
        prod_colorSecondary.setTypeface(textFont);
        prod_shippingCost.setTypeface(textFont);
        prod_deliveryTime.setTypeface(textFont);


//     prod_price.setTypeface(innerFont);
  //      prod_discountPrice.setTypeface(innerFont);
  //      prod_discountPrice.setPaintFlags(prod_discountPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

  //   prod_buyNow.setTypeface(innerFont);
        System.out.println("COOL" + productModel.getName());
productTitle.setText(productModel.getName());
        //prod_price.setText(productModel.getPriceNew());
        //prod_discountPrice.setText(productModel.getDiscPercent());




        mCustomPagerAdapter = new CustomPagerAdapter(getContext());


        viewPager = (ViewPager) mView.findViewById(R.id.imageSlider);
        viewPager.setAdapter(mCustomPagerAdapter);




        return mView;
    }
    private Bitmap base64ToBitmap(String imageString) {
        byte[] imageAsBytes = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

    }

    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = mLayoutInflater.inflate(R.layout.fragment_imageslider, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            //imageView.setImageResource(mResources[position]);
imageView.setImageBitmap(base64ToBitmap(image));

            container.addView(itemView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getContext(), FragmentFullScreenImage.class);
                    i.putExtra("size",mResources.length);
                    i.putExtra("position", position);
                    i.putExtra("image",image);
                            startActivity(i);
                }
            });

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }

}