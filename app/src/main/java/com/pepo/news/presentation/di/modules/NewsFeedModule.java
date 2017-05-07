package com.pepo.news.presentation.di.modules;


import com.pepo.news.domain.interactor.BaseUseCase;
import com.pepo.news.domain.interactor.GetNewsFeed;
import com.pepo.news.presentation.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsFeedModule {

    public NewsFeedModule() {
    }

    @Provides
    @PerActivity
    @Named("getNewsFeed")
    BaseUseCase provideGetNewsFeedUseCase(GetNewsFeed getNewsfeed) {
        return getNewsfeed;
    }


}
