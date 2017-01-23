package com.lib.imageselector.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lib.imageselector.R;
import com.lib.imageselector.beans.MediaFolder;

import java.util.List;

/**
 * @项目名称 JackFruit
 * @类：com.lib.imageselector.ui.adapter
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/23 9:55
 * @修改
 * @修改时期 2017/1/23 9:55
 */
public class ImageFolderAdapter extends RecyclerView.Adapter<ImageFolderAdapter.FolderHolder> {
    private Context context;
    private List<MediaFolder> folderList;
    //上次选中,默认选择所有图片文件夹
    private int lastPosition = 0;
    private OnItemClickListener onItemClickListener;


    public ImageFolderAdapter(Context context, List<MediaFolder> folderList) {
        this.context = context;
        this.folderList = folderList;
    }

    @Override
    public FolderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_folders, parent, false);
        return new FolderHolder(view);
    }

    @Override
    public void onBindViewHolder(final FolderHolder holder, final int position) {
        MediaFolder mediaFolder = getItem(position);
        Glide.with(context)
                .load(mediaFolder.getList().get(0).getPath())
                .centerCrop()
                .placeholder(R.mipmap.ic_placeholder)
                .into(holder.folderFirstImage);
        holder.setData(mediaFolder);

        holder.checkBox.setVisibility(position == lastPosition ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //和上次选中item相同
                if(!(lastPosition == position)) {
                    if(onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                    }

                    lastPosition = position;
                    notifyDataSetChanged();
                }
            }
        });
    }

    public void setFolderList(List<MediaFolder> folderList) {
        this.folderList = folderList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    private MediaFolder getItem(int position) {
        return folderList.get(position);
    }

    class FolderHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private ImageView folderFirstImage;
        private TextView folderName;
        private TextView imageNumber;
        private CheckBox checkBox;

        public FolderHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            initView(itemView);
        }

        private void initView(View itemView) {
            folderFirstImage = (ImageView) itemView.findViewById(R.id.iv_folder_first_image);
            folderName = (TextView) itemView.findViewById(R.id.tv_imagefold_name);
            imageNumber = (TextView) itemView.findViewById(R.id.image_number);
            checkBox = (CheckBox) itemView.findViewById(R.id.cb_fold_checked);
        }

        public void setData(MediaFolder folder) {
            folderName.setText(folder.getName());
            imageNumber.setText(folder.getList().size() + "张");
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
