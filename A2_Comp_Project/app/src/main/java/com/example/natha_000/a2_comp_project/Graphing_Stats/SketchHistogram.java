package com.example.natha_000.a2_comp_project.Graphing_Stats;

/**
 * Created by Natha_000 on 22/12/2016.
 */

import java.util.Arrays;
import java.util.Locale;

import processing.core.PApplet;

public class SketchHistogram extends PApplet {
    private static float lmargin = 0.15f;
    private static float rmargin = 0.1f;
    private static float tmargin = 0.1f;
    private static float bmargin = 0.15f;
    private static float ticklength = 0.15f;
    private static int xtick = 6;
    private static int ytick = 10;
    private static float ytickdist;
    private static float xtickdist;
    private static float xAxisLB;
    private static float xAxisUB;
    private static float xScale;
    private static float yAxisLB;
    private static float yAxisUB;
    private static float yScale;
    public static String sigfigs(float num, int sf) {
        String test = String.format("%."+sf+"G",num);
        if (test.contains("E+")) {
            test = String.format(Locale.US, "%.0f", Double.valueOf(String.format("%."+sf+"G",num)));
        }
        return test;
    }

    public static void reset() {
        ytickdist=0f;
        xtickdist=0f;
        xAxisLB=0f;
        xAxisUB=0f;
        xScale=0f;
        yAxisLB=0f;
        yAxisUB=0f;
        yScale=0f;
    }

    private static float getMin(float[] array) {
        float min = array[0];
        for (float ele: array) {
            if (ele<min) {
                min = ele;
            }
        }
        return min;
    }

    private static float getMax(float[] array) {
        float max = array[0];
        for (float ele: array) {
            if (ele>max) {
                max = ele;
            }
        }
        return max;
    }

    private static float[] scaling(float lb, float ub, int tick) {

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
            tickrange = (float) 0.1 * pow(10,x);
        } else if (tickrange<=0.2){
            tickrange = (float) 0.2 * pow(10,x);
        } else if (tickrange<=0.25){
            tickrange = (float) 0.25 * pow(10,x);
        } else if (tickrange<=0.4){
            tickrange = (float) 0.4 * pow(10,x);
        } else if (tickrange<=0.5){
            tickrange = (float) 0.5 * pow(10,x);
        } else if (tickrange<=0.75){
            tickrange = (float) 0.75 * pow(10,x);
        } else if (tickrange<=0.8){
            tickrange = (float) 0.8 * pow(10,x);
        } else if (tickrange<=1.0){
            tickrange = (float) 1.0 * pow(10,x);
        }
        float lowerbound = tickrange*floor(classMin/tickrange);
        float upperbound = tickrange*ceil(classMax/tickrange);
        float[] result = {lowerbound, upperbound, tickrange};
        return result;
    }

    public void settings() {
        size(displayWidth,displayHeight-60);
        float[] bounds = new float[StatsClasses.noOfClasses*2];
        float[] freqdens = new float[StatsClasses.noOfClasses];
        for (int i = 0; i<StatsClasses.noOfClasses; i++) {
            StatsClasses sclass = StatsClasses.classes.get(i);
            bounds[i*2] = sclass.lowerbound;
            bounds[i*2+1] = sclass.upperbound;
            freqdens[i] = sclass.freqdensity;
        }
        float[] xresults = scaling(getMin(bounds), getMax(bounds), xtick);
        xAxisLB = xresults[0];
        xAxisUB = xresults[1];
        xScale = xresults[2];
        while (xAxisUB > xScale * (xtick-1)) {
            xtick++;
        }
        while (xAxisUB < xScale * (xtick-1)){
            xtick--;
        }
        float[] yresults = scaling(0, getMax(freqdens), ytick);
        yAxisLB = 0;
        yAxisUB = yresults[1];
        yScale = yresults[2];
        while (yAxisUB > yScale * (ytick-1)) {
            ytick++;
        }
        while (yAxisUB < yScale * (ytick-1)){
            ytick--;
        }
        System.out.println(Arrays.toString(scaling(0,1, 10)));
        System.out.println(Arrays.toString(bounds));
        System.out.println(Arrays.toString(freqdens));
        System.out.println(Arrays.toString(xresults));
        System.out.println(Arrays.toString(yresults));
        xtickdist = displayWidth*(1 - rmargin - lmargin)/(xtick-1);
        ytickdist = sketchHeight()*(1 - tmargin - bmargin)/(ytick-1);
    }

    public void draw() {
        background(255);
        drawAxis();
        drawBars();
        noLoop();
    }

    public void drawAxis() {
        line(displayWidth*lmargin, sketchHeight()*(1-bmargin),displayWidth*lmargin,sketchHeight()*tmargin);
        line(displayWidth*lmargin, sketchHeight()*(1-bmargin),displayWidth*(1-rmargin),sketchHeight()*(1-bmargin));
        //yaxis
        textAlign(RIGHT, CENTER);
        for (int y = 0; y<ytick; y++) {
            float relY = sketchHeight()*tmargin + ytickdist*y;
            float tickBegin = (1-ticklength)*displayWidth*lmargin;
            float tickEnd = displayWidth*lmargin;
            line(tickBegin, relY, tickEnd, relY);
            textSize(14);
            fill(0);
            text(sigfigs(yAxisUB - yScale*y,2),tickBegin,relY);
        }
        //xaxis
        textAlign(LEFT, TOP);
        float theta = PI/3;
        for (int x = 0; x<xtick; x++) {
            float relX = displayWidth*lmargin + xtickdist*x;
            float tickBegin = sketchHeight()*(1-bmargin*(1-ticklength));
            float tickEnd = sketchHeight()*(1-bmargin);
            line(relX, tickBegin, relX, tickEnd);

            textSize(14);
            fill(0);
            //relX,sketchHeight()*(1-bmargin*(1-ticklength))
            pushMatrix();
            rotate(theta);
            translate(tickBegin*sin(theta) + relX*cos(theta),tickBegin*cos(theta) - relX*sin(theta));
            text(sigfigs(xAxisLB + xScale*x,2),0,0);
            popMatrix();
        }
    }

    public void drawBars() {
        int hue = 0;
        colorMode(HSB,255);
        for (StatsClasses c: StatsClasses.classes) {
            float left = displayWidth*(1 - rmargin - lmargin)*((c.lowerbound - xAxisLB)/(xAxisUB - xAxisLB));
            float top = sketchHeight()*(1 - tmargin - bmargin)*((c.freqdensity - yAxisLB)/(yAxisUB - yAxisLB));
            float w = displayWidth*(1 - rmargin - lmargin)*(c.classwidth/(xAxisUB - xAxisLB));
            fill(hue,255,255);
            rect(displayWidth*lmargin+left,sketchHeight()*(1-bmargin)-top,w,top);
            hue = hue + 40;
            hue = hue % 255;
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        reset();
    }
}