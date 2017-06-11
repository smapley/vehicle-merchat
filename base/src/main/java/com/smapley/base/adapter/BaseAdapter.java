package com.smapley.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;
import java.util.Map;

/**
 * Created by wuzhixiong on 2017/6/10.
 */

public abstract class BaseAdapter extends android.widget.BaseAdapter {

    protected Context context;
    protected LayoutInflater inflater;
    private List<Map> list;

    public BaseAdapter(Context context, List<Map> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public abstract int getLayout();

    public abstract BaseHolder getHolder(View view);

    @Override
    public int getCount() {
        if (list == null)
            return 0;
        else
            return list.size();
    }

    @Override
    public Map getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



}
