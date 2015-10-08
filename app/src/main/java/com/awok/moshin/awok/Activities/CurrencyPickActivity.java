package com.awok.moshin.awok.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.awok.moshin.awok.Adapters.CurrencyAdapter;
import com.awok.moshin.awok.Adapters.HotDealsAdapter;
import com.awok.moshin.awok.Models.CurrencyModel;
import com.awok.moshin.awok.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

public class CurrencyPickActivity extends AppCompatActivity {
    ListView listCurrency;
    List<CurrencyModel> availableCurrenciesList=new ArrayList<CurrencyModel>();
    CurrencyAdapter mAdapter;










    String[] array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_pick);
        listCurrency = (ListView)findViewById(R.id.currencylist);
        array=getResources().getStringArray(R.array.currency);
        for(int i=0;i<array.length;i++) {
            CurrencyModel cm = new CurrencyModel();
            cm.setCurrencyName(array[i]);
            availableCurrenciesList.add(cm);

        }
        //availableCurrenciesList = Arrays.asList(getResources().getStringArray(R.array.currency));
        mAdapter = new CurrencyAdapter(getApplicationContext(), availableCurrenciesList,"AED");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CurrencyPickActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, array);
        listCurrency.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_currency_pick, menu);
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
}
