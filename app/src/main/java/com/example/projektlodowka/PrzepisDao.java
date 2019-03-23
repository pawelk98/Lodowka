package com.example.projektlodowka;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface PrzepisDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Przepis... przepis);

    @Update
    void update(Przepis... przepis);

    @Delete
    void delete(Przepis... przepis);

    @Query("SELECT * FROM Przepis")
    Przepis[] loadAll();

    @Query("SELECT * FROM Przepis WHERE id = :id")
    Przepis loadId(int id);

    @Query("SELECT * FROM Przepis WHERE nazwa = :nazwa")
    Przepis loadNazwa(String nazwa);

    @Query("SELECT * FROM Przepis WHERE skladniki = :skladniki")
    Przepis loadSkladniki(int skladniki);

    @Query("SELECT * FROM Przepis WHERE czas <= :czas")
    Przepis[] loadCzasLEQ(int czas);
}
