package com.awok.moshin.awok.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.awok.moshin.awok.Activities.FragmentFullScreenImage;
import com.awok.moshin.awok.Activities.ShippingAddressActivity;
import com.awok.moshin.awok.Adapters.CheckOutAdapter;
import com.awok.moshin.awok.Adapters.ProductOverViewRatingAdapter;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.ProductDetailsModel;
import com.awok.moshin.awok.Models.productOverviewRating;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.awok.moshin.awok.Util.LinearLayoutBridge;
import com.davemorrissey.labs.subscaleview.ImageSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shon on 9/10/2015.
 */
public class ProductOverViewFragment extends Fragment{
private RatingBar ratingMain;
    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private NestedScrollView scroll;

    //private RecyclerView.LayoutManager mLayoutManager;

    ProductDetailsModel productModel = new ProductDetailsModel();
    List<productOverviewRating> ratingData=new ArrayList<productOverviewRating>();



private ImageView countButton,share;

    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager viewPager;
    ArrayList<String> mResources = new ArrayList<String>();
    ArrayList<String> imageString = new ArrayList<String>();



private TextView productTitle,product_reviewCount,prod_warranty,prod_color,prod_color_default,prod_colorSecondary,prod_shipping,prod_shippingCost,prod_delivery,prod_deliveryTime,prod_reviews,quickDeliveryTxt,
        prod_price,prod_discountPrice,countText;

 private Button prod_buyNow,save;
    private RatingBar prodRatingBar,prod_reviewRating;
    String image, baseImage,productName, productId;

    public ProductOverViewFragment(){

    }
    public ProductOverViewFragment(String productId,String productName, String imageData) {
        this.productName=productName;
        this.image=imageData;
        this.productId=productId;

    }
    
    
    public void getRatings()
    {
        ratingData.clear();
        for(int i=0;i<10;i++)
        {
            productOverviewRating productRating=new productOverviewRating();
            productRating.setMainText("SHON PRINSOON ferrao fvnxbvmxbvxfvn,mvnv n");
            productRating.setName("Shon Prinson");
            ratingData.add(productRating);



        }

       //mAdapter.notifyDataSetChanged();
        mCustomPagerAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View mView = inflater.inflate(R.layout.product_body_content, container, false);
        mResources.add(image);
        ratingMain=(RatingBar)mView.findViewById(R.id.main_prodRatingBar);
        viewPager = (ViewPager) mView.findViewById(R.id.imageSlider);
        countButton=(ImageView)mView.findViewById(R.id.imageView);
        productTitle=(TextView)mView.findViewById(R.id.productTitle);
        product_reviewCount=(TextView)mView.findViewById(R.id.product_reviewCount);
        scroll=(NestedScrollView)mView.findViewById(R.id.nestedScroll);
        countText=(TextView)mView.findViewById(R.id.countText);
        //prod_warranty=(TextView)mView.findViewById(R.id.prod_warranty);
        share=(ImageView)mView.findViewById(R.id.share);
        prod_color_default=(TextView)mView.findViewById(R.id.prod_color_default);
        prod_color=(TextView)mView.findViewById(R.id.prod_color);
        prod_colorSecondary=(TextView)mView.findViewById(R.id.prod_colorSecondary);
        prod_shipping=(TextView)mView.findViewById(R.id.prod_shipping);
        prod_shippingCost=(TextView)mView.findViewById(R.id.prod_shippingCost);
        prod_delivery=(TextView)mView.findViewById(R.id.prod_delivery);
        prod_deliveryTime=(TextView)mView.findViewById(R.id.prod_deliveryTime);
        //prod_reviews=(TextView)mView.findViewById(R.id.prod_reviews);
        //quickDeliveryTxt=(TextView)mView.findViewById(R.id.quickDeliveryTxt);
        //prod_price=(TextView)mView.findViewById(R.id.prod_price);
        //prod_discountPrice=(TextView)mView.findViewById(R.id.prod_discountPrice);
   //  prod_buyNow=(Button)mView.findViewById(R.id.prod_buyNow);

        viewPager.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_MOVE && scroll != null) {
                    scroll.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });


       mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerViewRating);

       mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setNestedScrollingEnabled(false);
       // int viewHeight = 100 * (10);
      //  mRecyclerView.getLayoutParams().height = viewHeight;

        // use a linear layout manager
        //mLayoutManager = new LinearLayoutManager(getActivity());
        //LinearLayoutBridge mLayoutManager=new LinearLayoutBridge(getActivity(),LinearLayoutManager.VERTICAL,false);
        MyLinearLayoutManager mLayoutManager=new MyLinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
       mRecyclerView.setLayoutManager(mLayoutManager);

       mAdapter = new ProductOverViewRatingAdapter(getActivity(), ratingData);

             //   scroll.fullScroll(NestedScrollView.FOCUS_UP);




                    prodRatingBar=(RatingBar)mView.findViewById(R.id.main_prodRatingBar);
       // prod_reviewRating=(RatingBar)mView.findViewById(R.id.prod_reviewRating);
        LayerDrawable mainRatingColor = (LayerDrawable) prodRatingBar.getProgressDrawable();
        mainRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        mainRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        mainRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);


//        LayerDrawable reviewRatingColor = (LayerDrawable) prod_reviewRating.getProgressDrawable();
//        reviewRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
//        reviewRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
//        reviewRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);

        Typeface mainProdFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf");
//        productTitle.setTypeface(mainProdFont);


        Typeface innerFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf");
        prod_color.setTypeface(innerFont);
        prod_shipping.setTypeface(innerFont);
        prod_delivery.setTypeface(innerFont);
//        prod_reviews.setTypeface(innerFont);

        Typeface textFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
        prod_color_default.setTypeface(textFont);
        prod_colorSecondary.setTypeface(textFont);
        prod_shippingCost.setTypeface(textFont);
        prod_deliveryTime.setTypeface(textFont);
        productTitle.setText(productName);
        mCustomPagerAdapter = new CustomPagerAdapter(getContext());





        viewPager.setAdapter(mCustomPagerAdapter);
        mRecyclerView.setAdapter(mAdapter);

//     prod_price.setTypeface(innerFont);
  //      prod_discountPrice.setTypeface(innerFont);
  //      prod_discountPrice.setPaintFlags(prod_discountPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

  //   prod_buyNow.setTypeface(innerFont);
//        System.out.println("COOL" + productModel.getName());
//productTitle.setText(productModel.getName());
        //prod_price.setText(productModel.getPriceNew());
        //prod_discountPrice.setText(productModel.getDiscPercent());

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new APIClient(getActivity(), getActivity(),  new GetProductDetailsCallback()).productDetailsAPICall(productId);
        } else {
            /*Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();*/

            Snackbar snackbar =Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }



countButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

            Intent i = new Intent(getActivity(), ShippingAddressActivity.class);
            startActivity(i);
    }
});



        /*save=(Button)mView.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ShippingAddressActivity.class);
                startActivity(i);
            }
        });*/

share.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //share();
        Share(getShareApplication(),"Hello Text Share");
    }
});
        return mView;
    }

    private void share() {



        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Here is the share content body";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    private Bitmap base64ToBitmap(String imageString) {
        byte[] imageAsBytes = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

    }
    private List<String> getShareApplication(){
        List<String> mList=new ArrayList<String>();
        mList.add("com.facebook.katana");
        mList.add("com.twitter.android");
        //mList.add("com.twitter.applib.PostActivity");
        mList.add("com.google.android.gm");
        mList.add("com.linkedin.android");
        return mList;

    }




    private void Share(List<String> PackageName,String Text) {
        try
        {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
           // List<ResolveInfo> resInfo = getActivity().getPackageManager().queryIntentActivities(share, 0);
            PackageManager packManager = getActivity().getPackageManager();
            List<ResolveInfo> resInfo = packManager.queryIntentActivities(share,  PackageManager.MATCH_DEFAULT_ONLY);
            if (!resInfo.isEmpty()){
                for (ResolveInfo info : resInfo) {
                    Intent targetedShare = new Intent(android.content.Intent.ACTION_SEND);
                    targetedShare.setType("text/plain"); // put here your mime type
                    if (PackageName.contains(info.activityInfo.packageName.toLowerCase())) {
                        targetedShare.putExtra(Intent.EXTRA_TEXT,Text);
                        targetedShare.setPackage(info.activityInfo.packageName.toLowerCase());
                        targetedShare.setClassName(
                                info.activityInfo.packageName,
                                info.activityInfo.name );
                        targetedShareIntents.add(targetedShare);
                    }
                }
                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
                startActivity(chooserIntent);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        /*Intent tweetIntent = new Intent(Intent.ACTION_SEND);
        tweetIntent.putExtra(Intent.EXTRA_TEXT, "This is a Test.");
        tweetIntent.setType("text/plain");

        PackageManager packManager = getActivity().getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent,  PackageManager.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for(ResolveInfo resolveInfo: resolvedInfoList){
            if(resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")){
                tweetIntent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name );
                resolved = true;
                break;
            }
        }
        if(resolved){
            startActivity(tweetIntent);
        }else{
            Toast.makeText(getActivity(), "Twitter app isn't found", Toast.LENGTH_LONG).show();
        }*/
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
            return mResources.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = mLayoutInflater.inflate(R.layout.fragment_imageslider, container, false);

            final ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            final ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.load_progress_bar);
            //imageView.setImageResource(mResources[position]);
//imageView.setImageBitmap(base64ToBitmap(image));
            Log.v("overview", "imageurl: " + image);
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();

//            if(image!=null && !image.equalsIgnoreCase("")){
//                imageView.setImageUrl(image, imageLoader);
//            }
//            else{
//                imageView.setImageDrawable(getActivity().getResources().getDrawable((R.drawable.default_img)));
//            }

            imageLoader.get(image, new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.default_img));
                    progressBar.setVisibility(View.GONE);
                }
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        // load image into imageview
                        imageView.setImageBitmap(response.getBitmap());
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });

            container.addView(itemView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(baseImage!=null){
                        Intent i=new Intent(getContext(), FragmentFullScreenImage.class);
                        i.putExtra("size",mResources.size());
                        i.putExtra("position", position);
                        i.putExtra("image",image);
                        i.putExtra("baseImage",mResources);
                        startActivity(i);
                    }

                }
            });

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }

    public class GetProductDetailsCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                mResources.clear();
                JSONObject mMembersJSON;
                mMembersJSON = new JSONObject(response);
                System.out.println(mMembersJSON.getString("name"));
                productTitle.setText(mMembersJSON.getString("name"));
                String ratingsCount=mMembersJSON.getJSONObject("rating").getString("average");
                String count=mMembersJSON.getJSONObject("rating").getString("sum");
//                prodNewPrice.setText(Integer.toString(mMembersJSON.getInt("new_price")) + " " + "AED");
//                prodOldPrice.setText(Integer.toString(mMembersJSON.getInt("original_price")) + " " + "AED");
//                System.out.println("COOLGBDJH" + productDetails.getName());
//                String prodDesc=mMembersJSON.getString("description");
//                productOverview.setOverViewTitle(prodDesc);
                image=mMembersJSON.getString("image");
                JSONArray imagesStringData=mMembersJSON.getJSONArray("images");
                for(int i=0;i<imagesStringData.length();i++)
                {
                    //JSONObject data=imagesStringData.getJSONObject(i);
                    String jsonData=imagesStringData.get(i).toString();
imageString.add(jsonData);
                    System.out.println(imageString);

                }

                //mResources=
                ratingMain.setRating(Float.parseFloat(ratingsCount));
                product_reviewCount.setText("(" + count + ")");
                countText.setText(String.valueOf(imageString.size()));
                baseImage=mMembersJSON.getString("image");
                //mResources.clear();
                mResources=imageString;
                //getRatings();
                getRatings();



                System.out.println("dcjdfhjhxv"+mResources.toString());
//                if(getApplicationContext()!=null){
//                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
//                    progressBar.startAnimation(animation);
//                }
//                progressBar.setVisibility(View.GONE);
                // initializeData();
            } catch (JSONException e) {
                e.printStackTrace();
                /*Snackbar.make(getActivity().findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();*/

                Snackbar snackbar =Snackbar.make(getActivity().findViewById(android.R.id.content), "Data could not be loaded", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED);

                View snackbarView = snackbar.getView();

                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
            }
        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
//            progressBar.setVisibility(View.VISIBLE);
        }
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


    @Override
    public void onResume() {
        super.onResume();
        /*mCustomPagerAdapter.notifyDataSetChanged();
        getRatings();*/
    }
}
