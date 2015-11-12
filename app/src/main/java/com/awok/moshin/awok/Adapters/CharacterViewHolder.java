/*
 * Copyright (C) 2015 Karumi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.awok.moshin.awok.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.DragonBallCharacter;
import com.awok.moshin.awok.Models.Products;
import com.awok.moshin.awok.R;
import com.squareup.picasso.Picasso;

/**
 * RecyclerView.ViewHolder extension which renders a DragonBallCharacter instance into a view.
 */
public class CharacterViewHolder extends RecyclerView.ViewHolder {

  CardView mCardView;
  //        TextView nameTextView;
  TextView priceTextView;
  //        TextView oldPriceTextView;
  TextView discountTextView;
  ImageView itemImageView;
  LinearLayout container;
  ProgressBar loadProgressBar;
  RelativeLayout priceLayout;
  View overLay;

  public CharacterViewHolder(View itemView) {
    super(itemView);
    mCardView = (CardView)itemView.findViewById(R.id.cv);
//            nameTextView = (TextView)itemView.findViewById(R.id.nameTextView);
    priceTextView = (TextView)itemView.findViewById(R.id.priceTextView);
//            oldPriceTextView = (TextView)itemView.findViewById(R.id.oldPriceTextView);
    discountTextView = (TextView)itemView.findViewById(R.id.percentTextView);
    loadProgressBar = (ProgressBar)itemView.findViewById(R.id.load_progress_bar);
    itemImageView = (ImageView)itemView.findViewById(R.id.itemImageView);
    container = (LinearLayout) itemView.findViewById(R.id.parentPanel);
    priceLayout= (RelativeLayout) itemView.findViewById(R.id.priceLayout);
    overLay = (View) itemView.findViewById(R.id.overLay);
  }

  public void render(final Products character, final Context mContext) {
    itemImageView.getViewTreeObserver().addOnPreDrawListener(
            new ViewTreeObserver.OnPreDrawListener() {
              public boolean onPreDraw() {
                int cellWidth = itemImageView.getMeasuredWidth();
                int imageHeighFromServer = character.getImageHeight(mContext);
                int imageWidthFromServer = 120;
                int cellHeight = cellWidth * imageHeighFromServer / imageWidthFromServer;
                itemImageView.getLayoutParams().height = cellHeight;
                itemImageView.requestLayout();
                return true;
              }
            });


    discountTextView.setText(character.getDiscPercent() + "% OFF");
    priceLayout.setVisibility(View.VISIBLE);
    priceTextView.setText(character.getPriceNew());

    itemImageView.setImageDrawable(null);

//        holder.itemImageView.setVisibility(View.INVISIBLE);
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    imageLoader.get(character.getImage(), new ImageLoader.ImageListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        itemImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.default_img));
//                holder.itemImageView.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out);
        animation.setStartOffset(500);
        animation.setFillAfter(true);
        overLay.startAnimation(animation);
        loadProgressBar.setVisibility(View.GONE);
      }

      @Override
      public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
        if (response.getBitmap() != null) {
          // load image into imageview
          itemImageView.setImageBitmap(response.getBitmap());
//                    holder.itemImageView.setVisibility(View.VISIBLE);
          Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out);
          animation.setStartOffset(500);
          animation.setFillAfter(true);
          overLay.startAnimation(animation);
          loadProgressBar.setVisibility(View.GONE);
        }
      }
    });
  }
}
