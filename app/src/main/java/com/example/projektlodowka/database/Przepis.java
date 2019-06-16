package com.example.projektlodowka.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class Przepis {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String nazwa = "BRAK NAZWY";

    private int czas;

    private String opis;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    private int poraDnia; // 0-nieokreślona, 1-śniadanie, 2-obiad, 3-kolacja, 4-śniadanie/kolacja

    @Ignore
    public Przepis() {}

    @Ignore
    public Przepis (@NonNull String nazwa, int czas, String opis) {
        this.nazwa = nazwa;
        this.czas = czas;
        this.opis = opis;
        this.poraDnia = 0;
    }

    public Przepis (@NonNull String nazwa, int czas, String opis, int poraDnia) {
        this.nazwa = nazwa;
        this.czas = czas;
        this.opis = opis;
        this.poraDnia = poraDnia;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getPoraDnia() {
        return poraDnia;
    }

    public void setPoraDnia(int poraDnia) {
        this.poraDnia = poraDnia;
    }
}
