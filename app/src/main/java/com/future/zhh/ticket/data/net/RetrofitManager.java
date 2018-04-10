package com.future.zhh.ticket.data.net;

import android.content.Context;

import com.future.zhh.ticket.data.net.intercepter.CacheInterceptor;
import com.future.zhh.ticket.data.net.intercepter.CustomHeaderInterceptor;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.File;
import java.lang.reflect.Type;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2017/11/19.
 */

public class RetrofitManager {

    private Context context;

    public static String URL;

    private static OkHttpClient mOkHttpClient;
    private final Retrofit retrofit;
    private NetUtil netUtil;


    public static RetrofitManager builder(Context context,boolean isScurity){
        return new RetrofitManager(context,isScurity);
    }

    public static RetrofitManager builder(Context context,int convertString){
        return new RetrofitManager(context,convertString);
    }

    public static RetrofitManager builder(Context context){
        return new RetrofitManager(context);
    }

    private RetrofitManager(Context context) {
        this.context = context;
        netUtil = new NetUtil(context);
        initOkHttpClient();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT
                    , JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        URL = ApplicationDatas.BASE_URL;
        ApplicationDatas.CONNECT_TYPE = 0;
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build();
    }

    private RetrofitManager(Context context,boolean isScurity) {
        this.context = context;
        netUtil = new NetUtil(context);
        initOkHttpClient();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT
                    , JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        if (isScurity) {
            URL = ApplicationDatas.SUPERVISION_BASE_URL;
            ApplicationDatas.CONNECT_TYPE = 1;
        }else{
            ApplicationDatas.CONNECT_TYPE = 0;
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build();
    }

    private RetrofitManager(Context context,int convertString) {
        this.context = context;
        netUtil = new NetUtil(context);
        initOkHttpClient();
        URL = ApplicationDatas.SUPERVISION_BASE_URL;
        ApplicationDatas.CONNECT_TYPE = 1;
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }


    private void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        CookieHandler cookieHandler = new CookieManager(
                new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (mOkHttpClient == null) {
                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(context.getCacheDir(), "HttpCache"),
                            1024 * 1024 * 100);
                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .cookieJar(new JavaNetCookieJar(cookieHandler))
                            .addInterceptor(new CacheInterceptor(netUtil))
                            .addNetworkInterceptor(new CacheInterceptor(netUtil))
                            .addInterceptor(interceptor)
                            .addInterceptor(new CustomHeaderInterceptor(context))
                            .retryOnConnectionFailure(true)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(20,TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }

    public OkHttpClient getOkHttpClient(){
        return mOkHttpClient;
    }
}
