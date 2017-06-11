package com.smapley.vehicle_merchat.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.smapley.base.adapter.BaseAdapter;
import com.smapley.base.adapter.BaseHolder;
import com.smapley.vehicle_merchat.R;


import java.util.List;
import java.util.Map;

/**
 * Created by wuzhixiong on 2017/5/6.
 */

public class MainAdapter extends BaseAdapter {

    public MainAdapter(Context context, List list) {
        super(context, list);
    }


    @Override
    public int getLayout() {
        return R.layout.layout_main_item;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(getLayout(), parent, false);
            holder = getHolder(convertView);
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        if (holder != null) {
            holder.bindData(getItem(position), position);
        }
        return convertView;
    }

    @Override
    public BaseHolder getHolder(View view) {
        return new BaseHolder(view) {

            private TextView gold;
            private TextView chepai;
            private TextView time;
            private TextView number;

            @Override
            protected void initView(View view) {
                gold = (TextView) view.findViewById(R.id.main_item_gold);
                chepai = (TextView) view.findViewById(R.id.main_item_chepai);
                time = (TextView) view.findViewById(R.id.main_item_time);
                number = (TextView) view.findViewById(R.id.main_item_number);
            }

            @Override
            public void bindData(Map item, int position) {
                try{
                    gold.setText(((Double)item.get("gold")).intValue()+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                chepai.setText(item.get("cp").toString());
                time.setText(item.get("tm").toString());
                number.setText(item.get("bianhao").toString());
            }
        };
    }
}
