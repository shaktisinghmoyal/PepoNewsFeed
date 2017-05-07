package com.pepo.news.presentation.appviewpresenter.home.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pepo.news.domain.exception.DefaultErrorBundle;
import com.pepo.news.domain.exception.ErrorBundle;
import com.pepo.news.domain.interactor.BaseUseCase;
import com.pepo.news.domain.interactor.DefaultSubscriber;
import com.pepo.news.domain.model.NewsFeed;
import com.pepo.news.presentation.appviewpresenter.base.presenter.Presenter;
import com.pepo.news.presentation.appviewpresenter.home.model.NewsFeedModel;
import com.pepo.news.presentation.appviewpresenter.home.view.NewsFeedView;
import com.pepo.news.presentation.appviewpresenter.mapper.DataMapper;
import com.pepo.news.presentation.di.PerActivity;
import com.pepo.news.presentation.exception.ErrorMessageFactory;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by shakti on 5/6/2017.
 */

@PerActivity
public class NewsFeedPresenter implements INewsFeedPresenter,Presenter{

    private final String Tag = "NewsFeedPresenter";
    private final BaseUseCase getNewsFeed;
    private final DataMapper dataMapper;
    private NewsFeedView newsFeedView;
    private List<NewsFeedModel> newsFeedModels;

    @Inject
    public NewsFeedPresenter(@Named("getNewsFeed") BaseUseCase getNewsFeed, DataMapper dataMapper) {
        this.getNewsFeed = getNewsFeed;
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
        fetchData();
    }


    public void tryAgain() {
        fetchData();
    }

    public void  fetchData(){
        hideViewRetry();
        showViewLoading();
        getNewsFeed();
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

    private void highMainLayout() {
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


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getNewsFeed.unsubscribe();
        this.newsFeedView = null;
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
            showMainLayout();
        }
    }


    @Override
    public void getNewsFeed() {
        getNewsFeed.execute(new NewsFeedSubscriber());
    }

    @Override
    public void onNewsTemplateClicked(NewsFeedModel newsFeedModel) {

    }


}
