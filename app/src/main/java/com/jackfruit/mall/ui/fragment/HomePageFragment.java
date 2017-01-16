package com.jackfruit.mall.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.jackfruit.mall.R;
import com.jackfruit.mall.ui.activity.DemoActivity;
import com.jackfruit.mall.ui.activity.PlaylistActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.OnClick;
import common.lib.MessageEvent;
import common.lib.base.BaseLazyFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends BaseLazyFragment {

    private static final String ARG_PARAM1 = "param1";

    public HomePageFragment() {
    }

    public static HomePageFragment newInstance(String param1) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void onNetworkConnect() {

    }

    @Override
    protected void onNetworkDisconnect() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onMessageEvent(MessageEvent event) {

    }

    @OnClick({R.id.tv_home, R.id.tv_homepage})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_home:
                startActivity(new Intent(getActivity(), DemoActivity.class));
                break;
            case R.id.tv_homepage:
                gotoActivity(PlaylistActivity.class);
                break;
        }

    }
}
