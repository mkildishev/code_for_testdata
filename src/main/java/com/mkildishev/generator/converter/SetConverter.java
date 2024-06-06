package com.mkildishev.generator.converter;

import com.mkildishev.generator.converter.factory.ConverterFactory;

import java.util.List;

import static com.mkildishev.generator.builder.NameBuilder.getName;

public class SetConverter extends CollectionConverter {

    public SetConverter(ConverterFactory converterFactory) {
        super(converterFactory);
    }

    @Override
    public String makeCollection(String type, List<String> values) {
        return String.format("%s %s = Set.of(%s);\n", type, getName(), String.join(", ", values));
    }
}
