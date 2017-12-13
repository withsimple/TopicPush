package com.topic.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.topic.DailyNewsApplication;
import com.topic.R;
import com.topic.adapter.ExperienceAdapter;
import com.topic.data.DailyNews;
import com.topic.data.Helper;

import java.util.List;

/**
 * Created by 坚守内心的宁静 on 2017/12/7.
 */

public class ExperienceActivity extends Activity {
    private RecyclerView recycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experience_layout);
        recycler = findViewById(R.id.experience_recycler);
        recycler.setHasFixedSize(false);

        List<DailyNews> newsList = DailyNewsApplication.getDailyNewsDataSource().theSaveDailyNews(Helper.EXPERIENCE_DATE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);
        ExperienceAdapter adapter = new ExperienceAdapter(newsList);
        recycler.setAdapter(adapter);
    }
}
