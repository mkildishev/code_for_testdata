package com.mkildishev.generator.plugin;

import com.mkildishev.generator.CodeGenerator;
import com.mkildishev.generator.utils.Utils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "generate")
public class PluginMojo extends AbstractMojo {


    @Parameter(property = "jsonFile")
    private String jsonFile;


    @Parameter(property = "jar")
    private String jar;

    @Parameter(property = "packageName")
    private String modelPackage;

    @Override
    public void execute() throws MojoExecutionException
    {
        CodeGenerator generator = new CodeGenerator();
        var result = generator.generate(jsonFile, jar, modelPackage);
        Utils.saveFile("output.txt", result);
    }
}
