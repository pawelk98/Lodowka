package com.example.projektlodowka;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ProduktDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Produkt... produkt);

    @Update
    void update(Produkt... produkt);

    @Delete
    void delete(Produkt... produkt);

    @Query("SELECT * FROM Produkt")
    List<Produkt> loadAll();

    @Query("SELECT * FROM Produkt WHERE id = :id")
    Produkt loadId(int id);

    @Query("SELECT * FROM Produkt WHERE nazwa = :nazwa")
    List<Produkt> loadNazwa(String nazwa);

    @Query("SELECT * FROM Produkt WHERE ilosc = :ilosc")
    List<Produkt> loadIlosc(int ilosc);

    @Query("SELECT * FROM Produkt WHERE typ = :typ")
    List<Produkt> loadTyp(int typ);
}
