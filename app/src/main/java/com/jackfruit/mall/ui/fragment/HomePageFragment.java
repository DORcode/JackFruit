package com.jackfruit.mall.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jackfruit.mall.R;
import com.jackfruit.mall.ui.activity.DemoActivity;
import com.jackfruit.mall.ui.activity.PlaylistActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
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
        return 0;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected void onNetworkConnect() {

    }

    @Override
    protected void onNetworkDisconnect() {

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
