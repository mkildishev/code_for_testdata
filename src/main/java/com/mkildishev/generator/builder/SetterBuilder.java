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

import static com.mkildishev.generator.builder.NameBuilder.getLastVariableName;
import static com.mkildishev.generator.builder.NameBuilder.getName;
import static com.mkildishev.generator.utils.Utils.capitalize;
import static com.mkildishev.generator.utils.Utils.getType;

public class SetterBuilder {

    public static String generateCode(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream s = App.class.getClassLoader().getResource(fileName).openStream()) {
            var json = s.readAllBytes();
            var objJson = mapper.readTree(json);
            StringBuilder result = new StringBuilder();
            for (Iterator<Map.Entry<String, JsonNode>> it = objJson.fields(); it.hasNext(); ) {
                var a = it.next();
                var val = a.getValue();
                var field = a.getKey();
                result.append(buildSetter("org.example.model.TestClass", "obj", field, val, ""));

            }
            System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static String buildSetter(String objectClass, String objectName, String fieldName, JsonNode fieldValue, String initial) throws ClassNotFoundException, NoSuchFieldException {
        var clazz = Class.forName(objectClass);
        var field = clazz.getDeclaredField(fieldName);
        var fieldType = field.getGenericType();
        if (NodeType.PRIMITIVE.equals(getType(fieldValue))) {
            initial += valueConverter(fieldValue, fieldType);
            initial += objectName + "." + "set" + capitalize(fieldName) + "(" + NameBuilder.getLastVariableName() + ");" + '\n';
        } else {
            initial += createNewObject(fieldType);
            StringBuilder initialBuilder = new StringBuilder(initial);
            for (Iterator<Map.Entry<String, JsonNode>> it = fieldValue.fields(); it.hasNext(); ) {
                var a = it.next();
                var val = a.getValue();
                var field1 = a.getKey();
                initialBuilder.append(buildSetter(fieldType.getTypeName(), "generatedName", field1, val, ""));
            }
            initial = initialBuilder.toString();
            //initial += buildSetter(fieldType.getName(), "generatedName", )
            System.out.println("complex");
        }
        System.out.println("succ");
        return initial;
    }

    public static String createNewObject(Type type) {
        return "obj";
    }

    public static String valueConverter(JsonNode val, Type fieldType) { // Отдельный модуль с классами под каждый тип?
        if (fieldType.equals(Integer.class)) {
            return "Integer " + getName(ObjectType.VARIABLE) + " = " + "Integer.valueOf(\"" + val.toString() + "\");\n";
        }
        if (fieldType.equals(BigDecimal.class)) {
            return "BigDecimal " + getName(ObjectType.VARIABLE) + " = " +  "BigDecimal.valueOf(\"" + val.toString() + "\");\n";
        }
        if (fieldType.equals(String.class)) {
            return "String " + getName(ObjectType.VARIABLE) + " = " +   val.toString() + ";\n";
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
                names.add(getLastVariableName());
            }
            String typeName = fieldType.getTypeName();
            result.append(typeName).append(" ").append(getName(ObjectType.VARIABLE)).append(" = ").append("List.of(").append(String.join(", ", names)).append(");\n");
            //result.append("List<").append(typeName).append("> ").append(getName(ObjectType.VARIABLE)).append(" = ").append("List.of(").append(String.join(", ", names)).append(");\n");
            return result.toString();
        }
        if (fieldType instanceof Object) {
            StringBuilder result = new StringBuilder();
            List<String> names = new ArrayList<>();
            result.append(fieldType.getTypeName()).append(" ").append(getName(ObjectType.VARIABLE)).append(" = ").append("new ").append(fieldType.getTypeName()).append("();\n").toString();
            for (var v : val) {
                result.append(valueConverter(v, ((ParameterizedType) fieldType).getActualTypeArguments()[0])); // срань
                names.add(getLastVariableName());
            }
            return result.toString();
        }

        return "No value\n";
    }

    private static boolean isGenericType(Type type) {
        return type instanceof ParameterizedType;
    }

    public static String processListNode(JsonNode node, Type fieldType) {
        StringBuilder result = new StringBuilder();
        List<String> names = new ArrayList<>();
        for (var v : node) {
            result.append(valueConverter(v, ((ParameterizedType) fieldType).getActualTypeArguments()[0]));
            names.add(getLastVariableName());
        }
        String typeName = ((Class<?>)((ParameterizedType) fieldType).getActualTypeArguments()[0]).getSimpleName();
        result.append("List<").append(typeName).append("> ").append(getName(ObjectType.VARIABLE)).append(" = ").append("List.of(").append(String.join(", ", names)).append(");\n");
        return result.toString();
    }

    private static String mapEntryGenerator(Map.Entry<String, JsonNode> node) {
        return "mapEntry";
    }

}
