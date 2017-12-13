package com.topic.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.topic.DailyNewsApplication;
import com.topic.R;
import com.topic.data.DailyNews;
import com.topic.data.Helper;
import com.topic.data.Question;
import com.topic.save.DailyNewsDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 坚守内心的宁静 on 2017/12/7.
 */

public class CompileActivity extends Activity{
    private EditText edTiTle;
    private EditText edContent;
    private Button submit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compile_layout);
        edTiTle = findViewById(R.id.compile_title);
        edContent = findViewById(R.id.compile_content);
        submit = findViewById(R.id.compile_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edTiTle.getText().toString();
                String content = edContent.getText().toString();

                if(!title.trim().equals("") && !content.trim().equals("")) {
                    DailyNewsDataSource source = DailyNewsApplication.getDailyNewsDataSource();
                    DailyNews dailyNews = new DailyNews();
                    Question question = new Question();
                    question.setTitle(title);
                    List<Question> questionList = new ArrayList<Question>();
                    questionList.add(question);
                    dailyNews.setQuestions(questionList);
                    dailyNews.setDailyTitle(content.substring(0, content.length() > 36 ? 36 : content.length()));
                    dailyNews.setContent(content);
                    source.insertOrUpdateTopicOrExperience(dailyNews, Helper.EXPERIENCE_DATE);
                    Toast.makeText(CompileActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    finish();

                }else {
                    Toast.makeText(CompileActivity.this, "请填写相关信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
