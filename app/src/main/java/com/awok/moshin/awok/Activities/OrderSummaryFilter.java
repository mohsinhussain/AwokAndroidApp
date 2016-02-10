package com.awok.moshin.awok.Activities;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.awok.moshin.awok.Adapters.OrderHistoryStatusAdapter;
import com.awok.moshin.awok.Adapters.OrderSummaryCustomAdapter;
import com.awok.moshin.awok.Models.OrderStatusModel;
import com.awok.moshin.awok.Models.OrderSummary;
import com.awok.moshin.awok.NetworkLayer.APIClient;
import com.awok.moshin.awok.NetworkLayer.AsyncCallback;
import com.awok.moshin.awok.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderSummaryFilter extends AppCompatActivity {
    private Button spinnerOrder, statusAll;
    private TextView       dateBy;
    private DatePickerDialog fromDatePickerDialog;
    private RecyclerView mRecyclerView;
    private List<OrderStatusModel> overViewList = new ArrayList<OrderStatusModel>();
    private RecyclerView.Adapter mAdapter;
String mainStatus;
    String mainId="";
    private Button apply;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final android.support.v7.app.ActionBar ab = getSupportActionBar();
        // ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.back_button);
        ab.setTitle("Filter Orders");
        spinnerOrder = (Button) findViewById(R.id.orderStatus);
        statusAll = (Button) findViewById(R.id.showall);
apply=(Button)findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();


                timeStamp(spinnerOrder.getText().toString());

                System.out.println("Status" + " " + mainStatus + " " + "id" + " " + mainId + " " + "From" + " " + timeStamp(spinnerOrder.getText().toString()) + " " + "To" + " " + timeStamp(statusAll.getText().toString()));
                i.putExtra("statusId", mainId);
                //i.putExtra("From",timeStamp(spinnerOrder.getText().toString()));
                //i.putExtra("To", timeStamp(statusAll.getText().toString()));


                i.putExtra("From",dateFetch(spinnerOrder.getText().toString()));
                i.putExtra("To", dateFetch(statusAll.getText().toString()));



                setResult(RESULT_OK, i);

                finish();


            }
        });
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = df.format(c.getTime());

        System.out.println("Current time => " + formattedDate);
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.MONTH, 0); // Months are 0-based!
        currentDate.set(Calendar.DAY_OF_MONTH, 1); // Clearer than DATE
        currentDate.set(Calendar.YEAR, 2015);
        SimpleDateFormat dg = new SimpleDateFormat("dd MMM yyyy");
        String formattedDateSpin = dg.format(currentDate.getTime());

        //statusAll.setText(formattedDateSpin);
        statusAll.setText(formattedDate);
        spinnerOrder.setText(formattedDateSpin);
//spinnerOrder.setText(formattedDate);
        spinnerOrder.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                setDateTimeField();
            }
        });
statusAll.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        setDateTimeFieldTo();
    }
});
        mRecyclerView = (RecyclerView) findViewById(R.id.overViewRecyclerView);

        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OrderHistoryStatusAdapter(OrderSummaryFilter.this,overViewList);




        ConnectivityManager connMgr = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new APIClient(this, getApplicationContext(),  new GetOrderHistoryStatus()).orderStatusCallBack();

        } else {



            Snackbar snackbar =Snackbar.make(findViewById(android.R.id.content), "No network connection available", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED);

            View snackbarView = snackbar.getView();

            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
//timeStamp();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_summary_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            //noinspection SimplifiableIfStatement
            case android.R.id.home:
                //mDrawerLayout.openDrawer(GravityCompat.START);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setDateTimeFieldTo() {


        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                DateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");
                newDate.set(year, monthOfYear, dayOfMonth);
                //  fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
                statusAll.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromDatePickerDialog.show();

       /* toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }*/

        // },

        // newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }




    private void setDateTimeField() {


        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                DateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");
                newDate.set(year, monthOfYear, dayOfMonth);
              //  fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
                spinnerOrder.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromDatePickerDialog.show();

       /* toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }*/

       // },

           // newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


public String dateFetch(String dateValue)
{
    DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
    Date date = null;
    Timestamp timeStampDate=null;
    try {
        date = (Date)formatter.parse(dateValue);
        timeStampDate = new Timestamp(date.getTime());
    } catch (ParseException e) {
        e.printStackTrace();
    }
    SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
    String s=sdf.format(date.getTime());
    System.out.println("Today is S" + s);
    return s;
}



    /*@Override
    public void onClick(View view) {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if(view == toDateEtxt) {
            toDatePickerDialog.show();
        }
    }*/
public String timeStamp(String dateValue)
{
    //String str_date="31 Oct 2015";
    DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
    Date date = null;
    Timestamp timeStampDate=null;
    try {
        date = (Date)formatter.parse(dateValue);
         timeStampDate = new Timestamp(date.getTime());
    } catch (ParseException e) {
        e.printStackTrace();
    }


    System.out.println("Today is " + date.getTime());
    System.out.println("Today is " + timeStampDate);
    System.out.println("Today is " + date.getTime() / 1000L);
    String valueDate=String.valueOf(date.getTime() / 1000L);
return valueDate;
}

    public void getData(String status,String id) {

        mainStatus=status;
        mainId=id;
        System.out.println("STATUS"+mainStatus);
        System.out.println("ID" + mainId);
    }


    public class GetOrderHistoryStatus extends AsyncCallback {
        public void onTaskComplete(String response) {
            try {

                System.out.println(response);

                JSONObject jsonObjectData;
                jsonObjectData=new JSONObject(response);


/*if(jsonObjectData.getString("status").equals("0"))
{

}
                else
{*/
JSONArray data=jsonObjectData.getJSONObject("OUTPUT").getJSONObject("DATA").getJSONArray("ORDER_STATUSES");
    for(int i=0;i<data.length();i++)
    {
        OrderStatusModel orderModel=new OrderStatusModel();
        JSONObject dataInner=data.getJSONObject(i);
//        String status=dataInner.getString("name");
        orderModel.setStatusType(dataInner.getString("NAME"));
        orderModel.setStatusId(dataInner.getString("ID"));
        orderModel.setIsSelected(false);
        overViewList.add(orderModel);
    }
//}



                if(getApplicationContext()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    //progressLayout.startAnimation(animation);
                }

                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(findViewById(android.R.id.content), "Test data could not be loaded", Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(Color.RED)
                        .show();
                if(getApplicationContext()!=null){
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                    //progressLayout.startAnimation(animation);
                }
               // progressLayout.setVisibility(View.GONE);

            }
        }
        @Override
        public void onTaskCancelled() {
        }
        @Override
        public void onPreExecute() {
            // TODO Auto-generated method stub
//            if(!mSwipeRefreshLayout.isRefreshing()){
            //progressLayout.setVisibility(View.VISIBLE);
//            }

        }
    }

}