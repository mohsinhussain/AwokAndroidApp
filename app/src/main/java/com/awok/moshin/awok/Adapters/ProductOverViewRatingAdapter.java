package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.Activities.FragmentFullScreenImage;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.ProductRatingModel;
import com.awok.moshin.awok.Models.ProductSpecification;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.facebook.FacebookSdk;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shon on 10/10/2015.
 */
public class ProductOverViewRatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  ////////////  private List<productOverviewRating> OverViewList = new ArrayList<productOverviewRating>();
  private List<ProductRatingModel> OverViewList = new ArrayList<ProductRatingModel>();
    private Activity activity_main;
    View customView;
    private  final int TYPE_FOOTER = 2;

    ArrayList<String> mResources = new ArrayList<String>();
    ArrayList<String> imageString = new ArrayList<String>();
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private List<ProductSpecification> specList = new ArrayList<ProductSpecification>();
    private String ratingCount="0";
    private ProductSpecificationAdapter bundleAdapter;
    Dialog bundleDialog;
    private String reviewCount="";
    private String countText="";
    CustomPagerAdapter mCustomPagerAdapter;
    private ArrayAdapter<CharSequence> adapter;
private String image,delivery_time,shipping_cost;
Activity activity;
    String type="";
    Context mContext;
    String pName="";
    String pId="";
    ArrayList<String> colorArrayList=new ArrayList<>();

    public ProductOverViewRatingAdapter(Activity activity, List<ProductRatingModel> overViewList, String image) {
        OverViewList = overViewList;


        mContext=activity.getApplication();
        this.image=image;
        this.activity=activity;
       // mResources.add(this.image);
        mCustomPagerAdapter = new CustomPagerAdapter(mContext);
        //svg = SVGParser.getSVGFromResource(activity.getResources(),R.raw.cross);

    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
      /*  View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.product_overview_rating, null);



        ViewHolder viewHolder = new ViewHolder(itemLayoutView);*/
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0) {
            View header = inflater.inflate(R.layout.product_main_header, parent, false);
            viewHolder = new HeaderViewHolder(header);

        }


        else if (viewType == 2) {
            View footer = inflater.inflate(R.layout.product_main_footer, parent, false);
        //    product_overview_rating
            viewHolder = new FooterHolder(footer);
        }

        else {
            View item = inflater.inflate(R.layout.bundle_productdetails_row, parent, false);
            viewHolder = new ViewHolder(item);
        }
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewholder, final int position) {

        if (viewholder.getItemViewType() == 0) {

            final HeaderViewHolder headView = (HeaderViewHolder) viewholder;
            //headView.ratingMain.setRating(Float.parseFloat(ratingCount));
            headView.ratingMain.setRating(Float.parseFloat(OverViewList.get(position).getRating()));
            mResources=OverViewList.get(position).getImageString();
            headView.viewPager.setAdapter(mCustomPagerAdapter);
       //     countButton=(ImageView)itemView.findViewById(R.id.imageView);
            headView.productTitle.setText(OverViewList.get(position).getProductName());
            pName=OverViewList.get(position).getProductName();
            pId=OverViewList.get(position).getProductId();

           //headView.product_reviewCount.setText("(" + reviewCount + ")");
            headView.product_reviewCount.setText("(" + OverViewList.get(position).getReviewCount() + ")");

       //    headView.countText.setText(countText);
            headView.countText.setText(OverViewList.get(position).getImagesCount());
            headView.shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //share();
                    //Share(getShareApplication(),"Hello Text Share");
                    shareIt(v);
                }
            });
            headView.countText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(image!=null){
                        Intent i=new Intent(activity, FragmentFullScreenImage.class);
                        i.putExtra("size",mResources.size());
                        i.putExtra("position", 0);
                        i.putExtra("image",image);
                        i.putExtra("baseImage",mResources);
                        activity.startActivity(i);
                    }
                }
            });

            if(OverViewList.get(position).getCommentSize()==0)
            {
                headView.reviewLay.setVisibility(View.GONE);
                type="http://www.awok.com/dp-";

            }
            else
            {
                type="http://www.awok.com/bundles/db-";
                headView.bundleCount.setText(OverViewList.get(position).getCommentSize()+" Items Included in the Bundle");
            }
            LayerDrawable reviewRatingColor = (LayerDrawable) headView.ratingMain.getProgressDrawable();
            reviewRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
            reviewRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
            reviewRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);
            /*ConnectivityManager connMgr = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new APIClient(activity,mContext ,  new GetProductDetailsCallback()).productDetailsAPICall(OverViewList.get(position).getProductId());
        } else {
            *//*Snackbar.make(getActivity().findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();*//*

            Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }*/
        }
        else
        if (viewholder.getItemViewType() == 2) {
            final FooterHolder footView = (FooterHolder) viewholder;
            //footView.prod_color_default.setText(colorArrayList.toString().replace("[", "").replace("]", ""));
            if(OverViewList.get(position).getColor().equals(""))
            {
                footView.prod_color_default.setVisibility(View.GONE);
                footView.prod_color.setVisibility(View.GONE);
            }
            else
            {
                footView.prod_color_default.setText(OverViewList.get(position).getColor());
            }
            //footView.prod_deliveryTime.setText(delivery_time);
            footView.prod_deliveryTime.setText(OverViewList.get(position).getEstimatedDays());

            //footView.prod_shippingCost.setText(shipping_cost);
            footView.prod_shippingCost.setText(OverViewList.get(position).getEstimatedPrice());
            if(OverViewList.get(position).getCondition().equals(""))
            {
                footView.condition.setVisibility(View.GONE);
                footView.prod_condition.setVisibility(View.GONE);
            }
            else {
                footView.prod_condition.setText(OverViewList.get(position).getCondition());
            }
        }
        else {
            //final ProductRatingModel item = OverViewList.get(position);
            final ViewHolder holder = (ViewHolder) viewholder;
          /*  LayerDrawable reviewRatingColor = (LayerDrawable) holder.rating.getProgressDrawable();
            reviewRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
            reviewRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
            reviewRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);

//////////////            holder.rating.setRating(Float.parseFloat(OverViewList.get(position).getRate()));
            holder.mainText.setText(OverViewList.get(position).getContent());
            holder.name.setText(OverViewList.get(position).getUsername());*/
            holder.name.setText(OverViewList.get(position).getBundleName());
            holder.newPrice.setText("AED "+OverViewList.get(position).getBundleNewPrice());
            holder.oldPrice.setText("AED "+OverViewList.get(position).getBundleOldPrice());


            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("NAME" + OverViewList.get(position).getBundleName());
                    clickBundle(OverViewList.get(position).getBundleName(), OverViewList.get(position).getBundleRating(), OverViewList.get(position).getBundleImage(),
                            OverViewList.get(position).getBundleNewPrice(), OverViewList.get(position).getBundleOldPrice(), OverViewList.get(position).getBundleProperties());
                }
            });



        }















    }

    public void shareIt(View view){
        //sharing implementation
        List<Intent> targetedShareIntents = new ArrayList<Intent>();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Awok.com";

        PackageManager pm = view.getContext().getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(sharingIntent, 0);
        for(final ResolveInfo app : activityList) {

            String packageName = app.activityInfo.packageName;
            Intent targetedShareIntent = new Intent(android.content.Intent.ACTION_SEND);
            targetedShareIntent.setType("text/plain");
            targetedShareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "share");
            if(TextUtils.equals(packageName, "com.facebook.katana")){
                targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, type+pId);
            } else {
                targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            }

            targetedShareIntent.setPackage(packageName);
            targetedShareIntents.add(targetedShareIntent);

        }

        Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Share");

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
        activity.startActivity(chooserIntent);

    }


    private void Share(List<String> PackageName,String Text) {
        try {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            // List<ResolveInfo> resInfo = getActivity().getPackageManager().queryIntentActivities(share, 0);
            PackageManager packManager = activity.getPackageManager();
            List<ResolveInfo> resInfo = packManager.queryIntentActivities(share, PackageManager.MATCH_DEFAULT_ONLY);
            if (!resInfo.isEmpty()) {
                for (ResolveInfo info : resInfo) {
                     // put here your mime type
                    Intent targetedShare = new Intent(Intent.ACTION_SEND);
                    targetedShare.setType("text/plain");
                    System.out.println("dvghcfsdc" + info.activityInfo.packageName);
                  //  String packageName = info.activityInfo.packageName;
                    if(info.activityInfo.packageName.contains("com.facebook.katana"))
                    {
                        /*ShareLinkContent content = new ShareLinkContent.Builder()
                                .setImageUrl(Uri.parse("http://" + image)).setContentTitle(pName)
                        .build();
                        ShareApi.share(content,null);*/
                       // setupFacebookShareIntent();
                       // System.out.println("dvghcfsdcsddcfdcxzczxczxc" + PackageName);
                        targetedShare.putExtra(android.content.Intent.EXTRA_TEXT, Uri.parse(type+pId));
                        targetedShare.setPackage(info.activityInfo.packageName.toLowerCase());
                        targetedShare.setClassName(
                                info.activityInfo.packageName,
                                info.activityInfo.name);
                        targetedShareIntents.add(targetedShare);
                    }
                    else if (PackageName.contains(info.activityInfo.packageName.toLowerCase())) {

                        targetedShare.putExtra(Intent.EXTRA_TEXT, Text);

                        targetedShare.setPackage(info.activityInfo.packageName.toLowerCase());
                        targetedShare.setClassName(
                                info.activityInfo.packageName,
                                info.activityInfo.name);
                        targetedShareIntents.add(targetedShare);
                    }
                }

                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
                activity.startActivity(chooserIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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



    public void setupFacebookShareIntent() {
        ShareDialog shareDialog;
        FacebookSdk.sdkInitialize(activity);
        shareDialog = new ShareDialog(activity);
/*
        SharePhoto photo = new SharePhoto.Builder()
                .setImageUrl((Uri.parse("http://" + image)))
        .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();*/
        ShareLinkContent linkContent = new ShareLinkContent.Builder()

                .setContentUrl(Uri.parse(type+pId))
                .build();
        shareDialog.show(linkContent);

    }


    private void clickBundle(String name,String rating,String image,String newPrice,String oldPrice,String properties) {

            bundleDialog = new Dialog(activity);
        bundleDialog.setCancelable(true);
        bundleDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bundleDialog.setContentView(R.layout.bundle_popup);
        final TextView popName=(TextView)bundleDialog.findViewById(R.id.name);
        final RatingBar popRating=(RatingBar)bundleDialog.findViewById(R.id.main_prodRatingBar);
        final TextView popOldPrice=(TextView)bundleDialog.findViewById(R.id.oldPrice);
        final TextView popNewPrice=(TextView)bundleDialog.findViewById(R.id.NewPrice);
        final ImageView popImage=(ImageView)bundleDialog.findViewById(R.id.mainImg);

            final ListView offersFilter = (ListView) bundleDialog.findViewById(R.id.codeListView);
        bundleAdapter=new ProductSpecificationAdapter(activity,specList);
            offersFilter.setAdapter(bundleAdapter);

        specList.clear();

        popName.setText(name);
        popNewPrice.setText("AED "+newPrice);
        popOldPrice.setText("AED "+oldPrice);
        LayerDrawable reviewRatingColor = (LayerDrawable) popRating.getProgressDrawable();
        reviewRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        reviewRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        reviewRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);
        popRating.setRating((float) Double.parseDouble(rating));

        if(popImage.equals(""))
        {

        }
        else
        {
            Picasso.with(mContext)
                    .load("http://" + image)
                    .noFade()
                    .into(popImage);
        }


        if(properties.equals("")) {

        }
        else {


            try {
                JSONArray jArray = new JSONArray(properties);
                for (int i = 0; i < jArray.length(); i++) {
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();
                    ProductSpecification listData = new ProductSpecification();
                    JSONObject jData = jArray.getJSONObject(i);
                    //listData.setSpecTitle(jData.getString("NAME"));
                    //     listData.setSpecValue(jData.getString("VALUE"));
                    if (jData.has("VALUE")) {
                 /*       if (jData.get("VALUE") instanceof JSONObject) {
                            listData.setSpecValue("");
                            listData.setSpecTitle(jData.getString("NAME"));
                            *//*JSONObject keyz = jData.getJSONObject("VALUE");
                            listData.setSubTitle(jData.getJSONObject("VALUE"));
                            listData.setSubData(jData.getJSONObject(""));*//*
                            Iterator<String> iter = jData.getJSONObject("VALUE").keys();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                try {
                                    Object value = jData.getJSONObject("VALUE").get(key);
                                        sb.append(key);
                                    sb.append(System.getProperty("line.separator"));
                                    sb2.append(value.toString());
                                    sb2.append(System.getProperty("line.separator"));
                                } catch (JSONException e) {
                                    // Something went wrong!
                                }

                            }
                            System.out.println("DATA"+sb.toString());
                            System.out.println("DATA" + sb2.toString());
                            listData.setSubTitle(sb.toString());
                            listData.setSubData(sb2.toString());
                        }*/
                        if (jData.get("VALUE") instanceof JSONArray) {
                            listData.setSpecValue("");
                            listData.setSpecTitle(jData.getString("NAME"));
                            /*JSONObject keyz = jData.getJSONObject("VALUE");
                            listData.setSubTitle(jData.getJSONObject("VALUE"));
                            listData.setSubData(jData.getJSONObject(""));*/
                    /*        Iterator<String> iter = jData.getJSONObject("VALUE").keys();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                try {
                                    Object value = jData.getJSONObject("VALUE").get(key);
                                    sb.append(key);
                                    sb.append(System.getProperty("line.separator"));
                                    sb2.append(value.toString());
                                    sb2.append(System.getProperty("line.separator"));
                                } catch (JSONException e) {
                                    // Something went wrong!
                                }*/
                            try {

                                int length = jData.getJSONArray("VALUE").length();
                                for (int j = 0; j < length; j++) {
                                    JSONObject jObj = jData.getJSONArray("VALUE").getJSONObject(j);


                                    sb.append(jObj.getString("NAME"));
                                    sb.append(System.getProperty("line.separator"));
                                    sb2.append(jObj.getString("VALUE"));
                                    sb2.append(System.getProperty("line.separator"));

                                    listData.setSubTitle(sb.toString());
                                    listData.setSubData(sb2.toString());
                                }

                            } catch (JSONException e) {
                                // Something went wrong!
                            }


                            System.out.println("DATA" + sb.toString());
                            System.out.println("DATA" + sb2.toString());
                            listData.setSubTitle(sb.toString());
                            listData.setSubData(sb2.toString());
                        } else {
                            listData.setSpecTitle(jData.getString("NAME"));
                            listData.setSpecValue(jData.getString("VALUE"));
                            listData.setSubTitle("");
                            listData.setSubData("");
                        }
                    }

                    specList.add(listData);

                }
                bundleAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        bundleDialog.show();
        bundleDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return OverViewList.size();
    }




    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        public TextView name,oldPrice,newPrice;



        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            oldPrice=(TextView)itemLayoutView
                    .findViewById(R.id.oldPrice);
            newPrice = (TextView) itemLayoutView
                    .findViewById(R.id.newPrice);
            name = (TextView) itemLayoutView
                    .findViewById(R.id.name);

        }


        @Override
        public void onClick(View v) {

        }
    }







    public void add(ProductRatingModel item, int position) {
        OverViewList.add(position, item);
        notifyItemInserted(position);
    }




    @Override
    public int getItemViewType(int position) {

        if (OverViewList.get(position).isHeader()) {
            return TYPE_HEADER;

        }



        else if (OverViewList.get(position).isLoader()) {
            return TYPE_FOOTER;

        }


        else if (position > 0) {
            return TYPE_ITEM;
        }

        return 1;
    }





    public static class FooterHolder extends RecyclerView.ViewHolder {


        private TextView prod_color_default,prod_deliveryTime,prod_shippingCost,prod_color;
        private TextView prod_condition,condition;



        FooterHolder(View footerView) {
            super(footerView);


            prod_color_default=(TextView)footerView.findViewById(R.id.prod_color_default);
            prod_deliveryTime=(TextView)footerView.findViewById(R.id.prod_deliveryTime);
            prod_shippingCost=(TextView)footerView.findViewById(R.id.prod_shippingCost);
            prod_condition=(TextView)footerView.findViewById(R.id.prod_cond_default);
            prod_color=(TextView)footerView.findViewById(R.id.prod_color);
            condition=(TextView)footerView.findViewById(R.id.condition);


        }
    }


     public static class HeaderViewHolder extends RecyclerView.ViewHolder {

         private RatingBar ratingMain;
         private ViewPager viewPager;
        private ImageView countButton,shareButton;
         private LinearLayout reviewLay;
        private TextView productTitle,reviewView,product_reviewCount,countText,bundleCount;



        HeaderViewHolder(View itemView) {
            super(itemView);

            ratingMain=(RatingBar)itemView.findViewById(R.id.main_prodRatingBar);
            viewPager= (ViewPager) itemView.findViewById(R.id.imageSlider);
            countButton=(ImageView)itemView.findViewById(R.id.imageView);
            productTitle=(TextView)itemView.findViewById(R.id.productTitle);
            shareButton=(ImageView)itemView.findViewById(R.id.share);
            bundleCount=(TextView)itemView.findViewById(R.id.bundleCount);
            reviewView=(TextView)itemView.findViewById(R.id.reviewView);
            product_reviewCount=(TextView)itemView.findViewById(R.id.product_reviewCount);
reviewLay=(LinearLayout)itemView.findViewById(R.id.reviewLay);
            countText=(TextView)itemView.findViewById(R.id.countText);
        }
    }



    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

      /*  @Override
        public int getCount() {
            return mResources.size();
        }*/
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
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // if(baseImage!=null){
                    if(image!=null)
                    {
                        Intent i=new Intent(activity, FragmentFullScreenImage.class);
                        i.putExtra("size",mResources.size());
                        i.putExtra("position", position);
                        i.putExtra("image",image);
                        i.putExtra("baseImage",mResources);
                        activity.startActivity(i);
                    }

                }
            });
if(position==0) {

    imageLoader.get("http://"+image, new ImageLoader.ImageListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            progressBar.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cart_icon));
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
           // imageLoader.get("http://"+mResources.get(position), new ImageLoader.ImageListener() {
            imageLoader.get("http://"+mResources.get(position), new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cart_icon));
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
                colorArrayList.clear();
                JSONObject mMembersJSON;
                mMembersJSON = new JSONObject(response);
                JSONObject dataObj = mMembersJSON.getJSONObject("OUTPUT");
//                System.out.println(mMembersJSON.getString("name"));
               // productTitle.setText(dataObj.getString("name"));
                String ratingsCount=dataObj.getJSONObject("DATA").getJSONObject("RATING").getString("RATING");
                String count=dataObj.getJSONObject("DATA").getJSONObject("RATING").getString("COUNT");
//                prodNewPrice.setText(Integer.toString(mMembersJSON.getInt("new_price")) + " " + "AED");
//                prodOldPrice.setText(Integer.toString(mMembersJSON.getInt("original_price")) + " " + "AED");
//                System.out.println("COOLGBDJH" + productDetails.getName());
//                String prodDesc=mMembersJSON.getString("description");
//                productOverview.setOverViewTitle(prodDesc);
         /////////////////////////////////////////////////////////////////////       image=dataObj.getString("image");
  //              prod_shippingCost.setText("AED "+(dataObj.getJSONObject("shipping_data").getString("shipping_cost")));
    //            prod_deliveryTime.setText((dataObj.getJSONObject("shipping_data").getString("est_from")) + " - " + (dataObj.getJSONObject("shipping_data").getString("est_to")) + " days");
                    delivery_time=(dataObj.getJSONObject("DATA").getJSONObject("SHIPPING").getString("PERIOD_FROM")) + " - " + (dataObj.getJSONObject("DATA").getJSONObject("SHIPPING").getString("PERIOD_TO")) + " days";
                shipping_cost="AED "+(dataObj.getJSONObject("DATA").getJSONObject("SHIPPING").getString("PRICE"));
                JSONArray imagesStringData=dataObj.getJSONObject("DATA").getJSONArray("IMAGES");
                for(int i=0;i<imagesStringData.length();i++)
                {
                    //JSONObject data=imagesStringData.getJSONObject(i);
                    String jsonData=imagesStringData.get(i).toString();
                    imageString.add(jsonData);


                }
                int colorLength=dataObj.getJSONObject("DATA").getJSONObject("VARIANTS").getJSONArray("COLORS").length();
                JSONArray colorArray = dataObj.getJSONObject("DATA").getJSONObject("VARIANTS").getJSONArray("COLORS");

                for(int j=0;j<colorLength;j++)
                {
                    JSONObject colorData=colorArray.getJSONObject(j);

                    colorArrayList.add(colorData.getString("NAME"));
                    System.out.println(colorArrayList + " color");

                }


            //    prod_color_default.setText(colorArrayList.toString().replace("[", "").replace("]", ""));



                //mResources=
                //////ratingMain.setRating(Float.parseFloat(ratingsCount));
                ratingCount=dataObj.getJSONObject("DATA").getJSONObject("RATING").getString("COUNT");
            //    product_reviewCount.setText("(" + count + ")");
                reviewCount=dataObj.getJSONObject("DATA").getJSONObject("RATING").getString("SUM");
                //countText.setText(String.valueOf(imageString.size()));
                countText=String.valueOf(imageString.size());
              //  baseImage=dataObj.getString("image");
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

          //      Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "Data could not be loaded", Snackbar.LENGTH_LONG)
                 //       .setActionTextColor(Color.RED);

          //      View snackbarView = snackbar.getView();

            //    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            //    textView.setTextColor(Color.WHITE);
              //  snackbar.show();
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

    public void getRatings()
    {


        //mAdapter.notifyDataSetChanged();
        /*mCustomPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(mCustomPagerAdapter);*/
        mCustomPagerAdapter.notifyDataSetChanged();
       // mAdapter.notifyDataSetChanged();
    //    notifyDataSetChanged();
        // viewPager.invalidate();
        //  viewPager.notify();

//        viewPager.notify();
    }

}

