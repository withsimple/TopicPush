package com.topic.observable;

import android.text.Html;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.topic.data.DailyNews;
import com.topic.data.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by 坚守内心的宁静 on 2017/11/15.
 */

public class NewsListObservable {

    public static Observable<List<DailyNews>> getNewsListObservable(final String date) {
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
        }).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return Html.fromHtml(Html.fromHtml(s).toString()).toString();
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
        }).flatMap(new Func1<JSONObject, Observable<JSONArray>>() {
            @Override
            public Observable<JSONArray> call(final JSONObject jsonObject) {
                return Observable.create(new Observable.OnSubscribe<JSONArray>() {
                    @Override
                    public void call(Subscriber<? super JSONArray> subscriber) {
                        try {
                            subscriber.onNext(jsonObject.getJSONArray("news"));
                            subscriber.onCompleted();
                        } catch (JSONException e) {
                            subscriber.onError(e);
                        }
                    }
                });
            }
        }).map(new Func1<JSONArray, List<DailyNews>>() {
            @Override
            public List<DailyNews> call(JSONArray jsonArray) {
                Gson gson = new GsonBuilder().create();
                return gson.fromJson(jsonArray.toString(), Helper.Types.typeToken);
            }
        });
    }
}
