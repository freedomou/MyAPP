package com.example.ex01;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {
    private static final String TAG = "MyContentProvider";
    private static final String AUTHORITY = "com.example.mycontentprovider";
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int TABLE1_DIR = 1;
    private static final int TABLE1_ITEM = 2;


    static {
        URI_MATCHER.addURI(AUTHORITY, "table1", TABLE1_DIR);
        URI_MATCHER.addURI(AUTHORITY, "table1/#", TABLE1_ITEM);
    }

    private MyDatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new MyDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        getContext().getContentResolver().notifyChange(Uri.parse("content://com.example.mycontentprovider/table1"),null);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (URI_MATCHER.match(uri)) {
            case TABLE1_DIR:
                cursor = db.query("person", projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case TABLE1_ITEM:
                String id = uri.getPathSegments().get(1);
                cursor = db.query("person", projection, "id=?", new String[]{id},
                        null, null, sortOrder);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (URI_MATCHER.match(uri)) {
            case TABLE1_DIR:
            case TABLE1_ITEM:
                long newTable1Id = db.insert("person", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/table1/" + newTable1Id);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (URI_MATCHER.match(uri)) {
            case TABLE1_DIR:
                updatedRows = db.update("person", values, selection, selectionArgs);
                break;
            case TABLE1_ITEM:
                String id = uri.getPathSegments().get(1);
                updatedRows = db.update("person", values, "id=?", new String[]{id});
                break;
        }
        return updatedRows;
    }

}
