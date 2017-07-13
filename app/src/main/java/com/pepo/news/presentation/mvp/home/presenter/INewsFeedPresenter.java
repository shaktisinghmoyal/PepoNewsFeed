package com.pepo.news.presentation.mvp.home.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.pepo.news.presentation.mvp.home.models.NewsFeedModel;

/**
 * Created by shakti on 5/6/2017.
 */

public interface INewsFeedPresenter {
    void getNewsFeed();

    void getNewsFeedFromDB();

    void onNewsTemplateClicked(int position,NewsFeedModel newsFeedModel);

    void updatedDataReceived(Intent newsFeedList);

    void onSaveInstanceState(Bundle outState);

    void onRestoreInstanceState(Bundle savedInstanceState);

    void setTheCurrentShownNewsRead(int position);



}
