package com.lib.imageselector.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import com.lib.imageselector.R;
import com.lib.imageselector.beans.MediaFolder;
import com.lib.imageselector.beans.MediaInfo;
import com.lib.imageselector.ui.adapter.ImageListAdapter;
import com.lib.imageselector.ui.widget.FolderWindow;
import com.lib.imageselector.ui.widget.GridSpacingItemDecoration;
import com.lib.imageselector.utils.MediaLoader;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class ImageSelectorActivity extends AppCompatActivity implements ImageListAdapter.OnImageSelectorItemListener {
    private static final String TAG = "ImageSelectorActivity";
    private static final String EXTRA_SELECTED_IMAGES = "selected_images";
    private static final String EXTRA_SELECTED_MAXMUM = "max_selected_num";
    private static final String EXTRA_CAMERA_ISSHOW = "camera_isshow";
    private static final String EXTRA_SHOW_TYPE = "show_media_type";
    private static final int REQUEST_CODE_CAMERA = 0x101;
    //列表显示类型，0代表图片，1代表视频和图片
    private int mediaShowType = 0;

    //是否显示拍照按钮
    private boolean isShowCamera = true;
    //列表中是否显示视频
    private boolean isShowVideo = true;
    //图片或视频最大选择
    private int selectedMaxNum = 9;
    private Context context;
    Toolbar toolbar;
    private ActionBar actionBar;
    private RecyclerView imageRecyclerView;
    private TextView mCompleteSelectedTV;
    private FrameLayout mFoldSelectFL;
    private ProgressBar progressBar;
    private TextView selectedFoldeName;
    private List<MediaFolder> folderList = new ArrayList<MediaFolder>();
    private List<MediaInfo> showMediaList = new ArrayList<MediaInfo>();
    private List<MediaInfo> selectedImages = new ArrayList<MediaInfo>();
    private ImageListAdapter imageListAdapter;
    //当前所选图片文件夹位置
    private int currentFold = 0;
    //图片文件夹列表
    private RecyclerView folderListRV;
    //文件夹列表点击按钮
    private LinearLayout folderSelectLayout;
    //图片文件夹列表对话框
    private BottomSheetDialog bottomSheetDialog;
    //预览按钮
    private TextView previewText;
    //文件夹选择弹窗
    private FolderWindow folderWindow;

    public static void start(Activity activity, List<MediaInfo> selected, int max, boolean isShowCamera, int showMediaType) {
        Intent intent = new Intent(activity, ImageSelectorActivity.class);
        intent.putExtra(EXTRA_SELECTED_IMAGES, (ArrayList) selected);
        intent.putExtra(EXTRA_SELECTED_MAXMUM, max);
        intent.putExtra(EXTRA_CAMERA_ISSHOW, isShowCamera);
        intent.putExtra(EXTRA_SHOW_TYPE, showMediaType);

        activity.startActivityForResult(intent, 1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selector);
        context = this;
        initData();
        initView();
        initListener();
        getAllImageFolderList();
    }

    private void initData() {
        //selectedImages = (List<MediaInfo>) getIntent().getSerializableExtra(EXTRA_SELECTED_IMAGES);
        selectedMaxNum = getIntent().getIntExtra(EXTRA_SELECTED_MAXMUM, 9);
        isShowCamera = getIntent().getBooleanExtra(EXTRA_CAMERA_ISSHOW, true);
        mediaShowType = getIntent().getIntExtra(EXTRA_SHOW_TYPE, 1);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_actionbar_back);
        toolbar.setTitle("图片选择");
        //setSupportActionBar(toolbar);
        mCompleteSelectedTV = (TextView) findViewById(R.id.image_selected_number);
        //设置完成按钮显示选择图片数量
        setCompleteText();
        imageRecyclerView = (RecyclerView) findViewById(R.id.image_recyclerview);
        imageListAdapter = new ImageListAdapter(context, true, selectedMaxNum);
        imageListAdapter.setOnImageSelectorItemListener(this);
        imageRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        imageRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 4, false));
        imageRecyclerView.hasFixedSize();
        imageRecyclerView.setAdapter(imageListAdapter);
        mFoldSelectFL = (FrameLayout) findViewById(R.id.image_bottom_line);
        selectedFoldeName = (TextView) findViewById(R.id.fold_selected_name);
        folderSelectLayout = (LinearLayout) findViewById(R.id.fold_select_layout);
        previewText = (TextView) findViewById(R.id.preview_selected);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        //bottomSheetDialog = new BottomSheetDialog(this);
        //bottomSheetDialog.setContentView(folderListRV);
        folderWindow = new FolderWindow(this, folderList);

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initListener() {
        //返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //完成
        mCompleteSelectedTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //文件夹对话框按钮
        folderSelectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据未加载完成时不响应点击
                if(folderList.size() == 0) {
                    return;
                }

                if(folderWindow.isShowing()) {
                    folderWindow.dismiss();
                } else {

                    int[] location = new int[2];
                    mFoldSelectFL.getLocationOnScreen(location);
                    Log.d(TAG, "onClick: " + mFoldSelectFL.getHeight() + "_" + location[1]);
                    folderWindow.showAtLocation(mFoldSelectFL, Gravity.BOTTOM, 0, 0);
                }
            }
        });

        previewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedImages.size() == 0) {
                    return;
                }
                startPreview(0, ImagePreviewActivity.PREVIEW_SELECTED_IMAGE);
            }
        });

        //弹窗选中文件夹回调
        folderWindow.setOnFolderSelectedListener(new FolderWindow.OnFolderSelectedListener() {
            @Override
            public void onFolderSelect(int position) {
                currentFold = position;
                showMediaList = folderList.get(position).getList();
                //当不是选择所有图片时
                if(position != 0) {
                    //不显示拍照按钮
                    imageListAdapter.setShowCamera(false);
                } else {
                    imageListAdapter.setShowCamera(true);
                }
                selectedFoldeName.setText(folderList.get(position).getName());
                imageListAdapter.setList(showMediaList);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            /*Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if(cursor != null) {
                cursor.moveToFirst();
                String path = cursor.getString(cursor.getColumnIndex("_data"));
                File file = new File(path);
                MediaInfo info= new MediaInfo(path, file.getName(), MediaInfo.MediaType.IMAGE, new Date(file.lastModified()).toString());
                Log.d(TAG, "onActivityResult: " + data.getData().getPath());
                cursor.close();
                selectedImages.add(0, info);
                imageListAdapter.setSelectedImages(selectedImages);
            } else {

            }*/
            MediaInfo newMedia = MediaLoader.getInstance(context).findImagePathByUri(uri);
            selectedImages.add(0, newMedia);
            imageListAdapter.setSelectedImages(selectedImages);
            Log.d(TAG, "onActivityResult: " + newMedia.toString());

        } else if(requestCode == ImagePreviewActivity.PREVIEW_SELECTED_IMAGE
                || requestCode == ImagePreviewActivity.PREVIEW_FOLD_IMAGE){
            if(data == null) {
                return;
            }
            selectedImages = (List<MediaInfo>) data.getSerializableExtra(ImagePreviewActivity.EXTRA_SELECTED_IMAGES);
            if(resultCode == RESULT_OK) {
                selectFinish(true);
                finish();
            } else {
                imageListAdapter.setSelectedImages(selectedImages);
            }

        }
        setCompleteText();
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

    private void setCompleteText() {
        if(selectedImages.size() == 0) {
            mCompleteSelectedTV.setText("完成");
        } else {
            mCompleteSelectedTV.setText("完成(" + selectedImages.size() + "/" + selectedMaxNum + ")");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 获取本地外部存在所有的图片所在文件夹及文件夹中的图片路径
     */
    private void getAllImageFolderList() {
        progressBar.setVisibility(View.VISIBLE);
        Observable<List<MediaFolder>> observable = Observable.create(new Observable.OnSubscribe<List<MediaFolder>>() {
            @Override
            public void call(Subscriber<? super List<MediaFolder>> subscriber) {
                List<MediaFolder> folders;
                if(mediaShowType == 0) {
                    folders = MediaLoader.getInstance(context).getImageFolders();
                } else {
                    folders = MediaLoader.getInstance(context).getImagesAndVideo();
                }

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

                //设置弹窗列表数据
                folderWindow.setFolderList(folderList);
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
            progressBar.setVisibility(GONE);
        }
    }

    @Override
    public void onTakePhoto() {
        takePhoto();
    }

    @Override
    public void onImageClick(int position) {
        Log.d(TAG, "onImageClick: " + showMediaList.get(position).getPath());
        startPreview(position, ImagePreviewActivity.PREVIEW_FOLD_IMAGE);
    }

    @Override
    public void onImageSelect(List<MediaInfo> medias) {
        selectedImages = medias;
        setCompleteText();
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,REQUEST_CODE_CAMERA);
    }

    /**
     * 进入预览界面
     * @param position 默认显示
     * @param type 点击文件夹或者点击预览按钮
     */
    private void startPreview(int position, int type) {
        ImagePreviewActivity.start(this, currentFold, selectedImages, selectedMaxNum, position, type);
    }
}
