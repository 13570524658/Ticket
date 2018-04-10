package com.future.zhh.ticket.presentation.internal.dl.modules;

import android.app.Activity;

import com.future.zhh.ticket.presentation.internal.dl.PerActivity;
import com.future.zhh.ticket.presentation.utils.JsonSerializerUtil;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/11/19.
 */
@Module
public class ActivityModule {
    private final Activity activity;
    public ActivityModule(Activity activity) {
        this.activity = activity;
    }
    @Provides
    @PerActivity
    Activity activity(){
        return this.activity;
    }

    @Provides
    @PerActivity
    JsonSerializerUtil provideJsonSerializer(){
        return new JsonSerializerUtil();
    }

}
