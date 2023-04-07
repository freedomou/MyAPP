package com.example.ex01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MyContentProvider provider;
    private String uri = "content://com.example.mycontentprovider/table1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        provider = new MyContentProvider();

    }
    public void useProvider(View view) {
        Log.i("hello", "useProvider");
        Cursor cursor = getContentResolver().query(Uri.parse(uri), null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            Log.i("hello","name:"+name);
        }
    }
}