package com.jackfruit.mall;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.jackfruit.mall.utils.DateUtils;
import com.jackfruit.mall.utils.LogUtils;
import com.jackfruit.mall.utils.PathUtil;
import com.jackfruit.mall.utils.permission.PermissionsManager;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * @项目名称 JackFruit
 * @类：com.jackfruit.mall
 * @描述 describe
 * @创建者 kh
 * @日期 2016/12/27 16:08
 * @修改
 * @修改时期 2016/12/27 16:08
 */
public class JFApplication extends Application {

    private static Context context;
    private static JFApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        instance = this;
        PermissionsManager.init(getApplicationContext());
        ZXingLibrary.initDisplayOpinion(this);
        SDKInitializer.initialize(getApplicationContext());
        PathUtil.init();
        LogUtils.init().writeLog(new LogUtils.AppLog("", "onCreate()", DateUtils.getDatetime(), null).toString());
        LogUtils.init().writeLog(new LogUtils.AppLog("Ap", "oC", DateUtils.getDatetime(), "").toString());
    }

    public static Context getAppContext() {
        return context;
    }

    public static JFApplication getApp() {
        return instance;
    }
}
