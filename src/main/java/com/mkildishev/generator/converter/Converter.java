package com.mkildishev.generator.converter;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

public interface Converter {
    String convert(JsonNode val, Type type);
}
