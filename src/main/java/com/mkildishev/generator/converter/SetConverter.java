package com.mkildishev.generator.converter;

import com.mkildishev.generator.builder.NameBuilder;
import com.mkildishev.generator.converter.factory.ConverterFactory;

import java.util.List;

public class SetConverter extends CollectionConverter {

    public SetConverter(ConverterFactory converterFactory) {
        super(converterFactory);
    }

    @Override
    public String makeCollection(String type, List<String> values) {
        return String.format("%s %s = Set.of(%s);\n", type, NameBuilder.getName(), String.join(", ", values));
    }
}
