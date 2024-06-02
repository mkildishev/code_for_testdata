package org.example;

import com.mkildishev.generator.CodeGenerator;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ListTest {
    private CodeGenerator codeGenerator;

    @BeforeAll
    void init() {
        codeGenerator = new CodeGenerator(Thread.currentThread().getContextClassLoader());
    }

    @Test
    @DisplayName("Can Process List")
    public void canProcessList() {
        String actualResult = codeGenerator.generate("simpleListTest.json", "org.example.model");
        String expectedResult = """
                SimpleListModel v1 = new SimpleListModel();
                String v2 = "prop1";
                String v3 = "prop2";
                String v4 = "prop3";
                List<String> v5 = List.of(v2, v3, v4);
                v1.setStringProperties(v5);
                """;
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Can Process Nested List")
    public void canProcessNestedList() {
        String expectedResult = """
                NestedListModel v1 = new NestedListModel();
                String v2 = "prop1";
                List<String> v3 = List.of(v2);
                String v4 = "prop2";
                List<String> v5 = List.of(v4);
                String v6 = "prop3";
                String v7 = "prop4";
                List<String> v8 = List.of(v6, v7);
                List<List<String>> v9 = List.of(v3, v5, v8);
                v1.setList(v9);
                """;
        String actualResult = codeGenerator.generate("nestedListTest.json", "org.example.model");
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Can Process Empty List")
    void canProcessEmptyList() {
        String expectedResult = """
                NestedListModel v1 = new NestedListModel();
                List<String> v2 = List.of();
                List<String> v3 = List.of();
                List<String> v4 = List.of();
                List<List<String>> v5 = List.of(v2, v3, v4);
                v1.setList(v5);
                """;
        String actualResult = codeGenerator.generate("emptyNestedListTest.json", "org.example.model");
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
