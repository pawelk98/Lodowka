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

        private final ProduktDao produktDao;
        private final PrzepisDao przepisDao;

        PopulateDbAsync(BazaDanych db) {
            produktDao = db.produktDao();
            przepisDao = db.przepisDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            produktDao.deleteAll();
            przepisDao.deleteAll();

            Produkt produkt = new Produkt("jeżyna",0,200);
            produktDao.insert(produkt);
            produkt = new Produkt("chleb",2,1000);
            produktDao.insert(produkt);
            produkt = new Produkt("pomidor",2,2000);
            produktDao.insert(produkt);
            produkt = new Produkt("ogórek",2,2000);
            produktDao.insert(produkt);
            produkt = new Produkt("bułka",2,1500);
            produktDao.insert(produkt);
            produkt = new Produkt("mleko",1,2500);
            produktDao.insert(produkt);
            produkt = new Produkt("śmietana",1,200);
            produktDao.insert(produkt);
            produkt = new Produkt("sos pomidor.",1,600);
            produktDao.insert(produkt);
            produkt = new Produkt("ziemniak",0,3000);
            produktDao.insert(produkt);
            produkt = new Produkt("makaron",2,1500);
            produktDao.insert(produkt);
            produkt = new Produkt("sos sojowy",1,300);
            produktDao.insert(produkt);
            produkt = new Produkt("rzodkiewka",0,150);
            produktDao.insert(produkt);
            produkt = new Produkt("cebula",2,3000);
            produktDao.insert(produkt);
            produkt = new Produkt("mąka",0,500);
            produktDao.insert(produkt);
            produkt = new Produkt("masło",0,100);
            produktDao.insert(produkt);

            Przepis przepis = new Przepis("parówki",15,"gotuj gotuj gotuj itd.");
            przepisDao.insert(przepis);
            przepis = new Przepis("makaron", 20, "gotuj gotuj smaż itd");
            przepisDao.insert(przepis);
            przepis = new Przepis("warzywa na patelnię", 30, "krój gotuj smaż itd");
            przepisDao.insert(przepis);
            przepis = new Przepis("kotlety z kartoflami", 40, "klep gotuj smaż itd");
            przepisDao.insert(przepis);
            przepis = new Przepis("karkówka", 35, "coś tam tego smaż itd");
            przepisDao.insert(przepis);
            przepis = new Przepis("kanapki z chlebem i bagietką", 5, "krój samaruj itd");
            przepisDao.insert(przepis);
            przepis = new Przepis("kawior", 15, "blee itd");
            przepisDao.insert(przepis);
            przepis = new Przepis("kaszanka", 5, "odgrzej w mikrofalówce itd");
            przepisDao.insert(przepis);


            return null;
        }
    }
}

