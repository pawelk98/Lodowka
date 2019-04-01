package com.example.projektlodowka.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PoraDniaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PoraDnia... poraDnia);

    @Update
    void update(PoraDnia... poraDnia);

    @Delete
    void delete(PoraDnia... poraDnia);

    @Query("SELECT * FROM PoraDnia")
    LiveData<List<PoraDnia>> loadAll();

    @Query("SELECT * FROM PoraDnia WHERE id = :id")
    PoraDnia loadId(int id);

    @Query("SELECT * FROM PoraDnia WHERE idPrzepisu = :idPrzepisu")
    List<PoraDnia> loadIdPrzepisu(int idPrzepisu);

    @Query("SELECT * FROM PoraDnia WHERE pora = :pora")
    List<PoraDnia> loadPora(int pora);
}
