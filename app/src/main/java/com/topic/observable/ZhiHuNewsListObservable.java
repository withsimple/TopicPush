package com.topic.observable;

import android.text.TextUtils;
import android.util.Log;

import com.topic.data.DailyNews;
import com.topic.data.Helper;
import com.topic.data.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 坚守内心的宁静 on 2018/1/26.
 */

public class ZhiHuNewsListObservable {
    private static final String QUESTION_SELECTOR = "div.question";
    private static final String QUESTION_TITLES_SELECTOR = "h2.question-title";
    private static final String QUESTION_LINKS_SELECTOR = "div.view-more a";

    public static Observable<List<DailyNews>> getZhiHuNewsListObservable(final String date) {
        final List<DailyNews> lists = new ArrayList<>();
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext(Helper.getHtml(date));
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        }).map(new Func1<String, JSONObject>() {
            @Override
            public JSONObject call(String s) {
                try {
                    return new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).map(new Func1<JSONObject, JSONArray>() {
            @Override
            public JSONArray call(JSONObject jsonObject) {
                try {
                    return jsonObject.getJSONArray("stories");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).map(new Func1<JSONArray, List<DailyNews>>() {
            @Override
            public List<DailyNews> call(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String s = Helper.getHtml(object.getInt("id"));
                        String dailyTitle = object.getString("title");

                        Document document = Jsoup.parse(new JSONObject(s).getString("body"));

                        Elements elements = document.select(QUESTION_SELECTOR);

                        List<Question> list = new ArrayList<>();
                        Question question;
                        for (Element element : elements) {
                            question = new Question();

                            String questionTitle = getQuestionTitleFromElement(element);
                            String questionUrl = getQuestionUrlFromElement(element);


                            questionTitle = TextUtils.isEmpty(questionTitle) ? dailyTitle : questionTitle;

                            question.setTitle(questionTitle);

                            question.setUrl(questionUrl);

                            list.add(question);

                        }

                        DailyNews dailyNews = new DailyNews();
                        dailyNews.setThumbnailUrl((String) object.getJSONArray("images").get(0));
                        dailyNews.setDailyTitle(dailyTitle);
                        dailyNews.setQuestions(list);
                        dailyNews.setDate(date);

                        lists.add(dailyNews);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return lists;
            }
        });

    }

    private static String getQuestionUrlFromElement(Element element) {
        Element e = element.select(QUESTION_LINKS_SELECTOR).first();

        if(e == null) {
            return null;
        }else {
            return e.attr("href");
        }
    }

    private static String getQuestionTitleFromElement(Element element) {
        Element e = element.select(QUESTION_TITLES_SELECTOR).first();

        if(e == null) {
            return null;
        }else {
            return e.text();
        }
    }
}
