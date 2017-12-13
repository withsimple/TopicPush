package com.topic.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.topic.R;
import com.topic.data.Helper;
import com.topic.fragment.NewsFragment;
import com.topic.view.TopView;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TopView topView;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FloatingActionButton floatingActionButton;

    private View.OnClickListener floatListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PopupMenu menu = new PopupMenu(MainActivity.this, floatingActionButton);
            MenuInflater inflater = menu.getMenuInflater();
            inflater.inflate(R.menu.float_bt_menu, menu.getMenu());
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.compile:
                            Intent intent = new Intent(MainActivity.this, CompileActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.experience:
                            Intent intent1 = new Intent(MainActivity.this, ExperienceActivity.class);
                            startActivity(intent1);
                            break;
                    }
                    return true;
                }
            });
            menu.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topView = (TopView) findViewById(R.id.topView);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.float_bt);
        //viewPager.setOffscreenPageLimit(4);

        floatingActionButton.setOnClickListener(floatListener);

        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);

        initTopViewListener();
    }

    private void initTopViewListener() {
        topView.btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(MainActivity.this);
                editText.setHint("格式如：20170809");
                new AlertDialog.Builder(MainActivity.this).setTitle("请填写日期：")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String date = editText.getText().toString();
                        if(date == null || date.trim().equals("")) {
                            Toast.makeText(MainActivity.this, "请填写日期", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        intent.putExtra(Helper.SEARCH_DATE, date);
                        startActivity(intent);
                    }
                })
                .show();
            }
        });


        topView.btTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TopicActivity.class);
                startActivity(intent);
            }
        });

    }

    public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

        public MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            NewsFragment newsFragment = new NewsFragment();

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -position);
            String date = Helper.Dates.format.format(calendar.getTime());

            bundle.putString(Helper.DATE_DAILYNEWS, date);
            bundle.putBoolean(Helper.Is_FIRST_PAGER, position == 0);

            newsFragment.setArguments(bundle);
            return newsFragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -position);

            return DateFormat.getDateInstance().format(calendar.getTime());
        }
    }
}
