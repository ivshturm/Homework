package ru.sberbank.jschool.homework.maruev;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Иван on 06.03.2018.
 */
public class EncryptedClassLoader extends ClassLoader {
    private int offset;
    private String fileDirectory;

    public EncryptedClassLoader(ClassLoader parentLoader, int offset, String fileDirectory) {
        super(parentLoader);
        this.offset = offset;
        this.fileDirectory = fileDirectory;
    }

    /**
     * @param name - equals "fileDirectory"
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classByte = null;
        Class<?> caesarClass = null;
        File file = new File(name);

        String[] fileList = file.list();

        if (fileList.length == 0) {
            throw new ClassNotFoundException();
        }
        try {
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].endsWith(".class")) {
                    classByte = encryptAndConvertToBytes(name + "/" + fileList[i]);
                    caesarClass = defineClass(name, classByte, 0, classByte.length);
                }
            }
        } catch (IOException e) {
            throw new ClassNotFoundException();
        }
        if (caesarClass == null) throw new ClassNotFoundException();
        return caesarClass;
    }

    private byte[] encryptAndConvertToBytes(String name) throws IOException {
        byte[] classByte = Files.readAllBytes(Paths.get(name));

        for (int i = 0; i < classByte.length; i++) {
            classByte[i] = (byte) (classByte[i] - offset);
        }
        return classByte;
    }
}
