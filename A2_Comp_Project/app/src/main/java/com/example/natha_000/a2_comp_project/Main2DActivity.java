package com.example.natha_000.a2_comp_project;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class Main2DActivity extends AppCompatActivity{

    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2d);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); */
        fragmentManager = getSupportFragmentManager();

//        Button plotter = (Button) findViewById(R.id.plot_button);
//        plotter.setOnClickListener(this);
        WebView myWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("file:///android_asset/Desmos.html");
        //myWebView.loadUrl("https://www.desmos.com/calculator");
    }

    public void sentdText(View view){
        /*Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.function);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);*/
    }



//    @Override
//    public void onClick(View v) {
//        Fragment lel = new BlankFragment();
//        Bundle data = new Bundle();
//        EditText editText = (EditText) findViewById(R.id.function);
//        String message = editText.getText().toString();
//        data.putString("function",message);
//        lel.setArguments(data);
//        fragmentManager.beginTransaction().replace(R.id.fragmentcontainer, lel).commit();
//    }
}
