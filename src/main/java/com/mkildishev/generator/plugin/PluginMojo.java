package com.mkildishev.generator.plugin;

import com.mkildishev.generator.CodeGenerator;
import com.mkildishev.generator.utils.Utils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

@Mojo(name = "generate", requiresDependencyResolution = ResolutionScope.RUNTIME)
public class PluginMojo extends AbstractMojo {

    @Parameter(property = "jsonFile")
    private String jsonFile;

    @Parameter(property = "packageName")
    private String modelPackage;

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException
    {
        CodeGenerator generator = new CodeGenerator();
        var result = generator.generate(jsonFile, modelPackage, project);
        String targetDirectoryPath = System.getProperty("user.dir") + "/generated-testdata";
        Utils.saveFile(targetDirectoryPath,"output.txt", result);
    }
}
