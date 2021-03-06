package com.lib.imageselector.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lib.imageselector.R;
import com.lib.imageselector.beans.MediaInfo;
import com.lib.imageselector.ui.widget.PreviewViewPager;
import com.lib.imageselector.utils.MediaLoader;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImagePreviewActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, PhotoViewAttacher.OnPhotoTapListener {
    private static final String TAG = "ImagePreviewActivity";
    private static final String EXTRA_FOLD_POSITION= "fold_position";
    public static final String EXTRA_SELECTED_IMAGES = "selected_images";
    private static final String EXTRA_SELECTED_MAXMUM = "max_selected_num";
    private static final String EXTRA_POSITION = "position";
    private static final String EXTRA_PREVIEW_TYPE = "preview_type";
    //预览选中图片
    public static final int PREVIEW_SELECTED_IMAGE = 0x601;
    //预览文件夹中的图片
    public static final int PREVIEW_FOLD_IMAGE = 0x602;
    private Context context;
    private int foldPosition;
    //图片列表
    private List<MediaInfo> mediaList = new ArrayList<MediaInfo>();
    //选中图片列表
    private List<MediaInfo> selectedImages;
    //预览图片类型
    private int previewType;
    private int maxSelectNum;
    private int selectedNum;
    private int currentPosition = 0;
    private ViewPager viewpager;
    private TextView completeText;
    private ImagePagerAdapter imageAdapter;
    private Toolbar toolbar;
    private CheckBox imageSelectCB;
    private FrameLayout mSelectLayout;

    public static void start(Activity activity, int foldPosition, List<MediaInfo> selected, int max, int position,int previewType) {
        Intent intent = new Intent(activity, ImagePreviewActivity.class);
        intent.putExtra(EXTRA_FOLD_POSITION, foldPosition);
        intent.putExtra(EXTRA_SELECTED_IMAGES, (ArrayList) selected);
        intent.putExtra(EXTRA_SELECTED_MAXMUM, max);
        intent.putExtra(EXTRA_POSITION, position);
        intent.putExtra(EXTRA_PREVIEW_TYPE, previewType);

        activity.startActivityForResult(intent, previewType);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        context = this;
        initData();
        initView();
    }

    private void initData() {
        foldPosition = getIntent().getIntExtra(EXTRA_FOLD_POSITION, 0);
        previewType = getIntent().getIntExtra(EXTRA_PREVIEW_TYPE, 0);
        selectedImages = (List<MediaInfo>) getIntent().getSerializableExtra(EXTRA_SELECTED_IMAGES);
        maxSelectNum = getIntent().getIntExtra(EXTRA_SELECTED_MAXMUM, 0);
        currentPosition = getIntent().getIntExtra(EXTRA_POSITION, 0);
        if(previewType == PREVIEW_SELECTED_IMAGE) {
            mediaList.addAll(selectedImages);
        } else {
            mediaList = MediaLoader.getInstance(this).getMediaListFromFoldList(foldPosition);
        }
    }

    private void initView() {
        viewpager = (PreviewViewPager) findViewById(R.id.viewpager);
        imageAdapter = new ImagePagerAdapter();
        viewpager.setAdapter(imageAdapter);
        viewpager.setCurrentItem(currentPosition);
        Log.d(TAG, "initView: " + mediaList.get(currentPosition).getPath());
        viewpager.addOnPageChangeListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle((currentPosition + 1) + "/" + mediaList.size());
        toolbar.setNavigationIcon(R.mipmap.ic_actionbar_back);
        completeText = (TextView) findViewById(R.id.tv_complete);
        setCompleteText();

        imageSelectCB = (CheckBox) findViewById(R.id.cb_image_select);
        mSelectLayout = (FrameLayout) findViewById(R.id.select_layout);

        if(selectedImages.contains(mediaList.get(currentPosition))) {
            imageSelectCB.setChecked(true);
        }

        imageSelectCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MediaInfo m = mediaList.get(currentPosition);

                if(selectedImages.size() == 9) {
                    if(isChecked) {
                        if(!selectedImages.contains(m)) {
                            imageSelectCB.setChecked(false);
                            Toast.makeText(context, "最多可以选择9张", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                //m.setChecked(isChecked);
                if(isChecked) {
                    if(!selectedImages.contains(m)) {
                        selectedImages.add(m);
                    }
                } else {
                    selectedImages.remove(m);
                }
                imageSelectCB.setChecked(isChecked);
                setCompleteText();
            }
        });

        //完成按钮
        completeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFinish(true);
                finish();
            }
        });

        //返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFinish(false);
                finish();
            }
        });
    }

    /**
     * 设置完成按钮
     */
    private void setCompleteText() {
        completeText.setText("完成(" + selectedImages.size() + "/" + maxSelectNum + ")");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageSelected: " + position);
        MediaInfo m = mediaList.get(position);
        currentPosition = position;
        toolbar.setTitle((currentPosition + 1) + "/" + mediaList.size());
        if(selectedImages.contains(m)) {
            imageSelectCB.setChecked(true);
        } else {
            imageSelectCB.setChecked(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPhotoTap(View view, float x, float y) {
        if(toolbar.isShown()) {
            toolbar.setVisibility(View.GONE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
        }
        if(mSelectLayout.isShown()) {
            mSelectLayout.setVisibility(View.GONE);
        } else {
            mSelectLayout.setVisibility(View.VISIBLE);
        }

        //finish();
    }

    @Override
    public void onOutsidePhotoTap() {

    }

    class ImagePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mediaList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.item_bigimage_layout, container, false);
            final MediaInfo image = mediaList.get(position);
            final PhotoView photoView = (PhotoView) view.findViewById(R.id.big_image_view);
            final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

            progressBar.setVisibility(View.VISIBLE);

            Glide.with(context)
                    .load(image.getPath())
                    .crossFade()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            int height = photoView.getHeight();
                            int wHeight = getWindowManager().getDefaultDisplay().getHeight();
                            Log.d(TAG, "onResourceReady: " + height + "_" + wHeight);
                            if(height > wHeight) {
                                photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            } else {
                                photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            }
                            return false;
                        }
                    })
                    .into(photoView);

            container.addView(view, 0);
            photoView.setOnPhotoTapListener(ImagePreviewActivity.this);

            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
            //super.destroyItem(container, position, object);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                selectFinish(false);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void selectFinish(boolean bool) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SELECTED_IMAGES, (ArrayList) selectedImages);

        if(bool) {
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED, intent);
        }
    }
}
