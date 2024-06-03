package com.mkildishev.generator.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        Path targetDirectory = Paths.get(path);

        if (!Files.exists(targetDirectory)) {
            try {
                Files.createDirectory(targetDirectory);
            } catch (IOException e) {
                throw new RuntimeException("Couldn't create target directory: " + path, e);
            }
        }

        Path outputFile = targetDirectory.resolve(fileName);
        try {
            Files.writeString(outputFile, content);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't save file: " + outputFile, e);
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
