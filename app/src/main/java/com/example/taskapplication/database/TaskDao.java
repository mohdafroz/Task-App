package com.example.taskapplication.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.taskapplication.model.Task;
import java.util.List;

@Dao
public interface TaskDao {
    //insert
    @Insert(onConflict = REPLACE)
    void insert(Task task);

    //delete
    @Delete
    void delete(Task task);

    //update
    @Query("UPDATE table_task SET text =:sText WHERE ID = :sID")
    void update(int sID,String sText);

    //get all records
    @Query("SELECT * FROM table_task")
    List<Task> getAll();
}
