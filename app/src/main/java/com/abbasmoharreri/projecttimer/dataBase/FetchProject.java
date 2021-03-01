package com.abbasmoharreri.projecttimer.dataBase;

import android.content.Context;
import android.util.Log;

import com.abbasmoharreri.projecttimer.model.Project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FetchProject extends DataBaseController {

    private List<Project> projects;

    public FetchProject(Context context) {
        super( context );
        projects = new ArrayList<>();
        fetchData();
    }

    private void fetchData() {


        String query = "SELECT "
                + DataBase.KEY_ID + ","
                + DataBase.KEY_NAME + ","
                + DataBase.KEY_TIME
                + " FROM " + DataBase.TABLE_PROJECT;

        cursor = db.rawQuery( query, null );

        if (cursor.moveToFirst())
            do {

                Project project = new Project();
                project.setId( cursor.getInt( 0 ) );
                project.setName( cursor.getString( 1 ) );
                project.setTime( cursor.getLong( 2 ) );

                this.projects.add( project );

            } while (cursor.moveToNext());

    }


    public List<Project> getList() {
        return projects;
    }

}
