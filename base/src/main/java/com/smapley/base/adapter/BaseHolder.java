package com.smapley.base.adapter;

import android.view.View;

import java.util.Map;

/**
 * Created by wuzhixiong on 2017/6/10.
 */

public abstract class BaseHolder {

    public  BaseHolder(View view){
        initView(view);
        view.setTag(this);
    }

    protected abstract void initView(View view);

    public abstract void bindData(Map item, int position) ;
}
