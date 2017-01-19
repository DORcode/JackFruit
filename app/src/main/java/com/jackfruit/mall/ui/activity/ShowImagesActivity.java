package com.jackfruit.mall.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jackfruit.mall.R;
import com.lib.imageselector.ImageSelectorActivity;

import butterknife.OnClick;
import common.lib.MessageEvent;
import common.lib.base.BaseAppCompatActivity;

public class ShowImagesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getRootViewLayoutId() {
        return R.layout.activity_show_images;
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
        return null;
    }

    @Override
    public void onMessageEvent(MessageEvent event) {

    }

    @OnClick({R.id.select_image_button})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_image_button:
                gotoActivity(ImageSelectorActivity.class);
                break;
        }
    }
}
