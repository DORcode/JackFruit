package com.jackfruit.mall.mvp;

import android.view.View;

/**
 * @项目名称 JackFruit
 * @类：com.jackfruit.mall.mvp
 * @描述 describe
 * @创建者 kh
 * @日期 2016/12/27 15:23
 * @修改
 * @修改时期 2016/12/27 15:23
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void showLoading();

    /*void showNetWork(View.OnClickListener clickListener);

    void showEmpty();

    void restore();*/
}
