package com.jackfruit.mall.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import common.lib.utils.DateUtils;

/**
 * 写日志文件
 * Created by Stats on 2016-12-02.
 */

public class LogRecord {
    private static final String TAG = "LogRecord";
    private static ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue<String>();
    private static boolean logFlag = false;
    private static boolean fileFlag = false;//创建txt文件线程是否运行
    private static LogRecord instance;
    private String logPath;
    private LogRunnable logRunnable;
    private FileRunnable fileRunnable;
    private Thread thread;
    private Thread fileThread;
    private int dayAgo = 7;

    private LogRecord() {
        fileRunnable = new FileRunnable();
        this.logPath = PathUtil.getLogPath();
        if(getLogFileLastestTime() == 0 || !fileIsToday()) {
            fileThread = new Thread(fileRunnable);
            if(!fileFlag) {
                fileThread.start();
                try {
                    fileThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        logRunnable = new LogRunnable();
    }

    public static LogRecord init() {
        if(instance == null) {
            instance = new LogRecord();
        }
        return instance;
    }

    public void produceLog(String str) {
        queue.add(str);
        //V2Log.d(TAG, "produceLog: 队列大小:" + queue.size());
        threadStart();
    }
    private void threadStart() {
        if(!logFlag) {
            logFlag = true;
            //logRunnable = new LogRunnable();
            thread = new Thread(logRunnable);
            thread.start();
        }
    }

    //写日志
    public synchronized void writeLog(String txt) {
        try{

            File file = new File(logPath + File.separator + "log_" + getLogFileLastestTime() + ".txt");
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(txt);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            //V2Log.d("", "writeLog: 失败" + e.toString());
        }
    }

    //创建当天日志文件
    public synchronized String createTXTFile() {

        try {
            String name = "log_" + DateUtils.removeHMS(new Date().getTime()) + ".txt";
            File file = new File(logPath + File.separator + name);
            file.createNewFile();
            produceLog(new AppLog("claxx", "method", "time", "desc").toString());
            return name;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteTXTFiles() {
        long present = getLogFileLastestTime();
        File fold = new File(logPath);
        File files[] = fold.listFiles();
        fold.list();
        if(files == null) {
            return;
        }
        for(int i = 1; i < files.length; i++) {
            File file = files[i];
            long old = getDateFromFileName(file.getName());
            if(DateUtils.dateDiff(present, old) > dayAgo) {
                file.delete();
            }
        }
    }

    //日志文件是否为当天
    private boolean fileIsToday() {
        long lastest = getLogFileLastestTime();
        try {
            if(DateUtils.removeHMS() == lastest) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    //获取日志文件夹中最近文件的时间
    private synchronized long getLogFileLastestTime() {
        File file = new File(logPath);
        File[] files = file.listFiles();

        if(files != null) {
            long[] times = new long[files.length];

            for (int i = 0; i < files.length; i++) {
                times[i] = getDateFromFileName(files[i].getName());
            }
            long max = 0;
            for (int j = 0; j < times.length; j++) {
                if(times[j] > max) {
                    max = times[j];
                }
            }
            return max;
        }
        return 0;
    }

    //获取log文件夹中时间最近的文件名
    private String getLogFileLastestName() {
        if(getLogFileLastestTime() == 0) {
            createTXTFile();
            try {
                return "log_" + DateUtils.removeHMS(new Date().getTime()) + ".txt";
            } catch (ParseException e) {

                e.printStackTrace();
                return "log_" + 0 + ".txt";
            }
        } else {
            return "log_" + getLogFileLastestTime() + ".txt";
        }
    }

    //获取文件名中的时间
    private long getDateFromFileName(String name) {
        return Long.valueOf(name.split("log_")[1].replace(".txt", ""));
    }

    class LogRunnable implements Runnable {

        @Override
        public void run() {
            while (true) {
                if(!fileIsToday()) {
                    if(!fileFlag) {
                        fileThread.start();
                        try {
                            fileThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (!queue.isEmpty() && getLogFileLastestTime() != 0){
                    writeLog((String) queue.poll());
                }
                if(!logFlag) {
                    break;
                }
            }
        }
    }

    class FileRunnable implements Runnable {

        @Override
        public void run() {
            fileFlag = true;
            createTXTFile();
            deleteTXTFiles();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
