package com.mkildishev.generator.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.mkildishev.generator.converter.factory.ConverterFactory;
import com.mkildishev.generator.utils.Utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.mkildishev.generator.builder.NameBuilder.getName;
import static com.mkildishev.generator.builder.NameBuilder.popName;

public class ListConverter implements Converter {

    private ConverterFactory converterFactory;

    public ListConverter(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    // Создание мейкера и генерация строки повторяется
    @Override
    public String convert(JsonNode node, Type type) {
        StringBuilder result = new StringBuilder();
        List<String> elementNames = new ArrayList<>();
        for (var v : node) {
            Converter converter = converterFactory.createConverter(((ParameterizedType) type).getActualTypeArguments()[0]);
            result.append(converter.convert(v, ((ParameterizedType) type).getActualTypeArguments()[0]));
            elementNames.add(popName());
        }
        result.append(makeList(Utils.getGenericSimpleName(type), elementNames));
        return result.toString();
    }

    private String makeList(String type, List<String> values) {
        return type + " " + getName() + " = " + "List.of(" + String.join(", ", values) + ");\n";
    }
}
