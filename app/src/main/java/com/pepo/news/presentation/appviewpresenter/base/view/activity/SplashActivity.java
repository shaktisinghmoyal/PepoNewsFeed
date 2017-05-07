package com.pepo.news.presentation.appviewpresenter.base.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.pepo.news.R;
import com.pepo.news.presentation.appviewpresenter.home.view.activity.NewsFeedActivity;


public class SplashActivity extends Activity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 500;
    private final String Tag = "SplashActivity";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Log.i(Tag, "onCreate");
        setContentView(R.layout.splash_activity);
            /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, NewsFeedActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
