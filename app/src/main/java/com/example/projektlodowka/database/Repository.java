package com.example.projektlodowka.database;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.widget.EditText;

import com.example.projektlodowka.R;

import java.util.List;

public class Repository {
    private ProduktDao produktDao;
    private LiveData<List<Produkt>> produkty;

    Repository(Application application) {
        BazaDanych db = BazaDanych.getBazaDanych(application);
        produktDao = db.produktDao();
        produkty = produktDao.loadAllOrderNazwa();
    }

    LiveData<List<Produkt>> getProdukty() { return produkty; }

    public void setEditProdukt(Activity activity, int id) { new setEditProduktAsyncTask(activity, produktDao).execute(id); }

    private static class setEditProduktAsyncTask extends AsyncTask<Integer, Void, Produkt> {
        private ProduktDao mAsyncTaskDao;
        private Activity mActivity;

        setEditProduktAsyncTask(Activity activity, ProduktDao dao) {
            mAsyncTaskDao = dao;
            mActivity = activity;
        }

        @Override
        protected Produkt doInBackground(Integer... integers) {
            Produkt p = mAsyncTaskDao.loadId(integers[0]);
            return p;
        }

        @Override
        protected void onPostExecute(Produkt produkt) {
            EditText nazwa = mActivity.findViewById(R.id.produktEditNazwaEditText);
            EditText ilosc = mActivity.findViewById(R.id.produktEditIloscEditText);
            EditText typ = mActivity.findViewById(R.id.produktEditTypEditText);

            nazwa.setText(produkt.getNazwa());
            ilosc.setText(String.valueOf(produkt.getIlosc()));
            typ.setText(String.valueOf(produkt.getTyp()));
        }
    }

    public void insertProdukt(Produkt produkt) { new insertProduktAsyncTask(produktDao).execute(produkt); }

    private static class insertProduktAsyncTask extends AsyncTask<Produkt, Void, Void> {
        private ProduktDao mAsyncTaskDao;
        insertProduktAsyncTask(ProduktDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Produkt... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void deleteProdukt(Produkt produkt) { new deleteProduktAsyncTask(produktDao).execute(produkt); }

    private static class deleteProduktAsyncTask extends AsyncTask<Produkt, Void, Void> {
        private ProduktDao mAsyncTaskDao;
        deleteProduktAsyncTask(ProduktDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final Produkt... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void deleteAllProduct() { new deleteAllProduktAsyncTask(produktDao).execute(new Produkt("a",1,1));}

    private static class deleteAllProduktAsyncTask extends AsyncTask<Produkt, Void, Void> {
        private ProduktDao mAsyncTaskDao;
        deleteAllProduktAsyncTask(ProduktDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(Produkt... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public void updateProdukt(Produkt produkt) { new updateProduktAsyncTask(produktDao).execute(produkt); }

    private static class updateProduktAsyncTask extends AsyncTask<Produkt, Void, Void> {
        private ProduktDao mAsyncTaskDao;
        updateProduktAsyncTask(ProduktDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(Produkt... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
