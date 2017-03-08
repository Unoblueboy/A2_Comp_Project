package com.example.natha_000.a2_comp_project.Graphing_Stats;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.natha_000.a2_comp_project.R;

import java.util.ArrayList;
import java.util.List;


public class StatsDataTable extends Fragment {
    private static List<EditText[]> etList = new ArrayList<EditText[]>();

    private static TableLayout tl;

    public StatsDataTable() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats_data_table, container, false);
        tl = (TableLayout) view.findViewById(R.id.tl2);
        Button b11 = (Button) view.findViewById(R.id.button11);
        EditText[] etArray = {(EditText) view.findViewById(R.id.editText),(EditText) view.findViewById(R.id.editText2),(EditText) view.findViewById(R.id.editText3)};
        etList.add(etArray);
        b11.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                add_row(v);
            }
        });
        Button b12 = (Button) view.findViewById(R.id.button12);
        b12.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clear_all(v);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void delete_row(View view) {
        TableRow parent = (TableRow) view.getParent();
        EditText lb = (EditText) parent.getChildAt(0);
        EditText ub = (EditText) parent.getChildAt(1);
        EditText freq = (EditText) parent.getChildAt(2);
        Log.i("Delete row",lb.getText().toString());
        Log.i("Delete row",ub.getText().toString());
        Log.i("Delete row",freq.getText().toString());
        try {
            StatsClasses relClass = StatsClasses.findClassByLb(Integer.parseInt(lb.getText().toString()));
            relClass.delete();
        } catch (Exception e) {}
        for (int i = etList.size()-1; i>= 0; i--) {
            if (lb == etList.get(i)[0]) {
                etList.remove(i);
            }
        }
        parent.setVisibility(View.GONE);
    }

    public void clear_all(View view) {
        for (int i=etList.size()-1; i>=1; i--) {
            delete_row(etList.get(i)[0]);
        }
    }

    public void add_row(View view){
        EditText[] lastItem = etList.get(etList.size()-1);
        Log.i("add_Row","This is the first item '" + lastItem[0].getText().toString()+"'") ;
        Log.i("add_Row","This is the second item '" + lastItem[1].getText().toString()+"'");
        Log.i("add_Row","This is the third item '" + lastItem[2].getText().toString()+"'");
        boolean isEmpty = lastItem[0].getText().toString().equals("") || lastItem[1].getText().toString().equals("") || lastItem[2].getText().toString().equals("");
        Log.i("add_Row",Boolean.toString(isEmpty));
        if(isEmpty) {
            Toast.makeText((Context) getActivity(),"Please enter valid data", Toast.LENGTH_SHORT).show();
        } else {
            boolean validLb = StatsClasses.validBound(Float.parseFloat(lastItem[0].getText().toString()));
            boolean validUb = StatsClasses.validBound(Float.parseFloat(lastItem[1].getText().toString()));
            boolean validBounds = StatsClasses.validBounds(Float.parseFloat(lastItem[0].getText().toString()), Float.parseFloat(lastItem[1].getText().toString()));
            boolean validFreq = Integer.parseInt(lastItem[2].getText().toString()) != 0;
            boolean invalid = !(validLb && validUb && validBounds && validFreq);
            Log.i("add_Row","ValidLb: "+Boolean.toString(validLb));
            Log.i("add_Row","ValidUb: "+Boolean.toString(validUb));
            Log.i("add_Row","ValidBounds: "+Boolean.toString(validBounds));
            Log.i("add_Row","ValidFreq: "+Boolean.toString(validFreq));
            Log.i("add_Row","invalid: "+Boolean.toString(invalid));
            if (invalid) {
                Toast.makeText((Context) getActivity(),"Please enter valid data", Toast.LENGTH_SHORT).show();
                return;
            }

            float lowerbound = Float.parseFloat(lastItem[0].getText().toString());
            float upperbound = Float.parseFloat(lastItem[1].getText().toString());
            int frequency = Integer.parseInt(lastItem[2].getText().toString());
            if (StatsClasses.findClassByLb(lowerbound) == null) {
                new StatsClasses(lowerbound, upperbound, frequency);
            }



            TableRow tr = new TableRow(getActivity());
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            EditText lb = new EditText(getActivity());
            lb.setKeyListener(DigitsKeyListener.getInstance(false,true));
            lb.setId(View.generateViewId());
//            lb.setInputType(InputType.TYPE_CLASS_NUMBER);
            TableRow.LayoutParams lbparams = new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, 1f);

            tr.addView(lb, lbparams);

            EditText ub = new EditText(getActivity());
            ub.setKeyListener(DigitsKeyListener.getInstance(false,true));
            ub.setId(View.generateViewId());
//            ub.setInputType(InputType.TYPE_CLASS_NUMBER);
            TableRow.LayoutParams ubparams = new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, 1f);

            tr.addView(ub, ubparams);

            EditText freq = new EditText(getActivity());
            freq.setId(View.generateViewId());
            freq.setInputType(InputType.TYPE_CLASS_NUMBER);
            TableRow.LayoutParams freqparams = new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, 1f);

            tr.addView(freq, freqparams);

            Button btn = new Button(getActivity());
            btn.setText("Delete?");
            btn.setGravity(Gravity.CENTER);
            TableRow.LayoutParams btnparams = new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, 1f);

            btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    delete_row(v);
                }
            });

            tr.addView(btn, btnparams);

            tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            Log.i("Add_Row", "Now adding a row");

            EditText[] etArray = {lb,ub,freq};
            etList.add(etArray);
        }
    }
}
