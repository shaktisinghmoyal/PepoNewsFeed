
package com.pepo.news.presentation.appviewpresenter.mapper;


import android.util.Log;

import com.pepo.news.data.entity.NewsFeedEntity;
import com.pepo.news.domain.model.NewsFeed;
import com.pepo.news.presentation.appviewpresenter.home.model.NewsFeedModel;
import com.pepo.news.presentation.di.PerActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Mapper class used to transform Book (in the domain layer) to { BookModel} in the
 * presentation layer.
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

        for (NewsFeed newsFeed : newsFeeds){
            NewsFeedModel newsFeedModel = new NewsFeedModel();
            newsFeedModel.setTitle(newsFeed.getTitle());
            newsFeedModel.setLink(newsFeed.getLink());
            newsFeedModel.setImageLink(newsFeed.getImageLink());
            newsFeedModels.add(newsFeedModel);
        }

        return newsFeedModels;
    }

    public NewsFeed transform(NewsFeedModel newsFeedModel) {
        NewsFeed newsFeed = null;
        if (newsFeedModel != null) {

        }

        return newsFeed;
    }

}
