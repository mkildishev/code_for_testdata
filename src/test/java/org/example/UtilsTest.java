package org.example;

import com.mkildishev.generator.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UtilsTest {

    @TempDir
    File tempDirectory;

    @Test
    @DisplayName("Make Object Is Able To Create Correct String")
    void makeObjectIsAbleToCreateCorrectString(){
        String expectedResult = "String variable = new String();\n";
        String actualResult = Utils.makeObject("String", "variable");
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Make Object Is Able To Create Correct Collection")
    void makeObjectIsAbleToCreateCorrectCollection() {
        String expectedResult = "List<BigInteger> variable = new List<BigInteger>();\n";
        String actualResult = Utils.makeObject("List<BigInteger>", "variable");
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Make Object Does Not Cut To Diamonds")
    void makeObjectsDoesNotCutToDiamonds() {
        String expectedResult = "List<BigInteger> variable = new List<>();\n";
        String actualResult = Utils.makeObject("List<BigInteger>", "variable");
        Assertions.assertNotEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Can Check If It Is Generic Type")
    void canCheckIfItIsGenericType() {
        List<String> genericList = new ArrayList<>();
        Type actualType = genericList.getClass().getGenericSuperclass();
        Assertions.assertTrue(Utils.isGenericType(actualType));
    }

    @Test
    @DisplayName("Can Check If It Is Not Generic Type")
    void canCheckIfItIsNotGenericType() {
        Type actualType = String.class;
        Assertions.assertFalse(Utils.isGenericType(actualType));
    }

    @Test
    @DisplayName("Can Get Class By Name")
    void canGetClassByName() {
        Class<UtilsTest> expectedClass = UtilsTest.class;
        Assertions.assertEquals(expectedClass, Utils.getClass("org.example.UtilsTest"));
    }

    @Test
    @DisplayName("Throws If Class Does Not Exists")
    void throwsIfClassDoesNotExists() {
        Assertions.assertThrows(RuntimeException.class,
                () -> Utils.getClass("UtilsTest"),
                "Class UtilsTest cannot be found. Please, check your configuration");
    }

    @Test
    @DisplayName("Can save file with content")
    void canSaveFileWithContent() throws IOException {
        File expectedFile = new File(tempDirectory, "expectedFile.txt");
        List<String> content = List.of("List<String> a = new ArrayList<>();", "a.add(\"one\")"); // why comma in debug?
        Files.write(expectedFile.toPath(), content);
        Utils.saveFile(tempDirectory.getPath(), "actualFile.txt", "List<String> a = new ArrayList<>();\na.add(\"one\")");
        Path actualFilePath = Path.of(tempDirectory.getPath(), "actualFile.txt");
        Assertions.assertAll(
                () -> Assertions.assertTrue(Files.exists(actualFilePath), "File should exists"),
                () -> Assertions.assertEquals(Files.readAllLines(expectedFile.toPath()), Files.readAllLines(actualFilePath))
        );

    }
    
}
