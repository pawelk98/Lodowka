package com.example.projektlodowka;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class Produkt {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String nazwa;

    private int typ;

    private int ilosc;


    public Produkt() {
        nazwa = "BRAK NAZWY";
    }

    public Produkt(@NonNull String nazwa, int typ, int ilosc) {
        this.nazwa = nazwa;
        this.typ = typ;
        this.ilosc = ilosc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    @NonNull
    public String getNazwa() { return nazwa; }

    public void setNazwa(@NonNull String nazwa) { this.nazwa = nazwa; }

    public int getIlosc() { return ilosc; }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public int getTyp() {
        return typ;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }
}
