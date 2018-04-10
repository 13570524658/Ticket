package com.future.zhh.ticket.presentation;

/**
 * Created by Administrator on 2017/11/19.
 */

public class ApplicationDatas {

    //app名称
    public final static String APP_NAME = "Ticket Store";

    //监管平台url
    public final static String SUPERVISION_BASE_URL = "http://121.8.226.249/";

    //baseurl
//    public final static String BASE_URL = "http://app.wlticket.cn/";
    //王斌
//    public final static String BASE_URL ="http://192.168.1.119:8080/Ticket/";

    //postMan
    public final static String BASE_URL="http://192.168.1.218:9999/";
//    曾胜辉
//    public final static String BASE_URL="http://192.168.1.159:8080/";



    //是否监管链接
    public static final boolean CONNECT_SUPERVISION = false;
    //是否调试
    public static boolean IS_DEBUG = true;
    //是否保存日志
    public static boolean IS_SAVE_LOG = false;
    //0:普通，1:监管平台
    public static int CONNECT_TYPE = 0;
    //日志等级
    public static final int LOG_LEVEL = android.util.Log.ERROR;

    //二维码扫码功能标签
    public final static String TAP_FROM = "tapFrom";
    //记录气瓶二维码标签
    public final static String QR_CODE = "qrCode";
    //详细标记
    public final static int TAP_FROM_HOME_BUTTON = 0;
    //登录标记
    public final static int TAP_FROM_LOGIN = 1;
    //配送任务交付标记
    public final static int TAP_FROM_JF = 2;
    //配送任务回收标记
    public final static int TAP_FROM_HS = 3;
    //交付回收标记
    public final static int TAP_FROM_JF_AND_HS = 4;
    //订单标记
    public final static int TAP_FROM_ORDER = 5;
    //用户卡标记
    public final static int TAP_FROM_CUSTOMER = 6;
    //气瓶建档扫码
    public final static int TAP_FROM_INPUTTING=7;
    //气瓶建档修改气瓶标签扫码
    public final static int TAP_FROM_CHANGE_LABEL= 8;
    //自提扫用户卡订单页面交付回收
    public final static int TAP_FROM_SINCE_JFHS = 9;

    //自提订单扫气瓶交付标记
    public final static int TAP_FROM_SINCE_JF = 10;
    //自提订单扫气瓶回收标记
    public final static int TAP_FROM_SINCE_HS = 11;


    //入户安全检查扫用户卡或者订单号
    public final static int TAP_FROM_CHECK = 12;
    //入户安全检查扫气瓶
    public final static int TAP_FROM_CHECK_SCAN_GAS = 13;


    //登录
    public static final String LOGIN_BY_PASSWORD = "loginByPassword";

//    public static final String LOGIN_BY_PASSWORD = "Ticket/login";
    //二维码登录
    public static final String LOGIN_BY_QR_CODE = "loginByQrCode";

    //获取车辆列表
    public static final String CAR_NP_LIST = "carNoList";
    //锁定选中的车辆
    public static final String ELECTCARNO = "electCarNo";

    //获取气瓶信息
    public static final String GAS_MSG_BY_QR_CODE = "gasMsgByQrCode";
    //获取客户信息
    public static final String CUSTOMER_MSG_BY_QR_CODE = "customerMsgByQrCode";
    //获取从业人员信息
    public static final String WORKER_MSG_BY_QR_CODE = "workerMsgByQrCode";
    //获取车辆信息
    public static final String CAR_MSG_BY_QR_CODE = "carMsgByQrCode";
    //获取订单信息
    public static final String ORDER_MSG_BY_QR_CODE = "orderMsgByQrCode";

    //获取配送任务列表
    public static final String TRANSPORT_TAKE_LIST = "transportTaskList";
    //配送任务明细
    public static final String TRANSPORT_TAKE_DETAIL = "transportTaskDetail";
    //配送任务订单编号查询订单详细
    public static final String ORDER_MSG_BY_ORDER_ID = "orderMsgByOrderID";
    //配送任务客户编号查询客户详细
    public static final String CUSTOMER_MSG_BY_CYSTONER_ID = "customerMsgByCustomerID";
    //配送任务气瓶标签查询气瓶详细
    public static final String GAS_MSG_BY_LABEL = "gasMsgByLabel";
    //配送任务交付
    public static final String TRANSPORT_OF_PAY = "transportOfPay";
    //配送任务回收
    public static final String TRANSPORT_OF_RECLAIM = "transportOfReclaim";
    //在线搜索配送任务
    public static final String TRANSPORT_ID_EQ_TASK = "transportIdEqTask";
    //配送任务扫用户卡
    public static final String TRANSPORT_BY_CUSTOMER_CARD_NO = "transportByCustomerCardNo";
    //配送任务扫订单卡
    public static final String TRANSPORT_BY_ORDER_QR_CODE = "transportByOrderQrCode";

    //建档气瓶型号
    public static final String GAD_MODULE="bottleInfoApp/gasModule";
    //气瓶信息建档
    public static final String GAS_MSG_CHANG="bottleInfoApp/gasMsgChange";
    //气瓶信息建档
    public static final String GAS_LABEL_EQ_LOG="gasLabeEqLog";
    //气瓶建档扫气瓶
    public static final String SET_UP_SCAN="setUpScan";
    //更换气瓶标签
    public static final String GAS_LABEL_REPLACE="gasLabeReplace";
    //气瓶建档记录
    public static final String GAS_SET_UP_LOG="bottleInfoApp/gasSetUpLog";

    //自提扫订单
    public static final String SINCE_BY_ORDER="sinceByOder";
    //自提扫用户卡
    public static final String SINCE_BY_CUSTOMER="sinceByCustomer";
    //自提交付
    public static final String SINCE_OF_PAY="sinceOfPay";
    //自提回收
    public static final String SINCE_OF_RECLAIM="sinceOfReclaim";
    //自提历史记录
    public static final String SINCE_LOG="sinceLog";
    //自提在线搜索
    public static final String SINCE_EQ_LOG="sinceEqLog";
    //自提在线搜索
    public static final String SINCE_TASK_MSG="sinceTaskMsg";


    //入户检查用户信息 扫用户卡
    public static final String CHECK_BY_CUSTOMER_CARD_NO="checkByCustomercardNo";
    //入户检查用户信息 扫订单卡
    public static final String CHECK_BY_ORDER="checkByOrder";
    //入户检查热水器类型
    public static final String HOT_WATER_HEATER_MODULE="hotWaterHeaterModule";
    //入户检查提交
    public static final String CHECK_SUBMIT="checkSubmit";
    //入户检查记录
    public static final String CHECK_LOG="checkLog";
    //入户检查在线搜索
    public static final String CHECK_LOG_BY_SEARCH="checkLogBySearch";
    //入户检查记录明细
    public static final String CHECK_LOG_BY_SEARCH_DETAIL="checkLogDetail";
}
