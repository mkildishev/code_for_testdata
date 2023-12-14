package com.mkildishev.generator.model.newmodel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.mkildishev.generator.model.newmodel.Utils.isGenericType;

public class MakerFactory {
    public Maker createMaker(Type type) {
        if (type.equals(Integer.class)) {
            return new IntegerMaker();
        }
        if (type.equals(BigDecimal.class)) {
            return new BigDecimalMaker();
        }
        if (type.equals(String.class)) {
            return new StringMaker();
        }
        if (isGenericType(type) && ((ParameterizedType) type).getRawType().equals(Map.class)) {
            return new MapMaker();
        }
        if (isGenericType(type) && ((ParameterizedType) type).getRawType().equals(List.class)) {
            return new ListMaker();
        }
        return new CustomMaker();
    }
}
