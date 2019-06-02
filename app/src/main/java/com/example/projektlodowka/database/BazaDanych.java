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
        private final ProduktPrzepisDao produktPrzepisDao;

        PopulateDbAsync(BazaDanych db) {
            produktDao = db.produktDao();
            przepisDao = db.przepisDao();
            produktPrzepisDao = db.produktPrzepisDao();
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

            Przepis przepis = new Przepis("Parówki",15,"Wlewamy wodę do garnka i stawiamy na piec. Wyjmujemy parówki, jeśli potrzeba obieramy z folii i wkładamy do wody. Kiedy woda zaczyna wrzeć zmniejszamy ogień i gotujemy parówki 3 minuty. Po 3 minutach wyłączamy piec, wyciągamy parówki na talerz i zjadamy. Parówki dobrze smakuja z ketchupem i musztardą. Smacznego!");
            przepisDao.insert(przepis);
            przepis = new Przepis("Makaron z sosem serowo-szpinakowym", 20, "Nastaw wodę na makaron. Na patelni zblanszuj cebulę, po czym dodaj szpinak. Gotuj przez pewien czas. Do szpinaku dodaj śmietanę 30% oraz ser (żółty/feta) wedle uznania. Na koniec przyprawy. Mniam mniam!");
            przepisDao.insert(przepis);
            przepis = new Przepis("Warzywa na patelnię", 30, "Na patelni rozgrzej olej, po czym dodaj warzywa. Smaż na wolnym ogniu dopóki wszystkie nie zmiękną. Na końcu przyprawy. Jedz je ochoczo :-))))  ");
            przepisDao.insert(przepis);
            przepis = new Przepis("Kotlety z kartoflami", 40, "Gotowe kotleciki sojowe rozmięcz w cieplutkim bulionie wołowym, po czym usmaż na oleju kokosowym. Gotowe ćwiarteczki ziemniaczane z czosnkiem z Carrefoura wysyp na blasze i piecz w piekarniku aż do zarumienienia");
            przepisDao.insert(przepis);
            przepis = new Przepis("Karkówka", 90, "Znajdujemy dobrze rozgrzanego grilla. Swieżą karkóweczkę marynujemy w specjalnej magicznej marynacie wujka Radzia. Karkówkę kładziemy na Aluminiowej blasze nad rozgrzanym piecem i gryllujemy dopóki karkóweczka nie wypuści z siebie wszystkich tłuszczy, oraz się nie zwęgli.");
            przepisDao.insert(przepis);
            przepis = new Przepis("Kanapki z chlebem i bagietką", 5, "Bagietkę czosnkową pokrój w drobne plasterki. Chleb rozmiękcz w letniej wodzie i uformuj z niego cienkie kotleciki. Obsmaż chlebowe kotlety z obu stron. Na kanapkach układaj plasterki bagietki oraz chlebowe kotlety. Dla smaku użyj ulubionych przypraw. Nie zapomnij także o ulubionych sosach ;)) Smacznego!");
            przepisDao.insert(przepis);
            przepis = new Przepis("Kawior", 15, "Kup akwarium. Poczekaj aż kultury bakterii rozwiną się w nim na tyle, ze ewoluowane z nich bardziej złożone stworzenia wodne będą składać ikrę. Pozyskaną ikrę zamarynuj w małych słoiczkach i wzbogać się na niej, zostając chlebodawcą połowy swojego rodu. Powodzenia! ;)");
            przepisDao.insert(przepis);
            przepis = new Przepis("Kaszanka", 5, "Krew przeznaczoną na mroczny obrządek podkradnij znajomemu okultyście. Kaszę pożycz od sąsiadki. Ugotowaną kaszę wymieszaj z krwią Bogu ducha winnych świnek. Nie zapomnij o życiodajnym tłuszczyku, czyli słoninie wieprzowej. Dodaj ją do swojej kaszanki. Zdobyte uprzednio jelita wypełnij kaszanką. Sparz wszystko w ogromnym garze. Voila! Tylko nie zjedz wszystkiego sam! :-)");
            przepisDao.insert(przepis);

            return null;
        }
    }
}

