package com.future.zhh.ticket.presentation;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.future.zhh.ticket.data.greendao.gen.DaoMaster;
import com.future.zhh.ticket.data.greendao.gen.DaoSession;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.modules.ApplicationModule;
import com.future.zhh.ticket.presentation.utils.SharedPreferencesUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/19.
 */

public class AppApplication extends Application {

    public final static String TAG = AppApplication.class.getSimpleName();
    private static List<Activity> activityList = new LinkedList<Activity>();
    private static List<Service> serviceList = new LinkedList<Service>();
    private ApplicationComponent applicationComponent;
    private SharedPreferencesUtils sharedPreferencesUtils;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static AppApplication instances;

    public static AppApplication get(Context context){
        return (AppApplication)context.getApplicationContext();
    }



    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        instances = this;
        setDatabase();
        init();
    }
    public static AppApplication getInstances(){
        return instances;
    }
    private void init(){
        sharedPreferencesUtils = new SharedPreferencesUtils(this,SharedPreferencesUtils.SHARED_CONFIG);
    }

    private void initializeInjector(){
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    // 添加Activity 到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
        System.out.println("size:"+activityList.size());
    }

    public int getActivitySize(){
        return activityList.size();
    }

    public void removeActivity(Activity activity){
        activityList.remove(activity);
        System.out.println("size:"+activityList.size());
    }

    public void finishActivity(Class clazz){
        for (Activity act : activityList) {
            if(act.getClass().getName()==clazz.getName()){
                act.finish();
            }
        }
    }

    // 添加Service 到容器中
    public void addService(Service service) {
        serviceList.add(service);
    }

    public int getServiceSize(){
        return serviceList.size();
    }

    public void removeService(Service service){
        serviceList.remove(service);
        System.out.println("size:"+serviceList.size());
    }

    public void finishAll(){
        for (Service service : serviceList) {
            service.stopSelf();
        }
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
    }

    // 遍历所有Activity 并finish
    public void exit() {
        for (Service service : serviceList) {
            service.stopSelf();
        }
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
        System.exit(0);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }



}
