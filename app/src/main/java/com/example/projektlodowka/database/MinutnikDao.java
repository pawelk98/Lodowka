package com.example.projektlodowka.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.projektlodowka.database.Minutnik;

import java.util.List;

@Dao
public interface MinutnikDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Minutnik... minutnik);

    @Update
    void update(Minutnik... minutnik);

    @Delete
    void delete(Minutnik... minutnik);

    @Query("SELECT * FROM Minutnik")
    List<Minutnik> loadAll();

    @Query("SELECT * FROM Minutnik WHERE id = :id")
    Minutnik loadId(int id);

    @Query("SELECT * FROM Minutnik WHERE idPrzepisu = :idPrzepisu")
    List<Minutnik> loadPrzepis(int idPrzepisu);

    @Query("SELECT * FROM Minutnik WHERE nazwa = :nazwa")
    List<Minutnik> loadNazwa(String nazwa);
}
