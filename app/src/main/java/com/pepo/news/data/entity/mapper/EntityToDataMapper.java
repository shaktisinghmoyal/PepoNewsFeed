package com.pepo.news.data.entity.mapper;

import com.pepo.news.data.entity.NewsFeedEntity;
import com.pepo.news.domain.model.NewsFeed;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * it maps the model classes (POJO classes ) of data layer into the model classes (POJO classes )
 * of domain layer , in our case we are simply mapping to another POJO class. But in ideal cases
 * there would be slight difference among these POJO classes corresponding to their layers. we
 * would write those  mapping logic here
 */
public class EntityToDataMapper {

    @Inject
    public EntityToDataMapper() {
    }


    public List<NewsFeed> transFromNewsFeedEntity(List<NewsFeedEntity> flightsEntity) {
        List<NewsFeed> newsFeeds = new ArrayList<NewsFeed>();

        for (NewsFeedEntity entity : flightsEntity) {
            NewsFeed newsFeed = new NewsFeed();
            newsFeed.setTitle(entity.getTitle());
            newsFeed.setLink(entity.getLink());
            newsFeed.setImageLink(entity.getImageLink());
            newsFeed.setIsRead(entity.getIsRead());
            newsFeeds.add(newsFeed);
        }
        return newsFeeds;
    }
}
