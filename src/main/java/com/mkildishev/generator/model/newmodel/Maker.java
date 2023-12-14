package com.mkildishev.generator.model.newmodel;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Type;

public interface Maker {
    String make(JsonNode node, Type type);
}
