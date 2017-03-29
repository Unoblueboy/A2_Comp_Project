package com.example.natha_000.a2_comp_project.Graphing_Stats;

/**
 * Created by Natha_000 on 22/12/2016.
 */

import android.util.Log;

import java.io.File;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

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

    public void settings() {
        size(displayWidth,displayHeight-60);
        startUp(displayWidth,sketchHeight());
    }

    public void startUp(int w, int h) {
        float[] bounds = new float[StatsClasses.noOfClasses*2];
        float[] freqdens = new float[StatsClasses.noOfClasses];
        Log.i("Project: ABC",Integer.toString(StatsClasses.noOfClasses));
        for (int i = 0; i<StatsClasses.noOfClasses; i++) {
            StatsClasses sclass = StatsClasses.classes.get(i);
            bounds[i*2] = sclass.lowerbound;
            bounds[i*2+1] = sclass.upperbound;
            freqdens[i] = sclass.freqdensity;
        }

        float[] xresults = GraphingMethods.scaling(GraphingMethods.getMin(bounds), GraphingMethods.getMax(bounds), xtick);
        xAxisLB = xresults[0];
        xAxisUB = xresults[1];
        xScale = xresults[2];
        xtick = (int) xresults[3];

        float[] yresults = GraphingMethods.scaling(0, GraphingMethods.getMax(freqdens), ytick);
        yAxisLB = 0;
        yAxisUB = yresults[1];
        yScale = yresults[2];
        ytick = (int) xresults[3];

        xtickdist = w*(1 - rmargin - lmargin)/(xtick-1);
        ytickdist = h*(1 - tmargin - bmargin)/(ytick-1);
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
            text(GraphingMethods.sigfigs(yAxisUB - yScale*y,2),tickBegin,relY);
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
            text(GraphingMethods.sigfigs(xAxisLB + xScale*x,2),0,0);
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

    public void drawAxis(PGraphics pg) {
        pg.beginDraw();
        pg.line(pg.width*lmargin, pg.height*(1-bmargin),pg.width*lmargin,pg.height*tmargin);
        pg.line(pg.width*lmargin, pg.height*(1-bmargin),pg.width*(1-rmargin),pg.height*(1-bmargin));
        //yaxis
        pg.textAlign(RIGHT, CENTER);
        for (int y = 0; y<ytick; y++) {
            float relY = pg.height*tmargin + ytickdist*y;
            float tickBegin = (1-ticklength)*pg.width*lmargin;
            float tickEnd = pg.width*lmargin;
            pg.line(tickBegin, relY, tickEnd, relY);
            pg.textSize(14);
            pg.fill(0);
            pg.text(GraphingMethods.sigfigs(yAxisUB - yScale*y,2),tickBegin,relY);
        }
        //xaxis
        pg.textAlign(LEFT, TOP);
        float theta = PI/3;
        for (int x = 0; x<xtick; x++) {
            float relX = pg.width*lmargin + xtickdist*x;
            float tickBegin = pg.height*(1-bmargin*(1-ticklength));
            float tickEnd = pg.height*(1-bmargin);
            pg.line(relX, tickBegin, relX, tickEnd);

            pg.textSize(14);
            pg.fill(0);
            pg.pushMatrix();
            pg.rotate(theta);
            pg.translate(tickBegin*sin(theta) + relX*cos(theta),tickBegin*cos(theta) - relX*sin(theta));
            pg.text(GraphingMethods.sigfigs(xAxisLB + xScale*x,2),0,0);
            pg.popMatrix();
        }
        pg.endDraw();
    }

    public void drawBars(PGraphics pg) {
        pg.beginDraw();
        int hue = 0;
        pg.colorMode(HSB,255);
        for (StatsClasses c: StatsClasses.classes) {
            float left = pg.width*(1 - rmargin - lmargin)*((c.lowerbound - xAxisLB)/(xAxisUB - xAxisLB));
            float top = pg.height*(1 - tmargin - bmargin)*((c.freqdensity - yAxisLB)/(yAxisUB - yAxisLB));
            float w = pg.width*(1 - rmargin - lmargin)*(c.classwidth/(xAxisUB - xAxisLB));
            pg.fill(hue,255,255);
            pg.rect(pg.width*lmargin+left,pg.height*(1-bmargin)-top,w,top);
            hue = hue + 40;
            hue = hue % 255;
        }
        pg.endDraw();
    }

    public void export(int w,int h,File folder) {
        PGraphics hist = createGraphics(w,h);
        startUp(w,h);
        hist.beginDraw();
        hist.background(255);
        hist.endDraw();
        drawAxis(hist);
        drawBars(hist);
        PImage result = hist.get();
        result.save(folder.toString() + File.separator + "Histogram.jpg");
//        Toast.makeText(getActivity(), "Saved :P", Toast.LENGTH_SHORT).show();
    }

    public void onStart() {
        Log.i("Hist","OnStart");
        super.onStart();
        redraw();
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//
//        if (isVisibleToUser)
//            Log.d("MyFragment", "Fragment is visible.");
//        else
//            Log.d("MyFragment", "Fragment is not visible.");
//    }

    public void onDestroyView() {
        super.onDestroyView();
        reset();
    }

    public void onResume() {
        Log.i("Hist","OnResume");
        super.onResume();
        redraw();
    }
}