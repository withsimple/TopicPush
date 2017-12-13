package com.topic.data;

import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by 坚守内心的宁静 on 2017/11/15.
 */

public class Helper {
    public static final String ZHIHU_DAILY_PURIFY_BEFORE = "http://zhihudailypurify.herokuapp.com/news/";

    public static final String DATE_DAILYNEWS = "date_daily_news";

    public static final String SEARCH_DATE = "search_date";

    public static final String TOPIC_COUNT = "topic_count";

    public static final String TOPIC_DATE = "10060708";
    public static final String EXPERIENCE_DATE = "10060709";

    public static final String CONTENT_TITLE = "content_title";
    public static final String CONTENT_TEXT = "content_text";

    public static final String Is_FIRST_PAGER = "is_first_pager";

    public static final class Types {
        public static final Type typeToken = new TypeToken<List<DailyNews>>(){

        }.getType();
    }

    public static final class Dates {
        public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.US);
    }

    public static String getHtml(String date) throws IOException {
        URL url = new URL(ZHIHU_DAILY_PURIFY_BEFORE + date);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
        try {
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String s;
                while ((s = reader.readLine()) != null) {
                    builder.append(s);
                }

                reader.close();
                return builder.toString();
            } else {
                throw new IOException("Network exception" + httpURLConnection.getResponseCode());
            }
        }finally {
            httpURLConnection.disconnect();
        }
    }
}
