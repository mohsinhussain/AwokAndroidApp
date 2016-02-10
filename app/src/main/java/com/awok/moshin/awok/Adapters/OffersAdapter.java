package com.awok.moshin.awok.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.awok.moshin.awok.AppController;
import com.awok.moshin.awok.Models.OffersModel;
import com.awok.moshin.awok.Models.Payment_Pick;
import com.awok.moshin.awok.Models.ProductRatingPageModel;
import com.awok.moshin.awok.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shon on 1/27/2016.
 */
public class OffersAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    Context context;
    private  final int TYPE_FOOTER = 2;

    private final int TYPE_ITEM = 1;
    private List<OffersModel> movieItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    Map<Integer,String> list = new HashMap<Integer,String>();
    public OffersAdapter(Activity activity, List<OffersModel> movieItems,Context context) {
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
            convertView = inflater.inflate(R.layout.offers_row_layout, null);


        final TextView title = (TextView) convertView.findViewById(R.id.title);
        final TextView desc = (TextView) convertView.findViewById(R.id.desc);
        final RelativeLayout rel=(RelativeLayout)convertView.findViewById(R.id.mainRel);

        final ImageView im=(ImageView)convertView.findViewById(R.id.offersImage);
System.out.println("GOING");

        final OffersModel m = movieItems.get(position);





        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get("http://"+movieItems.get(position).getImage(), new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                im.setImageDrawable(activity.getResources().getDrawable(R.drawable.default_img));
                im.setVisibility(View.GONE);
            }
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    im.setImageBitmap(response.getBitmap());

                }
            }
        });

        //thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(m.getName());
        desc.setText(m.getDesc());
        title.setTag(m.getId());
        // title.setTag(m.getId());

        // rating
      /*  rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key = m.getKey();
                String value = m.getValue();

                System.out.print("KEY " + key + "value   " + value);


            }
        });*/


        return convertView;
    }


/*    public static class FooterHolder extends RecyclerView.ViewHolder {



        FooterHolder(View itemView) {
            super(itemView);


        }
    }*/



 /*   @Override
    public int getItemViewType(int position) {





         if (movieItems.get(position).isLoader()) {
            return TYPE_FOOTER;

        }


        else if (position > 0) {
            return TYPE_ITEM;
        }

        return 1;
    }*/


}
