package com.topic.data;

import java.util.List;

/**
 * Created by 坚守内心的宁静 on 2017/11/14.
 */

public class DailyNews {
    private String date;
    private String thumbnailUrl;
    private List<Question> questions;
    private String dailyTitle;
    private boolean isSelect = false;

    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public String getDate() {
        return date;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getDailyTitle() {
        return dailyTitle;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setDailyTitle(String dailyTitle) {
        this.dailyTitle = dailyTitle;
    }
}
