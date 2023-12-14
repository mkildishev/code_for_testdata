package com.mkildishev.generator.converter.factory;

import com.mkildishev.generator.converter.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.mkildishev.generator.utils.Utils.isGenericType;

public class ConverterFactory {
    public Converter createConverter(Type type) {
        if (type.equals(Integer.class)) {
            return new IntegerConverter();
        }
        if (type.equals(BigDecimal.class)) {
            return new BigDecimalConverter();
        }
        if (type.equals(String.class)) {
            return new StringConverter();
        }
        if (isGenericType(type) && ((ParameterizedType) type).getRawType().equals(Map.class)) {
            return new MapConverter(this);
        }
        if (isGenericType(type) && ((ParameterizedType) type).getRawType().equals(List.class)) {
            return new ListConverter(this);
        }
        return new CustomConverter(this);
    }
}
