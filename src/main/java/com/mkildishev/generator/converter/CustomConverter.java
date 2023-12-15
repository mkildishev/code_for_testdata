package com.mkildishev.generator.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.mkildishev.generator.converter.factory.ConverterFactory;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

import static com.mkildishev.generator.builder.NameBuilder.getName;
import static com.mkildishev.generator.builder.NameBuilder.popName;
import static com.mkildishev.generator.utils.Utils.capitalize;
import static com.mkildishev.generator.utils.Utils.makeObject;

public class CustomConverter implements Converter {

    private ConverterFactory converterFactory;

    public CustomConverter(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public String convert(JsonNode node, Type type) {
        StringBuilder result = new StringBuilder();
        var objectName = getName();
        result.append(makeObject(type.getTypeName(), objectName));
        Class<?> clazz = getClass(type.getTypeName());
        for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
            try {
                var entry = it.next();
                var value = entry.getValue();
                var fieldName = entry.getKey();
                var field = clazz.getDeclaredField(fieldName);
                Converter typeConverter = converterFactory.createConverter(type);
                result.append(typeConverter.convert(value, field.getGenericType()));
                result.append(makeSetter(objectName, field.getName()));
            } catch (NoSuchFieldException e) {
                System.out.println("Field cannot be found");
            }

        }
        return result.toString();
    }

    private String makeSetter(String object, String method) {
        return object + "." + "set" + capitalize(method) + "(" + popName() + ");" + '\n';
    }

    private Class<?> getClass(String clazz) {
        try {
            return Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class " + clazz + " cannot be found. Please, check your configuration", e);
        }
    }
}
