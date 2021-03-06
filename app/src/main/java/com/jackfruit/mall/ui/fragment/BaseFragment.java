package com.jackfruit.mall.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jackfruit.mall.R;

import common.lib.base.BaseLazyFragment;
import common.lib.mvp.BaseModel;
import common.lib.mvp.BasePresenter;
import common.lib.mvp.BaseView;


public abstract class BaseFragment extends BaseLazyFragment implements BaseView {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
