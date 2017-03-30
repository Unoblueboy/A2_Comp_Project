package com.example.natha_000.a2_comp_project;

import com.example.natha_000.a2_comp_project.Graphing_3D.GraphData;
import com.example.natha_000.a2_comp_project.Graphing_3D.Function;

import org.junit.Test;

import java.util.Arrays;

import static com.example.natha_000.a2_comp_project.Graphing_3D.GraphData.function_creator;
import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class GraphDataTest {
    @Test
    public void function_creator_isCorrect() throws Exception {
        Function function1 = function_creator("x");
        Function function2 = function_creator("x + y");
        Function function3 = function_creator("x + y * x");
        Function function4 = function_creator("1 + 2");
        Function function5 = function_creator("3 + 4 * 5 / 6");
        Function function6 = function_creator("3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3");
        Function function7 = function_creator("sin ( 3 / 3 * 3.1415 )");
        Function function8 = function_creator("tanh ( x + ( y + 1 ) ^ 5 )");
        Function function9 = function_creator("sin ( 4 * x + y ) / cos ( 4 ^ y - x )");
        assertEquals(0      ,   function1.evalfunc( 0   ,   0   )   ,0.00001);
        assertEquals(0      ,   function1.evalfunc( 0   ,   1   )   ,0.00001);
        assertEquals(1      ,   function1.evalfunc( 1   ,   0   )   ,0.00001);
        assertEquals(1      ,   function1.evalfunc( 1   ,   1   )   ,0.00001);
        assertEquals(0      ,   function1.evalfunc( 0   ,   -1  )   ,0.00001);
        assertEquals(-1     ,   function1.evalfunc( -1  ,   0   )   ,0.00001);
        assertEquals(-1     ,   function1.evalfunc( -1  ,   -1  )   ,0.00001);
        assertEquals(-1     ,   function1.evalfunc( -1  ,   1   )   ,0.00001);
        assertEquals(1      ,   function1.evalfunc( 1   ,   -1  )   ,0.00001);
        assertEquals(100    ,   function1.evalfunc( 100 ,   100 )   ,0.00001);
        assertEquals(-100   ,   function1.evalfunc( -100,   -100)   ,0.00001);
        assertEquals(0      ,   function2.evalfunc( 0   ,   0   )   ,0.00001);
        assertEquals(1      ,   function2.evalfunc( 0   ,   1   )   ,0.00001);
        assertEquals(1      ,   function2.evalfunc( 1   ,   0   )   ,0.00001);
        assertEquals(2      ,   function2.evalfunc( 1   ,   1   )   ,0.00001);
        assertEquals(-1     ,   function2.evalfunc( 0   ,   -1  )   ,0.00001);
        assertEquals(-1     ,   function2.evalfunc( -1  ,   0   )   ,0.00001);
        assertEquals(-2     ,   function2.evalfunc( -1  ,   -1  )   ,0.00001);
        assertEquals(0      ,   function2.evalfunc( -1  ,   1   )   ,0.00001);
        assertEquals(0      ,   function2.evalfunc( 1   ,   -1  )   ,0.00001);
        assertEquals(200    ,   function2.evalfunc( 100 ,   100 )   ,0.00001);
        assertEquals(-200   ,   function2.evalfunc( -100,   -100)   ,0.00001);
        assertEquals(0      ,   function3.evalfunc( 0   ,   0   )   ,0.00001);
        assertEquals(0      ,   function3.evalfunc( 0   ,   1   )   ,0.00001);
        assertEquals(1      ,   function3.evalfunc( 1   ,   0   )   ,0.00001);
        assertEquals(2      ,   function3.evalfunc( 1   ,   1   )   ,0.00001);
        assertEquals(0      ,   function3.evalfunc( 0   ,   -1  )   ,0.00001);
        assertEquals(-1     ,   function3.evalfunc( -1  ,   0   )   ,0.00001);
        assertEquals(0      ,   function3.evalfunc( -1  ,   -1  )   ,0.00001);
        assertEquals(-2     ,   function3.evalfunc( -1  ,   1   )   ,0.00001);
        assertEquals(0      ,   function3.evalfunc( 1   ,   -1  )   ,0.00001);
        assertEquals(10100  ,   function3.evalfunc( 100 ,   100 )   ,0.00001);
        assertEquals(9900   ,   function3.evalfunc( -100,   -100)   ,0.00001);
        assertEquals(3      ,   function4.evalfunc( 0   ,   0   )   ,0.00001);
        assertEquals(3      ,   function4.evalfunc( 0   ,   1   )   ,0.00001);
        assertEquals(3      ,   function4.evalfunc( 1   ,   0   )   ,0.00001);
        assertEquals(3      ,   function4.evalfunc( 1   ,   1   )   ,0.00001);
        assertEquals(3      ,   function4.evalfunc( 0   ,   -1  )   ,0.00001);
        assertEquals(3      ,   function4.evalfunc( -1  ,   0   )   ,0.00001);
        assertEquals(3      ,   function4.evalfunc( -1  ,   -1  )   ,0.00001);
        assertEquals(3      ,   function4.evalfunc( -1  ,   1   )   ,0.00001);
        assertEquals(3      ,   function4.evalfunc( 1   ,   -1  )   ,0.00001);
        assertEquals(3      ,   function4.evalfunc( 100 ,   100 )   ,0.00001);
        assertEquals(3      ,   function4.evalfunc( -100,   -100)   ,0.00001);
        assertEquals(6.33333,   function5.evalfunc( 0   ,   0   )   ,0.00001);
        assertEquals(6.33333,   function5.evalfunc( 0   ,   1   )   ,0.00001);
        assertEquals(6.33333,   function5.evalfunc( 1   ,   0   )   ,0.00001);
        assertEquals(6.33333,   function5.evalfunc( 1   ,   1   )   ,0.00001);
        assertEquals(6.33333,   function5.evalfunc( 0   ,   -1  )   ,0.00001);
        assertEquals(6.33333,   function5.evalfunc( -1  ,   0   )   ,0.00001);
        assertEquals(6.33333,   function5.evalfunc( -1  ,   -1  )   ,0.00001);
        assertEquals(6.33333,   function5.evalfunc( -1  ,   1   )   ,0.00001);
        assertEquals(6.33333,   function5.evalfunc( 1   ,   -1  )   ,0.00001);
        assertEquals(6.33333,   function5.evalfunc( 100 ,   100 )   ,0.00001);
        assertEquals(6.33333,   function5.evalfunc( -100,   -100)   ,0.00001);
        assertEquals(3.00012,   function6.evalfunc( 0   ,   0   )   ,0.00001);
        assertEquals(3.00012,   function6.evalfunc( 0   ,   1   )   ,0.00001);
        assertEquals(3.00012,   function6.evalfunc( 1   ,   0   )   ,0.00001);
        assertEquals(3.00012,   function6.evalfunc( 1   ,   1   )   ,0.00001);
        assertEquals(3.00012,   function6.evalfunc( 0   ,   -1  )   ,0.00001);
        assertEquals(3.00012,   function6.evalfunc( -1  ,   0   )   ,0.00001);
        assertEquals(3.00012,   function6.evalfunc( -1  ,   -1  )   ,0.00001);
        assertEquals(3.00012,   function6.evalfunc( -1  ,   1   )   ,0.00001);
        assertEquals(3.00012,   function6.evalfunc( 1   ,   -1  )   ,0.00001);
        assertEquals(3.00012,   function6.evalfunc( 100 ,   100 )   ,0.00001);
        assertEquals(3.00012,   function6.evalfunc( -100,   -100)   ,0.00001);
        assertEquals(0.00009,   function7.evalfunc( 0   ,   0   )   ,0.00001);
        assertEquals(0.00009,   function7.evalfunc( 0   ,   1   )   ,0.00001);
        assertEquals(0.00009,   function7.evalfunc( 1   ,   0   )   ,0.00001);
        assertEquals(0.00009,   function7.evalfunc( 1   ,   1   )   ,0.00001);
        assertEquals(0.00009,   function7.evalfunc( 0   ,   -1  )   ,0.00001);
        assertEquals(0.00009,   function7.evalfunc( -1  ,   0   )   ,0.00001);
        assertEquals(0.00009,   function7.evalfunc( -1  ,   -1  )   ,0.00001);
        assertEquals(0.00009,   function7.evalfunc( -1  ,   1   )   ,0.00001);
        assertEquals(0.00009,   function7.evalfunc( 1   ,   -1  )   ,0.00001);
        assertEquals(0.00009,   function7.evalfunc( 100 ,   100 )   ,0.00001);
        assertEquals(0.00009,   function7.evalfunc( -100,   -100)   ,0.00001);
        assertEquals(0.76159,   function8.evalfunc( 0   ,   0   )   ,0.00001);
        assertEquals(1      ,   function8.evalfunc( 0   ,   1   )   ,0.00001);
        assertEquals(0.96402,   function8.evalfunc( 1   ,   0   )   ,0.00001);
        assertEquals(1      ,   function8.evalfunc( 1   ,   1   )   ,0.00001);
        assertEquals(0      ,   function8.evalfunc( 0   ,   -1  )   ,0.00001);
        assertEquals(0      ,   function8.evalfunc( -1  ,   0   )   ,0.00001);
        assertEquals(-0.76159,   function8.evalfunc( -1  ,   -1  )   ,0.00001);
        assertEquals(1      ,   function8.evalfunc( -1  ,   1   )   ,0.00001);
        assertEquals(0.76159,   function8.evalfunc( 1   ,   -1  )   ,0.00001);
        assertEquals(1      ,   function8.evalfunc( 100 ,   100 )   ,0.00001);
        assertEquals(-1     ,   function8.evalfunc( -100,   -100)   ,0.00001);
        assertEquals(0      ,   function9.evalfunc( 0   ,   0   )   ,0.00001);
        assertEquals(-1.28735,   function9.evalfunc( 0   ,   1   )   ,0.00001);
        assertEquals(-0.75680,   function9.evalfunc( 1   ,   0   )   ,0.00001);
        assertEquals(0.96861,   function9.evalfunc( 1   ,   1   )   ,0.00001);
        assertEquals(-0.86847,   function9.evalfunc( 0   ,   -1  )   ,0.00001);
        assertEquals(-1.81859,   function9.evalfunc( -1  ,   0   )   ,0.00001);
        assertEquals(3.04109,   function9.evalfunc( -1  ,   -1  )   ,0.00001);
        assertEquals(-0.49749,   function9.evalfunc( -1  ,   1   )   ,0.00001);
        assertEquals(0.19286,   function9.evalfunc( 1   ,   -1  )   ,0.00001);
        assertEquals(1.54638,   function9.evalfunc( 100 ,   10  )   ,0.00001);
        assertEquals(0.54246,   function9.evalfunc( -100,   -100)   ,0.00001);
    }

    @Test
    public void tokenise_isCorrect() throws Exception {
        String test1 = "x";
        String test2 = "x + y";
        String test3 = "x    +    y";
        String test4 = "3 + 4 * 2 / ( 1 – 5 ) ^ 2 ^ 3";
        String test5 = "sin ( 3 / 3 * 3.1415 )";
        String test6 = "sin ( x + ( y + 1 ) ^ 5 )";
        assertEquals(Arrays.asList(new String[]{"x"}), GraphData.tokenise(test1));
        assertEquals(Arrays.asList(new String[]{"x","+","y"}), GraphData.tokenise(test2));
        assertEquals(Arrays.asList(new String[]{"x","+","y"}), GraphData.tokenise(test3));
        assertEquals(Arrays.asList(new String[]{"3.0", "+", "4.0", "*", "2.0", "/", "(", "1.0", "–", "5.0", ")", "^", "2.0", "^", "3.0"}), GraphData.tokenise(test4));
        assertEquals(Arrays.asList(new String[]{"sin", "(", "3.0", "/", "3.0", "*", "3.1415", ")"}), GraphData.tokenise(test5));
        assertEquals(Arrays.asList(new String[]{"sin", "(",  "x", "+", "(", "y", "+", "1.0", ")", "^", "5.0", ")"}), GraphData.tokenise(test6));
    }

    @Test
    public void format_isCorrect() {
        String test1 = "e";
        String test2 = "pi";
        String test3 = "ex";
        String test4 = "pix";
        String test5 = "2pix";
        String test6 = "2x";
        String test7 = "e^x";
        String test8 = "pi^(2x)^2";
        String test9 = "2sin(pix)";
        String test10= "x + y";
        String test11= "3 + 4 * 2 / ( 1 – e ) ^ 2 ^ 3";
        String test12= "esin ( 3 / 3 * pi )";
        String test13= "sin ( 2pi + ( y + 1 ) ^ e )";
        String test14= "e     x";
        String test15= "-x";
        String test16= "sin(-x)";
        String test17= "cos(   -   x-y)";
        assertEquals("2.718281828459045",GraphData.format(test1));
        assertEquals("3.141592653589793",GraphData.format(test2));
        assertEquals("2.718281828459045 * x",GraphData.format(test3));
        assertEquals("3.141592653589793 * x",GraphData.format(test4));
        assertEquals("2 * 3.141592653589793 * x",GraphData.format(test5));
        assertEquals("2 * x",GraphData.format(test6));
        assertEquals("2.718281828459045^x",GraphData.format(test7));
        assertEquals("3.141592653589793^(2 * x)^2",GraphData.format(test8));
        assertEquals("2 * sin(3.141592653589793 * x)",GraphData.format(test9));
        assertEquals("x + y",GraphData.format(test10));
        assertEquals("3 + 4 * 2 / ( 1 – 2.718281828459045 ) ^ 2 ^ 3",GraphData.format(test11));
        assertEquals("2.718281828459045 * sin ( 3 / 3 * 3.141592653589793 )",GraphData.format(test12));
        assertEquals("sin ( 2 * 3.141592653589793 + ( y + 1 ) ^ 2.718281828459045 )",GraphData.format(test13));
        assertEquals("2.718281828459045 * x",GraphData.format(test14));
        assertEquals("0 - x",GraphData.format(test15));
        assertEquals("sin( 0 - x)",GraphData.format(test16));
        assertEquals("cos( 0 - x-y)",GraphData.format(test17));
    }
}