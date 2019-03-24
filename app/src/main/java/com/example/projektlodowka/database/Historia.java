package com.example.projektlodowka.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(foreignKeys = @ForeignKey(entity = Przepis.class, parentColumns = "id",
        childColumns = "idPrzepisu", onDelete = ForeignKey.CASCADE),
        indices = @Index("idPrzepisu"))

public class Historia {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int idPrzepisu;

    @NonNull
    private String data = "20990101";



    public Historia(int idPrzepisu, @NonNull String data) {
        this.idPrzepisu = idPrzepisu;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPrzepisu() {
        return idPrzepisu;
    }

    public void setIdPrzepisu(int idPrzepisu) {
        this.idPrzepisu = idPrzepisu;
    }

    @NonNull
    public String getData() { return data; }

    public void setData(@NonNull String data) {
        this.data = data;
    }
}
