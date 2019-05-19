package com.example.projektlodowka.database;

import android.arch.persistence.room.Ignore;

public class ProduktInPrzepis {

    String nazwa;
    int typ;
    int ilosc;
    int iloscProduktu;
    boolean opcjonalny;

    @Ignore
    ProduktInPrzepis() {};

    ProduktInPrzepis(String nazwa, int typ, int ilosc, int iloscProduktu, boolean opcjonalny) {
        this.nazwa = nazwa;
        this.ilosc = ilosc;
        this.iloscProduktu = iloscProduktu;
        this.opcjonalny = opcjonalny;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getTyp() {
        return typ;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public int getIloscProduktu() {
        return iloscProduktu;
    }

    public void setIloscProduktu(int iloscProduktu) {
        this.iloscProduktu = iloscProduktu;
    }

    public boolean isOpcjonalny() {
        return opcjonalny;
    }

    public void setOpcjonalny(boolean opcjonalny) {
        this.opcjonalny = opcjonalny;
    }
}
