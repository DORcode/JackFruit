package com.jackfruit.mall.mvp;

import common.lib.mvp.BaseModel;
import common.lib.mvp.BasePresenter;
import common.lib.mvp.BaseView;

/**
 * @项目名称 JackFruit
 * @类：com.jackfruit.mall.mvp
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/18 14:56
 * @修改
 * @修改时期 2017/1/18 14:56
 */

public interface HomeContract {
    public interface View extends BaseView {

    }

    public interface Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<View, Model> {

    }
}
