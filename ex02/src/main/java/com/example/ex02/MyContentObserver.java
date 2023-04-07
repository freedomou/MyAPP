package com.example.ex02;


import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;

public class MyContentObserver extends ContentObserver {
    private Context mContext;
    public MyContentObserver(Handler handler, Context context) {
        super(handler);
        mContext = context;
    }
    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        // 数据库数据发生改变时，会调用此方法
        Log.d("hello", "数据库数据发生改变");

    }
}
