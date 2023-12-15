package com.mkildishev.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkildishev.generator.converter.Converter;
import com.mkildishev.generator.converter.factory.ConverterFactory;
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

    ConverterFactory converterFactory;

    public MainClass() {
        converterFactory = new ConverterFactory();
    }


    public String generateCode(String file, String jar, String _package) {
        ObjectMapper mapper = new ObjectMapper();
        URLClassLoader classLoader = getClassLoader(jar);
        try (InputStream s = classLoader.getResource(file).openStream()) {
            var json = s.readAllBytes();
            var objJson = mapper.readTree(json);
            var className = Utils.capitalize(objJson.fields().next().getKey());
            var objToProcess = objJson.fields().next().getValue();
            var clazz = Class.forName(_package + "." + className); // first node is a classname
            Converter converter = converterFactory.createConverter(clazz);
            var str = converter.convert(objToProcess, clazz);
            System.out.println(str);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private URLClassLoader getClassLoader(String jar) {
        try {
            return new URLClassLoader(new URL[]{new File(jar).toURI().toURL()});
        } catch (MalformedURLException e) {
            throw new RuntimeException("JAR " + jar + " cannot be found, please check your configuration", e);
        }
    }
}
