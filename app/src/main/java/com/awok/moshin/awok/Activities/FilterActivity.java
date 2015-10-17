package com.awok.moshin.awok.Activities;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awok.moshin.awok.Fragments.HotDealsFragment;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;
import com.awok.moshin.awok.Util.PredicateLayout;
import com.liangfeizc.flowlayout.FlowLayout;

import java.net.URLEncoder;

public class FilterActivity extends AppCompatActivity {
private DrawerLayout mDrawerLayout;

    ActionBar ab;
    private String TAG = "Filter Activity";
    private LinearLayout mainContent;
    FlowLayout colorFlowLayout;
//    private String searchString = "";
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        colorFlowLayout = (FlowLayout) findViewById(R.id.colorFlowlayout );
        setSupportActionBar(toolbar);

        ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Filter");


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mainContent = (LinearLayout) findViewById(R.id.containerLayout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
                (this.LAYOUT_INFLATER_SERVICE);


        for (int i = 0; i < 5; i++) {
            Button t = (Button) inflater.inflate(R.layout.filter_button_tag,null);
                t.setText("hai");
                t.setSingleLine(true);
            t.setBackground(getResources().getDrawable(R.drawable.filter_button));
            colorFlowLayout.addView(t);
        }



//        searchString = getIntent().getStringExtra(Constants.SEARCH_FILTER_INTENT);
//        ab.setTitle(searchString);
//        URLEncoder.encode(searchString);

//        HotDealsFragment fragment = new HotDealsFragment(searchString, true);
//        FragmentManager fm = getSupportFragmentManager(); //or getFragmentManager() if you are not using support library.
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.containerLayout, fragment);
//        ft.commit();




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
//                mDrawerLayout.openDrawer(GravityCompat.START);
                onBackPressed();
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


}
