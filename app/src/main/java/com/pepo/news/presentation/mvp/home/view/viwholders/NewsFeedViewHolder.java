package com.pepo.news.presentation.mvp.home.view.viwholders;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pepo.news.R;
import com.pepo.news.databinding.SingleNewsTemplateBinding;
import com.pepo.news.presentation.mvp.home.model.NewsFeedModel;
import com.squareup.picasso.Picasso;


public class NewsFeedViewHolder extends RecyclerView.ViewHolder {
    private SingleNewsTemplateBinding itemBinding;
    private static Context context;
    private static int greyColor;
    private static int blackColor;

    private NewsFeedViewHolder(SingleNewsTemplateBinding binding, AppCompatActivity activity) {
        super(binding.getRoot());
        itemBinding = binding;
    }

    public static NewsFeedViewHolder create(LayoutInflater inflater, ViewGroup parent, AppCompatActivity activity) {
        SingleNewsTemplateBinding binding = SingleNewsTemplateBinding.inflate(inflater, parent, false);
        context=activity;
        if(android.os.Build.VERSION.SDK_INT>=23){
            greyColor = context.getResources().getColor(R.color.grey,null);
            blackColor = context.getResources().getColor(R.color.black,null);
        }
        else {
            greyColor = context.getResources().getColor(R.color.grey);
            blackColor = context.getResources().getColor(R.color.black);
        }
        return new NewsFeedViewHolder(binding, activity);
    }


    public void bindTo(NewsFeedModel newsFeedModel) {
        itemBinding.setNewsFeedModel(newsFeedModel);
        if(newsFeedModel.getIsRead()){
            itemBinding.newsTitle.setTextColor(greyColor);
        }
        else {
            itemBinding.newsTitle.setTextColor(blackColor);
        }

        itemBinding.executePendingBindings();
    }

    public void setNewsFeedImage(String url){
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .into(itemBinding.newsLogo);
    }
}