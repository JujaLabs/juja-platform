package juja.microservices.slack.archive.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

public class Util {

    public static String readStringFromFile(String fileName){

        String result = "";

        Foo foo = new Foo();
        ClassLoader classLoader = foo.getClass().getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    private static class Foo{}
}
