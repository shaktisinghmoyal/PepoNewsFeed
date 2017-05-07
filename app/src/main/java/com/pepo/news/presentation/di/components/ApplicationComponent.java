
package com.pepo.news.presentation.di.components;

import android.content.Context;

import com.pepo.news.domain.executor.PostExecutionThread;
import com.pepo.news.domain.executor.ThreadExecutor;
import com.pepo.news.domain.repository.INewsFeedRepository;
import com.pepo.news.domain.repository.INewsFeedRepository;
import com.pepo.news.presentation.appviewpresenter.base.view.activity.BaseActivity;
import com.pepo.news.presentation.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    INewsFeedRepository newsFeedRepository();


}
