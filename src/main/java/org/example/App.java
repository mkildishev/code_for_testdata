package org.example;


import com.mkildishev.generator.CodeGenerator;


/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
        CodeGenerator mc = new CodeGenerator();
        mc.generate("test.json",
                "testdata-generator-maven-plugin-1.0-SNAPSHOT.jar",
                "org.example.model");
        System.out.println( "Hello World!" );
    }
}
