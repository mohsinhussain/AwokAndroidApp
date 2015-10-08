package com.awok.moshin.awok.Activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.awok.moshin.awok.R;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class FragmentFullScreenImage extends AppCompatActivity {

    /*static final int NUM_ITEMS = 6;*/
    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager viewPager;
     ActionBar ab;
    int[] mResources = {R.drawable.eagle, R.drawable.horse, R.drawable.bonobo, R.drawable.wolf, R.drawable.owl};
int size,pos;
    String image;
    String baseImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_full_screen_image);
        Intent i=getIntent();
        image=getIntent().getExtras().getString("image");
        baseImage=getIntent().getExtras().getString("baseImage");
         size=getIntent().getExtras().getInt("size");
         pos=getIntent().getExtras().getInt("position");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

         ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
       // ab.setTitle(pos + "/" + size);
        mCustomPagerAdapter = new CustomPagerAdapter(getApplicationContext(),pos);


        viewPager = (ViewPager) findViewById(R.id.imageSlider);
        viewPager.setAdapter(mCustomPagerAdapter);
        viewPager.setCurrentItem(pos);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                ab.setTitle((position+1) + "/" + size);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }
    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        int pos;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context,int pos) {
            mContext = context;
            pos=pos;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View itemView = mLayoutInflater.inflate(R.layout.full_screen_image, container, false);
//            BitmapDrawable bitmapDrawable =  new BitmapDrawable(base64ToBitmap(image));
            Log.v("FullScreenImageUrl", "FullScreenImageUrl: " + baseImage);
            SubsamplingScaleImageView  imageView = (SubsamplingScaleImageView) itemView.findViewById(R.id.imageView);
            System.out.println(mResources[position]);
            if(baseImage!=null && !baseImage.equalsIgnoreCase("")){
                imageView.setImage(ImageSource.bitmap(base64ToBitmap(baseImage)));
            }
            else{
                imageView.setImage(ImageSource.resource(R.drawable.default_img));
            }

//            base64ToBitmap(image)
            //imageView.setImageResource(mResources[position]);




            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {





        switch (item.getItemId()) {
            case android.R.id.home:
                //mDrawerLayout.openDrawer(GravityCompat.START);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);


    }
    private Bitmap base64ToBitmap(String imageString) {
        byte[] imageAsBytes = Base64.decode(imageString.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

    }
}
