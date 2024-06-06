package com.mkildishev.generator.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.mkildishev.generator.converter.factory.ConverterFactory;
import com.mkildishev.generator.utils.Utils;
import org.apache.commons.lang3.NotImplementedException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.mkildishev.generator.builder.NameBuilder.popName;

public abstract class CollectionConverter implements Converter {

    private final ConverterFactory converterFactory;

    protected CollectionConverter(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public String convert(JsonNode node, Type type) {
        StringBuilder result = new StringBuilder();
        List<String> elementNames = new ArrayList<>();
        for (var v : node) {
            Converter converter = converterFactory.createConverter(((ParameterizedType) type).getActualTypeArguments()[0]);
            result.append(converter.convert(v, ((ParameterizedType) type).getActualTypeArguments()[0]));
            elementNames.add(popName());
        }
        result.append(makeCollection(Utils.getGenericSimpleName(type), elementNames));
        return result.toString();
    }

    public String makeCollection(String type, List<String> values) {
        throw new NotImplementedException("Method doesn't implemented");
    }
}
