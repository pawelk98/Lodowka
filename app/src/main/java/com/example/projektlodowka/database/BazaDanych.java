package com.example.projektlodowka.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.example.projektlodowka.R;

import java.io.ByteArrayOutputStream;

@Database(entities = {Produkt.class, Przepis.class, ProduktPrzepis.class, Minutnik.class, Historia.class, PoraDnia.class},
        version = 1, exportSchema = false)
public abstract class BazaDanych extends RoomDatabase {
    public abstract ProduktDao produktDao();
    public abstract PrzepisDao przepisDao();
    public abstract ProduktPrzepisDao produktPrzepisDao();
    public abstract MinutnikDao minutnikDao();
    public abstract HistoriaDao historiaDao();
    public abstract PoraDniaDao poraDniaDao();

    private static volatile Context c;
    private static volatile BazaDanych INSTANCE;


    public static BazaDanych getBazaDanych(final Context context) {
        if(INSTANCE == null) {
            synchronized (BazaDanych.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BazaDanych.class, "BazaDanych").addCallback(sRoomDatabaseCallback).build();
                }
            }
            c = context;
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

        private void insertProdukt(String nazwa, int typ, int ilosc, Drawable obrazek){
            Produkt p = new Produkt(nazwa, typ, ilosc);
            p.setImage(getBytesFromBitmap(obrazek));
            produktDao.insert(p);
        }
        private byte[] getBytesFromBitmap(Drawable d) {
            Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            return stream.toByteArray();
        }




        @Override
        protected Void doInBackground(final Void... params) {
            if(produktDao.count() == 0 && przepisDao.count() == 0) {

                insertProdukt("ziemniaki", 0, 0, c.getResources().getDrawable(R.drawable.ziemniak));
                insertProdukt("cebula", 2, 0, c.getResources().getDrawable(R.drawable.cebula));
                insertProdukt("mleko", 1, 0, c.getResources().getDrawable(R.drawable.mleko));
                insertProdukt("ser biały", 0, 0, c.getResources().getDrawable(R.drawable.serbialy));
                insertProdukt("makaron", 2, 0, c.getResources().getDrawable(R.drawable.makaron));
                insertProdukt("jogurt naturalny", 1, 0, c.getResources().getDrawable(R.drawable.jogurtnaturalny));
                insertProdukt("ser żółty", 0, 0, c.getResources().getDrawable(R.drawable.serzolty));
                insertProdukt("parówki", 3,0, c.getResources().getDrawable(R.drawable.parowki));
                insertProdukt("chleb", 2, 0, c.getResources().getDrawable(R.drawable.chleb));
                insertProdukt("bułki", 2, 0, c.getResources().getDrawable(R.drawable.bulka));
                insertProdukt("jabłka", 2, 0, c.getResources().getDrawable(R.drawable.jablko));
                insertProdukt("pomarańcze", 2, 0, c.getResources().getDrawable(R.drawable.pomarancze));
                insertProdukt("awokado", 2, 0, c.getResources().getDrawable(R.drawable.awokado));
                insertProdukt("fasola czarna", 0, 0, c.getResources().getDrawable(R.drawable.fasolaczarna));
                insertProdukt("fasola biała", 0, 0, c.getResources().getDrawable(R.drawable.fasolabiala));
                insertProdukt("mięso mielone", 0, 0, c.getResources().getDrawable(R.drawable.miesomielone));
                insertProdukt("schab", 0, 0, c.getResources().getDrawable(R.drawable.schab));
                insertProdukt("kiełbasa", 0, 0, c.getResources().getDrawable(R.drawable.kielbasa));
                insertProdukt("szproty w pomidorach", 2, 0, c.getResources().getDrawable(R.drawable.szprot));
                insertProdukt("sałata", 2, 0, c.getResources().getDrawable(R.drawable.salata));
                insertProdukt("soczewica", 0, 0, c.getResources().getDrawable(R.drawable.soczewica));
                insertProdukt("kefir", 1, 0, c.getResources().getDrawable(R.drawable.kefir));
                insertProdukt("przecier pomidorowy", 1, 0, c.getResources().getDrawable(R.drawable.przecier));
                insertProdukt("zioła prowansalskie", 0, 0, c.getResources().getDrawable(R.drawable.ziola));




                insertPrzepis("parówki", 10, "Wrzuć do gotującej się wody parówki");
                insertProduktPrzepis("parówki", "parówki", 4000, false);
                insertProduktPrzepis("bułki", "parówki", 1000, true);

                insertPrzepis("kartofle z kefirem", 30, "Zagotuj dobrze obrane kartofle, rozgnieć je tym takim do ziemniaków. " +
                        "Posól wedle uznania oraz zalej kefirem.");
                insertProduktPrzepis("ziemniaki", "kartofle z kefirem", 700, false);
                insertProduktPrzepis("kefir", "kartofle z kefirem", 350, false);

                insertPrzepis("kanapki ze szprotami", 10, "Chlebek boży posmaruj masełkiem i połóż na nim starannie " +
                        "rozgniecione widelcem szproty.");
                insertProduktPrzepis("szproty w pomidorach", "kanapki ze szprotami", 1000, false);
                insertProduktPrzepis("chleb", "kanapki ze szprotami", 300, false);

                insertPrzepis("spaghetti", 40, "Usmaż mięso na patelni, dodaj przecier i duś na wolnym ogniu. " +
                        "Jeżeli masz ochotę dodaj zioła prowansalskie." + "Makaron wrzuć do gotującej się wody i gotuj na wolnym ogniu około 8 minut czasem mieszając");
                insertProduktPrzepis("mięso mielone", "spaghetti", 200, false);
                insertProduktPrzepis("przecier pomidorowy", "spaghetti", 300, false);
                insertProduktPrzepis("makaron", "spaghetti", 250, false);
                insertProduktPrzepis("zioła prowansalskie", "spaghetti", 300, true);

                insertPrzepis("schab w sosie własnym", 60, "Schab pokrój w plastry około 5cm, włóż do naczynia żaroodpornego wraz z pokrojoną cebulą i piecz przez 60 minut w 190 stopniach");
                insertProduktPrzepis("schab", "Schab w sosie własnym", 500, false);
                insertProduktPrzepis("cebula", "Schab w sosie własnym", 1, false);

                insertPrzepis("zupa fasolowa", 40, "Do garnka z 3 litrami wody dodaj fasole i zagotuj, ziemniaki pokrój w małą kostkę i dorzuć do gotującej się wody");
                insertProduktPrzepis("ziemniaki", "Zupa fasolowa", 200, false);
                insertProduktPrzepis("fasola czarna", "200", 1, false);
                insertProduktPrzepis("fasola biała", "200", 1, false);

            }
            return null;
        }
    }
}

