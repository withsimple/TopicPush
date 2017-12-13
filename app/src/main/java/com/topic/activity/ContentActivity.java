package com.topic.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.topic.R;
import com.topic.data.Helper;

/**
 * Created by 坚守内心的宁静 on 2017/12/8.
 */

public class ContentActivity extends Activity {
    TextView titleVIew;
    TextView contentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_layout);

        Intent intent = getIntent();
        String title = intent.getStringExtra(Helper.CONTENT_TITLE);
        String content = intent.getStringExtra(Helper.CONTENT_TEXT);

        titleVIew = findViewById(R.id.content_title);
        contentView = findViewById(R.id.content_text);

        titleVIew.setText(title);
        contentView.setText(content);
    }
}
