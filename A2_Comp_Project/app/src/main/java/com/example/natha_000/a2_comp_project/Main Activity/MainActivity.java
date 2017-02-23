package com.example.natha_000.a2_comp_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
        Intent intent = new Intent(this, Intemediary3D.class);
        startActivity(intent);
    }

    public void goToStats(View view) {
        Intent intent = new Intent(this, MainStatsActivity.class);
        startActivity(intent);
    }

    public void goToTabbedStats(View view) {
        Intent intent = new Intent(this, TabbedStatsActivity.class);
        startActivity(intent);
    }
}
