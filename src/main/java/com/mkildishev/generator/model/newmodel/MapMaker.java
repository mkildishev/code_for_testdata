package com.mkildishev.generator.model.newmodel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

import static com.mkildishev.generator.builder.NameBuilder.getName;
import static com.mkildishev.generator.builder.NameBuilder.popName;
import static com.mkildishev.generator.model.newmodel.Utils.*;

public class MapMaker implements Maker {

    MakerFactory makerFactory;

    @Override
    public String make(JsonNode node, Type type) {
        StringBuilder result = new StringBuilder();
        var objectName = getName();
        result.append(makeObject(type.getTypeName(), objectName));
        for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
            var entry = it.next();
            Maker maker = makerFactory.createMaker(String.class);
            result.append(maker.make(new TextNode(entry.getKey()), String.class)); // сделать отдельный метод для строки?
            maker = makerFactory.createMaker(((ParameterizedType) type).getActualTypeArguments()[1]);
            result.append(maker.make(entry.getValue(), ((ParameterizedType) type).getActualTypeArguments()[1]));
            result.append(putIntoMap(objectName));
        }
        return result.toString();
    }

    private String putIntoMap(String mapName) {
        var value = popName();
        var key = popName();
        return mapName + ".put(" + key + ", " + value + ");\n";
    }
}
