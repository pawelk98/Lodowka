package com.example.projektlodowka;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Produkt.class, Przepis.class}, version = 1, exportSchema = false)
public abstract class LodowkaDatabase extends RoomDatabase {
    public abstract ProduktDao produktDao();
    public abstract PrzepisDao przepisDao();
}
