package com.mkildishev.generator.builder;

import com.mkildishev.generator.model.ObjectType;

import java.math.BigInteger;

public class NameBuilder {

    private static BigInteger objectCounter = BigInteger.valueOf(0);

    private static BigInteger variableCounter = BigInteger.valueOf(0);

    public static String getName(ObjectType type) {
        if (ObjectType.VARIABLE.equals(type)) {
            variableCounter = variableCounter.add(BigInteger.ONE);
            return "v" + variableCounter;
        }
        objectCounter = objectCounter.add(BigInteger.ONE);
        return "o" + objectCounter;
    }

    public static String getLastVariableName() {
        return "v" + variableCounter;
    }

    public static String getLastObjectName() {
        return "o" + objectCounter;
    }
}
