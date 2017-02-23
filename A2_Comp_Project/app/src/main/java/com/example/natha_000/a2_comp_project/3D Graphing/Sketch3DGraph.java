package com.example.natha_000.a2_comp_project;

/**
 * Created by Natha_000 on 22/12/2016.
 */
import android.util.Log;

import java.io.IOException;

import processing.core.PApplet;
import processing.core.PFont;

public class Sketch3DGraph extends PApplet {
    public void settings() {
        size(displayWidth, displayHeight, P3D);
        parent = (Main3DActivity) getActivity();
        Log.i("Sketch3DGraph","settings");
        f = createFont("Arial",32,true);
        funcText = parent.functext;
        try {
            WorldLayoutData.setParameters(-10f,10f,-10f,10f,21,21,10f);
            WorldLayoutData.setfunction(funcText);
            WorldLayoutData.generate();
        } catch (IOException e) {}
        floorVertices = WorldLayoutData.SURFACE_COORDS;
        floorColors[0] = WorldLayoutData.SURFACE_COLORS[0];
        floorColors[1] = WorldLayoutData.SURFACE_COLORS[1];
        floorColors[2] = WorldLayoutData.SURFACE_COLORS[2];
        floorColors[3] = WorldLayoutData.SURFACE_COLORS[3];
    }

    PFont f;
    boolean fly = false;
    Rotation rot;
    Main3DActivity parent;
    String funcText;
    private float[] floorVertices;
    private float[] floorColors = new float[4];


    float[] flying = new float[2];

    float[][] terrain;


    public void draw() {
        sketchGraph();
        Log.i("Sketch3DGraph","draw");
    }

    public void sketchGraph() {
        noStroke();
        Log.i("Sketch3DGraph","sketchGraph");
        translate(width/2, height);
        float rota = map(mouseY,0,displayHeight,-PI,PI);
        rotateX(rota);
//        rotateX(rot.getXRot());
//        rotateY(rot.getZRot());
//        rotateZ(rot.getYRot());
//        translate(-w/2, -h/2);
        background(0);
        noFill();
        fill(floorColors[0]*255,floorColors[1]*255,floorColors[2]*255,floorColors[3]*255);
        lights();
        float off = map(mouseX,0,displayWidth,-10,10);
        beginShape(TRIANGLES);
        for (int i=0; i<floorVertices.length/3; i++) {
            vertex(floorVertices[i*3], -floorVertices[i*3 + 1]+ off, floorVertices[i*3 + 2]);
        }
        endShape();
        fill(255);
    }

    public void mousePressed() {
        fly = true;
    }

    public void mouseReleased() {
        fly = false;
    }
}
