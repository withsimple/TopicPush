package com.topic.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.topic.R;
import com.topic.data.Helper;
import com.topic.fragment.NewsFragment;

/**
 * Created by 坚守内心的宁静 on 2017/11/21.
 */

public class SearchActivity extends FragmentActivity{
    private FrameLayout frameLayout;
    private TextView dateText;
    String date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        frameLayout = findViewById(R.id.frame_layout);
        dateText = findViewById(R.id.date_tv);

        date = getIntent().getStringExtra(Helper.SEARCH_DATE);

        dateText.setText(date);

        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Helper.DATE_DAILYNEWS, date);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_layout, fragment);
        transaction.commit();
    }
}
