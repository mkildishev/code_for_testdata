# Code for testdata
## JSON to Java code converter wrapped in Maven plugin

## How to use
1. Run mvn clean install in console or IDE
2. To use plugin in your project, need to add plugin info in the pom.xml. 

   There is two parameters:   `jsonFile` - the file with your json,   `modelPackage` - package where json model in described

   E.g.
   ```    
   <plugins>
     <plugin>
       <groupId>org.example</groupId>
       <artifactId>testdata-generator-maven-plugin</artifactId>
       <version>1.0-SNAPSHOT</version>
       <configuration>
         <jsonFile>test.json</jsonFile>
         <modelPackage>org.example.model</modelPackage>
       </configuration>
     </plugin>
   </plugins>
   ```
3. Result appears in **generated-testdata/output.txt** file