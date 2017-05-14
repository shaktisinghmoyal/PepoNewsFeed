package com.pepo.news.presentation.mvp.home.view.adapter;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pepo.news.presentation.mvp.home.models.NewsFeedModel;
import com.pepo.news.presentation.mvp.home.view.viwholders.NewsFeedViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;


// adapter for the recycler view used to display news template

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedViewHolder>  {
    private List<NewsFeedModel> newsFeedModelList;
    private OnItemClickListener onNewsFeedClickListener;
    private AppCompatActivity appCompatActivity;


    @Inject
    public NewsFeedAdapter(AppCompatActivity activity) {
        this.newsFeedModelList = new ArrayList<NewsFeedModel>();
        appCompatActivity = activity;
    }

    @Override
    public NewsFeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return NewsFeedViewHolder.create(LayoutInflater.from(viewGroup.getContext()), viewGroup, appCompatActivity);

    }

    @Override
    public void onBindViewHolder(NewsFeedViewHolder newsFeedViewHolder, final int position) {
        NewsFeedModel newsFeedModel = newsFeedModelList.get(position);
        newsFeedViewHolder.bindTo(newsFeedModel);
        newsFeedViewHolder.setNewsFeedImage(newsFeedModel.getImageLink());
        newsFeedViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNewsFeedClickListener != null) {
                    onNewsFeedClickListener.onNewsFeedClicked(position,newsFeedModelList.get
                            (position));
                }
            }
        });

    }

    public void setNewsFeeds(Collection<NewsFeedModel> newsFeedModelCollection) {
        newsFeedModelList.clear();
        validateUsersCollection(newsFeedModelCollection);
        newsFeedModelList.addAll(newsFeedModelCollection);
        notifyDataSetChanged();
    }

    public void blurTheReadNews(int position){
        newsFeedModelList.get(position).setIsRead(true);
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
        return (null != newsFeedModelList ? newsFeedModelList.size() : 0);
    }


    public interface OnItemClickListener {
        void onNewsFeedClicked(int position,NewsFeedModel newsFeedModel);
    }



}