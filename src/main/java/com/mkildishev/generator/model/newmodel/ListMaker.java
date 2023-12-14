package com.mkildishev.generator.model.newmodel;

import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.mkildishev.generator.builder.NameBuilder.getName;
import static com.mkildishev.generator.builder.NameBuilder.popName;

public class ListMaker implements Maker {

    MakerFactory makerFactory;
    // Создание мейкера и генерация строки повторяется
    @Override
    public String make(JsonNode node, Type type) {
        StringBuilder result = new StringBuilder();
        List<String> elementNames = new ArrayList<>();
        for (var v : node) {
            Maker maker = makerFactory.createMaker(((ParameterizedType) type).getActualTypeArguments()[0]);
            result.append(maker.make(v, ((ParameterizedType) type).getActualTypeArguments()[0]));
            elementNames.add(popName());
        }
        result.append(makeList(type.getTypeName(), elementNames));
        return result.toString();
    }

    private String makeList(String type, List<String> values) {
        return type + " " + getName() + " = " + "List.of(" + String.join(", ", values) + ");\n";
    }
}
