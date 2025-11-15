package com.mkildishev.generator.converter;

import com.mkildishev.generator.builder.NameBuilder;
import com.mkildishev.generator.converter.factory.ConverterFactory;

import java.util.List;

public class ListConverter extends CollectionConverter {

    public ListConverter(ConverterFactory converterFactory) {
        super(converterFactory);
    }

    @Override
    public String makeCollection(String type, List<String> values) {
        return String.format("%s %s = List.of(%s);\n", type, NameBuilder.getName(), String.join(", ", values));
    }
}
