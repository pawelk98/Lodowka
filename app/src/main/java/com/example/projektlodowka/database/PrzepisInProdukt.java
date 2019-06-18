package com.example.projektlodowka.database;

public class PrzepisInProdukt {

    String nazwa;
    byte[] image;

    public PrzepisInProdukt(String nazwa, byte[] image) {
        this.nazwa = nazwa;
        this.image = image;
    }


    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public byte[] getObrazek() {
        return image;
    }

    public void setObrazek(byte[] obrazek) {
        this.image = obrazek;
    }
}
