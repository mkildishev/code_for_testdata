package com.mkildishev.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkildishev.generator.converter.Converter;
import com.mkildishev.generator.converter.factory.ConverterFactory;
import com.mkildishev.generator.utils.Utils;
import org.codehaus.plexus.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLClassLoader;
import java.util.Objects;

// Можно ли улучшить то, что есть здесь?
// Есть ли смысл разделять создание объектов для примитивов и сложных объектов?
// Результат надо сохранять в файл
public class CodeGenerator {

    ConverterFactory converterFactory;

    public CodeGenerator() {
        converterFactory = new ConverterFactory();
    }


    public String generate(String jsonFile, String jar, String modelPackage) {
        ObjectMapper mapper = new ObjectMapper();
        URLClassLoader classLoader = Utils.getClassLoader(jar);
        try (InputStream s = Objects.requireNonNull(classLoader.getResource(jsonFile)).openStream()) {
            var json = s.readAllBytes();
            var objJson = mapper.readTree(json);
            var className = StringUtils.capitalise(objJson.fields().next().getKey());
            var objToProcess = objJson.fields().next().getValue();
            var clazz = Utils.getClass(modelPackage + "." + className);
            Converter converter = converterFactory.createConverter(clazz);
            return converter.convert(objToProcess, clazz);
        } catch (IOException e) {
            throw new RuntimeException("File " + jsonFile + " cannot be found, please check your configuration", e);
        } catch (NullPointerException e) {
            throw new RuntimeException("JAR " + jar + " cannot be found, please check your configuration", e);
        }
    }

}
