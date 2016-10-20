package com.example.natha_000.a2_comp_project;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainStatsActivity extends AppCompatActivity {

    private LinearLayout ll;
    private Integer counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ll = (LinearLayout) findViewById(R.id.main_linear_layout);
        counter = 1;
    }

    public void Add_Row(View view) {

        //Create a holding horizontal layout linear layout (weight_sum: 10)
        LinearLayout tl = new LinearLayout(this);
        LinearLayout.LayoutParams tlparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tl.setWeightSum(10.0f);
        tl.setOrientation(LinearLayout.HORIZONTAL);

        //Create a relative layout (gravity: center, weight: 5)

        RelativeLayout rl = new RelativeLayout(this);
        rl.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams rlparams = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,5.0f);

        //Create new lower bound, text, upper bound to relative layout

        EditText lb = new EditText(this);
        lb.setId(View.generateViewId());
        lb.setInputType(InputType.TYPE_CLASS_NUMBER);
        lb.setEms(3);
        RelativeLayout.LayoutParams lbparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lbparams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lbparams.addRule(RelativeLayout.ALIGN_PARENT_START);

        TextView ineq = new TextView(this);
        ineq.setId(View.generateViewId());
        ineq.setText("<x<");
        if (Build.VERSION.SDK_INT < 23) {
            ineq.setTextAppearance(this, android.R.style.TextAppearance_Large);
        } else {
            ineq.setTextAppearance(android.R.style.TextAppearance_Large);
        }
        RelativeLayout.LayoutParams ineqparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ineqparams.addRule(RelativeLayout.END_OF, lb.getId());
        ineqparams.addRule(RelativeLayout.CENTER_VERTICAL);

        EditText ub = new EditText(this);
        ub.setInputType(InputType.TYPE_CLASS_NUMBER);
        ub.setEms(3);
        RelativeLayout.LayoutParams ubparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ubparams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        ubparams.addRule(RelativeLayout.END_OF, ineq.getId());

        // Add lower bound, text, and upper bound to relative view

        rl.addView(lb, lbparams);
        rl.addView(ineq, ineqparams);
        rl.addView(ub, ubparams);

        //Add relative layout to linear layout

        tl.addView(rl,rlparams);

        //Add freq input to linear layout (gravity: center, weight: 3.5)

        EditText freq = new EditText(this);
        freq.setInputType(InputType.TYPE_CLASS_NUMBER);
        freq.setEms(4);
        LinearLayout.LayoutParams freqparams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,3.5f);
        tl.addView(freq,freqparams);

        //add delete row button (weight 1.5)

        Button btn = new Button(this);
        btn.setText("X");
        btn.setGravity(Gravity.CENTER);
        btn.setBackgroundColor(Color.TRANSPARENT);
        LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1.5f);
        tl.addView(btn,btnparams);

        //Add linear layout to master linear layout

        ll.addView(tl,counter,tlparams);
        counter+=1;
    }

}