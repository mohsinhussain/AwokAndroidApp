package com.awok.moshin.awok.Activities;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.awok.moshin.awok.Models.DisputeChildModel;
import com.awok.moshin.awok.Models.DisputeExpandableListModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
  /*  SparseArray<Group> mGroups = new SparseArray<Group>();
    private List<DisputeExpandableListModel> disputeMainModel = new ArrayList<DisputeExpandableListModel>();
    private ArrayList<DisputeChildModel> disputeChildModel = new ArrayList<DisputeChildModel>();
    ExpandableListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        createData();
        mListView = (ExpandableListView) findViewById(R.id.list);
        *//*MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
                mGroups);
        mListView.setAdapter(adapter);
        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });*//*
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            new APIClient(Main2Activity.this, getApplicationContext(), new GetDisputeDetails()).disputeDetailsCallBack("5641ae9c1a7da7640300003a");


        } else {




            Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();



        }


        MyExpandableListAdapter expListAdapter = new MyExpandableListAdapter(this,
                disputeMainModel);

        mListView.setAdapter(expListAdapter);






        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                System.out.println("LIST " + groupPosition);

                setListViewHeight(parent,groupPosition);
                return false;
            }
        });

    }
    public void createData() {
        for (int j = 0; j < 5; j++) {
            Group group = new Group("Test " + j);
            for (int i = 0; i < 5; i++) {
                group.children.add("Sub Item" + i);
            }
            mGroups.append(j, group);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    public class GetDisputeDetails extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {


                System.out.println(response);

                JSONObject jsonObjectData;
                jsonObjectData = new JSONObject(response);
                System.out.println(jsonObjectData.toString());


                JSONArray jsonArray;


                String disputeStatusTxt=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("dispute_status");
                //    String reminder=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("note");









                JSONArray data=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getJSONArray("response");
                for (int i=0;i<data.length();i++)
                {

                    DisputeExpandableListModel disputeDataModel=new DisputeExpandableListModel();
                    DisputeChildModel disputeDataChild=new DisputeChildModel();


                    JSONObject responseData=data.getJSONObject(i);
                    //disputeDataModel.setAction(responseData.getString("action"));
                    disputeDataModel.setDate(responseData.getString("created_at"));
                    disputeDataModel.setInitiator(responseData.getString("initiator"));
                    //disputeDataModel.setIsReceived(responseData.getString("is_received"));
                    //disputeDataModel.setNote(responseData.getString("details"));
                    //disputeDataModel.setRefundAmount(responseData.getString("refund_amount"));
                    //disputeDataModel.setShipGoods(responseData.getString("ship_goods_back"));


                    disputeDataChild.setAction(responseData.getString("action"));

                    disputeDataChild.setInitiator(responseData.getString("initiator"));
                    disputeDataChild.setIsReceived(responseData.getString("is_received"));
                    disputeDataChild.setNote(responseData.getString("details"));
                    disputeDataChild.setRefundAmount(responseData.getString("refund_amount"));
                    disputeDataChild.setShipGoods(responseData.getString("ship_goods_back"));



                    disputeChildModel.add(disputeDataChild);



                    disputeDataModel.setItems(disputeChildModel);


                    disputeMainModel.add(disputeDataModel);




                }








                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);


                }



            } catch (JSONException e) {
                e.printStackTrace();
                *//*Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();*//*


                Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Data could not be loaded", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED);

                View snackbarView = snackbar.getView();

                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);


                }

                *//*if (mSwipeRefreshLayout!=null && mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }*//*
            }
        }

        @Override
        public void onTaskCancelled() {
        }

        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
//            if(!mSwipeRefreshLayout.isRefreshing()){

//            }

        }
    }*/

}
