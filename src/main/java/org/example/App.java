package org.example;


import static com.mkildishev.generator.builder.SetterBuilder.generateCode;



/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
        generateCode("test.json",
                "testdata-generator-maven-plugin-1.0-SNAPSHOT.jar",
                "org.example.model");
        System.out.println( "Hello World!" );
    }
}
