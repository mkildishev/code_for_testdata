package com.mkildishev.generator.utils;

import org.codehaus.plexus.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

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

    public static void saveFile(String fileName, String content) {

        String currentDir = System.getProperty("user.dir");
        String targetDirectoryPath = currentDir + "/generated-testdata";
        File targetDirectory = new File(targetDirectoryPath);

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

    public static URLClassLoader getClassLoader(String jar) {
        try {
            return new URLClassLoader(new URL[]{new File(jar).toURI().toURL()});
        } catch (MalformedURLException e) {
            throw new RuntimeException("JAR " + jar + " cannot be found, please check your configuration", e);
        }
    }
}
