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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Fragments.DragonBallFooter;
import com.awok.moshin.awok.Fragments.FooterViewHolder;
import com.awok.moshin.awok.Models.DragonBallCharacter;
import com.awok.moshin.awok.Models.DragonBallHeader;
import com.awok.moshin.awok.Models.Products;
import com.awok.moshin.awok.R;
import com.karumi.headerrecyclerview.HeaderRecyclerViewAdapter;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.*;

/**
 * HeaderRecyclerViewAdapter extension created to show how to use the library using DragonBall
 * characters.
 */
public class DragonBallAdapter extends
        HeaderRecyclerViewAdapter<RecyclerView.ViewHolder,DragonBallHeader, Products,
                DragonBallFooter> {

    private Context mContext;
    ArrayList<Products> items;
    public static final int ITEM_WITH_DISCOUNT = 1;
    public static final int ITEM_WITHOUT_DISCOUNT = 2;
    public static final int ITEM_WITHOUT_LOADER = 3;

    public DragonBallAdapter(Context context, ArrayList<Products> items){
        this.mContext = context;
        this.items = items;
    }

  @Override
  protected ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
//    LayoutInflater inflater = getLayoutInflater(parent);
//    View headerView = inflater.inflate(R.layout.row_dragon_ball_header, parent, false);
    return null;
  }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = getLayoutInflater(parent);
        View characterView = inflater.inflate(R.layout.new_item, parent, false);
        return new ItemViewHolder(characterView);
    }

  @Override
  protected ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = getLayoutInflater(parent);
    View footerView = inflater.inflate(R.layout.row_dragon_ball_footer, parent, false);
    return new FooterViewHolder(footerView);
  }

  @Override
  protected void onBindHeaderViewHolder(ViewHolder holder, int position) {
//    DragonBallFooternBallHeader header = getHeader();
//    HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
//    headerViewHolder.render(header);
  }

  @Override
  protected void onBindItemViewHolder(final ViewHolder hold, int i) {
      final ItemViewHolder holder = (ItemViewHolder) hold;
      final int viewType = getItemViewType(i);
      switch (viewType) {
          case ITEM_WITHOUT_LOADER:
          {
              holder.priceTextView.setVisibility(View.GONE);
              holder.itemImageView.setVisibility(View.GONE);
              break;
          }
          case ITEM_WITH_DISCOUNT: {
              holder.discountTextView.setText(items.get(i).getDiscPercent() + "%");
              holder.priceLayout.setVisibility(View.VISIBLE);
              holder.priceTextView.setText(items.get(i).getPriceNew());
              holder.itemImageView.setImageDrawable(null);
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
              break;
          }
          case ITEM_WITHOUT_DISCOUNT: {
              holder.discountTextView.setVisibility(View.GONE);
              holder.priceLayout.setVisibility(View.VISIBLE);
              holder.priceTextView.setText(items.get(i).getPriceNew());
              holder.itemImageView.setImageDrawable(null);
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
              break;
          }
          default:
              // Blow up in whatever way you choose.
      }
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
      public int getItemViewType(int position) {
          if(items.get(position).isLoader()){
              return ITEM_WITHOUT_LOADER;
          }
          if (!items.get(position).getPriceOld().equalsIgnoreCase("0 AED")) {
              return ITEM_WITH_DISCOUNT;
          }
          else{
              return ITEM_WITHOUT_DISCOUNT;
          }
      }

  @Override
  protected void onBindFooterViewHolder(ViewHolder sadaHolder, int position) {
    DragonBallFooter footer = (DragonBallFooter) getFooter();
    FooterViewHolder footerViewHolder = (FooterViewHolder) sadaHolder;
    footerViewHolder.render(footer);
  }




  private LayoutInflater getLayoutInflater(ViewGroup parent) {
    return LayoutInflater.from(parent.getContext());
  }
}
