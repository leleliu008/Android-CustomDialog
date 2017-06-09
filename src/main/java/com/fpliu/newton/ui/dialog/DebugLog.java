package com.fpliu.newton.ui.dialog;

import android.os.Build;
import android.util.Log;

/**
 * 调试日志
 *
 * @author 792793182@qq.com 2015-06-11
 */
public final class DebugLog {

    /**
     * 调试日志的开关，一般Debug版本中打开，便于开发人员观察日志，Release版本中关闭
     */
    private static boolean ENABLED = true;

    /**
     * TAG的前缀，便于过滤
     */
    private static String PREFIX = "";

    private DebugLog() {
    }

    public static void setEnabled(boolean enabled) {
        ENABLED = enabled;
    }

    public static void setPrefix(String prefix) {
        PREFIX = prefix;
    }

    public static int v(String tag, String msg) {
        return ENABLED ? Log.v(PREFIX + tag, "" + msg) : 0;
    }

    public static int v(String tag, String msg, Throwable throwable) {
        return ENABLED ? Log.v(PREFIX + tag, msg, throwable) : 0;
    }

    public static int d(String tag, String msg) {
        //华为的这款手机只能打印information信息
        if ("GEM-703L".equals(Build.MODEL)
                || "H60-L11".equals(Build.MODEL)) {
            return i(tag, msg);
        }
        return ENABLED ? Log.d(PREFIX + tag, "" + msg) : 0;
    }

    public static int d(String tag, String msg, Throwable throwable) {
        return ENABLED ? Log.d(PREFIX + tag, msg, throwable) : 0;
    }

    public static int i(String tag, String msg) {
        return ENABLED ? Log.i(PREFIX + tag, "" + msg) : 0;
    }

    public static int i(String tag, String msg, Throwable tr) {
        return ENABLED ? Log.i(PREFIX + tag, msg, tr) : 0;
    }

    public static int w(String tag, String msg) {
        return ENABLED ? Log.w(PREFIX + tag, "" + msg) : 0;
    }

    public static int w(String tag, String msg, Throwable tr) {
        return ENABLED ? Log.w(PREFIX + tag, msg, tr) : 0;
    }

    public static int w(String tag, Throwable tr) {
        return ENABLED ? Log.w(PREFIX + tag, tr) : 0;
    }

    public static int e(String tag, String msg) {
        return ENABLED ? Log.e(PREFIX + tag, "" + msg) : 0;
    }

    public static int e(String tag, String msg, Throwable tr) {
        return ENABLED ? Log.e(PREFIX + tag, msg, tr) : 0;
    }
}
