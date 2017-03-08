package com.example.natha_000.a2_comp_project.Graphing_3D;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.natha_000.a2_comp_project.R;

/**
 * This class is used as a the container for the fragment used to draw a 2D
 * Projection of the 3D graph
*/
public class Main3DActivity extends AppCompatActivity{

    /**
     * This is a string into which we put the function text so that it can be used by the attached fragment
    */
    static String functext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_3d);

        // Put the sent in value from the main activity into the variable
        Intent intent = getIntent();
        functext = intent.getStringExtra(Intemediary3D.EXTRA_MESSAGE);

        // Set up the fragment that draws the 3D graph
        // and start it within the activity
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new Sketch3DGraph();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
