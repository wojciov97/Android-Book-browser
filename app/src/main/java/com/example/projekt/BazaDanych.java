package com.example.projekt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BazaDanych extends SQLiteOpenHelper {

    private static String tableName = "books";
    private static String nameDB = "book.db";
    private static String author_col = "AUTHOR";
    private static String title_col  = "TITLE";
    private static String id_col = "ID";

    public BazaDanych(Context context) {
        super(context, nameDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + tableName + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, AUTHOR TEXT )"
                    );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addBook (String title, String author){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(title_col,title);
        values.put(author_col, author);
        db.insertOrThrow(tableName,null,values);
    }

    public Cursor getAll(){
        String [] col = {id_col,title_col,author_col};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(tableName,col,null,null,null,null,null);
        return cursor;
    }

}
