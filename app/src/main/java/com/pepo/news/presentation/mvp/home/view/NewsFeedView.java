package com.pepo.news.presentation.mvp.home.view;


import android.content.Context;

import com.pepo.news.presentation.mvp.home.model.NewsFeedModel;

import java.util.List;

public interface NewsFeedView {
    void showMainLayout();

    void hideMainLayout();

    void showLoadingForNews();

    void hideLoadingForNews();

    void showRetryForNews();

    void hideRetryForNews();

    void showErrorForNews(String message);

    void disableErrorForNews();

    void displayNewsTemplate(List<NewsFeedModel> newsFeedModels);

    void showFullNews(int position,NewsFeedModel newsFeedModel);

    void blurTheReadNews(int position);

    void setActionBar();

     Context context();

    void hideErrorMessageIfShown();
}
