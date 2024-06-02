package com.mkildishev.generator.utils;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class ClassLoaderUtils {

    ClassLoader classLoader;

    public ClassLoaderUtils(MavenProject project) {
        try
        {
            List<String> classpathElements = project.getCompileClasspathElements();
            classpathElements.add(project.getBuild().getOutputDirectory());
            classpathElements.add(project.getBuild().getTestOutputDirectory());
            URL urls[] = new URL[classpathElements.size()];

            for (int i = 0; i < classpathElements.size(); ++i)
            {
                urls[i] = new File(classpathElements.get(i)).toURI().toURL();
            }
            this.classLoader = new URLClassLoader(urls, getClass().getClassLoader());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Couldn't create a classloader.", e);
        }
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }
}
