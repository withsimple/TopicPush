package com.topic.save;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.style.UpdateAppearance;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.topic.data.DailyNews;
import com.topic.data.Helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 坚守内心的宁静 on 2017/11/19.
 */

public class DailyNewsDataSource {
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    private String[] allColumns = {
            DBHelper.COLUMN_DATE,
            DBHelper.COLUMN_CONTENT
    };

    public DailyNewsDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void openDatabase() {
        database = dbHelper.getWritableDatabase();
    }

    public void insertDailyNewsList(String date, String content) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_DATE, date);
        values.put(DBHelper.COLUMN_CONTENT, content);

        database.insert(DBHelper.TABLE_NAME, null, values);
    }

    public void updateDailyNewsList(String date, String content) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_DATE, date);
        values.put(DBHelper.COLUMN_CONTENT, content);

        database.update(DBHelper.TABLE_NAME, values, DBHelper.COLUMN_DATE + " = " + date, null);
    }

    public void insertOrUpdateDailyNews(String date, String content, String newsSize) {
        List<DailyNews> dataList = theSaveDailyNews(date);

        String dataSize = "0";
        if(dataList != null) {
            dataSize = dataList.size() + "";
        }

        if(dataList == null) {
            insertDailyNewsList(date, content);
        }else if(dataList != null && !dataSize.equals(newsSize)) {
            updateDailyNewsList(date, content);
        }
    }

    public void insertOrUpdateTopicOrExperience(DailyNews dailyNews, String date) {
        List<DailyNews> dataList = theSaveDailyNews(date);
        if(dataList == null) {
            dataList = new ArrayList<>();
            dataList.add(dailyNews);
            insertDailyNewsList(date, new GsonBuilder().create().toJson(dataList));
        }else{
            dataList.add(dailyNews);
            updateDailyNewsList(date, new GsonBuilder().create().toJson(dataList));
        }
    }

    public List<DailyNews> theSaveDailyNews(String date) {
        Cursor cursor = database.query(DBHelper.TABLE_NAME, allColumns, DBHelper.COLUMN_DATE + "=" + date, null, null, null, null);

        cursor.moveToFirst();
        List<DailyNews> newsList = toDailyNews(cursor);
        cursor.close();
        return newsList;
    }

    private List<DailyNews> toDailyNews(Cursor cursor) {
        if(cursor != null && cursor.getCount() > 0) {
            return new GsonBuilder().create().fromJson(cursor.getString(1), Helper.Types.typeToken);
        }else {
            return null;
        }
    }

    public List<DailyNews>  deleteTopicOrExperience(int position, String date){
        List<DailyNews> newsList = theSaveDailyNews(date);
        newsList.remove(position);
        updateDailyNewsList(date, new GsonBuilder().create().toJson(newsList));
        return newsList;
    }
}
