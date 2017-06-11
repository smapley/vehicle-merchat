package com.smapley.vehicle_merchat.activity;

import android.content.Intent;

import com.smapley.base.activity.BaseActivity;
import com.smapley.base.activity.LoginActivity;
import com.smapley.base.utils.BaseConstant;
import com.smapley.base.utils.SP;
import com.smapley.vehicle_merchat.R;
import com.smapley.vehicle_merchat.utils.Constant;

import org.xutils.view.annotation.ContentView;

/**
 * Created by wuzhixiong on 2017/4/29.
 */
@ContentView(R.layout.activity_first)
public class FirstActivity extends BaseActivity {
    @Override
    public void initArgument() {
        switch ((int) SP.getSet(BaseConstant.SP_PAGETOGO, Constant.PAGE_LOGIN)) {
            case BaseConstant.PAGE_MAIN:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case BaseConstant.PAGE_LOGIN:
                startActivity(new Intent(this, MyLoginActivity.class));
                break;
        }
    }

    @Override
    public void initView() {


    }
}
