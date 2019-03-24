package com.example.projektlodowka.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(foreignKeys = @ForeignKey(entity = Przepis.class, parentColumns = "id",
        childColumns = "idPrzepisu", onDelete = ForeignKey.CASCADE),
        indices = @Index("idPrzepisu"))

public class Minutnik {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String nazwa = "BRAK NAZWY";

    private int idPrzepisu;

    private int czas;



    public Minutnik(@NonNull String nazwa, int idPrzepisu, int czas) {
        this.nazwa = nazwa;
        this.idPrzepisu = idPrzepisu;
        this.czas = czas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(@NonNull String nazwa) {
        this.nazwa = nazwa;
    }

    public int getIdPrzepisu() {
        return idPrzepisu;
    }

    public void setIdPrzepisu(int idPrzepisu) {
        this.idPrzepisu = idPrzepisu;
    }

    public int getCzas() {
        return czas;
    }

    public void setCzas(int czas) {
        this.czas = czas;
    }
}
