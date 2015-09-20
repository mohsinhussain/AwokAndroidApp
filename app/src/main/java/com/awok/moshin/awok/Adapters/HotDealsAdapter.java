package com.awok.moshin.awok.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.Products;
import com.awok.moshin.awok.R;

import java.util.List;

/**
 * Created by moshin on 9/6/2015.
 */
public class HotDealsAdapter extends RecyclerView.Adapter<HotDealsAdapter.ItemViewHolder>{

    private int lastPosition = -1;
    private Context mContext;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ItemViewHolder pvh = new ItemViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int i) {
        holder.nameTextView.setText(items.get(i).getName());
        holder.priceTextView.setText(items.get(i).getPriceNew());

        final int viewType = getItemViewType(i);
        switch (viewType) {
            case ITEM_WITH_DISCOUNT:
                holder.discountTextView.setText("55"+"%");
                holder.oldPriceTextView.setText(items.get(i).getPriceOld());
//                holder.discountTextView.setRotation();

//                holder.endsInTextView.setText("Ends in " + items.get(i).getH() + "h " + items.get(i).getI() + "m " + items.get(i).getS() + "s");
                break;
            case ITEM_WITHOUT_DISCOUNT:
                holder.discountTextView.setVisibility(View.GONE);
//                holder.endsInTextView.setVisibility(View.GONE);
                holder.oldPriceTextView.setVisibility(View.GONE);
                break;
            default:
                // Blow up in whatever way you choose.
        }

        float init = 0;
        float rotate = 315;

        if (Build.VERSION.SDK_INT < 11) {

            RotateAnimation animation = new RotateAnimation(init, rotate);
            animation.setDuration(100);
            animation.setFillAfter(true);
            holder.discountTextView.startAnimation(animation);
        } else {

            holder.discountTextView.setRotation(rotate);
        }


//        if(!items.get(i).getPriceOld().equalsIgnoreCase("0 AED")){
//            holder.discountTextView.setText(items.get(i).getDiscPercent()+"%");
//            holder.oldPriceTextView.setText(items.get(i).getPriceOld());
//            holder.endsInTextView.setText("Ends in "+items.get(i).getH()+"h "+items.get(i).getI()+"m "+items.get(i).getS()+"s");
//        }
//        else{
//            holder.discountTextView.setVisibility(View.GONE);
//            holder.endsInTextView.setVisibility(View.GONE);
//            holder.oldPriceTextView.setVisibility(View.GONE);
//        }


        byte[] decodedString = Base64.decode(items.get(i).getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.itemImageView.setImageBitmap(decodedByte);

//        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//
//// If you are using normal ImageView
//        imageLoader.get(items.get(i).getImage(), new ImageLoader.ImageListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("HotDealsAdapter", "Image Load Error: " + error.getMessage());
//            }
//
//            @Override
//            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
//                if (response.getBitmap() != null) {
//                    // load image into imageview
//                    holder.itemImageView.setImageBitmap(response.getBitmap());
//                }
//            }
//        });

//        setAnimation(holder.container, i);
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


    List<Products> items;
    public static final int ITEM_WITH_DISCOUNT = 1;
    public static final int ITEM_WITHOUT_DISCOUNT = 2;

    public HotDealsAdapter(Context context, List<Products> items){
        this.mContext = context;
        this.items = items;
    }


    @Override
    public int getItemViewType(int position) {
        if (!items.get(position).getPriceOld().equalsIgnoreCase("0 AED")) {
            return ITEM_WITH_DISCOUNT;
        }
        else{
            return ITEM_WITHOUT_DISCOUNT;
        }
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView nameTextView;
        TextView priceTextView;
        TextView oldPriceTextView;
        TextView discountTextView;
        ImageView itemImageView;
        LinearLayout container;
//        TextView endsInTextView;


        ItemViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView)itemView.findViewById(R.id.cv);
            nameTextView = (TextView)itemView.findViewById(R.id.nameTextView);
            priceTextView = (TextView)itemView.findViewById(R.id.priceTextView);
            oldPriceTextView = (TextView)itemView.findViewById(R.id.oldPriceTextView);
            discountTextView = (TextView)itemView.findViewById(R.id.percentTextView);
//            endsInTextView = (TextView)itemView.findViewById(R.id.endsInTextView);
            itemImageView = (ImageView)itemView.findViewById(R.id.itemImageView);
            container = (LinearLayout) itemView.findViewById(R.id.parentPanel);
        }
    }

}
