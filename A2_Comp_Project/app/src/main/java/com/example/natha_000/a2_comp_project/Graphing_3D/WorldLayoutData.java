package com.example.natha_000.a2_comp_project.Graphing_3D;

/**
 * Created by Natha_000 on 02/01/2017.
 */

import com.google.vr.sdk.base.HeadTransform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * This class is used to generate the class data that will be used for the
 * Plotting of the 3D graphs
*/
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
    public static float[] SURFACE_COORDS;
    public static float[] SURFACE_NORMALS;
    public static float[] SURFACE_COLORS;
    private static float[] eulerAngles = new float[3];
    private static float flySpeed = 0.1f;
    private static boolean flying = false;
    private static Function a;

    /**
     * This is used to change the parameters used in generating the coordinate of the landscape
     * @param xMin
    */
    public static void setParameters(float xMin, float xMax,
                              float yMin, float yMax,
                              int col, int row, float sl) {
        minX = xMin;
        maxX = xMax;
        minY = yMin;
        maxY = yMax;
        cols = col;
        rows = row;
        scl = sl;

        // Find the distance between consecutive x values
        w = (maxX - minX)/(cols-1);
        // Find the distance between consecutive y values
        h = (maxY - minY)/(rows-1);

        // Calculate the size of the necessary arrays and instantiate arrays of the appropriate size
        SURFACE_COORDS = new float[(cols-1)*(rows-1)*6*3];
        SURFACE_NORMALS = new float[SURFACE_COORDS.length];
        SURFACE_COLORS = new float[4 * SURFACE_COORDS.length / 3];
    }


    /**
     * Toggles to indicate whether the user is moving through the space or not.
     */
    static void toggleFlying() {flying = !flying;}


    /**
     * Used to obtain whether the user is flying from outside of the class.
     */
    static boolean isFlying() {
        return flying;
    }

    /**
     * Used to reset the class once the activity has finished using it.
     */
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

    /**
     * Used to obtain whether the x,y, and z offset from outside of the class.
     */
    static float[] getOffset() {
        return offset;
    }


    /**
     * The purpose of this function is to use the rotation of the user to emulate
     * the feeling of flight via the changing of the relative x,y, and z offset.
     * @param hT the relative position of the head, from the Google VR class
     */
    static void fly(HeadTransform hT) {
        hT.getEulerAngles(eulerAngles, 0);
        offset[0] = offset[0] - flySpeed*(float) Math.sin( (double) eulerAngles[1])*
                (float) Math.cos( (double) eulerAngles[0]);
        offset[1] = offset[1] - flySpeed*(float) Math.cos( (double) eulerAngles[1])*
                (float) Math.cos( (double) eulerAngles[0]);
        offset[2] = offset[2] - flySpeed*(float) Math.sin( (double) eulerAngles[0]);
    }

    /**
     * The purpose of this function is to use the rotation of the user to emulate
     * the feeling of flight via the changing of the relative x,y, and z offset.
     * @param eulerAngle the relative rotation of the user, given by an array of length 2 or 3
     */
    static void fly(float[] eulerAngle) {
        offset[0] = offset[0] + flySpeed*(float) Math.sin( (double) eulerAngle[1])*
                (float) Math.cos( (double) eulerAngle[0]);
        offset[1] = offset[1] - flySpeed*(float) Math.cos( (double) eulerAngle[1])*
                (float) Math.cos( (double) eulerAngle[0]);
        offset[2] = offset[2] - flySpeed*(float) Math.sin( (double) eulerAngle[0]);
    }


    /**
     * The purpose of this function is to parse the functext to generate a mathematical formula
     * That can be used in the rest of this activity.
     * @param functext is the text that represents the main function
     * @throws IOException If the function is invalid
     */
    public static void setfunction(String functext) throws IOException{
        a = GraphData.function_creator(functext);
    }

    /**
     * Converts Hue, saturation, lightness into red, green, blue
     * @param h Hue between 0 and 360 degrees
     * @param s Saturation between 0 and 1l
     * @param l lightness between 0 and 1
    */
    public static float[] hslTorgb(float h, float s, float l) {
        float C = (1 - Math.abs(2*l - 1)) * s;
        float X = C*(1- Math.abs(((h/60)%2) - 1));
        float m = l-C/2;
        float H = Math.max(0,Math.min(h/60,6));
        if (H<=1) {
            float[] result = {Math.round((C+m)*255),Math.round((X+m)*255),Math.round((0+m)*255)};
            return result;
        } else if (H<=2) {
            float[] result = {Math.round((X+m)*255),Math.round((C+m)*255),Math.round((0+m)*255)};
            return result;
        } else if (H<=3) {
            float[] result = {Math.round((0+m)*255),Math.round((C+m)*255),Math.round((X+m)*255)};
            return result;
        } else if (H<=4) {
            float[] result = {Math.round((0+m)*255),Math.round((X+m)*255),Math.round((C+m)*255)};
            return result;
        } else if (H<=5) {
            float[] result = {Math.round((X+m)*255),Math.round((0+m)*255),Math.round((C+m)*255)};
            return result;
        } else {
            float[] result = {Math.round((C+m)*255),Math.round((0+m)*255),Math.round((X+m)*255)};
            return result;
        }
    }

    /**
     * This is a convenience method so that I can call func(x,y) instead of
     * a.evalfunc(x,y)
    */
    private static float func(float x, float y) throws IOException {
        return a.evalfunc(x,y);
    }

    /**
     * Calculates the crossproduct of 2 vectors
     * @param v1 The first vector
     * @param v2 The second vector
     * @return A 1D array of size 3, Representing a 3D vector
     */
    public static float[] crossproduct(float[] v1, float[] v2){
        float[] cp = {v1[1]*v2[2] - v1[2]*v2[1],
                v1[2]*v2[0] - v1[0]*v2[2],
                v1[0]*v2[1] - v1[1]*v2[0]};
        return cp;
    }

    /**
     * normalise a 3D vector
     * @param v1 A vector to be normalised
     * @return The vector v1 normalised
     */
    public static float[] normalise(float[] v1){
        float mag = (float) Math.sqrt(v1[0]*v1[0] + v1[1]*v1[1] + v1[2]*v1[2]);
        float[] resvec = {v1[0]/mag, v1[1]/mag, v1[2]/mag};
        if (mag==0){
            return new float[]{0,0,0};
        }
        return resvec;
    }


    /**
     * Responsible for generating the values for the floor coords, floor colour, and floor normals.
     * @throws IOException if an invalid function is used.
     */
    public static void generate() throws IOException {
        values = new ArrayList<float[]>();
        int x=0;
        while (x < cols){
            int y = 0;
            while (y < rows){
                float[] value = {   (minX + x*w)*scl,
                                    (func(  minX + x*w + offset[0],
                                            minY + y*h + offset[1]) + offset[2])*scl,
                                    (minY + y*h)*scl};
                values.add(value);
                y++;
            }
            x++;
        }



        x=0;
        int counter = 0;
        while (x < cols-1){
            int y = 0;
            while (y < rows-1){
                float[] p00 = values.get(x*rows     + y  );
                float[] p01 = values.get(x*rows     + y+1);
                float[] p10 = values.get((x+1)*rows + y  );
                float[] p11 = values.get((x+1)*rows + y+1);
                //  poo po1 p1o po1 p11 p10
                //kill me
                SURFACE_COORDS[counter*18]    = p00[0];
                SURFACE_COORDS[counter*18+1]  = p00[1];
                SURFACE_COORDS[counter*18+2]  = p00[2];
                SURFACE_COORDS[counter*18+3]  = p01[0];
                SURFACE_COORDS[counter*18+4]  = p01[1];
                SURFACE_COORDS[counter*18+5]  = p01[2];
                SURFACE_COORDS[counter*18+6]  = p10[0];
                SURFACE_COORDS[counter*18+7]  = p10[1];
                SURFACE_COORDS[counter*18+8]  = p10[2];
                SURFACE_COORDS[counter*18+9]  = p01[0];
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

                counter++;
                y++;
            }
            x++;
        }

        for (int i=0; i<SURFACE_COLORS.length/4; i++) {
//            Uncoomment for colour based on height
//            float zvalue = SURFACE_COORDS[i*3+1];
//            float mapZValue = 360/200*(4*zvalue+100);
//            float[] rgbvalue = hslTorgb(mapZValue, 1.0f, 0.5f);
//            SURFACE_COLORS[i*4] = rgbvalue[0]/255;
//            SURFACE_COLORS[i*4+1] = rgbvalue[1]/255;
//            SURFACE_COLORS[i*4+2] = rgbvalue[2]/255;
//            SURFACE_COLORS[i*4+3] = 1.0f;
            SURFACE_COLORS[i*4] = 0.5f;
            SURFACE_COLORS[i*4+1] = 0.5f;
            SURFACE_COLORS[i*4+2] = 0.5f;
            SURFACE_COLORS[i*4+3] = 1.0f;
        }

    }
}
