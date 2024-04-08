package com.mkildishev.generator.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.plexus.util.StringUtils;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
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
            return Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class " + clazz + " cannot be found. Please, check your configuration", e);
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

    public static JsonNode getResource(String resourceName, String jarName) {
        ObjectMapper mapper = new ObjectMapper();
        URLClassLoader classLoader = Utils.getClassLoader(jarName);
        URL resourceUrl = Objects.requireNonNull(classLoader.getResource(resourceName));
        try (InputStream s = resourceUrl.openStream()) {
            var json = s.readAllBytes();
            var objJson = mapper.readTree(json);
            return objJson;
        } catch (IOException e) {
            throw new RuntimeException("File " + resourceName + " cannot be found, please check your configuration", e);
        } catch (NullPointerException e) {
            throw new RuntimeException("JAR " + jarName + " cannot be found, please check your configuration", e);
        }
    }

    private static URLClassLoader getClassLoader(String jar) {
        try {
            return new URLClassLoader(new URL[]{new URL("file","", jar)});
        } catch (MalformedURLException e) {
            throw new RuntimeException("JAR " + jar + " cannot be found, please check your configuration", e);
        }
    }
}
