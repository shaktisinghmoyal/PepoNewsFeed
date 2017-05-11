package com.pepo.news.presentation.di.components;

import com.pepo.news.presentation.mvp.home.view.activity.NewsFeedActivity;
import com.pepo.news.presentation.di.PerActivity;
import com.pepo.news.presentation.di.modules.ActivityModule;
import com.pepo.news.presentation.di.modules.NewsFeedModule;

import dagger.Component;

/**
 * A component whose lifetime is the life of the NewsFeedActivity  .
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, NewsFeedModule.class})
public interface NewsFeedComponent extends ActivityComponent {
    void inject(NewsFeedActivity mainActivity);


}
