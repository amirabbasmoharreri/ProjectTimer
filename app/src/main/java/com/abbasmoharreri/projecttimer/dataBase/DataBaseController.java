package com.abbasmoharreri.projecttimer.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.abbasmoharreri.projecttimer.model.Project;
import com.abbasmoharreri.projecttimer.model.Time;

import java.util.ArrayList;
import java.util.List;

public class DataBaseController {

    protected Cursor cursor;
    protected Context context;
    protected SQLiteDatabase db;


    public DataBaseController(Context context) {
        this.db = new DataBase( context ).getWritableDatabase();
    }

    public void close() {
        this.cursor.close();
        this.db.close();
    }


    public void insertProject(Project project) {

        ContentValues contentValues = new ContentValues();

        contentValues.put( DataBase.KEY_NAME, project.getName() );
        contentValues.put( DataBase.KEY_TIME, project.getTime() );

        db.insert( DataBase.TABLE_PROJECT, null, contentValues );

    }


    public void insertTime(Time time) {

        ContentValues contentValues = new ContentValues();

        contentValues.put( DataBase.KEY_PROJECT_ID, time.getProjectId() );
        contentValues.put( DataBase.KEY_TIME, time.getTime() );

        db.insert( DataBase.TABLE_TIME, null, contentValues );

    }

    public void updateProject(Project project) {
        ContentValues contentValues = new ContentValues();
        contentValues.put( DataBase.KEY_NAME, project.getName() );
        contentValues.put( DataBase.KEY_TIME, project.getTime() );

        db.update( DataBase.TABLE_PROJECT, contentValues, DataBase.KEY_ID + " =?", new String[]{String.valueOf( project.getId() )} );

    }


    public Long fetchTime(Project project) {

        Long sumTime = 0L;

        String query = "SELECT SUM("
                + DataBase.KEY_TIME
                + ") FROM " + DataBase.TABLE_TIME
                + " WHERE " + DataBase.KEY_PROJECT_ID + " = " + project.getId();

        cursor = db.rawQuery( query, null );

        if (cursor.moveToFirst())
            sumTime = cursor.getLong( 0 );

        return sumTime;
    }
}
