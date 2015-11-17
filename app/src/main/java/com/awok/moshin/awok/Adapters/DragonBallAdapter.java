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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awok.moshin.awok.Fragments.DragonBallFooter;
import com.awok.moshin.awok.Fragments.FooterViewHolder;
import com.awok.moshin.awok.Models.DragonBallCharacter;
import com.awok.moshin.awok.Models.DragonBallHeader;
import com.awok.moshin.awok.Models.Products;
import com.awok.moshin.awok.R;
import com.karumi.headerrecyclerview.HeaderRecyclerViewAdapter;

/**
 * HeaderRecyclerViewAdapter extension created to show how to use the library using DragonBall
 * characters.
 */
public class DragonBallAdapter extends
        HeaderRecyclerViewAdapter<RecyclerView.ViewHolder, DragonBallHeader, Products,
                DragonBallFooter> {

  Context mContext;
  int cellWidth = 0;

  public DragonBallAdapter(Context mContext){
    this.mContext = mContext;
    this.cellWidth = cellWidth;
  }

  @Override
  protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = getLayoutInflater(parent);
    View headerView = inflater.inflate(R.layout.new_item, parent, false);
    return new HeaderViewHolder(headerView);
  }

  @Override
  protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = getLayoutInflater(parent);
    View characterView = inflater.inflate(R.layout.new_item, parent, false);
    return new CharacterViewHolder(characterView);
  }

  @Override
  protected RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = getLayoutInflater(parent);
    View footerView = inflater.inflate(R.layout.row_dragon_ball_footer, parent, false);
    return new FooterViewHolder(footerView);
  }

  @Override
  protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
    DragonBallHeader header = getHeader();
    HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
    headerViewHolder.render(header);
  }

  @Override
  protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    Products character = getItem(position);
    CharacterViewHolder characterViewHolder = (CharacterViewHolder) holder;
    characterViewHolder.render(character, mContext);
  }

  @Override
  protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {

    DragonBallFooter footer = getFooter();
    FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
    footerViewHolder.render(footer);
  }

  private LayoutInflater getLayoutInflater(ViewGroup parent) {
    return LayoutInflater.from(parent.getContext());
  }
}
