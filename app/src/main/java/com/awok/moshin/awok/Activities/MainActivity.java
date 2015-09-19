package com.awok.moshin.awok.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
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
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.awok.moshin.awok.Fragments.BundleOffersFragment;
import com.awok.moshin.awok.Fragments.CategoriesFragment;
import com.awok.moshin.awok.Fragments.DailyDealsFragment;
import com.awok.moshin.awok.Fragments.Home_Fragment;
import com.awok.moshin.awok.Fragments.HotDealsFragment;
import com.awok.moshin.awok.Fragments.WeeklyBestSellersFragment;
import com.awok.moshin.awok.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
private DrawerLayout mDrawerLayout;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }





        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

         tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:

                        viewPager.setCurrentItem(0);
                        mDrawerLayout.closeDrawers();

                        return true;
                    case R.id.nav_messages:
                        viewPager.setCurrentItem(1);
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.nav_friends:
                        viewPager.setCurrentItem(2);
                        mDrawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_discussion:
                        /*Intent i=new Intent(MainActivity.this,ProductDetailsView.class);
                        startActivity(i);*/
                        //mDrawerLayout.closeDrawers();
                        Intent i=new Intent(MainActivity.this,ProductDetailsView.class);
                        startActivity(i);

                        mDrawerLayout.closeDrawer(Gravity.LEFT);

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








        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);


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
                   case R.id.app_cart:
                       Intent i=new Intent(this,CheckOutActivity.class);
                       startActivity(i);

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

    @Override
    public boolean onQueryTextSubmit(String query) {


        Snackbar.make(MainActivity.this.findViewById(android.R.id.content), "Submitted", Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .show();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Snackbar.make(MainActivity.this.findViewById(android.R.id.content), "CHANGE", Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .show();
        return false;
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
