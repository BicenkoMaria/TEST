package com.example.lab2.Db;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteRecepts {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "user_id")
    public long userId;

    @ColumnInfo(name = "recept_id")
    public long receptId;
}