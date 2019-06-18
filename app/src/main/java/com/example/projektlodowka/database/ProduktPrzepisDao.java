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
public interface ProduktPrzepisDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProduktPrzepis... produktPrzepis);

    @Update
    void update(ProduktPrzepis... produktPrzepis);

    @Delete
    void delete(ProduktPrzepis... produktPrzepis);

    @Query("SELECT * FROM ProduktPrzepis")
    LiveData<List<ProduktPrzepis>> loadAll();

    @Query("SELECT * FROM ProduktPrzepis WHERE id = :id")
    ProduktPrzepis loadId(int id);

    @Query("SELECT * FROM ProduktPrzepis WHERE idProduktu = :idProduktu")
    LiveData<List<ProduktPrzepis>> loadProdukt(int idProduktu);

    @Query("SELECT * FROM ProduktPrzepis WHERE idPrzepisu = :idPrzepisu")
    LiveData<List<ProduktPrzepis>> loadPrzepis(int idPrzepisu);

    @Query("SELECT * FROM ProduktPrzepis WHERE idPrzepisu = :idPrzepisu")
    List<ProduktPrzepis> loadPrzepisList(int idPrzepisu);

    @Query("SELECT Produkt.nazwa, Produkt.typ, Produkt.ilosc, ProduktPrzepis.iloscProduktu, ProduktPrzepis.opcjonalny " +
            "FROM Produkt, ProduktPrzepis WHERE Produkt.id = ProduktPrzepis.idProduktu AND ProduktPrzepis.idPrzepisu = :idPrzepisu")
    LiveData<List<ProduktInPrzepis>> loadProduktInPrzepis(int idPrzepisu);

    @Query("SELECT Przepis.nazwa, Przepis.image FROM Przepis, ProduktPrzepis " +
            "WHERE Przepis.id = ProduktPrzepis.idPrzepisu AND ProduktPrzepis.idProduktu = :idProduktu")
    LiveData<List<PrzepisInProdukt>> loadPrzepisInProdukt(int idProduktu);

    @Query("DELETE FROM ProduktPrzepis WHERE idPrzepisu = :idPrzepisu")
    void deleteProdukty(int idPrzepisu);


}