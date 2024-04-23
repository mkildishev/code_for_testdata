package com.mkildishev.generator;

import com.mkildishev.generator.converter.Converter;
import com.mkildishev.generator.converter.factory.ConverterFactory;
import com.mkildishev.generator.utils.ClassLoaderUtils;
import com.mkildishev.generator.utils.Utils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.StringUtils;

public class CodeGenerator {

    ConverterFactory converterFactory;

    public CodeGenerator() {
        converterFactory = new ConverterFactory();
    }


    public String generate(String jsonFile, String modelPackage, MavenProject mavenProject) throws MojoExecutionException {
        ClassLoaderUtils classLoaderUtils = new ClassLoaderUtils(mavenProject); // doubtful solution
        Thread.currentThread().setContextClassLoader(classLoaderUtils.getClassLoader());
        var objJson = Utils.getResource(jsonFile);
        var className = StringUtils.capitalise(objJson.fields().next().getKey());
        var objToProcess = objJson.fields().next().getValue();
        var clazz = Utils.getClass(modelPackage + "." + className);
        Converter converter = converterFactory.createConverter(clazz);
        return converter.convert(objToProcess, clazz);
    }


}
