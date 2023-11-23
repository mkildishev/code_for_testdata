package com.mkildishev.generator.builder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkildishev.generator.model.NodeType;
import com.mkildishev.generator.model.ObjectType;
import org.example.App;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.mkildishev.generator.builder.NameBuilder.*;
import static com.mkildishev.generator.utils.Utils.capitalize;
import static com.mkildishev.generator.utils.Utils.getType;

public class SetterBuilder {

    public static String generateCode(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream s = App.class.getClassLoader().getResource(fileName).openStream()) {

            var json = s.readAllBytes();
            var objJson = mapper.readTree(json);
            var clazz = Class.forName("org.example.model.TestClass");
            var str = valueConverter(objJson, clazz);
            System.out.println(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String valueConverter(JsonNode val, Type fieldType) {
        if (fieldType.equals(Integer.class)) {
            return "Integer " + getName() + " = " + "Integer.valueOf(\"" + val.toString() + "\");\n";
        }
        if (fieldType.equals(BigDecimal.class)) {
            return "BigDecimal " + getName() + " = " +  "BigDecimal.valueOf(\"" + val.toString() + "\");\n";
        }
        if (fieldType.equals(String.class)) {
            return "String " + getName() + " = " +   val.toString() + ";\n";
        }
        if (isGenericType(fieldType) && ((ParameterizedType) fieldType).getRawType().equals(Map.class)) {
            for (Iterator<Map.Entry<String, JsonNode>> it = val.fields(); it.hasNext(); ) {
                var v = it.next();
                
                System.out.println("succ");
            }
            // generate entry
            return "we're here with map\n";
        } // классы читаются отдельно как непараметризуемый тип
        if (isGenericType(fieldType) && ((ParameterizedType) fieldType).getRawType().equals(List.class)) {
            StringBuilder result = new StringBuilder();
            List<String> names = new ArrayList<>();
            for (var v : val) {
                result.append(valueConverter(v, ((ParameterizedType) fieldType).getActualTypeArguments()[0]));
                names.add(popName());
            }
            String typeName = fieldType.getTypeName();
            result.append(typeName).append(" ").append(getName()).append(" = ").append("List.of(").append(String.join(", ", names)).append(");\n");
            return result.toString();
        }

        StringBuilder result = new StringBuilder();
        result.append(fieldType.getTypeName()).append(" ").append(getName()).append(" = ").append("new ").append(fieldType.getTypeName()).append("();\n").toString();
        var objectName = getLastVariableName();
        for (Iterator<Map.Entry<String, JsonNode>> it = val.fields(); it.hasNext(); ) {
            try {
                var a = it.next();
                var value = a.getValue();
                var field1 = a.getKey();
                var clazz = Class.forName(fieldType.getTypeName());
                var field = clazz.getDeclaredField(field1);
                var fieldType1 = field.getGenericType();
                result.append(valueConverter(value, fieldType1));
                result.append(objectName + "." + "set" + capitalize(field.getName()) + "(" + popName() + ");" + '\n');
            } catch (ClassNotFoundException | NoSuchFieldException e) {
                System.out.println("sdass");
            }

        }
        return result.toString();
    }

    private static boolean isGenericType(Type type) {
        return type instanceof ParameterizedType;
    }
}
