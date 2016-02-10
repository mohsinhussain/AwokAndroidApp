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
import com.awok.moshin.awok.Models.OffersModel;
import com.awok.moshin.awok.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shon on 1/31/2016.
 */
public class OffersRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OffersModel> moviesList;
    Context mContext;


    private  final int TYPE_FOOTER = 2;
    private final int TYPE_ITEM = 1;



    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public class MyViewHolder extends RecyclerView.ViewHolder {





         TextView title;
         TextView desc;
        RelativeLayout mainRel;


        ImageView im;
        public MyViewHolder(View view) {
            super(view);


            title= (TextView) view.findViewById(R.id.title);
           desc = (TextView) view.findViewById(R.id.desc);
           im =(ImageView)view.findViewById(R.id.offersImage);
            mainRel=(RelativeLayout)view.findViewById(R.id.mainRel);
//            nameTextView = (TextView)itemView.findViewById(R.id.nameTextView);

        }
    }


    public OffersRecyclerAdapter(Activity activity,List<OffersModel> moviesList,Context context) {
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




            View item = inflater.inflate(R.layout.offers_row_layout, parent, false);
            viewHolder = new MyViewHolder(item);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder1, int position) {


            final MyViewHolder holder=(MyViewHolder)holder1;
            OffersModel movie = moviesList.get(position);

        holder.title.setText(movie.getName());
        holder.desc.setText(movie.getDesc());


        Picasso.with(mContext)
                .load("http://" + movie.getImage())
                .noFade()
                .into(holder.im);

/*holder.mainRel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        System.out.println("KEY ");
    }
});*/
    }
    @Override
    public int getItemCount() {
        return moviesList.size();
    }








}

