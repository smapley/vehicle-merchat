package com.smapley.vehicle_merchat.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.SoundPool;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.smapley.base.activity.BaseActivity;
import com.smapley.base.application.BaseApplication;
import com.smapley.base.http.BaseCallback;
import com.smapley.base.utils.SP;
import com.smapley.base.utils.ThreadSleep;
import com.smapley.vehicle_merchat.R;
import com.smapley.vehicle_merchat.adapter.MainAdapter;
import com.smapley.vehicle_merchat.utils.Constant;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.zip.CheckedInputStream;

import io.reactivex.internal.fuseable.SimpleQueue;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.main_gold)
    private TextView gold;
    @ViewInject(R.id.main_chepai)
    private TextView chepai;
    @ViewInject(R.id.main_time)
    private TextView time;
    @ViewInject(R.id.main_number)
    private TextView number;

    @ViewInject(R.id.main_listView)
    private ListView listView;
    private MainAdapter adapter;
    private List list = new ArrayList();

    private Map oldMap;
    private BroadcastReceiver receiver;

    private SoundPool soundPool;
    private List<Integer> soundList;
    private Queue<Integer> playQueue;
    private Thread playThread;

    @Override
    public void initArgument() {
        isExit = true;
        getData();
    }

    @Override
    public void initView() {
        initSound();
        initXG();

        setTitle("车速通");
        showRightText("退出");
        adapter = new MainAdapter(this, list);
        listView.setAdapter(adapter);
        initReceiver();
    }

    private void initSound() {
        soundList = new ArrayList<>();
        playQueue = new ArrayDeque<>();
        if (playThread == null) {
            playThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (playQueue.size() > 0) {
                            int number = playQueue.poll();
                            if (number >= 10) {
                                if (number / 10 != 1) {
                                    soundPool.play(soundList.get(number / 10), 1, 1, 0, 0, 1);
                                    try {
                                        Thread.sleep(600);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                soundPool.play(soundList.get(10), 1, 1, 0, 0, 1);
                                try {
                                    Thread.sleep(600);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            int i = number % 10;
                            if (i != 0) {
                                soundPool.play(soundList.get(i), 1, 1, 0, 0, 1);
                                try {
                                    Thread.sleep(700);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            soundPool.play(soundList.get(11), 1, 1, 0, 0, 1);
                            try {
                                Thread.sleep(700);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            soundPool.play(soundList.get(0), 1, 1, 0, 0, 1);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
            playThread.start();
        }
        //初始化SoundPool
        soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 0);
        soundList.add(soundPool.load(this, R.raw.received, 1));
        soundList.add(soundPool.load(this, R.raw.number1, 1));
        soundList.add(soundPool.load(this, R.raw.number2, 1));
        soundList.add(soundPool.load(this, R.raw.number3, 1));
        soundList.add(soundPool.load(this, R.raw.number4, 1));
        soundList.add(soundPool.load(this, R.raw.number5, 1));
        soundList.add(soundPool.load(this, R.raw.number6, 1));
        soundList.add(soundPool.load(this, R.raw.number7, 1));
        soundList.add(soundPool.load(this, R.raw.number8, 1));
        soundList.add(soundPool.load(this, R.raw.number9, 1));
        soundList.add(soundPool.load(this, R.raw.number10, 1));
        soundList.add(soundPool.load(this, R.raw.gold, 1));
    }

    private void playSound(int number) {
        if (number > 99)
            return;
        playQueue.add(number);
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter("com.smapley.vehicle_merchat.update_main_list");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getStringExtra("data");
                Gson gson = new Gson();
                Map map = gson.fromJson(data, Map.class);
                try {
                    playSound(((Double) map.get("gold")).intValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                showTop(map);
            }
        };
        registerReceiver(receiver, filter);
    }

    private void showTop(Map map) {
        if (oldMap != null) {
            list.add(0, oldMap);
            adapter.notifyDataSetChanged();
        }
        oldMap = map;
        try {
            gold.setText(((Double) map.get("gold")).intValue() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        chepai.setText(map.get("cp").toString());
        time.setText(map.get("tm").toString());
        number.setText(map.get("bianhao").toString());
    }

    @Override
    protected void onRightText(View v) {
        super.onRightText(v);
        BaseApplication.getInstance().loginOut();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void initXG() {
        Context context = getApplicationContext();
        XGPushManager.registerPush(context, new XGIOperateCallback() {
            @Override
            public void onSuccess(final Object o, int i) {
            }

            @Override
            public void onFail(Object o, int i, String s) {
                Toast.makeText(context, "系统注册失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData() {
        RequestParams params = new RequestParams(Constant.URL_SGETMINGXI);
        params.addBodyParameter("ukey", (String) SP.getUser("ukey"));
        x.http().post(params, new BaseCallback<Map>() {
            @Override
            public void success(Map result) {
                List dataList = (List) result.get("lsmx");
                showTop((Map) dataList.get(0));
                dataList.remove(0);
                list.clear();
                list.addAll(dataList);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
