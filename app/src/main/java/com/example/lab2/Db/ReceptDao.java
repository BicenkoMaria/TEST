package com.example.lab2.Db;
        import androidx.room.Dao;
        import androidx.room.Insert;
        import androidx.room.OnConflictStrategy;
        import androidx.room.Query;

        import java.util.List;

@Dao
public interface ReceptDao {
    @Query("SELECT * FROM recept")
    List<Recept> getAll();

    @Query("SELECT * FROM recept WHERE recept.id=:receptId")
    Recept getReceptById(long receptId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recept... recepts);
}