package com.example.projektlodowka.database;

public class CookData {
    private String przepis;
    private String data;
    private int porcje;


    public CookData(){}

    public CookData(String przepis, String data, int porcje){
        this.data = data;
        this.porcje = porcje;
        this.przepis = przepis;
    }

    public String getPrzepis() {
        return przepis;
    }

    public void setPrzepis(String przepis) {
        this.przepis = przepis;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getPorcje() {
        return porcje;
    }

    public void setPorcje(int porcje) {
        this.porcje = porcje;
    }
}
