package com.example.projektlodowka.database;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projektlodowka.R;

import java.util.List;

public class RepositoryPrzepis {

    private LiveData<List<Przepis>> przepisy;
    private PrzepisDao przepisDao;

    RepositoryPrzepis(Application application) {
        BazaDanych db = BazaDanych.getBazaDanych(application);
        przepisDao = db.przepisDao();
        przepisy = przepisDao.loadAllOrderNazwa();
    }


    LiveData<List<Przepis>> getPrzepisy() { return przepisy; }


    public void insertPrzepis(Activity activity, Przepis przepis) { new insertPrzepisAsyncTask(activity, przepisDao).execute(przepis); }

    private static class insertPrzepisAsyncTask extends AsyncTask<Przepis, Void, Boolean> {
        private PrzepisDao mAsyncTaskDao;
        private Activity mActivity;
        insertPrzepisAsyncTask(Activity activity, PrzepisDao dao) {
            mAsyncTaskDao = dao;
            mActivity = activity;
        }

        @Override
        protected Boolean doInBackground(final Przepis... params) {
            Przepis p = mAsyncTaskDao.loadNazwa(params[0].getNazwa());
            if(p == null) {
                mAsyncTaskDao.insert(params[0]);
                return true;
            }
            else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean)
                Toast.makeText(mActivity,"Dodano przepis",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mActivity,"Przepis o podanej nazwie już istnieje",Toast.LENGTH_SHORT).show();
        }
    }


    public void deletePrzepis(Przepis przepis) { new deletePrzepisAsyncTask(przepisDao).execute(przepis); }

    private static class deletePrzepisAsyncTask extends AsyncTask<Przepis, Void, Void> {
        private PrzepisDao mAsyncTaskDao;
        deletePrzepisAsyncTask(PrzepisDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Przepis... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }


    public void updatePrzepis(Activity activity, Przepis przepis) { new updatePrzepisAsyncTask(activity, przepisDao).execute(przepis); }

    private static class updatePrzepisAsyncTask extends AsyncTask<Przepis, Void, Boolean> {
        private PrzepisDao mAsyncTaskDao;
        private Activity mActivity;
        updatePrzepisAsyncTask(Activity activity, PrzepisDao dao) {
            mAsyncTaskDao = dao;
            mActivity = activity;
        }

        @Override
        protected Boolean doInBackground(Przepis... params) {
            Przepis p = mAsyncTaskDao.loadNazwa(params[0].getNazwa());
            if(p == null || params[0].getId() == p.getId()) {
                mAsyncTaskDao.update(params[0]);
                return true;
            }
            else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean)
                Toast.makeText(mActivity,"Edytowano przepis",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mActivity,"Przepis o podanej nazwie już istnieje",Toast.LENGTH_SHORT).show();
        }
    }


    public void setShowPrzepis(Activity activity, int id) {
        new setShowPrzepisAsyncTask(activity, przepisDao).execute(id);
    }

    private static class setShowPrzepisAsyncTask extends AsyncTask<Integer, Void, Przepis> {
        private PrzepisDao mAsyncTaskDao;
        private Activity mActivity;

        setShowPrzepisAsyncTask(Activity activity, PrzepisDao dao) {
            mAsyncTaskDao = dao;
            mActivity = activity;
        }

        @Override
        protected Przepis doInBackground(Integer... integers) {
            return mAsyncTaskDao.loadId(integers[0]);
        }

        @Override
        protected void onPostExecute(Przepis przepis) {
            TextView nazwa = mActivity.findViewById(R.id.przepisShowNazwaTextView);
            TextView czas = mActivity.findViewById(R.id.przepisShowCzasTextView);
            TextView opis = mActivity.findViewById(R.id.przepisShowOpisTextView);

            nazwa.setText(przepis.getNazwa());
            czas.setText(String.valueOf(przepis.getCzas()));
            opis.setText(przepis.getOpis());
        }
    }
}
