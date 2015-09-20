package com.awok.moshin.awok.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.awok.moshin.awok.Adapters.CategoriesAdapter;
import com.awok.moshin.awok.Fragments.BundleOffersFragment;
import com.awok.moshin.awok.Fragments.CategoriesFragment;
import com.awok.moshin.awok.Fragments.DailyDealsFragment;
import com.awok.moshin.awok.Fragments.HotDealsFragment;
import com.awok.moshin.awok.Fragments.WeeklyBestSellersFragment;
import com.awok.moshin.awok.Models.Categories;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.awok.moshin.awok.Util.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SubCategoriesActivity extends AppCompatActivity {
private DrawerLayout mDrawerLayout;
    RecyclerView mRecyclerView;
    CategoriesAdapter mAdapter;
    ProgressBar progressBar;
    ArrayList<Categories> categoriesArrayList;
    ArrayList<Categories> localCategoriesArrayList;
    int depthLevel = 2;
    int parentId = 0;
    String name;
    String id;
    ActionBar ab;
    private String TAG = "Categories Fragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(getIntent().getExtras().getString(Constants.CAT_NAME_INTENT));


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }


        mRecyclerView = (RecyclerView) findViewById(R.id.caetegoriesRecyclerView);
        progressBar = (ProgressBar) findViewById(R.id.marker_progress);
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager mLayoutManager = new GridLayoutManager(SubCategoriesActivity.this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(SubCategoriesActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

//                        name = localCategoriesArrayList.get(position).getName();
//                        parentId =localCategoriesArrayList.get(position).getParentId();
//                        id = localCategoriesArrayList.get(position).getId();
//                        depthLevel =localCategoriesArrayList.get(position).getDepthLevel();


                        int size = categoriesArrayList.size();
                        ArrayList<Categories> tempArray = new ArrayList<Categories>();
//                        for (int i=0;i<size;i++){
//                            if (categoriesArrayList.get(i).getParentId()==localCategoriesArrayList.get(position).getId()){
//                                tempArray.add(categoriesArrayList.get(i));
//                            }
//                        }

                        if (tempArray.size()==0){
                            Snackbar.make(SubCategoriesActivity.this.findViewById(android.R.id.content), "Open Products Listing", Snackbar.LENGTH_SHORT)
                    .setActionTextColor(Color.RED)
                    .show();
                        }
                        else{
                            name = localCategoriesArrayList.get(position).getName();
//                            parentId =localCategoriesArrayList.get(position).getParentId();
                            id = localCategoriesArrayList.get(position).getId();
//                            depthLevel =localCategoriesArrayList.get(position).getDepthLevel();
                            localCategoriesArrayList.clear();
                            localCategoriesArrayList.addAll(tempArray);
                            tempArray.clear();
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.getLayoutManager().scrollToPosition(0);
                            ab.setTitle(name);
//                            mRecyclerView.smoothScrollToPosition(0);
//                            mAdapter = new CategoriesAdapter(SubCategoriesActivity.this, localCategoriesArrayList);
//                            mRecyclerView.setAdapter(mAdapter);
                        }


                    }
                })
        );
        categoriesArrayList = new ArrayList<Categories>();
        categoriesArrayList = (ArrayList<Categories>)getIntent().getSerializableExtra(Constants.CAT_ARRAY_INTENT);

        initializeData();



//        ConnectivityManager connMgr = (ConnectivityManager)
//                SubCategoriesActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            new APIClient(SubCategoriesActivity.this, SubCategoriesActivity.this,  new GetCategoriesCallback()).categoriesAPICall();
//        } else {
//            Snackbar.make(SubCategoriesActivity.this.findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
//                    .setActionTextColor(Color.RED)
//                    .show();
//        }





        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:

//                        viewPager.setCurrentItem(0);
                        mDrawerLayout.closeDrawers();

                        return true;
                    case R.id.nav_messages:
//                        viewPager.setCurrentItem(1);
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_friends:
//                        viewPager.setCurrentItem(2);
                        mDrawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_discussion:
                        /*Intent i=new Intent(MainActivity.this,ProductDetailsView.class);
                        startActivity(i);*/

//                        Intent i=new Intent(getApplicationContext(),ProductDetailsView.class);
//                        startActivity(i);
                        mDrawerLayout.closeDrawers();
                        return true;


                    default:
                        return true;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (depthLevel==1){
            super.onBackPressed();
        }
        else{
            int size = categoriesArrayList.size();
            for(int i=0;i<size;i++){
//                if(categoriesArrayList.get(i).getId()==parentId){
//                    name = categoriesArrayList.get(i).getName();
//                    parentId =categoriesArrayList.get(i).getParentId();
//                    id = categoriesArrayList.get(i).getId();
////                    depthLevel =categoriesArrayList.get(i).getDepthLevel();
//                    ab.setTitle(name);
//                    break;
//                }
            }
            ArrayList<Categories> tempArray = new ArrayList<Categories>();
            for (int i=0;i<size;i++){
//                if (categoriesArrayList.get(i).getParentId()==id){
//                    tempArray.add(categoriesArrayList.get(i));
//                }
            }
                localCategoriesArrayList.clear();
                localCategoriesArrayList.addAll(tempArray);
                tempArray.clear();
                mAdapter.notifyDataSetChanged();
                mRecyclerView.getLayoutManager().scrollToPosition(0);
//                            mRecyclerView.smoothScrollToPosition(0);
//                            mAdapter = new CategoriesAdapter(SubCategoriesActivity.this, localCategoriesArrayList);
//                            mRecyclerView.setAdapter(mAdapter);

        }

    }


    //    public class GetCategoriesCallback extends AsyncCallback {
//        public void onTaskComplete(String response) {
//            try {
//                JSONObject mMembersJSON;
//                mMembersJSON = new JSONObject(response);
//                JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_CATEGORY_LIST_NAME);
//                int length = jsonArray.length();
//
//                for(int i=0;i<length;i++){
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                    categoriesArrayList.add(new Categories(jsonObject.getString("ID"), jsonObject.getString("NAME"), jsonObject.getString("SORT"), jsonObject.getString("IMAGE"),
//                            jsonObject.getString("DEPTH_LEVEL")));
//                }
//
//                if(SubCategoriesActivity.this!=null){
//                    Animation animation = AnimationUtils.loadAnimation(SubCategoriesActivity.this, android.R.anim.fade_out);
//                    progressBar.startAnimation(animation);
//                }
//                progressBar.setVisibility(View.GONE);
//                initializeData();
//            } catch (JSONException e) {
//                e.printStackTrace();
//                Snackbar.make(SubCategoriesActivity.this.findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
//                        .setActionTextColor(Color.RED)
//                        .show();
//            }
//        }
//        @Override
//        public void onTaskCancelled() {
//        }
//        @Override
//        public void onPreExecute() {
//            // TODO Auto-generated method stub
//            progressBar.setVisibility(View.VISIBLE);
//        }
//    }



    private void initializeData(){
        localCategoriesArrayList = new ArrayList<Categories>();
        name = getIntent().getExtras().getString(Constants.CAT_NAME_INTENT);
        parentId = getIntent().getExtras().getInt(Constants.CAT_PARENT_ID_INTENT);
//        id = getIntent().getExtras().getInt(Constants.CAT_ID_INTENT);
        depthLevel = getIntent().getExtras().getInt(Constants.CAT_DEPTH_LEVEL_INTENT);

        int size = categoriesArrayList.size();
        for (int i=0;i<size;i++){
//            if (categoriesArrayList.get(i).getParentId()==id){
//                localCategoriesArrayList.add(categoriesArrayList.get(i));
//            }
        }

        mAdapter = new CategoriesAdapter(SubCategoriesActivity.this, localCategoriesArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        ImageView closeButton = (ImageView)searchView.findViewById(R.id.search_close_btn);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
  /*      searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tabLayout.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tabLayout.setVisibility(View.GONE);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                tabLayout.setVisibility(View.VISIBLE);

                return false;
            }
        });

closeButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        tabLayout.setVisibility(View.VISIBLE);
    }
});*/


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {





               switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
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



    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
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
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new HotDealsFragment(), "Hot Deals");
        adapter.addFragment(new CategoriesFragment(), "Categories");
        adapter.addFragment(new DailyDealsFragment(), "Daily Deals");
        adapter.addFragment(new WeeklyBestSellersFragment(), "Weekly Best Sellers");
        adapter.addFragment(new BundleOffersFragment(), "Bundle Offers");




        viewPager.setAdapter(adapter);
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



}
