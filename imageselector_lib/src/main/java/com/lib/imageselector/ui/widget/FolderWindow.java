package com.lib.imageselector.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.lib.imageselector.R;
import com.lib.imageselector.utils.ScreenUtils;
import com.lib.imageselector.beans.MediaFolder;
import com.lib.imageselector.ui.adapter.ImageFolderAdapter;

import java.util.List;

/**
 * @项目名称 JackFruit
 * @类：com.lib.imageselector.ui.widget
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/23 11:01
 * @修改
 * @修改时期 2017/1/23 11:01
 */
public class FolderWindow extends PopupWindow {
    private Context context;
    private View view;
    private RecyclerView recyclerView;
    private ImageFolderAdapter folderAdapter;
    private List<MediaFolder> folderList;
    private OnFolderSelectedListener onFolderSelectedListener;

    public FolderWindow(Context context, List<MediaFolder> folderList) {
        super(context);
        this.context = context;
        this.folderList = folderList;
        initView();
    }

    private void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.layout_folders_dialog, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_folder_list);
        folderAdapter = new ImageFolderAdapter(context, folderList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(folderAdapter);
        //外部点击
        setOutsideTouchable(true);

        //设置高度
        setHeight(ScreenUtils.getScreenHeight(context) - ScreenUtils.dip2px(context, 200));
        //设置宽度
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setAnimationStyle(R.style.popup);
        setFocusable(true);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(view);
        update();

        folderAdapter.setOnItemClickListener(new ImageFolderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                onFolderSelectedListener.onFolderSelect(position);
                dismiss();
            }
        });
    }

    public void setFolderList(List<MediaFolder> folderList) {
        this.folderList = folderList;
        folderAdapter.setFolderList(folderList);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    public void setOnFolderSelectedListener(OnFolderSelectedListener onFolderSelectedListener) {
        this.onFolderSelectedListener = onFolderSelectedListener;
    }

    public interface OnFolderSelectedListener {
        void onFolderSelect(int position);
    }
}
