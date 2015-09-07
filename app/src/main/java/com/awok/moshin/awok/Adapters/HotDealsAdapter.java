package com.awok.moshin.awok.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.Items;
import com.awok.moshin.awok.R;

import java.util.List;

/**
 * Created by moshin on 9/6/2015.
 */
public class HotDealsAdapter extends RecyclerView.Adapter<HotDealsAdapter.PersonViewHolder>{

    private int lastPosition = -1;
    private Context mContext;

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder holder, int i) {
        holder.nameTextView.setText(items.get(i).getName());
        holder.priceTextView.setText(items.get(i).getPrice());

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

// If you are using normal ImageView
        imageLoader.get(items.get(i).getImage(), new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HotDealsAdapter", "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    holder.itemImageView.setImageBitmap(response.getBitmap());
                }
            }
        });

        setAnimation(holder.container, i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    List<Items> items;

    public HotDealsAdapter(Context context, List<Items> items){
        this.mContext = context;
        this.items = items;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView nameTextView;
        TextView priceTextView;
        ImageView itemImageView;
        LinearLayout container;


        PersonViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView)itemView.findViewById(R.id.cv);
            nameTextView = (TextView)itemView.findViewById(R.id.person_name);
            priceTextView = (TextView)itemView.findViewById(R.id.person_age);
            itemImageView = (ImageView)itemView.findViewById(R.id.person_photo);
            container = (LinearLayout) itemView.findViewById(R.id.parentPanel);
        }
    }

}
