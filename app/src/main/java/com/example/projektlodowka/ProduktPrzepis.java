package com.example.projektlodowka;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(foreignKeys = {
        @ForeignKey(entity = Przepis.class, parentColumns = "id", childColumns = "idPrzepisu", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Produkt.class, parentColumns = "id", childColumns = "idProduktu", onDelete = ForeignKey.CASCADE)})

public class ProduktPrzepis {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int idPrzepisu;

    private int idProduktu;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPrzepisu() {
        return idPrzepisu;
    }

    public void setIdPrzepisu(int idPrzepisu) {
        this.idPrzepisu = idPrzepisu;
    }

    public int getIdProduktu() {
        return idProduktu;
    }

    public void setIdProduktu(int idProduktu) {
        this.idProduktu = idProduktu;
    }
}
