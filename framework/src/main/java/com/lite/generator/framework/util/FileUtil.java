package com.lite.generator.framework.util;

import com.lite.generator.framework.Application;

import java.io.File;
import java.net.URL;

public class FileUtil {

    public FileUtil() {

    }

    public static String getProjectPath() {
        URL url = Application.class .getProtectionDomain().getCodeSource().getLocation();
        String filePath;
        try {
            filePath = java.net.URLDecoder.decode (url.getPath(), "utf-8");
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        if (filePath.endsWith(".jar")) {
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        }
        return new File(filePath).getAbsolutePath();
    }

    public static void mkdirs(String path){
        File pathFile = new File(path);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
    }

    public static boolean deleteFile(File deleteFile){
        File[] files = deleteFile.listFiles();
        if(files != null) {
            for (int i = 0 ; i < files.length ; i++) {
                File file = files[i];
                if(file.isDirectory()) {
                    FileUtil.deleteFile(file);
                }
                else {
                    if (file.delete()){

                    }
                    else {
                        return false;
                    }
                }
            }
        }
        return deleteFile.delete();
    }

}
