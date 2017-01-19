package com.lib.imageselector.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lib.imageselector.R;
import com.lib.imageselector.beans.MediaInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名称 JackFruit
 * @类：com.lib.imageselector.ui.adapter
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/19 17:51
 * @修改
 * @修改时期 2017/1/19 17:51
 */
public class ImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MediaInfo> list;
    private boolean isShowCamera;
    private int ITEM_TYPE_CAMERA = 0;
    private int ITEM_TYPE_IMAGE = 1;
    private List<MediaInfo> selectedImages;

    public ImageListAdapter(Context context, List<MediaInfo> list, boolean isShowCamera) {
        this.context = context;
        this.list = list;
        this.isShowCamera = isShowCamera;
        selectedImages = new ArrayList<MediaInfo>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE_CAMERA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camera_layout, parent, false);
            return new CameraHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images_layout, parent, false);
            return new ImageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return isShowCamera ? ITEM_TYPE_CAMERA : ITEM_TYPE_IMAGE;
        } else {
            return ITEM_TYPE_IMAGE;
        }
    }

    public boolean isShowCamera() {
        return isShowCamera;
    }

    public void setShowCamera(boolean showCamera) {
        isShowCamera = showCamera;
    }

    class ImageHolder extends RecyclerView.ViewHolder {

        public ImageHolder(View itemView) {
            super(itemView);
        }
    }

    class CameraHolder extends RecyclerView.ViewHolder {

        public CameraHolder(View itemView) {
            super(itemView);
        }
    }
}
