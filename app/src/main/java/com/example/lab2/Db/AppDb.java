package com.example.lab2.Db;

        import android.content.Context;

        import androidx.room.Database;
        import androidx.room.Room;
        import androidx.room.RoomDatabase;

@Database(entities = {Recept.class, User.class, FavoriteRecepts.class}, version = 1)
public abstract class AppDb extends RoomDatabase {
    public abstract ReceptDao receptDao();
    public abstract UserDao userDao();
    public abstract FavoriteReceptsDao FavoriteReceptsDao();

    private static AppDb instance;

    public static AppDb getInstance(Context context) {

        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDb.class, "dbRecepts")
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }
}