package com.example.projektlodowka.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Produkt.class, Przepis.class, ProduktPrzepis.class, Minutnik.class, Historia.class, PoraDnia.class},
        version = 1, exportSchema = false)
public abstract class BazaDanych extends RoomDatabase {
    public abstract ProduktDao produktDao();
    public abstract PrzepisDao przepisDao();
    public abstract ProduktPrzepisDao produktPrzepisDao();
    public abstract MinutnikDao minutnikDao();
    public abstract HistoriaDao historiaDao();
    public abstract PoraDniaDao poraDniaDao();

    private static volatile BazaDanych INSTANCE;

    static BazaDanych getBazaDanych(final Context context) {
        if(INSTANCE == null) {
            synchronized (BazaDanych.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BazaDanych.class, "BazaDanych").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ProduktDao mDao;

        PopulateDbAsync(BazaDanych db) {
            mDao = db.produktDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Produkt produkt = new Produkt("Jeżyny",1,1);
            mDao.insert(produkt);
            produkt = new Produkt("Chleb",2,1000);
            mDao.insert(produkt);
            produkt = new Produkt("Pomidor",2,500);
            mDao.insert(produkt);
            produkt = new Produkt("Ogórek",2,2000);
            mDao.insert(produkt);
            produkt = new Produkt("Bułki",2,5000);
            mDao.insert(produkt);
            produkt = new Produkt("Mleko",1,1000);
            mDao.insert(produkt);
            produkt = new Produkt("Smietana",1,200);
            mDao.insert(produkt);
            produkt = new Produkt("Smietana",1,1);
            mDao.insert(produkt);
            produkt = new Produkt("Ziemniaki",0,1500);
            mDao.insert(produkt);
            produkt = new Produkt("Makaron",2,1200);
            mDao.insert(produkt);
            produkt = new Produkt("Sos",1,600);
            mDao.insert(produkt);
            produkt = new Produkt("Rzodkiew",0,50);
            mDao.insert(produkt);
            produkt = new Produkt("Cebula",2,3000);
            mDao.insert(produkt);
            produkt = new Produkt("Mąka",0,250);
            mDao.insert(produkt);

            return null;
        }
    }
}

