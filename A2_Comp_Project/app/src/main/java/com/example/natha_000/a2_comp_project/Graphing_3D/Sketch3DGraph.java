package com.example.natha_000.a2_comp_project.Graphing_3D;

/**
 * Created by Natha_000 on 22/12/2016.
 */
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

import processing.core.PApplet;

import static com.example.natha_000.a2_comp_project.Graphing_3D.Intemediary3D.ERROR_MESSAGE;

public class Sketch3DGraph extends PApplet {
    // This variable is used to store the parent activity of this fragment
    Main3DActivity parent;

    // This variable stores the text contain the desired function
    String funcText;

    // This is an array that stores all of the z values for all of the coordinates
    private float[] floorVertices;

    // This holds the colour of all of the vertices
    private float[] floorColors = new float[4];

    // Stores the rotation of the user within the 3D space
    // [rotation in x axis, rotation in y axis]
    float[] rotation = new float[2];

    // A constant used to change the sensitivity in rotation
    float DT = 0.001f;

    // Values for the previous x and y position of the finger on the touch screen respectively
    float pMouseX = 0;
    float pMouseY = 0;

    // contains the distance the finger has traveled while on the touch screen squared
    // It is reset whenever the user raises their finger
    float absDistSquared = 0;

    /**
     * This sets up the general settings for the running of the app.
     */
    public void settings() {
        size(displayWidth, displayHeight, P3D);
        parent = (Main3DActivity) getActivity();
        Log.i("Project: Sketch3DGraph","settings");
        funcText = parent.functext;
        try {
            WorldLayoutData.setParameters(-10f,10f,-10f,10f,51,51,100f);
            WorldLayoutData.setfunction(funcText);
            WorldLayoutData.generate();
        } catch (IOException e) {
            Intent intent = new Intent(getActivity(), Intemediary3D.class);

            String message = "Error";
            intent.putExtra(ERROR_MESSAGE, message);

            startActivity(intent);
        }
        floorVertices = WorldLayoutData.SURFACE_COORDS;
        floorColors[0] = WorldLayoutData.SURFACE_COLORS[0];
        floorColors[1] = WorldLayoutData.SURFACE_COLORS[1];
        floorColors[2] = WorldLayoutData.SURFACE_COLORS[2];
        floorColors[3] = WorldLayoutData.SURFACE_COLORS[3];
    }

    /**
     * This function is called repeatedly during run time and allows for drawing on the canvas.
     */
    @Override
    public void draw() {
        sketchGraph();
        Log.i("Project: Sketch3DGraph","draw");
    }

    /**
     * This function is responsible for the main drawing of the 3D graph.
     * It first finds the current rotation, and then rotates the projection accordingly
     * And then plots the graph within this rotated coordinate system.
     */
    public void sketchGraph() {
        if (WorldLayoutData.isFlying()) {
            WorldLayoutData.fly(rotation);
            try {
                WorldLayoutData.generate();
                floorVertices = WorldLayoutData.SURFACE_COORDS;
            } catch (IOException e) {
                Log.e("Project: LOL", "An error has occurred trying to generate new co-ordinates");
            }
        }
        noStroke();
        Log.i("Project: Sketch3DGraph","sketchGraph");
        translate(width/2, height);
//        float rota = map(mouseY,0,displayHeight,-PI,PI);
        rotateX(rotation[0]);
        rotateY(rotation[1]);
        background(0);
        noFill();
        fill(floorColors[0]*255,floorColors[1]*255,floorColors[2]*255,floorColors[3]*255);
        lights();
        float off = map(mouseX,0,displayWidth,-10,10);
        beginShape(TRIANGLES);
        for (int i=0; i<floorVertices.length/3; i++) {
            vertex(floorVertices[i*3], (-floorVertices[i*3 + 1]+ off), floorVertices[i*3 + 2]);
        }
        endShape();
        fill(255);
    }

    /**
     * This function is responsible for resetting the varirable pMouseX and pMouseY to 0
     * It is also responsible for determining whether the user should start moving through the graph
     */
    public void mouseReleased() {
        pMouseX = 0;
        pMouseY = 0;
        Log.i("Project: mouseReleased","Screen has been released and absDist is " + Float.toString(absDistSquared));
        if (absDistSquared <=5) {
            WorldLayoutData.toggleFlying();
        }
        absDistSquared = 0;
    }

    /**
     * This function is responsible for changing the rotation depending on how the user drags their finger around the screen.
     */
    public void mouseDragged() {
        if (pMouseX != 0 || pMouseY != 0) {
            float deltaX = mouseX - pmouseX;
            float deltaY = mouseY - pmouseY;
            rotation[1] -= deltaX * DT;
            rotation[0] += deltaY * DT;
            absDistSquared += deltaX * deltaX + deltaY * deltaY;
        }
        pMouseX = mouseX;
        pMouseY = mouseY;
    }
}
