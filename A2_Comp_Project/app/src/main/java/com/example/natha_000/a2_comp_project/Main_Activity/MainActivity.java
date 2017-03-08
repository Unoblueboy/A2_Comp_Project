package com.example.natha_000.a2_comp_project.Main_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.natha_000.a2_comp_project.Graphing_2D.Main2DActivity;
import com.example.natha_000.a2_comp_project.Graphing_3D.Intemediary3D;
import com.example.natha_000.a2_comp_project.Graphing_Stats.MainStatsActivity;
import com.example.natha_000.a2_comp_project.Graphing_Stats.TabbedStatsActivity;
import com.example.natha_000.a2_comp_project.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Sends the user to the activity that contains the 2D graph
     * @param view The view which called this function
     */
    public void goTo2D(View view) {
        Intent intent = new Intent(this, Main2DActivity.class);
        startActivity(intent);
    }

    /**
     * Sends the user to the activity that contains the 3D graph
     * @param view The view which called this function
     */
    public void goTo3D(View view) {
        Intent intent = new Intent(this, Intemediary3D.class);
        startActivity(intent);
    }

    /**
     * Sends the user to the activity that contains the Statistical Plotting
     * @param view The view which called this function
     */
    public void goToStats(View view) {
        Intent intent = new Intent(this, MainStatsActivity.class);
        startActivity(intent);
    }

    /**
     * Sends the user to the activity that contains the Statistical Plotting V2
     * @param view The view which called this function
     */
    public void goToTabbedStats(View view) {
        Intent intent = new Intent(this, TabbedStatsActivity.class);
        startActivity(intent);
    }
}
