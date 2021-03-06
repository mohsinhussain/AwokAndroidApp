package com.awok.moshin.awok.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.Adapters.ProductRatingPageAdapter;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.ProductRatingPageModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ReviewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int firstVisibleItem, visibleItemCount, totalItemCount, lastVisibleItem;
    // TODO: Rename and change types of parameters
    private String productName="";
    private String boughtBy="";
    private String savedBy="";
    private String ratingCount="";
    private String image="";
    private String ratingtxt="0";
    private TextView productNameView,ratingCounttxt,boughtByTxt,savedByTxt;
    private ImageView imgMain;
    private ProgressBar progressBar;
    private String mParam2;
    private RatingBar prod_reviewRating;
    private int previousTotal = 0;
    private String productId="";
    List<ProductRatingPageModel> rating=new ArrayList<ProductRatingPageModel>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayout reviewsLayout;
    private int totalPages=2;
    private boolean loading = true;

    private int current_page = 1;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private List<ProductRatingPageModel> prodRating = new ArrayList<ProductRatingPageModel>();
private RelativeLayout nestedScroll;
    private int visibleThreshold = 5;
    View views;

    public ReviewsFragment() {
        // Required empty public constructor
    }

 /*   public ReviewsFragment(String productName,String image,String ratingValue,String ratingCountValue) {
        this.productName=productName;
        this.image=image;
        rating=ratingValue;
        ratingCount=ratingCountValue;
    }*/

/*    public ReviewsFragment(List<ProductRatingModel> rating,String productName,String image,String ratingValue,String ratingCountValue,String boughtBy,String savedBy) {
        this.productName=productName;
        this.image=image;
        this.ratingtxt=ratingValue;
        ratingCount=ratingCountValue;
        this.rating=rating;
        this.boughtBy=boughtBy;
        this.savedBy=savedBy;

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_reviews, container, false);
        nestedScroll=(RelativeLayout)mView.findViewById(R.id.nestedScroll);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.overViewRecyclerView);
        views=(View)mView.findViewById(R.id.view);
//
//            // getSupportActionBar().setIcon(R.drawable.ic_launcher);
//
//            // getSupportActionBar().setTitle("Android Versions");
//
//            // use this setting to improve performance if you know that changes
//            // in content do not change the layout size of the RecyclerView
        /*mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);*/
   /////////     mRecyclerView.setNestedScrollingEnabled(false);
        // mRecyclerView.hasNestedScrollingParent();
  //////////      mRecyclerView.setHasFixedSize(false);
        mLayoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //mLayoutManager=new MyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        //mRecyclerView.setLayoutManager(new com.awok.moshin.awok.Util.LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        //mLayoutManager = new LinearLayoutManager(getActivity());
    //    mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //mLayoutManager = new MyLinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
       // mRecyclerView.setLayoutManager(mLayoutManager);
//
//            // use a linear layout manager
       /* mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);*/
//
        mAdapter = new ProductRatingPageAdapter(getActivity(),rating);
        mRecyclerView.setAdapter(mAdapter);

        reviewsLayout = (LinearLayout) mView.findViewById(R.id.reviews);
        if(rating.size()>0){
            reviewsLayout.setVisibility(View.GONE);
        }





/*views.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        onLoadMore();
    }
});*/

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                    StaggeredGridLayoutManager layoutManager = ((StaggeredGridLayoutManager) mRecyclerView.getLayoutManager());
                int visibleItemCount, totalItemCount, firstVisibleItem;
//
                visibleItemCount = mRecyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();


                firstVisibleItem = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + 5)) {
                    // End has been reached

                    // Do something
                   /*     for (int j=0;j<20;j++)
                        {
                            Model mm=new Model();


                            mm.setText("I am "+j);
                            mm.setColor("Color is" + j);


                            model.add(mm);

                        }*/
                    System.out.println("TOTAL" + totalPages);
                    current_page++;
                    if(totalPages>=current_page) {
                        onLoadMore();
//                        mRecAdapter.notifyDataSetChanged();


                        loading = true;
                    }
                }
            }
        });


/*nestedScroll.setOnScrollChangeListener(new OnScrollChangeListener() {

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        Rect scrollBounds = new Rect();
        nestedScroll.getHitRect(scrollBounds);
        if (views.getLocalVisibleRect(scrollBounds)) {
            // Any portion of the imageView, even a single pixel, is within the visible window
            System.out.println("?<D?>KDS?>D" );
        } else {
            // NONE of the imageView is within the visible window
            System.out.println("NOPE"  );
        }
    }
});*/









/*
        nestedScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });*/







       /* mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                System.out.println("COOL LISTENE");
                Rect scrollBounds = new Rect();
                nestedScroll.getHitRect(scrollBounds);
                if (views.getLocalVisibleRect(scrollBounds)) {
                    // Any portion of the imageView, even a single pixel, is within the visible window
                    System.out.println("?<D?>KDS?>D" );
                } else {
                    // NONE of the imageView is within the visible window
                    System.out.println("NOPE"  );
                }
            }
        });*/




/*nestedScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        Rect scrollBounds = new Rect();
        nestedScroll.getHitRect(scrollBounds);
        if (views.getLocalVisibleRect(scrollBounds)) {
            // Any portion of the imageView, even a single pixel, is within the visible window
            System.out.println("?<D?>KDS?>D" );
        } else {
            // NONE of the imageView is within the visible window
            System.out.println("NOPE"  );
        }

    }
});*/

    /*    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Rect scrollBounds = new Rect();
                nestedScroll.getHitRect(scrollBounds);
                if (views.getLocalVisibleRect(scrollBounds)) {
                    // Any portion of the imageView, even a single pixel, is within the visible window
                    System.out.println("?<D?>KDS?>Dvhnbnvbnbvnvb" );
                } else {
                    // NONE of the imageView is within the visible window
                    System.out.println("NOPE"  );
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerViewTxt, int dx, int dy) {
                super.onScrolled(recyclerViewTxt, dx, dy);
                LinearLayoutManager layoutManager = ((LinearLayoutManager)mRecyclerView.getLayoutManager());
                visibleItemCount = recyclerViewTxt.getChildCount();
                //totalItemCount = layoutManager.getItemCount();
                totalItemCount=recyclerViewTxt.getLayoutManager().getItemCount();
                //firstVisibleItem =layoutManager.findFirstVisibleItemPosition();
                firstVisibleItem=((LinearLayoutManager) recyclerViewTxt.getLayoutManager()).findFirstCompletelyVisibleItemPosition();

                //firstVisibleItem=layoutManager.findFirstCompletelyVisibleItemPosition(null);
                System.out.println("dx"+dx);
                System.out.println("dy"+dy);
                System.out.println("recyclerViewTxt"+recyclerViewTxt.getChildCount());
                System.out.println("recyclerViewTxt"+recyclerViewTxt.getChildCount());
                System.out.println("recyclerViewTxt"+recyclerViewTxt.getChildCount());
                System.out.println("recyclerViewTxt"+recyclerViewTxt.getChildCount());
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                        System.out.println("totalItemCount MAIN"+totalItemCount);
                        System.out.println("previousTotal MAIN"+previousTotal);
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached
                    System.out.println("COUNT"+current_page);
                    System.out.println("totalItemCount - visibleItemCount" + (totalItemCount - visibleItemCount));
                    System.out.println("firstVisibleItem + visibleThreshold" + (firstVisibleItem + visibleThreshold));
                    System.out.println("totalItemCount"+totalItemCount);
                    System.out.println("visibleItemCount" + visibleItemCount);
                    System.out.println("firstVisibleItem" + firstVisibleItem);
                    System.out.println("visibleThreshold" + visibleThreshold);

                    // Do something
                    current_page++;

                    onLoadMore();

                    loading = true;
                }




                Rect scrollBounds = new Rect();
                nestedScroll.getHitRect(scrollBounds);
                if (views.getLocalVisibleRect(scrollBounds)) {
                    // Any portion of the imageView, even a single pixel, is within the visible window
                    System.out.println("?<D?>KDS?>D" );
                } else {
                    // NONE of the imageView is within the visible window
                    System.out.println("NOPE"  );
                }


            }
        });*/



       /* mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = ((LinearLayoutManager)mRecyclerView.getLayoutManager());
                visibleItemCount = mRecyclerView.getChildCount();
                //totalItemCount = layoutManager.getItemCount();
                totalItemCount=mRecyclerView.getLayoutManager().getItemCount();
                //firstVisibleItem =layoutManager.findFirstVisibleItemPosition();
                firstVisibleItem=((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();

                //firstVisibleItem=layoutManager.findFirstCompletelyVisibleItemPosition(null);
                System.out.println("totalItemCount - visibleItemCount" + (totalItemCount - visibleItemCount));
                System.out.println("firstVisibleItem + visibleThreshold" + (firstVisibleItem + visibleThreshold));
                System.out.println("totalItemCount" + totalItemCount);
                System.out.println("visibleItemCount" + visibleItemCount);
                System.out.println("firstVisibleItem" + firstVisibleItem);
                System.out.println("visibleThreshold" + visibleThreshold);
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                        System.out.println("totalItemCount MAIN"+totalItemCount);
                        System.out.println("previousTotal MAIN"+previousTotal);
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached
System.out.println("COUNT"+current_page);
                    *//*System.out.println("totalItemCount - visibleItemCount" + (totalItemCount - visibleItemCount));
                    System.out.println("firstVisibleItem + visibleThreshold" + (firstVisibleItem + visibleThreshold));
                    System.out.println("totalItemCount"+totalItemCount);
                    System.out.println("visibleItemCount" + visibleItemCount);
                    System.out.println("firstVisibleItem" + firstVisibleItem);
                    System.out.println("visibleThreshold" + visibleThreshold);*//*

                    // Do something
                    current_page++;

                    onLoadMore();

                    loading = true;
                }
            }
        });*/




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

        productNameView=(TextView)mView.findViewById(R.id.productTitle);
        //RatingBar ratingBar=(RatingBar)mView.findViewById(R.id.main_prodRatingBar);
        ratingCounttxt=(TextView)mView.findViewById(R.id.product_reviewCount);
        boughtByTxt=(TextView)mView.findViewById(R.id.textView);
        savedByTxt=(TextView)mView.findViewById(R.id.textView2);
        imgMain=(ImageView)mView.findViewById(R.id.mainImg);
        progressBar = (ProgressBar) mView.findViewById(R.id.load_progress_bar);
        prod_reviewRating=(RatingBar)mView.findViewById(R.id.main_prodRatingBar);



        /*TextView productNameView=(TextView)mView.findViewById(R.id.productTitle);
        //RatingBar ratingBar=(RatingBar)mView.findViewById(R.id.main_prodRatingBar);
        TextView ratingCounttxt=(TextView)mView.findViewById(R.id.product_reviewCount);
        TextView boughtByTxt=(TextView)mView.findViewById(R.id.textView);
        TextView savedByTxt=(TextView)mView.findViewById(R.id.textView2);
        final ImageView imgMain=(ImageView)mView.findViewById(R.id.mainImg);
        final ProgressBar progressBar = (ProgressBar) mView.findViewById(R.id.load_progress_bar);*/
        if(boughtBy.equals("")||boughtBy.equals("0"))
        {
            boughtByTxt.setVisibility(View.GONE);
        }
        else
        {

            boughtByTxt.setText("Bought By: "+boughtBy+" people");


        }

       /* if(savedBy.equals("")||savedBy.equals("0"))
        {
            savedByTxt.setVisibility(View.GONE);
        }
        else
        {

            savedByTxt.setText("Saved By: "+savedBy+" people");


        }*/


        productNameView.setText(productName);
        System.out.println(image + "image");
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        if(image.equals(""))
        {
            imgMain.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cart_icon));
        }
        else {
            imageLoader.get("http://"+image, new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    imgMain.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cart_icon));
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
        }



        prod_reviewRating=(RatingBar)mView.findViewById(R.id.main_prodRatingBar);
        prod_reviewRating.setRating(Float.parseFloat(ratingtxt));
        if(ratingCount.equals(""))
        {
            ratingCounttxt.setVisibility(View.GONE);
        }
        else
        {


            ratingCounttxt.setText("(" + ratingCount + ")");

        }
        LayerDrawable mainRatingColor = (LayerDrawable) prod_reviewRating.getProgressDrawable();
        mainRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        mainRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        mainRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);


/*mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
    @Override
    public void onLoadMore(int currentPage) {

        System.out.println("CURRENT" + currentPage);
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new APIClient(getActivity(), getActivity(), new GetCartCallback()).productReviewCommentsCallBack(productId, current_page);

        } else {

            *//*Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();*//*


            Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }
});*/


        return mView;
    }

    private void onLoadMore() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if(!rating.get(rating.size()-1).isLoader()){
            rating.add(new ProductRatingPageModel(true, "Loading More..."));

            mAdapter.notifyItemInserted((rating.size() - 1));
        }
        else{
            //rating.remove(new ProductRatingPageModel(true, "Loading More..."));
            //mAdapter.notifyItemRemoved((rating.size() - 1));
        }
            new APIClient(getActivity(), getActivity(),  new GetCartCallback()).productReviewCommentsCallBack(productId,current_page);

        } else {

            /*Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();*/


            Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
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



    public void call(List<ProductRatingPageModel> rating,String productName,String image,String ratingValue,String ratingCountValue,String boughtBy,String productId) {
        this.productName = productName;
        this.image = image;
        System.out.println(image + " review Frag");
        this.ratingtxt = ratingValue;
        this.ratingCount = ratingCountValue;
        this.rating = rating;
        this.boughtBy = boughtBy;
        //this.savedBy = savedBy;
        this.productId = productId;


        if (boughtByTxt != null ||imgMain != null || ratingCounttxt != null) {

            if (this.boughtBy.equals("") || this.boughtBy.equals("0")) {
                boughtByTxt.setVisibility(View.GONE);
            } else {

                boughtByTxt.setText("Bought By: " + this.boughtBy + " people");
                boughtByTxt.setVisibility(View.VISIBLE);


            }

           /* if (this.savedBy.equals("") || this.savedBy.equals("0")) {
                savedByTxt.setVisibility(View.GONE);
            } else {

                savedByTxt.setText("Saved By: " + this.savedBy + " people");
                savedByTxt.setVisibility(View.VISIBLE);


            }*/


            productNameView.setText(this.productName);
            System.out.println(image + "image");
        /*ImageLoader imageLoader = AppController.getInstance().getImageLoader();*/
            if (this.image.equals("")) {
                imgMain.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cart_icon));
            } else {
                imageLoader.get(this.image, new ImageLoader.ImageListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        imgMain.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cart_icon));
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
            }


            prod_reviewRating.setRating(Float.parseFloat(this.ratingtxt));
            if (this.ratingCount.equals("")) {
                ratingCounttxt.setVisibility(View.GONE);
            } else {


                ratingCounttxt.setText("(" + this.ratingCount + ")");
                ratingCounttxt.setVisibility(View.VISIBLE);

            }
            LayerDrawable mainRatingColor = (LayerDrawable) prod_reviewRating.getProgressDrawable();
            mainRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
            mainRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
            mainRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);


            System.out.println("MOHSIN/SHON HAS DONE IT");

           // mRecyclerView.setAdapter(mAdapter);


            if(this.rating.size()>0){
                reviewsLayout.setVisibility(View.GONE);
            }
            mAdapter = new ProductRatingPageAdapter(getActivity(),rating);
            mRecyclerView.setAdapter(mAdapter);
//            mAdapter.notifyDataSetChanged();
        }

    }



    /*public class EndlessScrollListener extends RecyclerView.OnScrollListener {

        private int visibleThreshold = 5;
        private int currentPage = 0;
        private int previousTotal = 0;
        private boolean loading = true;

        public EndlessScrollListener() {
        }
        public EndlessScrollListener(int visibleThreshold) {
            this.visibleThreshold = visibleThreshold;
        }

        @Override
        public void onScroll(RecyclerView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                    currentPage++;
                }
            }


            if (!loading && (totalItemCount - visibleItemCount)<=(firstVisibleItem + visibleThreshold)) {
                new LoadGigsTask().execute();
                currentPage=currentPage + 1;
                loading = true;
            }
        }

        *//*@Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }*//*
    }*/


    public class GetCartCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {





                rating.remove((rating.size()-1));

                JSONObject jsonObjectData;
                jsonObjectData = new JSONObject(response);


             ///////////////////////   if (jsonObjectData.getString("errors").equals("true")) {


                   /* Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "No More Comments", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED);

                    View snackbarView = snackbar.getView();

                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();*/
          ///////////      } else {

                    for (int i = 0; i < jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PRODUCT_REVIEWS").getJSONArray("MESSAGES").length(); i++) {
                        ProductRatingPageModel prodRatingData=new ProductRatingPageModel();

                        JSONObject data = jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONObject("PRODUCT_REVIEWS").getJSONArray("MESSAGES").getJSONObject(i);

                        prodRatingData.setContent(data.getString("MESSAGE"));
                       // prodRatingData.setRate(data.getString("rate"));
                        prodRatingData.setUsername(data.getString("NAME"));





                       /* if(data.has("days")){
                            prodRatingData.setDays(data.getString("days"));
                        }
                        else{
                            prodRatingData.setDays(data.getString("created_at"));
                        }*/
prodRatingData.setDays(data.getString("DATE_UPDATED"));

                        rating.add(prodRatingData);

                    }
                    //     mRecyclerView.setAdapter(mAdapter);
              ////////  }
                totalPages=(int)Math.ceil((jsonObjectData.getJSONObject("OUTPUT").getJSONObject("NAVIGATION").getDouble("TOTAL") / jsonObjectData.getJSONObject("OUTPUT").getJSONObject("NAVIGATION").getDouble("COUNT")));
                mAdapter.notifyDataSetChanged();
               // current_page++;

                }catch(JSONException e){
                    e.printStackTrace();
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                            .setActionTextColor(Color.RED)
                            .show();

                }

        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
//            if(!mSwipeRefreshLayout.isRefreshing()){

//            }

        }
    }

}
