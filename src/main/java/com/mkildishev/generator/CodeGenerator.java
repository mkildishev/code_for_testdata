package com.mkildishev.generator;

import com.mkildishev.generator.converter.Converter;
import com.mkildishev.generator.converter.factory.ConverterFactory;
import com.mkildishev.generator.utils.ClassLoaderUtils;
import com.mkildishev.generator.utils.Utils;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.StringUtils;

public class CodeGenerator {

    ConverterFactory converterFactory;

    public CodeGenerator(ClassLoader classLoader) {
        converterFactory = new ConverterFactory();
        Thread.currentThread().setContextClassLoader(classLoader);
    }


    public String generate(String jsonFile, String modelPackage){
        var objJson = Utils.getResource(jsonFile);
        var className = StringUtils.capitalise(objJson.fields().next().getKey());
        var objToProcess = objJson.fields().next().getValue();
        var clazz = Utils.getClass(modelPackage + "." + className);
        Converter converter = converterFactory.createConverter(clazz);
        return converter.convert(objToProcess, clazz);
    }


}
