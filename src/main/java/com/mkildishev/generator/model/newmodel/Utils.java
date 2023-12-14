package com.mkildishev.generator.model.newmodel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static com.mkildishev.generator.builder.NameBuilder.getName;
import static com.mkildishev.generator.builder.NameBuilder.popName;
import static com.mkildishev.generator.utils.Utils.capitalize;

public class Utils {


    public static String makeObject(String type, String obj) {
        return type + " " + obj + " = " + "new " + type + "();\n";
    }

    public static boolean isGenericType(Type type) {
        return type instanceof ParameterizedType;
    }





}
