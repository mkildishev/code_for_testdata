package org.example;


import com.mkildishev.generator.CodeGenerator;
import com.mkildishev.generator.utils.Utils;


/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
        CodeGenerator mc = new CodeGenerator();
        var result  = mc.generate("test.json",
                "testdata-generator-maven-plugin-1.0-SNAPSHOT.jar",
                "org.example.model");
        Utils.saveFile("output.txt", result);
    }
}
