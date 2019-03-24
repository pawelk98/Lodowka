package com.example.projektlodowka.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class Przepis {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String nazwa;

    private int czas;

    private String opis;


    public Przepis () {
        nazwa = "BRAK NAZWY";
    }

    public Przepis (@NonNull String nazwa, int czas, String opis) {
        this.nazwa = nazwa;
        this.czas = czas;
        this.opis = opis;
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

    public int getCzas() {
        return czas;
    }

    public void setCzas(int czas) {
        this.czas = czas;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
