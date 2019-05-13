package com.example.projektlodowka.database;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class RepositoryProduktPrzepis {
    private ProduktPrzepisDao produktPrzepisDao;
    private LiveData<List<ProduktPrzepis>> produktyPrzepisy;

    RepositoryProduktPrzepis(Application application){
        BazaDanych db = BazaDanych.getBazaDanych(application);
        produktPrzepisDao = db.produktPrzepisDao();
        produktyPrzepisy = produktPrzepisDao.loadAll();
    }


    LiveData<List<ProduktPrzepis>> getProduktyPrzepisy() {
        return produktyPrzepisy;
    }


    public void insertProduktPrzepis(ProduktPrzepis produktprzepis) {
        new insertProduktPrzepisAsyncTask(produktPrzepisDao).execute(produktprzepis);
    }

    private static class insertProduktPrzepisAsyncTask extends AsyncTask<ProduktPrzepis, Void, Void> {
        private ProduktPrzepisDao mAsyncTaskDao;
        private Activity mActivity;

        insertProduktPrzepisAsyncTask(ProduktPrzepisDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ProduktPrzepis... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    public void deleteProduktPrzepis(ProduktPrzepis produktPrzepis) {
        new deleteProduktPrzepisAsyncTask(produktPrzepisDao).execute(produktPrzepis);
    }

    private static class deleteProduktPrzepisAsyncTask extends AsyncTask<ProduktPrzepis, Void, Void> {
        private ProduktPrzepisDao mAsyncTaskDao;

        deleteProduktPrzepisAsyncTask(ProduktPrzepisDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ProduktPrzepis... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }


    public void updateProduktPrzepis(ProduktPrzepis produktPrzepis) {
        new updateProduktPrzepisAsyncTask(produktPrzepisDao).execute(produktPrzepis);
    }

    private static class updateProduktPrzepisAsyncTask extends AsyncTask<ProduktPrzepis, Void, Void> {
        private ProduktPrzepisDao mAsyncTaskDao;

        updateProduktPrzepisAsyncTask(ProduktPrzepisDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ProduktPrzepis... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
