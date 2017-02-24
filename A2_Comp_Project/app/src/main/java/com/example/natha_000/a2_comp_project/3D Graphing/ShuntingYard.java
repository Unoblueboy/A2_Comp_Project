package com.example.natha_000.a2_comp_project;

/**
 * Created by Natha_000 on 02/01/2017.
 */

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
     * @param  assoc The associativity of the first operator
     * @param  o1 The first operator
     * @param  o2 The second operator
     * @return Whether the first operator should be pushed to the stack
    */
    private boolean opsCheck(boolean assoc, String o1, String o2) {
        if (Objects.equals(assoc, left)){
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
    public boolean isAlgebraic(String s) {
        return s.matches("-?\\d+(\\.\\d+)?") ||
                        Objects.equals(s,"x") ||
                        Objects.equals(s,"y");
    }

    /**
     * This method checks whether the inputted string is a function
     * @param s The token to be checked
     * @return True if s is a valid function, False otherwise
    */
    public boolean isFunction(String s) {
        return (!Objects.equals(s,null) &&
                !isAlgebraic(s) &&
                s.length()>1 &&
                !Objects.equals(s,"x") &&
                !Objects.equals(s,"y") &&
                !Objects.equals(s,")"));
    }

    /**
     * This method performs the shunting yard algorithm as described by dijkstra
     * , using isAlgebraic, isFunction, and opsCheck
     * @param tokens A list of tokens, which represent a function
     * @return A string that represents the inputted function in reverse polish
     * notation
     * @see isAlgebraic
     * @see isFunction
     * @see opsCheck
    */
    public String shuntingYard(List<String> tokens) {
        StringBuilder output = new StringBuilder();
        Deque<String> stack  = new LinkedList<>();
        for (String token : tokens) {
            if (isAlgebraic(token)){
                output.append(token).append(" ");
            } else if (ops_prec.containsKey(token)){
                while (true){
                    if (!stack.isEmpty() &&
                        ops_prec.containsKey(stack.peek()) &&
                        opsCheck((boolean) ops_assoc.get(token),
                                token,
                                stack.peek())){
                        output.append(stack.pop()).append(" ");
                    } else {
                        break;
                    }
                }
                stack.push(token);
            } else if (Objects.equals(token,"(")) {
                stack.push(token);
            } else if (Objects.equals(token,")")){
                while (!Objects.equals(stack.peek(),"(")) {
                    output.append(stack.pop()).append(" ");
                }
                stack.pop();
                if (isFunction(stack.peek())) {
                    output.append(stack.pop()).append(" ");
                }
            } else if (Objects.equals(token,",")) {
                while (!Objects.equals(stack.peek(),"(")) {
                    output.append(stack.pop()).append(" ");
                }
            } else if (isFunction(token)){
                stack.push(token);
            }
        }

        while (!stack.isEmpty()){
            output.append(stack.pop()).append(" ");
        }
        return output.toString();
    }
}
