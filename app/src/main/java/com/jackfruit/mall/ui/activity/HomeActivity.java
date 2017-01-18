package com.jackfruit.mall.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.jackfruit.mall.R;
import com.jackfruit.mall.mvp.HomeContract;
import com.jackfruit.mall.mvp.HomePresenter;
import com.jackfruit.mall.mvp.model.HomeModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import common.lib.MessageEvent;

public class HomeActivity extends BaseMVPActivity<HomePresenter, HomeModel> implements HomeContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.aaa(1);
        mPresenter.detach();
        mPresenter.onDestory();
    }

    @Override
    protected int getRootViewLayoutId() {
        return R.layout.activity_home;
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
        return false;
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
