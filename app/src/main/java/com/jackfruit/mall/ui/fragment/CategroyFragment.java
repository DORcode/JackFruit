package com.jackfruit.mall.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jackfruit.mall.R;

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
        return inflater.inflate(R.layout.fragment_categroy, container, false);
    }

}
