package com.lib.imageselector.ui.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @项目名称 JackFruit
 * @类：com.lib.imageselector.ui.widget
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/22 14:59
 * @修改
 * @修改时期 2017/1/22 14:59
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;
        if(includeEdge) {
            outRect.left = spacing - column * spacing / spanCount;
            outRect.right = (column + 1) * spacing / spanCount;
            if(position < spanCount) {
                outRect.top = spacing;
            }
            outRect.bottom = spacing;
        } else {
            outRect.left = column * spacing / spanCount;//0列无分割线，1列
            outRect.right = spacing - (column + 1) * spacing / spanCount;

            if(position < spanCount) {//第一行设置分割线，之后行分割线使用bottom
                outRect.top = spacing;
            }
            outRect.bottom = spacing;
        }
    }
}
