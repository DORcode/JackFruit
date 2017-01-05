package common.lib.netstatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import common.lib.utils.NetUtils;

/**
 * @项目名称 JackFruit
 * @类：common.lib.netstatus
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/5 10:35
 * @修改
 * @修改时期 2017/1/5 10:35
 */
public class NetBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "NetChangeObservableImpl";
    private static List<WeakReference<NetChangeObserver>> observers = new ArrayList<WeakReference<NetChangeObserver>>();
    private static NetBroadcastReceiver broadcastReceiver;
    private static boolean isNetConnect = false;
    private static NetUtils.NetType netType;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            netType = NetUtils.getAPNType(context);
            if(NetUtils.isConnected(context)) {
                isNetConnect = true;
            } else {
                isNetConnect = false;
            }
            notifyObservers();
        }
    }

    public static NetBroadcastReceiver getReceiver() {
        if(broadcastReceiver == null) {
            synchronized (NetBroadcastReceiver.class) {
                if(null == broadcastReceiver) {
                    Log.d(TAG, "getReceiver: 网络发生变化");
                    broadcastReceiver = new NetBroadcastReceiver();
                }
            }
        }
        return broadcastReceiver;
    }

    //注册网络状态广播
    public static void registerNetworkReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.getApplicationContext().registerReceiver(getReceiver(), filter);
    }

    //取消广播注册
    public static void unregisterNetworkReceiver(Context context) {
        if(broadcastReceiver != null) {
            context.getApplicationContext().unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    //加入观察
    public static void registerObserver(NetChangeObserver observer) {
        if(observers == null) {
            observers = new ArrayList<WeakReference<NetChangeObserver>>();
        }

        observers.add(new WeakReference<NetChangeObserver>(observer));
    }

    //取消观察网络变化
    public static void unregisterObserver(NetChangeObserver observer) {
        Iterator<WeakReference<NetChangeObserver>> iterator = observers.iterator();
        while (iterator.hasNext()) {
            WeakReference<NetChangeObserver> weakReference = iterator.next();
            if(weakReference.get() == observer) {
                iterator.remove();
            }
        }
    }

    //通知观察者
    public static void notifyObservers() {
        Iterator<WeakReference<NetChangeObserver>> iterator = observers.iterator();
        while (iterator.hasNext()) {
            WeakReference<NetChangeObserver> weakReference = iterator.next();
            NetChangeObserver observer = weakReference.get();
            if(observer != null) {
                if(isNetConnect) {
                    observer.onNetConnect(netType);
                } else {
                    observer.onNetDisConnect();
                }

            }
        }
    }
}
