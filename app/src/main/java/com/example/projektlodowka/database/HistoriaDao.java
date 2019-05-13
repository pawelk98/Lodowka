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
public interface HistoriaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Historia... histora);

    @Update
    void update(Historia... historia);

    @Delete
    void delete(Historia... historia);

    @Query("DELETE FROM Historia")
    void deleteAll();

    @Query("SELECT * FROM Historia")
    LiveData<List<Historia>> loadAll();

    @Query("SELECT * FROM Historia WHERE id = :id")
    Historia loadId(int id);

    @Query("SELECT * FROM Historia WHERE idPrzepisu = :idPrzepisu")
    List<Historia> loadPrzepis(int idPrzepisu);

    @Query("SELECT * FROM Historia WHERE data = :data")
    List<Historia> loadData(String data);
}
