package com.awok.moshin.awok.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.awok.moshin.awok.Adapters.DisputeListAdapter;
import com.awok.moshin.awok.Models.Checkout;
import com.awok.moshin.awok.Models.DisputeChildModel;
import com.awok.moshin.awok.Models.DisputeExpandableListModel;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DisputeDetails extends AppCompatActivity {
String cart_id;
    private TextView disputeNo,disputeStatus,rem;
    private LinearLayout progressLayout,main;
    private String is_received,goods_condition,ship_goods_back,dispute_request,disputeId;
    String additional_details,user_id,send_status,send_productName,send_price,send_currency,prod_id;
    private Button modify,cancel;
private LinearLayout solutionLay,buttonLay;
    private Button   escalate;
    private TextView order_no,status,reminder,received_order,return_order,dispute_opened,total,solutionTxt,refund_requested,requestDetailsTxt;

    private List<DisputeExpandableListModel> disputeMainModel = new ArrayList<DisputeExpandableListModel>();
    private ArrayList<DisputeChildModel> disputeChildModel = new ArrayList<DisputeChildModel>();
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> laptopCollection;
    ExpandableListView expListView;

    private String is_Cancel,is_Closed,is_Escalate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispute_details);

        cancel=(Button)findViewById(R.id.cancel_dispute);
disputeNo=(TextView)findViewById(R.id.disputeNo);
        disputeStatus=(TextView)findViewById(R.id.disputeStatus);
        rem=(TextView)findViewById(R.id.rem);
        solutionLay=(LinearLayout)findViewById(R.id.solutionLay);
main=(LinearLayout)findViewById(R.id.main);
        solutionLay=(LinearLayout)findViewById(R.id.solutionLay);
        main.setVisibility(View.GONE);
//modify=(Button)findViewById(R.id.modify);
        escalate=(Button)findViewById(R.id.escalate);
   /*     modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisputeDetails.this, ModifyDispute.class);
                i.putExtra("user_id", user_id);
                i.putExtra("dispute_id", disputeId);
                i.putExtra("is_received", is_received);
                i.putExtra("goods_condition", goods_condition);
                i.putExtra("ship_goods_back", ship_goods_back);
                i.putExtra("dispute_request", dispute_request);
                i.putExtra("additional_details", additional_details);
                i.putExtra("send_status", send_status);
                i.putExtra("send_productName", send_productName);
                i.putExtra("send_currency", send_currency);
                i.putExtra("send_price", send_price);
                i.putExtra("prod_id", prod_id);
                i.putExtra("cart_id", cart_id);


                startActivity(i);
            }
        });
*/
        buttonLay=(LinearLayout)findViewById(R.id.buttonLay);

        order_no=(TextView)findViewById(R.id.order_no);
        status=(TextView)findViewById(R.id.status);
        reminder=(TextView)findViewById(R.id.reminder);
        received_order=(TextView)findViewById(R.id.received_order);
        return_order=(TextView)findViewById(R.id.return_order);
        dispute_opened=(TextView)findViewById(R.id.dispute_opened);
        total=(TextView)findViewById(R.id.total);
        solutionTxt=(TextView)findViewById(R.id.solutionTxt);
        refund_requested=(TextView)findViewById(R.id.refund_requested);
        requestDetailsTxt=(TextView)findViewById(R.id.requestDetailsTxt);



        progressLayout=(LinearLayout)findViewById(R.id.progressLayout);
        progressLayout.setVisibility(View.GONE);

cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            new APIClient(DisputeDetails.this, getApplicationContext(), new GetDisputeCancel()).disputeCancelCallBack(disputeId);


        } else {




            Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();



        }
    }
});
        Intent i=getIntent();
        cart_id=i.getStringExtra("cart_id");
        prod_id=i.getStringExtra("prod_id");
        System.out.println("pid" + prod_id);



        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            new APIClient(DisputeDetails.this, getApplicationContext(), new GetDisputeDetails()).disputeDetailsCallBack(cart_id);


        } else {




            Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();



        }

        createGroupList();

        createCollection();

        expListView = (ExpandableListView) findViewById(R.id.list);

        /*final ExpandableListAdapter expListAdapter = new DisputeListAdapter(
                this, groupList, laptopCollection);*/
        final ExpandableListAdapter expListAdapter = new DisputeListAdapter(
                this, disputeMainModel);
        //expListView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

        expListView.setAdapter(expListAdapter);
   //     expListView.requestLayout();



        setListViewHeightBasedOnChildren(expListView);









        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                System.out.println("LIST "+groupPosition);
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });

       // setListViewHeight(expListView,0);

//expListView.setNestedScrollingEnabled(false);
        setGroupIndicatorToRight();

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });
    }

    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add("HP");
        groupList.add("Dell");
        groupList.add("Lenovo");
        groupList.add("Sony");
        groupList.add("HCL");
        groupList.add("Samsung");
    }

    private void createCollection() {
        // preparing laptops collection(child)
        String[] hpModels = { "HP Pavilion G6-2014TX", "ProBook HP 4540",
                "HP Envy 4-1025TX" };
        String[] hclModels = { "HCL S2101", "HCL L2102", "HCL V2002" };
        String[] lenovoModels = { "IdeaPad Z Series", "Essential G Series",
                "ThinkPad X Series", "Ideapad Z Series" };
        String[] sonyModels = { "VAIO E Series", "VAIO Z Series",
                "VAIO S Series", "VAIO YB Series" };
        String[] dellModels = { "Inspiron", "Vostro", "XPS" };
        String[] samsungModels = { "NP Series", "Series 5", "SF Series" };

        laptopCollection = new LinkedHashMap<String, List<String>>();

        for (String laptop : groupList) {
            if (laptop.equals("HP")) {
                loadChild(hpModels);
            } else if (laptop.equals("Dell"))
                loadChild(dellModels);
            else if (laptop.equals("Sony"))
                loadChild(sonyModels);
            else if (laptop.equals("HCL"))
                loadChild(hclModels);
            else if (laptop.equals("Samsung"))
                loadChild(samsungModels);
            else
                loadChild(lenovoModels);

            laptopCollection.put(laptop, childList);
        }
    }

    private void loadChild(String[] laptopModels) {
        childList = new ArrayList<String>();
        for (String model : laptopModels)
            childList.add(model);
    }

    private void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dispute_details, menu);
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

    public class GetDisputeCancel extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {


                System.out.println(response);

                JSONObject jsonObjectData;
                jsonObjectData = new JSONObject(response);
                System.out.println(jsonObjectData.toString());


               /* JSONArray jsonArray;

                disputeId=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("id");
                String disputeStatusTxt=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("dispute_status");
                String reminder=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("note");



                is_received=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("is_received");
                goods_condition=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("goods_condition");
                ship_goods_back=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("ship_goods_back");
                dispute_request=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("dispute_request");
                additional_details=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("additional_details");
                user_id=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("user_id");

                //=additional_details
                send_status=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("dispute_status");
                send_productName=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getJSONObject("product").getString("product_name");
                send_currency=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getJSONObject("product").getString("currency");
                send_price=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getJSONObject("product").getString("product_price");


                disputeNo.setText(disputeId);
                disputeStatus.setText(disputeStatusTxt);
                rem.setText(reminder);*/

                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);

                    progressLayout.startAnimation(animation);
                }
                progressLayout.setVisibility(View.GONE);
                main.setVisibility(View.VISIBLE);

            } catch (JSONException e) {
                e.printStackTrace();
                /*Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();*/


                Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Data could not be loaded", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED);

                View snackbarView = snackbar.getView();

                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);

                    progressLayout.startAnimation(animation);
                }
                progressLayout.setVisibility(View.GONE);
                /*if (mSwipeRefreshLayout!=null && mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }*/
            }
        }

        @Override
        public void onTaskCancelled() {
        }

        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
//            if(!mSwipeRefreshLayout.isRefreshing()){
            progressLayout.setVisibility(View.VISIBLE);
//            }

        }
    }

    public class GetDisputeDetails extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {


                System.out.println(response);

                JSONObject jsonObjectData;
                jsonObjectData = new JSONObject(response);
                System.out.println(jsonObjectData.toString());


                JSONArray jsonArray;

                disputeId=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("id");
                String disputeStatusTxt=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("dispute_status");
            //    String reminder=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("note");



                is_received=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("is_received");
                //goods_condition=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("goods_condition");
                ship_goods_back=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("ship_goods_back");
                dispute_request=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("dispute_request");
                additional_details=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("additional_details");
                user_id=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("user_id");

                //=additional_details
                send_status=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("dispute_status");
                send_productName=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getJSONObject("product").getString("product_name");
                send_currency=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getJSONObject("product").getString("currency");
                send_price=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getJSONObject("product").getString("product_price");


                disputeNo.setText(disputeId);
                disputeStatus.setText(disputeStatusTxt);
        //        rem.setText(reminder);

is_Cancel=(jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("is_cancel"));
                is_Closed=(jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("is_closed"));
                is_Escalate=jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("is_escalate");
order_no.setText(jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getJSONObject("order").getString("order_number"));
                status.setText(jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("dispute_status"));
                reminder.setText(jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("note"));
                received_order.setText(jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("is_received"));
                return_order.setText(jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("ship_goods_back").toString());
                dispute_opened.setText(jsonObjectData.getJSONObject("data").getJSONObject("server").getJSONObject("disputes").getString("created_at"));
                total.setText(send_currency+" "+send_price);
                requestDetailsTxt.setText(additional_details);


if (is_Closed.equals("false"))
{
    solutionLay.setVisibility(View.GONE);
}
                else
{
    solutionLay.setVisibility(View.VISIBLE);
}


                if (is_Cancel.equals("true"))
                {
                    buttonLay.setVisibility(View.GONE);
                }
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


                if (is_Escalate.equals("true"))
                {
                    escalate.setVisibility(View.GONE);
                }





                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);

                    progressLayout.startAnimation(animation);
                }
                progressLayout.setVisibility(View.GONE);
                main.setVisibility(View.VISIBLE);

            } catch (JSONException e) {
                e.printStackTrace();
                /*Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();*/


                Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "Data could not be loaded", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED);

                View snackbarView = snackbar.getView();

                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
                if (getApplicationContext() != null) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);

                    progressLayout.startAnimation(animation);
                }
                progressLayout.setVisibility(View.GONE);
                /*if (mSwipeRefreshLayout!=null && mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }*/
            }
        }

        @Override
        public void onTaskCancelled() {
        }

        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
//            if(!mSwipeRefreshLayout.isRefreshing()){
            progressLayout.setVisibility(View.VISIBLE);
//            }

        }
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




    /*public static boolean setListViewHeightBasedOnItems(ExpandableListView listView) {

        ExpandableListView listAdapter = (ExpandableListView) listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }*/

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


}








