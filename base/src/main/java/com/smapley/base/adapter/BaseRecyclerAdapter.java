package com.smapley.base.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Eric on 2017/3/20.
 * 适配器
 */
public class BaseRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
