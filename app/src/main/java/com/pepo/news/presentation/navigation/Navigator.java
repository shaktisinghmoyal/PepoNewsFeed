package com.pepo.news.presentation.navigation;
//import javax.inject.Inject;
//import javax.inject.Singleton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.pepo.news.presentation.appviewpresenter.home.view.activity.NewsFeedActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {
    private final String Tag = "Navigator";
    @Inject
    public Navigator() {
        //empty
    }

    public void startBookDetailActivity(AppCompatActivity from, Bundle bundle) {
        Intent intent = new Intent(from, NewsFeedActivity.class);
        from.startActivity(intent);

    }

    public void startAnotherActivity(AppCompatActivity from, Intent intent) {


    }





    public void openAsRoot(AppCompatActivity activity, int containerViewId, Fragment fragment, String tag) {
        popEveryFragment(activity);
    }

    public void openAsMainRoot(AppCompatActivity activity) {
        popEveryFragment(activity);
        // addFragment( activity, containerViewId,  fragment, tag);
    }

    public void popEveryFragment(AppCompatActivity activity) {
        // Clear all back stack.
        FragmentManager manager = activity.getSupportFragmentManager();
        int backStackCount = manager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {

            // Get the back stack fragment id.
            int backStackId = manager.getBackStackEntryAt(i).getId();

            manager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }
    }
}
