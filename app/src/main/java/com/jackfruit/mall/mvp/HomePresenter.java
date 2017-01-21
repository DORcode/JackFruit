package com.jackfruit.mall.mvp;

import com.jackfruit.mall.bean.DemoBean;
import com.jackfruit.mall.bean.DemoResult;
import com.jackfruit.mall.http.RetrofitManager;

import common.lib.rx.BaseSubscriber;
import common.lib.utils.ExceptionHandle;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @项目名称 JackFruit
 * @类：com.jackfruit.mall.mvp
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/18 14:43
 * @修改
 * @修改时期 2017/1/18 14:43
 */
public class HomePresenter extends HomeContract.Presenter {
    public void aaa(int id) {
        addSubscription(RetrofitManager.getInstance().getApiService().getDemoResult()
                .doOnNext(new Action1<DemoResult<DemoBean>>() {
                    @Override
                    public void call(DemoResult<DemoBean> demoBeanDemoResult) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<DemoResult<DemoBean>>() {
            @Override
            public void onError(Throwable e) {
                mView.showError(ExceptionHandle.handleException(e).message);
            }

            @Override
            public void onNext(DemoResult<DemoBean> demoBeanDemoResult) {

            }
        }));
    }
}
