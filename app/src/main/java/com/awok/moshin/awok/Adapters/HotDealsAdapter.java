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
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.Products;
import com.awok.moshin.awok.R;

import java.util.List;

/**
 * Created by moshin on 9/6/2015.
 */
public class HotDealsAdapter extends RecyclerView.Adapter<HotDealsAdapter.ItemViewHolder>{


    private Context mContext;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_item, parent, false);
        ItemViewHolder pvh = new ItemViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int i) {
//        holder.nameTextView.setText(items.get(i).getName());
        holder.priceTextView.setText(items.get(i).getPriceNew());

        final int viewType = getItemViewType(i);
        switch (viewType) {
            case ITEM_WITH_DISCOUNT:
                holder.discountTextView.setText(items.get(i).getDiscPercent()+"%");
                break;
            case ITEM_WITHOUT_DISCOUNT:
                holder.discountTextView.setVisibility(View.GONE);
                break;
            default:
                // Blow up in whatever way you choose.
        }



        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(items.get(i).getImage(), new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                holder.itemImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.default_img));
                holder.loadProgressBar.setVisibility(View.GONE);
            }
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    holder.itemImageView.setImageBitmap(response.getBitmap());
                    holder.loadProgressBar.setVisibility(View.GONE);
                }
            }
        });


        holder.itemImageView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        int cellWidth = holder.itemImageView.getMeasuredWidth();
                        int imageHeighFromServer = items.get(i).getImageHeight(mContext);
                        int imageWidthFromServer = 150;
                        int cellHeight = cellWidth*imageHeighFromServer/imageWidthFromServer;
                        holder.itemImageView.getLayoutParams().height = cellHeight;
                        holder.itemImageView.requestLayout();
                        return true;
                    }
                });
    }

    @Override
    public int getItemCount() {
        if(items!=null){
            return items.size();
        }
        else{
            return 0;
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
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
//        TextView nameTextView;
        TextView priceTextView;
//        TextView oldPriceTextView;
        TextView discountTextView;
        ImageView itemImageView;
        LinearLayout container;
        ProgressBar loadProgressBar;


        ItemViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView)itemView.findViewById(R.id.cv);
//            nameTextView = (TextView)itemView.findViewById(R.id.nameTextView);
            priceTextView = (TextView)itemView.findViewById(R.id.priceTextView);
//            oldPriceTextView = (TextView)itemView.findViewById(R.id.oldPriceTextView);
            discountTextView = (TextView)itemView.findViewById(R.id.percentTextView);
            loadProgressBar = (ProgressBar)itemView.findViewById(R.id.load_progress_bar);
            itemImageView = (ImageView)itemView.findViewById(R.id.itemImageView);
            container = (LinearLayout) itemView.findViewById(R.id.parentPanel);
        }
    }

}
