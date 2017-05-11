package com.pepo.news.presentation.di.modules;


import com.pepo.news.domain.interactor.BaseUseCase;
import com.pepo.news.domain.interactor.GetNewsFeed;
import com.pepo.news.domain.interactor.GetNewsFeedFromDB;
import com.pepo.news.domain.interactor.UpdateReadInfoInDB;
import com.pepo.news.presentation.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the NewsFeedActivity lifecycle.
 */

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

    @Provides
    @PerActivity
    @Named("updateReadInfoInDB")
    BaseUseCase provideUpdateReadInfoInDBUseCase(UpdateReadInfoInDB updateReadInfoInDB) {
        return updateReadInfoInDB;
    }

    @Provides
    @PerActivity
    @Named("getNewsFeedFromDB")
    BaseUseCase provideGetNewsFeedFromDBUseCase(GetNewsFeedFromDB getNewsFeedFromDB) {
        return getNewsFeedFromDB;
    }


}
