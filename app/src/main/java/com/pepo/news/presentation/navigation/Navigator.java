package com.pepo.news.presentation.navigation;
//import javax.inject.Inject;
//import javax.inject.Singleton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application ( to different acivities or fragments, however
 * in our case we have just 1 activity hence this class
 * has no use in our case, although its convenient to have this class if we have lots of
 * activities and fragments).
 */
@Singleton
public class Navigator {
    @Inject
    public Navigator() {
        //empty
    }


    public void startAnotherActivity(AppCompatActivity from, Intent intent) {
        from.startActivity(intent);
    }

}
