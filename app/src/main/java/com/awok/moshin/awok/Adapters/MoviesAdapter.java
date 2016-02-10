package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.OffersCatModel;
import com.awok.moshin.awok.R;
import com.squareup.picasso.Picasso;

import java.util.List;



/**
 * Created by ANSH on 27/01/2016.
 */



public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<OffersCatModel> moviesList;
    Context mContext;


    public static  final int TYPE_FOOTER = 2;
    public static final int TYPE_ITEM = 1;



    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public class MyViewHolder extends RecyclerView.ViewHolder {




        TextView priceTextView;
        TextView oldPriceTextView;

        Button discountTextView;
        TextView timerTextView;
        ImageView itemImageView;
        LinearLayout container, timerLayout;
        ProgressBar loadProgressBar;
        RelativeLayout priceLayout;
        View overLay;
        public MyViewHolder(View view) {
            super(view);



//            nameTextView = (TextView)itemView.findViewById(R.id.nameTextView);
            priceTextView = (TextView)itemView.findViewById(R.id.priceTextView);
            oldPriceTextView = (TextView)itemView.findViewById(R.id.offTextView);
            discountTextView = (Button)itemView.findViewById(R.id.percentTextView);
            loadProgressBar = (ProgressBar)itemView.findViewById(R.id.load_progress_bar);
            itemImageView = (ImageView)itemView.findViewById(R.id.itemImageView);
            container = (LinearLayout) itemView.findViewById(R.id.parentPanel);
            timerLayout = (LinearLayout) itemView.findViewById(R.id.timerLayout);
            priceLayout= (RelativeLayout) itemView.findViewById(R.id.priceLayout);
            overLay = (View) itemView.findViewById(R.id.overLay);
            timerTextView = (TextView)itemView.findViewById(R.id.timerTextView);
        }
    }


    public MoviesAdapter(Activity activity, List<OffersCatModel> moviesList) {
        this.moviesList = moviesList;
        this.mContext=activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
 /*       View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_item, parent, false);

        return new MyViewHolder(itemView);*/
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());



         if (viewType == 2) {
            View footer = inflater.inflate(R.layout.footer_product_progressbar, parent, false);
            viewHolder = new FooterHolder(footer);
        }

        else {
            View item = inflater.inflate(R.layout.data_item, parent, false);
            viewHolder = new MyViewHolder(item);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, int position) {

        if (holder1.getItemViewType() == 2) {
            final FooterHolder footer = (FooterHolder) holder1;

        }
        else {
            final MyViewHolder holder=(MyViewHolder)holder1;

            OffersCatModel movie = moviesList.get(position);
            System.out.println("fbkjgv" + movie.getName());
            holder.priceLayout.setVisibility(View.VISIBLE);
            holder.priceTextView.setVisibility(View.VISIBLE);
            holder.discountTextView.setVisibility(View.VISIBLE);
            holder.priceTextView.setText("AED " + movie.getNewPrice());
            if(movie.getOldPrice().equals("0"))
            {
                holder.oldPriceTextView.setVisibility(View.GONE);

            }

            else {
                holder.oldPriceTextView.setText("AED " + movie.getOldPrice());
            }
            holder.discountTextView.setText(movie.getPercentage() + "% OFF");
            holder.timerTextView.setText("");
            if (!movie.getDays().equals("00")) {
                holder.timerTextView.append(movie.getDays() + "day ");
            }
            if (!movie.getHrs().equals("00")) {
                holder.timerTextView.append(movie.getHrs() + "hrs ");
            }
            if (!movie.getMin().equals("00")) {
                holder.timerTextView.append(movie.getMin() + "min ");
            }
            if (!movie.getSec().equals("00"))


            {
                holder.timerTextView.append(movie.getSec() + "secs");
            }


            if(movie.getShowTimer())
            {
                holder.timerTextView.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.timerTextView.setVisibility(View.GONE);
            }
if(movie.getImg().equals(""))
{

}
            else {
    Picasso.with(mContext)
            .load("http://" + movie.getImg())
            .noFade()
            .into(holder.itemImageView);
}
           /* System.out.println("ch" + movie.getImg());
            imageLoader.get("http://" + movie.getImg(), new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean b) {
                    if (response.getBitmap() != null) {
                        // load image into imageview
                        holder.itemImageView.setImageBitmap(response.getBitmap());
//                    holder.itemImageView.setVisibility(View.VISIBLE);

                        Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out);
                        animation.setStartOffset(500);
                        animation.setFillAfter(true);
                        holder.overLay.startAnimation(animation);
                        holder.loadProgressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {

//                holder.itemImageView.setVisibility(View.VISIBLE);

                    holder.itemImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_tab_call));

                    Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out);
                    animation.setStartOffset(500);
                    animation.setFillAfter(true);
                    holder.overLay.startAnimation(animation);

                    holder.loadProgressBar.setVisibility(View.GONE);


                }
            });*/
        }
    }
        @Override
    public int getItemCount() {
        return moviesList.size();
    }




    @Override
    public int getItemViewType(int position) {




if(moviesList.size()>0) {
    if (moviesList.get(position).isLoader()) {
        return TYPE_FOOTER;

    } else if (position > 0) {
        return TYPE_ITEM;
    }
}
        return 1;
    }




    public static class FooterHolder extends RecyclerView.ViewHolder {



        FooterHolder(View itemView) {
            super(itemView);


        }
    }



}

