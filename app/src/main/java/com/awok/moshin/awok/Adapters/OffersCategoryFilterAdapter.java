package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.Profile_CountryModel;
import com.awok.moshin.awok.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shon on 2/1/2016.
 */
public class OffersCategoryFilterAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    Context context;
    private List<String> movieItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    Map<Integer,String> list = new HashMap<Integer,String>();
    public OffersCategoryFilterAdapter(Activity activity, List<String> movieItems,Context context) {
        this.activity = activity;
        this.movieItems = movieItems;
        this.context=context;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.dialog_offers_filter_row, null);


        final TextView title = (TextView) convertView.findViewById(R.id.filterText);






        //thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(movieItems.get(position));
        // title.setTag(m.getId());

        // rating
        //rating.setText("Quantity: " + String.valueOf(m.getRating()));

        // genre
   /* String genreStr = "";
    for (String str : m.getGenre()) {
        genreStr += str + ", ";
    }
    genreStr = genreStr.length() > 0 ? genreStr.substring(0,
            genreStr.length() - 2) : genreStr;
    genre.setText(genreStr);

    // release year
    year.setText(String.valueOf(m.getYear()));*/

        return convertView;
    }
}
