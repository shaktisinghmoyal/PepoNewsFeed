package com.pepo.news.data.networking;


import android.content.Context;
import android.util.Log;

import com.pepo.news.data.entity.NewsFeedEntity;
import com.pepo.news.data.entity.mapper.RSSToEntityMapper;
import com.pepo.news.data.exception.NetworkConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

@Singleton
public class RestApi extends BaseMethodsForApiRestCalls implements IRestApi, BaseURL {
    private final String Tag = "RestApi";
    private final Context context;
    private RSSToEntityMapper RSSToEntityMapper;


    @Inject
    public RestApi(Context context, RSSToEntityMapper RSSToEntityMapper) {
        if (context == null || RSSToEntityMapper == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.RSSToEntityMapper = RSSToEntityMapper;
    }


    public Observable<List<NewsFeedEntity>> getNewsFeeds() {
        return Observable.create(new Observable.OnSubscribe<List<NewsFeedEntity>>() {

            @Override
            public void call(Subscriber<? super List<NewsFeedEntity>> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        InputStream inputStream = getNewsStream();

                        if(inputStream!=null){
                            subscriber.onNext(RSSToEntityMapper.parse(
                                    inputStream));
                            subscriber.onCompleted();
                        }
                        else {
                            subscriber.onError(new NetworkConnectionException());
                        }


                    } catch (Exception e) {
                        subscriber.onError(new NetworkConnectionException(e.getMessage()));
                    }
                } else {
                    subscriber.onError(new NetworkConnectionException());
                }
            }
        });

    }
    private InputStream getNewsStream() throws MalformedURLException {
        try {
            URL url = new URL(NEWS_FEED_URL);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }
}
