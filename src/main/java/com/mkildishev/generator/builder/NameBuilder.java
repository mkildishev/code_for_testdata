package com.mkildishev.generator.builder;

import java.math.BigInteger;
import java.util.Stack;

public class NameBuilder {

    private static BigInteger variableCounter = BigInteger.ZERO;

    private static final Stack<String> nameStack = new Stack<>();

    public static String getName() {
        variableCounter = variableCounter.add(BigInteger.ONE);
        nameStack.add("v" + variableCounter);
        return "v" + variableCounter;
    }

    public static String popName() {
        return !nameStack.isEmpty() ? nameStack.pop() : "defaultName";
    }

    public static void reset() {
        variableCounter = BigInteger.ZERO;
        nameStack.clear();
    }
}
