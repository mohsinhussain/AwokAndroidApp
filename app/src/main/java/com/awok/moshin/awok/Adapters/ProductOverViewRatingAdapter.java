package com.awok.moshin.awok.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.Activities.CheckOutActivity;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.productOverviewRating;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.larvalabs.svgandroid.SVG;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shon on 10/10/2015.
 */
public class ProductOverViewRatingAdapter extends RecyclerView.Adapter<ProductOverViewRatingAdapter.ViewHolder> {
    private List<productOverviewRating> OverViewList = new ArrayList<productOverviewRating>();
    private Activity activity_main;
    View customView;

    private ArrayAdapter<CharSequence> adapter;


    Context mContext;


    public ProductOverViewRatingAdapter(Activity activity, List<productOverviewRating> overViewList) {
        OverViewList = overViewList;


        mContext=activity.getApplication();
        //svg = SVGParser.getSVGFromResource(activity.getResources(),R.raw.cross);

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProductOverViewRatingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.product_overview_rating, null);



        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        final productOverviewRating item = OverViewList.get(position);

        LayerDrawable reviewRatingColor = (LayerDrawable) viewHolder.rating.getProgressDrawable();
        reviewRatingColor.getDrawable(2).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        reviewRatingColor.getDrawable(1).setColorFilter(Color.parseColor("#FFEA00"), PorterDuff.Mode.SRC_ATOP);
        reviewRatingColor.getDrawable(0).setColorFilter(Color.parseColor("#E0E0E0"), PorterDuff.Mode.SRC_ATOP);

            viewHolder.rating.setRating(2);
            viewHolder.mainText.setText(OverViewList.get(position).getMainText());
            viewHolder.name.setText(OverViewList.get(position).getName());






















    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return OverViewList.size();
    }




    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        public TextView mainText,name;
        public RatingBar rating;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            rating=(RatingBar)itemLayoutView
                    .findViewById(R.id.main_prodRatingBar);
            mainText = (TextView) itemLayoutView
                    .findViewById(R.id.mainText);
            name = (TextView) itemLayoutView
                    .findViewById(R.id.name);

        }


        @Override
        public void onClick(View v) {

        }
    }







    public void add(productOverviewRating item, int position) {
        OverViewList.add(position, item);
        notifyItemInserted(position);
    }










}

