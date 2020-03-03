package com.example.barcastations;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PictureDao {
    @Insert
    void insert(Picture picture);
    @Update
    void update(Picture picture);
    @Delete
    void delete(Picture picture);
    @Query("DELETE FROM picture_table WHERE id = :id_picture")
    void deleteById(long id_picture);
    @Query("SELECT * FROM picture_table WHERE station_id = :id")
    LiveData<List<Picture>> getPicturesById(String id);
}
