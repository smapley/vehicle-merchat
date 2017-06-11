package com.smapley.vehicle_merchat.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.smapley.base.activity.BaseActivity;
import com.smapley.base.http.BaseCallback;
import com.smapley.base.utils.ActivityStack;
import com.smapley.base.utils.BaseConstant;
import com.smapley.base.utils.SP;
import com.smapley.base.widget.LinearlayoutView;
import com.smapley.vehicle_merchat.R;
import com.smapley.vehicle_merchat.utils.Constant;

import org.apache.commons.lang3.StringUtils;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Map;

/**
 * Created by wuzhixiong on 2017/6/10.
 */
@ContentView(R.layout.activity_mylogin)
public class MyLoginActivity extends BaseActivity implements LinearlayoutView.KeyBoardStateListener {

    @ViewInject(R.id.mylogin_logo)
    private ImageView logo;
    @ViewInject(R.id.mylogin_layout)
    private LinearlayoutView layout;
    @ViewInject(R.id.mylogin_phone)
    private EditText phoneET;

    private String phone;
    private String deviceId;
    private String token;

    @Override
    public void initArgument() {

    }

    @Override
    public void initView() {
        isExit = true;
        layout.setKeyBoardStateListener(this);

    }

    @Event({R.id.mylogin_login})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.mylogin_login:
                if (checkForm()) {
                    doLogin();
                }
                break;
        }
    }

    private void doLogin() {
        RequestParams params = new RequestParams(Constant.URL_SADDREG);
        params.addBodyParameter("user", phone);
        params.addBodyParameter("token", token);
        params.addBodyParameter("biaoshi", deviceId);
        System.out.println(params.toJSONString());
        x.http().post(params, new BaseCallback<Map>() {
            @Override
            public void success(Map result) {
                afterLogin(result);
            }
        });
    }

    private void afterLogin(Map result) {
        SP.saveUser("ukey", result.get("ukey").toString());
        SP.saveUser("user", result.get("user").toString());
        SP.saveUser("token", result.get("token").toString());
        SP.saveUser("biaoshi", result.get("biaoshi").toString());
        SP.saveSet(BaseConstant.SP_ISLOGIN, true);
        SP.saveSet(BaseConstant.SP_PAGETOGO, BaseConstant.PAGE_MAIN);
        ActivityStack.getInstance().finishActivityButMain();
    }

    private boolean checkForm() {
        token = (String) SP.getSet("token");
        deviceId = Constant.getDeviceId(this);
        phone = phoneET.getText().toString();
        if(StringUtils.isEmpty(token)){
            Toast.makeText(this, "系统尚未注册成功，请稍后再试！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isEmpty(deviceId)) {
            Toast.makeText(this, "获取设备标识失败，请稍后再试！", Toast.LENGTH_SHORT).show();
            deviceId = Constant.getDeviceId(this);
            return false;
        }
        if (StringUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    @Override
    public void stateChange(int state) {
        switch (state) {
            case LinearlayoutView.KEYBOARD_HIDE:
                logo.setVisibility(View.VISIBLE);
                break;
            case LinearlayoutView.KEYBOARD_SHOW:
                logo.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SP.saveSet(BaseConstant.SP_PAGETOGO, BaseConstant.PAGE_MAIN);
            ActivityStack.getInstance().finishActivityButMain();
        }
        return false;
    }
}
