package com.lib.imageselector.ui.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lib.imageselector.R;
import com.lib.imageselector.beans.MediaInfo;

import java.io.File;
import java.nio.charset.Charset;
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
    private int selectedMaxNum;

    private OnImageSelectorItemListener onImageSelectorItemListener;

    public ImageListAdapter(Context context, boolean isShowCamera, int maxNum) {
        this.context = context;
        this.list = new ArrayList<MediaInfo>();
        this.isShowCamera = isShowCamera;
        selectedImages = new ArrayList<MediaInfo>();
        selectedMaxNum = maxNum;
    }

    public void setList(List<MediaInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setSelectedImages(List<MediaInfo> selectedImages) {
        this.selectedImages = selectedImages;
        notifyDataSetChanged();
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(getItemViewType(position) == ITEM_TYPE_CAMERA) {
            CameraHolder cameraHolder = (CameraHolder) holder;
            cameraHolder.camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onImageSelectorItemListener.onTakePhoto();
                }
            });
        } else {
            final ImageHolder imageHolder = (ImageHolder) holder;
            final int pos = isShowCamera ? position -1 : position;
            final MediaInfo mediaInfo = list.get(pos);

            selectImage(imageHolder, selectedImages.contains(mediaInfo));

            Glide.with(context)
                    .load(new File(mediaInfo.getPath()))
                    .placeholder(R.mipmap.ic_placeholder)
                    .centerCrop()
                    .into(imageHolder.image);

            imageHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(selectedImages.size() == 9) {
                        if(isChecked) {
                            if(!selectedImages.contains(mediaInfo)) {
                                imageHolder.checkbox.setChecked(false);
                                Toast.makeText(context, "最多可以选择9张", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }

                    //mediaInfo.setChecked(isChecked);

                    if(isChecked) {
                        if(!selectedImages.contains(mediaInfo)) {
                            selectedImages.add(mediaInfo);
                        }
                    } else {
                        selectedImages.remove(mediaInfo);
                    }
                    selectImage(imageHolder, isChecked);
                    onImageSelectorItemListener.onImageSelect(selectedImages);

                }
            });

            imageHolder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "" + pos, Toast.LENGTH_LONG).show();
                    Log.d("", "onClick: " + mediaInfo.getPath());
                    onImageSelectorItemListener.onImageClick(pos);
                }
            });
            imageHolder.checkbox.setChecked(selectedImages.contains(mediaInfo));
        }
    }

    private void selectImage(ImageHolder imageHolder, boolean isChecked) {
        if(isChecked) {
            imageHolder.image.setColorFilter(ContextCompat.getColor(context, R.color.image_overlay2), PorterDuff.Mode.SRC_ATOP);
        } else {
            imageHolder.image.setColorFilter(ContextCompat.getColor(context, R.color.image_overlay), PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public int getItemCount() {
        return isShowCamera ? list.size() + 1 : list.size();
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

    //图片
    class ImageHolder extends RecyclerView.ViewHolder {
        ImageView image;
        CheckBox checkbox;

        public ImageHolder(View itemView) {
            super(itemView);

            init(itemView);
        }

        private void init(View itemView) {
            image = (ImageView) itemView.findViewById(R.id.image);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }

    //照相
    class CameraHolder extends RecyclerView.ViewHolder {
        ImageView camera;

        public CameraHolder(View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View itemView) {
            camera = (ImageView) itemView.findViewById(R.id.camera);
        }
    }

    public void setOnImageSelectorItemListener(OnImageSelectorItemListener onImageSelectorItemListener) {
        this.onImageSelectorItemListener = onImageSelectorItemListener;
    }

    public interface OnImageSelectorItemListener {
        void onTakePhoto();
        void onImageClick(int position);
        void onImageSelect(List<MediaInfo> medias);
    }
}
