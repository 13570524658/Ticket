package com.future.zhh.ticket.presentation.utils;

import android.os.Environment;
import android.util.Log;

import com.future.zhh.ticket.presentation.ApplicationDatas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017/11/19.
 */

public class CommonLog {
    private String tag = ApplicationDatas.APP_NAME; // 一般都是项目名称
    public static int logLevel = ApplicationDatas.LOG_LEVEL; // 默认android.util.Log.VERBOSE;
    public static boolean isDebug = ApplicationDatas.IS_DEBUG;// 默认 true
    public static boolean isSaveLog = ApplicationDatas.IS_SAVE_LOG;
    protected static final String PATH_ROOT = Environment
            .getExternalStorageDirectory() + "/" + ApplicationDatas.APP_NAME + "/log/";

    public CommonLog() {
    }

    public CommonLog(String tag) {
        this.tag = tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    private String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();

        if (sts == null) {
            return null;
        }

        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }

            if (st.getClassName().equals(this.getClass().getName())) {
                continue;
            }

            return "[" + Thread.currentThread().getId() + ": "
                    + st.getFileName() + ":" + st.getLineNumber() + "]";
        }

        return null;
    }

    private void info(Object str) {
        if (logLevel <= Log.INFO) {
            String name = getFunctionName();
            String ls = (name == null ? str.toString() : (name + " - " + str));
            Log.i(tag, ls);
        }
    }

    private void infoSave(Object str) {
        if (logLevel <= Log.INFO) {
            String name = getFunctionName();
            String ls = (name == null ? str.toString() : (name + " - " + str));
            savePoint(tag, ls);
        }
    }

    public void i(Object str) {
        if (isDebug) {
            info(str);
        }
        if (isSaveLog) {
            infoSave(str);
        }
    }

    private void verbose(Object str) {
        if (logLevel <= Log.VERBOSE) {
            String name = getFunctionName();
            String ls = (name == null ? str.toString() : (name + " - " + str));
            Log.v(tag, ls);
        }
    }

    private void verboseSave(Object str) {
        if (logLevel <= Log.VERBOSE) {
            String name = getFunctionName();
            String ls = (name == null ? str.toString() : (name + " - " + str));
            savePoint(tag, ls);
        }
    }

    public void v(Object str) {
        if (isDebug) {
            verbose(str);
        }
        if (isSaveLog) {
            verboseSave(str);
        }
    }

    private void warn(Object str) {
        if (logLevel <= Log.WARN) {
            String name = getFunctionName();
            String ls = (name == null ? str.toString() : (name + " - " + str));
            Log.w(tag, ls);
        }
    }

    private void warnSave(Object str) {
        if (logLevel <= Log.WARN) {
            String name = getFunctionName();
            String ls = (name == null ? str.toString() : (name + " - " + str));
            savePoint(tag, ls);
        }
    }

    public void w(Object str) {
        if (isDebug) {
            warn(str);
        }
        if (isSaveLog) {
            warnSave(str);
        }
    }

    private void error(Object str) {
        if (logLevel <= Log.ERROR) {
            String name = getFunctionName();
            String ls = (name == null ? str.toString() : (name + " - " + str));
            Log.e(tag, ls);
        }
    }

    private void errorSave(Object str) {
        if (logLevel <= Log.ERROR) {
            String name = getFunctionName();
            String ls = (name == null ? str.toString() : (name + " - " + str));
            savePoint(tag, ls);
        }
    }

    private void error(Exception ex) {
        if (logLevel <= Log.ERROR) {
            StringBuffer sb = new StringBuffer();
            String name = getFunctionName();
            StackTraceElement[] sts = ex.getStackTrace();

            if (name != null) {
                sb.append(name + " - " + ex + "\r\n");
            } else {
                sb.append(ex + "\r\n");
            }

            if (sts != null && sts.length > 0) {
                for (StackTraceElement st : sts) {
                    if (st != null) {
                        sb.append("[ " + st.getFileName() + ":"
                                + st.getLineNumber() + " ]\r\n");
                    }
                }
            }

            Log.e(tag, sb.toString());
        }
    }

    private void errorSave(Exception ex) {
        if (logLevel <= Log.ERROR) {
            StringBuffer sb = new StringBuffer();
            String name = getFunctionName();
            StackTraceElement[] sts = ex.getStackTrace();

            if (name != null) {
                sb.append(name + " - " + ex + "\r\n");
            } else {
                sb.append(ex + "\r\n");
            }

            if (sts != null && sts.length > 0) {
                for (StackTraceElement st : sts) {
                    if (st != null) {
                        sb.append("[ " + st.getFileName() + ":"
                                + st.getLineNumber() + " ]\r\n");
                    }
                }
            }

            savePoint(tag, sb.toString());
        }
    }

    public void e(Object str) {
        if (isDebug) {
            error(str);
        }
        if (isSaveLog) {
            errorSave(str);
        }
    }

    public void e(Exception ex) {
        if (isDebug) {
            error(ex);
        }
        if (isSaveLog) {
            errorSave(ex);
        }
    }

    private void debug(Object str) {
        if (logLevel <= Log.DEBUG) {
            String name = getFunctionName();
            String ls = (name == null ? str.toString() : (name + " - " + str));
            Log.d(tag, ls);
        }
    }

    private void debugSave(Object str) {
        if (logLevel <= Log.DEBUG) {
            String name = getFunctionName();
            String ls = (name == null ? str.toString() : (name + " - " + str));
            savePoint(tag, ls);
        }
    }

    public void d(Object str) {
        if (isDebug) {
            debug(str);
        }
        if (isSaveLog) {
            debugSave(str);
        }
    }

    public static void savePoint(String tag, String msg) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("",
                Locale.SIMPLIFIED_CHINESE);
        dateFormat.applyPattern("yyyy");

        String fileName = PATH_ROOT + dateFormat.format(date) + "/";
        dateFormat.applyPattern("MM");
        fileName += dateFormat.format(date) + "/";
        dateFormat.applyPattern("dd");
        fileName += dateFormat.format(date) + ".txt";

        point(fileName, tag, msg);
    }

    public static void point(String fileName, String tag, String msg) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("",
                Locale.SIMPLIFIED_CHINESE);
        dateFormat.applyPattern("[yyyy-MM-dd HH:mm:ss]");
        String time = dateFormat.format(date);
        File file = new File(fileName);
        if (!file.exists())
            createDipPath(fileName);
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(time + " " + tag + " " + msg + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createDipPath(String file) {
        String parentFile = file.substring(0, file.lastIndexOf("/"));
        File file1 = new File(file);
        File parent = new File(parentFile);
        if (!file1.exists()) {
            try {
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
