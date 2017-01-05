package common.lib.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import common.lib.R;
import common.lib.loading.VaryViewHelperController;
import common.lib.netstatus.NetBroadcastReceiver;
import common.lib.netstatus.NetChangeObserver;
import common.lib.utils.NetUtils;
import common.lib.utils.StatusBarUtils;

/**
 * @项目名称 JackFruit
 * @类：common.lib.base
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/5 17:52
 * @修改
 * @修改时期 2017/1/5 17:52
 */
public abstract class BaseFragmentActivity extends FragmentActivity {
    protected static String TAG = null;
    protected Context mContext;
    //手机屏幕宽度
    protected int mScreenWidth = 0;
    //手机屏幕高度
    protected int mScreenHeight = 0;
    //手机屏幕
    protected float mScreenDensity = 0.0f;
    /*转场枚举*/
    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }
    /**
     * loading view controller
     */
    private VaryViewHelperController mVaryViewHelperController = null;
    protected NetChangeObserver mNetChangeObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /*Activity 切换时的动画*/
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in,R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in,R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in,R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    break;
            }
        }

        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        mContext = this;
        if(isBindEventBusHere()) {
            EventBus.getDefault().register(this);
        }
        if(getRootViewLayoutId() != 0) {
            setContentView(getRootViewLayoutId());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }

        Bundle extras = getIntent().getExtras();
        if(null != extras) {
            getBundleExtras(extras);
        }

        setStatusBar();

        //屏幕宽度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenDensity = displayMetrics.density;

        //网络状态
        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnect(NetUtils.NetType type) {
                if(type.equals(NetUtils.NetType.MOBILE)) {
                    //Toast.makeText(mContext, "手机网络", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(mContext, "Wifi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNetDisConnect() {

            }
        };
        NetBroadcastReceiver.registerObserver(mNetChangeObserver);

        initViewsAndEvents();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in,R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in,R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in,R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
        NetBroadcastReceiver.unregisterObserver(mNetChangeObserver);
        if(isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
    }

    /*
    根Id
    */
    protected abstract int getRootViewLayoutId();

    protected abstract boolean isBindEventBusHere();

    protected abstract void getBundleExtras(Bundle extras);

    /*
    初始化界面
    */
    protected abstract void initViewsAndEvents();

    /**
     * 加载目标控件
     * */
    protected abstract View getLoadingTargetView();

    /**
     * toggle overridePendingTransition
     *
     * @return
     */
    protected abstract boolean toggleOverridePendingTransition();

    /**
     * get the overridePendingTransition mode
     */
    protected abstract BaseAppCompatActivity.TransitionMode getOverridePendingTransitionMode();

    /**
     * toggle show loading
     *
     * @param toggle
     */
    protected void toggleShowLoading(boolean toggle, String msg) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showLoading(msg);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show empty
     *
     * @param toggle
     */
    protected void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showEmpty(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show error
     *
     * @param toggle
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showError(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show network error
     *
     * @param toggle
     */
    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showNetworkError(onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    protected void gotoActivity(Class clx) {
        startActivity(new Intent(this, clx));
    }

    protected void gotoActivity(Class clx, Bundle bundle) {
        Intent intent = new Intent(this, clx);
        if(intent != null) intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void gotoActivity(Class clx, boolean isFinishCurrent, Bundle bundle) {
        gotoActivity(clx, bundle);
        if (isFinishCurrent) {
            finish();
        }
    }

    protected void gotoActivityForResult(Class clx, int code) {
        Intent intent = new Intent(this, clx);
        startActivityForResult(intent, code);
    }

    protected void gotoActivityForResult(Class clx, int code, Bundle bundle) {
        Intent intent = new Intent(this, clx);
        intent.putExtras(bundle);
        startActivityForResult(intent, code);
    }

    //设置状态栏透明
    protected void setStatusBar() {
        StatusBarUtils.setColor(this, getResources().getColor(R.color.colorPrimary));
    }
}
