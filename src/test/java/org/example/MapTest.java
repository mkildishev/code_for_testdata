package org.example;

import com.mkildishev.generator.CodeGenerator;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MapTest {
    private CodeGenerator codeGenerator;

    @BeforeAll
    void init() {
        codeGenerator = new CodeGenerator(Thread.currentThread().getContextClassLoader());
    }

    @Test
    @DisplayName("Can Process Map")
    public void canProcessMap() {
        String actualResult = codeGenerator.generate("simpleMapTest.json", "org.example.model");
        String expectedResult = """
                SimpleMapModel v1 = new SimpleMapModel();
                Map<String, Integer> v2 = new Map<String, Integer>();
                String v3 = "key1";
                Integer v4 = Integer.valueOf("333");
                v2.put(v3, v4);
                String v5 = "key2";
                Integer v6 = Integer.valueOf("444");
                v2.put(v5, v6);
                v1.setMapProperty(v2);
                """;
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Can Process Map With Nested Map")
    public void canProcessMapWithNestedMap() {
        String actualResult = codeGenerator.generate("nestedMapTest.json", "org.example.model");
        String expectedResult = """
                NestedMapModel v1 = new NestedMapModel();
                Map<String, Map<String, String>> v2 = new Map<String, Map<String, String>>();
                String v3 = "key1";
                Map<String, String> v4 = new Map<String, String>();
                String v5 = "nestedKey1";
                String v6 = "kek";
                v4.put(v5, v6);
                v2.put(v3, v4);
                String v7 = "key2";
                Map<String, String> v8 = new Map<String, String>();
                String v9 = "nestedKey2";
                String v10 = "kek x2";
                v8.put(v9, v10);
                v2.put(v7, v8);
                v1.setMapProperty(v2);
                """;
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Can Process Map With Nested List")
    public void canProcessMapWithNestedList() {
        String actualResult = codeGenerator.generate("nestedListMapTest.json", "org.example.model");
        String expectedResult = """
                NestedListMapModel v1 = new NestedListMapModel();
                Map<String, List<Integer>> v2 = new Map<String, List<Integer>>();
                String v3 = "key1";
                Integer v4 = Integer.valueOf("1");
                Integer v5 = Integer.valueOf("2");
                Integer v6 = Integer.valueOf("3");
                List<Integer> v7 = List.of(v4, v5, v6);
                v2.put(v3, v7);
                String v8 = "key2";
                Integer v9 = Integer.valueOf("4");
                Integer v10 = Integer.valueOf("5");
                Integer v11 = Integer.valueOf("6");
                List<Integer> v12 = List.of(v9, v10, v11);
                v2.put(v8, v12);
                v1.setMapProperty(v2);
                """;
        Assertions.assertEquals(expectedResult, actualResult);
    }

}
