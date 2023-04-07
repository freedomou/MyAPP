package com.example.ex02;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private MyContentObserver observer;
    private String uri = "content://com.example.mycontentprovider/table1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        observer=new MyContentObserver(new Handler(),this);
        getContentResolver().registerContentObserver(Uri.parse(uri),true,observer);
    }
}