package org.example;

import com.mkildishev.generator.CodeGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ListTest {
    private CodeGenerator codeGenerator;

    @BeforeAll
    void init() {
        codeGenerator = new CodeGenerator();
    }

    @Test
    public void canParseListOfLists() {
//        String str = codeGenerator.generate("test.json", "simple-test-1.0-SNAPSHOT.jar", "org.example.model");
//        return;
    }
}
