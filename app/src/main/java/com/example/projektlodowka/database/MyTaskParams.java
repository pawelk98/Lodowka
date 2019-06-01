package com.example.projektlodowka.database;

public class MyTaskParams {
    String przepisName;
    String produktName;
    int ilosc;
    boolean opcjonalny;

    public MyTaskParams() {}

    public MyTaskParams(String produktName, int ilosc, boolean opcjonalny) {
        this.produktName = produktName;
        this.ilosc = ilosc;
        this.opcjonalny = opcjonalny;
    }

    public MyTaskParams(String przepisName, String produktName, int ilosc, boolean opcjonalny) {
        this.przepisName = przepisName;
        this.produktName = produktName;
        this.ilosc = ilosc;
        this.opcjonalny = opcjonalny;
    }

    public String getPrzepisName() {
        return przepisName;
    }

    public void setPrzepisName(String przepisName) {
        this.przepisName = przepisName;
    }

    public String getProduktName() {
        return produktName;
    }

    public void setProduktName(String produktName) {
        this.produktName = produktName;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public boolean isOpcjonalny() {
        return opcjonalny;
    }

    public void setOpcjonalny(boolean opcjonalny) {
        this.opcjonalny = opcjonalny;
    }
}