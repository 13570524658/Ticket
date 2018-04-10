package com.future.zhh.ticket.presentation.utils;

/**
 * Created by Administrator on 2017/11/23.
 * 判断二维码类型工具类
 *
 *
 * 字段编号规则：
 * 产品规格编号 五位数，2+后面五位数，如200001
 * 销售产品编号 五位数，1+后缀五位数，如10001
 * 租金单编号 11位数，317+后8位随机数，如31712345678
 * 押金单编号 11位数，217+后8位随机数，如21712345678
 * 结算记录编号 年月日+后缀4位编号：如1711170001
 *
 * 员工编号：6位数，前缀3+后面5位后缀，如300001
 * 订单编号：11位数，117+后面8位随机数，如11712345678
 * 配送任务编号：根据订单号，订单号+后缀-1，如11712345678-1，11712345678-2
 * 客户编号：7位数，前缀4+后面6位后缀，如4000001
 * 用户卡编号：10位数，前缀２+后面９位随机数，如2123456789
 * 气瓶标签：10位数，前缀１+后面９位随机数，如1123456789
 * 车辆编号：6位数，前缀５+后面5位后缀，如500001
 *
 * 二维码编号规则：全部加密
 *
 * 用户二维码：用户卡编号
 * 员工二维码：气站ID+员工ID
 * 订单二维码：气站ID+员工ID
 * 气瓶二维码：气瓶标签号
 * 
 * 气站ID是UUID
 */

public class CardTypeUtils {
    /**
     *用户卡二维码
     */
    public static Boolean isCustomerCard(String result) {
        String type = result.substring(0,1);
        if (result.length()==10){
            if(type.equals("2")){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    /**
     *气瓶标签二维码
     */
    public static Boolean isGasLabelCard(String result) {
        String type = result.substring(0,1);
        if (result.length()==10){
            if(type.equals("1")){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
    /**
     *车辆二维码
     */
    public static Boolean isCarCard(String result) {
        String type = result.substring(0,1);
        if (result.length()==6){
            if(type.equals("5")){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
    /**
     *员工二维码
     */
    public static Boolean isWorkerCard(String result) {
        String type = result.substring(0, 1);
        if (result.length()==6){
            if(type.equals("3")){
              return true;
            }else {
                return false;
            }
        }
        return false;
    }

    /**
     *订单二维码
     */
    public static Boolean isOrderCard(String result) {
        String type = result.substring(0, 3);
        if (result.length()==11){
            if(type.equals("117")){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }







}
