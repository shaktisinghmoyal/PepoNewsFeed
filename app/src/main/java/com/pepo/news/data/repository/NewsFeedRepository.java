package com.pepo.news.data.repository;



import com.pepo.news.data.entity.NewsFeedEntity;
import com.pepo.news.data.entity.mapper.EntityToDataMapper;
import com.pepo.news.data.networking.RestApi;
import com.pepo.news.domain.model.NewsFeed;
import com.pepo.news.domain.repository.INewsFeedRepository;


import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;


public class NewsFeedRepository implements INewsFeedRepository {

    private final String Tag = "NewsFeedRepository";
    private RestApi restApi;
    private EntityToDataMapper dataMapper;

    @Inject
    public NewsFeedRepository(RestApi restApi, EntityToDataMapper dataMapper) {
        this.dataMapper=dataMapper;
        this.restApi = restApi;

    }

    @Override
    public Observable<List<NewsFeed>> getNewsFeed() {
        return restApi.getNewsFeeds().map(new Func1<List<NewsFeedEntity>,
                List<NewsFeed>>() {
            @Override
            public List<NewsFeed> call(List<NewsFeedEntity> newsFeedEntity) {
                return dataMapper.transformFromNewsFeedEntity(newsFeedEntity);
            }
        });
    }


}
