package com.jackfruit.mall.utils;

import android.support.v4.BuildConfig;
import android.util.Log;

public class LogUtils {
    private static boolean debug = BuildConfig.DEBUG;
    private static int level = debug ? Log.VERBOSE : Log.DEBUG;

    public static void d(String tag, String msg) {
        if(debug) {
            Log.d(tag, msg);
        }
        trace(Log.DEBUG, tag, msg);
    }

    public static void d(String tag, String msg, Throwable tr) {
        if(debug) {
            Log.d(tag, msg, tr);
        }
        trace(Log.DEBUG, tag, msg, tr);
    }

    public static void i(String tag, String msg) {
        if(debug) {
            Log.i(tag, msg);
        }
        trace(Log.INFO, tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr) {
        if(debug) {
            Log.i(tag, msg, tr);
        }
        trace(Log.INFO, tag, msg, tr);
    }

    public static void w(String tag, String msg) {
        if(debug) {
            Log.w(tag, msg);
        }
        trace(Log.WARN, tag, msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
        if(debug) {
            Log.w(tag, msg, tr);
        }
        trace(Log.WARN, tag, msg, tr);
    }

    public static void e(String tag, String msg) {
        if(debug) {
            Log.w(tag, msg);
        }
        trace(Log.ERROR, tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if(debug) {
            Log.w(tag, msg, tr);
        }
        trace(Log.ERROR, tag, msg, tr);
    }

    public static void v(String tag, String msg) {
        if(debug) {
            Log.v(tag, msg);
        }
        trace(Log.VERBOSE, tag, msg);
    }

    public static void v(String tag, String msg, Throwable tr) {
        if(debug) {
            Log.v(tag, msg, tr);
        }
        trace(Log.VERBOSE, tag, msg, tr);
    }

    private static void trace(int logType, String tag, String msg) {
        if(logType > level) {
            LogRecord.init().produceLog(new LogRecord.AppLog(tag, "", DateUtils.getDatetime(), msg).toString());
        }
    }

    private static void trace(int logType, String tag, String msg, Throwable tr) {
        trace(logType, tag, msg + Log.getStackTraceString(tr));
    }
}
