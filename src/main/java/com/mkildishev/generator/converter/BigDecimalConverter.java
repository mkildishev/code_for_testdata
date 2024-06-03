package com.mkildishev.generator.converter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

import static com.mkildishev.generator.builder.NameBuilder.getName;

public class BigDecimalConverter implements Converter {
    @Override
    public String convert(JsonNode node, Type type) {
        return String.format("BigDecimal %s = BigDecimal.valueOf(\"%s\");\n", getName(), node.asText());
    }
}
