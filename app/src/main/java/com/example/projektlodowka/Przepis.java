package com.example.projektlodowka;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class Przepis {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String nazwa;

    private int skladniki;

    private int czas;

    private String opis;

    private int idMinutnik;

    public Przepis (String nazwa, int skladniki, int czas, String opis, int idMinutnik) {
        this.nazwa = nazwa;
        this.skladniki = skladniki;
        this.czas = czas;
        this.opis = opis;
        this.idMinutnik = idMinutnik;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getSkladniki() {
        return skladniki;
    }

    public void setSkladniki(int skladniki) {
        this.skladniki = skladniki;
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

    public int getIdMinutnik() { return idMinutnik; }

    public void setIdMinutnik(int minutnik) {
        this.idMinutnik = minutnik;
    }
}
