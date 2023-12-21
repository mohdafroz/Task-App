package com.example.taskapplication.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.taskapplication.model.Task;

@Database(entities = {Task.class},version = 1,exportSchema = false)
public abstract class DataBase extends RoomDatabase {
    //create Database instance
    private static DataBase dataBase;
    //define db name
    private static String DATABASE_NAME="database";

    public  synchronized static DataBase getInstance(Context context){
        if(dataBase==null){
            //If dataBase is null initialize dataBase
            dataBase= Room.databaseBuilder(context.getApplicationContext(), DataBase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return dataBase;
    }

    //create Dao
    public abstract TaskDao taskDao();
}
