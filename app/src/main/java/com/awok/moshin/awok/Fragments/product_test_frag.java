package com.awok.moshin.awok.Fragments;

import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awok.moshin.awok.R;

/**
 * Created by shon on 9/10/2015.
 */
public class product_test_frag extends Fragment{






    static final int NUM_ITEMS = 6;
    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager viewPager;
    int[] mResources = {R.drawable.eagle, R.drawable.horse, R.drawable.bonobo, R.drawable.wolf, R.drawable.owl};


private TextView productTitle,product_reviewCount,prod_warranty,prod_color,prod_color_default,prod_colorSecondary,prod_shipping,prod_shippingCost,prod_delivery,prod_deliveryTime,prod_reviews,quickDeliveryTxt,
        prod_price,prod_discountPrice;

 private Button prod_buyNow;
    private RatingBar prodRatingBar,prod_reviewRating;
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
     //   prod_price=(TextView)mView.findViewById(R.id.prod_price);
     //   prod_discountPrice=(TextView)mView.findViewById(R.id.prod_discountPrice);
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



        mCustomPagerAdapter = new CustomPagerAdapter(getContext());


        viewPager = (ViewPager) mView.findViewById(R.id.imageSlider);
        viewPager.setAdapter(mCustomPagerAdapter);




        return mView;
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
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.fragment_imageslider, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView.setImageResource(mResources[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }

}
