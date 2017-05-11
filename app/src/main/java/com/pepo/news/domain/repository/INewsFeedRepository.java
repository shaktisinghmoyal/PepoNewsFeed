package com.pepo.news.domain.repository;


import com.pepo.news.domain.model.NewsFeed;

import java.util.List;

import rx.Observable;


/**
 * This is the interface of the entry point of all kind of data related calls, whether it is to
 * fetch data from
 * server or from databases.
 * */

public interface INewsFeedRepository {


    Observable<List<NewsFeed>> getNewsFeed();
    Observable<List<NewsFeed>> getNewsFeedFromDB();
    Observable<Integer> updateReadInfoInDB(int position);

}
