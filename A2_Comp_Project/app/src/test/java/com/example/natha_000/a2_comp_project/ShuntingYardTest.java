package com.example.natha_000.a2_comp_project;

import com.example.natha_000.a2_comp_project.Graphing_3D.GraphData;
import com.example.natha_000.a2_comp_project.Graphing_3D.ShuntingYard;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ShuntingYardTest {
    @Test
    public void opsCheck_isCorrect() throws Exception {
        assertEquals(true,ShuntingYard.opsCheck("+","-"));
        assertEquals(false,ShuntingYard.opsCheck("^","-"));
        assertEquals(true,ShuntingYard.opsCheck("-","*"));
        assertEquals(true,ShuntingYard.opsCheck("+","^"));
        assertEquals(false,ShuntingYard.opsCheck("*","-"));
        assertEquals(false,ShuntingYard.opsCheck("^","+"));
        assertEquals(true,ShuntingYard.opsCheck("-","-"));
        assertEquals(false,ShuntingYard.opsCheck("^","*"));
        assertEquals(false,ShuntingYard.opsCheck("^","^"));
    }

    @Test
    public void isAlgebraic_isCorrect() throws Exception {
        assertEquals(false,ShuntingYard.isAlgebraic(""));
        assertEquals(true,ShuntingYard.isAlgebraic("x"));
        assertEquals(true,ShuntingYard.isAlgebraic("y"));
        assertEquals(false,ShuntingYard.isAlgebraic("xy"));
        assertEquals(false,ShuntingYard.isAlgebraic("a"));
        assertEquals(true,ShuntingYard.isAlgebraic("1"));
        assertEquals(true,ShuntingYard.isAlgebraic("123"));
        assertEquals(true,ShuntingYard.isAlgebraic("1.23"));
        assertEquals(false,ShuntingYard.isAlgebraic("1.2.3"));
        assertEquals(true,ShuntingYard.isAlgebraic("-1"));
        assertEquals(true,ShuntingYard.isAlgebraic("-1.23"));
        assertEquals(false,ShuntingYard.isAlgebraic("-1.2.3"));
    }

    @Test
    public void isFunction_isCorrect() throws Exception {
        assertEquals(true,ShuntingYard.isFunction("sin"));
        assertEquals(true,ShuntingYard.isFunction("cos"));
        assertEquals(false,ShuntingYard.isFunction("s"));
        assertEquals(false,ShuntingYard.isFunction("1"));
        assertEquals(true,ShuntingYard.isFunction("max2"));
        assertEquals(false,ShuntingYard.isFunction("x"));
        assertEquals(false,ShuntingYard.isFunction("y"));
        assertEquals(false,ShuntingYard.isFunction(")"));
        assertEquals(false,ShuntingYard.isFunction("("));
        assertEquals(false,ShuntingYard.isFunction("abc)abc"));
    }


    @Test
    public void shuntingYard_isCorrect() throws Exception {
        List<String> test1 = GraphData.tokenise("x");
        List<String> test2 = GraphData.tokenise("x + y");
        List<String> test3 = GraphData.tokenise("x + y * x");
        List<String> test4 = GraphData.tokenise("1 + 2");
        List<String> test5 = GraphData.tokenise("3 + 4 * 5 / 6");
        List<String> test6 = GraphData.tokenise("3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3");
        List<String> test7 = GraphData.tokenise("sin ( 3 / 3 * 3.1415 )");
        List<String> test8 = GraphData.tokenise("sin ( x + ( y + 1 ) ^ 5 )");
        List<String> test9 = GraphData.tokenise("sin ( 4 * x + y ) / cos ( 4 ^ y - x )");
        System.out.println(ShuntingYard.shuntingYard(test1));
        assertEquals("x", ShuntingYard.shuntingYard(test1));
        assertEquals("x y +", ShuntingYard.shuntingYard(test2));
        assertEquals("x y x * +", ShuntingYard.shuntingYard(test3));
        assertEquals("1.0 2.0 +", ShuntingYard.shuntingYard(test4));
        assertEquals("3.0 4.0 5.0 * 6.0 / +", ShuntingYard.shuntingYard(test5));
        assertEquals("3.0 4.0 2.0 * 1.0 5.0 - 2.0 3.0 ^ ^ / +", ShuntingYard.shuntingYard(test6));
        assertEquals("3.0 3.0 / 3.1415 * sin", ShuntingYard.shuntingYard(test7));
        assertEquals("x y 1.0 + 5.0 ^ + sin", ShuntingYard.shuntingYard(test8));
        assertEquals("4.0 x * y + sin 4.0 y ^ x - cos /", ShuntingYard.shuntingYard(test9));
    }
}