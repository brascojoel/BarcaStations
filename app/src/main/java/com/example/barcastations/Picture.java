package com.example.barcastations;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "picture_table",

        foreignKeys = @ForeignKey(entity = Station.class,
        parentColumns = "id",
        childColumns = "station_id",
        onDelete = CASCADE))
public class Picture {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "station_id")
    private String station_id;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "date_picture")
    private String date_picture;

    public Picture(String station_id, byte[] image, String title, String date_picture) {
        this.station_id = station_id;
        this.image = image;
        this.title = title;
        this.date_picture = date_picture;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate_picture() {
        return date_picture;
    }

    public void setDate_picture(String date_picture) {
        this.date_picture = date_picture;
    }
}
