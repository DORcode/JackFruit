package com.jackfruit.mall.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.jackfruit.mall.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import common.lib.MessageEvent;
import common.lib.base.BaseAppCompatActivity;

public class PlaylistActivity extends BaseActivity {
    //内容区
    /*@BindView(R.id.content_playlist)
    RelativeLayout contentPlaylist;*/
    private Handler handler;
    @Override
    protected int getRootViewLayoutId() {
        return R.layout.activity_playlist;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewsAndEvents() {
        //toggleShowLoading(true, "正在加载...");
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //toggleShowLoading(false, null);
            }
        }, 5000);
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onNetworkConnect() {

    }

    @Override
    protected void onNetworkDisconnect() {

    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.LEFT;
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

    }

}
