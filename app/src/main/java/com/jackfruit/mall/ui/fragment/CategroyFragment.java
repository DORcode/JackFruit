package com.jackfruit.mall.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jackfruit.mall.R;
import com.jackfruit.mall.ui.activity.LocationActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategroyFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;


    public CategroyFragment() {
    }

    public static CategroyFragment newInstance(String param1) {
        CategroyFragment fragment = new CategroyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_categroy, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.tv_location)
    public void onClick() {
        startActivity(new Intent(getActivity(), LocationActivity.class));
    }
}
