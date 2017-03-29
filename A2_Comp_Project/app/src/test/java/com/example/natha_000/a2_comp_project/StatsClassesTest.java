package com.example.natha_000.a2_comp_project;

import com.example.natha_000.a2_comp_project.Graphing_Stats.StatsClasses;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class StatsClassesTest {
    @Test
    public void contains_isCorrect() {
        StatsClasses testClass = new StatsClasses(0,10,3);

        //contains(float bound)
        assertEquals(true,testClass.contains(1));
        assertEquals(true,testClass.contains(5));
        assertEquals(true,testClass.contains(9));
        assertEquals(false,testClass.contains(10));
        assertEquals(false,testClass.contains(0));
        assertEquals(false,testClass.contains(-1));
        assertEquals(false,testClass.contains(11));

        //contains(float lb, float ub)
        assertEquals(true, testClass.contains(1,9));
        assertEquals(true, testClass.contains(2,8));
        assertEquals(true, testClass.contains(0,10));
        assertEquals(false, testClass.contains(5,11));
        assertEquals(false, testClass.contains(-1,9));
        assertEquals(false, testClass.contains(11,20));
        assertEquals(false, testClass.contains(-10,-1));

        // contains(StatsClasses class_)
        StatsClasses c1 = new StatsClasses(1,9,1);
        StatsClasses c2 = new StatsClasses(2,8,1);
        StatsClasses c3 = new StatsClasses(0,10,1);
        StatsClasses c4 = new StatsClasses(5,11,1);
        StatsClasses c5 = new StatsClasses(-1,9,1);
        StatsClasses c6 = new StatsClasses(11,20,1);
        StatsClasses c7 = new StatsClasses(-10,-1,1);
        assertEquals(true, testClass.contains(c1));
        assertEquals(true, testClass.contains(c2));
        assertEquals(true, testClass.contains(c3));
        assertEquals(false, testClass.contains(c4));
        assertEquals(false, testClass.contains(c5));
        assertEquals(false, testClass.contains(c6));
        assertEquals(false, testClass.contains(c7));

        StatsClasses.reset();
    }

    @Test
    public void surrounds_iscorrect() {
        StatsClasses testClass = new StatsClasses(0,10,3);
        //surrounds(float lb, float ub)
        assertEquals(true,testClass.surrounds(-1,11));
        assertEquals(true,testClass.surrounds(0,10));
        assertEquals(true,testClass.surrounds(-10,20));
        assertEquals(false,testClass.surrounds(-1,5));
        assertEquals(false,testClass.surrounds(5,11));
        assertEquals(false,testClass.surrounds(1,11));
        assertEquals(false,testClass.surrounds(1,9));

        //surrounds(StatsClasses other_)
        StatsClasses c1 = new StatsClasses(-1,11,1);
        StatsClasses c2 = new StatsClasses(0,10,1);
        StatsClasses c3 = new StatsClasses(-10,20,1);
        StatsClasses c4 = new StatsClasses(-1,5,1);
        StatsClasses c5 = new StatsClasses(5,11,1);
        StatsClasses c6 = new StatsClasses(1,11,1);
        StatsClasses c7 = new StatsClasses(1,9,1);
        assertEquals(true,testClass.surrounds(c1));
        assertEquals(true,testClass.surrounds(c2));
        assertEquals(true,testClass.surrounds(c3));
        assertEquals(false,testClass.surrounds(c4));
        assertEquals(false,testClass.surrounds(c5));
        assertEquals(false,testClass.surrounds(c6));
        assertEquals(false,testClass.surrounds(c7));

        StatsClasses.reset();
    }

    @Test
    public void validBound_isCorrect() {
        //Set up a model class
        new StatsClasses(0,10,5);
        new StatsClasses(10,20,10);
        new StatsClasses(20,40,15);
        new StatsClasses(40,55,10);
        new StatsClasses(55,100,7);

        assertEquals(true,StatsClasses.validBound(0));
        assertEquals(true,StatsClasses.validBound(100));
        assertEquals(true,StatsClasses.validBound(55));
        assertEquals(true,StatsClasses.validBound(-1));
        assertEquals(true,StatsClasses.validBound(-150));
        assertEquals(false,StatsClasses.validBound(5));
        assertEquals(false,StatsClasses.validBound(90));
        assertEquals(false,StatsClasses.validBound(41));

        StatsClasses.reset();
    }

    @Test
    public void validBounds_isCorrect() {
        //Set up a model class
        new StatsClasses(0,10,5);
        new StatsClasses(10,20,10);
        new StatsClasses(20,40,15);
        new StatsClasses(40,55,10);
        new StatsClasses(55,100,7);

        assertEquals(true,StatsClasses.validBounds(-10,0));
        assertEquals(true,StatsClasses.validBounds(100,150));
        assertEquals(true,StatsClasses.validBounds(-1,0));
        assertEquals(true,StatsClasses.validBounds(-150,0));
        assertEquals(false,StatsClasses.validBounds(5,15));
        assertEquals(false,StatsClasses.validBounds(55,100));
        assertEquals(false,StatsClasses.validBounds(0,100));
        assertEquals(false,StatsClasses.validBounds(45,60));

        StatsClasses.reset();
    }

    @Test
    public void findClassIndexById_isCorrect() {
        StatsClasses c1 = new StatsClasses(0,10,1);
        StatsClasses c2 = new StatsClasses(10,20,1);
        StatsClasses c3 = new StatsClasses(20,30,1);
        StatsClasses c4 = new StatsClasses(30,40,1);
        StatsClasses c5 = new StatsClasses(40,50,1);
        StatsClasses c6 = new StatsClasses(50,60,1);
        StatsClasses c7 = new StatsClasses(60,70,1);
        StatsClasses c8 = new StatsClasses(70,80,1);

        assertEquals(2,StatsClasses.findClassIndexById(c3.id));
        assertEquals(7,StatsClasses.findClassIndexById(c8.id));
        assertEquals(-1,StatsClasses.findClassIndexById(-1));
        c4.delete();
        assertEquals(2,StatsClasses.findClassIndexById(c3.id));
        assertEquals(6,StatsClasses.findClassIndexById(c8.id));
        assertEquals(-1,StatsClasses.findClassIndexById(c4.id));
        assertEquals(-1,StatsClasses.findClassIndexById(-1));

        StatsClasses.reset();
    }

    @Test
    public void getClassById_isCorrect() {
        StatsClasses c1 = new StatsClasses(0,10,1);
        StatsClasses c2 = new StatsClasses(10,20,1);
        StatsClasses c3 = new StatsClasses(20,30,1);
        StatsClasses c4 = new StatsClasses(30,40,1);
        StatsClasses c5 = new StatsClasses(40,50,1);
        StatsClasses c6 = new StatsClasses(50,60,1);
        StatsClasses c7 = new StatsClasses(60,70,1);
        StatsClasses c8 = new StatsClasses(70,80,1);

        assertEquals(c3,StatsClasses.getClassById(c3.id));
        assertEquals(c8,StatsClasses.getClassById(c8.id));
        assertEquals(null,StatsClasses.getClassById(-1));
        c4.delete();
        assertEquals(c3,StatsClasses.getClassById(c3.id));
        assertEquals(c8,StatsClasses.getClassById(c8.id));
        assertEquals(null,StatsClasses.getClassById(c4.id));
        assertEquals(null,StatsClasses.getClassById(-1));

        StatsClasses.reset();
    }

    @Test
    public void findClassIndexByLb_isCorrect() {
        StatsClasses c1 = new StatsClasses(0,10,1);
        StatsClasses c2 = new StatsClasses(10,20,1);
        StatsClasses c3 = new StatsClasses(20,30,1);
        StatsClasses c4 = new StatsClasses(30,40,1);
        StatsClasses c5 = new StatsClasses(40,50,1);
        StatsClasses c6 = new StatsClasses(50,60,1);
        StatsClasses c7 = new StatsClasses(60,70,1);
        StatsClasses c8 = new StatsClasses(70,80,1);

        assertEquals(2,StatsClasses.findClassIndexByLb(c3.lowerbound));
        assertEquals(7,StatsClasses.findClassIndexByLb(c8.lowerbound));
        assertEquals(-1,StatsClasses.findClassIndexByLb(-1));
        c4.delete();
        assertEquals(2,StatsClasses.findClassIndexByLb(c3.lowerbound));
        assertEquals(6,StatsClasses.findClassIndexByLb(c8.lowerbound));
        assertEquals(-1,StatsClasses.findClassIndexByLb(c4.lowerbound));
        assertEquals(-1,StatsClasses.findClassIndexByLb(-1));

        StatsClasses.reset();
    }

    @Test
    public void getClassByLb_isCorrect() {
        StatsClasses c1 = new StatsClasses(0,10,1);
        StatsClasses c2 = new StatsClasses(10,20,1);
        StatsClasses c3 = new StatsClasses(20,30,1);
        StatsClasses c4 = new StatsClasses(30,40,1);
        StatsClasses c5 = new StatsClasses(40,50,1);
        StatsClasses c6 = new StatsClasses(50,60,1);
        StatsClasses c7 = new StatsClasses(60,70,1);
        StatsClasses c8 = new StatsClasses(70,80,1);

        assertEquals(c3,StatsClasses.getClassByLb(c3.lowerbound));
        assertEquals(c8,StatsClasses.getClassByLb(c8.lowerbound));
        assertEquals(null,StatsClasses.getClassByLb(-1));
        c4.delete();
        assertEquals(c3,StatsClasses.getClassByLb(c3.lowerbound));
        assertEquals(c8,StatsClasses.getClassByLb(c8.lowerbound));
        assertEquals(null,StatsClasses.getClassByLb(c4.lowerbound));
        assertEquals(null,StatsClasses.getClassByLb(-1));

        StatsClasses.reset();
    }

    @Test
    public void mergeSort_isCorrect() {
        assertEquals(new ArrayList<StatsClasses>(),StatsClasses.getSortedClasses());
        StatsClasses c1 = new StatsClasses(0,10,1);
        assertEquals(new ArrayList<StatsClasses>(Arrays.asList(c1)),StatsClasses.getSortedClasses());
        StatsClasses c2 = new StatsClasses(10,20,1);
        StatsClasses c3 = new StatsClasses(20,30,1);
        StatsClasses c4 = new StatsClasses(30,40,1);
        StatsClasses c5 = new StatsClasses(40,50,1);
        assertEquals(new ArrayList<StatsClasses>(Arrays.asList(c1,c2,c3,c4,c5)),StatsClasses.getSortedClasses());
        StatsClasses.reset();
        c2 = new StatsClasses(10,20,1);
        c1 = new StatsClasses(0,10,1);
        assertEquals(new ArrayList<StatsClasses>(Arrays.asList(c1,c2)),StatsClasses.getSortedClasses());
        c4 = new StatsClasses(30,40,1);
        c5 = new StatsClasses(40,50,1);
        c3 = new StatsClasses(20,30,1);
        assertEquals(new ArrayList<StatsClasses>(Arrays.asList(c1,c2,c3,c4,c5)),StatsClasses.getSortedClasses());
        StatsClasses.reset();
        c5 = new StatsClasses(40,50,1);
        c4 = new StatsClasses(30,40,1);
        c3 = new StatsClasses(20,30,1);
        assertEquals(new ArrayList<StatsClasses>(Arrays.asList(c3,c4,c5)),StatsClasses.getSortedClasses());
        c2 = new StatsClasses(10,20,1);
        c1 = new StatsClasses(0,10,1);
        assertEquals(new ArrayList<StatsClasses>(Arrays.asList(c1,c2,c3,c4,c5)),StatsClasses.getSortedClasses());
    }
}