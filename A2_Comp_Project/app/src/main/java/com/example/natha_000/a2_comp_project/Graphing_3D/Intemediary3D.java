package com.example.natha_000.a2_comp_project.Graphing_3D;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.natha_000.a2_comp_project.R;

/**
 * This class holds the activity that allows the user to input their 3D function
 * And allows them to choose the way in which they wih to view it.
*/
public class Intemediary3D extends AppCompatActivity {

    /**
     * Textstring used so that the other activities can get the information provided by this activity
    */
    public final static String EXTRA_MESSAGE = "com.example.natha_000.a2_comp_project.Graphing_3D.MESSAGE";
    public final static String ERROR_MESSAGE = "com.example.natha_000.a2_comp_project.Graphing_3D.ERROR";

    /**
     * Sets up the general start up of the activity
     * @param savedInstanceState The information sent from the previous activity
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intemediary3d);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String message = intent.getStringExtra(ERROR_MESSAGE);
        if (message == "error") {
            Toast.makeText(this,"Please enter a valid function",Toast.LENGTH_LONG);
        }
    }

    /**
     * Sends the user to the activity that contains the projection of the 3D graph
     * @param view The view which called this function
    */
    public void goTo3D(View view) {
        //Sets up the new activity
        Intent intent = new Intent(this, Main3DActivity.class);

        //Get the desired function and add it to be sent with the activity
        EditText editText = (EditText) findViewById(R.id.funcText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);

        //Send the user to the activity
        startActivity(intent);
    }

    /**
     * Sends the user to the activity that contains the VR 3D graph
     * @param view The view which called this function
    */
    public void goTo3DVR(View view) {
        //Sets up the new activity
        Intent intent = new Intent(this, Main3DVRActivity.class);

        //Get the desired function and add it to be sent with the activity
        EditText editText = (EditText) findViewById(R.id.funcText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);

        //Send the user to the activity
        startActivity(intent);
    }

}
