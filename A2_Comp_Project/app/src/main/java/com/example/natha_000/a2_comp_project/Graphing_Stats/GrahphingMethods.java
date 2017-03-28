package com.example.natha_000.a2_comp_project.Graphing_Stats;

import java.util.Locale;

import processing.core.PApplet;

/**
 * Created by Natha_000 on 28/03/2017.
 */

public class GrahphingMethods {
    public static String sigfigs(float num, int sf) {
        String test = String.format("%."+sf+"G",num);
        if (test.contains("E+")) {
            test = String.format(Locale.US, "%.0f", Double.valueOf(String.format("%."+sf+"G",num)));
        }
        return test;
    }

    static float getMin(float[] array) {
        float min = array[0];
        for (float ele: array) {
            if (ele<min) {
                min = ele;
            }
        }
        return min;
    }

    static float getMax(float[] array) {
        float max = array[0];
        for (float ele: array) {
            if (ele>max) {
                max = ele;
            }
        }
        return max;
    }

    static float[] scaling(float lb, float ub, int tick) {

        float classMin = lb;
        float classMax = ub;
        float range = classMax-classMin;
        float tickrange = range/tick;
        int x = 0;
        while (tickrange > 1 || tickrange < 0.1) {
            if (tickrange < 0.1) {
                tickrange = tickrange*10;
                x--;
            }
            else {
                tickrange = tickrange/10;
                x++;
            }
        }
        if (tickrange==0.1){
            tickrange = (float) 0.1 * PApplet.pow(10,x);
        } else if (tickrange<=0.2){
            tickrange = (float) 0.2 * PApplet.pow(10,x);
        } else if (tickrange<=0.25){
            tickrange = (float) 0.25 * PApplet.pow(10,x);
        } else if (tickrange<=0.4){
            tickrange = (float) 0.4 * PApplet.pow(10,x);
        } else if (tickrange<=0.5){
            tickrange = (float) 0.5 * PApplet.pow(10,x);
        } else if (tickrange<=0.75){
            tickrange = (float) 0.75 * PApplet.pow(10,x);
        } else if (tickrange<=0.8){
            tickrange = (float) 0.8 * PApplet.pow(10,x);
        } else if (tickrange<=1.0){
            tickrange = (float) 1.0 * PApplet.pow(10,x);
        }
        float lowerbound = tickrange* PApplet.floor(classMin/tickrange);
        float upperbound = tickrange* PApplet.ceil(classMax/tickrange);
        while (ub > tickrange * (tick-1)) {
            tick++;
        }
        while (ub < tickrange * (tick-1)){
            tick--;
        }
        float[] result = {lowerbound, upperbound, tickrange,tick};
        return result;
    }
}
