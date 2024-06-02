package com.mkildishev.generator.plugin;

import com.mkildishev.generator.CodeGenerator;
import com.mkildishev.generator.utils.ClassLoaderUtils;
import com.mkildishev.generator.utils.Utils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

@Mojo(name = "generate", requiresDependencyResolution = ResolutionScope.RUNTIME)
public class PluginMojo extends AbstractMojo {

    @Parameter(property = "jsonFile")
    private String jsonFile;

    @Parameter(property = "packageName")
    private String modelPackage;

    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    @Override
    public void execute()
    {
        ClassLoaderUtils classLoaderUtils = new ClassLoaderUtils(project);
        CodeGenerator generator = new CodeGenerator(classLoaderUtils.getClassLoader());
        var result = generator.generate(jsonFile, modelPackage);
        String targetDirectoryPath = System.getProperty("user.dir") + "/generated-testdata";
        Utils.saveFile(targetDirectoryPath,"output.txt", result);
    }
}
