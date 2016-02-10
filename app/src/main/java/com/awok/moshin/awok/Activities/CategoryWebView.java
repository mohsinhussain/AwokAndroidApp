package com.awok.moshin.awok.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.awok.moshin.awok.R;

public class CategoryWebView extends AppCompatActivity {

    private WebView webView;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_web_view);
        data=getIntent().getStringExtra("data");
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new MyWebViewClient());

        //String url = "http://javatechig.com";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(data);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
