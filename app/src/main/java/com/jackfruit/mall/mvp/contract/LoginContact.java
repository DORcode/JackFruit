package com.jackfruit.mall.mvp.contract;

import com.jackfruit.mall.mvp.BasePresenter;
import com.jackfruit.mall.mvp.BaseView;

/**
 * @项目名称 JackFruit
 * @类：com.jackfruit.mall.mvp.contract
 * @描述 describe
 * @创建者 kh
 * @日期 2016/12/27 15:26
 * @修改
 * @修改时期 2016/12/27 15:26
 */

public interface LoginContact {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
