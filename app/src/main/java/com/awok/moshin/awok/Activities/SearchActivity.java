package com.awok.moshin.awok.Activities;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.awok.moshin.awok.Adapters.CategoriesAdapter;
import com.awok.moshin.awok.Adapters.HotDealsAdapter;
import com.awok.moshin.awok.Fragments.BundleOffersFragment;
import com.awok.moshin.awok.Fragments.CategoriesFragment;
import com.awok.moshin.awok.Fragments.DailyDealsFragment;
import com.awok.moshin.awok.Fragments.HotDealsFragment;
import com.awok.moshin.awok.Fragments.WeeklyBestSellersFragment;
import com.awok.moshin.awok.Models.Categories;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.awok.moshin.awok.Util.RecyclerItemClickListener;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
private DrawerLayout mDrawerLayout;

    ActionBar ab;
    private String TAG = "Search Activity";
    private String searchString = "";
    MenuItem searchItem;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(getIntent().getExtras().getString(Constants.CAT_NAME_INTENT));


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        searchString = getIntent().getStringExtra(Constants.SEARCH_FILTER_INTENT);
        ab.setTitle(searchString);
        URLEncoder.encode(searchString);

        HotDealsFragment fragment = new HotDealsFragment(searchString, true);
        FragmentManager fm = getSupportFragmentManager(); //or getFragmentManager() if you are not using support library.
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.containerLayout, fragment);
        ft.commit();



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
                        /*Intent i=new Intent(MainActivity.this,ProductDetailsActivity.class);
                        startActivity(i);*/

//                        Intent i=new Intent(getApplicationContext(),ProductDetailsActivity.class);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        ImageView closeButton = (ImageView)searchView.findViewById(R.id.search_close_btn);
        searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {





               switch (item.getItemId()) {
            case android.R.id.home:
//                mDrawerLayout.openDrawer(GravityCompat.START);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);


    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public boolean onQueryTextSubmit(String query) {
        if(query.equalsIgnoreCase("")){
            Snackbar.make(SearchActivity.this.findViewById(android.R.id.content), "Please type some thing to search Awok", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }
        else{
            Intent i = new Intent(SearchActivity.this, SearchActivity.class);
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


}
