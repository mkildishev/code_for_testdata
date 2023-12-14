package com.mkildishev.generator.model.newmodel;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

import static com.mkildishev.generator.builder.NameBuilder.getName;

public class IntegerMaker implements Maker {
    @Override
    public String make(JsonNode node, Type type) {
        return "Integer " + getName() + " = " + "Integer.valueOf(\"" + node.asText() + "\");\n";
    }
}
