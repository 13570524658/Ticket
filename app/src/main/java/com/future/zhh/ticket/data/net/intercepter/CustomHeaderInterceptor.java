package com.future.zhh.ticket.data.net.intercepter;

import android.content.Context;

import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/19.
 */

public  class CustomHeaderInterceptor implements Interceptor {
    private Context mContext;
    private SharedPreferencesUtils sharedPreferencesUtils;

    public CustomHeaderInterceptor(Context mContext){
        this.mContext = mContext;
        sharedPreferencesUtils = new SharedPreferencesUtils(mContext,SharedPreferencesUtils.SHARED_USER_INFO);
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();
        String token = sharedPreferencesUtils.loadStringSharedPreference(SharedPreferencesUtils.SHARED_LOGIN_TOKEN);
        Request.Builder requestBuilder = original.newBuilder()
                .header("charset", "utf-8")
                .header("Cookie", "JSESSIONID=" + token);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
};
