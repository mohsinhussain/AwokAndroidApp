package com.awok.moshin.awok.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.Categories;
import com.awok.moshin.awok.Models.Products;
import com.awok.moshin.awok.R;

import java.util.List;

/**
 * Created by moshin on 9/6/2015.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>{

    private int lastPosition = -1;
    private Context mContext;

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
        CategoriesViewHolder pvh = new CategoriesViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final CategoriesViewHolder holder, int i) {

        holder.nameTextView.setText(categories.get(i).getName());
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//        if(categories.get(i).getImage()!=null && !categories.get(i).getImage().equalsIgnoreCase("") && !categories.get(i).getImage().equalsIgnoreCase("null")){
//            imageLoader.get(categories.get(i).getImage(), new ImageLoader.ImageListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("HotDealsAdapter", "Image Load Error: " + error.getMessage());
//                }
//
//                @Override
//                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
//                    if (response.getBitmap() != null) {
//                        // load image into imageview
//                        holder.itemImageView.setImageBitmap(response.getBitmap());
//                    }
//                }
//            });
//        }
//        else{
//            holder.itemImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_awok));
//        }


//        setAnimation(holder.container, i);
    }

    @Override
    public int getItemCount() {
        return categories.size();
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


    List<Categories> categories;
    public static final int ITEM_WITH_DISCOUNT = 1;
    public static final int ITEM_WITHOUT_DISCOUNT = 2;

    public CategoriesAdapter(Context context, List<Categories> categories){
        this.mContext = context;
        this.categories = categories;
    }


//    @Override
//    public int getItemViewType(int position) {
////        if(categories.get(position).getImage()!=null && !categories.get(position).getImage().equalsIgnoreCase("") && !categories.get(position).getImage().equalsIgnoreCase("null")){
////            return ITEM_WITH_DISCOUNT;
////        }
////        else{
////            return ITEM_WITHOUT_DISCOUNT;
////        }
//    }


    public static class CategoriesViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView nameTextView;
        ImageView itemImageView;
        LinearLayout container;


        CategoriesViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView)itemView.findViewById(R.id.cv);
            nameTextView = (TextView)itemView.findViewById(R.id.nameTextView);
            itemImageView = (ImageView)itemView.findViewById(R.id.itemImageView);
            container = (LinearLayout) itemView.findViewById(R.id.parentPanel);
        }
    }

}
