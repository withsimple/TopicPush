package com.topic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.topic.DailyNewsApplication;
import com.topic.R;
import com.topic.adapter.NewsAdapter;
import com.topic.data.DailyNews;
import com.topic.data.Helper;
import com.topic.observable.NewsListObservable;
import com.topic.save.DailyNewsDataSource;
import com.topic.save.SaveNewsList;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by 坚守内心的宁静 on 2017/11/14.
 */

public class NewsFragment extends Fragment implements Observer<List<DailyNews>>, SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout refresh;
    private RecyclerView recycler;

    private NewsAdapter adapter;
    String date;

    private boolean isToday;

    private List<DailyNews> newsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            Bundle bundle = getArguments();
            date = bundle.getString(Helper.DATE_DAILYNEWS);
            isToday = bundle.getBoolean(Helper.Is_FIRST_PAGER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_layout, container, false);

        refresh = view.findViewById(R.id.refresh_fragment);
        recycler = view.findViewById(R.id.recyclers_fragment);
        recycler.setHasFixedSize(!isToday);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);


        adapter = new NewsAdapter(newsList);
        recycler.setAdapter(adapter);

        refresh.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DailyNewsDataSource dataSource = DailyNewsApplication.getDailyNewsDataSource();

        List<DailyNews> dataNewsList = dataSource.theSaveDailyNews(date);
        if(dataNewsList != null) {
            newsList = dataNewsList;
            adapter.updateNewsList(newsList);
        }else{
            loadTopic();
        }
    }

    private void loadTopic(){
        if(refresh != null) {
            refresh.setRefreshing(true);
        }
        NewsListObservable.getNewsListObservable(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onCompleted() {
        refresh.setRefreshing(false);
        adapter.updateNewsList(newsList);

        new SaveNewsList(newsList).execute();
    }

    @Override
    public void onError(Throwable e) {
        refresh.setRefreshing(false);
        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNext(List<DailyNews> dailyNewses) {
        newsList = dailyNewses;
    }

    @Override
    public void onRefresh() {
        doRefresh();
    }

    private void doRefresh() {
        loadTopic();
    }
}
