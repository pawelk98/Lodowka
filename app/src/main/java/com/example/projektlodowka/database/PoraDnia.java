package com.example.projektlodowka.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Przepis.class, parentColumns = "id",
        childColumns = "idPrzepisu", onDelete = ForeignKey.CASCADE),
        indices = @Index("idPrzepisu"))
public class PoraDnia {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int idPrzepisu;

    private int pora;

    public PoraDnia(int idPrzepisu, int pora) {
        this.idPrzepisu = idPrzepisu;
        this.pora = pora;
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

    public int getPora() {
        return pora;
    }

    public void setPora(int pora) {
        this.pora = pora;
    }
}
