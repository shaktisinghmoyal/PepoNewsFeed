
package com.pepo.news.presentation.mapper;


import com.pepo.news.domain.model.NewsFeed;
import com.pepo.news.presentation.mvp.home.models.NewsFeedModel;
import com.pepo.news.presentation.di.PerActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * it maps the model classes (POJO classes ) of domain layer into the model classes (POJO classes )
 * of presentation layer , in our case we are simply mapping to another POJO class. But in ideal
 * cases
 * there would be slight difference among these POJO classes corresponding to their layers. we
 * would write those  mapping logic here
 */
@PerActivity
public class DataMapper {


    private final String Tag = "BookModelDataMapper";

    @Inject
    public DataMapper() {
    }

    public List<NewsFeedModel> transform(List<NewsFeed> newsFeeds) {
        if (newsFeeds == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }

        List<NewsFeedModel> newsFeedModels = new ArrayList<NewsFeedModel>();

        for (NewsFeed newsFeed : newsFeeds) {
            NewsFeedModel newsFeedModel = new NewsFeedModel();
            newsFeedModel.setTitle(newsFeed.getTitle());
            newsFeedModel.setLink(newsFeed.getLink());
            newsFeedModel.setImageLink(newsFeed.getImageLink());
            newsFeedModel.setIsRead(newsFeed.getIsRead());
            newsFeedModels.add(newsFeedModel);
        }

        return newsFeedModels;
    }


}
