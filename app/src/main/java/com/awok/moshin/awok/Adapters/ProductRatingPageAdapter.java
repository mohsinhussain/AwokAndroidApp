package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.ProductRatingPageModel;
import com.awok.moshin.awok.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shon on 11/12/2015.
 */

    public class ProductRatingPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  final int TYPE_FOOTER = 2;
    private List<ProductRatingPageModel> OverViewList = new ArrayList<ProductRatingPageModel>();
        private Activity activity;
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;


        // Provide a suitable constructor (depends on the kind of dataset)
        public ProductRatingPageAdapter(Activity activity, List<ProductRatingPageModel> overViewList) {
            OverViewList = overViewList;
            activity=activity;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
         /*   // create a new view
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.store_rating_layout, null);

            // create ViewHolder

            ViewHolder viewHolder = new ViewHolder(itemLayoutView);*/
            RecyclerView.ViewHolder viewHolder;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (viewType == 0) {
                View header = inflater.inflate(R.layout.header_reviews, parent, false);
                viewHolder = new HeaderViewHolder(header);

            }


            else if (viewType == 2) {
                View footer = inflater.inflate(R.layout.footer_product_progressbar, parent, false);
                viewHolder = new FooterHolder(footer);
            }

            else {
                View item = inflater.inflate(R.layout.store_rating_layout, parent, false);
                viewHolder = new item(item);
            }
            return viewHolder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewholder, int position) {

            // - get data from your itemsData at this position
            // - replace the contents of the view with that itemsData
            if (viewholder.getItemViewType() == 0) {
                final HeaderViewHolder headView = (HeaderViewHolder) viewholder;
                //firstVH.headerTextView.setText(OverViewList.get(position).getHeaderView());
                headView.productNameView.setText(OverViewList.get(position).getProductName());
                // headView.ratingCounttxt.setText(OverViewList.get(position).getRatingCount());
               // headView.boughtByTxt.setText(OverViewList.get(position).getBoughtBy());
                       // headView.savedByTxt.setText(OverViewList.get(position).getSavedBy());
                if(OverViewList.get(position).getBoughtBy().equals("")||OverViewList.get(position).getBoughtBy().equals("0"))
                {
                    headView.boughtByTxt.setVisibility(View.GONE);
                }
                else
                {

                    headView.boughtByTxt.setText("Bought By: "+OverViewList.get(position).getBoughtBy()+" people");


                }
                System.out.println("size"+OverViewList.get(position).getCommentsValueSize());
if(OverViewList.get(position).getCommentsValueSize()==0)
{
    headView.reviewsLayout.setVisibility(View.VISIBLE);
}
            /*    if(OverViewList.get(position).getSavedBy().equals("")||OverViewList.get(position).getSavedBy().equals("0"))
                {
                    headView.savedByTxt.setVisibility(View.GONE);
                }
                else
                {

                    headView.savedByTxt.setText("Saved By: "+OverViewList.get(position).getSavedBy()+" people");


                }*/




                if(OverViewList.get(position).getRatingCount().equals(""))
                {
                    headView.ratingCounttxt.setVisibility(View.GONE);
                }
                else
                {


                    headView.ratingCounttxt.setText("(" + OverViewList.get(position).getRatingCount() + ")");

                }



                LayerDrawable reviewRatingColor = (LayerDrawable) headView.prod_reviewRating.getProgressDrawable();
                reviewRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
                reviewRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
                reviewRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);
                headView.prod_reviewRating.setRating(Float.parseFloat(OverViewList.get(position).getRating()));
                ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                if(OverViewList.get(position).getImage().equals(""))
                {
                    headView.imgMain.setImageDrawable(activity.getResources().getDrawable(R.drawable.cart_icon));
                }
                else {
                    imageLoader.get("http://"+OverViewList.get(position).getImage(), new ImageLoader.ImageListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            headView.imgMain.setImageDrawable(activity.getResources().getDrawable(R.drawable.mic));

                        }

                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                            if (response.getBitmap() != null) {
                                // load image into imageview
                                headView.imgMain.setImageBitmap(response.getBitmap());

                            }
                        }
                    });
                }

            }
           else if (viewholder.getItemViewType() == 2) {
                final FooterHolder footer = (FooterHolder) viewholder;
            }
            else {
                final item holder = (item) viewholder;
                LayerDrawable reviewRatingColor = (LayerDrawable) holder.main_prodRatingBar.getProgressDrawable();
                reviewRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
                reviewRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
                reviewRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);
                holder.content.setText(OverViewList.get(position).getContent());
//                holder.main_prodRatingBar.setRating(Float.parseFloat(OverViewList.get(position).getRate()));
                holder.user.setText(OverViewList.get(position).getUsername());
                holder.days.setText(OverViewList.get(position).getDays());
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            if(OverViewList!=null){
                return OverViewList.size();
            }
            else {
                return 0;
            }
        }

        // inner class to hold a reference to each item of RecyclerView
        public static class item extends RecyclerView.ViewHolder {

            public TextView user,content,days;
            public RatingBar main_prodRatingBar;

            public item(View itemLayoutView) {
                super(itemLayoutView);
                user = (TextView) itemLayoutView
                        .findViewById(R.id.name);
                main_prodRatingBar = (RatingBar) itemLayoutView
                        .findViewById(R.id.main_prodRatingBar);

                days=(TextView)itemLayoutView
                        .findViewById(R.id.days);

                content = (TextView) itemLayoutView
                        .findViewById(R.id.content);



            }
        }


    @Override
    public int getItemViewType(int position) {

        if (OverViewList.get(position).getHeaderView()) {
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






    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView productNameView,ratingCounttxt,boughtByTxt,savedByTxt;
        ImageView imgMain;
        RatingBar prod_reviewRating;
        LinearLayout reviewsLayout;


        HeaderViewHolder(View itemView) {
            super(itemView);

            prod_reviewRating=(RatingBar)itemView.findViewById(R.id.main_prodRatingBar);
            productNameView=(TextView)itemView.findViewById(R.id.productTitle);
            //RatingBar ratingBar=(RatingBar)mView.findViewById(R.id.main_prodRatingBar);
            ratingCounttxt=(TextView)itemView.findViewById(R.id.product_reviewCount);
            boughtByTxt=(TextView)itemView.findViewById(R.id.textView);
            savedByTxt=(TextView)itemView.findViewById(R.id.textView2);
            imgMain=(ImageView)itemView.findViewById(R.id.mainImg);
            reviewsLayout = (LinearLayout) itemView.findViewById(R.id.reviews_txt);

        }
    }


    public static class FooterHolder extends RecyclerView.ViewHolder {



        FooterHolder(View itemView) {
            super(itemView);


        }
    }

}
