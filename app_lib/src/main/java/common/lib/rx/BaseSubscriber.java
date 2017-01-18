package common.lib.rx;

import rx.Subscriber;

public abstract class BaseSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onStart() {

    }
}
