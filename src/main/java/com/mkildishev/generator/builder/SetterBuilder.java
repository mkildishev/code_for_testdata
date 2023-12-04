package com.mkildishev.generator.builder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkildishev.generator.utils.Utils;
import org.example.App;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.mkildishev.generator.builder.NameBuilder.*;
import static com.mkildishev.generator.utils.Utils.capitalize;

public class SetterBuilder {
    // Make it maven plugin
    public static String generateCode(String file, String jar, String _package) {
        ObjectMapper mapper = new ObjectMapper();
        // Need to improve ability to change class loader
        // If we can run plugin with using common application classes, then ok
        // Otherwise we have to parse model source code
        URLClassLoader classLoader = null;
        try {
            classLoader = new URLClassLoader(new URL[]{new File(jar).toURI().toURL()});
        } catch (MalformedURLException e) {
            //do nothing until test
            //throw new RuntimeException(e);
        }
        //classLoader.loadClass()
        try (InputStream s = classLoader.getResource(file).openStream()) {
            var json = s.readAllBytes();
            var objJson = mapper.readTree(json);
            var className = Utils.capitalize(objJson.fields().next().getKey());
            var objToProcess = objJson.fields().next().getValue();
            var clazz = Class.forName(_package + "." + className); // first node is a classname
            var str = valueConverter(objToProcess, clazz);
            System.out.println(str);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String valueConverter(JsonNode val, Type fieldType) {
        if (fieldType.equals(Integer.class)) {
            return makeInteger(val.asText());
        }
        if (fieldType.equals(BigDecimal.class)) {
            return makeBigDecimal(val.asText());
        }
        if (fieldType.equals(String.class)) {
            return makeString(val.asText());
        }
        if (isGenericType(fieldType) && ((ParameterizedType) fieldType).getRawType().equals(Map.class)) {
            return makeMap(fieldType, val);
        }
        if (isGenericType(fieldType) && ((ParameterizedType) fieldType).getRawType().equals(List.class)) {
            return makeList(fieldType,  val);
        }
        return makeObject(fieldType, val);
    }

    private static boolean isGenericType(Type type) {
        return type instanceof ParameterizedType;
    }

    private static String makeInteger(String value) {
        return "Integer " + getName() + " = " + "Integer.valueOf(\"" + value + "\");\n";
    }

    private static String makeBigDecimal(String value) {
        return "BigDecimal " + getName() + " = " + "BigDecimal.valueOf(\"" + value + "\");\n";
    }

    private static String makeString(String value) {
        return "String " + getName() + " = \"" + value + "\";\n";
    }

    private static String putIntoMap(String mapName, String key, String value) {
        return mapName + ".put(" + key + ", " + value + ");\n";
    }

    private static String makeObject(String type, String obj) {
        return type + " " + obj + " = " + "new " + type + "();\n";
    }

    private static String makeSetter(String object, String method) {
        return object + "." + "set" + capitalize(method) + "(" + popName() + ");" + '\n';
    }

    private static String makeList(String type, List<String> values) {
        return type + " " + getName() + " = " + "List.of(" + String.join(", ", values) + ");\n";
    }

    private static String makeList(Type type, JsonNode node) {
        StringBuilder result = new StringBuilder();
        List<String> names = new ArrayList<>();
        for (var v : node) {
            result.append(valueConverter(v, ((ParameterizedType) type).getActualTypeArguments()[0]));
            names.add(popName());
        }
        result.append(makeList(type.getTypeName(), names));
        return result.toString();
    }

    private static String makeMap(Type type, JsonNode node) {
        StringBuilder result = new StringBuilder();
        var objectName = getName();
        result.append(makeObject(type.getTypeName(), objectName));
        for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
            var entry = it.next();
            result.append(makeString(entry.getKey()));
            result.append(valueConverter(entry.getValue(), ((ParameterizedType) type).getActualTypeArguments()[1]));
            var valueResult = popName();
            var keyResult = popName();
            result.append(putIntoMap(objectName, keyResult, valueResult));
        }
        return result.toString();
    }

    private static String makeObject(Type type, JsonNode node) {
        StringBuilder result = new StringBuilder();
        var objectName = getName();
        result.append(makeObject(type.getTypeName(), objectName));
        Class<?> clazz = null;
        try {
            clazz = Class.forName(type.getTypeName());
        } catch (ClassNotFoundException e) {
            System.out.println("Class " + type.getTypeName() + " cannot be found. Please, check your configuration");
            throw new RuntimeException(e);
        }
        for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
            try {
                var entry = it.next();
                var value = entry.getValue();
                var fieldName = entry.getKey();
                var field = clazz.getDeclaredField(fieldName);
                result.append(valueConverter(value, field.getGenericType()));
                result.append(makeSetter(objectName, field.getName()));
            } catch (NoSuchFieldException e) {
                System.out.println("Field cannot be found");
            }

        }
        return result.toString();
    }

}
