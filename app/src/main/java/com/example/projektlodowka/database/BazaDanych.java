package com.example.projektlodowka.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Dao;
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

    public static BazaDanych getBazaDanych(final Context context) {
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

    public static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    public static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ProduktDao produktDao;
        private final PrzepisDao przepisDao;
        private final ProduktPrzepisDao produktPrzepisDao;

        public PopulateDbAsync(BazaDanych db) {
            produktDao = db.produktDao();
            przepisDao = db.przepisDao();
            produktPrzepisDao = db.produktPrzepisDao();
        }

        private void insertProduktPrzepis(String produktNazwa, String przepisNazwa, int ilosc, boolean opcjonalny ) {
            int produktId = produktDao.loadNazwa(produktNazwa).getId();
            int przepisId = przepisDao.loadNazwa(przepisNazwa).getId();
            produktPrzepisDao.insert(new ProduktPrzepis(przepisId, produktId, ilosc, opcjonalny));
        }

        private void insertProdukt(String nazwa, int typ, int ilosc) {
            produktDao.insert(new Produkt(nazwa, typ, ilosc));
        }

        private void insertPrzepis(String nazwa, int czas, String opis) {
            przepisDao.insert(new Przepis(nazwa, czas, opis));
        }


        @Override
        protected Void doInBackground(final Void... params) {
            if(produktDao.count() == 0 && przepisDao.count() == 0) {

                insertProdukt("ziemniaki", 0, 0);
                insertProdukt("cebula", 2, 0);
                insertProdukt("mleko", 1, 0);
                insertProdukt("ser biały", 0, 0);
                insertProdukt("makaron", 2, 0);
                insertProdukt("jogurt naturalny", 1, 0);
                insertProdukt("ser żółty", 0, 0);
                insertProdukt("parówki", 3,0);
                insertProdukt("chleb", 2, 0);
                insertProdukt("bułki", 2, 0);
                insertProdukt("jabłka", 2, 0);
                insertProdukt("pomarańcze", 2, 0);
                insertProdukt("awokado", 2, 0);
                insertProdukt("fasola czarna", 0, 0);
                insertProdukt("mięso mielone", 0, 0);
                insertProdukt("schab", 0, 0);
                insertProdukt("kiełbasa", 0, 0);
                insertProdukt("szproty w pomidorach", 2, 0);
                insertProdukt("sałata", 2, 0);
                insertProdukt("soczewica", 0, 0);


                insertPrzepis("parówki", 10, "Wrzuć do gotującej się wody parówki");
                insertProduktPrzepis("parówki", "parówki", 4000, false);
                insertProduktPrzepis("bułki", "parówki", 1000, true);
            }
            return null;
        }
    }
}

