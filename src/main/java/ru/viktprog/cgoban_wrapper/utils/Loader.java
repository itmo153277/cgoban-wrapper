package ru.viktprog.cgoban_wrapper.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

public class Loader {

    private static class CustomClassLoader extends URLClassLoader {
        private CustomClassLoader(String jarLocation) throws IOException {
            super(getClassPathURLs(jarLocation), getSystemClassLoader());
        }

        private static URL[] getClassPathURLs(String jarLocation) throws MalformedURLException {
            return new URL[]{
                Paths.get(jarLocation).toUri().toURL()
            };
        }
    }

    public static ClassLoader setClassLoader(String jarLocation) throws IOException {
        URLClassLoader classLoader = new CustomClassLoader(jarLocation);
        Thread.currentThread().setContextClassLoader(classLoader);
        return classLoader;
    }
}
