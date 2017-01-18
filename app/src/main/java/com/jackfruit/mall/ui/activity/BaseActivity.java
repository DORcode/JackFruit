package com.jackfruit.mall.ui.activity;

import android.os.Bundle;

import common.lib.base.BaseAppCompatActivity;
import common.lib.mvp.BaseModel;
import common.lib.mvp.BasePresenter;
import common.lib.mvp.BaseView;

/**
 *
 *@author kh
 *create at  
 */
public abstract class BaseActivity<P extends BasePresenter, M extends BaseModel> extends BaseAppCompatActivity implements BaseView {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showLoading(String msg) {
        toggleShowLoading(true, null);
    }

    @Override
    public void hideLoading() {
        toggleShowLoading(false,null);
    }

    @Override
    public void showError(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showExcepiton(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, null);
    }
}
