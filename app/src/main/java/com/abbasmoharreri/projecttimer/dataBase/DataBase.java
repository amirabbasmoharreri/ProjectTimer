package com.abbasmoharreri.projecttimer.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "ProjectTime";
    public final static int DATABASE_VERSION = 1;

    public final static String TABLE_PROJECT = "Project";
    public final static String TABLE_TIME = "Time";
    public final static String KEY_ID = "ID";
    public final static String KEY_NAME = "Name";
    public final static String KEY_TIME = "Time";
    public final static String KEY_PROJECT_ID = "ProjectId";


    public DataBase(@Nullable Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_PROJECT = "CREATE TABLE IF NOT EXISTS " + TABLE_PROJECT + "( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT ,"
                + KEY_TIME + " INTEGER)";

        String CREATE_TABLE_TIME = "CREATE TABLE IF NOT EXISTS " + TABLE_TIME + "( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PROJECT_ID + " INTEGER ,"
                + KEY_TIME + " INTEGER,"
                + "FOREIGN KEY ( " + KEY_PROJECT_ID + ") REFERENCES " + TABLE_PROJECT + " ( " + KEY_ID + " ))";

        db.execSQL( CREATE_TABLE_PROJECT );
        db.execSQL( CREATE_TABLE_TIME );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
