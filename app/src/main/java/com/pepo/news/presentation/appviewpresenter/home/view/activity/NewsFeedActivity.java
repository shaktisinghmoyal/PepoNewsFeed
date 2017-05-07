package com.pepo.news.presentation.appviewpresenter.home.view.activity;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.pepo.news.R;
import com.pepo.news.databinding.ActivityNewsFeedBinding;
import com.pepo.news.presentation.appviewpresenter.base.view.activity.BaseActivity;
import com.pepo.news.presentation.appviewpresenter.home.model.NewsFeedModel;
import com.pepo.news.presentation.appviewpresenter.home.presenter.NewsFeedPresenter;
import com.pepo.news.presentation.appviewpresenter.home.view.NewsFeedView;
import com.pepo.news.presentation.appviewpresenter.home.view.adapter.NewsFeedAdapter;
import com.pepo.news.presentation.di.HasComponent;
import com.pepo.news.presentation.di.components.DaggerNewsFeedComponent;
import com.pepo.news.presentation.di.components.NewsFeedComponent;

import java.util.List;

import javax.inject.Inject;


public class NewsFeedActivity extends BaseActivity implements NewsFeedView,
        HasComponent<NewsFeedComponent> {
    public static Activity activity;
    private final String Tag = "NewsFeedActivity";
    @Inject
    NewsFeedPresenter newsFeedPresenter;
    @Inject
    NewsFeedAdapter newsFeedAdapter;
    RecyclerView newsRecyclerView;
    private NewsFeedComponent newsFeedComponent;

    @Override
    public Context context() {
        return null;
    }

    private ActivityNewsFeedBinding activityNewsFeedBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNewsFeedBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_news_feed);
        initializeInjector();
        newsFeedComponent.inject(this);
        initializeViews();
        newsFeedPresenter.setView(this);
        newsFeedPresenter.initialize();

    }

    private void initializeViews() {
        activityNewsFeedBinding.viewRetry.setOnClickListener(clickListners);
        newsRecyclerView=activityNewsFeedBinding.newsFeedRecyclerView;
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this
                .getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        newsRecyclerView.setAdapter(newsFeedAdapter);
    }

    private void initializeInjector() {
        this.newsFeedComponent = DaggerNewsFeedComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();

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
    public void disableErrorForNews() {
        activityNewsFeedBinding.viewRetry.setVisibility(View.GONE);
    }

    @Override
    public void displayNewsTemplate(List<NewsFeedModel> newsFeedModels) {
            newsFeedAdapter.setNewsFeeds(newsFeedModels);
    }

    @Override
    public void showFullNews(NewsFeedModel newsFeedModel) {

    }

    @Override
    public void setActionBar() {

    }

    private NewsFeedAdapter.OnItemClickListener onNewsTemplateClickListener =
            new NewsFeedAdapter.OnItemClickListener() {
                @Override
                public void onNewsFeedClicked(NewsFeedModel newsFeedModel) {
                    if (newsFeedPresenter != null && newsFeedModel != null) {
                        newsFeedPresenter.onNewsTemplateClicked(newsFeedModel);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        newsRecyclerView.setAdapter(null);
    }


    @Override
    public void showMainLayout() {
        activityNewsFeedBinding.mainView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMainLayout() {
        activityNewsFeedBinding.mainView.setVisibility(View.INVISIBLE);
    }




}
