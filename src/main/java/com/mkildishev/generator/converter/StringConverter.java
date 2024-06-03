package com.mkildishev.generator.converter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

import static com.mkildishev.generator.builder.NameBuilder.getName;

public class StringConverter implements Converter {
    @Override
    public String convert(JsonNode node, Type type) {
        return  String.format("String %s = \"%s\";\n", getName(), node.asText());
    }
}
