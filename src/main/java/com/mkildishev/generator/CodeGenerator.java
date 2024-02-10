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
// Результат надо сохранять в файл
public class CodeGenerator {

    ConverterFactory converterFactory;

    public CodeGenerator() {
        converterFactory = new ConverterFactory();
    }


    public String generate(String file, String jar, String _package) {
        ObjectMapper mapper = new ObjectMapper();
        URLClassLoader classLoader = Utils.getClassLoader(jar);
        try (InputStream s = classLoader.getResource(file).openStream()) {
            var json = s.readAllBytes();
            var objJson = mapper.readTree(json);
            var className = Utils.capitalize(objJson.fields().next().getKey());
            var objToProcess = objJson.fields().next().getValue();
            var clazz = Utils.getClass(_package + "." + className);
            Converter converter = converterFactory.createConverter(clazz);
            return converter.convert(objToProcess, clazz);
        } catch (IOException e) {
            throw new RuntimeException("File " + file + " cannot be found, please check your configuration", e);
        }
    }

}
