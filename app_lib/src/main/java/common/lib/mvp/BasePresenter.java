package common.lib.mvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @项目名称 JackFruit
 * @类：common.lib.mvp
 * @描述 describe
 * @创建者 kh
 * @日期 2017/1/18 14:34
 * @修改
 * @修改时期 2017/1/18 14:34
 */
public abstract class BasePresenter<V extends BaseView, M extends BaseModel> {
    private CompositeSubscription subscription = new CompositeSubscription();
    protected V mView;
    protected M mModel;

    public void attacth(V v, M m) {
        mView = v;
        mModel = m;
    }

    public void detach() {
        mView = null;
        mModel = null;
    }

    public void addSubscription(Subscription sub) {
        subscription.add(sub);
    }

    public void removeSubscription(Subscription sub) {
        subscription.remove(sub);
    }

    public void onDestory() {
        subscription.unsubscribe();
    }
}
