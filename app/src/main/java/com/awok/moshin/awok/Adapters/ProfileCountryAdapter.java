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
import com.awok.moshin.awok.Models.Interest_LanguageModel;
import com.awok.moshin.awok.Models.Profile_CountryModel;
import com.awok.moshin.awok.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shon on 1/21/2016.
 */
public class ProfileCountryAdapter extends BaseAdapter{


    /**
     * Created by shon on 1/21/2016.
     */

        private Activity activity;
        private LayoutInflater inflater;
        Context context;
        private List<Profile_CountryModel> movieItems;
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        Map<Integer,String> list = new HashMap<Integer,String>();
        public ProfileCountryAdapter(Activity activity, List<Profile_CountryModel> movieItems,Context context) {
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
                convertView = inflater.inflate(R.layout.interest_custom_list_row, null);


            final TextView title = (TextView) convertView.findViewById(R.id.title);

            final ImageView im=(ImageView)convertView.findViewById(R.id.im);


            Profile_CountryModel m = movieItems.get(position);



            if(m.isSelected())
            {
                im.setVisibility(View.VISIBLE);
            }
            else
            {
                im.setVisibility(View.GONE);
            }

            //thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

            // title
            title.setText(m.getName());
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


