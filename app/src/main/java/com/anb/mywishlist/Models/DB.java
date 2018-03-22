package com.anb.mywishlist.Models;

/**
 * Created by IT001 on 23-Jun-16.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "todolist.db";

    public DB(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_STUDENT = "CREATE TABLE " + Todo.TABLE  + "("
                + Todo._ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Todo._TITLE+ " TEXT, "
                + Todo._DESCRIPTION+ " TEXT, "
                + Todo._TIME + " INTEGER, "
                + Todo._COMPLETED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP )";

        db.execSQL(CREATE_TABLE_STUDENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Todo.TABLE);

        // Create tables again
        onCreate(db);

    }

}