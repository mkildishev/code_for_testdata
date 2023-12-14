package com.mkildishev.generator.model.newmodel;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

import static com.mkildishev.generator.builder.NameBuilder.getName;
import static com.mkildishev.generator.builder.NameBuilder.popName;
import static com.mkildishev.generator.model.newmodel.Utils.*;
import static com.mkildishev.generator.utils.Utils.capitalize;

public class CustomMaker implements Maker {

    private MakerFactory makerFactory;

    @Override
    public String make(JsonNode node, Type type) {
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
                Maker typeMaker = makerFactory.createMaker(type);
                result.append(typeMaker.make(value, field.getGenericType()));
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
}
