package com.topic.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.topic.R;
import com.topic.data.DailyNews;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 坚守内心的宁静 on 2017/12/1.
 */

public abstract class BaseTopicAdapter extends RecyclerView.Adapter<BaseTopicAdapter.BaseTopicHolder> {
    Context context;
    List<DailyNews> newsList;

    public void updateNewsList(List<DailyNews> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.noimage)
            .showImageOnFail(R.drawable.noimage)
            .showImageForEmptyUri(R.drawable.lks_for_blank_url)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .build();

    ImageLoadingComplete complete = new ImageLoadingComplete();

    @Override
    public BaseTopicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new BaseTopicHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return newsList.get(position).hashCode();
    }

    @Override
    public void onBindViewHolder(BaseTopicHolder holder, int position) {
        DailyNews dailyNew = newsList.get(position);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(dailyNew.getThumbnailUrl(), holder.icon, options, complete);
        if(dailyNew.isSelect()) {
            holder.title.setTextColor(Color.parseColor("#60000000"));
        }else {
            holder.title.setTextColor(Color.parseColor("#8A000000"));
        }
        if(dailyNew.getQuestions().size() > 1) {
            holder.title.setText(dailyNew.getDailyTitle());
            holder.content.setText(R.string.qustions_title);
        }else {
            holder.title.setText(dailyNew.getQuestions().get(0).getTitle());
            holder.content.setText(dailyNew.getDailyTitle());
        }
        bindTopicListener(holder, position);
    }

    protected abstract void bindTopicListener(BaseTopicHolder holder, int position);

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }

    public class BaseTopicHolder extends RecyclerView.ViewHolder{
        TextView content;
        TextView title;
        ImageView icon;
        View itemView;

        public BaseTopicHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            content = itemView.findViewById(R.id.item_content);
            title = itemView.findViewById(R.id.item_title);
            icon = itemView.findViewById(R.id.item_imageView);
        }
    }
    public static class ImageLoadingComplete extends SimpleImageLoadingListener {
        static final List<String> newsList = new ArrayList<>();

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            super.onLoadingComplete(imageUri, view, loadedImage);
            if(loadedImage != null){
                ImageView imageView = (ImageView) view;
                boolean contain = newsList.contains(imageUri);
                if(!contain){
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    newsList.add(imageUri);
                }
            }
        }
    }
}
