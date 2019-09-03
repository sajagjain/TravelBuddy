package com.sajagjain.sajagjain.majorproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PlaceInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_information);
        String placename=getIntent().getStringExtra("placeName");
        WebView webView= (WebView)findViewById(R.id.placedescriptionwebview);
        webView.loadUrl("https://en.wikipedia.org/wiki/"+placename);

//        webView.loadUrl("https://en.wikipedia.org/w/api.php?action=parse&page=Delhi&prop=text&format=json&mobileformat=true");
//        webView.loadUrl("https://maps.googleapis.com/maps/api/place/textsearch/json?query=point_of_interest_Khurai_madhya_pradesh&key=AIzaSyDsO0IyfdE_36fEZUEQIU-Vhcg4cRi8nzE");
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
    }
}
