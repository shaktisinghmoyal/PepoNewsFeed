package com.pepo.news.presentation.utils;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pepo.news.databinding.SingleNewsTemplateBinding;
import com.pepo.news.presentation.appviewpresenter.home.model.NewsFeedModel;


public class NewsFeedViewHolder extends RecyclerView.ViewHolder {
    private SingleNewsTemplateBinding itemBinding;

    private NewsFeedViewHolder(SingleNewsTemplateBinding binding, AppCompatActivity activity) {
        super(binding.getRoot());
        itemBinding = binding;
    }

    public static NewsFeedViewHolder create(LayoutInflater inflater, ViewGroup parent, AppCompatActivity activity) {
        SingleNewsTemplateBinding binding = SingleNewsTemplateBinding.inflate(inflater, parent, false);
        return new NewsFeedViewHolder(binding, activity);
    }


    public void bindTo(NewsFeedModel newsFeedModel) {
        itemBinding.setNewsFeedModel(newsFeedModel);
        itemBinding.executePendingBindings();
    }
}