package com.mkildishev.generator.builder;

import com.mkildishev.generator.model.ObjectType;

import java.math.BigInteger;
import java.util.Stack;

public class NameBuilder {

    private static BigInteger objectCounter = BigInteger.valueOf(0);

    private static BigInteger variableCounter = BigInteger.valueOf(0);

    private static Stack<String> nameStack = new Stack<>();

    public static String getName() {
        variableCounter = variableCounter.add(BigInteger.ONE);
        nameStack.add("v" + variableCounter);
        return "v" + variableCounter;
    }

    public static String getLastVariableName() {
        return "v" + variableCounter;
    }

    public static String popName() {
        if (nameStack.isEmpty()) {
            return "stub";
        }
        return nameStack.pop();
    }
}
