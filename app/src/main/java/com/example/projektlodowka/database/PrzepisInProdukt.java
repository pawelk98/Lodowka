package com.example.projektlodowka.database;

public class PrzepisInProdukt {

    String nazwa;
    byte[] obrazek;

    public PrzepisInProdukt(String nazwa, byte[] obrazek) {
        this.nazwa = nazwa;
        this.obrazek = obrazek;
    }


    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public byte[] getObrazek() {
        return obrazek;
    }

    public void setObrazek(byte[] obrazek) {
        this.obrazek = obrazek;
    }
}
