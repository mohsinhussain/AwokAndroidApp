package com.awok.moshin.awok.Activities;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.awok.moshin.awok.Adapters.OffersCategoryFilterAdapter;
import com.awok.moshin.awok.Adapters.ProfileCountryAdapter;
import com.awok.moshin.awok.Fragments.OneFragment;
import com.awok.moshin.awok.Models.OffersCategoryModel;
import com.awok.moshin.awok.Models.OffersSubCategory;
import com.awok.moshin.awok.Models.Profile_CountryModel;
import com.awok.moshin.awok.Models.SortOffersModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OffersCategoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private String dataArrayAll="";
    String data="";
    private int totalPages=0;
    public String type="";
    Spinner popup;
    private ViewPager viewPager;
    //ArrayList<Profile_CountryModel> filterData=new ArrayList<>();
    ArrayList<String > filterData=new ArrayList<>();
    private String mainCategoryName="";
    ArrayList<SortOffersModel> sortArray=new ArrayList<>();
    Dialog filterDialog;
    ArrayList<OffersCategoryModel> offersMainData=new ArrayList<>();
    OffersCategoryFilterAdapter filterAdapter;
    ArrayList<OffersSubCategory> offersSubData=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_category);
data=getIntent().getStringExtra("data");
        type=getIntent().getStringExtra("type");
        System.out.println("dcfv" + type);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
     //   popup=(Spinner)findViewById(R.id.spinner);
        // setupViewPager(viewPager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Offers");

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        // tabLayout.setupWithViewPager(viewPager);
      /*  tabLayout.setBackgroundColor(getResources().getColor(R.color.primary));
        tabLayout.setTabTextColors(getResources().getColor(R.color.normal_text), getResources().getColor(R.color.header_text));*/

        filterAdapter=new OffersCategoryFilterAdapter(OffersCategoryActivity.this,filterData,getApplicationContext());

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new APIClient(OffersCategoryActivity.this, getApplicationContext(),  new GetCategoriesCallback()).getOffersCatrgory(data.toString());
        } else {
            Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
  /*      adapter.addFrag(new OneFragment(), "ONE");
        adapter.addFrag(new OneFragment(), "TWO");
        adapter.addFrag(new OneFragment(), "THREE");
        adapter.addFrag(new OneFragment(), "FOUR");
        adapter.addFrag(new OneFragment(), "FIVE");
        adapter.addFrag(new OneFragment(), "SIX");
        adapter.addFrag(new OneFragment(), "SEVEN");
        adapter.addFrag(new OneFragment(), "EIGHT");
        adapter.addFrag(new OneFragment(), "NINE");
        adapter.addFrag(new OneFragment(), "TEN");*/
        int dataLength=offersMainData.size();
        if(dataLength==0)
        {
            adapter.addFrag(new OneFragment(data, "", "", "", "[]", sortArray, dataArrayAll, totalPages), getIntent().getStringExtra("title"));

        }
        else {
            for (int k = 0; k < dataLength; k++) {
                if (k == 0) {
                    adapter.addFrag(new OneFragment(data, offersMainData.get(k).getTitle(), offersMainData.get(k).getKey(), offersMainData.get(k).getValue(), offersMainData.get(k).getSubCat(), sortArray, dataArrayAll, totalPages), offersMainData.get(k).getTitle().toString());

                } else {
                    adapter.addFrag(new OneFragment(data, offersMainData.get(k).getTitle(), offersMainData.get(k).getKey(), offersMainData.get(k).getValue(), offersMainData.get(k).getSubCat(), sortArray, "", totalPages), offersMainData.get(k).getTitle().toString());
                    System.out.println("DATA SIZE" + offersMainData.get(k).getTitle() + "czgvxcgbdcgb" + offersMainData.get(k).getSubCat());
                }
            }
        }
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }




    public class GetCategoriesCallback extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject mMembersJSON;
                System.out.println("CATE" + response);

                mMembersJSON = new JSONObject(response);
                //JSONArray jsonArray = mMembersJSON.getJSONArray(Constants.JSON_CATEGORY_LIST_NAME);

                    if (mMembersJSON.getJSONObject("STATUS").getInt("CODE") == 200) {
                        totalPages=mMembersJSON.getJSONObject("OUTPUT").getJSONObject("NAVIGATION").getInt("TOTAL");
                        if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").has("MAIN_NAV")) {
                            JSONArray jsonArray = mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("MAIN_NAV");
                            int length = jsonArray.length();

                            for (int i = 0; i < length; i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);


                                OffersCategoryModel catModel = new OffersCategoryModel();
                                catModel.setTitle(jsonObject.getString("TITLE"));
                                catModel.setKey(jsonObject.getString("KEY"));
                                catModel.setValue(jsonObject.getString("VALUE"));



                       /* for(int j=0;j<arrayLength;j++) {
                            OffersSubCategory subData = new OffersSubCategory();
                            JSONObject subObject=jArray.getJSONObject(j);
                            subData.setTitle(subObject.getString("TITLE"));
                            subData.setKey(subObject.getString("KEY"));
                            subData.setValue(subObject.getString("VALUE"));



                            offersSubData.add(subData);

                            System.out.println(jsonObject.getString("TITLE")+"DATA: FLUSH"+subData.toString());



                        }*/
                                //catModel.setSubCat(offersSubData);
                                if (jsonObject.has("SUB_NAV")) {
                                    JSONArray jArray = jsonObject.getJSONArray("SUB_NAV");
                                    int arrayLength = jArray.length();
                                    catModel.setSubCat(jsonObject.getJSONArray("SUB_NAV").toString());
                                }
                                else
                                {
                                catModel.setSubCat("[]");

                                }




                                offersMainData.add(catModel);
                            }

                            /*dataArrayAll = mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("ITEMS").toString();*/
                        }
                        dataArrayAll = mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("ITEMS").toString();
                        System.out.println("call"+dataArrayAll);
                        if(mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").has("SORT_NAV")) {
                            int sortNavLength = mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("SORT_NAV").length();
                            for (int k = 0; k < sortNavLength; k++) {
                                JSONObject jDataSort = mMembersJSON.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("SORT_NAV").getJSONObject(k);
                                SortOffersModel sModel=new SortOffersModel();
                                sModel.setTitle(jDataSort.getString("TITLE"));
                                sModel.setKey(jDataSort.getString("KEY"));
                                sModel.setValue(jDataSort.getString("VALUE"));


                                sortArray.add(sModel);
                            }
                        }

                    }
                    setupViewPager(viewPager);


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








        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_catmenu, menu);



        return true;
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







    /*private void filterPopUp() {
        filterDialog = new Dialog(this);
        filterDialog.setCancelable(true);
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filterDialog.setContentView(R.layout.dialog_cat_filter);

        final ListView offersFilter = (ListView) filterDialog.findViewById(R.id.countryList);
        offersFilter.setAdapter(filterAdapter);





        offersFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {








            }

        });







        filterDialog.show();
        filterDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }*/



}
