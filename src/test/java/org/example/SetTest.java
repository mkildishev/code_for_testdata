package org.example;

import com.mkildishev.generator.CodeGenerator;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SetTest {

    private CodeGenerator codeGenerator;

    @BeforeAll
    void init() {
        codeGenerator = new CodeGenerator(Thread.currentThread().getContextClassLoader());
    }

    @Test
    @DisplayName("Can process set")
    void canProcessSet() {
        String actualResult = codeGenerator.generate("simpleSetTest.json", "org.example.model");
        String expectedResult = """
                SimpleSetModel v1 = new SimpleSetModel();
                String v2 = "prop1";
                String v3 = "prop2";
                String v4 = "prop3";
                Set<String> v5 = Set.of(v2, v3, v4);
                v1.setStringProperties(v5);
                """;
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Can process nested set")
    void canProcessNestedSet() {
        String expectedResult = """
                NestedSetModel v1 = new NestedSetModel();
                String v2 = "prop1";
                Set<String> v3 = Set.of(v2);
                String v4 = "prop2";
                Set<String> v5 = Set.of(v4);
                String v6 = "prop3";
                String v7 = "prop4";
                Set<String> v8 = Set.of(v6, v7);
                Set<Set<String>> v9 = Set.of(v3, v5, v8);
                v1.setSet(v9);
                """;
        String actualResult = codeGenerator.generate("nestedSetTest.json", "org.example.model");
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Can process empty set")
    void canProcessEmptySet() {
        String expectedResult = """
                NestedSetModel v1 = new NestedSetModel();
                Set<String> v2 = Set.of();
                Set<String> v3 = Set.of();
                Set<String> v4 = Set.of();
                Set<Set<String>> v5 = Set.of(v2, v3, v4);
                v1.setSet(v5);
                """;
        String actualResult = codeGenerator.generate("emptyNestedSetTest.json", "org.example.model");
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
