package com.topic.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.topic.R;
import com.topic.fragment.TopicFragment;

/**
 * Created by 坚守内心的宁静 on 2017/11/30.
 */

public class TopicActivity extends FragmentActivity{
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_layout);
        tv = findViewById(R.id.topic_tv);

        tv.setAlpha(0.2f);
        tv.setText("Topic DailyNews");
        tv.animate().scaleX(1.3f).scaleY(1.3f).alpha(1).setDuration(1000);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.topic_frame, new TopicFragment());
        transaction.commit();

    }
}
