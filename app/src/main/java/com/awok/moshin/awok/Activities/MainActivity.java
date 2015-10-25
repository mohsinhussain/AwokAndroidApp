package com.awok.moshin.awok.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awok.moshin.awok.Fragments.BundleOffersFragment;
import com.awok.moshin.awok.Fragments.CategoriesFragment;
import com.awok.moshin.awok.Fragments.DailyDealsFragment;
import com.awok.moshin.awok.Fragments.FilterFragment;
import com.awok.moshin.awok.Fragments.Home_Fragment;
import com.awok.moshin.awok.Fragments.HotDealsFragment;
import com.awok.moshin.awok.Fragments.ProductOverViewFragment;
import com.awok.moshin.awok.Fragments.SignInFragment;
import com.awok.moshin.awok.Fragments.WeeklyBestSellersFragment;
import com.awok.moshin.awok.Models.Categories;
import com.awok.moshin.awok.Models.Products;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, FilterFragment.StartCommunication{
private DrawerLayout mDrawerLayout;
    private TabLayout tabLayout;
    ProgressBar progressBar;
    private ImageView img;
    public static final String AVATAR_URL = "http://lorempixel.com/200/200/people/1/";
    ViewPager viewPager;
    ArrayList<Categories> categoriesArrayList;
    private String TAG = "Main Activity";
    SharedPreferences mSharedPrefs;
    SearchView searchView;
    MenuItem searchItem;
    int selectedTabIndex = 0;
    ProgressBar progressBarLogout;
    boolean isFilter;
    Adapter adapter;
    RelativeLayout navHeaderLayout;
    Button applyButton;
    ArrayList<String> tagsFilterArray = new ArrayList<String>();
    ArrayList<String> colorFilterArray = new ArrayList<String>();
    ArrayList<String> priceFilterArray = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPrefs = getSharedPreferences(Constants.PREFS_NAME, 0);
        progressBar = (ProgressBar) findViewById(R.id.marker_progress);
        progressBar.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        applyButton = (Button) findViewById(R.id.applyButton);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.menu_icon);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setLogo(R.drawable.awok_logo);
        ab.setIcon(R.drawable.back_button);
//        ab.setIcon(R.drawable.menu_icon);
        img = (ImageView)findViewById(R.id.avatar);
        Picasso.with(this).load(R.drawable.textimg).transform(new CircleTransformation()).into(img);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navHeaderLayout = (RelativeLayout) findViewById(R.id.navHeaderBackground);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new Adapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        isFilter = false;


        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFilter = false;
                if (Build.VERSION.SDK_INT >= 11)
                {
                    VersionHelper.refreshActionBarMenu(MainActivity.this);
                }
                selectedTabIndex = tabLayout.getSelectedTabPosition();
                System.out.println("Selected tab: " + selectedTabIndex);
                adapter.clear();
                applyButton.setVisibility(View.GONE);
                applyFilter();
            }
        });



//        if (viewPager != null) {
//            setupViewPager(viewPager);
//        }

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabTextColors(getResources().getColor(R.color.normal_text), getResources().getColor(R.color.header_text));

//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab selectedTab) {
////                int tabCount = tabLayout.getTabCount();
////                for (int i = 0; i < tabCount; i++) {
////                    TabLayout.Tab tab = tabLayout.getTabAt(i);
////                    View tabView = tab != null ? tab.getCustomView() : null;
//                    if (selectedTab.getCustomView() instanceof TextView) {
//                        ((TextView) selectedTab.getCustomView()).setTextAppearance(getApplicationContext(),R.style.TextAppearance_Tabs_Selected);
////                        ((TextView) selectedTab.getCustomView()).setTextAppearance(getApplicationContext(), selectedTab.equals(tab)
////                                ? R.style.TextAppearance_Tabs_Selected
////                                : R.style.TextAppearance_Tabs);
//                    }
////                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });


        navHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:

                        viewPager.setCurrentItem(0);
                        mDrawerLayout.closeDrawers();

                        return true;
                    case R.id.cart:
                        Intent i=new Intent(MainActivity.this,CheckOutActivity.class);
                        startActivity(i);
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.orderHistory:
                        Intent j=new Intent(MainActivity.this,OrderHistory.class);
                        startActivity(j);
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.navigation_Logout:
                        final Dialog logoutDialog = new Dialog(MainActivity.this, R.style.AppCompatAlertDialogStyle);
                        logoutDialog.setCancelable(true);
                        logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        logoutDialog.setContentView(R.layout.dialog_logout);
                        Button cancelButton = (Button) logoutDialog.findViewById(R.id.cancelButton);
                        Button logoutButton = (Button) logoutDialog.findViewById(R.id.logoutButton);
                        progressBarLogout = (ProgressBar) logoutDialog.findViewById(R.id.load_progress_bar);
                        // if button is clicked, close the custom dialog
                        cancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                logoutDialog.dismiss();
                            }
                        });
                        logoutButton .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String mobileNumber = mSharedPrefs.getString(Constants.USER_MOBILE_PREFS, null);
                                new APIClient(MainActivity.this, MainActivity.this, new logoutUserCallback()).userLogoutAPICall(mobileNumber);
                            }
                        });

                        logoutDialog.show();
                        logoutDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                    case R.id.navigation_settings:
                        Intent k=new Intent(MainActivity.this,Settings.class);
                        startActivity(k);
                        mDrawerLayout.closeDrawers();
                        return true;

                    default:
                        return true;
                }
            }
        });

        /**
         *
         * FETCHING CATEGORIES FROM SERVER
         */
        categoriesArrayList = new ArrayList<Categories>();

        if (mSharedPrefs.contains(Constants.CAT_LIST_PREFS)){
            String resp = mSharedPrefs.getString(Constants.CAT_LIST_PREFS, null);
            try {
                JSONObject mMembersJSON;
                mMembersJSON = new JSONObject(resp);
                JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_CATEGORY_LIST_NAME);
                int length = jsonArray.length();

                for(int i=0;i<length;i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    categoriesArrayList.add(new Categories(jsonObject.getString("id"), jsonObject.getString("name"), jsonObject.getString("parent")));
                }
                initializeData();
                tabLayout.setupWithViewPager(viewPager);

//                tabLayout.setOnTabSelectedListener(
//                        new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
//                            @Override
//                            public void onTabSelected(TabLayout.Tab selectedTab) {
//                                super.onTabSelected(selectedTab);
//                                int tabCount = tabLayout.getTabCount();
//                                for (int i = 0; i < tabCount; i++) {
//                                    TabLayout.Tab tab = tabLayout.getTabAt(i);
//                                    View tabView = tab != null ? tab.getCustomView() : null;
//                                    if (tabView instanceof TextView) {
//                                        ((TextView) selectedTab.getCustomView()).setTextAppearance(getApplicationContext(), R.style.TextAppearance_Tabs_Selected);
//                                        ((TextView) selectedTab.getCustomView()).setTextAppearance(getApplicationContext(), selectedTab.equals(tab) ? R.style.TextAppearance_Tabs_Selected
//                                            : R.style.TextAppearance_Tabs);
//                                }
//                }
//
//                            }
//                        });
            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(findViewById(android.R.id.content), "Categories could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();
            }
        }
        else{
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new APIClient(MainActivity.this, MainActivity.this,  new GetCategoriesCallback()).categoriesAPICall();
            } else {
                Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED);

                View snackbarView = snackbar.getView();

                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
            }
        }
    }



    @Override
    public void addColor(String color) {
        colorFilterArray.add(color);
        System.out.println("Add Color: "+color);
    }

    @Override
    public void removeColor(String color) {
        for(int i=0;i<colorFilterArray.size();i++){
            if(colorFilterArray.get(i).equalsIgnoreCase(color))
                colorFilterArray.remove(i);
        }
        System.out.println("Remove Color: " + color);
    }

    @Override
    public void addTag(String tag) {
        tagsFilterArray.add(tag);
        System.out.println("Add Color: "+tag);
    }

    @Override
    public void removeTag(String tag) {
        for(int i=0;i<tagsFilterArray.size();i++){
            if(tagsFilterArray.get(i).equalsIgnoreCase(tag))
                tagsFilterArray.remove(i);
        }
        System.out.println("Remove Color: " + tag);
    }

    @Override
    public void addPrice(String price) {
        priceFilterArray.add(price);
        System.out.println("Add Price: "+price);
    }

    @Override
    public void removePrice(String price) {
        for(int i=0;i<priceFilterArray.size();i++){
            if(priceFilterArray.get(i).equalsIgnoreCase(price))
                priceFilterArray.remove(i);
        }
        System.out.println("Remove Color: " + price);
    }

    public class logoutUserCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
                if(obj.getBoolean("errors")) {
                    Snackbar.make(findViewById(android.R.id.content), obj.getString("message"), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                else{
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.clear();
                    editor.commit();
                    Intent i = new Intent(MainActivity.this, SplashActivity.class);
                    startActivity(i);
                    finish();
                }
                progressBarLogout.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
                progressBarLogout.setVisibility(View.GONE);
            }

        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);

        }
    }

    public class GetCategoriesCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject mMembersJSON;
                Log.v(TAG, response);
                mMembersJSON = new JSONObject(response);
                JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_CATEGORY_LIST_NAME);
                int length = jsonArray.length();

                for(int i=0;i<length;i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    categoriesArrayList.add(new Categories(jsonObject.getString("id"), jsonObject.getString("name"), jsonObject.getString("parent")));

                }

                if(MainActivity.this!=null){
                    Animation animation = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out);
                    progressBar.startAnimation(animation);
                }
                progressBar.setVisibility(View.GONE);
                SharedPreferences.Editor editor = mSharedPrefs.edit();
                editor.putString(Constants.CAT_LIST_PREFS, response);
                editor.commit();
                initializeData();
                tabLayout.setupWithViewPager(viewPager);
            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(findViewById(android.R.id.content), "Categories could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();
            }
        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void applyFilter() {

        if (viewPager != null) {
            if(selectedTabIndex==0){
                adapter.addFragment(new HotDealsFragment(null, colorFilterArray, tagsFilterArray, priceFilterArray, true), "All");
            }
            else{
                adapter.addFragment(new HotDealsFragment(), "All");
            }



            int size = categoriesArrayList.size();

            for (int i = 0; i < size; i++) {
                if (selectedTabIndex == i+1) {
                    adapter.addFragment(new HotDealsFragment(categoriesArrayList.get(i).getId(), colorFilterArray, tagsFilterArray, priceFilterArray, true), categoriesArrayList.get(i).getName());
                } else {
                    adapter.addFragment(new HotDealsFragment(categoriesArrayList.get(i).getId()), categoriesArrayList.get(i).getName());
                }

            }
        }
        for(int i=0;i<tabLayout.getTabCount();i++){
            adapter.notifyChangeInPosition(i);
        }
        adapter.notifyDataSetChanged();
    }

    private void initializeData(){

//        adapter = new Adapter(getSupportFragmentManager());
        if(viewPager!=null){
            if(isFilter){
                adapter.addFragment(new FilterFragment(MainActivity.this), "All");
            }
            else{
                adapter.addFragment(new HotDealsFragment(), "All");
            }

            int size = categoriesArrayList.size();

            for (int i = 0; i < size; i++){
                if(isFilter){
                    adapter.addFragment(new FilterFragment(categoriesArrayList.get(i).getId(), MainActivity.this), categoriesArrayList.get(i).getName());
                }
                else{
                    adapter.addFragment(new HotDealsFragment(categoriesArrayList.get(i).getId()), categoriesArrayList.get(i).getName());
                }

            }
        }

        adapter.notifyChangeInPosition(0);
        adapter.notifyChangeInPosition(1);
        adapter.notifyDataSetChanged();
//        tabLayout.setupWithViewPager(viewPager);
//        TabLayout.Tab tab = tabLayout.getTabAt(selectedTabIndex);
//        tab.select();
    }


    static class VersionHelper
    {
        static void refreshActionBarMenu(Activity activity)
        {
            activity.invalidateOptionsMenu();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem tickItem = menu.findItem(R.id.action_tick);
        MenuItem crossItem = menu.findItem(R.id.action_cross);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem filterItem = menu.findItem(R.id.app_cart);
        if(isFilter){
            tickItem.setEnabled(true).setVisible(true);
            crossItem.setEnabled(true).setVisible(true);
            searchItem.setEnabled(false).setVisible(false);
            filterItem.setEnabled(false).setVisible(false);
        }
        else{
            tickItem.setEnabled(false).setVisible(false);
            crossItem.setEnabled(false).setVisible(false);
            searchItem.setEnabled(true).setVisible(true);
            filterItem.setEnabled(true).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    searchItem.collapseActionView();
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {





        switch (item.getItemId()) {
            case android.R.id.home:
            {
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }

           case R.id.app_cart:
           {
               isFilter = true;
               if (Build.VERSION.SDK_INT >= 11)
               {
                   VersionHelper.refreshActionBarMenu(this);
               }
               selectedTabIndex = tabLayout.getSelectedTabPosition();
               System.out.println("Selected tab: "+selectedTabIndex);
               adapter.clear();
               initializeData();
               applyButton.setVisibility(View.VISIBLE);
               return true;
           }

           case R.id.action_tick:
           {
               isFilter = false;
               if (Build.VERSION.SDK_INT >= 11)
               {
                   VersionHelper.refreshActionBarMenu(this);
               }
               selectedTabIndex = tabLayout.getSelectedTabPosition();
               System.out.println("Selected tab: " + selectedTabIndex);
               adapter.clear();
               applyButton.setVisibility(View.GONE);
               applyFilter();
               return true;
           }

           case R.id.action_cross:
           {
               isFilter = false;
               if (Build.VERSION.SDK_INT >= 11)
               {
                   VersionHelper.refreshActionBarMenu(this);
               }
               selectedTabIndex = tabLayout.getSelectedTabPosition();
               System.out.println("Selected tab: "+selectedTabIndex);
               adapter.clear();
               applyButton.setVisibility(View.GONE);
               initializeData();
               return true;
           }

        }
        return super.onOptionsItemSelected(item);


    }



    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public boolean onQueryTextSubmit(String query) {
        if(query.equalsIgnoreCase("")){
            Snackbar.make(MainActivity.this.findViewById(android.R.id.content), "Please type some thing to search Awok", Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .show();
        }
        else{
            Intent i = new Intent(MainActivity.this, SearchActivity.class);
            i.putExtra(Constants.SEARCH_FILTER_INTENT, query);
            startActivity(i);
            searchItem.collapseActionView();
        }


        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }








    class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();
        private long baseId = 0;
        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        public void clear(){
            mFragments.clear();
            mFragmentTitles.clear();
        }

        //this is called when notifyDataSetChanged() is called
        @Override
        public int getItemPosition(Object object) {
            // refresh all fragments when data set changed
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
                return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitles.get(position);
        }

        @Override
        public long getItemId(int position) {
            // give an ID different from position when position has been changed
            return baseId + position;
        }

        /**
         * Notify that the position of a fragment has been changed.
         * Create a new ID for each position to force recreation of the fragment
         * @param n number of items which have been changed
         */
        public void notifyChangeInPosition(int n) {
            // shift the ID returned by getItemId outside the range of all previous fragments
            baseId += getCount() + n;
        }
    }



    /*@Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HotDealsFragment();

                break;
            case 1:
                fragment = new CategoriesFragment();

                break;
            case 2:
                fragment = new BundleOffersFragment();

                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }*/
   // }

    public class RoundedTransformation extends Transformation {
        private final int radius;
        private final int margin;  // dp

        // radius is corner radii in dp
        // margin is the board in dp
        public RoundedTransformation(final int radius, final int margin) {
            this.radius = radius;
            this.margin = margin;
        }


        public Bitmap transform(final Bitmap source) {
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            canvas.drawRoundRect(new RectF(margin, margin, source.getWidth() - margin, source.getHeight() - margin), radius, radius, paint);

            if (source != output) {
                source.recycle();
            }

            return output;
        }


        public String key() {
            return "rounded";
        }
    }

}
