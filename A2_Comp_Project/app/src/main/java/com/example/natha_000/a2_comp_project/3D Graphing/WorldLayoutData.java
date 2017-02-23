package com.example.natha_000.a2_comp_project;

/**
 * Created by Natha_000 on 02/01/2017.
 */

import com.google.vr.sdk.base.HeadTransform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class WorldLayoutData {
    private static float minX;
    private static float maxX;
    private static float minY;
    private static float maxY;
    private static int cols;
    private static int rows;
    private static float scl;
    private static float w;
    private static float h;
    private static float[] offset = {0,0,0};
    private static List<float[]> values;
    static float[] SURFACE_COORDS;
    static float[] SURFACE_NORMALS;
    static float[] SURFACE_COLORS;
    private static float[] eulerAngles = new float[3];
    private static float flySpeed = 0.1f;
    private static boolean flying = false;
    private static Function a;

    static void setParameters(float xMin, float xMax, float yMin, float yMax, int col, int row, float sl) {
        minX = xMin;
        maxX = xMax;
        minY = yMin;
        maxY = yMax;
        cols = col;
        rows = row;
        scl = sl;
        w = (maxX - minX)/(cols-1);
        h = (maxY - minY)/(rows-1);
        SURFACE_COORDS = new float[(cols-1)*(rows-1)*6*3];
        SURFACE_NORMALS = new float[SURFACE_COORDS.length];
        SURFACE_COLORS = new float[4 * SURFACE_COORDS.length / 3];
    }

    static void toggleFlying() {flying = !flying;};

    static boolean isFlying() {
        return flying;
    }

    static void reset() {
        offset[0] = 0;
        offset[1] = 0;
        offset[2] = 0;
    }

    static void changeResolution() {
        if (cols == 61) {
            WorldLayoutData.setParameters(-10f,10f,-10f,10f,41,41,10f);
        } else {
            WorldLayoutData.setParameters(-10f,10f,-10f,10f,61,61,10f);
        }
    }

    static float[] getOffset() {
        return offset;
    }

    static void fly(HeadTransform hT) {
        hT.getEulerAngles(eulerAngles, 0);
        offset[0] = offset[0] - flySpeed*(float) Math.sin( (double) eulerAngles[1])*(float) Math.cos( (double) eulerAngles[0]);
        offset[1] = offset[1] - flySpeed*(float) Math.cos( (double) eulerAngles[1])*(float) Math.cos( (double) eulerAngles[0]);
        offset[2] = offset[2] - flySpeed*(float) Math.sin( (double) eulerAngles[0]);

    }

    static void setfunction(String functext) throws IOException{
        a = GraphData.function_creator(functext);
    }

    private static float[] hslTorgb(float h, float s, float l) { // h between 0 and 360 degrees, s between 0 and 1, v between 0 and 1
        float C = (1 - abs(2*l - 1)) * s;
        float H = Math.max(0,Math.min(h/60,6));
        float X = C*(1- abs((H/60)%2 - 1));
        float m = l-C/2;
        if (H==0) {
            float[] result = {0+m,0+m,0+m};
            return result;
        } else if (H<=1) {
            float[] result = {C+m,X+m,0+m};
            return result;
        } else if (H<=2) {
            float[] result = {X+m,C+m,0+m};
            return result;
        } else if (H<=3) {
            float[] result = {0+m,C+m,X+m};
            return result;
        } else if (H<=4) {
            float[] result = {0+m,X+m,C+m};
            return result;
        } else if (H<=5) {
            float[] result = {X+m,0+m,C+m};
            return result;
        } else {
            float[] result = {C+m,0+m,X+m};
            return result;
        }
    }

    private static float func(float x, float y) throws IOException {
        return a.evalfunc(x,y);
    }

    private static float[] crossproduct(float[] v1, float[] v2){
        float[] cp = {v1[1]*v2[2] - v1[2]*v2[1],
                v1[2]*v2[0] - v1[0]*v2[2],
                v1[0]*v2[1] - v1[1]*v2[0]};
        return cp;
    }

    private static float[] normalise(float[] v1){
        float mag = (float) Math.sqrt(v1[0]*v1[0] + v1[1]*v1[1] + v1[2]*v1[2]);
        float[] resvec = {v1[0]/mag, v1[1]/mag, v1[2]/mag};
        return resvec;
    }

    static void generate() throws IOException {
        values = new ArrayList<float[]>();
        int x=0;
        while (x < cols){
            int y = 0;
            while (y < rows){
                float[] value = {(minX + x*w)*scl, (func(minX + x*w + offset[0], minY + y*h + offset[1]) + offset[2])*scl, (minY + y*h)*scl};
                values.add(value);
                y++;
            }
            x++;
        }

        for (int i=0; i<SURFACE_COLORS.length/4; i++) {
//                float zvalue = values.get(i)[1];
//                float mapZValue = 360/200*(zvalue+100);
//                float[] rgbvalue = hslTorgb(mapZValue, 1.0f, 1.0f);
                SURFACE_COLORS[i*4] = 0;
                SURFACE_COLORS[i*4+1] = 1.0f;
                SURFACE_COLORS[i*4+2] = 1.0f;
                SURFACE_COLORS[i*4+3] = 1.0f;


        }

        x=0;
        int counter = 0;
        while (x < cols-1){
            int y = 0;
            while (y < rows-1){
                float[] p00 = values.get(x*cols + y);
                float[] p01 = values.get(x*cols + y+1);
                float[] p10 = values.get((x+1)*cols + y);
                float[] p11 = values.get((x+1)*cols + y+1);
                //  poo po1 p1o po1 p11 p10
                //kill me
                SURFACE_COORDS[counter*18] = p00[0];
                SURFACE_COORDS[counter*18+1] = p00[1];
                SURFACE_COORDS[counter*18+2] = p00[2];
                SURFACE_COORDS[counter*18+3] = p01[0];
                SURFACE_COORDS[counter*18+4] = p01[1];
                SURFACE_COORDS[counter*18+5] = p01[2];
                SURFACE_COORDS[counter*18+6] = p10[0];
                SURFACE_COORDS[counter*18+7] = p10[1];
                SURFACE_COORDS[counter*18+8] = p10[2];
                SURFACE_COORDS[counter*18+9] = p01[0];
                SURFACE_COORDS[counter*18+10] = p01[1];
                SURFACE_COORDS[counter*18+11] = p01[2];
                SURFACE_COORDS[counter*18+12] = p11[0];
                SURFACE_COORDS[counter*18+13] = p11[1];
                SURFACE_COORDS[counter*18+14] = p11[2];
                SURFACE_COORDS[counter*18+15] = p10[0];
                SURFACE_COORDS[counter*18+16] = p10[1];
                SURFACE_COORDS[counter*18+17] = p10[2];

                float[] v1 = {p00[0]-p01[0],p00[1]-p01[1],p00[2]-p01[2]};
                float[] v2 = {p00[0]-p10[0],p00[1]-p10[1],p00[2]-p10[2]};
                float[] v3 = {p01[0]-p11[0],p01[1]-p11[1],p01[2]-p11[2]};
                float[] v4 = {p01[0]-p10[0],p01[1]-p10[1],p01[2]-p10[2]};
                float[] cp1 = normalise(crossproduct(v1,v2));
                float[] cp2 = normalise(crossproduct(v3,v4));

                for (int i = 0; i < 9; i++) {
                    SURFACE_NORMALS[counter*18+ i] = cp1[i%3];
                }
                for (int i = 0; i < 9; i++) {
                    SURFACE_NORMALS[counter*18+ 9 + i] = cp2[i%3];
                }

//                SURFACE_NORMALS[counter*18] = cp1[0];
//                SURFACE_NORMALS[counter*18+1] = cp1[1];
//                SURFACE_NORMALS[counter*18+2] = cp1[2];
//                SURFACE_NORMALS[counter*18+3] = cp1[0];
//                SURFACE_NORMALS[counter*18+4] = cp1[1];
//                SURFACE_NORMALS[counter*18+5] = cp1[2];
//                SURFACE_NORMALS[counter*18+6] = cp1[0];
//                SURFACE_NORMALS[counter*18+7] = cp1[1];
//                SURFACE_NORMALS[counter*18+8] = cp1[2];
//                SURFACE_NORMALS[counter*18+9] = cp2[0];
//                SURFACE_NORMALS[counter*18+10] = cp2[1];
//                SURFACE_NORMALS[counter*18+11] = cp2[2];
//                SURFACE_NORMALS[counter*18+12] = cp2[0];
//                SURFACE_NORMALS[counter*18+13] = cp2[1];
//                SURFACE_NORMALS[counter*18+14] = cp2[2];
//                SURFACE_NORMALS[counter*18+15] = cp2[0];
//                SURFACE_NORMALS[counter*18+16] = cp2[1];
//                SURFACE_NORMALS[counter*18+17] = cp2[2];
                counter++;
                y++;
            }
            x++;
        }

    }
}
