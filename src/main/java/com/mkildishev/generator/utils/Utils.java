package com.mkildishev.generator.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Objects;

public class Utils {

    public static String makeObject(String type, String obj) {
        return type + " " + obj + " = " + "new " + type + "();\n";
    }

    public static boolean isGenericType(Type type) {
        return type instanceof ParameterizedType;
    }

    public static Class<?> getClass(String clazz) {
        try {
            return Class.forName(clazz, false, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            String message = "Class " + clazz + " cannot be found. Please, check your configuration";
            throw new RuntimeException(message);
        }
    }

    public static void saveFile(String path, String fileName, String content) {

        File targetDirectory = new File(path);

        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }

        File outputFile = new File(targetDirectory, fileName);
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            writer.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getGenericSimpleName(Type type) {
        Type rawType = ((ParameterizedType) type).getRawType();
        StringBuilder rawTypeName = new StringBuilder(((Class<?>) rawType).getSimpleName());
        rawTypeName.append("<");
        Type[] genericArgs = ((ParameterizedType) type).getActualTypeArguments();
        for (int i = 0; i < genericArgs.length; i++) {
            if (genericArgs[i] instanceof ParameterizedType) {
                rawTypeName.append(getGenericSimpleName(genericArgs[i]));
            } else {
                Class<?> argClass = (Class<?>) genericArgs[i];
                rawTypeName.append(argClass.getSimpleName());
                if (i < genericArgs.length - 1) {
                    rawTypeName.append(", ");
                }
            }
        }
        rawTypeName.append(">");
        return rawTypeName.toString();
    }

    public static String getSimpleName(Type type) {
        if (isGenericType(type)) {
            Type rawType = ((ParameterizedType) type).getRawType();
            return ((Class<?>) rawType).getSimpleName();
        } else {
            return ((Class<?>) type).getSimpleName();
        }
    }

    public static Type getMapValueType(Type type) {
        if (isGenericType(type)) {
            return ((ParameterizedType) type).getActualTypeArguments()[1];
        }
        throw new IllegalArgumentException("Unknown type " + type.getTypeName());
    }

    public static JsonNode getResource(String resourceName) {
        ObjectMapper mapper = new ObjectMapper();
        ClassLoader classLoader = Utils.getClassLoader();
        URL resourceUrl = Objects.requireNonNull(classLoader.getResource(resourceName));
        try (InputStream s = resourceUrl.openStream()) {
            var json = s.readAllBytes();
            var objJson = mapper.readTree(json);
            return objJson;
        } catch (IOException e) {
            throw new RuntimeException("File " + resourceName + " cannot be found, please check your configuration");
        }
    }

    private static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

}
