package com.awok.moshin.awok.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.awok.moshin.awok.R;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Set;

public class CurrencySelectionActivity extends AppCompatActivity {
    ListView listCurrency;
    Set<Currency> availableCurrenciesSet;
    List<Currency> availableCurrenciesList;
    ArrayAdapter<Currency> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_selection);
        listCurrency = (ListView)findViewById(R.id.currencylist);


        availableCurrenciesSet =
                Currency.getAvailableCurrencies();

        availableCurrenciesList = new ArrayList<Currency>(availableCurrenciesSet);
        adapter = new ArrayAdapter<Currency>(
                this,
                android.R.layout.simple_list_item_1,
                availableCurrenciesList);
        listCurrency.setAdapter(adapter);

        listCurrency.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Currency currency = (Currency) parent.getItemAtPosition(position);
                String currencyCode = currency.getCurrencyCode();
                String displayName = currency.getDisplayName();
                String symbol = currency.getSymbol();

                Toast.makeText(CurrencySelectionActivity.this,
                        displayName + "\n" +
                                currencyCode + "\n" +
                                symbol,
                        Toast.LENGTH_LONG).show();
            }});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_currency_selection, menu);
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
