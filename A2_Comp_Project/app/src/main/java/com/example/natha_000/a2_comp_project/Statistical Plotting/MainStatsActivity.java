package com.example.natha_000.a2_comp_project;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_stats);
        // Generation of random data, will be changed to get data from other fragments
        //GCSE Question
        new StatsClasses(0,10,10);
        new StatsClasses(10,20,18);
        new StatsClasses(20,25,14);
        new StatsClasses(25,30,10);
        new StatsClasses(30,50,8);

        // end of random generation
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new SketchHistogram();
        fragmentManager.beginTransaction()
                .replace(R.id.container1, fragment)
                .commit();
    }

    public void histogram(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new SketchHistogram();
        fragmentManager.beginTransaction()
                .replace(R.id.container1, fragment)
                .commit();
    }

    public void cumFreq(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new SketchCumFreq();
        fragmentManager.beginTransaction()
                .replace(R.id.container1, fragment)
                .commit();
    }

    public void dataTable(View view) {
        StatsClasses.reset();
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new StatsDataTable();
        fragmentManager.beginTransaction()
                .replace(R.id.container1, fragment)
                .commit();
    }

    public void onDestroy() {
        super.onDestroy();
        StatsClasses.reset();
    }

}