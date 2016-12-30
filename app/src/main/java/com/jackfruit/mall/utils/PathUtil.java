package com.jackfruit.mall.utils;

import android.os.Environment;

import com.jackfruit.mall.JFApplication;

import java.io.File;

/**
 * Created by Stats on 2016-11-27.
 */

public class PathUtil {
    private static PathUtil instance;
    private static final String APP_NAME = "jackfruit";
    private static String APP_DB_PATH;
    private static String APP_ROOT_PATH;
    private static String APP_LOG_PATH;
    private static String APP_IMAGE_PATH;

    public static void init() {
        initExternalRootPath();
        initDbpath();
        initLogPath();
        initImagePath();
    }

    private static void initExternalRootPath() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            APP_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + APP_NAME;
            if(!new File(APP_ROOT_PATH).exists()) {
                new File(APP_ROOT_PATH).mkdirs();
            }
        }
    }

    public static String getExternalRootPath() {
        return APP_ROOT_PATH;
    }

    private static void initDbpath() {
        String path = JFApplication.getApp().getApplicationContext().getFilesDir().getAbsolutePath();
        APP_DB_PATH = path.substring(0, path.lastIndexOf(File.separator)) + File.separator + "databases";
        if(!new File(APP_DB_PATH).exists()) {
            new File(APP_DB_PATH).mkdirs();
        }
    }

    public static String getDbPath() {

        if(!new File(APP_DB_PATH).exists()) {
            new File(APP_DB_PATH).mkdirs();
        }
        return APP_DB_PATH;
    }

    private static void initLogPath() {
        if(APP_ROOT_PATH != null) {
            APP_LOG_PATH = APP_ROOT_PATH + File.separator + "log";
            createFold(APP_LOG_PATH);
        }
    }

    public static String getLogPath() {
        initLogPath();
        return APP_LOG_PATH;
    }

    private static void initImagePath() {
        if(APP_ROOT_PATH != null) {
            APP_IMAGE_PATH = APP_ROOT_PATH + File.separator + "image";
            createFold(APP_IMAGE_PATH);
        }
    }

    public static String getImagePath() {
        initImagePath();
        return APP_IMAGE_PATH;
    }

    private static void createFold(String path) {
        if(!new File(path).exists()) {
            new File(path).mkdirs();
        }
    }
}
