package com.sajagjain.sajagjain.majorproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RestaurantInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_information);
        String shareUrl=getIntent().getStringExtra("shareURL");
        WebView webView= (WebView)findViewById(R.id.restaurantdescriptionwebview);
        webView.loadUrl(shareUrl);

        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.canGoBack();

    }
}
