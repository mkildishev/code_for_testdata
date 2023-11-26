package org.example;


import static com.mkildishev.generator.builder.SetterBuilder.generateCode;



/**
 * Hello world!
 *
 */
public class App 
{
    private static final String SET = "set";

    public static void main( String[] args )
    {
        generateCode("test.json");
        System.out.println( "Hello World!" );
    }
}
