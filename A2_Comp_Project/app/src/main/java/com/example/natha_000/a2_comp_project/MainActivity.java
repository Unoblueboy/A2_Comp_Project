package com.example.natha_000.a2_comp_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void goTo2D(View view) {
        Intent intent = new Intent(this, Main2DActivity.class);
        startActivity(intent);
    }

    public void goTo3D(View view) {
        Intent intent = new Intent(this, Main3DActivity.class);
        startActivity(intent);
    }

    public void goToStats(View view) {
        Intent intent = new Intent(this, MainStatsActivity.class);
        startActivity(intent);
    }
}
