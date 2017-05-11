package com.pepo.news.data.networking;

import com.pepo.news.data.exception.NetworkConnectionException;
import com.pepo.news.data.networking.datastorage.NewsFeedStorage;
import com.pepo.news.domain.model.NewsFeed;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * This class is to get the data from our device's database instead of getting it from server.
 * It's purpose is same as what EndPointsCalls.class is doing. Only difference that is fetch the
 * data from Database.
 * */

public class DBCalls extends BaseMethodsForApiCalls {
    @Inject
    NewsFeedStorage newsFeedStorage;


    @Inject
    public DBCalls() {

    }

    public Observable<Integer> updateReadInfoInDB(final int position) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {

            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        newsFeedStorage.updateNewsFeedReadInfo(position);
                        subscriber.onNext(position);
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        subscriber.onError(new NetworkConnectionException(e.getMessage()));
                    }
                }

            }
        });

    }

    public Observable<List<NewsFeed>> getNewsFeeds() {

        return Observable.create(new Observable.OnSubscribe<List<NewsFeed>>() {
            @Override
            public void call(Subscriber<? super List<NewsFeed>> subscriber) {
                List<NewsFeed> newsFeedEntityList = newsFeedStorage.getAllNewsFeeds();
                if (newsFeedEntityList.size() != 0) {
                    subscriber.onNext(newsFeedEntityList);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new NetworkConnectionException());
                }
            }
        });

    }


}
