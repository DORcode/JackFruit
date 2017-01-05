package common.lib.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import common.lib.netstatus.NetBroadcastReceiver;
import common.lib.netstatus.NetChangeObserver;
import common.lib.utils.NetUtils;

/**
 * @项目名称 JackFruit
 * @类：common.lib.base
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/4 17:55
 * @修改
 * @修改时期 2017/1/4 17:55
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {
    protected static String TAG = null;
    protected Context mContext;
    //手机屏幕宽度
    protected int mScreenWidth = 0;
    //手机屏幕高度
    protected int mScreenHeight = 0;
    //手机屏幕
    protected float mScreenDensity = 0.0f;

    protected NetChangeObserver mNetChangeObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        mContext = this;
        if(isBindEventBusHere()) {
            EventBus.getDefault().register(this);
        }
        if(getRootViewLayoutId() != 0) {
            setContentView(getRootViewLayoutId());
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenDensity = displayMetrics.density;

        NetBroadcastReceiver.registerObserver(mNetChangeObserver);
        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnect(NetUtils.NetType type) {
                if(type.equals(NetUtils.NetType.MOBILE)) {
                    Toast.makeText(mContext, "手机网络", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Wifi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNetDisConnect() {

            }
        };
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
    }

    abstract int getRootViewLayoutId();

    abstract boolean isBindEventBusHere();


    protected void gotoActivity(Class clx) {
        startActivity(new Intent(this, clx));
    }

    protected void gotoActivity(Class clx, Bundle bundle) {
        Intent intent = new Intent(this, clx);
        if(intent != null) intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void gotoActivity(Class clx, Bundle bundle, boolean isFinishCurrent) {
        gotoActivity(clx, bundle);
        if (isFinishCurrent) {
            finish();
        }
    }
}
