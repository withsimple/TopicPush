package com.topic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topic.DailyNewsApplication;
import com.topic.R;
import com.topic.adapter.TopicAdapter;
import com.topic.data.DailyNews;
import com.topic.data.Helper;
import com.topic.save.DailyNewsDataSource;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 坚守内心的宁静 on 2017/11/30.
 */

public class TopicFragment extends Fragment{
    TopicAdapter adapter;
    List<DailyNews> newsList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_layout, container, false);
        RecyclerView recycler = view.findViewById(R.id.topic_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setHasFixedSize(false);

        adapter = new TopicAdapter(newsList);
        recycler.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DailyNewsDataSource dataSource = DailyNewsApplication.getDailyNewsDataSource();
        newsList = dataSource.theSaveDailyNews(Helper.TOPIC_DATE);
        adapter.updateNewsList(newsList);
    }
}
