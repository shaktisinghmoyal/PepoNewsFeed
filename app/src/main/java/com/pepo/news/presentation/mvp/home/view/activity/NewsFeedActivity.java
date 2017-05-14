package com.pepo.news.presentation.mvp.home.view.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pepo.news.R;
import com.pepo.news.databinding.ActivityNewsFeedBinding;
import com.pepo.news.presentation.di.HasComponent;
import com.pepo.news.presentation.di.components.DaggerNewsFeedComponent;
import com.pepo.news.presentation.di.components.NewsFeedComponent;
import com.pepo.news.presentation.mvp.base.view.activity.BaseActivity;
import com.pepo.news.presentation.mvp.home.models.NewsFeedModel;
import com.pepo.news.presentation.mvp.home.presenter.NewsFeedPresenter;
import com.pepo.news.presentation.mvp.home.view.NewsFeedView;
import com.pepo.news.presentation.mvp.home.view.adapter.NewsFeedAdapter;

import java.util.List;

import javax.inject.Inject;


public class NewsFeedActivity extends BaseActivity implements NewsFeedView,
        HasComponent<NewsFeedComponent> {
    public static Activity activity;
    private final int FULL_NEWS_REQUEST_CODE = 100;
    RecyclerView newsRecyclerView;
    private NewsFeedComponent newsFeedComponent;
    private int pollingInterval = 60000;
    private Handler mHandler;
    private BroadcastReceiver newsUpdateReceiver;
    private BroadcastReceiver networkConnectivityStatusReceiver;
    private int positionOfCurrentFullNews;

    @Inject
    NewsFeedPresenter newsFeedPresenter;
    @Inject
    NewsFeedAdapter newsFeedAdapter;

    private final Runnable getNewsFeedPeriodically = new Runnable() {
        @Override
        public void run() {
            newsFeedPresenter.getNewsFeed();
            mHandler.postDelayed(this, pollingInterval);
        }
    };


    @Override
    public Context context() {
        return getApplicationContext();
    }

    private ActivityNewsFeedBinding activityNewsFeedBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNewsFeedBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_news_feed);
        initialize(savedInstanceState);


    }

    private void initialize(Bundle savedInstanceState) {
        initializeInjector();
        newsFeedComponent.inject(this);
        initializeViews();
        newsFeedPresenter.setView(this);
        newsFeedPresenter.onRestoreInstanceState(savedInstanceState);
        networkConnectivityStatusReceiver = new UpdateNetworkStateReceiver();
        newsUpdateReceiver = new NewsUpdateReceiver();
        registerNetworkConnectivityStatusReceiver();
        registerNewsUpdateReceiver();
        setNewsFeedRepeatingTask();
        newsFeedPresenter.initialize();

    }

    private void initializeInjector() {
        this.newsFeedComponent = DaggerNewsFeedComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();

    }


    private void initializeViews() {
        newsFeedAdapter.setOnItemClickListener(onNewsTemplateClickListener);
        activityNewsFeedBinding.viewRetry.setOnClickListener(clickListners);
        newsRecyclerView = activityNewsFeedBinding.newsFeedRecyclerView;
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this
                .getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        newsRecyclerView.setAdapter(newsFeedAdapter);

    }

    private void registerNewsUpdateReceiver() {
        IntentFilter filter = new IntentFilter(getString(R.string.custom_intent_news_update));
        try {
            registerReceiver(newsUpdateReceiver, filter);
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException occured");
        }


    }

    private void registerNetworkConnectivityStatusReceiver() {
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkConnectivityStatusReceiver, filter);
    }


    private void unRegisterNewsUpdateReceiver() {

        try {
            unregisterReceiver(newsUpdateReceiver);
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException occured");
        }
    }


    private void unRegisterNetworkConnectivityStatus() {
        unregisterReceiver(networkConnectivityStatusReceiver);
    }

    private void setNewsFeedRepeatingTask() {
        mHandler = new Handler();

    }


    /*to handle the rotation, otherwise we have to fetch the data once again from the network*/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        newsFeedPresenter.onSaveInstanceState(outState);
    }

    @Override
    public void showLoadingForNews() {
        activityNewsFeedBinding.fetchingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingForNews() {
        activityNewsFeedBinding.fetchingBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showRetryForNews() {
        activityNewsFeedBinding.viewRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetryForNews() {
        activityNewsFeedBinding.viewRetry.setVisibility(View.INVISIBLE);

    }

    @Override
    public void showErrorForNews(String message) {
        activityNewsFeedBinding.viewRetry.setVisibility(View.VISIBLE);
        activityNewsFeedBinding.errorTextView.setText(message);
    }

    @Override
    public void hideErrorMessageIfShown() {
        activityNewsFeedBinding.viewRetry.setVisibility(View.GONE);
    }

    @Override
    public void disableErrorForNews() {
        activityNewsFeedBinding.viewRetry.setVisibility(View.GONE);
    }

    @Override
    public void displayNewsTemplate(List<NewsFeedModel> newsFeedModels) {
        newsFeedAdapter.setNewsFeeds(newsFeedModels);
    }

    @Override
    public void showMainLayout() {
        activityNewsFeedBinding.mainView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMainLayout() {
        activityNewsFeedBinding.mainView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showFullNews(int position, NewsFeedModel newsFeedModel) {
        positionOfCurrentFullNews = position;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(newsFeedModel.getLink()));
        startActivityForResult(intent, FULL_NEWS_REQUEST_CODE);
    }

    @Override
    public void blurTheReadNews(int position) {
        newsFeedAdapter.blurTheReadNews(position);

    }

    @Override
    public void setActionBar() {

    }

    private NewsFeedAdapter.OnItemClickListener onNewsTemplateClickListener =
            new NewsFeedAdapter.OnItemClickListener() {
                @Override
                public void onNewsFeedClicked(int position, NewsFeedModel newsFeedModel) {
                    if (newsFeedPresenter != null && newsFeedModel != null) {
                        newsFeedPresenter.onNewsTemplateClicked(position, newsFeedModel);
                    }
                }

            };


    private View.OnClickListener clickListners = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.view_retry:
                    newsFeedPresenter.tryAgain();

                    break;


            }

        }
    };


    @Override
    public NewsFeedComponent getComponent() {
        return newsFeedComponent;
    }


    @Override
    public void onResume() {
        super.onResume();
        newsFeedPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        newsFeedPresenter.pause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        newsFeedPresenter.destroy();
        unRegisterNewsUpdateReceiver();
        unRegisterNetworkConnectivityStatus();
        newsRecyclerView.setAdapter(null);
        mHandler.removeCallbacks(getNewsFeedPeriodically);
    }


    public class NewsUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            newsFeedPresenter.updatedDataReceived(intent);
        }
    }


    public class UpdateNetworkStateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {


            ConnectivityManager connectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);

            final android.net.NetworkInfo networkInfo = connectivityManager
                    .getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                registerNewsUpdateReceiver();
                //newsFeedPresenter.getNewsFeed();
                mHandler.post(getNewsFeedPeriodically);
            } else {
                if (newsFeedAdapter.getItemCount() == 0) {
                    newsFeedPresenter.getNewsFeedFromDB();
                }
                unregisterReceiver(newsUpdateReceiver);
                mHandler.removeCallbacks(getNewsFeedPeriodically);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FULL_NEWS_REQUEST_CODE) {
            newsFeedPresenter.setTheCurrentShownNewsRead(positionOfCurrentFullNews);
        }
    }
}
