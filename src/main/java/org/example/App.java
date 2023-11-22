package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mkildishev.generator.model.NodeType;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import static com.mkildishev.generator.builder.SetterBuilder.buildSetter;
import static com.mkildishev.generator.builder.SetterBuilder.generateCode;
import static com.mkildishev.generator.utils.Utils.capitalize;
import static com.mkildishev.generator.utils.Utils.getType;


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







//    Expected result:
//    TestClass testClass = new TestClass();
//    testClass.setId(123);
//    testClass.setName("TeStClass");
//    testClass.setPrice(BigDecimal.valueOf(13.37));
//    testClass.setStringProperties(List.of("prop1", "prop2", "prop3"));
//    testClass.setMapProperties();
//    testClass.setObjectProperties();

// мапы и объекты - это ObjectNode
// списки - ListNode
// остальное - *Node
}
