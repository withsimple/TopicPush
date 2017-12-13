package com.topic.save;

import android.os.AsyncTask;

import com.google.gson.GsonBuilder;
import com.topic.DailyNewsApplication;
import com.topic.data.DailyNews;

import java.util.List;

/**
 * Created by 坚守内心的宁静 on 2017/11/19.
 */

public class SaveNewsList extends AsyncTask<Void, Void, Void>{
    List<DailyNews> newsList;

    public SaveNewsList(List<DailyNews> newsList) {
        this.newsList = newsList;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if(newsList != null && newsList.size() > 0) {
            saveNewsList(newsList);
        }
        return null;
    }

    private void saveNewsList(List<DailyNews> newsList) {
        DailyNewsDataSource source = DailyNewsApplication.getDailyNewsDataSource();
        String date = newsList.get(0).getDate();
        String size = newsList.size() + "";
        source.insertOrUpdateDailyNews(date, new GsonBuilder().create().toJson(newsList), size);
    }

}
