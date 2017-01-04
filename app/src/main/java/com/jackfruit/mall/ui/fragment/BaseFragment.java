package com.jackfruit.mall.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jackfruit.mall.R;


public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void goToActivity(Class clx) {
        startActivity(new Intent(getActivity(), clx));
    }
}
