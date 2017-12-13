package com.topic.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.topic.DailyNewsApplication;
import com.topic.activity.ContentActivity;
import com.topic.data.DailyNews;
import com.topic.data.Helper;
import com.topic.save.DailyNewsDataSource;

import java.util.List;

/**
 * Created by 坚守内心的宁静 on 2017/12/8.
 */

public class ExperienceAdapter extends BaseTopicAdapter {

    public ExperienceAdapter(List<DailyNews> newsList) {
        this.newsList = newsList;
    }

    @Override
    protected void bindTopicListener(BaseTopicHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = newsList.get(position).getQuestions().get(0).getTitle();
                String content = newsList.get(position).getContent();
                Intent intent = new Intent(context, ContentActivity.class);
                intent.putExtra(Helper.CONTENT_TITLE, title);
                intent.putExtra(Helper.CONTENT_TEXT, content);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String title = "";
                String experienceTitle =  newsList.get(position).getQuestions().get(0).getTitle();
                if(experienceTitle.length() > 22) {
                    title = "是否删除“" + experienceTitle.substring(0, 21) + "...”话题?";
                }else {
                    title = "是否删除“" + experienceTitle + "”话题?";
                }

                new AlertDialog.Builder(context).setTitle(title)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DailyNewsDataSource source = DailyNewsApplication.getDailyNewsDataSource();
                                List<DailyNews> newsList = source.deleteTopicOrExperience(position, Helper.EXPERIENCE_DATE);
                                updateNewsList(newsList);
                            }
                        }).show();
                return true;
            }
        });

    }
}
