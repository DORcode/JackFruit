package com.jackfruit.mall.utils;

import com.jackfruit.mall.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Stats on 2016-12-02.
 */

public class LogUtils {
    private static ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue<String>();
    private static boolean flag = true;
    private static LogUtils instance;
    private LogRunnable logRunnable;
    private Thread thread;
    private int dayAgo = 7;
    private JFPreferenceUtil preferenceUtil;

    private LogUtils() {
        logRunnable = new LogRunnable();
        preferenceUtil = JFPreferenceUtil.getInstance();
        if(JFPreferenceUtil.getInstance().getLogName() == null || !fileIsToday()) {
            createTXTFile();
            writeLog(new AppLog("claxx", "method", "time", "desc").toString());
        }
    }

    public static LogUtils init() {
        if(instance == null) {
            instance = new LogUtils();
        }
        return instance;
    }

    public void produceLog(String str) {
        queue.add(str);
        threadStart();

    }
    private void threadStart() {
        if(!flag) {
            flag = true;
            logRunnable = new LogRunnable();
            thread = new Thread(logRunnable);
            thread.start();
        }
    }

    public void writeLog(String txt) {
        try{
            if(BuildConfig.DEBUG) {
                File file = new File(PathUtil.getLogPath() + File.separator + preferenceUtil.getLogName());
                FileWriter fileWriter = new FileWriter(file, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(txt);
                bufferedWriter.newLine();
                bufferedWriter.close();
            }
        } catch (IOException e) {

        }

    }

    public void createTXTFile() {

        try {
            String name = "log_" + DateUtils.removeHMS(new Date().getTime()) + ".txt";
            File file = new File(PathUtil.getLogPath() + File.separator + name);
            file.createNewFile();
            preferenceUtil.setLogName(name);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void deleteTXTFiles() {
        long present = getDateFromFileName(preferenceUtil.getLogName());
        File fold = new File(PathUtil.getLogPath());
        File files[] = fold.listFiles();
        for(int i = 1; i < files.length; i++) {
            File file = files[i];
            long old = getDateFromFileName(file.getName());
            if(DateUtils.dateDiff(present, old) > 1000) {
                file.delete();
            }
        }
    }

    private boolean fileIsToday() {
        long present = getDateFromFileName(preferenceUtil.getLogName());
        try {
            if(DateUtils.removeHMS() == present) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    private long getDateFromFileName(String name) {
        return Long.valueOf(name.split("log_")[1].replace(".txt", ""));
    }

    class LogRunnable implements Runnable {

        @Override
        public void run() {
            while (true) {
                if(fileIsToday()) {
                    createTXTFile();
                }
                deleteTXTFiles();
                if (!queue.isEmpty() && preferenceUtil.getLogName() != null){
                    writeLog((String) queue.poll());
                }

                if(!flag) {
                    break;
                }

            }
        }
    }

    public static class AppLog {
        private String claxx;
        private String method;
        private String time;
        private String desc;

        public AppLog(String claxx, String method, String time, String desc) {
            this.claxx = claxx;
            this.method = method;
            this.time = time;
            this.desc = desc;
        }

        @Override
        public String toString() {
            return claxx + '\t' + method + '\t' + time + '\t' + desc + '\t';
        }
    }
}
