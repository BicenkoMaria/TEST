package com.example.lab2.Db;
        import androidx.room.Dao;
        import androidx.room.Insert;
        import androidx.room.OnConflictStrategy;
        import androidx.room.Query;
        import java.util.List;

@Dao
public interface FavoriteReceptsDao {
    @Query("SELECT * FROM FavoriteRecepts")
    List<FavoriteRecepts> getAll();

    @Query("SELECT * FROM FavoriteRecepts WHERE FavoriteRecepts.user_id =:userId")
    List<FavoriteRecepts> getUserFavoriteReceptsById(long userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteRecepts... userFavoriteRecepts);
}