package com.mkildishev.generator.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mkildishev.generator.model.NodeType;

public class Utils {

    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static NodeType getType(JsonNode node) {
//        if (node instanceof ObjectNode) {
//            return NodeType.COMPLEX;
//        }
        return NodeType.PRIMITIVE;
    }

}
