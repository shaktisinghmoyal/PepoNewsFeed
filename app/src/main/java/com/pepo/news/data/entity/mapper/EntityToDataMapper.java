package com.pepo.news.data.entity.mapper;

import android.util.Log;

import com.pepo.news.data.entity.NewsFeedEntity;
import com.pepo.news.domain.model.NewsFeed;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class EntityToDataMapper {

    @Inject
    public EntityToDataMapper() {
    }


    public List<NewsFeed> transFromNewsFeedEntity(List<NewsFeedEntity> flightsEntity) {
        List<NewsFeed> newsFeeds = new ArrayList<NewsFeed>();

        for (NewsFeedEntity entity : flightsEntity){
            NewsFeed newsFeed = new NewsFeed();
            newsFeed.setTitle(entity.getTitle());
            newsFeed.setLink(entity.getLink());
            newsFeed.setImageLink(entity.getImageLink());
            newsFeeds.add(newsFeed);
        }
        return newsFeeds;
    }
}
