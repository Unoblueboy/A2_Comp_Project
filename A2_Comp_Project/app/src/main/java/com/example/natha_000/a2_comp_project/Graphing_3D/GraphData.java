package com.example.natha_000.a2_comp_project.Graphing_3D;

/**
 * Created by Natha_000 on 02/01/2017.
 */

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class is used to generate an evaluatable function from a string function
 * It uses the Function interface to allow for easy composition of functions.
*/
public class GraphData {

    //The following code defines a large array of simple arithmetic functions

    //This defines the arithmetic operation of addition
    private static Function add = (float x, float y) -> {
        float z = x+y;
        return z;
    };

    //This defines the arithmetic operation of subtraction
    private static Function sub = (float x, float y) -> {
        float z = x-y;
        return z;
    };

    //This defines the arithmetic operation of multiplication
    private static Function mul = (float x, float y) -> {
        float z = x*y;
        return z;
    };

    //This defines the arithmetic operation of Division
    private static Function div = (float x, float y) -> {
        float z = x/y;
        return z;
    };

    //This defines the arithmetic operation of exponentiation
    private static Function power = (float x, float y) -> {
        float z = (float) Math.pow(x,y);
        if (z!=z) {
            return 0;
        }
        return z;
    };

    //This calculates the sine of the first input
    private static Function sin = (float x, float y) -> {
        float z = (float) Math.sin(x);
        return z;
    };

    //This calculates the cosine of the first input
    private static Function cos = (float x, float y) -> {
        float z = (float) Math.cos(x);
        return z;
    };

    //This calculates the tangent of the first input
    private static Function tan = (float x, float y) -> {
        float z = (float) Math.tan(x);
        return z;
    };

    //This calculates the cosecant (=1/sine) of the first input
    private static Function cosec = (float x, float y) -> {
        float z = (float) Math.sin(x);
        return 1/z;
    };

    //This calculates the secant (=1/cosine) of the first input
    private static Function sec = (float x, float y) -> {
        float z = (float) Math.cos(x);
        return 1/z;
    };

    //This calculates the cotangent (=1/tangent) of the first input
    private static Function cot = (float x, float y) -> {
        float z = (float) Math.tan(x);
        return 1/z;
    };

    //This calculates the hyperbolic sine of the first input
    private static Function sinh = (float x, float y) -> {
        float z = (float) Math.sinh(x);
        return z;
    };

    //This calculates the hyperbolic cosine of the first input
    private static Function cosh = (float x, float y) -> {
        float z = (float) Math.cosh(x);
        return z;
    };

    //This calculates the hyperbolic tangent of the first input
    private static Function tanh = (float x, float y) -> {
        float z = (float) Math.tanh(x);
        return z;
    };

    //This calculates the inverse sine of the first input
    private static Function asin = (float x, float y) -> {
        float z = (float) Math.asin(x);
        return z;
    };

    //This calculates the inverse cosine of the first input
    private static Function acos = (float x, float y) -> {
        float z = (float) Math.acos(x);
        return z;
    };

    //This calculates the inverse tangent of the first input
    private static Function atan = (float x, float y) -> {
        float z = (float) Math.atan(x);
        return z;
    };

    //This calculates the greatest integer less than or equal to the first input
    private static Function floor = (float x, float y) -> {
        float z = (float) Math.floor(x);
        return z;
    };

    //This calculates the least integer greater than or equal to the first input
    private static Function ceil = (float x, float y) -> {
        float z = (float) Math.ceil(x);
        return z;
    };

    //This calculates the natural logarithm of the first input
    private static Function ln = (float x, float y) -> {
        float z = (float) Math.log(x);
        return z;
    }; //log

    //This calculates the logarithm base 10 of the first input
    private static Function log = (float x, float y) -> {
        float z = (float) Math.log10(x);
        return z;
    }; //log10

    //This rounds the first input to the nearest integer
    private static Function round = (float x, float y) -> {
        float z = (float) Math.round(x);
        return z;
    };

    //This calculates the sign of the first input(positive = 1, negative = -1, zero = 0)
    private static Function signum = (float x, float y) -> {
        float z = (float) Math.signum(x);
        return z;
    };

    //This calculates the absolute value of the first input
    private static Function abs = (float x, float y) -> {
        float z = (float) Math.abs(x);
        return z;
    };

    //This calculates the square root of the first input
    private static Function sqrt = (float x, float y) -> {
        float z = (float) Math.sqrt(x);
        return z;
    };

    //This maps all of the functions to corresponding strings
    private static Map<String, Function> ops_func = new HashMap<String, Function>(){{
        put("+",add);
        put("-",sub);
        put("*",mul);
        put("/",div);
        put("^",power);
        put("sin",sin);
        put("cos",cos);
        put("tan",tan);
        put("cosec",cosec);
        put("csc",cosec);
        put("sec",sec);
        put("cot",cot);
        put("sinh",sinh);
        put("cosh",cosh);
        put("tanh",tanh);
        put("asin",asin);
        put("arcsin",asin);
        put("acos",acos);
        put("arccos",acos);
        put("atan",atan);
        put("arctan",atan);
        put("floor",floor);
        put("ceil",ceil);
        put("ln",ln);
        put("log",log);
        put("round",round);
        put("signum",signum);
        put("abs",abs);
        put("sqrt",sqrt);
        put("√",sqrt);
    }};

    public static String format(String func){
        return func.replaceAll("(\\d+(\\.\\d+)?)\\s*(e|pi)","$1 * $3")
                .replaceAll("(e|pi)\\s*(e|pi)","$1 * $2")
                .replaceAll("(e|pi|\\d+(\\.\\d+)?)\\s*(\\w)","$1 * $3")
                .replaceAll("e",Double.toString(Math.E))
                .replaceAll("pi",Double.toString(Math.PI))
                .replaceAll("(^|\\D) \\s* - \\s* (\\w) ","$1 0 - $0");

    }


    /**
     * This creates a mathematical function into which a value of x and y
     * can be entered.
     * @param func A string which represents the function to be evaluated
     * @return a Function object which can be used to evaluate the function for
     * different x and y
    */
    public static Function function_creator(String func) throws IOException{
        //This uses the function input and converts it into a string of tokens
        ShuntingYard parser = new ShuntingYard();
        List<String> tokens = tokenise(func);
        List<String> ops = tokenise(parser.shuntingYard(tokens));

        //This sets up the variables to be used within the process of parsing the reverse polish notation
        StringBuilder output = new StringBuilder();
        Deque<Object> stack  = new LinkedList<>();

        //For each of the inputs in the token list
        for (String token : ops) {
            //If the token is a number or a variable add it to the stack
            if (parser.isAlgebraic(token)) {
                try {
                    stack.push( Float.parseFloat(token));
                } catch (Exception e) {
                    stack.push(token);
                }

            //The token is an operator
            } else {
                /* If the operator takes two arguments
                   pop two functions from the stack,
                   evaluate the operator with the arguments as the two functions,
                   and push the result to the stack. */
                if (ShuntingYard.ops_2_arg.containsKey(token)) {
                    Object arg2 = (Object) stack.pop();
                    Object arg1 = (Object) stack.pop();

                    /* Case 1:
                        The first argument is a float
                        The second argument is a float
                    */
                    if (arg2 instanceof Float && arg1 instanceof Float) {
                        float v1 = (float) arg1;
                        float v2 = (float) arg2;
                        Function a = (float x, float y) -> {
                            float z = ops_func.get(token).evalfunc(v1,v2);
                            return z;
                        };
                        stack.push(a);
                    }

                    /* Case 2:
                    The first argument is a float
                    The second argument is a function
                    */
                    else if (arg2 instanceof Function && arg1 instanceof Float) {
                        float v1 = (float) arg1;
                        Function f1 = (Function) arg2;
                        Function a = (float x, float y) -> {
                            float z = ops_func.get(token).evalfunc(v1,f1.evalfunc(x,y));
                            return z;
                        };
                        stack.push(a);
                    }

                    /* Case 3:
                    The first argument is a function
                    The second argument is a float
                    */
                    else if (arg1 instanceof Function && arg2 instanceof Float) {
                        float v1 = (float) arg2;
                        Function f1 = (Function) arg1;
                        Function a = (float x, float y) -> {
                            float z = ops_func.get(token).evalfunc(f1.evalfunc(x,y),v1);
                            return z;
                        };
                        stack.push(a);
                    }

                    /* Case 4:
                    The first argument is a function
                    The second argument is a function
                    */
                    else if (arg1 instanceof Function && arg2 instanceof Function) {
                        Function f2 = (Function) arg2;
                        Function f1 = (Function) arg1;
                        Function a = (float x, float y) -> {
                            float z = ops_func.get(token).evalfunc(f1.evalfunc(x,y),
                                                                   f2.evalfunc(x,y));
                            return z;
                        };
                        stack.push(a);
                    }

                    /* Case 5:
                    The first argument is a variable
                    The second argument is a variable
                    */
                    else if (arg1 instanceof String && arg2 instanceof String) {
                        String v2 = (String) arg2;
                        String v1 = (String) arg1;
                        Function a = (float x, float y) -> {
                            float z = ops_func.get(token).evalfunc((Objects.equals(v1,"x")) ? x : y,
                                                                   (Objects.equals(v2,"x")) ? x : y);
                            return z;
                        };
                        stack.push(a);
                    }

                    /* Case 6:
                    The first argument is a variable
                    The second argument is a float
                    */
                    else if (arg1 instanceof String && arg2 instanceof Float) {
                        float v2 = (float) arg2;
                        String v1 = (String) arg1;
                        Function a = (float x, float y) -> {
                            float z = ops_func.get(token).evalfunc((Objects.equals(v1,"x")) ? x : y,v2);
                            return z;
                        };
                        stack.push(a);
                    }

                    /* Case 7:
                    The first argument is a float
                    The second argument is a variable
                    */
                    else if (arg2 instanceof String && arg1 instanceof Float) {
                        float v2 = (float) arg1;
                        String v1 = (String) arg2;
                        Function a = (float x, float y) -> {
                            float z = ops_func.get(token).evalfunc(v2, (Objects.equals(v1,"x")) ? x : y);
                            return z;
                        };
                        stack.push(a);
                    }

                    /* Case 8:
                    The first argument is a variable
                    The second argument is a function
                    */
                    else if (arg1 instanceof String && arg2 instanceof Function) {
                        Function v2 = (Function) arg2;
                        String v1 = (String) arg1;
                        Function a = (float x, float y) -> {
                            float z = ops_func.get(token).evalfunc((Objects.equals(v1,"x")) ? x : y, v2.evalfunc(x,y));
                            return z;
                        };
                        stack.push(a);
                    }

                    /* Case 9:
                    The first argument is a function
                    The second argument is a variable
                    */
                    else if (arg2 instanceof String && arg1 instanceof Function) {
                        Function v2 = (Function) arg1;
                        String v1 = (String) arg2;
                        Function a = (float x, float y) -> {
                            float z = ops_func.get(token).evalfunc(v2.evalfunc(x,y), (Objects.equals(v1,"x")) ? x : y);
                            return z;
                        };
                        stack.push(a);
                    }

                /* If the operator takes one argument
                   pop one function from the stack,
                   evaluate the operator with the argument as the function,
                   and push the result to the stack. */
                } else {
                    Object arg1 = (Object) stack.pop();

                    /* Case 1:
                    The argument is a float
                    */
                    if (arg1 instanceof Float) {
                        float v1 = (float) arg1;
                        Function a = (float x, float y) -> {
                            float z = ops_func.get(token).evalfunc(v1,0.0f);
                            return z;
                        };
                        stack.push(a);
                    }

                    /* Case 2:
                    The argument is a variable
                    */
                    else if (arg1 instanceof String) {
                        String v1 = (String) arg1;
                        Function a = (float x, float y) -> {
                            float z = ops_func.get(token).evalfunc((Objects.equals(v1,"x")) ? x : y, 0.0f);
                            return z;
                        };
                        stack.push(a);
                    }

                    /* Case 3:
                    The argument is a function
                    */
                    else if (arg1 instanceof Function) {
                        Function f1 = (Function) arg1;
                        Function a = (float x, float y) -> {
                            float z = ops_func.get(token).evalfunc(f1.evalfunc(x,y),0.0f);
                            return z;
                        };
                        stack.push(a);
                    }
                }
            }
        }

        //The only object left in the stack should now be the final function
        Object end = stack.pop();

        /* Case 1:
        The final function is a function
        */
        if (end instanceof Function) {
            return (Function) end;
        }

        /* Case 2:
        The final function is a float
        */
        else if (end instanceof Float) {
            Function a = (float x, float y) -> {
                float z = (float) end;
                return z;
            };
            return a;
        }

        /* Case 3:
        The final function is a variable
        */
        else if (end instanceof String) {
            Function a = (float x, float y) -> {
                float z = (Objects.equals(end,"x")) ? x : y;
                return z;
            };
            return a;
        }
        return (Function) stack.pop();
    }

    /**
     * This takes in a string and breaks it up into it's constituent tokens
     * @param s A string that is going to be tokenised
     * @return a list of string tokens in the order in which they appeared in s
    */
    public static List<String> tokenise(String s) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(s));
        tokenizer.ordinaryChar('-'); // Don't parse minus as part of numbers.
        tokenizer.ordinaryChar('/'); // Don't parse / as a special character
        List<String> tokBuf = new ArrayList<String>();
        // While the final token has not been reached
        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            /* Make sure all of the items that are added to the list are of type
            string */
            switch(tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    tokBuf.add(String.valueOf(tokenizer.nval));
                    break;
                case StreamTokenizer.TT_WORD:
                    tokBuf.add(tokenizer.sval);
                    break;
                default:  // operator
                    tokBuf.add(String.valueOf((char) tokenizer.ttype));
            }
        }
        return tokBuf;
    }
}
