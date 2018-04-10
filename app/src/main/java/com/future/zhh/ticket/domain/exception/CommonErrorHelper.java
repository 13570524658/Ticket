package com.future.zhh.ticket.domain.exception;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.future.zhh.ticket.presentation.ApplicationDatas;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Administrator on 2017/11/19.
 */

public class CommonErrorHelper {
    public static void handleCommonError(Context context, Throwable e) {
        String type = "";
        if(ApplicationDatas.CONNECT_TYPE == 1){
            type+="监管平台:";
        }
        if(e instanceof SocketTimeoutException) {
            Toast.makeText(context, type+"连接超时，请重试!", Toast.LENGTH_SHORT).show();
        } else if(e instanceof IndexOutOfBoundsException){
            Toast.makeText(context, type+"返回数据出错，请重试!", Toast.LENGTH_SHORT).show();
        }else if (e instanceof HttpException) {
            Toast.makeText(context, type+"Request Failed,HttpException!", Toast.LENGTH_SHORT).show();
            e.printStackTrace() ;
        } else if (e instanceof IOException) {
            Toast.makeText(context, type+"Request Failed,IOException!", Toast.LENGTH_SHORT).show();
            e.printStackTrace() ;
        } else{
            Toast.makeText(context, type+"请求失败，请重试!", Toast.LENGTH_SHORT).show();
            e.printStackTrace() ;
        }
    }
}
