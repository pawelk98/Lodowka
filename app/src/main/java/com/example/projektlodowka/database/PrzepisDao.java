package com.example.projektlodowka.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PrzepisDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Przepis... przepis);

    @Update
    void update(Przepis... przepis);

    @Delete
    void delete(Przepis... przepis);

    @Query("SELECT * FROM Przepis")
    List<Przepis> loadAll();

    @Query("SELECT * FROM Przepis WHERE id = :id")
    Przepis loadId(int id);

    @Query("SELECT * FROM Przepis WHERE nazwa = :nazwa")
    List<Przepis> loadNazwa(String nazwa);

    @Query("SELECT * FROM Przepis WHERE czas <= :czas")
    List<Przepis> loadCzasLEQ(int czas);
}
