package com.pepo.news.data.networking;




import com.pepo.news.data.entity.NewsFeedEntity;

import java.util.List;

import rx.Observable;

public interface IEndPoints extends BaseURL {

    /**
     * Retrieves an {@link Observable} which will emit a List of {@link NewsFeedEntity}.
     */
    Observable<List<NewsFeedEntity>> getNewsFeeds();



}
