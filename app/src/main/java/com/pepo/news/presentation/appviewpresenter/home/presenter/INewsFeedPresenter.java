package com.pepo.news.presentation.appviewpresenter.home.presenter;

import com.pepo.news.presentation.appviewpresenter.home.model.NewsFeedModel;

/**
 * Created by shakti on 5/6/2017.
 */

public interface INewsFeedPresenter {
    void getNewsFeed();

    void onNewsTemplateClicked(NewsFeedModel newsFeedModel);

}
