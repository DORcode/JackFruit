package com.jackfruit.mall;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.jackfruit.mall.utils.DateUtils;
import com.jackfruit.mall.utils.LogUtils;
import com.jackfruit.mall.utils.PathUtil;
import com.jackfruit.mall.utils.permission.PermissionsManager;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import common.lib.netstatus.NetBroadcastReceiver;

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
        NetBroadcastReceiver.registerNetworkReceiver(getApplicationContext());
        PermissionsManager.init(getApplicationContext());
        ZXingLibrary.initDisplayOpinion(this);
        SDKInitializer.initialize(getApplicationContext());
        Config.DEBUG = true;
        UMShareAPI.get(this);
        //微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //豆瓣RENREN平台目前只能在服务器端配置
        //新浪微博
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        //易信
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");
        PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
        PlatformConfig.setVKontakte("5764965","5My6SNliAaLxEm3Lyd9J");
        PlatformConfig.setDropbox("oz8v5apet3arcdy","h7p2pjbzkkxt02a");

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
