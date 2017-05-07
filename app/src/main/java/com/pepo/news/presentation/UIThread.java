
package com.pepo.news.presentation;


import com.pepo.news.domain.executor.PostExecutionThread;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * MainThread (UI Thread) implementation based on a {@link Scheduler}
 * which will execute actions on the Android UI thread
 */
@Singleton
public class UIThread implements PostExecutionThread {
    private final String Tag = "UIThread";
    @Inject
    public UIThread() {
    }

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
