package com.mkildishev.generator.plugin;

import com.mkildishev.generator.builder.SetterBuilder;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "generate")
public class PluginMojo extends AbstractMojo {


    @Parameter(property = "file")
    private String file;


    @Parameter(property = "jar")
    private String jar;

    @Parameter(property = "package")
    private String _package;

    @Override
    public void execute() throws MojoExecutionException
    {
        var result = SetterBuilder.generateCode(file, jar,_package);
        System.out.println(result);
    }
}
