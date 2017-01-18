package com.jackfruit.mall.ui.activity;

import android.os.Bundle;

import common.lib.base.BaseAppCompatActivity;
import common.lib.mvp.BaseModel;
import common.lib.mvp.BasePresenter;
import common.lib.mvp.BaseView;
import common.lib.utils.TUtil;

/**
 *
 *@author kh
 *create at  
 */
public abstract class BaseMVPActivity<P extends BasePresenter, M extends BaseModel> extends BaseAppCompatActivity implements BaseView {
    protected P mPresenter;
    protected M mModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取父类Presenter实例
        mPresenter = TUtil.getT(this, 0);
        //获取父类Model实例
        mModel = TUtil.getT(this, 1);
        if(this instanceof BaseView) {
            if(mPresenter != null) {
                //在mPresenter中绑定view和model
                mPresenter.attacth(this, mModel);
            } else {
                throw new IllegalArgumentException("GenericSuperclass Can not null");
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null) {
            mPresenter.detach();
            mPresenter.onDestory();
        }
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
