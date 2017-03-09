package com.lib.imageselector.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.lib.imageselector.R;
import com.lib.imageselector.utils.ScreenUtils;
import com.lib.imageselector.beans.MediaFolder;
import com.lib.imageselector.ui.adapter.ImageFolderAdapter;

import java.util.List;

import butterknife.OnClick;

/**
 * @项目名称 JackFruit
 * @类：com.lib.imageselector.ui.widget
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/23 11:01
 * @修改
 * @修改时期 2017/1/23 11:01
 */
public class FolderWindow extends PopupWindow implements View.OnClickListener{
    private Context context;
    private View view;
    private RecyclerView recyclerView;
    private View marginView;
    private ImageFolderAdapter folderAdapter;
    private List<MediaFolder> folderList;
    private OnFolderSelectedListener onFolderSelectedListener;
    private int marginPx;

    public FolderWindow(Context context, List<MediaFolder> folderList) {
        super(context);
        this.context = context;
        this.folderList = folderList;
        initView();
    }

    private void initView() {
        view = LayoutInflater.from(context).inflate(R.layout.layout_folders_dialog, null);
        view.setOnClickListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_folder_list);
        marginView = view.findViewById(R.id.margin);
        folderAdapter = new ImageFolderAdapter(context, folderList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(folderAdapter);
        //外部点击
        setOutsideTouchable(true);

        //设置高度
        setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置宽度
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //setAnimationStyle(R.style.popup);
        setFocusable(true);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(view);
        update();

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int maxHeight = view.getHeight() * 5 / 8;
                int realHeight = recyclerView.getHeight();
                ViewGroup.LayoutParams listParams = recyclerView.getLayoutParams();
                listParams.height = realHeight > maxHeight ? maxHeight : realHeight;
                recyclerView.setLayoutParams(listParams);
                LinearLayout.LayoutParams marginParams = (LinearLayout.LayoutParams) marginView.getLayoutParams();
                marginParams.height = marginPx;
                marginView.setLayoutParams(marginParams);
                enterAnimator();
            }
        });

        folderAdapter.setOnItemClickListener(new ImageFolderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                onFolderSelectedListener.onFolderSelect(position);
                dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        exitAnimator();
    }

    private void enterAnimator() {
        ObjectAnimator listTranslate = ObjectAnimator.ofInt(recyclerView, "translationY", recyclerView.getHeight(), 0);
        ObjectAnimator listAlpha = ObjectAnimator.ofFloat(recyclerView, "alpha", 0, 1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        //animatorSet.play(listAlpha).with(listTranslate);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(listAlpha, listTranslate);
        animatorSet.start();
    }

    private void exitAnimator() {
        ObjectAnimator listTranslate = ObjectAnimator.ofInt(recyclerView, "translationY", 0, recyclerView.getHeight());
        ObjectAnimator listAlpha = ObjectAnimator.ofFloat(recyclerView, "alpha", 1, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                FolderWindow.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.play(listAlpha).with(listTranslate);
        animatorSet.start();
    }

    public void setFolderList(List<MediaFolder> folderList) {
        this.folderList = folderList;
        folderAdapter.setFolderList(folderList);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        enterAnimator();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    public void setOnFolderSelectedListener(OnFolderSelectedListener onFolderSelectedListener) {
        this.onFolderSelectedListener = onFolderSelectedListener;
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    public interface OnFolderSelectedListener {
        void onFolderSelect(int position);
    }

    public void setMarginPx(int marginPx) {
        this.marginPx = marginPx;
    }
}
