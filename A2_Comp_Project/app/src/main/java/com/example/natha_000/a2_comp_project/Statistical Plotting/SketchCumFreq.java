package com.example.natha_000.a2_comp_project;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import processing.core.PApplet;

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
        List<StatsClasses> c = StatsClasses.classes;
        return mergeSort(c);
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

    private static List<StatsClasses> mergeSort(List<StatsClasses> classes) {
        int n = classes.size();
        if (n == 1){
            return classes;
        }
        List<StatsClasses> l1 = mergeSort(classes.subList(0,floor(n/2)));
        List<StatsClasses> l2 = mergeSort(classes.subList(floor(n/2),n));


        return merge(l1,l2);
    }

    private static List<StatsClasses> merge(List<StatsClasses> l1, List<StatsClasses> l2){
        List<StatsClasses> l3 = new ArrayList<StatsClasses>();
        int l1counter = 0;
        int l2counter = 0;
        while (l1counter!=l1.size() && l2counter!=l2.size()) {
            if (l1.get(l1counter).lowerbound>l2.get(l2counter).lowerbound) {
                l3.add(l2.get(l2counter));
                l2counter++;
            } else {
                l3.add(l1.get(l1counter));
                l1counter++;
            }
        }

        while (l1counter!=l1.size()) {
            l3.add(l1.get(l1counter));
            l1counter++;
        }

        while (l2counter!=l2.size()) {
            l3.add(l2.get(l2counter));
            l2counter++;
        }
        return l3;
    }

    public static String sigfigs(float num, int sf) {
        String test = String.format("%."+sf+"G",num);
        if (test.contains("E+")) {
            test = String.format(Locale.US, "%.0f", Double.valueOf(String.format("%."+sf+"G",num)));
        }
        return test;
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
        // Generation of random data, will be changed to get data from other fragments
        //Random r = new Random(2000);
        //new StatsClasses(1,5,6);
        //for (int i=0; i<100; i++){
        //  // System.out.println(i);
        //  float lb = r.nextFloat()*1000+1;
        //  float ub = r.nextFloat()*1000+1;
        //  int freq = (int)(r.nextFloat()*50-24);
        //  if (!StatsClasses.validBound(lb)) {
        //    continue;
        //  } else if (!StatsClasses.validBound(ub)) {
        //    continue;
        //  } else if (!StatsClasses.validBounds(lb,ub)) {
        //    continue;
        //  } else if (freq<=0) {
        //    continue;
        //  }
        //  new StatsClasses(lb,ub,freq);
        //}
        //GCSE Question
        //new StatsClasses(0,10,10);
        //new StatsClasses(10,20,18);
        //new StatsClasses(30,50,8);
        //new StatsClasses(20,25,14);
        //new StatsClasses(25,30,10);
        //Normal Dist
//        new StatsClasses(0,10,1);
//        new StatsClasses(10,20,2);
//        new StatsClasses(20,30,1);
//        new StatsClasses(30,40,2);
//        new StatsClasses(40,50,1);
//        new StatsClasses(50,60,1);
//        new StatsClasses(60,70,5);
//        new StatsClasses(70,80,4);
//        new StatsClasses(80,90,11);
//        new StatsClasses(90,100,5);
//        new StatsClasses(100,110,4);
//        new StatsClasses(110,120,8);
//        new StatsClasses(120,130,11);
//        new StatsClasses(130,140,13);
//        new StatsClasses(140,150,7);
//        new StatsClasses(150,160,12);
//        new StatsClasses(160,170,10);
//        new StatsClasses(170,180,7);
//        new StatsClasses(180,190,13);
//        new StatsClasses(190,200,25);
//        new StatsClasses(200,210,11);
//        new StatsClasses(210,220,21);
//        new StatsClasses(220,230,22);
//        new StatsClasses(230,240,31);
//        new StatsClasses(240,250,24);
//        new StatsClasses(250,260,35);
//        new StatsClasses(260,270,31);
//        new StatsClasses(270,280,36);
//        new StatsClasses(280,290,37);
//        new StatsClasses(290,300,39);
//        new StatsClasses(300,310,40);
//        new StatsClasses(310,320,57);
//        new StatsClasses(320,330,41);
//        new StatsClasses(330,340,50);
//        new StatsClasses(340,350,52);
//        new StatsClasses(350,360,70);
//        new StatsClasses(360,370,72);
//        new StatsClasses(370,380,67);
//        new StatsClasses(380,390,82);
//        new StatsClasses(390,400,74);
//        new StatsClasses(400,410,85);
//        new StatsClasses(410,420,73);
//        new StatsClasses(420,430,81);
//        new StatsClasses(430,440,72);
//        new StatsClasses(440,450,92);
//        new StatsClasses(450,460,85);
//        new StatsClasses(460,470,108);
//        new StatsClasses(470,480,90);
//        new StatsClasses(480,490,84);
//        new StatsClasses(490,500,85);
//        new StatsClasses(500,510,83);
//        new StatsClasses(510,520,94);
//        new StatsClasses(520,530,100);
//        new StatsClasses(530,540,74);
//        new StatsClasses(540,550,85);
//        new StatsClasses(550,560,105);
//        new StatsClasses(560,570,73);
//        new StatsClasses(570,580,82);
//        new StatsClasses(580,590,86);
//        new StatsClasses(590,600,80);
//        new StatsClasses(600,610,87);
//        new StatsClasses(610,620,72);
//        new StatsClasses(620,630,67);
//        new StatsClasses(630,640,72);
//        new StatsClasses(640,650,67);
//        new StatsClasses(650,660,69);
//        new StatsClasses(660,670,38);
//        new StatsClasses(670,680,58);
//        new StatsClasses(680,690,63);
//        new StatsClasses(690,700,63);
//        new StatsClasses(700,710,58);
//        new StatsClasses(710,720,42);
//        new StatsClasses(720,730,44);
//        new StatsClasses(730,740,41);
//        new StatsClasses(740,750,28);
//        new StatsClasses(750,760,45);
//        new StatsClasses(760,770,35);
//        new StatsClasses(770,780,24);
//        new StatsClasses(780,790,24);
//        new StatsClasses(790,800,13);
//        new StatsClasses(800,810,22);
//        new StatsClasses(810,820,16);
//        new StatsClasses(820,830,16);
//        new StatsClasses(830,840,6);
//        new StatsClasses(840,850,12);
//        new StatsClasses(850,860,9);
//        new StatsClasses(860,870,13);
//        new StatsClasses(870,880,11);
//        new StatsClasses(880,890,11);
//        new StatsClasses(890,900,9);
//        new StatsClasses(900,910,5);
//        new StatsClasses(910,920,5);
//        new StatsClasses(920,930,6);
//        new StatsClasses(930,940,2);
//        new StatsClasses(940,950,4);
//        new StatsClasses(950,960,4);
//        new StatsClasses(960,970,2);
//        new StatsClasses(970,980,4);
//        new StatsClasses(980,990,2);
//        new StatsClasses(990,1000,1);
//        new StatsClasses(1000,1010,0);
//        new StatsClasses(1010,1020,5);


        // end of random generation
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
        float[] yresults = scaling(0, getMax(cumfreq), ytick);
        yAxisLB = 0;
        yAxisUB = yresults[1];
        yScale = yresults[2];
        while (yAxisUB > yScale * (ytick-1)) {
            ytick++;
        }
        while (yAxisUB < yScale * (ytick-1)){
            ytick--;
        }
        xtickdist = sketchWidth()*(1 - rmargin - lmargin)/(xtick-1);
        ytickdist = sketchHeight()*(1 - tmargin - bmargin)/(ytick-1);
    }

    public void draw() {
        try {
            background(255);
            drawAxis();
            drawLines();
            noLoop();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "please Enter Valid Data", Toast.LENGTH_SHORT).show();
        }
    }

    public void drawAxis() {
        line(sketchWidth()*lmargin, sketchHeight()*(1-bmargin),sketchWidth()*lmargin,sketchHeight()*tmargin);
        line(sketchWidth()*lmargin, sketchHeight()*(1-bmargin),sketchWidth()*(1-rmargin),sketchHeight()*(1-bmargin));
        //yaxis
        textAlign(RIGHT, CENTER);
        for (int y = 0; y<ytick; y++) {
            float relY = sketchHeight()*tmargin + ytickdist*y;
            float tickBegin = (1-ticklength)*sketchWidth()*lmargin;
            float tickEnd = sketchWidth()*lmargin;
            line(tickBegin, relY, tickEnd, relY);
            textSize(14);
            fill(0);
            text(sigfigs(yAxisUB - yScale*y,3),tickBegin,relY);
        }
        //xaxis
        textAlign(LEFT, TOP);
        float theta = PI/3;
        for (int x = 0; x<xtick; x++) {
            float relX = sketchWidth()*lmargin + xtickdist*x;
            float tickBegin = sketchHeight()*(1-bmargin*(1-ticklength));
            float tickEnd = sketchHeight()*(1-bmargin);
            line(relX, tickBegin, relX, tickEnd);

            textSize(14);
            fill(0);
            //relX,sketchHeight()*(1-bmargin*(1-ticklength))
            pushMatrix();
            rotate(theta);
            translate(tickBegin*sin(theta) + relX*cos(theta),tickBegin*cos(theta) - relX*sin(theta));
            text(sigfigs(xAxisLB + xScale*x,3),0,0);
            popMatrix();
        }
    }

    public void drawLines() {
        noFill();
        float cumFreq = 0;
        beginShape();
        for (StatsClasses class_: sClasses) {
            float freq = sketchHeight()*(1-bmargin)-sketchHeight()*(1 - tmargin - bmargin)*((cumFreq - yAxisLB)/(yAxisUB - yAxisLB));
            float bound = sketchWidth()*lmargin + sketchWidth()*(1 - rmargin - lmargin)*((class_.lowerbound - xAxisLB)/(xAxisUB - xAxisLB));
            vertex(bound,freq);
            cumFreq += class_.frequency;
            freq = sketchHeight()*(1-bmargin)-sketchHeight()*(1 - tmargin - bmargin)*((cumFreq - yAxisLB)/(yAxisUB - yAxisLB));
            bound = sketchWidth()*lmargin + sketchWidth()*(1 - rmargin - lmargin)*((class_.upperbound - xAxisLB)/(xAxisUB - xAxisLB));
            vertex(bound,freq);
        }
        endShape();
    }

    public void onDestroyView() {
        super.onDestroyView();
        reset();
    }
}