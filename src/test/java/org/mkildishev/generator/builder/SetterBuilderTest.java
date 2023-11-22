package org.mkildishev.generator.builder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkildishev.generator.builder.SetterBuilder;
import org.example.App;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

public class SetterBuilderTest {

    @Test
    @DisplayName("Test for test")
    void test() throws ClassNotFoundException, NoSuchFieldException {
        ObjectMapper mapper = new ObjectMapper();
        var clazz = Class.forName("org.example.model.NestedListClass");
        var fieldc = clazz.getDeclaredField("list");
        var fieldType = fieldc.getGenericType();
        try (InputStream s = SetterBuilderTest.class.getClassLoader().getResource("test.json").openStream()) {
            var json = s.readAllBytes();
            var objJson = mapper.readTree(json);
            StringBuilder result = new StringBuilder();
            for (Iterator<Map.Entry<String, JsonNode>> it = objJson.fields(); it.hasNext(); ) {
                var a = it.next();
                var val = a.getValue();
                var field = a.getKey();
                result.append(SetterBuilder.valueConverter(val, fieldType));
                //result.append(buildSetter("org.example.model.TestClass", "obj", field, val, ""));

            }
            System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // load file
        // load type

    }
}
