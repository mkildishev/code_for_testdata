package com.mkildishev.generator.converter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

import static com.mkildishev.generator.builder.NameBuilder.getName;

public class IntegerConverter implements Converter {
    @Override
    public String convert(JsonNode node, Type type) {
        return String.format("Integer %s = Integer.valueOf(\"%s\");\n", getName(), node.asText());
    }
}
