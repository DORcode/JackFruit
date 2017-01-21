package com.lib.imageselector.ui.activity;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import com.lib.imageselector.R;
import com.lib.imageselector.beans.MediaFolder;
import com.lib.imageselector.beans.MediaInfo;
import com.lib.imageselector.ui.adapter.ImageListAdapter;
import com.lib.imageselector.utils.MediaLoader;

import java.util.ArrayList;
import java.util.List;

public class ImageSelectorActivity extends AppCompatActivity implements ImageListAdapter.OnImageSelectorItemListener {
    private static final String TAG = "ImageSelectorActivity";
    //是否显示拍照按钮
    private boolean isShowCamera = true;
    //列表中是否显示视频
    private boolean isShowVideo = true;
    //图片或视频最大选择
    private int selectedMaxNum = 9;
    private Context context;
    Toolbar toolbar;
    RecyclerView imageRecyclerView;
    private ProgressBar progressBar;
    private List<MediaFolder> folderList = new ArrayList<MediaFolder>();
    private List<MediaInfo> showMediaList = new ArrayList<MediaInfo>();
    private List<MediaInfo> selectedImages = new ArrayList<MediaInfo>();
    private ImageListAdapter imageListAdapter;
    //当前所选图片文件夹位置
    private int currentFold = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selector);
        context = this;
        initView();
        getAllImageFolderList();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageRecyclerView = (RecyclerView) findViewById(R.id.image_recyclerview);
        imageListAdapter = new ImageListAdapter(context, true, selectedMaxNum);
        imageListAdapter.setOnImageSelectorItemListener(this);
        imageRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        imageRecyclerView.hasFixedSize();
        imageRecyclerView.setAdapter(imageListAdapter);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
    }

    /**
     * 获取本地外部存在所有的图片所在文件夹及文件夹中的图片路径
     */
    private void getAllImageFolderList() {
        progressBar.setVisibility(View.VISIBLE);
        Observable<List<MediaFolder>> observable = Observable.create(new Observable.OnSubscribe<List<MediaFolder>>() {
            @Override
            public void call(Subscriber<? super List<MediaFolder>> subscriber) {
                List<MediaFolder> folders = MediaLoader.getInstance(context).getImageFolders();
                subscriber.onNext(folders);
            }
        });
        Subscriber<List<MediaFolder>> subscriber = new Subscriber<List<MediaFolder>>() {
            @Override
            public void onCompleted() {
                showProgressbar(false);
            }

            @Override
            public void onError(Throwable e) {
                showProgressbar(false);
            }

            @Override
            public void onNext(List<MediaFolder> mediaFolders) {
                folderList = mediaFolders;
                showMediaList = folderList.get(currentFold).getList();
                imageListAdapter.setList(showMediaList);
                showProgressbar(false);
            }
        };
        Subscription subscription = observable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void showProgressbar(boolean flag) {
        if(flag) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onTakePhoto() {

    }

    @Override
    public void onImageClick(int position) {
        ImagePreviewActivity.start(this, currentFold, selectedImages, selectedMaxNum, position);
    }

    @Override
    public void onImageSelect(List<MediaInfo> medias) {
        selectedImages = medias;
    }
}
