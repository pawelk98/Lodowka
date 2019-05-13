package com.example.projektlodowka.database;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

public class RepositoryPoraDnia {
    private PoraDniaDao poraDniaDao;
    private LiveData<List<PoraDnia>> poryDnia;

    RepositoryPoraDnia(Application application){
        BazaDanych bd = BazaDanych.getBazaDanych(application);
        poraDniaDao = bd.poraDniaDao();
        poryDnia = poraDniaDao.loadAll();
    }


    LiveData<List<PoraDnia>> getPoryDnia() {
        return poryDnia;
    }

    LiveData<List<PoraDnia>> getPoryDniaPrzepis(int id) { return poraDniaDao.loadIdPrzepisu(id); }

    public void insertPoraDnia(Activity activity, PoraDnia poraDnia) {
        new insertPoraDniaAsyncTask(activity, poraDniaDao).execute(poraDnia);
    }

    private static class insertPoraDniaAsyncTask extends AsyncTask<PoraDnia, Void, Boolean> {
        private PoraDniaDao mAsyncTaskDao;
        private Activity mActivity;

        insertPoraDniaAsyncTask(Activity activity, PoraDniaDao dao) {
            mAsyncTaskDao = dao;
            mActivity = activity;
        }

        @Override
        protected Boolean doInBackground(final PoraDnia... params) {
            PoraDnia p = mAsyncTaskDao.loadPoraPrzepis(params[0].getPora(), params[0].getIdPrzepisu());
            if (p == null) {
                mAsyncTaskDao.insert(params[0]);
                return true;
            } else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean)
                Toast.makeText(mActivity, "Dodano porę dnia", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mActivity, "Ta pora dnia w tym przepisie już istnieje!", Toast.LENGTH_SHORT).show();
        }
    }


    public void deletePoraDnia(PoraDnia poraDnia) {
        new deletePoraDniaAsyncTask(poraDniaDao).execute(poraDnia);
    }

    private static class deletePoraDniaAsyncTask extends AsyncTask<PoraDnia, Void, Void> {
        private PoraDniaDao mAsyncTaskDao;

        deletePoraDniaAsyncTask(PoraDniaDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final PoraDnia... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
