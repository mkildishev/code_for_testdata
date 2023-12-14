package com.mkildishev.generator.model.newmodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkildishev.generator.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
// Можно ли улучшить то, что есть здесь?
// Есть ли смысл разделять создание объектов для примитивов и сложных объектов?
public class MainClass {

    MakerFactory makerFactory;

    public String generateCode(String file, String jar, String _package) {
        ObjectMapper mapper = new ObjectMapper();
        // Need to improve ability to change class loader
        // If we can run plugin with using common application classes, then ok
        // Otherwise we have to parse model source code
        URLClassLoader classLoader = null;
        try {
            classLoader = new URLClassLoader(new URL[]{new File(jar).toURI().toURL()});
        } catch (MalformedURLException e) {
            //do nothing until test
            //throw new RuntimeException(e);
        }
        //classLoader.loadClass()
        try (InputStream s = classLoader.getResource(file).openStream()) {
            var json = s.readAllBytes();
            var objJson = mapper.readTree(json);
            var className = Utils.capitalize(objJson.fields().next().getKey());
            var objToProcess = objJson.fields().next().getValue();
            var clazz = Class.forName(_package + "." + className); // first node is a classname
            Maker maker = makerFactory.createMaker(clazz);
            var str = maker.make(objToProcess, clazz);
            System.out.println(str);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
