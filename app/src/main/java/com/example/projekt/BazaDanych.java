package com.example.projekt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class    BazaDanych extends SQLiteOpenHelper {

    private static String tableName = "books";
    private static String nameDB = "books.db";
    private static String author_col = "AUTHOR";
    private static String title_col  = "TITLE";
    private static String pubHouse_col = "HOUSE";
    private static String img_col = "PATH";
    private static String img_path = "@drawable/";
    private static String id_col = "ID";

    public BazaDanych(Context context) {
        super(context, nameDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + tableName + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, AUTHOR TEXT, HOUSE TEXT, PATH TEXT)"
                    );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addBook (String title, String author,String pubHouse, String path){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title_col,title);
        values.put(author_col, author);
        values.put(pubHouse_col,pubHouse);
        values.put(img_col,img_path+path);
        db.insertOrThrow(tableName,null,values);
    }

    public Cursor getAll(){
        String [] col = {id_col,title_col,author_col,pubHouse_col, img_col};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(tableName,col,null,null,null,null,null);
//        db.execSQL("DELETE FROM "+tableName);
        return cursor;
    }



}
