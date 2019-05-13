package com.example.projektlodowka.database;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

public class RepositoryHistoria {
    private HistoriaDao historiaDao;
    private LiveData<List<Historia>> historia;

    RepositoryHistoria(Application application){
        BazaDanych db = BazaDanych.getBazaDanych(application);
        historiaDao = db.historiaDao();
        historia = historiaDao.loadAll();
    }


    LiveData<List<Historia>> getHistoria() { return historia; }


    public void insertHistoria(Historia historia) {
        new insertHistoriaAsyncTask(historiaDao).execute(historia);
    }

    private static class insertHistoriaAsyncTask extends AsyncTask<Historia, Void, Void> {
        private HistoriaDao mAsyncTaskDao;

        insertHistoriaAsyncTask(HistoriaDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Historia... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void deleteHistoria(Historia historia) {
        new deleteHistoriaAsyncTask(historiaDao).execute(historia);
    }

    private static class deleteHistoriaAsyncTask extends AsyncTask<Historia, Void, Void> {
        private HistoriaDao mAsyncTaskDao;

        deleteHistoriaAsyncTask(HistoriaDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Historia... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }


    public void deleteAllHistoria() {
        new deleteAllHistoriaAsyncTask(historiaDao).execute();
    }

    private static class deleteAllHistoriaAsyncTask extends AsyncTask<Historia, Void, Void> {
        private HistoriaDao mAsyncTaskDao;

        deleteAllHistoriaAsyncTask(HistoriaDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Historia... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}