package com.pepo.news.presentation.appviewpresenter.home.view.adapter;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pepo.news.presentation.appviewpresenter.home.model.NewsFeedModel;
import com.pepo.news.presentation.utils.NewsFeedViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedViewHolder>  {
    private List<NewsFeedModel> flightModelList;
    private OnItemClickListener onNewsFeedClickListener;
    private AppCompatActivity appCompatActivity;


    @Inject
    public NewsFeedAdapter(AppCompatActivity activity) {
        this.flightModelList = new ArrayList<NewsFeedModel>();
        appCompatActivity = activity;
    }

    @Override
    public NewsFeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return NewsFeedViewHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup, appCompatActivity);

    }

    @Override
    public void onBindViewHolder(NewsFeedViewHolder newsFeedViewHolder, final int position) {
        newsFeedViewHolder.bindTo(flightModelList.get(position));
        newsFeedViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNewsFeedClickListener != null) {
                    onNewsFeedClickListener.onNewsFeedClicked(flightModelList.get(position));
                }
            }
        });

    }

    public void setNewsFeeds(Collection<NewsFeedModel> newsFeedModelCollection) {
        validateUsersCollection(newsFeedModelCollection);
        flightModelList.addAll(newsFeedModelCollection);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onNewsFeedClickListener) {
        this.onNewsFeedClickListener = onNewsFeedClickListener;
    }

    private void validateUsersCollection(Collection<NewsFeedModel> usersCollection) {
        if (usersCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    @Override
    public int getItemCount() {
        return (null != flightModelList ? flightModelList.size() : 0);
    }


    public interface OnItemClickListener {
        void onNewsFeedClicked(NewsFeedModel newsFeedModel);
    }



}