package com.jackfruit.mall.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    public static void copyFile(String filePath, String toPath) {
        InputStream fis = null;
        OutputStream fos = null;
        ByteArrayOutputStream bos = null;
        int len;

        try {
            bos = new ByteArrayOutputStream();
            fis = new FileInputStream(new File(filePath));
            fos = new FileOutputStream(new File(toPath));
            byte[] buf = new byte[1024];
            while((len = fis.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
            fos.write(bos.toByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
                fis.close();
                fos.close();
            } catch (Exception e) {

            }
        }
    }

    public static void deleteFiles(String fold) {
        File files[] = new File(fold).listFiles();
        for(int i = 0; i < files.length; i++) {
            if(files[i].isDirectory()) {
                deleteFiles(files[i].toString());
            } else {
                files[i].delete();
            }
        }
    }
}
