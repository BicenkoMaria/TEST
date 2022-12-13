package com.example.lab2.Db;
        import androidx.room.ColumnInfo;
        import androidx.room.Entity;
        import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "password")
    public String password;
}