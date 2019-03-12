package com.example.toplist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table items(id int primary key,description text)");
        db.execSQL("create table lists(id int primary key,description text)");
        db.execSQL("create table relation(idlist int,iditem int, FOREIGN KEY(idlist) REFERENCES lists(id), FOREIGN KEY(iditem) REFERENCES items(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
