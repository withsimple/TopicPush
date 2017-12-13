package com.topic;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.topic.save.DailyNewsDataSource;

/**
 * Created by 坚守内心的宁静 on 2017/11/14.
 */

public class DailyNewsApplication extends Application{
    private static DailyNewsDataSource dataSource;

    public static DailyNewsDataSource getDailyNewsDataSource() {
        return dataSource;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initImageLoader();

        dataSource = new DailyNewsDataSource(getApplicationContext());
        dataSource.openDatabase();

    }

    public void initImageLoader() {
        ImageLoader imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .denyCacheImageMultipleSizesInMemory()
                .threadPriority(Thread.NORM_PRIORITY-2)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        imageLoader.init(configuration);
    }


}
