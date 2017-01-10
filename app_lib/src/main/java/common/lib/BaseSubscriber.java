package common.lib;

import rx.Subscriber;

public abstract class BaseSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onStart() {
    }
}
