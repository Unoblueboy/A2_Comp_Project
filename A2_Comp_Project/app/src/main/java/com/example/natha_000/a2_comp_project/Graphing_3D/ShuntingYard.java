package com.example.natha_000.a2_comp_project.Graphing_3D;

/**
 * Created by Natha_000 on 02/01/2017.
 */

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class is used to perform the suhnting yard algorithm as well as
 * methods relating to the running of this algorithm
*/
public class ShuntingYard {
    private static boolean left = true;
    private static boolean right = false;

    /**
     * This stores the precedence of the common binary operators
    */
    private static Map<String,Integer> ops_prec = new HashMap<String,
                                                              Integer>(){{
        put("+",2);
        put("-",2);
        put("*",3);
        put("/",3);
        put("^",4);
    }};

    /**
     * This stores the associativity of the common binary operators
    */
    private static Map<String,Boolean> ops_assoc = new HashMap<String,
                                                               Boolean>(){{
        put("+",left);
        put("-",left);
        put("*",left);
        put("/",left);
        put("^",right);
    }};

    /**
     * This stores the associated binary operators with their binary function
    */
    public static Map<String,String> ops_2_arg = new HashMap<String,String>(){{
        put("+","add");
        put("-","sub");
        put("*","mul");
        put("/","div");
        put("^","power");
    }};

    /**
     * This method is used to check whether the first operator should be added
     * To the stack
     * @param  o1 The first operator
     * @param  o2 The second operator
     * @return Whether the first operator should be pushed to the stack
    */
    public static boolean opsCheck(String o1, String o2) {
        if (Objects.equals(ops_assoc.get(o1), left)){
            return (int) ops_prec.get(o1) <= (int) ops_prec.get(o2);
        } else {
            return (int) ops_prec.get(o1) < (int) ops_prec.get(o2);
        }
    }

    /**
     * This method is used to check whether the inputted string contains a
     * number or a variable
     * @param s The token to be checked
     * @return True if s is a float or variable, False otherwise
    */
    public static boolean isAlgebraic(String s) {
        return s.matches("-?\\d+(\\.\\d+)?") ||
                        Objects.equals(s,"x") ||
                        Objects.equals(s,"y");
    }

    /**
     * This method checks whether the inputted string is a function
     * @param s The token to be checked
     * @return True if s is a valid function, False otherwise
    */
    public static boolean isFunction(String s) {
        return (!Objects.equals(s,null) &&
                !isAlgebraic(s) &&
                s.length()>1 &&
                !Objects.equals(s,"x") &&
                !Objects.equals(s,"y") &&
                !s.contains(")")) &&
                !s.contains("(");
    }

    /**
     * This method performs the shunting yard algorithm as described by dijkstra
     * , using isAlgebraic, isFunction, and opsCheck
     * @param tokens A list of tokens, which represent a function
     * @return A string that represents the inputted function in reverse polish
     * notation
     * @see #isAlgebraic(String)
     * @see #isFunction(String)
     * @see #opsCheck(String, String)
    */
    public static String shuntingYard(List<String> tokens) {
        // This shall be used as a queue
        StringBuilder output = new StringBuilder();
        // This shall be used as a stack
        Deque<String> stack  = new LinkedList<>();

        // For token in the token list
        for (String token : tokens) {
            // If the number is a number or variable, push it to the stack
            if (isAlgebraic(token)){
                output.append(token).append(" ");

            // Else, if it is an opperator
            } else if (ops_prec.containsKey(token)){

                // While there is an operator in the stack and:
                while (true){
                    /*
                    IF:
                    (The stack isn't empty) AND
                    (The next operator in the stack is an operator) AND
                    (The first operator is left asociative and
                      its precedence is less than or equal to that of the second
                      operator OR
                    The first operator is right asociative and
                      its precedence is less than to that of the second
                      operator)
                    */
                    if (!stack.isEmpty() &&
                        ops_prec.containsKey(stack.peek()) &&
                        opsCheck(token,
                                stack.peek())){
                        // Push the operator to the queue
                        output.append(stack.pop()).append(" ");
                    } else {
                        break;
                    }
                }
                // Push the operator to the stack
                stack.push(token);
            // If the token is a left parenthesis
            } else if (Objects.equals(token,"(")) {
                // Push it to the stack
                stack.push(token);
            // If the token is a right parenthesis
            } else if (Objects.equals(token,")")){
                // While the token at the top of the stack isn't a
                // Right parenthesis
                while (!Objects.equals(stack.peek(),"(")) {
                    // Pop it from the stack and append it to the queue
                    output.append(stack.pop()).append(" ");
                }
                // Pop the left parenthesis from the stack but
                // Don't add it to the queue
                stack.pop();

                // If the token at the top is a function
                if (isFunction(stack.peek())) {
                    // Pop it from the stack and append it to the queue
                    output.append(stack.pop()).append(" ");
                }

            // If the token is a argument seperator (a comma)
            } else if (Objects.equals(token,",")) {
                // While the token at the top of the stack isn't a
                // Right parenthesis
                while (!Objects.equals(stack.peek(),"(")) {
                    // Pop it from the stack and append it to the queue
                    output.append(stack.pop()).append(" ");
                }
            // If the token is a function token
            } else if (isFunction(token)){
                // Push it onto the stack
                stack.push(token);
            }
        }

        // After all the tokens have been read
        // While the stack isn't empty
        while (!stack.isEmpty()){
            // Pop it from the stack and append it to the queue
            output.append(stack.pop()).append(" ");
        }
        // Return the function as a string
        return output.toString().trim();
    }
}
