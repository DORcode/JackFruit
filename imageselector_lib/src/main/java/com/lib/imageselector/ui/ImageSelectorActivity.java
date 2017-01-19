package com.lib.imageselector.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.lib.imageselector.R;

public class ImageSelectorActivity extends AppCompatActivity {
    private static final String TAG = "ImageSelectorActivity";
    Toolbar toolbar;
    RecyclerView imageRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selector);

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageRecyclerView = (RecyclerView) findViewById(R.id.image_recyclerview);
    }
}
