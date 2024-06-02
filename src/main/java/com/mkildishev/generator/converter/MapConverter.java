package com.mkildishev.generator.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.mkildishev.generator.converter.factory.ConverterFactory;
import com.mkildishev.generator.utils.Utils;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

import static com.mkildishev.generator.builder.NameBuilder.getName;
import static com.mkildishev.generator.builder.NameBuilder.popName;
import static com.mkildishev.generator.utils.Utils.makeObject;

public class MapConverter implements Converter {

    private final ConverterFactory converterFactory;

    public MapConverter(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }
    // Можно ли MapConverter унаследовать от Custom?
    @Override
    public String convert(JsonNode node, Type type) {
        StringBuilder result = new StringBuilder();
        var objectName = getName();
        result.append(makeObject(Utils.getGenericSimpleName(type), objectName));
        for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
            var entry = it.next();
            Converter converter = converterFactory.createConverter(String.class);
            result.append(converter.convert(new TextNode(entry.getKey()), String.class)); // сделать отдельный метод для строки?
            converter = converterFactory.createConverter(Utils.getMapValueType(type));
            result.append(converter.convert(entry.getValue(), Utils.getMapValueType(type)));
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
