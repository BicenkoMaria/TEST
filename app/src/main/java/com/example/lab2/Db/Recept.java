package com.example.lab2.Db;
        import androidx.room.ColumnInfo;
        import androidx.room.Entity;
        import androidx.room.PrimaryKey;

@Entity
public class Recept {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

}