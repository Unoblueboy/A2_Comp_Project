package com.example.natha_000.a2_comp_project;

import com.example.natha_000.a2_comp_project.Graphing_Stats.GraphingMethods;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

/**
 * Created by Natha_000 on 28/03/2017.
 */

public class GraphingMethodsTest {
    @Test
    public void sigfigs_isCorrect() {
        assertEquals("0",GraphingMethods.sigfigs(0,2));
        assertEquals("25",GraphingMethods.sigfigs(25,4));
        assertEquals("-25",GraphingMethods.sigfigs(-25,4));
        assertEquals("250",GraphingMethods.sigfigs(251,2));
        assertEquals("250",GraphingMethods.sigfigs(250,2));
        assertEquals("30.12",GraphingMethods.sigfigs(30.1172342f,4));
        assertEquals("-30.12",GraphingMethods.sigfigs(-30.1172342f,4));
        assertEquals("25",GraphingMethods.sigfigs(25.0000000f,4));
    }

    @Test
    public void getMin_isCorrect() {
        assertEquals(0f,GraphingMethods.getMin(new float[]{0,1,2,3,4}));
        assertEquals(0f,GraphingMethods.getMin(new float[]{1,3,2,0,4}));
        assertEquals(0f,GraphingMethods.getMin(new float[]{1,3,2,4,0}));
        assertEquals(-4f,GraphingMethods.getMin(new float[]{-1,-3,-2,-4,0}));
        assertEquals(0f,GraphingMethods.getMin(new float[]{0.1f,0.3f,0.2f,0.4f,0}));
        assertEquals(-0.4f,GraphingMethods.getMin(new float[]{-0.1f,-0.3f,-0.2f,-0.4f,0}));
        assertEquals(0f,GraphingMethods.getMin(new float[]{0}));
        assertEquals(Float.NaN,GraphingMethods.getMin(new float[]{}));
    }

    @Test
    public void getMax_isCorrect() {
        assertEquals(4f,GraphingMethods.getMax(new float[]{0,1,2,3,4}));
        assertEquals(4f,GraphingMethods.getMax(new float[]{1,3,2,0,4}));
        assertEquals(4f,GraphingMethods.getMax(new float[]{1,3,2,4,0}));
        assertEquals(0f,GraphingMethods.getMax(new float[]{-1,-3,-2,-4,0}));
        assertEquals(0.4f,GraphingMethods.getMax(new float[]{0.1f,0.3f,0.2f,0.4f,0}));
        assertEquals(0f,GraphingMethods.getMax(new float[]{-0.1f,-0.3f,-0.2f,-0.4f,0}));
        assertEquals(0f,GraphingMethods.getMax(new float[]{0}));
        assertEquals(Float.NaN,GraphingMethods.getMax(new float[]{}));
    }

    @Test
    public void scaling_isCorrect() {
        assertArrayEquals(new float[]{0,5,1,6}, GraphingMethods.scaling(0,5,6),0.00001f);
        assertArrayEquals(new float[]{0.5f,1,0.1f,6}, GraphingMethods.scaling(0.5f,1,6),0.00001f);
        assertArrayEquals(new float[]{0.6f,0.9f,0.05f,7}, GraphingMethods.scaling(0.6f,0.9f,6),0.00001f);
        assertArrayEquals(new float[]{100,200,25,5}, GraphingMethods.scaling(100,200,5),0.00001f);
        assertArrayEquals(new float[]{75,525,75,7}, GraphingMethods.scaling(100,500,7),0.00001f);
    }
}