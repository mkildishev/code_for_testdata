package com.mkildishev.generator.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Utils {

    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String makeObject(String type, String obj) {
        return type + " " + obj + " = " + "new " + type + "();\n";
    }

    public static boolean isGenericType(Type type) {
        return type instanceof ParameterizedType;
    }

    public static Class<?> getClass(String clazz) {
        try {
            return Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class " + clazz + " cannot be found. Please, check your configuration", e);
        }
    }
}
