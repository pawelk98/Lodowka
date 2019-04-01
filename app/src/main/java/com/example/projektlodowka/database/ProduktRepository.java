package com.example.projektlodowka.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ProduktRepository {
    private ProduktDao produktDao;
    private LiveData<List<Produkt>> produkty;

    ProduktRepository(Application application) {
        BazaDanych db = BazaDanych.getBazaDanych(application);
        produktDao = db.produktDao();
        produkty = produktDao.loadAllOrderNazwa();
    }

    LiveData<List<Produkt>> getProdukty() { return produkty; }

    public void insertProdukt(Produkt produkt) {

        new insertAsyncTask(produktDao).execute(produkt);
    }

    private static class insertAsyncTask extends AsyncTask<Produkt, Void, Void> {

        private ProduktDao mAsyncTaskDao;

        insertAsyncTask(ProduktDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Produkt... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
