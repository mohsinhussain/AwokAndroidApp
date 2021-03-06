package com.awok.moshin.awok.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.Activities.ProductDetailsActivity;
import com.awok.moshin.awok.Adapters.ProductOverViewRatingAdapter;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.ProductDetailsModel;
import com.awok.moshin.awok.Models.ProductRatingModel;
import com.awok.moshin.awok.Models.productOverviewRating;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shon on 9/10/2015.
 */
public class ProductOverViewFragment extends Fragment {
private RatingBar ratingMain;
    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private ScrollView scroll;
    RelativeLayout middle;
    private int lastTopValue = 0;
    //private RecyclerView.LayoutManager mLayoutManager;

    ProductDetailsModel productModel = new ProductDetailsModel();
    List<productOverviewRating> ratingData=new ArrayList<productOverviewRating>();



private ImageView countButton,share;

    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager viewPager;
    ArrayList<String> mResources = new ArrayList<String>();
    ArrayList<String> imageString = new ArrayList<String>();



private TextView productTitle,product_reviewCount,prod_warranty,prod_color,prod_color_default,prod_shipping,prod_shippingCost,prod_delivery,prod_deliveryTime,prod_reviews,quickDeliveryTxt,
        prod_price,prod_discountPrice,countText,estArrival,estShipping,reviewView;

 private Button prod_buyNow,save;
    private RatingBar prodRatingBar,prod_reviewRating;
    String image, baseImage,productName, productId,estimatedDays,estimatedPrice,condition;
    List<ProductRatingModel> rating;
    public ProductOverViewFragment(){

    }
    public ProductOverViewFragment(String productId,String productName, String imageData,List<ProductRatingModel> rating,String estimatedDays,String estimatedPrice,String condition) {
        this.productName=productName;
        this.image=imageData;

        this.productId=productId;
        this.rating=rating;
        this.estimatedDays=estimatedDays;
        this.estimatedPrice=estimatedPrice;
        this.condition=condition;

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
        /*mCustomPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(mCustomPagerAdapter);*/
        mCustomPagerAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
       // viewPager.invalidate();
      //  viewPager.notify();

//        viewPager.notify();
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View mView = inflater.inflate(R.layout.product_body_content, container, false);
/************************************/
      /*mResources.add(image);
        ratingMain=(RatingBar)mView.findViewById(R.id.main_prodRatingBar);
        viewPager = (ViewPager) mView.findViewById(R.id.imageSlider);
        countButton=(ImageView)mView.findViewById(R.id.imageView);
        productTitle=(TextView)mView.findViewById(R.id.productTitle);
        reviewView=(TextView)mView.findViewById(R.id.reviewView);
        product_reviewCount=(TextView)mView.findViewById(R.id.product_reviewCount);
  /////////////      scroll=(ScrollView)mView.findViewById(R.id.nestedScroll);
        countText=(TextView)mView.findViewById(R.id.countText);
      //  countText=(TextView)mView.findViewById(R.id.countText);
        estArrival=(TextView)mView.findViewById(R.id.estimatedArrival);*/


/********************/
      /*  reviewView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProductDetailsActivity)getActivity()).goToView();
            }
        });*/


   /*     middle=(RelativeLayout)mView.findViewById(R.id.layMiddle);

                estShipping=(TextView)mView.findViewById(R.id.estimatedShipping);

        estArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProductDetailsActivity)getActivity()).goTo();
                System.out.println("hjfgjhfgk");
            }
        });

        estShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ProductDetailsActivity)getActivity()).goTo();
                System.out.println("hjfgjhfgk");
            }
        });*/

        //prod_warranty=(TextView)mView.findViewById(R.id.prod_warranty);
/*        share=(ImageView)mView.findViewById(R.id.share);
        prod_color_default=(TextView)mView.findViewById(R.id.prod_color_default);
        prod_color=(TextView)mView.findViewById(R.id.prod_color);
        prod_shipping=(TextView)mView.findViewById(R.id.prod_shipping);
        prod_shippingCost=(TextView)mView.findViewById(R.id.prod_shippingCost);
        prod_delivery=(TextView)mView.findViewById(R.id.prod_delivery);
        prod_deliveryTime=(TextView)mView.findViewById(R.id.prod_deliveryTime);*/
        //prod_reviews=(TextView)mView.findViewById(R.id.prod_reviews);
        //quickDeliveryTxt=(TextView)mView.findViewById(R.id.quickDeliveryTxt);
        //prod_price=(TextView)mView.findViewById(R.id.prod_price);
        //prod_discountPrice=(TextView)mView.findViewById(R.id.prod_discountPrice);
   //  prod_buyNow=(Button)mView.findViewById(R.id.prod_buyNow);

    /*    viewPager.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_MOVE && scroll != null) {
                    scroll.requestDisallowInterceptTouchEvent(true);

                }
                return false;
            }
        });*/
       // share=(ImageView)mView.findViewById(R.id.share);

       mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerViewRating);

    //   mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setNestedScrollingEnabled(false);
       // int viewHeight = 100 * (10);
      //  mRecyclerView.getLayoutParams().height = viewHeight;

        // use a linear layout manager
        //mLayoutManager = new LinearLayoutManager(getActivity());
        //LinearLayoutBridge mLayoutManager=new LinearLayoutBridge(getActivity(),LinearLayoutManager.VERTICAL,false);
 //////////////////////////////////       MyLinearLayoutManager mLayoutManager=new MyLinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setNestedScrollingEnabled(true);
        // mRecyclerView.hasNestedScrollingParent();
        mRecyclerView.setHasFixedSize(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
  /////////////     mRecyclerView.setLayoutManager(mLayoutManager);

      //  mAdapter = new ProductOverViewRatingAdapter(getActivity(), ratingData);
        mAdapter = new ProductOverViewRatingAdapter(getActivity(), rating,image);

             //   scroll.fullScroll(NestedScrollView.FOCUS_UP);




                /*    prodRatingBar=(RatingBar)mView.findViewById(R.id.main_prodRatingBar);
       // prod_reviewRating=(RatingBar)mView.findViewById(R.id.prod_reviewRating);
        LayerDrawable mainRatingColor = (LayerDrawable) prodRatingBar.getProgressDrawable();
        mainRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        mainRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        mainRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);*/


//        LayerDrawable reviewRatingColor = (LayerDrawable) prod_reviewRating.getProgressDrawable();
//        reviewRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
//        reviewRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
//        reviewRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);

//        Typeface mainProdFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf");
//        productTitle.setTypeface(mainProdFont);


    //    Typeface innerFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf");
    //    prod_color.setTypeface(innerFont);
    //    prod_shipping.setTypeface(innerFont);
    //    prod_delivery.setTypeface(innerFont);
//        prod_reviews.setTypeface(innerFont);

     //   Typeface textFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf");
      //  prod_color_default.setTypeface(textFont);
//        prod_colorSecondary.setTypeface(textFont);
   //     prod_shippingCost.setTypeface(textFont);
   //     prod_deliveryTime.setTypeface(textFont);
        /*productTitle.setText(productName);
        mCustomPagerAdapter = new CustomPagerAdapter(getContext());*/

//prod_deliveryTime.setText(estimatedDays);


        //prod_shippingCost.setText(estimatedPrice);

        /*viewPager.setAdapter(mCustomPagerAdapter);*/
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

//     prod_price.setTypeface(innerFont);
  //      prod_discountPrice.setTypeface(innerFont);
  //      prod_discountPrice.setPaintFlags(prod_discountPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

  //   prod_buyNow.setTypeface(innerFont);
//        System.out.println("COOL" + productModel.getName());
//productTitle.setText(productModel.getName());
        //prod_price.setText(productModel.getPriceNew());
        //prod_discountPrice.setText(productModel.getDiscPercent());

/*
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new APIClient(getActivity(), getActivity(),  new GetProductDetailsCallback()).productDetailsAPICall(productId);
        } else {


            Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
*/




//
//countButton.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//
//            Intent i = new Intent(getActivity(), ShippingAddressActivity.class);
//            startActivity(i);
//    }
//});



        /*save=(Button)mView.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ShippingAddressActivity.class);
                startActivity(i);
            }
        });*/

    /*share.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //share();
            Share(getShareApplication(),"Hello Text Share");
        }
    });*/
            return mView;
        }

    private void share() {



        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Here is the share content body";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
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

    /*@Override
    public void onScroll(RelativeLayout view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Rect rect = new Rect();
        viewPager.getLocalVisibleRect(rect);
        if (lastTopValue != rect.top) {
            lastTopValue = rect.top;
            viewPager.setY((float) (rect.top / 2.0));
        }
    }*/


    private void Share(List<String> PackageName,String Text) {
        try
        {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
           // List<ResolveInfo> resInfo = getActivity().getPackageManager().queryIntentActivities(share, 0);
            PackageManager packManager = getActivity().getPackageManager();
            List<ResolveInfo> resInfo = packManager.queryIntentActivities(share,  PackageManager.MATCH_DEFAULT_ONLY);
            if (!resInfo.isEmpty()){
                for (ResolveInfo info : resInfo) {
                    Intent targetedShare = new Intent(Intent.ACTION_SEND);
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
   ////////         final ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.load_progress_bar);
            final ImageView progressBar = (ImageView) itemView.findViewById(R.id.load_progress_bar);
            //imageView.setImageResource(mResources[position]);
//imageView.setImageBitmap(base64ToBitmap(image));
            Log.v("overview", "imageurl: " + image);
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
         //   progressBar.setVisibility(View.VISIBLE);
//            if(image!=null && !image.equalsIgnoreCase("")){
//                imageView.setImageUrl(image, imageLoader);
//            }
//            else{
//                imageView.setImageDrawable(getActivity().getResources().getDrawable((R.drawable.default_img)));
//            }
if(position==0) {

    imageLoader.get("http://"+image, new ImageLoader.ImageListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            progressBar.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cart_icon));
            //progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
            if (response.getBitmap() != null) {


                progressBar.setImageBitmap(response.getBitmap());
                //  progressBar.setVisibility(View.GONE);

            }
        }

    });

}
            imageLoader.get("http://"+mResources.get(position), new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cart_icon));
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {

 System.out.println("POSITION"+position);                       // load image into imageview
/*if(position==0)
{
    imageView.invalidate();

    imageView.setImageBitmap(response.getBitmap());

 //   imageView.setBackground(getResources().getDrawable(R.drawable.flag_id));
   // System.out.println("POSITION" + response.toString());
    progressBar.setVisibility(View.GONE);
}
                        else {*/
    imageView.setImageBitmap(response.getBitmap());
    progressBar.setVisibility(View.GONE);
}
                   // }
                }

            });
/*if(position==mResources.size())
{
    mCustomPagerAdapter.notifyDataSetChanged();
}*/

            container.addView(itemView);


/*if(mResources.get(position).equals(mResources.size()))
{
    viewPager.invalidate();
}*/



            return itemView;
        }
      /*  @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }*/

      public int getItemPosition(Object object) {

          return POSITION_NONE;
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
                JSONObject dataObj = mMembersJSON.getJSONObject("OUTPUT");
//                System.out.println(mMembersJSON.getString("name"));
                productTitle.setText(dataObj.getJSONObject("DATA").getString("NAME"));
                String ratingsCount=dataObj.getJSONObject("DATA").getJSONObject("RATING").getString("RATING");
                String count=dataObj.getJSONObject("DATA").getJSONObject("RATING").getString("COUNT");
//                prodNewPrice.setText(Integer.toString(mMembersJSON.getInt("new_price")) + " " + "AED");
//                prodOldPrice.setText(Integer.toString(mMembersJSON.getInt("original_price")) + " " + "AED");
//                System.out.println("COOLGBDJH" + productDetails.getName());
//                String prodDesc=mMembersJSON.getString("description");
//                productOverview.setOverViewTitle(prodDesc);
         /////////////////////////////////////////////////////////////////////       image=dataObj.getString("image");
                prod_shippingCost.setText("AED "+(dataObj.getJSONObject("DATA").getJSONObject("SHIPPING").getString("PRICE")));
                prod_deliveryTime.setText((dataObj.getJSONObject("DATA").getJSONObject("SHIPPING").getString("PERIOD_FROM")) + " - " + (dataObj.getJSONObject("DATA").getJSONObject("SHIPPING").getString("PERIOD_TO")) + " days");
                JSONArray imagesStringData=dataObj.getJSONObject("DATA").getJSONArray("IMAGES");
                for(int i=0;i<imagesStringData.length();i++)
                {
                    //JSONObject data=imagesStringData.getJSONObject(i);
                    String jsonData=imagesStringData.get(i).toString();
                    imageString.add(jsonData);
                    System.out.println(imageString);
                    baseImage=imagesStringData.get(i).toString();

                }

               /* int colorLength=dataObj.getJSONObject("DATA").getJSONObject("VARIANTS").getJSONArray("COLORS").length();
                JSONArray colorArray=dataObj.getJSONObject("DATA").getJSONObject("VARIANTS").getJSONArray("COLORS");
                ArrayList<String> colorArrayList=new ArrayList<>();
                for(int j=0;j<colorLength;j++)
                {
                    JSONObject colorData=colorArray.getJSONObject(j);

                    colorArrayList.add(colorData.getString("NAME"));
                    System.out.println(colorArrayList + " color");

                }
//prod_color_default.setText(colorArrayList.toString().replace("[", "").replace("]", ""));

                prod_color_default.setText(colorArrayList.toString().replace("[", "").replace("]", ""));*/
                int colorLength=dataObj.getJSONObject("DATA").getJSONArray("SPECIFICATIONS").length();
                JSONArray colorArray=dataObj.getJSONObject("DATA").getJSONArray("SPECIFICATIONS");
                ArrayList<String> colorArrayList=new ArrayList<>();
                for(int j=0;j<colorLength;j++)
                {
                    JSONObject colorData=colorArray.getJSONObject(j);
                if(colorData.getString("CODE").equals("COLOR"))
                {
                  //  prod_color_default.setText(colorData.getString("VALUE").toString());
                }

                }






                //mResources=

                    ratingMain.setRating(Float.parseFloat(ratingsCount));


                    product_reviewCount.setText("(" + count + ")");

                countText.setText(String.valueOf(imageString.size()));
           ////////////////////////     baseImage=dataObj.getString("image");
                //mResources.clear();
               // mResources.clear();

                mResources=imageString;
         //       mCustomPagerAdapter.notifyDataSetChanged();
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

                Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "Data could not be loaded", Snackbar.LENGTH_LONG)
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
