package ru.sberbank.jschool.homework.maruev;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Иван on 05.03.2018.
 */
public class SpecialClassLoader extends ClassLoader {

    private static Map<String, Class> storageClass = new HashMap<>();

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        File file = new File(name);
        Class<?> pluginClass = null;
        String className;

        String[] fileList = file.list();

        if (fileList.length == 0) {
            throw new ClassNotFoundException();
        }

        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].endsWith(".class")) {
                className = fileList[i].replaceAll(".class", "");

                if (storageClass.containsKey(className)) {
                    pluginClass = storageClass.get(className);
                } else {
                    pluginClass = findClass(name + "/" + className);
                    storageClass.put(className, pluginClass);
                }
            }
        }
        return pluginClass;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String[] fileList = name.split("/");
        String className = fileList[fileList.length - 1].replaceAll(".class", "");

        try {
            byte[] byteClass = convertClassInBytes(name + ".class");
            return defineClass(className, byteClass, 0, byteClass.length);
        } catch (IOException e) {
            throw new ClassNotFoundException();
        }
    }

    protected byte[] convertClassInBytes(String classPath) throws IOException {
        return Files.readAllBytes(Paths.get(classPath));
    }
}
