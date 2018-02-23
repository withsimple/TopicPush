package com.topic.adapter;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.annimon.stream.Stream;
import com.google.gson.GsonBuilder;
import com.topic.DailyNewsApplication;
import com.topic.data.DailyNews;
import com.topic.data.Helper;
import com.topic.data.Question;
import com.topic.save.DailyNewsDataSource;

import java.util.List;

/**
 * Created by 坚守内心的宁静 on 2017/11/14.
 */

public class NewsAdapter extends BaseTopicAdapter {

    public NewsAdapter(List<DailyNews> newsList,String date) {
        this.newsList = newsList;
        setHasStableIds(true);
    }

    @Override
    protected void bindTopicListener(BaseTopicHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DailyNews dailyNews = newsList.get(position);
                dailyNews.setSelect(true);
                DailyNewsApplication.getDailyNewsDataSource().updateDailyNewsList(dailyNews.getDate(), new GsonBuilder().create().toJson(newsList));

                if(dailyNews.getQuestions().size() > 1) {
                    String[] questions = Stream.of(dailyNews.getQuestions()).map(Question::getTitle).toArray(String[]::new);
                    new AlertDialog.Builder(context)
                            .setTitle(dailyNews.getDailyTitle())
                            .setItems(questions, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String url = dailyNews.getQuestions().get(i).getUrl();
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    context.startActivity(intent);
                                }
                            }).create().show();

                }else {
                    String url = dailyNews.getQuestions().get(0).getUrl();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(intent);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final DailyNews dailyNew = newsList.get(position);
                String topicTitle = dailyNew.getQuestions().get(0).getTitle();
                String title = "";
                if(topicTitle.length() > 22) {
                    title = "是否添加话题“" + topicTitle.substring(0, 21) + "...”?";
                }else {
                    title = "是否添加话题“" + topicTitle + "”?";
                }

                new AlertDialog.Builder(context).setTitle(title)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DailyNewsDataSource source = DailyNewsApplication.getDailyNewsDataSource();
                                source.insertOrUpdateTopicOrExperience(dailyNew, Helper.TOPIC_DATE);
                            }
                        }).show();
                return true;
            }
        });
    }
}
