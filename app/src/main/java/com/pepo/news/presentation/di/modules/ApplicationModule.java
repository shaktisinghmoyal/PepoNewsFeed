
package com.pepo.news.presentation.di.modules;

import android.content.Context;

import com.pepo.news.data.executor.JobExecutor;
import com.pepo.news.data.repository.NewsFeedRepository;
import com.pepo.news.domain.executor.PostExecutionThread;
import com.pepo.news.domain.executor.ThreadExecutor;
import com.pepo.news.domain.repository.INewsFeedRepository;
import com.pepo.news.presentation.NewsFeedApplication;
import com.pepo.news.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final NewsFeedApplication application;

    public ApplicationModule(NewsFeedApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    INewsFeedRepository provideNewsFeedRepository(NewsFeedRepository newsFeedRepository) {
        return newsFeedRepository;
    }


}

