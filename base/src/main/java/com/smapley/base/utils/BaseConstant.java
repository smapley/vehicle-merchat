package com.smapley.base.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by eric on 2017/3/13.
 * 基础数据
 */

public class BaseConstant {

    public static final int PAGE_MAIN = 0;
    public static final int PAGE_LOGIN = 1;

    protected static final String URL_BASE = "http://119.23.151.61/newera/";
    public static final String URL_IMG = URL_BASE + "upload/";
    public static final String URL_LOGIN = URL_BASE + "addReg.php";
    public static final String URL_REGISTER = URL_BASE + "addZhuce.php";
    public static final String URL_FORGET = URL_BASE + "updateReg.php";
    public static final String URL_GETCODE = URL_BASE + "addSMS.php";


    public static final int ALIPAY = 1;
    public static final int WECHATPAY = 2;

    public static final String URL_ADDGOLD = URL_BASE + "addGold.php";
    public static final String URL_ALIPAYORDER = URL_BASE + "addChongzhi.php";
    public static final String URL_WECHATPAYORDER = URL_BASE + "addZF.php";


    public static final String SP_USER = "user";
    public static final String SP_SET = "set";
    public static final String SP_DATA = "data";
    public static final String SP_ISLOGIN = "isLogin";
    public static final String SP_PAGETOGO = "pageToGo";
    public static final String SP_USERNAME = "username";
    public static final String SP_PASSWORD = "password";
    public static final String SP_UKEY = "ukey";

    //获取设备id
    public static String getDeviceId(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        return TelephonyMgr.getDeviceId();
    }
}
