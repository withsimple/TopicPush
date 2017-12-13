package com.topic.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.topic.R;

/**
 * Created by 坚守内心的宁静 on 2017/11/9.
 */

public class TopView extends RelativeLayout {
    String bt_leftText;
    String bt_rightText;
    String titleText;
    int textColor;
    public Button btSearch;
    public Button btTopic;
    TextView title;
    int searchColor;
    int topicColor;

    public TopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopView);
        bt_leftText = typedArray.getString(R.styleable.TopView_bt_left_text);
        bt_rightText = typedArray.getString(R.styleable.TopView_bt_right_text);
        titleText = typedArray.getString(R.styleable.TopView_text);
        textColor = typedArray.getColor(R.styleable.TopView_text_color, 0);
        searchColor = typedArray.getColor(R.styleable.TopView_bt_right_color, 0);
        topicColor = typedArray.getColor(R.styleable.TopView_bt_left_color, 0);

        typedArray.recycle();

        LayoutParams leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LayoutParams rightParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LayoutParams centerParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        btTopic = new Button(context);
        btTopic.setText(bt_leftText);
        btTopic.setBackground(getResources().getDrawable(R.drawable.bt_style));
        btTopic.getBackground().setAlpha(40);
        btTopic.setTextColor(topicColor);
        leftParams.addRule(CENTER_VERTICAL);
        leftParams.addRule(ALIGN_PARENT_LEFT);
        addView(btTopic, leftParams);

        btSearch = new Button(context);
        btSearch.setText(bt_rightText);
        btSearch.setTextColor(searchColor);
        btSearch.setBackground(getResources().getDrawable(R.drawable.bt_style));
        btSearch.getBackground().setAlpha(40);
        rightParams.addRule(CENTER_VERTICAL);
        rightParams.addRule(ALIGN_PARENT_RIGHT);
        addView(btSearch, rightParams);

        title = new TextView(context);
        title.setText(titleText);
        title.setTextColor(textColor);
        title.setTextSize(30f);
        title.setAlpha(0.2f);
        //title.getPaint().setFakeBoldText(true);
        int width = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        title.measure(width, height);
        int textWidth = title.getMeasuredWidth();
        int x = (1024 - textWidth) / 2;
        title.setX(-textWidth);
        title.animate().alpha(1).translationX(x).setDuration(1500);
        centerParams.addRule(CENTER_VERTICAL);
        addView(title, centerParams);


    }

}
