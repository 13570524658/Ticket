package com.future.zhh.ticket.presentation.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/11/19.
 */

public class SharedPreferencesUtils {
    /***
     * config shared preferences
     */
    public static final String SHARED_USER_INFO = "userInfo";
    //配置
    public static final String SHARED_CONFIG = "config";
    //是否第一次启动
    public static final String SHARED_IS_FIRST_STARTUP = "isFirstStartUp";
    //登录token
    public static final String SHARED_LOGIN_TOKEN = "loginToken";
    //是否已登录
    public static final String SHARED_IS_LOGINED = "isLogined";


    //登录的qrCode
    public static final String SHARED_USER_QR_CODE = "userQrcode";
    //登录的账号
    public static final String SHARED_USER_ID = "userID";
    //登录的密码
    public static final String SHARED_PASSWORD= "password";
    //APP用户名称
    public static final String SHARED_USER_NAME = "userName";
    //APP用户ID
    public static final String SHARED_ID = "id";
    //APP用户岗位
    public static final String SHARED_IS_ADMIN = "isAdmin";
    //APP气站名称
    public static final String SHARED_GAS_STATION_NAME = "gasStationName";
    //APP用户二维码图
    public static final String SHARED_USER_QR_CODE_IMAGE = "userQrCodeImage";



    //企业ID
    public static final String SHARED_ENTERPRISE_ID = "enterPriseID";
    //气站ID
    public static final String SHARED_STATION_ID = "stationID";
    //门店ID
    public static final String SHARED_SHOP_ID = "shopID";
    //部门ID
    public static final String SHARED_DEP_ID = "depID";

    public static final String SHARED_SCRAPPING_GAS_TOTAL="scrappingGasTotal";
    public static final String SHARED_OUT_TIME_GAS_TOTAL="outTimeGasTotal";


    private SharedPreferences sharedpreferences;
    private Context mContext;


    public SharedPreferencesUtils(Context context) {
        mContext = context;
        sharedpreferences = mContext.getSharedPreferences(SHARED_CONFIG,
                Context.MODE_PRIVATE);
    }

    public SharedPreferencesUtils(Context context, String name) {
        mContext = context;
        sharedpreferences = mContext.getSharedPreferences(name,
                Context.MODE_PRIVATE);
    }




    public boolean saveSharedPreferences(String key, String value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public String loadStringSharedPreference(String key) {
        String str = null;
        str = sharedpreferences.getString(key, null);
        return str;
    }

    public String loadStringSharedPreference(String key, String preKey) {
        String str = null;
        str = sharedpreferences.getString(key, preKey);
        return str;
    }

    public boolean saveSharedPreferences(String key, int value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public int loadIntSharedPreference(String key) {
        return sharedpreferences.getInt(key, 0);
    }

    public boolean saveSharedPreferences(String key, float value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public float loadFloatSharedPreference(String key) {
        return sharedpreferences.getFloat(key, 0f);
    }

    public boolean saveSharedPreferences(String key, Long value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public long loadLongSharedPreference(String key) {
        return sharedpreferences.getLong(key, 0);
    }

    public boolean saveSharedPreferences(String key, Boolean value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public boolean loadBooleanSharedPreference(String key) {
        return sharedpreferences.getBoolean(key, false);
    }

    public boolean saveSharedPreferences(String key, Set<String> set){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putStringSet(key,set);
        return editor.commit();
    }

    public Set<String> loadSetSharedPreference(String key){
        return sharedpreferences.getStringSet(key,null);

    }

    public boolean saveAllSharePreference(String keyName, List<?> list) {
        int size = list.size();
        if (size < 1) {
            return false;
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if (list.get(0) instanceof String) {
            for (int i = 0; i < size; i++) {
                editor.putString(keyName + i, (String) list.get(i));
            }
        } else if (list.get(0) instanceof Long) {
            for (int i = 0; i < size; i++) {
                editor.putLong(keyName + i, (Long) list.get(i));
            }
        } else if (list.get(0) instanceof Float) {
            for (int i = 0; i < size; i++) {
                editor.putFloat(keyName + i, (Float) list.get(i));
            }
        } else if (list.get(0) instanceof Integer) {
            for (int i = 0; i < size; i++) {
                editor.putLong(keyName + i, (Integer) list.get(i));
            }
        } else if (list.get(0) instanceof Boolean) {
            for (int i = 0; i < size; i++) {
                editor.putBoolean(keyName + i, (Boolean) list.get(i));
            }
        }
        return editor.commit();
    }

    public Map<String, ?> loadAllSharePreference() {
        return sharedpreferences.getAll();
    }

    public boolean removeKey(String key) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(key);
        return editor.commit();
    }

    public boolean removeAllKey() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        return editor.commit();
    }

    public void setPreferences(String name) {
        sharedpreferences = mContext.getSharedPreferences(name,
                Context.MODE_PRIVATE);
    }
}

