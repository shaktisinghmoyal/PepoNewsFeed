package com.pepo.news.presentation.mvp.base.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pepo.news.presentation.NewsFeedApplication;
import com.pepo.news.presentation.di.components.ApplicationComponent;
import com.pepo.news.presentation.di.modules.ActivityModule;
import com.pepo.news.presentation.navigation.Navigator;

public abstract class BaseActivity extends AppCompatActivity {

    private final String Tag = "BaseActivity";
    // @Inject
    public Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
        navigator = new Navigator();
    }

    /**
     * Get the Main Application component for dependency injection.
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((NewsFeedApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }


}
