package com.example.barcastations;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StationDao {
    @Insert
    void insert(Station station);
    @Update
    void update(Station station);
    @Delete
    void delete(Station station);
    @Query("SELECT * FROM station_table")
    LiveData<List<Station>> getAllStation();
    @Query("SELECT * FROM station_table WHERE id = :id_station")
    Station getStation(String id_station);
}
