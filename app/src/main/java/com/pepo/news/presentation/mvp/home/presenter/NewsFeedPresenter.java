package com.pepo.news.presentation.mvp.home.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.pepo.news.R;
import com.pepo.news.domain.exception.DefaultErrorBundle;
import com.pepo.news.domain.exception.ErrorBundle;
import com.pepo.news.domain.interactor.BaseUseCase;
import com.pepo.news.domain.interactor.DefaultSubscriber;
import com.pepo.news.domain.model.NewsFeed;
import com.pepo.news.presentation.exception.ErrorMessageFactory;
import com.pepo.news.presentation.mvp.base.presenter.Presenter;
import com.pepo.news.presentation.mvp.home.models.NewsFeedModel;
import com.pepo.news.presentation.mvp.home.view.NewsFeedView;
import com.pepo.news.presentation.mapper.DataMapper;
import com.pepo.news.presentation.di.PerActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by shakti on 5/6/2017.
 */

@PerActivity
public class NewsFeedPresenter implements INewsFeedPresenter, Presenter {

    private final BaseUseCase getNewsFeed;
    private final BaseUseCase getNewsFeedFromDB;
    private final BaseUseCase updateReadInfoInDB;
    private final DataMapper dataMapper;
    private NewsFeedView newsFeedView;
    private List<NewsFeedModel> newsFeedModels;
    private final String NEWS_FEEDS = "NEWS_FEEDS";

    @Inject
    public NewsFeedPresenter(@Named("getNewsFeed") BaseUseCase getNewsFeed, @Named("updateReadInfoInDB")
            BaseUseCase updateReadInfoInDB, @Named("getNewsFeedFromDB")
                                     BaseUseCase getNewsFeedFromDB, DataMapper dataMapper) {
        this.getNewsFeed = getNewsFeed;
        this.getNewsFeedFromDB = getNewsFeedFromDB;
        this.updateReadInfoInDB = updateReadInfoInDB;
        this.dataMapper = dataMapper;

    }

    public void setView(@NonNull NewsFeedView newsFeedView) {
        this.newsFeedView = newsFeedView;

    }

    /**
     * Initializes the presenter by start retrieving the FlightsEntity list.
     */
    public void initialize() {

        setActionBar();
        if (newsFeedModels != null) {
            hideViewLoading();
            showMainLayout();
            showNewsTemplates(newsFeedModels);
        } else {
            hideViewRetry();
            showViewLoading();
        }

    }


    public void tryAgain() {
        fetchData();
    }

    public void fetchData() {
        hideViewRetry();
        showViewLoading();
        getNewsFeed();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(NEWS_FEEDS, (ArrayList) newsFeedModels);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            newsFeedModels = savedInstanceState.getParcelableArrayList(NEWS_FEEDS);
        }

    }

    private void setActionBar() {
        newsFeedView.setActionBar();
    }

    private void showViewLoading() {
        newsFeedView.showLoadingForNews();

    }

    private void hideViewLoading() {
        newsFeedView.hideLoadingForNews();
    }


    private void hideViewRetry() {
        newsFeedView.hideRetryForNews();
    }

    private void showViewRetry() {
        newsFeedView.showRetryForNews();
    }

    private void showMainLayout() {
        newsFeedView.showMainLayout();
    }

    private void hideMainLayout() {
        newsFeedView.hideMainLayout();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(newsFeedView.context(),
                errorBundle.getException());
        this.newsFeedView.showErrorForNews(errorMessage);
    }


    private void showNewsTemplates(List<NewsFeedModel> newsFeedModels) {
        newsFeedView.displayNewsTemplate(newsFeedModels);
    }

    private void hideErrorMessageIfShown() {
        newsFeedView.hideErrorMessageIfShown();
    }

    @Override
    public void setTheCurrentShownNewsRead(int position) {
        setTheCurrentNewsReadInDB(position);
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getNewsFeed.unsubscribe();
        getNewsFeedFromDB.unsubscribe();
        updateReadInfoInDB.unsubscribe();
        this.newsFeedView = null;
    }

    private final class NewsFeedReadDBUpdateSubscriber extends DefaultSubscriber<Integer> {


        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            showErrorMessage(new DefaultErrorBundle((Exception) e));

        }

        @Override
        public void onNext(Integer position) {
            newsFeedView.blurTheReadNews(position);
        }
    }


    private final class NewsFeedSubscriber extends DefaultSubscriber<List<NewsFeed>> {


        @Override
        public void onCompleted() {
            hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            showErrorMessage(new DefaultErrorBundle((Exception) e));
            hideViewLoading();
            showViewRetry();
        }

        @Override
        public void onNext(List<NewsFeed> newsFeeds) {
            newsFeedModels = dataMapper.transform(newsFeeds);
            showNewsTemplates(newsFeedModels);
            hideViewLoading();
            hideViewRetry();
            showMainLayout();
        }
    }

    private void setTheCurrentNewsReadInDB(int position) {
        updateReadInfoInDB.execute(position, new NewsFeedReadDBUpdateSubscriber());
    }

    @Override
    public void getNewsFeedFromDB() {
        getNewsFeedFromDB.execute(new NewsFeedSubscriber());
    }

    @Override
    public void getNewsFeed() {
        getNewsFeed.execute(new NewsFeedSubscriber());
    }

    @Override
    public void onNewsTemplateClicked(int position, NewsFeedModel newsFeedModel) {
        newsFeedView.showFullNews(position, newsFeedModel);
    }

    @Override
    public void updatedDataReceived(Intent intent) {
        ArrayList<NewsFeed> newsFeedList = intent.getParcelableArrayListExtra
                (newsFeedView.context().getString(R.string.news_feeds));
        newsFeedModels = dataMapper.transform(newsFeedList);
        hideViewLoading();
        showMainLayout();
        hideErrorMessageIfShown();
        showNewsTemplates(newsFeedModels);

    }

}
