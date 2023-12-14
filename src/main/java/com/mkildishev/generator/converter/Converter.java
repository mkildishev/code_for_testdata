package com.mkildishev.generator.converter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

public interface Converter {
    String make(JsonNode node, Type type);
}
