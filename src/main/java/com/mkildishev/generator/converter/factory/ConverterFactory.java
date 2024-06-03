package com.mkildishev.generator.converter.factory;

import com.mkildishev.generator.converter.*;
import com.mkildishev.generator.utils.Utils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ConverterFactory {

    private final Map<String, Converter> converters = new HashMap<>();

    public ConverterFactory() {
        converters.put("Integer", new IntegerConverter());
        converters.put("BigDecimal", new BigDecimalConverter());
        converters.put("String", new StringConverter());
        converters.put("Map", new MapConverter(this));
        converters.put("List", new ListConverter(this));
    }

    public Converter createConverter(Type type) {
        return converters.getOrDefault(Utils.getSimpleName(type), new CustomConverter(this));
    }
}
