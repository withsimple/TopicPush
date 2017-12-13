package com.topic.adapter;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.google.gson.GsonBuilder;
import com.topic.DailyNewsApplication;
import com.topic.data.DailyNews;
import com.topic.data.Helper;
import com.topic.save.DailyNewsDataSource;

import java.util.List;

/**
 * Created by 坚守内心的宁静 on 2017/11/30.
 */

public class TopicAdapter extends BaseTopicAdapter{

    public TopicAdapter(List<DailyNews> newsList) {
        this.newsList = newsList;
    }

    @Override
    protected void bindTopicListener(BaseTopicHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DailyNews dailyNews = newsList.get(position);
                dailyNews.setSelect(true);
                DailyNewsApplication.getDailyNewsDataSource().updateDailyNewsList(Helper.TOPIC_DATE, new GsonBuilder().create().toJson(newsList));
                String url = newsList.get(position).getQuestions().get(0).getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String title = "";
                String topicTitle =  newsList.get(position).getQuestions().get(0).getTitle();
                if(topicTitle.length() > 20) {
                    title = "是否删除“" + topicTitle.substring(0, 19) + "...”话题?";
                }else {
                    title = "是否删除“" + topicTitle + "”话题?";
                }

                new AlertDialog.Builder(context).setTitle(title)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DailyNewsDataSource source = DailyNewsApplication.getDailyNewsDataSource();
                                List<DailyNews> newsList = source.deleteTopicOrExperience(position, Helper.TOPIC_DATE);
                                updateNewsList(newsList);
                            }
                        }).show();
                return true;
            }
        });
    }
}
