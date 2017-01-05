package common.lib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Locale;

/**
 * @项目名称 JackFruit
 * @类：common.lib.utils
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/5 11:03
 * @修改
 * @修改时期 2017/1/5 11:03
 */

public class NetUtils {
    public static enum NetType {
        WIFI, MOBILE, NONE
    }

    //得到NetworkInfo
    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    //是否连接
    public static boolean isConnected(Context context){
        NetworkInfo info = NetUtils.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static boolean isConnected(NetworkInfo info){
        return (info != null && info.isConnected());
    }

    //是否可用
    public static boolean isAvailable(Context context){
        NetworkInfo info = NetUtils.getNetworkInfo(context);
        return (info != null && info.isAvailable());
    }

    //wifi
    public static boolean isConnectedWifi(NetworkInfo info){
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    public static boolean isConnectedWifi(Context context){
        NetworkInfo info = NetUtils.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    //mobile
    public static boolean isConnectedMobile(Context context){
        NetworkInfo info = NetUtils.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public static boolean isConnectedMobile(NetworkInfo info) {
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = mgr.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    //网络类型
    public static NetType getAPNType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo == null) return NetType.NONE;
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            /*if (networkInfo.getExtraInfo().toLowerCase(Locale.getDefault()).equals("cmnet")) {
                return NetType.CMNET;
            }else {
                return NetType.CMWAP;
            }*/
            return NetType.MOBILE;
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        }
        return NetType.NONE;
    }
}
