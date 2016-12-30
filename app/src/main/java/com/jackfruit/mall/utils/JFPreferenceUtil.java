package com.jackfruit.mall.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.jackfruit.mall.JFApplication;

/**
 * @项目名称 JackFruit
 * @类：com.jackfruit.mall.utils
 * @描述 describe
 * @创建者 kh
 * @日期 2016/12/29 10:26
 * @修改
 * @修改时期 2016/12/29 10:26
 */
public class JFPreferenceUtil {
    //上次是否登录
    public static final String SHARE_LAST_LOGIN = "last_login";
    private static final String SHARE_LOG_NAME = "log_name";
    private Context context;
    private static final String PREF_NAME = "";
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static JFPreferenceUtil instance;

    private JFPreferenceUtil() {
        context = JFApplication.getApp().getApplicationContext();
        sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static JFPreferenceUtil getInstance() {
        if(instance == null) {
            instance = new JFPreferenceUtil();
        }
        return instance;
    }

    public boolean isLastLogin() {
        return sp.getBoolean(SHARE_LAST_LOGIN, false);
    }

    public void setLastLogin(boolean isLogin) {
        editor.putBoolean(SHARE_LAST_LOGIN, isLogin);
        editor.commit();
    }

    public String getLogName() {
        return sp.getString(SHARE_LOG_NAME, null);
    }

    public void setLogName(String str) {
        editor.putString(SHARE_LOG_NAME, str);
        editor.commit();
    }
}
