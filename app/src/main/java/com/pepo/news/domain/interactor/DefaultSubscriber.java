
package com.pepo.news.domain.interactor;

/**
 * Default subscriber base class to be used whenever you want default error handling.
 */
public class DefaultSubscriber<T> extends rx.Subscriber<T> {
    private final String Tag = "DefaultSubscriber";
    @Override
    public void onCompleted() {
        // no-op by default.
    }

    @Override
    public void onError(Throwable e) {
        // no-op by default.
    }

    @Override
    public void onNext(T t) {
        // no-op by default.
    }
}
