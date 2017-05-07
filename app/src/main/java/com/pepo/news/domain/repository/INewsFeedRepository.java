package com.pepo.news.domain.repository;


import com.pepo.news.domain.model.NewsFeed;

import java.util.List;

import rx.Observable;

public interface INewsFeedRepository {


    Observable<List<NewsFeed>> getNewsFeed();

}
