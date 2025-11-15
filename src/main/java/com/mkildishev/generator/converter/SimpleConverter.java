package com.mkildishev.generator.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.mkildishev.generator.builder.NameBuilder;
import com.mkildishev.generator.utils.Utils;

import java.lang.reflect.Type;

public class SimpleConverter implements Converter {
    @Override
    public String convert(JsonNode node, Type type) {
        var simpleName = Utils.getSimpleName(type);
        return String.format("%s %s = %s.valueOf(\"%s\");\n", simpleName, NameBuilder.getName(), simpleName, node.asText());
    }
}
