package com.example.projektlodowka.database;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

public class RepositoryMinutnik {
    private MinutnikDao minutnikDao;
    private LiveData<List<Minutnik>> minutniki;

    RepositoryMinutnik(Application application){
        BazaDanych bd = BazaDanych.getBazaDanych(application);
        minutnikDao = bd.minutnikDao();
        minutniki = minutnikDao.loadAll();
    }


    LiveData<List<Minutnik>> getMinutniki() {
        return minutniki;
    }

    LiveData<List<Minutnik>> getMinutnikiPrzepis(int id) { return minutnikDao.loadPrzepis(id); }

    public void insertMinutnik(Activity activity, Minutnik minutnik) {
        new insertMinutnikAsyncTask(activity, minutnikDao).execute(minutnik);
    }

    private static class insertMinutnikAsyncTask extends AsyncTask<Minutnik, Void, Boolean> {
        private MinutnikDao mAsyncTaskDao;
        private Activity mActivity;

        insertMinutnikAsyncTask(Activity activity, MinutnikDao dao) {
            mAsyncTaskDao = dao;
            mActivity = activity;
        }

        @Override
        protected Boolean doInBackground(final Minutnik... params) {
            Minutnik m = mAsyncTaskDao.loadNazwa(params[0].getNazwa());
            if (m == null) {
                mAsyncTaskDao.insert(params[0]);
                return true;
            } else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean)
                Toast.makeText(mActivity, "Dodano minutnik", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mActivity, "Minutnik o podanej nazwie już istnieje", Toast.LENGTH_SHORT).show();
        }
    }


    public void deleteMinutnik(Minutnik minutnik) {
        new deleteMinutnikAsyncTask(minutnikDao).execute(minutnik);
    }

    private static class deleteMinutnikAsyncTask extends AsyncTask<Minutnik, Void, Void> {
        private MinutnikDao mAsyncTaskDao;

        deleteMinutnikAsyncTask(MinutnikDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Minutnik... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
