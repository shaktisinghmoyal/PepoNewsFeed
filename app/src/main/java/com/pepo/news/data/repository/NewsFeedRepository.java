package com.pepo.news.data.repository;


import com.pepo.news.data.entity.NewsFeedEntity;
import com.pepo.news.data.entity.mapper.EntityToDataMapper;
import com.pepo.news.data.networking.DBCalls;
import com.pepo.news.data.networking.EndPointsCalls;
import com.pepo.news.domain.model.NewsFeed;
import com.pepo.news.domain.repository.INewsFeedRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;


/**
 * This is the entry point of all kind of data related calls, whether it is to fetch data from
 * server or from databases.
 * */
public class NewsFeedRepository implements INewsFeedRepository {

    private EndPointsCalls restApiCalls;
    private EntityToDataMapper dataMapper;
    private DBCalls dbCalls;

    @Inject
    public NewsFeedRepository(EndPointsCalls restApiCalls, EntityToDataMapper dataMapper, DBCalls dbCalls) {
        this.dataMapper = dataMapper;
        this.restApiCalls = restApiCalls;
        this.dbCalls = dbCalls;

    }

    @Override
    public Observable<List<NewsFeed>> getNewsFeed() {

        return restApiCalls.getNewsFeeds().map(new Func1<List<NewsFeedEntity>,
                List<NewsFeed>>() {
            @Override
            public List<NewsFeed> call(List<NewsFeedEntity> newsFeedEntity) {
                return dataMapper.transFromNewsFeedEntity(newsFeedEntity);
            }
        });
    }

    @Override
    public Observable<List<NewsFeed>> getNewsFeedFromDB() {
        return dbCalls.getNewsFeeds();
    }

    @Override
    public Observable<Integer> updateReadInfoInDB(int position) {
        return dbCalls.updateReadInfoInDB(position);
    }
}
