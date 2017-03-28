package com.example.natha_000.a2_comp_project.Graphing_Stats;

import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

/**
 * Created by Natha_000 on 05/02/2017.
 */

public class SketchCumFreq extends PApplet {
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
//    private static float[] cumfreq;
    private static List<StatsClasses> sClasses;
    public static List<StatsClasses> sortedClasses(){
        return StatsClasses.getSortedClasses();
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
//        cumfreq = new float[0];
    }

    public void settings() {
        size(displayWidth,displayHeight-60);
        startUp(displayWidth,displayHeight-60);
    }

    public void startUp(int w, int h) {
        float[] bounds = new float[StatsClasses.noOfClasses*2];
        float[] freqdens = new float[StatsClasses.noOfClasses];
        for (int i = 0; i<StatsClasses.noOfClasses; i++) {
            StatsClasses sclass = StatsClasses.classes.get(i);
            bounds[i*2] = sclass.lowerbound;
            bounds[i*2+1] = sclass.upperbound;
            freqdens[i] = sclass.frequency;
        }
        sClasses = sortedClasses();
        float[] cumfreq = new float[sClasses.size()*2];
        for (int i=0; i<sClasses.size();i++) {
            cumfreq[i*2] = (i==0) ? 0 : cumfreq[(i-1)*2]+sClasses.get(i-1).frequency;
            cumfreq[i*2+1] = cumfreq[(i)*2]+sClasses.get(i).frequency;
        }

        float[] xresults = GrahphingMethods.scaling(GrahphingMethods.getMin(bounds), GrahphingMethods.getMax(bounds), xtick);
        xAxisLB = xresults[0];
        xAxisUB = xresults[1];
        xScale = xresults[2];
        xtick = (int) xresults[3];

        float[] yresults = GrahphingMethods.scaling(0, GrahphingMethods.getMax(freqdens), ytick);
        yAxisLB = 0;
        yAxisUB = yresults[1];
        yScale = yresults[2];
        ytick = (int) xresults[3];

        xtickdist = w*(1 - rmargin - lmargin)/(xtick-1);
        ytickdist = h*(1 - tmargin - bmargin)/(ytick-1);
    }

    public void draw() {
        try {
            background(255);
            drawAxis();
            drawLines();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "please Enter Valid Data", Toast.LENGTH_SHORT).show();
        }
        noLoop();
    }

    public void export(int w,int h, File folder) {
        PGraphics cumuFreq = createGraphics(w,h);
        startUp(w,h);
        cumuFreq.beginDraw();
        cumuFreq.background(255);
        cumuFreq.endDraw();
        drawAxis(cumuFreq);
        drawLines(cumuFreq);
        PImage result = cumuFreq.get();
        result.save(folder.toString() + File.separator + "CumulativeFrequency.jpg");
//        Toast.makeText(getActivity(), "Saved :P", Toast.LENGTH_SHORT).show();
    }

    public void drawAxis(PGraphics pg) {
        Log.i("Project: SketchCumFreq", "Draw Axis, with PGraphic");
        pg.beginDraw();
        pg.line(pg.width * lmargin, pg.height * (1 - bmargin), pg.width * lmargin, pg.height * tmargin);
        pg.line(pg.width * lmargin, pg.height * (1 - bmargin), pg.width * (1 - rmargin), pg.height * (1 - bmargin));
        //yaxis
        pg.textAlign(RIGHT, CENTER);
        for (int y = 0; y < ytick; y++) {
            float relY = pg.height * tmargin + ytickdist * y;
            float tickBegin = (1 - ticklength) * pg.width * lmargin;
            float tickEnd = pg.width * lmargin;
            pg.line(tickBegin, relY, tickEnd, relY);
            pg.textSize(14);
            pg.fill(0);
            pg.text(GrahphingMethods.sigfigs(yAxisUB - yScale * y, 3), tickBegin, relY);
        }
        //xaxis
        pg.textAlign(LEFT, TOP);
        float theta = PI / 3;
        for (int x = 0; x < xtick; x++) {
            float relX = pg.width * lmargin + xtickdist * x;
            float tickBegin = pg.height * (1 - bmargin * (1 - ticklength));
            float tickEnd = pg.height * (1 - bmargin);
            pg.line(relX, tickBegin, relX, tickEnd);

            pg.textSize(14);
            pg.fill(0);
            //relX,pg.height*(1-bmargin*(1-ticklength))
            pg.pushMatrix();
            pg.rotate(theta);
            pg.translate(tickBegin * sin(theta) + relX * cos(theta), tickBegin * cos(theta) - relX * sin(theta));
            pg.text(GrahphingMethods.sigfigs(xAxisLB + xScale * x, 3), 0, 0);
            pg.popMatrix();
        }
        pg.endDraw();
    }

    public void drawAxis() {
        Log.i("Project: SketchCumFreq", "Draw Axis, no PGraphic");
        line(sketchWidth() * lmargin, sketchHeight() * (1 - bmargin), sketchWidth() * lmargin, sketchHeight() * tmargin);
        line(sketchWidth() * lmargin, sketchHeight() * (1 - bmargin), sketchWidth() * (1 - rmargin), sketchHeight() * (1 - bmargin));
        //yaxis
        textAlign(RIGHT, CENTER);
        for (int y = 0; y < ytick; y++) {
            float relY = sketchHeight() * tmargin + ytickdist * y;
            float tickBegin = (1 - ticklength) * sketchWidth() * lmargin;
            float tickEnd = sketchWidth() * lmargin;
            line(tickBegin, relY, tickEnd, relY);
            textSize(14);
            fill(0);
            text(GrahphingMethods.sigfigs(yAxisUB - yScale * y, 3), tickBegin, relY);
        }
        //xaxis
        textAlign(LEFT, TOP);
        float theta = PI / 3;
        for (int x = 0; x < xtick; x++) {
            float relX = sketchWidth() * lmargin + xtickdist * x;
            float tickBegin = sketchHeight() * (1 - bmargin * (1 - ticklength));
            float tickEnd = sketchHeight() * (1 - bmargin);
            line(relX, tickBegin, relX, tickEnd);

            textSize(14);
            fill(0);
            //relX,sketchHeight()*(1-bmargin*(1-ticklength))
            pushMatrix();
            rotate(theta);
            translate(tickBegin * sin(theta) + relX * cos(theta), tickBegin * cos(theta) - relX * sin(theta));
            text(GrahphingMethods.sigfigs(xAxisLB + xScale * x, 3), 0, 0);
            popMatrix();
        }
    }

    public void drawLines(PGraphics pg) {
        Log.i("Project: SketchCumFreq", "Draw Lines, with PGraphic");
        pg.beginDraw();
        pg.noFill();
        float cumFreq = 0;
        pg.beginShape();
        for (StatsClasses class_ : sClasses) {
            float freq = pg.height * (1 - bmargin) - pg.height * (1 - tmargin - bmargin) * ((cumFreq - yAxisLB) / (yAxisUB - yAxisLB));
            float bound = pg.width * lmargin + pg.width * (1 - rmargin - lmargin) * ((class_.lowerbound - xAxisLB) / (xAxisUB - xAxisLB));
            pg.vertex(bound, freq);
            cumFreq += class_.frequency;
            freq = pg.height * (1 - bmargin) - pg.height * (1 - tmargin - bmargin) * ((cumFreq - yAxisLB) / (yAxisUB - yAxisLB));
            bound = pg.width * lmargin + pg.width * (1 - rmargin - lmargin) * ((class_.upperbound - xAxisLB) / (xAxisUB - xAxisLB));
            pg.vertex(bound, freq);
        }
        pg.endShape();
        pg.endDraw();
        return;
    }

    public void drawLines() {
        Log.i("Project: SketchCumFreq", "Draw Lines, no PGraphic");
        noFill();
        float cumFreq = 0;
        beginShape();
        for (StatsClasses class_ : sClasses) {
            float freq = sketchHeight() * (1 - bmargin) - sketchHeight() * (1 - tmargin - bmargin) * ((cumFreq - yAxisLB) / (yAxisUB - yAxisLB));
            float bound = sketchWidth() * lmargin + sketchWidth() * (1 - rmargin - lmargin) * ((class_.lowerbound - xAxisLB) / (xAxisUB - xAxisLB));
            vertex(bound, freq);
            cumFreq += class_.frequency;
            freq = sketchHeight() * (1 - bmargin) - sketchHeight() * (1 - tmargin - bmargin) * ((cumFreq - yAxisLB) / (yAxisUB - yAxisLB));
            bound = sketchWidth() * lmargin + sketchWidth() * (1 - rmargin - lmargin) * ((class_.upperbound - xAxisLB) / (xAxisUB - xAxisLB));
            vertex(bound, freq);
        }
        endShape();
    }

    public void onDestroyView() {
        super.onDestroyView();
        reset();
    }

    public void onStart() {
        Log.i("CumFreq","OnStart");
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

    public void onResume() {
        Log.i("CumFreq","OnResume");
        super.onResume();
        redraw();
    }
}
