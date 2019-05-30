package com.example.projektlodowka.database;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class RepositoryProduktPrzepis {
    private ProduktPrzepisDao produktPrzepisDao;
    private ProduktDao produktDao;
    private PrzepisDao przepisDao;
    private LiveData<List<ProduktPrzepis>> produktyPrzepisy;

    RepositoryProduktPrzepis(Application application){
        BazaDanych db = BazaDanych.getBazaDanych(application);
        produktPrzepisDao = db.produktPrzepisDao();
        produktDao = db.produktDao();
        przepisDao = db.przepisDao();
        produktyPrzepisy = produktPrzepisDao.loadAll();
    }


    LiveData<List<ProduktPrzepis>> getProduktyPrzepisy() {
        return produktyPrzepisy;
    }

    LiveData<List<ProduktPrzepis>> getProduktyPrzepisyIdPrzepis(int idPrzepisu){
        return produktPrzepisDao.loadPrzepis(idPrzepisu);
    }

    LiveData<List<ProduktInPrzepis>> getProduktyInPrzepis(int idPrzepisu) { return produktPrzepisDao.loadProduktInPrzepis(idPrzepisu); }

    public void insertProduktPrzepis(ProduktPrzepis produktprzepis) {
        new insertProduktPrzepisAsyncTask(produktPrzepisDao).execute(produktprzepis);
    }

    private static class insertProduktPrzepisAsyncTask extends AsyncTask<ProduktPrzepis, Void, Void> {
        private ProduktPrzepisDao mAsyncTaskDao;

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

    class MyTaskParams {
        String przepisName;
        String produktName;
        int ilosc;
        boolean opcjonalny;

        MyTaskParams(String przepisName, String produktName, int ilosc, boolean opcjonalny) {
            this.przepisName = przepisName;
            this.produktName = produktName;
            this.ilosc = ilosc;
            this.opcjonalny = opcjonalny;
        }
    }

    public void insertProduktPrzepisByName(String przepisName, String produktName, int ilosc, boolean opcjonalny) {
        new insertProduktPrzepisNameAsyncTask(produktPrzepisDao, produktDao, przepisDao)
                .execute(new MyTaskParams(przepisName, produktName, ilosc, opcjonalny));
    }

    private static class insertProduktPrzepisNameAsyncTask extends AsyncTask<MyTaskParams, Void, Void> {
        private ProduktPrzepisDao produktPrzepisDao;
        private PrzepisDao przepisDao;
        private ProduktDao produktDao;

        insertProduktPrzepisNameAsyncTask(ProduktPrzepisDao produktPrzepisDao, ProduktDao produktDao, PrzepisDao przepisDao) {
            this.produktPrzepisDao = produktPrzepisDao;
            this.przepisDao = przepisDao;
            this.produktDao = produktDao;
        }

        @Override
        protected Void doInBackground(final MyTaskParams... params) {
            int produktId = produktDao.loadNazwa(params[0].produktName).getId();
            int przepisId = przepisDao.loadNazwa(params[0].przepisName).getId();
            produktPrzepisDao.insert(new ProduktPrzepis(przepisId, produktId, params[0].ilosc, params[0].opcjonalny));
            return null;
        }
    }
}