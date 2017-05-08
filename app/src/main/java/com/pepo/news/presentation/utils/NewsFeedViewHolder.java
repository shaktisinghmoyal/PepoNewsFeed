package com.pepo.news.presentation.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pepo.news.R;
import com.pepo.news.databinding.SingleNewsTemplateBinding;
import com.pepo.news.presentation.appviewpresenter.home.model.NewsFeedModel;
import com.squareup.picasso.Picasso;


public class NewsFeedViewHolder extends RecyclerView.ViewHolder {
    private SingleNewsTemplateBinding itemBinding;
    private static Context context;

    private NewsFeedViewHolder(SingleNewsTemplateBinding binding, AppCompatActivity activity) {
        super(binding.getRoot());
        itemBinding = binding;
    }

    public static NewsFeedViewHolder create(LayoutInflater inflater, ViewGroup parent, AppCompatActivity activity) {
        SingleNewsTemplateBinding binding = SingleNewsTemplateBinding.inflate(inflater, parent, false);
        context=activity;
        return new NewsFeedViewHolder(binding, activity);
    }


    public void bindTo(NewsFeedModel newsFeedModel) {
        itemBinding.setNewsFeedModel(newsFeedModel);
        itemBinding.executePendingBindings();
    }

    public void setNewsFeedImage(String url){
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .into(itemBinding.newsLogo);
    }
}