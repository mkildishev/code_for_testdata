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
            System.out.println(message);
            throw new RuntimeException(message, e);
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

    public static JsonNode getResource(String resourceName) {
        ObjectMapper mapper = new ObjectMapper();
        ClassLoader classLoader = Utils.getClassLoader();
        URL resourceUrl = Objects.requireNonNull(classLoader.getResource(resourceName));
        try (InputStream s = resourceUrl.openStream()) {
            var json = s.readAllBytes();
            var objJson = mapper.readTree(json);
            return objJson;
        } catch (IOException e) {
            throw new RuntimeException("File " + resourceName + " cannot be found, please check your configuration", e);
        }
    }

    private static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

}
