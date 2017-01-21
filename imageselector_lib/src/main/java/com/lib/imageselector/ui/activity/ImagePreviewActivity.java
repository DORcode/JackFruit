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
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    private static final String EXTRA_SELECTED_IMAGES = "selected_images";
    private static final String EXTRA_SELECTED_MAXMUM = "max_selected_num";
    private static final String EXTRA_POSITION = "position";
    private Context context;
    private int foldPosition;
    private List<MediaInfo> mediaList;
    private List<MediaInfo> selectedList;
    private int maxSelectNum;
    private int selectedNum;
    private int currentPosition = 0;
    private ViewPager viewpager;
    private TextView completeText;
    private ImagePagerAdapter imageAdapter;
    private Toolbar toolbar;
    private CheckBox imageSelectCB;

    public static void start(Activity activity, int foldPosition, List<MediaInfo> selected, int max, int position) {
        Intent intent = new Intent(activity, ImagePreviewActivity.class);
        intent.putExtra(EXTRA_FOLD_POSITION, foldPosition);
        intent.putExtra(EXTRA_SELECTED_IMAGES, (ArrayList) selected);
        intent.putExtra(EXTRA_SELECTED_MAXMUM, max);
        intent.putExtra(EXTRA_POSITION, position);

        activity.startActivityForResult(intent, 1);
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
        mediaList = MediaLoader.getInstance(this).getMediaListFromFoldList(foldPosition);
        selectedList = (List<MediaInfo>) getIntent().getSerializableExtra(EXTRA_SELECTED_IMAGES);
        maxSelectNum = getIntent().getIntExtra(EXTRA_SELECTED_MAXMUM, 0);
        currentPosition = getIntent().getIntExtra(EXTRA_POSITION, 0);
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

        completeText = (TextView) findViewById(R.id.tv_complete);
        completeText.setText("完成(" + selectedList.size() + "/" + maxSelectNum + ")");

        imageSelectCB = (CheckBox) findViewById(R.id.cb_image_select);
        if(mediaList.get(currentPosition).isChecked()) {
            imageSelectCB.setChecked(true);
        }
        imageSelectCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaInfo m = mediaList.get(currentPosition);
                if(!m.isChecked()) {
                    m.setChecked(true);
                    selectedList.add(m);
                } else {
                    m.setChecked(false);
                    selectedList.remove(m);
                }
                imageAdapter.notifyDataSetChanged();
            }
        });

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
        if(selectedList.contains(m)) {
            imageSelectCB.setChecked(true);
        } else {
            imageSelectCB.setChecked(false);
        }
        /*if(m.isChecked()) {
            imageSelectCB.setChecked(true);
        } else {
            imageSelectCB.setChecked(false);
        }*/
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPhotoTap(View view, float x, float y) {
        finish();
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

            /*if(selectedList.contains(image)) {
                imageSelectCB.setChecked(true);
            } else {
                imageSelectCB.setChecked(false);
            }*/
            progressBar.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(image.getPath())
                    .crossFade(700)
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
}
