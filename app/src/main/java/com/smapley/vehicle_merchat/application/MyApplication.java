package com.smapley.vehicle_merchat.application;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.smapley.base.application.BaseApplication;
import com.smapley.base.utils.SP;

/**
 * Created by wuzhixiong on 2017/6/11.
 */

public class MyApplication extends BaseApplication {
    private static final String TAG = "Init";

    @Override
    public void onCreate() {
        super.onCreate();
        initCloudChannel(this);
    }

    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.setLogLevel(CloudPushService.LOG_ERROR);
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                SP.saveSet("token", pushService.getDeviceId());
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
            }
        });

    }
}
