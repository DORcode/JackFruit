package com.jackfruit.mall.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.jackfruit.mall.R;
import com.jackfruit.mall.databinding.ActivityShowImagesBinding;
import com.jackfruit.mall.http.RetrofitManager;
import com.jackfruit.mall.http.api.ApiService;
import com.lib.imageselector.ui.activity.ImageSelectorActivity;

public class ShowImagesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityShowImagesBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_show_images);

        binding.selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowImagesActivity.this, ImageSelectorActivity.class));


            }
        });

        //RetrofitManager.getInstance().getRetrofit("http://192.168.65.72:3000/").create(ApiService.class).httpPostMulti("upload", null);
    }

}
