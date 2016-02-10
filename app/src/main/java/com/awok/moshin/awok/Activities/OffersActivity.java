package com.awok.moshin.awok.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.awok.moshin.awok.Adapters.OffersAdapter;
import com.awok.moshin.awok.Adapters.OffersRecyclerAdapter;
import com.awok.moshin.awok.Adapters.PaymentPickAdapter;
import com.awok.moshin.awok.Fragments.DividerItemDecoration;
import com.awok.moshin.awok.Models.OffersModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;
import com.awok.moshin.awok.Util.LinearLayoutManager;
import com.awok.moshin.awok.Util.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OffersActivity extends AppCompatActivity {
RecyclerView recyclerView;
    OffersRecyclerAdapter mAdapter;
    ArrayList<OffersModel> page=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Offers");


        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
          recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
         recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter=new OffersRecyclerAdapter(OffersActivity.this, page, getApplicationContext());
        recyclerView.setAdapter(mAdapter);



        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        System.out.print("KEY ");
                        String key=page.get(position).getKey();
                        String value=page.get(position).getValue();
                        Snackbar.make(findViewById(android.R.id.content), "KEY"+key+"value"+value, Snackbar.LENGTH_LONG)
                                .setActionTextColor(Color.RED)
                                .show();





                        if(page.get(position).getView().equals("Y"))
                        {
                            Intent i=new Intent(OffersActivity.this,CategoryWebView.class);
                            i.putExtra("data","http://"+page.get(position).getLink());
                            startActivity(i);
                        }
                        else
                        {
                            Intent i=new Intent(OffersActivity.this,OffersCategoryActivity.class);
                            i.putExtra("data",key+"="+value);
                            i.putExtra("title",page.get(position).getName());
                            if(value.equals("bundle-offers"))
                            {
                                i.putExtra("type","BP");
                            }
                            else
                            {
                                i.putExtra("type","SP");
                            }

                            startActivity(i);
                        }


                    }
                })
        );
          /*offersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                              @Override
                                              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                             *//*     TextView name = (TextView) view.findViewById(R.id.title);




                                                  String key=page.get(position).getKey();
                                                  String value=page.get(position).getValue();*//*

                                                  System.out.print("KEY ");



                                              }
                                          });*/


     //   offersList.setAdapter(offersAdapter);
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new APIClient(OffersActivity.this, getApplicationContext(),  new OffersPage()).getOffers();
            //new APIClient(this, getApplicationContext(),  new GetAddressCallback()).getPrimaryAddressAPICall("55f6a9e52f17f64a9b5f5ce5");
        } else {

            Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_offers, menu);
        return true;
    }






    public class OffersPage extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {
                JSONObject obj = new JSONObject(response);
                System.out.println("offers"+obj);
                if(obj.getJSONObject("STATUS").getInt("CODE")==200) {
                  /*  Snackbar.make(findViewById(android.R.id.content), obj.getJSONObject("STATUS").getString("MESSAGE").toString(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();*/


                    JSONArray offersArray=obj.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("OFFERS");
                    int length=obj.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("OFFERS").length();




                    for(int i=0;i<length;i++)
                    {
OffersModel dataOffers=new OffersModel();
                        JSONObject jsonData=offersArray.getJSONObject(i);
                        dataOffers.setName(jsonData.getString("TITLE"));
                        dataOffers.setDesc(jsonData.getString("DESCRIPTION"));
                        dataOffers.setImage(jsonData.getString("IMAGE"));
                        dataOffers.setKey(jsonData.getString("KEY"));
                        dataOffers.setValue(jsonData.getString("VALUE"));
                        if(jsonData.has("WEB_VIEW")) {
                            dataOffers.setView(jsonData.getString("WEB_VIEW"));
                            dataOffers.setLink(jsonData.getString("LINK"));
                        }

                        page.add(dataOffers);
                    }


                    mAdapter.notifyDataSetChanged();

                }
                else{

                }

            } catch (JSONException e) {
                e.printStackTrace();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                //mDrawerLayout.openDrawer(GravityCompat.START);
                onBackPressed();
                return true;




        }

        return super.onOptionsItemSelected(item);
    }



}
