package com.mkildishev.generator.model.newmodel;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

import static com.mkildishev.generator.builder.NameBuilder.getName;

public class BigDecimalMaker implements Maker {
    @Override
    public String make(JsonNode node, Type type) {
        return "BigDecimal " + getName() + " = " + "BigDecimal.valueOf(\"" + node.asText() + "\");\n";
    }
}
