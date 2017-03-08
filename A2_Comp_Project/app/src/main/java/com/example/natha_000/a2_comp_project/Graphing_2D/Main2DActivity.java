package com.example.natha_000.a2_comp_project.Graphing_2D;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.natha_000.a2_comp_project.R;

/**
 * This contains the activity responsible for the plotting of the 2D
 * graph using the Desmos API
*/
public class Main2DActivity extends AppCompatActivity{

    // Sets up the general start up of the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2d);

        // Get the webview and Load the Desmos API into it
        WebView myWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("file:///android_asset/Desmos.html");
    }
}
