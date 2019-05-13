package com.example.projektlodowka.database;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projektlodowka.R;

import java.util.List;

public class Repository {
    private ProduktDao produktDao;
    private PrzepisDao przepisDao;
    private ProduktPrzepisDao produktPrzepisDao;
    private LiveData<List<Produkt>> produkty;
    private LiveData<List<Przepis>> przepisy;
    private LiveData<List<ProduktPrzepis>> produktyPrzepisy;

    Repository(Application application) {
        BazaDanych db = BazaDanych.getBazaDanych(application);
        produktDao = db.produktDao();
        przepisDao = db.przepisDao();

        produkty = produktDao.loadAllOrderNazwa();
        przepisy = przepisDao.loadAllOrderNazwa();
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
            Spinner typ = mActivity.findViewById(R.id.spinner_prod_edit);

            nazwa.setText(produkt.getNazwa());

            if(produkt.getIlosc()<500) {
                ilosc.setText(String.valueOf(produkt.getIlosc()));
                typ.setSelection(produkt.getTyp() + 3);
            }
            else if(produkt.getIlosc()>1000) {
                ilosc.setText(String.valueOf(produkt.getIlosc()/1000));
                typ.setSelection(produkt.getTyp());
            }
            else {
                ilosc.setText(String.valueOf(produkt.getIlosc()));
                typ.setSelection(produkt.getTyp());
            }
            }

    }

    public void insertProdukt(Activity activity, Produkt produkt) { new insertProduktAsyncTask(activity, produktDao).execute(produkt); }

    private static class insertProduktAsyncTask extends AsyncTask<Produkt, Void, Boolean> {
        private ProduktDao mAsyncTaskDao;
        private Activity mActivity;
        insertProduktAsyncTask(Activity activity, ProduktDao dao) {
            mAsyncTaskDao = dao;
            mActivity = activity;
        }

        @Override
        protected Boolean doInBackground(final Produkt... params) {
            Produkt p = mAsyncTaskDao.loadNazwa(params[0].getNazwa());
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
                Toast.makeText(mActivity,"Dodano produkt",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mActivity,"Produkt o podanej nazwie już istnieje",Toast.LENGTH_SHORT).show();
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

    public void updateProdukt(Activity activity, Produkt produkt) { new updateProduktAsyncTask(activity, produktDao).execute(produkt); }

    private static class updateProduktAsyncTask extends AsyncTask<Produkt, Void, Boolean> {
        private ProduktDao mAsyncTaskDao;
        private Activity mActivity;
        updateProduktAsyncTask(Activity activity, ProduktDao dao) {
            mAsyncTaskDao = dao;
            mActivity = activity;
        }

        @Override
        protected Boolean doInBackground(Produkt... params) {
            Produkt p = mAsyncTaskDao.loadNazwa(params[0].getNazwa());
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
                Toast.makeText(mActivity,"Edytowano produkt",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mActivity,"Produkt o podanej nazwie już istnieje",Toast.LENGTH_SHORT).show();
        }
    }

    LiveData<List<ProduktPrzepis>> getProduktyPrzepisy() { return produktyPrzepisy; }

    public void insertProduktPrzepis(ProduktPrzepis produktprzepis) { new insertProduktPrzepisAsyncTask(produktPrzepisDao).execute(produktprzepis); }

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

    public void deleteProduktPrzepis(ProduktPrzepis produktPrzepis) { new deleteProduktPrzepisAsyncTask(produktPrzepisDao).execute(produktPrzepis); }

    private static class deleteProduktPrzepisAsyncTask extends AsyncTask<ProduktPrzepis, Void, Void> {
        private ProduktPrzepisDao mAsyncTaskDao;
        deleteProduktPrzepisAsyncTask(ProduktPrzepisDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(final ProduktPrzepis... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void updateProduktPrzepis(ProduktPrzepis produktPrzepis) { new updateProduktPrzepisAsyncTask(produktPrzepisDao).execute(produktPrzepis); }

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

}
