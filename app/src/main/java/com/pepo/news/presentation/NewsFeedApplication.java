package com.pepo.news.presentation;

import android.app.Application;
import android.content.Context;

import com.pepo.news.presentation.di.components.ApplicationComponent;
import com.pepo.news.presentation.di.components.DaggerApplicationComponent;
import com.pepo.news.presentation.di.modules.ApplicationModule;

public class NewsFeedApplication extends Application {

    private static ApplicationComponent applicationComponent;
    private final String Tag = "NewsFeedApplication";

    public static Context getAppContext() {
        return applicationComponent.context();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }
    private void initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }


}
