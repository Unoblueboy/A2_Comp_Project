package com.example.natha_000.a2_comp_project;

import com.example.natha_000.a2_comp_project.Graphing_3D.WorldLayoutData;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class WorldLayoutDataTest {
    @Test
    public void hsltorgb_isCorrect() throws Exception {
        assertArrayEquals(new float[]{0,0,0},WorldLayoutData.hslTorgb(0,0,0),1f);
        assertArrayEquals(new float[]{255,255,255},WorldLayoutData.hslTorgb(0,0,1),1f);
        assertArrayEquals(new float[]{255,0,0},WorldLayoutData.hslTorgb(0,1,0.5f),1f);
        assertArrayEquals(new float[]{0,255,0},WorldLayoutData.hslTorgb(120,1,0.5f),1f);
        assertArrayEquals(new float[]{0,0,255},WorldLayoutData.hslTorgb(240,1,0.5f),1f);
        assertArrayEquals(new float[]{255,255,0},WorldLayoutData.hslTorgb(60,1,0.5f),1f);
        assertArrayEquals(new float[]{0,255,255},WorldLayoutData.hslTorgb(180,1,0.5f),1f);
        assertArrayEquals(new float[]{255,0,255},WorldLayoutData.hslTorgb(300,1,0.5f),1f);
        assertArrayEquals(new float[]{192,192,192},WorldLayoutData.hslTorgb(0,0,0.75f),1f);
        assertArrayEquals(new float[]{128,128,128},WorldLayoutData.hslTorgb(0,0,0.5f),1f);
        assertArrayEquals(new float[]{128,0,0},WorldLayoutData.hslTorgb(0,1,0.25f),1f);
        assertArrayEquals(new float[]{128,128,0},WorldLayoutData.hslTorgb(60,1,0.25f),1f);
        assertArrayEquals(new float[]{0,128,0},WorldLayoutData.hslTorgb(120,1,0.25f),1f);
        assertArrayEquals(new float[]{128,0,128},WorldLayoutData.hslTorgb(300,1,0.25f),1f);
        assertArrayEquals(new float[]{0,128,128},WorldLayoutData.hslTorgb(180,1,0.25f),1f);
        assertArrayEquals(new float[]{0,0,128},WorldLayoutData.hslTorgb(240,1,0.25f),1f);

    }

    @Test
    public void crossproduct_isCorrect() throws Exception {
        assertArrayEquals(new float[]{0,0,0},WorldLayoutData.crossproduct(new float[]{0,0,0}, new float[]{0,0,0}), 0.01f);
        assertArrayEquals(new float[]{-48,0,40},WorldLayoutData.crossproduct(new float[]{5,4,6}, new float[]{0,8,0}), 0.01f);
        assertArrayEquals(new float[]{-12,0,20},WorldLayoutData.crossproduct(new float[]{5,4,3}, new float[]{5,8,3}), 0.01f);
        assertArrayEquals(new float[]{-24,32,11},WorldLayoutData.crossproduct(new float[]{5,1,8}, new float[]{4,3,0}), 0.01f);
        assertArrayEquals(new float[]{71,-23,-77},WorldLayoutData.crossproduct(new float[]{4,9,1}, new float[]{9,1,8}), 0.01f);
        assertArrayEquals(new float[]{26,-31,9},WorldLayoutData.crossproduct(new float[]{8,7,1}, new float[]{1,2,4}), 0.01f);
        assertArrayEquals(new float[]{21,10,-33},WorldLayoutData.crossproduct(new float[]{5,6,5}, new float[]{8,3,6}), 0.01f);
        assertArrayEquals(new float[]{0.4f,0.04f,-0.08f},WorldLayoutData.crossproduct(new float[]{0.0f,0.8f,0.4f}, new float[]{0.1f,0.0f,0.5f}), 0.01f);
        assertArrayEquals(new float[]{0.4f,-0.4f,0.25f},WorldLayoutData.crossproduct(new float[]{0.5f,0.5f,0.0f}, new float[]{0.1f,0.6f,0.8f}), 0.01f);
        assertArrayEquals(new float[]{-0.08f,-0.22f,0.24f},WorldLayoutData.crossproduct(new float[]{0.4f,0.4f,0.5f}, new float[]{0.2f,0.8f,0.8f}), 0.01f);

    }

    @Test
    public void normalise_isCorrect() {
        assertArrayEquals(new float[]{0,0,0},WorldLayoutData.normalise(new float[]{0.0f,0.0f,0.0f}), 0.00001f);
        assertArrayEquals(new float[]{-0.768221f, 0, 0.640184f},WorldLayoutData.normalise(new float[]{-48,0,40}), 0.00001f);
        assertArrayEquals(new float[]{-0.514496f, 0, 0.857493f},WorldLayoutData.normalise(new float[]{-12,0,20}), 0.00001f);
        assertArrayEquals(new float[]{-0.578523f, 0.771364f, 0.265156f},WorldLayoutData.normalise(new float[]{-24,32,11}), 0.00001f);
        assertArrayEquals(new float[]{0.662107f, -0.214485f, -0.71806f},WorldLayoutData.normalise(new float[]{71,-23,-77}), 0.00001f);
        assertArrayEquals(new float[]{0.62728f, -0.747911f, 0.217136f},WorldLayoutData.normalise(new float[]{26,-31,9}), 0.00001f);
        assertArrayEquals(new float[]{0.520146f, 0.247689f, -0.817373f},WorldLayoutData.normalise(new float[]{21,10,-33}), 0.00001f);
        assertArrayEquals(new float[]{0.9759f, 0.09759f, -0.19518f},WorldLayoutData.normalise(new float[]{0.4f,0.04f,-0.08f}), 0.00001f);
        assertArrayEquals(new float[]{0.646762f, -0.646762f, 0.404226f},WorldLayoutData.normalise(new float[]{0.4f,-0.4f,0.25f}), 0.00001f);
        assertArrayEquals(new float[]{-0.23862f, -0.656205f, 0.71586f}, WorldLayoutData.normalise(new float[]{-0.08f,-0.22f,0.24f}), 0.00001f);
    }

    @Test
    public void generate_isCorrect() {
        WorldLayoutData.setParameters(0,1,0,1,2,2,1);
        try {
            WorldLayoutData.setfunction("x");
            WorldLayoutData.generate();
        } catch(Exception e) {}
        assertArrayEquals(new float[]{0,0,0,0,0,1,1,1,0,0,0,1,1,1,1,1,1,0},WorldLayoutData.SURFACE_COORDS,0.00001f);
        WorldLayoutData.setParameters(0,2,0,2,3,3,1);
        try {
            WorldLayoutData.setfunction("x");
            WorldLayoutData.generate();
        } catch(Exception e) {}
        assertArrayEquals(new float[]{0,0,0,0,0,1,1,1,0,0,0,1,1,1,1,1,1,0,
                0,0,1,0,0,2,1,1,1,0,0,2,1,1,2,1,1,1,
                1,1,0,1,1,1,2,2,0,1,1,1,2,2,1,2,2,0,
                1,1,1,1,1,2,2,2,1,1,1,2,2,2,2,2,2,1},WorldLayoutData.SURFACE_COORDS,0.00001f);
        WorldLayoutData.setParameters(0,1,0,1,2,3,1);
        try {
            WorldLayoutData.setfunction("x");
            WorldLayoutData.generate();
        } catch(Exception e) {}
//        System.out.println(Arrays.toString(WorldLayoutData.SURFACE_COORDS));
        assertArrayEquals(new float[]{  0 ,0  ,0   ,0  ,0  ,0.5f,1 ,1  ,0   ,0  ,0  ,0.5f,1 ,1  ,0.5f,1 ,1  ,0  ,
                                        0 ,0  ,0.5f,0  ,0  ,1   ,1 ,1  ,0.5f,0  ,0  ,1   ,1 ,1  ,1   ,1 ,1  ,0.5f},WorldLayoutData.SURFACE_COORDS,0.00001f);
        WorldLayoutData.setParameters(5,7,1,4,3,4,1);
        try {
            WorldLayoutData.setfunction("x+y");
            WorldLayoutData.generate();
        } catch(Exception e) {}
        assertArrayEquals(new float[]{
                5,  6,  1,  5,  7,  2,  6,  7,  1,  5,  7,  2,  6,  8,  2,  6,  7,  1,
                5,  7,  2,  5,  8,  3,  6,  8,  2,  5,  8,  3,  6,  9,  3,  6,  8,  2,
                5,  8,  3,  5,  9,  4,  6,  9,  3,  5,  9,  4,  6,  10, 4,  6,  9,  3,
                6,  7,  1,  6,  8,  2,  7,  8,  1,  6,  8,  2,  7,  9,  2,  7,  8,  1,
                6,  8,  2,  6,  9,  3,  7,  9,  2,  6,  9,  3,  7,  10, 3,  7,  9,  2,
                6,  9,  3,  6,  10, 4,  7,  10, 3,  6,  10, 4,  7,  11, 4,  7,  10, 3
        },WorldLayoutData.SURFACE_COORDS,0.00001f);
    }
}