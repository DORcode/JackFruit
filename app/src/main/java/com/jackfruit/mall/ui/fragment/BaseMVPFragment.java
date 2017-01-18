package com.jackfruit.mall.ui.fragment;

import android.os.Bundle;

import common.lib.base.BaseLazyFragment;
import common.lib.mvp.BaseModel;
import common.lib.mvp.BasePresenter;
import common.lib.mvp.BaseView;
import common.lib.utils.TUtil;


public abstract class BaseMVPFragment<P extends BasePresenter, M extends BaseModel> extends BaseLazyFragment implements BaseView {
    protected P mPresenter;
    protected M mModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
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
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showExcepiton(String msg) {

    }

    @Override
    public void showNetError() {

    }
}
