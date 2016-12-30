package com.jackfruit.mall.mvp;

/**
 * @项目名称 JackFruit
 * @类：com.jackfruit.mall.mvp
 * @描述 describe
 * @创建者 kh
 * @日期 2016/12/27 15:24
 * @修改
 * @修改时期 2016/12/27 15:24
 */
public interface BasePresenter {
    void start();
    void unsubscribe();//RxJava 取消订阅
}
